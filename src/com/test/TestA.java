package com.test;

import java.io.IOException;
import org.apache.http.ParseException;
import org.json.JSONObject;

import com.TestFrame.Http;

public class TestA {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		Http http = new Http();
		String res;
		http.url = "http://www.testingedu.com.cn/inter/HTTP/auth";
		//获取token
//		res = http.post("");
//		//JSON格式响应
//		JSONObject json = new JSONObject(res);
//		System.out.println(json);
//		System.out.println("get token:"+json.getString("token"));	
//		System.out.println("get token:"+json.getInt("status"));
//		//添加token到head
//		http.addHead("token", json.getString("token"));
		
		//登录
//		http.url = "http://www.testingedu.com.cn/inter/HTTP//login?password=123456"; //username=ted1&
//		res = http.post("");
//		System.out.println(res);
		http.addHead("token", "411c76ababa24b79b13ead07ab96c76a");
		//获取用户信息
//		http.url = "http://www.testingedu.com.cn/inter/HTTP//getUserInfo?id=28";
//		res = http.post("");
//		System.out.println(res);		
		//登出
		http.url = "http://www.testingedu.com.cn/inter/HTTP//logout";
		res = http.post("");
		System.out.println(res);
	}

}
