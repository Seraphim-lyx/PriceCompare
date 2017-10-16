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

import com.pricecompare.BirdActivity;
import com.pricecompare.R;
import com.pricecompare.ViewPagerActivity.AsyncImageLoader.ImageCallback;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;


/***
 * 
 * ˵����ViewPager ����СԲ�㵼��������������PagerAdapter������������������
 * Ҳ���Բ���FragmentPagerAdapter������˵��Fragment���Ը��õ���Ӧƽ����ֻ���
 * ���ҿ��Ը��õĴ������ã�������Щ�ô������һ�¾�֪���ˡ�
 * 
 * @author andylaw
 * 
 * ����������鿴���ͣ�http://blog.csdn.net/lyc66666666666
 * 
 */

@SuppressLint("NewApi")
public class ViewPagerActivity extends Activity {

	private ViewPager view_pager;
	
	private LayoutInflater inflater;
	
	private TextView textView;

	// ͼƬ�ĵ�ַ��������Դӷ�������ȡ
	String[] urls;
		
	private ImageView image;
	private View item ;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs;//�������ͼƬ����
	private int a,b;
	private ListView listView;
	private JSONArray jsonArr;
	private Button buttonBuy;
	private JSONObject obj;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		Bundle bundle = this.getIntent().getExtras();
		System.out.println(bundle.getString("jsonObj"));
		try {
			obj = new JSONObject(bundle.getString("jsonObj"));
			b = obj.getInt("productId");
			setTitle(obj.getString("productName"));
			System.out.println(obj.getJSONObject("pictures").length());
			a = obj.getJSONObject("pictures").length();
			urls = new String[a];
			indicator_imgs = new ImageView[a];
			for(int n = 0;n < a;n++){
				urls[n] = obj.getJSONObject("pictures").getString("picture"+n);
			}
			textView = (TextView) findViewById(R.id.avDescript);
			textView.setText(obj.getString("description"));
			listView = (ListView) findViewById(R.id.avCommentList);
			buttonBuy = (Button) findViewById(R.id.avButtonBuy);
			buttonBuy.setOnClickListener(new OnClickListener(){
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
			if(obj.getJSONObject("comments").length()!=0){
				List<String> list = new ArrayList<String>();
				int x = obj.getJSONObject("comments").length()/3;
				for(int i = 0;i < x;i++){
					list.add(obj.getJSONObject("comments").getString("comment"+i)+"\n���ߣ�"+obj.getJSONObject("comments").getString("commentId"+i)+"\t\t���ڣ�"+getMilliToDate(obj.getJSONObject("comments").getString("commentDate"+i)));
				}	
				listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
				fixListViewHeight(listView);   
			}
			else{
				List<String> list = new ArrayList<String>();
				list.add("û������");
				listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		view_pager = (ViewPager) findViewById(R.id.avView_pager);
		List<View> list = new ArrayList<View>();
		inflater = LayoutInflater.from(this);

		/**
		 * �������item ��ÿһ��viewPager����һ��item��
		 * �ӷ�������ȡ�����ݣ������±��⡢url��ַ�� ��������������
		 */
		for (int i = 0; i < a; i++) {
			item = inflater.inflate(R.layout.item, null);
			int j=i+1;
			((TextView) item.findViewById(R.id.text_view)).setText("�� " + j + " ��ͼ��һ��" + a + "��ͼ");
			list.add(item);
		}

		//������������ ����װ���������ݽ�ȥ
		adapter = new MyAdapter(list);
		view_pager.setAdapter(adapter);

		//�󶨶������������緭ҳ�Ķ���
		view_pager.setOnPageChangeListener(new MyListener());
		
		initIndicator();
	}

	
	
	/**
	 * ��ʼ������ͼ��
	 * ��̬�������СԲ�㣬Ȼ����װ�����Բ�����
	 */
	private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.avRL);// ����ˮƽ���֣�����̬��������ͼ��
		
		for (int i = 0; i < a; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;
			
			if (i == 0) { // ��ʼ����һ��Ϊѡ��״̬
				
				indicator_imgs[i].setImageResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setImageResource(R.drawable.indicator);
			}
			((ViewGroup)v).addView(indicator_imgs[i]);
		}
		
	}
	
	
	
	
	/**
	 * ������������װ�� ������  ����  ��  ��� ��
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
		 * ������������� �����ٵ�ǰҳ��ǰһ����ǰһ����ҳ��
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
	 * ���������������첽����ͼƬ
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
			
			// �ı����е����ı���ͼƬΪ��δѡ��
			for (int i = 0; i < indicator_imgs.length; i++) {
				
				indicator_imgs[i].setImageResource(R.drawable.indicator);
				
			}
			
			// �ı䵱ǰ����ͼƬΪ��ѡ��
			indicator_imgs[position].setImageResource(R.drawable.indicator_focused);
		}
		
		
	}
	
	

	/**
	 * �첽����ͼƬ
	 */
	static class AsyncImageLoader {

		// �����ã�ʹ���ڴ�����ʱ���� �������˳������ڴ治������������ã�
		private HashMap<String, SoftReference<Drawable>> imageCache;

		public AsyncImageLoader() {
			imageCache = new HashMap<String, SoftReference<Drawable>>();
		}

		/**
		 * ����ص��ӿ�
		 */
		public interface ImageCallback {
			public void imageLoaded(Drawable imageDrawable, String imageUrl);
		}

		
		/**
		 * �������̼߳���ͼƬ
		 * ���̼߳�����ͼƬ����handler�������̲߳��ܸ���ui����handler�������̣߳����Ը���ui��
		 * handler�ֽ���imageCallback��imageCallback��Ҫ�Լ���ʵ�֣���������ԶԻص��������д���
		 *
		 * @param imageUrl ����Ҫ���ص�ͼƬurl
		 * @param imageCallback��
		 * @return
		 */
		public Drawable loadDrawable(final String imageUrl,
				final ImageCallback imageCallback) {
			
			//��������д���ͼƬ  ��������ʹ�û���
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					imageCallback.imageLoaded(drawable, imageUrl);//ִ�лص�
					return drawable;
				}
			}

			/**
			 * �����߳���ִ�лص���������ͼ
			 */
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			
			/**
			 * �������̷߳������粢����ͼƬ ���ѽ������handler����
			 */
			new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// �������ͼƬ�ŵ�������
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();
			
			return null;
		}

		
		/**
		 * ����ͼƬ  ��ע��HttpClient ��httpUrlConnection������
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

		//�������
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
	        // ���û��������������������ListViewû��������ء�  
	        ListAdapter listAdapter = listView.getAdapter();  
	        int totalHeight = 0;   
	        if (listAdapter == null) {   
	            return;   
	        }   
	        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {     
	            View listViewItem = listAdapter.getView(index , null, listView);  
	            // ��������View �Ŀ��   
	            listViewItem.measure(0, 0);    
	            // ������������ĸ߶Ⱥ�
	            totalHeight += listViewItem.getMeasuredHeight();    
	        }   
	   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        // listView.getDividerHeight()��ȡ�����ָ����ĸ߶�   
	        // params.height����ListView��ȫ��ʾ��Ҫ�ĸ߶�    
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))+ 20;   
	        listView.setLayoutParams(params);   
	    }      
	public static String getMilliToDate(String time){
		Date date = new Date(Long.valueOf(time));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	

}
