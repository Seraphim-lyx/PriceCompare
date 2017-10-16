package com.pricecompare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pricecompare.UserActivity;
import com.pricecompare.LoginActivity;
import com.pricecompare.R;
import com.pricecompare.ViewPagerActivity;
import com.pricecompare.fragment.LoginFragment;

public class MenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.content_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:	
			newContent = new SampleListFragment();
			break;
		case 1:
			newContent = new MyFavouriteFragment();
			break;
		case 2:
			newContent = new LoginFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof UserActivity) {
			UserActivity fca = (UserActivity) getActivity();
			fca.switchContent(fragment);
		} else if (getActivity() instanceof LoginActivity){
			LoginActivity la = (LoginActivity) getActivity();
			la.switchContent(fragment);
		} 
	}


}
