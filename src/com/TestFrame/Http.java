package com.TestFrame;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Http {
	
	public CloseableHttpClient httpClient;
	public HttpResponse response;
	public String key="";
	public String value="";
//构造函数
	public Http(){
		httpClient = HttpClientBuilder.create().build();
	}
	
//添加head方法
	public void addHead(String key, String value){
		this.key = key;
		this.value = value;		
	}
	
// post接口方法	
	public String post(String url) throws ParseException, IOException{
		HttpPost httpPost = new HttpPost(url);
		if (!key.equals("")){
			httpPost.addHeader(key,value);
		}
		//发包
		response = httpClient.execute(httpPost);		
		//获取回复内容中，response里面的对象
		HttpEntity responseEntity = response.getEntity();
//		System.out.println(EntityUtils.toString(responseEntity));
		return EntityUtils.toString(responseEntity);
	}

}
