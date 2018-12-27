package com.TestFrame;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public class InterfaceKeywords {
	
	public Http http;
//	private Soap soap;
	public Log log;
	public String res="";
	public String urlParams = "?";
	
	public InterfaceKeywords(Log log) {
		this.log = log;
	}
	
	public String getRes() {
		return res;
	}
	
	public boolean httpKeyword(String action, String[] params, Param p){
		if(action.equals("http.set")) {
			return httpSet(params);
		}else if(action.equals("http.post")){
			return httpPost(params);
		}else if(action.equals("http.get")){
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
		}else if(action.equals("http.addHeader")){
			return httpAddHeader(params, p);
		}else if(action.equals("http.removeHeader")){
			return httpRemoveHeader(params);
		}else if(action.equals("http.addBody")){
			return httpAddBody(params);
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
	
	public boolean httpGet(String params[]) {
		res = ""; //发送前重置响应
		if(!params[0].trim().equals("")){
			http.url = params[0];
		}
		if(!urlParams.equals("?")){
			http.url = http.url+urlParams;
		}
		try {
			res = http.get("");
			log.write("INFO", "try to Get: "+http.url+"|---Success!");
			log.write("INFO", "response:-->|"+res+"|<---");
			httpClearUrlParam(); //发送请求后,清空urlParam
		}catch (Exception e) {
			httpClearUrlParam(); //发送请求后,清空urlParam
			log.write("SEVERE", "try to Get: "+http.url+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	
	public boolean httpPost(String params[]) {
		res = ""; //发送前重置响应
		if(!params[0].trim().equals("")){
			http.url = params[0];
		}
		if(!urlParams.equals("?")){
			http.url = http.url+urlParams;
		}
		try {
			res = http.post("");
			log.write("INFO", "try to Post: "+http.url+"|---Success!");
			log.write("INFO", "response:-->|"+res+"|<---");
			httpClearUrlParam(); //发送请求后,清空urlParam
			http.body = ""; //发送后重置body
		} catch (Exception e) {
			httpClearUrlParam(); //发送请求后,清空urlParam
			http.body = ""; //发送后重置body
			log.write("SEVERE", "try to Post: "+http.url+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	
	public boolean httpGetToken(String params[], Param p){		
		try{
			JSONObject json = new JSONObject(res);
			p.setParam(params[0], json.getString("token"));
			log.write("INFO", "Get token |---Success!");
			log.write("INFO", "token: --->|"+json.getString("token")+"|<---");
		}catch(JSONException e){
			log.write("SEVERE", "Get token |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
	public boolean httpAddToken(String params[], Param p){
		String tk="";
		try {
			tk = p.getParam(params[0]);
			http.addHead("token", tk);
			log.write("INFO", "try to add token |---Success!");
			log.write("INFO", "token added: --->|"+tk+"|<---");
		} catch (Exception e) {
			log.write("SEVERE", "try to add token |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean httpAddHeader(String params[], Param p){
		String value="";
		try {
			value = p.getParam(params[1]);
			http.addHead(params[0], value);
			log.write("INFO", "try to add header |---Success!");
			log.write("INFO", "header added: --->|"+params[0]+":"+value+"|<---");
		} catch (Exception e) {
			log.write("SEVERE", "try to add header |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean httpRemoveHeader(String params[]) {
		try {
			http.removeHead(params[0]);
			log.write("INFO", "try to remove header |---Success!");
			log.write("INFO", "header removed: --->|"+params[0]+"|<---");
		}catch (Exception e) {
			log.write("SEVERE", "try to remove header |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
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
		try{
			JSONObject json = new JSONObject(res);
			p.setParam(params[1], json.getString(params[0]).trim());
			log.write("INFO", "try to get JSON value by key:"+"--->|"+params[0]+"|<---Success!");	
			log.write("INFO", "set key "+params[0]+" value: --->|"+json.getString(params[0])+"|<---");
		}catch(JSONException e){
			log.write("SEVERE", "try to get JSON value by key:"+"--->|"+params[0]+"|<---Fail!");	
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
	public boolean httpAddBody(String[] params) {
		try {
			http.setBody(params[0]);
			log.write("INFO", "try to add Body :"+"--->|"+params[0]+"|<---Success!");
		}catch(Exception e) {
			log.write("SEVERE", "try to add Body :"+"--->|"+params[0]+"|<---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean soapPost(Soap soap, String action, String[] params) {
		//未实现
		return false;
	}

	//以下是断言方法
	public boolean assertJson(String params[], Param p){
		String value = "";
		JSONObject json;
		String expect = p.getParam(params[1].toString());
		try{
			json = new JSONObject(res);
			value = String.valueOf(json.get(params[0]).toString());
		}catch(Exception e){		
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		if(value.indexOf(expect) != -1){
			log.write("INFO", "assertJson contain: --->|"+params[0]+" = "+expect+"|<---PASS");
			return true;
		}else{
			log.write("SEVERE", "assertJson contain: --->|"+params[0]+" = "+expect+"|<---FAIL");
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
