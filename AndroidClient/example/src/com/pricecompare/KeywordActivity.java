package com.pricecompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pricecompare.util.DialogUtil;
import com.pricecompare.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KeywordActivity extends Activity{
	EditText etName;
	Button bnSend;
	ListView listView;
	JSONArray arr, jsonA;
	ArrayAdapter<String> adapter;
	List<String> list;
	String Keyword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keywordlist);
		setTitle("�ؼ��ֹ���");
		list = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.KeywordList);
		try {
			jsonA = getKeyword();
			if(jsonA.getJSONObject(0).getInt("isNull")!=0){
				int x = jsonA.length();
				for(int i = 0;i < x;i++){
					list.add(jsonA.getJSONObject(i).getString("keyword"));
				}
				adapter =new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						Log.v("testKey", ""+parent.getId());
						new AlertDialog.Builder(KeywordActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  
					     .setMessage("��ȷ���Ƿ�ɾ�������ݣ�")//������ʾ������  
					     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
					         @Override  
					         public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
					             // TODO Auto-generated method stub  
					        	 try {
						        		Log.v("testK",Keyword);
										deleteKeyword(Keyword);
										Intent i = new Intent(KeywordActivity.this,KeywordActivity.class);
										startActivity(i);
										finish();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}  
					         }  
					     }).setNegativeButton("����",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
					         @Override  
					         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
					             // TODO Auto-generated method stub  
					             Log.i("alertdialog"," �뱣�����ݣ�"); 
					         }  
					     }).show();
					}
				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		etName = (EditText) findViewById(R.id.edtKeyword);
		bnSend = (Button) findViewById(R.id.buttonKeyword);
		bnSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(validate()){
					arr = setKeyword();
					Intent i = new Intent(KeywordActivity.this,KeywordActivity.class);
					startActivity(i);
					finish();
				}
			}
		});
	}
	@Override
	protected void onResume(){
		super.onResume();
		listView = (ListView) findViewById(R.id.KeywordList);
		try {
			jsonA = getKeyword();
			if(jsonA.length()!=0){
				List<String> list = new ArrayList<String>();
				list.clear();
				int x = jsonA.length();
				for(int i = 0;i < x;i++){
					list.add(jsonA.get(i).toString());
				}
				adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						Log.v("testKey", ""+parent.getId());
						Keyword = ((TextView)v).getText().toString();
						new AlertDialog.Builder(KeywordActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  
					     .setMessage("��ȷ���Ƿ�ɾ�������ݣ�")//������ʾ������  
					     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
					         @Override  
					         public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
					             // TODO Auto-generated method stub  
					        	 try {
					        		Log.v("testK",Keyword);
									deleteKeyword(Keyword);
									Intent i = new Intent(KeywordActivity.this,KeywordActivity.class);
									startActivity(i);
									finish();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					         }  
					     }).setNegativeButton("����",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
					         @Override  
					         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
					             // TODO Auto-generated method stub  
					             Log.i("alertdialog"," �뱣�����ݣ�"); 
					         }  
					     }).show();
					}
				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private boolean validate(){
		String keyword = etName.getText().toString().trim();
		if(keyword.equals("")){
			Toast.makeText(this, "�ؼ��ֲ���Ϊ��",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private JSONArray setKeyword(){
		String keyword = etName.getText().toString();
		;
		try{
			query(keyword);
		}
		catch(Exception e){
			DialogUtil.showDialog(this, "�������쳣", false);
			e.printStackTrace();
		}
		return null;
	}
	private void query(String keyword) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyword", keyword);
		String url = HttpUtil.BASE_URL + "setKeyword.action";
		HttpUtil.postRequest(url, map);
	}
	private JSONArray getKeyword() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyword", "keyword");
		String url = HttpUtil.BASE_URL + "getKeyword.action";
		return new JSONArray(HttpUtil.postRequest(url, map));
	}
	private void deleteKeyword(String str)throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyword", str);
		String url = HttpUtil.BASE_URL + "deleteKeyword.action";
		HttpUtil.postRequest(url, map);
	}
}
	
