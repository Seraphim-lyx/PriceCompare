package com.pricecompare.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	public static HttpClient httpClient = new DefaultHttpClient();
//	public static final String BASE_URL = "http://172.16.27.95:8080/PriceCompare/";
//	public static final String BASE_URL = "http://192.168.1.8:8080/PriceCompare/";
	public static final String BASE_URL = "http://10.0.0.6:8080/PriceCompare/";
	private static String JSESSIONID;
	/**
	 * 
	 * @param url 发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(final String url) throws Exception{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>(){
					@Override
					public String call() throws Exception{
						HttpGet get = new HttpGet(url);
						HttpResponse httpResponse = httpClient.execute(get);
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							String result = EntityUtils.toString(httpResponse.getEntity());
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
	/**
	 * @param url 发送请求的URL
	 * @param params 请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(final String url, final Map<String, String> rawParams) throws Exception{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>(){
					@Override
					public String call() throws Exception{
						HttpPost post = new HttpPost(url);
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for(String key : rawParams.keySet()){
							params.add(new BasicNameValuePair(key, rawParams.get(key)));
						}
						post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
						HttpResponse httpResponse = httpClient.execute(post);
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							if(httpResponse.getEntity() != null){
								String result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
								return result;
							}
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}
