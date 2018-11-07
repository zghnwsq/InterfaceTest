package com.TestFrame;

import java.io.IOException;

import org.apache.http.ParseException;

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
		}else if(action.equals("http.send")){
			return httpSend();
		}else {
			return false;
		}
		
	}
	
	public boolean httpPost(String[] params) {
		http = new Http();	
		http.url= params[0];
		log.write("INFO", "new Http Object set");
		log.write("INFO", "set post url: "+params[0]);
		return true;
	}
	
	public boolean httpSend() {
		try {
			log.write("INFO", "try to send request");
			String res = http.post("");
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
	
	
	public boolean soapPost(Soap soap, String action, String[] params) {
		
		
		
		
		return false;
	}

}
