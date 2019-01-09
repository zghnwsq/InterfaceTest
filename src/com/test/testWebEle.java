package com.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.TestFrame.Log;
import com.TestFrame.Param;
import com.TestFrame.WebEle;
import com.TestFrame.WebKeywords;


public class testWebEle {

	public static void main(String[] args) throws SecurityException, IOException {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.ie.driver", "D:\\Driver\\IEDriverServer.exe");
//		WebDriver dr = new InternetExplorerDriver();
//		WebDriver dr = new ChromeDriver();
		Log log = new Log("111", "INFO");
//		WebEle ele = new WebEle(dr, log);
//		dr.get("https://www.baidu.com");
//		dr.manage().window().maximize();
//		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		dr.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//		ele.get("id", "kw", "").sendKeys("aaaa");
//		dr.close();
//		dr.quit();
		
//		AbstractWebDriverEventListener
		
		WebKeywords wk = new WebKeywords(log);
		Param p = new Param();
		p.setParam("key", "hahahaha");
		wk.ie(new String[]{"http://www.baidu.com","",""}, p);
		wk.getAttribute(new String[]{"id=su","value","s"},p); 
		System.out.println(p.getParam("${s}"));
		wk.input(new String[]{"id=kw","","${key}"}, p); 
		wk.click(new String[]{"id=su","","搜索"}, p); 
//		wk.alertAccept(new String[]{"","",""}, p);
//		wk.dr.close();
//		wk.dr.quit();
	}

}
