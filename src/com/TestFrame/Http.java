package com.TestFrame;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Http {
	
	public CloseableHttpClient httpClient;
	public HttpResponse response;
	public String key="";
	public String value="";
	public String url="";
	public List<String[]> head=null;
	public String body="";

	//构造函数
	public Http(){
		httpClient = HttpClientBuilder.create().build();
	}
	
	//添加head方法
	public void addHead(String key, String value){
		this.key = key;
		this.value = value;		
	}
	
	/***
	 * 
	 * @param head 多个请求头键值对，以字符串数组集合方式存储
	 */
	public void addHead(ArrayList<String[]> head){
		this.head.addAll(head);
	}
	
	//去掉头
	public void removeHead(String hd) {
		if(key.equals(hd)) {
			value = "";
		}
		if(head!=null) {
			for(String[] h: head) {
				if(h[0].equals(hd)) {
					String [] rr = new String[] {h[0],h[1]};
					head.remove(rr);
				}
			}
		}
	}
	
	public void setBody(String body) {
		if(!body.equals("")) {
			this.body = body;
		}
	}
	
	
	//建立新连接 抛弃旧连接
	public void newClient() throws IOException {
		httpClient.close();
		httpClient = HttpClientBuilder.create().build();
	}
	
	//get接口方法
	public String get(String url) throws ClientProtocolException, IOException {
		if(!url.equals("")) {
			this.url = url;
		}
		HttpGet httpGet = new HttpGet();
		//单个header添加
		if (!key.equals("")){
			httpGet.addHeader(key,value);
		}
		//多个header键值对添加
		if(head!=null){
			for(String[] i:head){
				httpGet.addHeader(i[0], i[1]);
			}
		}
		//发包
		response = httpClient.execute(httpGet);		
		//获取回复内容中，response里面的对象
		HttpEntity responseEntity = response.getEntity();
		return EntityUtils.toString(responseEntity);
	}
	
	// post接口方法	
	public String post(String url) throws ParseException, IOException{
		if(!url.equals("")) {
			this.url = url;
		}
		HttpPost httpPost = new HttpPost(this.url);
		//单个header添加
		if (!key.equals("")){
			httpPost.addHeader(key,value);
		}
		//多个header键值对添加
		if(head!=null){
			for(String[] i:head){
				httpPost.addHeader(i[0], i[1]);
			}
		}
		//body添加
		if(!body.equals("") || body != null) {
			StringEntity data = new StringEntity(body,Charset.forName("UTF-8"));
			httpPost.setEntity(data);
		}
		//发包
		response = httpClient.execute(httpPost);		
		//获取回复内容中，response里面的对象
		HttpEntity responseEntity = response.getEntity();
//		System.out.println(EntityUtils.toString(responseEntity));
		return EntityUtils.toString(responseEntity);
	}

}
