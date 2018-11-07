package com.test;

import java.io.IOException;
import org.apache.http.ParseException;
import org.json.JSONObject;

import com.TestFrame.Http;

public class TestA {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		Http http = new Http();
		http.url = "http://www.testingedu.com.cn/inter/HTTP/auth";
		//获取token
		String res = http.post("");
		//JSON格式响应
		JSONObject json = new JSONObject(res);
		System.out.println(json);
		System.out.println("get token:"+json.getString("token"));	
		//添加token到head
		http.addHead("token", json.getString("token"));
		//登录
		http.url = "http://www.testingedu.com.cn/inter/HTTP//login?username=will&password=123456";
		res = http.post("");
		System.out.println(res);
//		http.addHead("token", "02f469785ff84c12979b5be372c1f75e");
		//获取用户信息
		http.url = "http://www.testingedu.com.cn/inter/HTTP//getUserInfo?id=1";
		res = http.post("");
		System.out.println(res);		
		//登出
		http.url = "http://www.testingedu.com.cn/inter/HTTP//logout";
		res = http.post("");
		System.out.println(res);
	}

}
