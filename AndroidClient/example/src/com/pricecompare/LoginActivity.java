package com.pricecompare;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pricecompare.R;
import com.pricecompare.fragment.MenuFragment;
import com.pricecompare.fragment.LoginFragment;
import com.pricecompare.fragment.SampleListFragment;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

public class LoginActivity extends BaseActivity{
private Fragment mContent;
private PullToRefreshListView mPullRefreshListView;
private ListView listView;
private JSONObject obj;
	
	public LoginActivity() {
		super(R.string.changing_fragments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new SampleListFragment();	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(mContent.isAdded())
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSubmitButtonEnabled(true); 
		searchView.setOnQueryTextListener(new OnQueryTextListener() {  
			  
            @Override  
            public boolean onQueryTextSubmit(String query) {  
                Log.v("test", "onQueryTextSubmit(), query=" + query); 
                try {
                	query(query);
                	changeSequence("3");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.v("test", e.toString());
				}
                Intent i = new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                return true;  
            }  
  
            @Override  
            public boolean onQueryTextChange(String newText) {  
                Log.v("test", "onQueryTextChange(), newText=" + newText);  
                return false;  
            }  
              
        });  
		return true;
	}
	public static void query(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", str);
		String url = HttpUtil.BASE_URL+"search.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}
	public static void changeSequence(String str) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("s", str);
		String url = HttpUtil.BASE_URL+"changeSequence.action";
		String jsonStr = HttpUtil.postRequest(url, map);
	}
}

