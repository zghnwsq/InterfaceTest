package com.TestFrame;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.NoSuchElementException;

public class WebKeywords {
	
	public Log log;
	private String msg = "";
	public WebDriver dr;
	private WebEle ele;

	public WebKeywords(Log log) {
		this.log = log;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public boolean act(String action, String[] params, Param p){
		if(action.equals("web.ie")) {
			return ie(params);
		}else if(action.equals("web.chrome")) {
			return chrome(params);
		}else if(action.equals("web.get")) {
			return get(params, p);
		}else if(action.equals("web.click")) {
			return click(params);
		}else if(action.equals("input")) {
			return input(params, p);
		}else{
			log.write("SEVERE", "no keyword of web collection matched!");
			return false;
		}
	}
	
	public boolean ie(String[] params) {
		try {
			System.setProperty("webdriver.ie.driver", "./driver/win/IEDriverServer.exe");
			dr = new InternetExplorerDriver();
			ele = new WebEle(dr, log);
			dr.manage().window().maximize();
			if(!params[0].trim().equals("")) {
				dr.get(params[0]);
			}
			log.write("INFO", "try to open ie: |"+params[0]+"|---Success!");
			if(!params[1].trim().equals("")) {
				dr.manage().timeouts().implicitlyWait(Long.valueOf(params[1]), TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(Long.valueOf(params[1]), TimeUnit.SECONDS);
				log.write("INFO", "set default wait time : "+params[1]+" seconds!");
			}else {
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				log.write("INFO", "set default wait time : 10 seconds!");
			}
			return true;
		}catch (Exception e) {
			log.write("SEVERE", "try to open ie: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean chrome(String[] params) {
		try {
			System.setProperty("webdriver.chorme.driver", "./driver/win/chromedriver.exe");
			dr = new ChromeDriver();
			dr.manage().window().maximize();
			ele = new WebEle(dr, log);
			if(!params[0].trim().equals("")) {
				dr.get(params[0]);
			}
			log.write("INFO", "try to open chrome: |"+params[0]+"|---Success!");
			if(!params[1].trim().equals("")) {
				dr.manage().timeouts().implicitlyWait(Long.valueOf(params[1]), TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(Long.valueOf(params[1]), TimeUnit.SECONDS);
				log.write("INFO", "set default wait time : "+params[1]+" seconds!");
			}else {
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				log.write("INFO", "set default wait time : 10 seconds!");
			}
			return true;
		}catch (Exception e) {
			log.write("SEVERE", "try to open chrome: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean get(String[] params, Param p) {
		String url="";
		try {
			if(!params[0].trim().equals("")) {
				url = p.getParam(params[0]);
				dr.get(url);
			}
			log.write("INFO", "try to open : |"+url+"|---Success!");
			return true;
		}catch(Exception e) {
			log.write("SEVERE", "try to open : |"+url+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean click(String[] params) {
		try {
			ele.get(params[0], params[1]).click();
			log.write("INFO", "try to click : |"+params[2]+"|---Success!");
			return true;
		}
		catch(NoSuchElementException e) {
			log.write("SEVERE", "no such element : |"+params[2]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "try to click : |"+params[2]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean input(String[] params, Param p) {
		String str="";
		try {
			str = p.getParam(params[2]);
			ele.get(params[0], params[1]).sendKeys(str);
			log.write("INFO", "try to input : |"+str+" |---Success!");
			return true;
		}
		catch(NoSuchElementException e) {
			log.write("SEVERE", "no such element : "+params[0]+" : "+params[1]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "try to input : |"+str+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
}
