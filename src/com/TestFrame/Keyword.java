package com.TestFrame;

public class Keyword {
	
	public Http http;
	public Soap soap;
	public Log log;
	
	public Keyword(Log log) {
		this.log = log;
	}
	
	//
	public boolean keyword(String action, String[] params) {
		if(action.equals("http.post")) {
			return httpPost(params);
		}else {
			return false;
		}
		
	}
	
	public boolean httpPost(String[] params) {
		http = new Http();	
		http.url= params[0];
		log.write("info", "new Http Object set");
		log.write("info", "set post url:"+params[1]);
		return true;
	}
	
	public boolean soapPost(Soap soap, String action, String[] params) {
		
		
		
		
		return false;
	}

}
