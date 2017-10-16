package com.pricecompare;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pricecompare.BirdActivity;
import com.pricecompare.R;
import com.pricecompare.ViewPagerUserActivity.AsyncImageLoader.ImageCallback;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;


/***
 * 
 * 说明：ViewPager ，带小圆点导航，适配器采用PagerAdapter，基本可以满足需求
 * 也可以采用FragmentPagerAdapter，有人说，Fragment可以更好的适应平板和手机，
 * 并且可以更好的代码重用，具体这些好处大家试一下就知道了。
 * 
 * @author andylaw
 * 
 * 更多内容请查看博客：http://blog.csdn.net/lyc66666666666
 * 
 */

@SuppressLint("NewApi")
public class ViewPagerUserActivity extends Activity {

	private ViewPager view_pager;
	
	private LayoutInflater inflater;
	
	private TextView textView;

	// 图片的地址，这里可以从服务器获取
	String[] urls;
		
	private ImageView image;
	private View item ;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs;//存放引到图片数组
	private int a,b;
	private ListView listView;
	private Button btnComment, btnBuy, btnCP;
	private EditText edtComment;
	private JSONObject obj, jsonArr;
	private Bundle bundle;
	private List<String> list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpageruser);
		bundle = this.getIntent().getExtras();
		System.out.println(bundle.getString("jsonObj"));
		try {
			obj = new JSONObject(bundle.getString("jsonObj"));
			b = obj.getInt("productId");
			setTitle(obj.getString("productName"));
			System.out.println(obj.getJSONObject("pictures").length());
			Log.v("testViewPager",obj.toString());
			a = obj.getJSONObject("pictures").length();
			urls = new String[a];
			indicator_imgs = new ImageView[a];
			for(int n = 0;n < a;n++){
				urls[n] = obj.getJSONObject("pictures").getString("picture"+n);
			}
			textView = (TextView) findViewById(R.id.descript);
			textView.setText(obj.getString("description"));
			listView = (ListView) findViewById(R.id.CommentList);
			try {
				jsonArr = getComment(b);
				if(jsonArr.getInt("isNull")!=0){
					int x = jsonArr.getJSONObject("comments").length()/3;
					Log.v("test.commentLength",""+x);
					list = new ArrayList<String>();
					for(int i = 0;i < x;i++){
						list.add(jsonArr.getJSONObject("comments").getString("comment"+i)+"\n作者："+jsonArr.getJSONObject("comments").getString("commentId"+i)+"\t\t日期："+getMilliToDate(jsonArr.getJSONObject("comments").getString("commentDate"+i)));	
					}	
					listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
					fixListViewHeight(listView);   
				}
				else{
					list = new ArrayList<String>();
					list.add("没有数据");
					listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			edtComment = (EditText) findViewById(R.id.edtComment);
			btnComment = (Button) findViewById(R.id.buttonComment);
			btnComment.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					if(validate()){
						comment(b);
						Intent i = new Intent(ViewPagerUserActivity.this,ViewPagerUserActivity.class);
						i.putExtras(bundle);
						startActivity(i);
						finish();
					}
				}
				});	
			btnBuy = (Button) findViewById(R.id.button1);
			btnBuy.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					Uri uriUrl;
					try {
						uriUrl = Uri.parse(obj.getString("productWebAddress"));
						Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); 
						startActivity(launchBrowser);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnCP = (Button) findViewById(R.id.button2);
			if(isCP(b)){
				btnCP.setText("取消关注");
			}
			btnCP.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					if(isCP(b)){
						try {
							deleteCP(b);
							btnCP.setText("添加关注");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else{
						try {
							setCP(b);
							btnCP.setText("取消关注");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		List<View> list = new ArrayList<View>();
		inflater = LayoutInflater.from(this);

		/**
		 * 创建多个item （每一条viewPager都是一个item）
		 * 从服务器获取完数据（如文章标题、url地址） 后，再设置适配器
		 */
		for (int i = 0; i < a; i++) {
			item = inflater.inflate(R.layout.item, null);
			((TextView) item.findViewById(R.id.text_view)).setText("第 " + i + " 张图，一共" + a + "张图");
			list.add(item);
		}

		//创建适配器， 把组装完的组件传递进去
		adapter = new MyAdapter(list);
		view_pager.setAdapter(adapter);

		//绑定动作监听器：如翻页的动画
		view_pager.setOnPageChangeListener(new MyListener());
		
		initIndicator();
	}

	
	
	/**
	 * 初始化引导图标
	 * 动态创建多个小圆点，然后组装到线性布局里
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.widget36);// 线性水平布局，负责动态调整导航图标
		
		for (int i = 0; i < a; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;
			
			if (i == 0) { // 初始化第一个为选中状态
				
				indicator_imgs[i].setImageResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setImageResource(R.drawable.indicator);
			}
			((ViewGroup)v).addView(indicator_imgs[i]);
		}
		
	}
	
	
	
	
	/**
	 * 适配器，负责装配 、销毁  数据  和  组件 。
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;

		
		private AsyncImageLoader asyncImageLoader;
		
		public MyAdapter(List<View> list) {
			mList = list;
			asyncImageLoader = new AsyncImageLoader();  
		}

		
		
		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		
		/**
		 * Remove a page for the given position.
		 * 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position)
		 * This method was deprecated in API level . Use instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(mList.get(position));
			
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		
		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container, final int position) {
			

			Drawable cachedImage = asyncImageLoader.loadDrawable(
					urls[position], new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {

							View view = mList.get(position);
							image = ((ImageView) view.findViewById(R.id.image));
							image.setBackground(imageDrawable);
							container.removeView(mList.get(position));
							container.addView(mList.get(position));
							// adapter.notifyDataSetChanged();

						}
					});

			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
			image.setBackground(cachedImage);

			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			// adapter.notifyDataSetChanged();
				

			return mList.get(position);

		}
		
	
	}
	
	
	/**
	 * 动作监听器，可异步加载图片
	 *
	 */
	private class MyListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			if (state == 0) {
				//new MyAdapter(null).notifyDataSetChanged();
			}
		}

		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			
			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {
				
				indicator_imgs[i].setImageResource(R.drawable.indicator);
				
			}
			
			// 改变当前背景图片为：选中
			indicator_imgs[position].setImageResource(R.drawable.indicator_focused);
		}
		
		
	}
	
	

	/**
	 * 异步加载图片
	 */
	static class AsyncImageLoader {

		// 软引用，使用内存做临时缓存 （程序退出，或内存不够则清除软引用）
		private HashMap<String, SoftReference<Drawable>> imageCache;

		public AsyncImageLoader() {
			imageCache = new HashMap<String, SoftReference<Drawable>>();
		}

		/**
		 * 定义回调接口
		 */
		public interface ImageCallback {
			public void imageLoaded(Drawable imageDrawable, String imageUrl);
		}

		
		/**
		 * 创建子线程加载图片
		 * 子线程加载完图片交给handler处理（子线程不能更新ui，而handler处在主线程，可以更新ui）
		 * handler又交给imageCallback，imageCallback须要自己来实现，在这里可以对回调参数进行处理
		 *
		 * @param imageUrl ：须要加载的图片url
		 * @param imageCallback：
		 * @return
		 */
		public Drawable loadDrawable(final String imageUrl,
				final ImageCallback imageCallback) {
			
			//如果缓存中存在图片  ，则首先使用缓存
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					imageCallback.imageLoaded(drawable, imageUrl);//执行回调
					return drawable;
				}
			}

			/**
			 * 在主线程里执行回调，更新视图
			 */
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			
			/**
			 * 创建子线程访问网络并加载图片 ，把结果交给handler处理
			 */
			new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// 下载完的图片放到缓存里
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();
			
			return null;
		}

		
		/**
		 * 下载图片  （注意HttpClient 和httpUrlConnection的区别）
		 */
		public Drawable loadImageFromUrl(String url) {

			try {
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*15);
				HttpGet get = new HttpGet(url);
				HttpResponse response;

				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();

					Drawable d = Drawable.createFromStream(entity.getContent(),
							"src");

					return d;
				} else {
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		//清除缓存
		public void clearCache() {

			if (this.imageCache.size() > 0) {

				this.imageCache.clear();
			}

		}

	}
	public void onBirdPressed(int pos) {
		Log.v("test","pos is "+pos);
		Intent intent = BirdActivity.newInstance(this, pos);
		startActivity(intent);
	}
	 public void fixListViewHeight(ListView listView) {   
	        // 如果没有设置数据适配器，则ListView没有子项，返回。  
	        ListAdapter listAdapter = listView.getAdapter();  
	        int totalHeight = 0;   
	        if (listAdapter == null) {   
	            return;   
	        }   
	        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {     
	            View listViewItem = listAdapter.getView(index , null, listView);  
	            // 计算子项View 的宽高   
	            listViewItem.measure(0, 0);    
	            // 计算所有子项的高度和
	            totalHeight += listViewItem.getMeasuredHeight();    
	        }   
	   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        // listView.getDividerHeight()获取子项间分隔符的高度   
	        // params.height设置ListView完全显示需要的高度    
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))+ 20;   
	        listView.setLayoutParams(params);   
	    }      
	public static String getMilliToDate(String time){
		Date date = new Date(Long.valueOf(time));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	private JSONObject comment(int productid){
		String comment = edtComment.getText().toString();
		try{
			query(comment, productid);
		}
		catch(Exception e){
			DialogUtil.showDialog(this, "服务器异常", false);
			e.printStackTrace();
		}		
		return null;
	}
	private void query(String comment,int productid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("comment", comment);
		map.put("productid", ""+productid);
		String url = HttpUtil.BASE_URL + "comment.action";
		HttpUtil.postRequest(url, map);
	}
	private boolean isCP(int productid){
		JSONObject json;
		try{
			json = isCustomerProduct(productid);
			if(json.getInt("isCustomerProduct")==1){
				return true;
			}
		}catch(Exception e){
			DialogUtil.showDialog(this, "服务器异常", false);
			e.printStackTrace();
		}
		return false;
	}
	private JSONObject isCustomerProduct(int productid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("productid", ""+productid);
		String url = HttpUtil.BASE_URL + "isCustomerProduct.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	private void deleteCP(int productid){
		try {
			deleteCustomerProduct(productid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void deleteCustomerProduct(int productid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("productid", ""+productid);
		String url = HttpUtil.BASE_URL + "deleteCustomerProduct.action";
		HttpUtil.postRequest(url, map);
	}
	private void setCP(int productid){
		try {
			setCustomerProduct(productid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setCustomerProduct(int productid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("productid", ""+productid);
		String url = HttpUtil.BASE_URL + "setCustomerProduct.action";
		HttpUtil.postRequest(url, map);
	}
	private JSONObject getComment(int productid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("productid", ""+productid);
		String url = HttpUtil.BASE_URL + "getListComment.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	private boolean validate(){
		String comment = edtComment.getText().toString().trim();
		if(comment==null){
			Toast.makeText(this, "评论不能为空",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
