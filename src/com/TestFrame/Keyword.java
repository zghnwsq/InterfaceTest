package com.TestFrame;

import java.io.IOException;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class Keyword {
	
	public Http http;
	public Soap soap;
	public Log log;
	public static String res="";
	
	public Keyword(Log log) {
		this.log = log;
	}
	
	//关键字选择分支
	public boolean keyword(String action, String[] params, Param p) {
		if(action.equals("http.post")) {
			return httpPost(params);
		}else if(action.equals("http.send")){
			return httpSend();
		}else if(action.equals("http.getToken")){
			return httpGetToken(params, p);
		}else if(action.equals("http.addToken")){
			return httpAddToken(params, p);
		}else if(action.equals("assertJson")){
			return assertJson(params);
		}else if(action.equals("assertResContain")){
			return assertResContain(params);
		}else {
			return false;
		}	
	}
	
	public boolean httpPost(String params[]) {
		http = new Http();	
		http.url= params[0];
		log.write("INFO", "new Http Object set");
		log.write("INFO", "set post url: "+params[0]);
		return true;
	}
	
	public boolean httpSend() {
		try {
			log.write("INFO", "try to send request");
			res = http.post("");
			log.write("INFO", "response:-->|"+res+"|<---");
		} catch (ParseException e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean httpGetToken(String param[], Param p){
		log.write("INFO", "try to get token");
		JSONObject json = new JSONObject(res);
		try{
			p.setParam(param[0], json.getString("token"));
			log.write("INFO", "token: --->|"+json.getString("token")+"|<---");
		}catch(JSONException e){
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
	public boolean httpAddToken(String param[], Param p){
		log.write("INFO", "try to add token");
		String tk="";
		try {
			tk = p.getParam(param[0]);
			http.addHead("token", tk);
		} catch (Exception e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		log.write("INFO", "token added: --->|"+tk+"|<---");
		return true;
	}
	
	public boolean soapPost(Soap soap, String action, String[] params) {
		
		
		
		
		return false;
	}

	//以下是断言方法
	public boolean assertJson(String param[]){
		String value = "";
		JSONObject json;
		log.write("INFO", "assertJson: --->|"+param[0]+" = "+param[1]+"|<---");
		try{
			json = new JSONObject(res);
			value = String.valueOf(json.getInt((param[0])));
		}catch(Exception e){		
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		if(value.indexOf(param[1]) != -1){
			log.write("INFO", "status: --->|"+value+"|<--- PASS");
			return true;
		}else{
			log.write("SEVERE", "status: --->|"+value+"|<--- FAIL");
			return false;
		}	
	}
	
	public boolean assertResContain(String param[]){
		log.write("INFO", "assertResContain: --->|"+param[0]+"|<---");
		if(res.indexOf(param[0]) != -1){
			log.write("INFO", "响应包含字符串："+param[0]+"-PASS");
			return true;
		}else{
			log.write("SEVERE", "响应不包含字符串："+param[0]+"-FAIL");
			return false;
		}
	}
	
}
