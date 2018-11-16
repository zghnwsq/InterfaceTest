package com.TestFrame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class Keyword {
	
	public Http http;
	public Soap soap;
	public Log log;
	public static String res="";
	public String urlParams = "?";
	
	public Keyword(Log log) {
		this.log = log;
	}
	
	//关键字选择分支
	public boolean keyword(String action, String[] params, Param p) {
		if(action.indexOf("http") != -1){			
			return httpKeyword(action, params, p);
		}else if(action.indexOf("soap") != -1){
			return soapKeyword(action, params, p);
		}else if(action.indexOf("assert") != -1){
			return assertKeyword(action, params, p);
		}else{
			log.write("SEVERE", "no keyword matched!");
			return false;
		}	
	}
	
	public boolean httpKeyword(String action, String[] params, Param p){
		if(action.equals("http.set")) {
			return httpSet(params);
		}else if(action.equals("http.post")){
			return httpPost(params);
		}else if(action.equals("http.getToken")){
			return httpGetToken(params, p);
		}else if(action.equals("http.addToken")){
			return httpAddToken(params, p);
		}else if(action.equals("http.addUrlParam")){
			return httpAddUrlParam(params);
		}else if(action.equals("http.clearUrlParam")){
			return httpClearUrlParam();
		}else if(action.equals("http.getJsonValue")){
			return httpGetJsonValue(params, p);
		}else if(action.equals("http.addUrlEncodedParam")){
			return httpAddUrlEncodedParam(params);
		}else{
			log.write("SEVERE", "no keyword of http collection matched!");
			return false;
		}
	}
	
	public boolean soapKeyword(String action, String[] params, Param p){
		return false;
	}
	
	public boolean assertKeyword(String action, String[] params, Param p){
		if(action.equals("assertJson")){
			return assertJson(params, p);
		}else if(action.equals("assertResContain")){
			return assertResContain(params, p);
		}else{
			log.write("SEVERE", "no keyword of assert collection matched!");
			return false;
		}
	}
	
	
	public boolean httpSet(String params[]) {
		http = new Http();	
		if(!params[0].equals("")) {
			http.url= params[0];
		}
		log.write("INFO", "new Http Object set");
		log.write("INFO", "set post url: "+params[0]);
		return true;
	}
	
	public boolean httpPost(String params[]) {
		if(!params[0].equals("")){
			http.url = params[0];
		}
		if(!urlParams.equals("?")){
			http.url = http.url+urlParams;
		}
		try {
			log.write("INFO", "try to send request");
			res = http.post("");
			log.write("INFO", "response:-->|"+res+"|<---");
		} catch (Exception e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	
	public boolean httpGetToken(String params[], Param p){
		log.write("INFO", "try to get token");		
		try{
			JSONObject json = new JSONObject(res);
			p.setParam(params[0], json.getString("token"));
			log.write("INFO", "token: --->|"+json.getString("token")+"|<---");
		}catch(JSONException e){
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
	public boolean httpAddToken(String params[], Param p){
		log.write("INFO", "try to add token");
		String tk="";
		try {
			tk = p.getParam(params[0]);
			http.addHead("token", tk);
		} catch (Exception e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		log.write("INFO", "token added: --->|"+tk+"|<---");
		return true;
	}
	
	public boolean httpAddUrlParam(String params[]){
		if(urlParams.equals("?")){
			urlParams = urlParams+params[0]+"="+params[1];
		}else{
			
			urlParams = urlParams+"&"+params[0]+"="+params[1];
		}
		log.write("INFO", "urlParams set to:--->|"+urlParams+"|<---");
		return true;
	}
	
	public boolean httpAddUrlEncodedParam(String params[]){
		try {
			params[0] = URLEncoder.encode(params[0], "UTF-8");
			params[1] = URLEncoder.encode(params[1], "UTF-8");
			if(urlParams.equals("?")){
				urlParams = urlParams+params[0]+"="+params[1];
			}else{
				
				urlParams = urlParams+"&"+params[0]+"="+params[1];
			}
			log.write("INFO", "urlParams set to:--->|"+urlParams+"|<---");
			return true;
		} catch (UnsupportedEncodingException e) {
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean httpClearUrlParam(){
		urlParams = "?";
		log.write("INFO", "urlParams cleared");
		return true;
	}
	
	public boolean httpGetJsonValue(String[] params, Param p){
		log.write("INFO", "try to get JSON value by key:"+"--->|"+params[0]+"|<---");		
		try{
			JSONObject json = new JSONObject(res);
			p.setParam(params[1], json.getString(params[0]).trim());
			log.write("INFO", "set key "+params[0]+" value: --->|"+json.getString(params[0])+"|<---");
		}catch(JSONException e){
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
	
	
	
	
	public boolean soapPost(Soap soap, String action, String[] params) {
		
		
		
		
		return false;
	}

	//以下是断言方法
	public boolean assertJson(String params[], Param p){
		String value = "";
		JSONObject json;
		String expect = p.getParam(params[1]);
		log.write("INFO", "assertJson: --->|"+params[0]+" = "+expect+"|<---");
		try{
			json = new JSONObject(res);
			value = String.valueOf(json.get(params[0]).toString());
		}catch(Exception e){		
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		if(value.indexOf(expect) != -1){
			log.write("INFO", "status: --->|"+value+"|<--- PASS");
			return true;
		}else{
			log.write("SEVERE", "status: --->|"+value+"|<--- FAIL");
			return false;
		}	
	}
	
	public boolean assertResContain(String params[], Param p){
		String expect = p.getParam(params[0]);
		log.write("INFO", "assertResContain: --->|"+expect+"|<---");
		if(res.indexOf(expect) != -1){
			log.write("INFO", "响应包含字符串：--->|"+expect+"|<---PASS");
			return true;
		}else{
			log.write("SEVERE", "响应不包含字符串：--->|"+expect+"<---FAIL");
			return false;
		}
	}
	
}
