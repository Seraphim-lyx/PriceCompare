package com.pricecompare;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.pricecompare.R;
import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	EditText etName, etPass1, etPass2, etPhone;
	Button bnRegister, bnCancel;
	RadioGroup sexGroup, eduGroup;
	RadioButton radioMale, radioFemale, radioPrimary, radioJunior, radioSenior,radioUniversity;
	String sex = "0" ,edu = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etName = (EditText) findViewById(R.id.edtName);
		etPass1 = (EditText) findViewById(R.id.edtPwd1);
		etPass2 = (EditText) findViewById(R.id.edtPwd2);
		etPhone = (EditText) findViewById(R.id.edtPhone);
		bnRegister = (Button) findViewById(R.id.btnRegister);
		bnCancel = (Button) findViewById(R.id.btnCancel);
		sexGroup = (RadioGroup) findViewById(R.id.sexGroup);
		eduGroup = (RadioGroup) findViewById(R.id.eduGroup);
		radioMale = (RadioButton) findViewById(R.id.radioMale);
		radioFemale = (RadioButton) findViewById(R.id.radioFemale);
		radioPrimary = (RadioButton) findViewById(R.id.radioPrimary);
		radioJunior = (RadioButton) findViewById(R.id.radioJunior);
		radioSenior = (RadioButton) findViewById(R.id.radioSenior);
		radioUniversity = (RadioButton) findViewById(R.id.radioUniversity);
		bnRegister.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(validate()){
					if(register()){
						Intent intent = new Intent(RegisterActivity.this,UserActivity.class);
						startActivity(intent);
					}
				}
			}
		});
		bnCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				finish();
			}
		});
		sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			 @Override  
			 public void onCheckedChanged(RadioGroup group, int checkedId) {  
				 if(checkedId == radioFemale.getId()){
					 sex = "2";
				 }
				 else{
					 sex = "1";
				 }
			 }
		});
		eduGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			 @Override  
			 public void onCheckedChanged(RadioGroup group, int checkedId) {  
				 if(checkedId == radioUniversity.getId()){
					 edu = "4";
				 }
				 else if(checkedId == radioSenior.getId()){
					 edu = "3";
				 }
				 else if(checkedId == radioJunior.getId()){
					 edu = "2";
				 }
				 else{
					 edu = "1";
				 }
			 }
		});
		
	}
	private boolean validate(){
		String username = etName.getText().toString().trim();
		String password1 = etPass1.getText().toString().trim();
		String password2 = etPass2.getText().toString().trim();
		String phone = etPhone.getText().toString().trim();
		if(username.equals("")||password1.equals("")||password2.equals("")||phone.equals("")){
			Toast.makeText(this, "用户名、密码和手机不能为空",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(!password1.equals(password2)){
			Toast.makeText(this, "两次密码需相同",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(sex.equals("0")){
			Toast.makeText(this, "请选择性别",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(edu.equals("0")){
			Toast.makeText(this, "请选择学历",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(username.length()<6||username.length()>18){
			Toast.makeText(this, "用户名需在6-18位之间",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(password1.length()<6||password1.length()>18){
			Toast.makeText(this, "密码需在需在6-18位之间",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(phone.length()!=11){
			Toast.makeText(this, "手机号为11位",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private boolean register(){
		String username = etName.getText().toString();
		String password1 = etPass1.getText().toString();
		String phone = etPhone.getText().toString();
		JSONObject jsonObj;
		try{
			jsonObj = query(username, password1, phone, sex, edu);
			if(jsonObj.getInt("userid")>0)
				return true;
			else
				Toast.makeText(this, "已有用户名或手机号，请重新输入",Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			DialogUtil.showDialog(this, "服务器异常", false);
			e.printStackTrace();
		}
		return false;
	}
	
	private JSONObject query(String username, String password, String phone, String sex, String edu) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		map.put("phone", phone);
		map.put("sex", sex);
		map.put("edu", edu);
		String url = HttpUtil.BASE_URL + "register.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
		

}
