package com.test;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.ParseException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;

import com.TestFrame.Http;

public class TestA {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		Http http = new Http();
		String res;
		http.url = "http://www.testingedu.com.cn/inter/HTTP/auth";
		//获取token
		res = http.post("");
		//JSON格式响应
		JSONObject json = new JSONObject(res);
		System.out.println(json);
		System.out.println("get token:"+json.getString("token"));	
		System.out.println("get token:"+json.getInt("status"));
		//添加token到head
		http.addHead("token", json.getString("token"));
		
		//登录
//		http.url = "http://www.testingedu.com.cn/inter/HTTP//login?username=ted1&password=123456"; //username=ted1&
//		res = http.post("");
//		System.out.println(res);

		//获取用户信息
//		http.url = "http://www.testingedu.com.cn/inter/HTTP//getUserInfo?id=1";
//		res = http.post("");
//		System.out.println(res);	
		
//		http.addHead("token", "4b8896bb9fd54962829432777b98c873");
		//登出
//		http.url = "http://www.testingedu.com.cn/inter/HTTP//logout";
//		res = http.post("");
//		System.out.println(res);
		
		//彩云天气
//		http.url = "https://api.caiyunapp.com/v2/aJkb6gTZrkEqnAQh/121.6544,25.1552/realtime";
//		res = http.post("");
//		System.out.println(res);
		
		//测试url enccode		
//		res = http.post("http://www.testingedu.com.cn/inter/HTTP//register?username=ted123&pwd=123456&nickname=ted123&describe=哈哈哈");
//		System.out.println(res);
		String url = "?username=tedted&password=&nickname=你好&des=";
		String a = URLEncoder.encode(url, "UTF-8");
		System.out.println(a);
		
	}

}
