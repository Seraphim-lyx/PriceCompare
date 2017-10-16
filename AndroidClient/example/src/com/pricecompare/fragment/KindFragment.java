package com.pricecompare.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.pricecompare.R;
import com.pricecompare.util.HttpUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class KindFragment extends Fragment implements OnItemClickListener {
	private ListView listView, detailListView;
	private List<String> list = new ArrayList<String>(),
			detaillist = new ArrayList<String>();
	private KindAdapter listAdapter, detailAdapter;
	private Map<String, String[]> map = new HashMap<String, String[]>();
	private String[] strs;

	//用于保存选中的列表索引值
	private int listSelect = -1, detailSelect = -1;

	private Handler handler;
	
	private JSONArray jsonA,jsonB;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_kind, null);
		initData();
		init(view);

		return view;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		getType();
		
		for(int i=0 ; i<jsonA.length() ; i++){
			try {
				getBrand(jsonA.getJSONObject(i).getString("name"));
				strs =new String[jsonB.length()];
				for(int j=0;j<jsonB.length();j++){
					strs[j] = jsonB.getJSONObject(j).getString("name");
					
				}
				Log.v("test",strs.toString());
				map.put(jsonA.getJSONObject(i).getString("name"), strs);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for(Object key : map.keySet()){
			Log.v("test",key+":"+map.get(key));
		}
		if (list != null && list.size() > 0)
			list.clear();
		for (Map.Entry entry : map.entrySet()){
			list.add(entry.getKey().toString());
		}
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void init(View view) {
		// TODO Auto-generated method stub
		listView = (ListView) view.findViewById(R.id.kind_list);
		listAdapter = new KindAdapter(getActivity());
		listAdapter.setList(list);
		listAdapter.setClickItem(listSelect);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		detailListView = (ListView) view.findViewById(R.id.kind_detil_list);
		detailAdapter = new KindAdapter(getActivity());
		detailAdapter.setList(detaillist);
		detailAdapter.setClickItem(detailSelect);
		detailListView.setAdapter(detailAdapter);
		detailListView.setOnItemClickListener(this);
	}

	/**
	 * 加载子列表数据
	 * 
	 * @param key
	 * @return
	 */
	private boolean addDetailList(String key) {
		if (detaillist != null && detaillist.size() > 0)
			detaillist.clear();
		Log.v("test",key);
		if(map.get(key).equals(null)){
			Log.v("test","value is null");
		}else{
			Log.v("test","value "+map.get(key).length);
		}
		String[] detailArray = map.get(key);
		Log.v("test",""+detailArray.length);
		if (detailArray.length <= 0)
			return false;
		for (String str : detailArray){
			detaillist.add(str);
			
		}
			
		return true;
	}

	/**
	 * ListView点击事件
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (parent.getId() == R.id.kind_list) {
			listAdapter.setClickItem(position);
			listAdapter.notifyDataSetChanged();

			listSelect = position;
			detailSelect = -1;

			boolean hasDetail = addDetailList(list.get(position));
			Log.v("test",list.get(position));
			detailAdapter.setClickItem(-1);
			detailAdapter.notifyDataSetChanged();
			if (!hasDetail)// 将点击结果发送到主界面
				handler.obtainMessage(0, list.get(position)).sendToTarget();
		} else if (parent.getId() == R.id.kind_detil_list) {
			detailAdapter.setClickItem(position);
			detailAdapter.notifyDataSetChanged();
			detailSelect = position;
			Log.v("test", detaillist.get(position));
			//将点击结果发送到主界面
			handler.obtainMessage(0, detaillist.get(position)).sendToTarget();

		}

	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public boolean getType(){
		String str = "type";
		try{
			jsonA = type(str);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return false;
	}
	public static JSONArray type(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", str);
		String url = HttpUtil.BASE_URL+"type.action";
		String jsonStr = HttpUtil.postRequest(url, map);
		Log.v("jsonStr",jsonStr);
		JSONArray json = new JSONArray(jsonStr);
		Log.v("json",json.toString());
		return json;
	}
	public boolean getBrand(String str){
		try{
			jsonB = brand(str);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return false;
	}
	public static JSONArray brand(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("brand", str);
		String url = HttpUtil.BASE_URL+"brand.action";
		String jsonStr = HttpUtil.postRequest(url, map);
		Log.v("jsonStr",jsonStr);
		JSONArray json = new JSONArray(jsonStr);
		Log.v("json",json.toString());
		return json;
	}

}
