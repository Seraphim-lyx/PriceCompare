package com.pricecompare.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pricecompare.AlterPasswordActivity;
import com.pricecompare.KeywordActivity;
import com.pricecompare.R;
import com.pricecompare.RegisterActivity;
import com.pricecompare.UserActivity;

public class UserFragment extends Fragment{
	private View mView;
	TextView text;
	Button bnKeyword,bnPassword,bnLogout;
	JSONObject obj; 
	Bundle bundle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_user, null);
		this.mView = v;
		return v;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);	
		bundle = getActivity().getIntent().getExtras();
		Log.v("test",bundle.getString("jsonObj"));
		text = (TextView) getActivity().findViewById(R.id.fuText);
		bnKeyword = (Button) getActivity().findViewById(R.id.btnKeyword);
		bnPassword = (Button) getActivity().findViewById(R.id.btnChange);
		bnLogout = (Button) getActivity().findViewById(R.id.btnLogout);
		try {
			obj = new JSONObject(bundle.getString("jsonObj"));
			text.setText(obj.getString("name")+",»¶Ó­Äã");
			bnKeyword.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(getActivity(),KeywordActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			bnPassword.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(getActivity(),AlterPasswordActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	

		
