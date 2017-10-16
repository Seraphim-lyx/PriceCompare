package com.pricecompare;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlterPasswordActivity extends Activity{
	Bundle bundle;
	EditText edtOpw,edtNpw1,edtNpw2;
	Button btnAlter, btnCancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alterpassword);
		setTitle("更改密码");
		bundle = this.getIntent().getExtras();
		edtOpw = (EditText) findViewById(R.id.edtOpw);
		edtNpw1 = (EditText) findViewById(R.id.edtNpw1);
		edtNpw2 = (EditText) findViewById(R.id.edtNpw2);
		btnAlter = (Button) findViewById(R.id.btnAlter);
		btnCancel = (Button) findViewById(R.id.btnAlterCancel);
		Log.v("test","Keyword"+bundle.getString("jsonObj"));
		btnAlter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				String opw = edtOpw.getText().toString();
				String npw1 = edtNpw1.getText().toString();
				String npw2 = edtNpw2.getText().toString();
				if(npw1.equals(npw2)){
					if(isOpw(opw)){
						if(validate()){
							try {
								changePwd(npw1);
								Toast.makeText(AlterPasswordActivity.this, "密码更改完毕",Toast.LENGTH_SHORT).show();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				finish();
			}
		});
	}
	private boolean isOpw(String opw){
		JSONObject jsonObj;
		try{
			jsonObj = query(opw);
			if(jsonObj.getInt("userid")>0)
				return true;
			else
				Toast.makeText(this, "旧密码错误，请重新输入",Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			DialogUtil.showDialog(this, "服务器异常", false);
			e.printStackTrace();
		}
		return false;
	}
	private boolean validate(){
		String opw = edtOpw.getText().toString().trim();
		String npw1 = edtNpw1.getText().toString().trim();
		String npw2 = edtNpw2.getText().toString().trim();
		if(opw.equals("")||npw1.equals("")||npw2.equals("")){
			Toast.makeText(this, "密码不能为空",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(npw1.length()<6||npw2.length()>18){
			Toast.makeText(this, "新密码需在6-18位之间",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private JSONObject query(String password) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("password", password);
		String url = HttpUtil.BASE_URL + "isOpw.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	private void changePwd(String npw) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("newPassword", npw);
		String url = HttpUtil.BASE_URL + "changePwd.action";
		HttpUtil.postRequest(url, map);
	}

}
