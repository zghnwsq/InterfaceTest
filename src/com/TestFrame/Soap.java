package com.TestFrame;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/***
 * @author ted
 */

public class Soap {

	
	public CloseableHttpClient httpClient;
	public HttpResponse response;
	public String body="";
	public String key="";
	public String value="";
	public List<String[]> head=null;
	
	/***
	 * @param CloseableHttpClient httpClient http客户端
	 * @param HttpResponse response 响应
	 * @param String body 字符串形式的xml请求主体
	 * @param String key 单个请求头键名
	 * @param String value 单个请求头值
	 * @param List<String[]> head 多个请求头键值对，以字符串数组集合方式存储
	 */
	
	public Soap(){
		httpClient = HttpClientBuilder.create().build();
	}
	
	/***
	 * 
	 * @param key 单个请求头键名
	 * @param value 单个请求头值
	 */
	public void addHead(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	/***
	 * 
	 * @param head 多个请求头键值对，以字符串数组集合方式存储
	 */
	public void addHead(ArrayList<String[]> head){
		this.head = head;
	}
	
	/***
	 * 
	 * @param body字符串形式的xml请求主体
	 */
	public void addBody(String body){
		this.body = body;
	}
	
	/***
	 * 
	 * @param url 请求url，带wsdl的
	 * @return 返回响应的字符串形式
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String post(String url) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		//单个header键值对添加
		if(!key.equals("")){
			httpPost.addHeader(key, value);//同名则追加
		}
		//多个header键值对添加
		if(head!=null){
			for(String[] i:head){
				httpPost.addHeader(i[0], i[1]);
			}
		}
		httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");//同名则覆盖
//		httpPost.setHeader("SOAPAction", soapAction);
		StringEntity data = new StringEntity(body,Charset.forName("UTF-8"));
		httpPost.setEntity(data);		
		response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);		
	}
	
	/***
	 * 
	 * @param response 响应的字符串形式
	 * @return 返回result标签内的所有字符串
	 */
	public String getResult(String response){
		return response.substring(response.indexOf("<return>")+8, response.indexOf("</return>"));	
	}
	
}
