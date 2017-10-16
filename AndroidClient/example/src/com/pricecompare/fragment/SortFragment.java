package com.pricecompare.fragment;

import java.util.ArrayList;
import java.util.List;

import com.pricecompare.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SortFragment extends Fragment implements OnItemClickListener {
	private ListView listView;
	private KindAdapter listAdapter;
	private List<String> list = new ArrayList<String>();

	private int listSelect = -1;

	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_sort, null);
		initData();
		init(view);
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (list != null && list.size() > 0)
			list.clear();
		list.add("按商品入库时间升序排列");
		list.add("按商品入库时间降序排列");
		list.add("按商品价格升序排列");
		list.add("按商品价格降序排列");
		list.add("全部商品");
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		listView = (ListView) view.findViewById(R.id.sort_list);
		listAdapter = new KindAdapter(getActivity());
		listAdapter.setList(list);
		listAdapter.setClickItem(listSelect);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		listAdapter.setClickItem(position);
		listAdapter.notifyDataSetChanged();

		listSelect = position;
		handler.obtainMessage(2, list.get(position)).sendToTarget();
	}

}