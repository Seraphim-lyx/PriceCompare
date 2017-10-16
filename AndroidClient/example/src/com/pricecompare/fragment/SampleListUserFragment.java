package com.pricecompare.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pricecompare.R;
import com.pricecompare.ViewPagerActivity;
import com.pricecompare.R.id;
import com.pricecompare.R.layout;
import com.pricecompare.ViewPagerUserActivity;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;



public class SampleListUserFragment extends Fragment implements OnItemClickListener{
	JSONArray jsonArr,jsonA;
	JSONObject jsonObj;
	private RadioGroup group;
	private RadioButton kindBtn, distanceBtn, sortBtn;
	private KindFragment kindFragment;
	private DistanceFragment distanceFragment;
	private SortFragment sortFragment;
	private Fragment fragment;
	private PullToRefreshListView mPullRefreshListView;
	private ListView listView,mListView;
	private View mView;
	private static int a=0, page = 0, nextPage = 1;
	private SampleAdapter adapter;
	private Handler handler = new Handler() {  
		  
		 @Override  
	     public void handleMessage(Message msg) {  
	            // TODO Auto-generated method stub  
	            super.handleMessage(msg);  
	            switch (msg.what) {  
	            case 0:// 分类发来的消息  
	                try {
						searchBrand(msg.obj.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SampleListUserFragment())  
	                        .commit();  
	                group.clearCheck();
	                break;    
	            case 2:// 排序发来的消息  
	                Toast.makeText(getActivity(), msg.obj.toString(),  
	                        Toast.LENGTH_SHORT).show(); 
	                try {
	                	if(msg.obj.toString().equals("按商品入库时间升序排列")){
	                    	changeSequence("1");
	                    }
	                    else if(msg.obj.toString().equals("按商品入库时间降序排列")){
	                    	changeSequence("2");
	                    }
	                    else if(msg.obj.toString().equals("按商品价格升序排列")){
	                    	changeSequence("3");
	                    }
	                    else if(msg.obj.toString().equals("按商品价格降序排列")){
	                    	changeSequence("4");
	                    }
	                    else{
	                    	changeSequence("0");
	                    }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}   	
	                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SampleListFragment()) 
	                        .commit();    
	                group.clearCheck();  
	                break;   
	            }  
	        }  
	  
	    }; 

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.fragment_samplelistuser, null);
		this.mView = v;
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		kindFragment = new KindFragment();
		kindFragment.setHandler(handler);
		distanceFragment = new DistanceFragment();
		sortFragment = new SortFragment();
		sortFragment.setHandler(handler); 
		init();
		SampleAdapter adapterPush = new SampleAdapter(getActivity());
		getPush();
		try {
			if(jsonA.getJSONObject(0).getInt("isNull")!=0){
				for(int i =0;i<jsonA.length();i++)
					adapterPush.add(new SampleItem(jsonA.getJSONObject(i).getString("productName")+"\n"+jsonA.getJSONObject(i).getString("productWebsite")+"\n"+"价格："+jsonA.getJSONObject(i).getDouble("productPrice"), R.drawable.selector_radio_back));
			}
			else{
				adapterPush.add(new SampleItem("没有数据", R.drawable.selector_radio_back));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(a == 0){
			try {
				search("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			a = 1;
		}
		mListView.setAdapter(adapterPush);
		adapter = new SampleAdapter(getActivity());
		sendMessage();
		try {
			if(jsonArr.getJSONObject(0).getInt("isNull")!=0){
				for(int i =0;i<jsonArr.length();i++)
					adapter.add(new SampleItem(jsonArr.getJSONObject(i).getString("productName")+"\n"+jsonArr.getJSONObject(i).getString("productWebsite")+"\n"+"价格："+jsonArr.getJSONObject(i).getDouble("productPrice"), R.drawable.selector_radio_back));
			}			
			else{
				adapter.add(new SampleItem("没有数据", R.drawable.selector_radio_back));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setAdapter(adapter);
	}
	
	public void onResume(){
		super.onResume();
		kindFragment = new KindFragment();
		kindFragment.setHandler(handler);
		distanceFragment = new DistanceFragment();
		sortFragment = new SortFragment();
		sortFragment.setHandler(handler); 
		init();
		SampleAdapter adapterPush = new SampleAdapter(getActivity());
		getPush();
		try {
			if(jsonA.getJSONObject(0).getInt("isNull")!=0){
				for(int i =0;i<jsonA.length();i++)
					adapterPush.add(new SampleItem(jsonA.getJSONObject(i).getString("productName")+"\n"+jsonA.getJSONObject(i).getString("productWebsite")+"\n"+"价格："+jsonA.getJSONObject(i).getDouble("productPrice"), R.drawable.selector_radio_back));
			}
			else{
				adapterPush.add(new SampleItem("没有数据", R.drawable.selector_radio_back));
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mListView.setAdapter(adapterPush);
		adapter = new SampleAdapter(getActivity());
		sendMessage();
		try {
			if(jsonArr.getJSONObject(0).getInt("isNull")!=0){
				for(int i =0;i<jsonArr.length();i++)
					adapter.add(new SampleItem(jsonArr.getJSONObject(i).getString("productName")+"\n"+jsonArr.getJSONObject(i).getString("productWebsite")+"\n"+"价格："+jsonArr.getJSONObject(i).getDouble("productPrice"), R.drawable.selector_radio_back));
			}			
			else{
				adapter.add(new SampleItem("没有数据", R.drawable.selector_radio_back));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setAdapter(adapter);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		try {
			System.out.println(parent.getId());
			Intent i = new Intent(getActivity(),ViewPagerUserActivity.class);
			Bundle bundle = new Bundle(); 
			switch(parent.getId()){
			case R.id.fsupush_list:
				if(jsonA.getJSONObject(0).getInt("isNull")!=0){
					bundle.putString("jsonObj", jsonA.getJSONObject(position).toString());
					try {
						click(""+jsonA.getJSONObject(position).getInt("productId"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						DialogUtil.showDialog(getActivity(), "服务器异常", false);
						e.printStackTrace();
					}
					i.putExtras(bundle);
					startActivity(i);
				}
				break;
			case 16908298:
				if(jsonArr.getJSONObject(0).getInt("isNull")!=0){
					bundle.putString("jsonObj", jsonArr.getJSONObject(position-1).toString());
					try {
						click(""+jsonArr.getJSONObject(position-1).getInt("productId"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						DialogUtil.showDialog(getActivity(), "服务器异常", false);
						e.printStackTrace();
					}
					i.putExtras(bundle);
					startActivity(i);
				}
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("test", e.toString());
			e.printStackTrace();
		}
		

	 }
	public boolean sendMessage(){
		String str = "send";
		
		try{
			jsonArr = query(str);	
			return true;
			
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return false;
	}
	public void click(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("click", str);
		String url = HttpUtil.BASE_URL+"click.action";
		HttpUtil.postRequest(url, map);
	}
	public static JSONArray query(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("send", str);
		String url = HttpUtil.BASE_URL+"test.action";
		String jsonStr = HttpUtil.postRequest(url, map);
		Log.v("jsonStr",jsonStr);
		JSONArray json = new JSONArray(jsonStr);
		Log.v("json",json.toString());
		return json;
	}
	public boolean getPush(){
		String str = "push";
		try{
			jsonA = push(str);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return false;
	}
	public static JSONArray push(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("push", str);
		String url = HttpUtil.BASE_URL+"push.action";
		String jsonStr = HttpUtil.postRequest(url, map);
		Log.v("jsonStr",jsonStr);
		JSONArray json = new JSONArray(jsonStr);
		Log.v("json",json.toString());
		return json;
	}
	public static void search(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", str);
		String url = HttpUtil.BASE_URL+"search.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}
	public static void searchBrand(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("brand", str);
		String url = HttpUtil.BASE_URL+"searchBrand.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}
	public static void changeSequence(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("s", str);
		String url = HttpUtil.BASE_URL+"changeSequence.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}
	public static void changePage(int i,int j) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("page", ""+i);
		map.put("nextPage", ""+j);
		String url = HttpUtil.BASE_URL+"changePage.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}

	private void init() {
		// TODO Auto-generated method stub
		mPullRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.fsuSamplelist);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				 if (refreshView.isHeaderShown()){
					 String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						try {
							page = 0;
							nextPage = 1;
							changePage(page,nextPage);
							adapter.clear();
							sendMessage();
							for(int i =0;i<jsonArr.length();i++)
								adapter.add(new SampleItem(jsonArr.getJSONObject(i).getJSONObject("info").getString("name")+"\n"+jsonArr.getJSONObject(i).getJSONObject("info").getString("website")+"\n"+"价格："+jsonArr.getJSONObject(i).getJSONObject("info").getDouble("price"), R.drawable.selector_radio_back));
							adapter.notifyDataSetChanged();
							new GetDataTask().execute();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
				 else{
					 String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						try {
							page = page+1;
							nextPage = nextPage+1;
							changePage(page,nextPage);
							adapter.clear();
							sendMessage();
							for(int i =0;i<jsonArr.length();i++)
								adapter.add(new SampleItem(jsonArr.getJSONObject(i).getJSONObject("info").getString("name")+"\n"+jsonArr.getJSONObject(i).getJSONObject("info").getString("website")+"\n"+"价格："+jsonArr.getJSONObject(i).getJSONObject("info").getDouble("price"), R.drawable.selector_radio_back));
							adapter.notifyDataSetChanged();
							listView.smoothScrollToPosition(0, 0);
							new GetDataTask().execute();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }

				// Do work to refresh the list here.
				
			}
		});

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		mPullRefreshListView.setMode(Mode.BOTH);
		listView = mPullRefreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		mPullRefreshListView.setOnPullEventListener(soundListener);
		mListView = (ListView) mView.findViewById(R.id.fsupush_list);
		mListView.setOnItemClickListener(this);
		group = (RadioGroup) mView.findViewById(R.id.fsugroup);
		kindBtn = (RadioButton) mView.findViewById(R.id.fsukind);
		kindBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.fsufragment_container, kindFragment).commit();
		
			}
		});
		sortBtn = (RadioButton) mView.findViewById(R.id.fsusort);
		sortBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.fsufragment_container, sortFragment).commit();
		fragment = sortFragment;
			}
		});
	}
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
}
