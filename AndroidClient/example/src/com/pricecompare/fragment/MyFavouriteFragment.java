package com.pricecompare.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.pricecompare.R;
import com.pricecompare.ViewPagerActivity;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

public class MyFavouriteFragment extends Fragment implements OnItemClickListener{
	JSONArray jsonArr,jsonA;
	JSONObject jsonObj;
	private RadioGroup group;
	private RadioButton kindBtn, distanceBtn, sortBtn;
	private KindFragment kindFragment;
	private DistanceFragment distanceFragment;
	private SortFragment sortFragment;
	private Fragment fragment;
	private ListView listView,mListView;
	private View mView;
	private static int a=0;
	private SampleAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.fragment_myfavourite, null);
		this.mView = v;
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		adapter.add(new SampleItem("请先到用户中心进行登录再浏览此页面，以享受最好的服务", R.drawable.selector_radio_back));
		listView.setAdapter(adapter);
	}
	
	public void onResume(){
		super.onResume();
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
		adapter.add(new SampleItem("请先到用户中心进行登录再浏览此页面，以享受最好的服务", R.drawable.selector_radio_back));
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
			Intent i = new Intent(getActivity(),ViewPagerActivity.class);
			Bundle bundle = new Bundle(); 
			switch(parent.getId()){
			case R.id.fmfPush_List:
				if(jsonA.length()!=0){
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
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("test", e.toString());
			e.printStackTrace();
		}
		

	 }
	public void click(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("click", str);
		String url = HttpUtil.BASE_URL+"click.action";
		HttpUtil.postRequest(url, map);
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

	private void init() {
		// TODO Auto-generated method stub
		listView = (ListView) mView.findViewById(R.id.fmfMyFocus_List);
		mListView = (ListView) mView.findViewById(R.id.fmfPush_List);
		mListView.setOnItemClickListener(this);
	}
	
}