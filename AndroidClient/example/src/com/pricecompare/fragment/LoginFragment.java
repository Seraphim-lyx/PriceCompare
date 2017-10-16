package com.pricecompare.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pricecompare.UserActivity;
import com.pricecompare.R;
import com.pricecompare.RegisterActivity;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

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
import android.widget.Toast;

public class LoginFragment extends Fragment{
	private View mView;
	EditText etName,etPass;
	Button bnLogin,bnRegister;
	JSONObject jsonObj;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login, null);
		this.mView = v;
		return v;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		etName = (EditText)mView.findViewById(R.id.userEditText);
		etPass = (EditText)mView.findViewById(R.id.pwdEditText);
		bnLogin = (Button)mView.findViewById(R.id.bnLogin);
		bnRegister = (Button)mView.findViewById(R.id.bnRegister);
		bnLogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(validate()){
					if(login()){
						try {
							Bundle bundle = new Bundle(); 
							bundle.putString("jsonObj",jsonObj.getJSONObject("user").toString());
							Intent intent = new Intent(getActivity(),UserActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
		});
		bnRegister.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(getActivity(),RegisterActivity.class);
				startActivity(intent);
			}
		});
		
	}
	private boolean validate(){
		String username = etName.getText().toString().trim();
		String password = etPass.getText().toString().trim();
		if(username.equals("")||password.equals("")){
			Toast.makeText(getActivity(), "用户名和密码不能为空",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private boolean login(){
		String username = etName.getText().toString();
		String password = etPass.getText().toString();
		try{
			jsonObj = query(username, password);
			if(jsonObj.getInt("userid")>0)
				return true;
			else if(jsonObj.getInt("userid")==0)
				Toast.makeText(getActivity(), "用户名和密码错误，请重新输入",Toast.LENGTH_SHORT).show();
			else if(jsonObj.getInt("userid")== -1)
				Toast.makeText(getActivity(), "账号已被封锁，禁止登陆",Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			DialogUtil.showDialog(getActivity(), "服务器异常", false);
			e.printStackTrace();
		}
		return false;
	}
	private JSONObject query(String username, String password) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		String url = HttpUtil.BASE_URL + "login.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
		
}
	


