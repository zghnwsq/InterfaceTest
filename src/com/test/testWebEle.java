package com.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.TestFrame.Log;
import com.TestFrame.WebEle;

public class testWebEle {

	public static void main(String[] args) throws SecurityException, IOException {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.ie.driver", "D:\\Driver\\IEDriverServer.exe");
//		WebDriver dr = new InternetExplorerDriver();
		WebDriver dr = new ChromeDriver();
		Log log = new Log("111", "INFO");
		WebEle ele = new WebEle(dr, log);
		dr.get("https://www.baidu.com");
		dr.manage().window().maximize();
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dr.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		ele.get("id", "kw", "").sendKeys("aaaa");
		dr.close();
		dr.quit();
	}

}
