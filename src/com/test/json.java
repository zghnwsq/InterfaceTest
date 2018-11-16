package com.test;

import java.awt.Window.Type;

import org.json.JSONObject;

public class json {

	public static void main(String[] args) {
		String j = "{\"result\":\"0\","
				+ "\"data\":{"
				+ "\"state\":\"4\","
				+ "\"login\":\"1111\"},"
				+ "\"msg\":\"sss\"}";
		JSONObject json = new JSONObject(j);
		System.out.println(json.getJSONObject("data").get("state"));
		System.out.println(json.get("data").toString());
		System.out.println(new JSONObject(json.get("data").toString()).get("state"));
	}

}
