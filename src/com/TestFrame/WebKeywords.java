package com.TestFrame;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WebKeywords {
	
	public Log log;
	private String msg = "";
	public WebDriver dr;
	private WebEle ele;
	private boolean autoScreenshot = false;

	public WebKeywords(Log log) {
		this.log = log;
	}
	
	public String getMsg() {
		return msg;
	}
	
//	public boolean act(String action, String[] params, Param p){
//		if(action.equals("web.ie")) {
//			return ie(params, p);
//		}else if(action.equals("web.chrome")) {
//			return chrome(params, p);
//		}else if(action.equals("web.get")) {
//			return get(params, p);
//		}else if(action.equals("web.click")) {
//			return click(params, p);
//		}else if(action.equals("web.input")) {
//			return input(params, p);
//		}else if(action.equals("web.text")) {
//			return text(params, p);
//		}else if(action.equals("web.getAttribute")) {
//			return getAttribute(params, p);
//		}else if(action.equals("web.selectByIndex")) {
//			return selectByIndex(params, p);
//		}else if(action.equals("web.selectByText")) {
//			return selectByText(params, p);
//		}else if(action.equals("web.selectByValue")) {
//			return selectByValue(params, p);
//		}else{
//			log.write("SEVERE", "No keyword of web collection matched!");
//			return false;
//		}
//	}
	public boolean autoScreenshot(String[] params, Param p) {
		if(params[0].equals("on")) {
			this.autoScreenshot = true;
			log.write("INFO", "Auto screenshot: ON");
			return true;
		}else if(params[0].equals("off")) {
			this.autoScreenshot = false;
			log.write("INFO", "Auto screenshot: OFF");
			return true;
		}else {
			log.write("SEVERE", "Wrong input!   Only  on/off acceptable!");
			return false;
		}
	}
	
	public void takeScreenshot() {
		File scrFile = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
		String path = "./screenshots/"+ft.format(new Date())+".jpg";
//		System.out.println(path);
		scrFile.renameTo(new File(path));
		log.write("INFO", "Take a screenshot: "+path);
	}
	
	public void quit() {
		try {
			this.dr.close();
			this.dr.quit();
			log.write("INFO", "Quit!");
		}catch(Exception e) {
			e.printStackTrace();
			log.write("SEVERE", e.toString());
		}
	}
	
	public boolean quit(String[] params, Param p) {
		try {
			this.dr.close();
			this.dr.quit();
			log.write("INFO", "Quit!");
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			log.write("SEVERE", e.toString());
			return false;
		}
	}
	
	public boolean ie(String[] params, Param p) {
		try {
			String driverPath = System.getenv("driver");
			if(driverPath.equals("")) {
				System.setProperty("webdriver.ie.driver", "./driver/win/IEDriverServer.exe");
			}else {
				System.setProperty("webdriver.ie.driver", driverPath+"\\IEDriverServer.exe");
			}		
			dr = new InternetExplorerDriver();
			ele = new WebEle(dr, log);
			dr.manage().window().maximize();
			if(!params[0].trim().equals("")) {
				dr.get(params[0]);
			}
			log.write("INFO", "Try to open ie: |"+params[0]+"|---Success!");
			if(!params[1].trim().equals("")) {
				dr.manage().timeouts().implicitlyWait(Long.valueOf(params[1]), TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(Long.valueOf(params[1]), TimeUnit.SECONDS);
				log.write("INFO", "Set default wait time : "+params[1]+" seconds!");
			}else {
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				log.write("INFO", "Set default wait time : 10 seconds!");
			}
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to open ie: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Try to open ie: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}	
	}
	
	public boolean chrome(String[] params, Param p) {
		try {
			String driverPath = System.getenv("driver");
			if(driverPath.equals("") || driverPath==null) {
				System.setProperty("webdriver.chorme.driver", "./driver/win/chromedriver.exe");
			}else {
				System.setProperty("webdriver.chorme.driver", driverPath+"\\chromedriver.exe");
			}	
			dr = new ChromeDriver();
			dr.manage().window().maximize();
			ele = new WebEle(dr, log);
			if(!params[0].trim().equals("")) {
				dr.get(params[0]);
			}
			log.write("INFO", "Try to open chrome: |"+params[0]+"|---Success!");
			if(!params[1].trim().equals("")) {
				dr.manage().timeouts().implicitlyWait(Long.valueOf(params[1]), TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(Long.valueOf(params[1]), TimeUnit.SECONDS);
				log.write("INFO", "Set default wait time : "+params[1]+" seconds!");
			}else {
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				log.write("INFO", "Set default wait time : 10 seconds!");
			}
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to open chrome: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Try to open chrome: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
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
			log.write("INFO", "Try to open : |"+url+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to open : |"+url+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to open : |"+url+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}		
	}

	public boolean click(String[] params, Param p) {
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
//			loc = params[0].split("="); // xpath中会含有多个"="
			loc[0] = params[0].trim().substring(0, params[0].trim().indexOf("="));
			loc[1] = params[0].trim().substring(params[0].trim().indexOf("=")+1, params[0].trim().length());
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			ele.get(loc[0], loc[1]).click();
			log.write("INFO", "Try to click : |"+params[2]+loc[1]+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to click : |"+params[2]+loc[1]+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to click : |"+params[2]+loc[1]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectByIndex(String[] params, Param p) {
		int index=Integer.valueOf(p.getParam(params[1]));
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			Select s = new Select(ele.get(loc[0], loc[1]));
			s.selectByIndex(index);
			log.write("INFO", "Try to select by index : |"+loc[1]+" : "+String.valueOf(index)+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to select by index : |"+loc[1]+" : "+String.valueOf(index)+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to select by index : |"+loc[1]+" : "+String.valueOf(index)+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectByText(String[] params, Param p) {
		String label=p.getParam(params[1]);
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			Select s = new Select(ele.get(loc[0], loc[1]));
			s.selectByVisibleText(label);
			log.write("INFO", "Try to select by text : |"+loc[1]+" : "+label+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to select by text : |"+loc[1]+" : "+label+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to select by text : |"+loc[1]+" : "+label+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectByValue(String[] params, Param p) {
		String value= p.getParam(params[1]);
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			Select s = new Select(ele.get(loc[0], loc[1]));
			s.selectByValue(value);
			log.write("INFO", "Try to select by value : |"+loc[1]+" : "+value+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to select by value : |"+loc[1]+" : "+value+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to select by value : |"+loc[1]+" : "+value+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean input(String[] params, Param p) {
		String str="";
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			str = p.getParam(params[2]);
			ele.get(loc[0], loc[1]).sendKeys(str);
			log.write("INFO", "Try to input : |"+str+" |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Try to input : |"+str+" |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Try to input : |"+str+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean text(String[] params, Param p) {
		String str="";
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			str = ele.get(loc[0], loc[1]).getText();
			p.setParam(params[1], str);
			log.write("INFO", "Get text of : |"+loc[1]+" : "+str+" |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Get text of : |"+loc[1]+" : "+str+" |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Get text of : |"+loc[1]+" : "+str+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}

	public boolean getAttribute(String[] params, Param p) {
		String str="";
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			str = ele.get(loc[0], loc[1]).getAttribute(params[1]);
			p.setParam(params[2], str);
			log.write("INFO", "Get text of : |"+loc[1]+" : "+str+" |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Get text of : |"+loc[1]+" : "+str+" |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch(Exception e) {
			log.write("SEVERE", "Get text of : |"+loc[1]+" : "+str+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean alertAccept(String[] params,Param p) {
		WebDriverWait wait = new WebDriverWait(dr, 10);		
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			log.write("INFO", dr.switchTo().alert().getText());
			dr.switchTo().alert().accept();
			log.write("INFO", "Accept alert |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Accept alert |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Accept alert |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}

	public boolean alertDismiss(String[] params,Param p) {
		WebDriverWait wait = new WebDriverWait(dr, 10);		
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			log.write("INFO", dr.switchTo().alert().getText());
			dr.switchTo().alert().dismiss();
			log.write("INFO", "Dismiss alert |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Dismiss alert |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Dismiss alert |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean alertSendkeys(String[] params,Param p) {
		WebDriverWait wait = new WebDriverWait(dr, 10);	
		String text = p.getParam(params[0]);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			log.write("INFO", dr.switchTo().alert().getText());
			dr.switchTo().alert().sendKeys(text);
			log.write("INFO", "Alert sendkeys: |"+text+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Alert sendkeys: |"+text+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Alert sendkeys: |"+text+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean alertText(String[] params,Param p) {
		WebDriverWait wait = new WebDriverWait(dr, 10);		
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			log.write("INFO", dr.switchTo().alert().getText());
			p.setParam(params[0], dr.switchTo().alert().getText());
			log.write("INFO", "Get alert text |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Get alert text |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Get alert text |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectFrame(String[] params,Param p) {
		String[] loc= {"",""};
		if(!params[0].trim().equals("") && params[0].indexOf("=")!=-1) {
			loc = params[0].split("=");
		}else {
			log.write("SEVERE", "Wrong params format : "+params[0]);
			return false;
		}
		try {
			dr.switchTo().frame(ele.get(loc[0], loc[1]));
			log.write("INFO", "Select frame: |"+params[0]+"|---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Select frame: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Select frame: |"+params[0]+"|---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectTopFrame(String[] params,Param p) {
		try {
			dr.switchTo().defaultContent();
			log.write("INFO", "Select default frame |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Select default frame |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Select default frame |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean selectWindow(String[] params,Param p) {
		String title = p.getParam(params[0]);
		try {
			Set<String> hds = dr.getWindowHandles();
			for(String hd : hds) {
				dr.switchTo().window(hd);
				if(dr.getTitle().trim().equals(title)) {
					break;
				}
			}
			log.write("INFO", "Select window with title: |"+title+" |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Select window with title: |"+title+" |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Select window with title: |"+title+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	public boolean excuteJavascript(String[] params,Param p) {
		JavascriptExecutor js = (JavascriptExecutor)dr;
		try {
			if(params[1].equals("")) {
				js.executeScript(params[0]);
			}else {
				String args = p.getParam(params[1]);
				js.executeScript(params[0], args);
			}
			log.write("INFO", "Execute Javascript: |"+params[0]+" |---Success!");
			return true;
		}
		catch (WebDriverException e) {
			log.write("SEVERE", "Execute Javascript: |"+params[0]+" |---Fail!");
			log.write("SEVERE", e.getMessage());
			if(autoScreenshot==true) {
				this.takeScreenshot();
			}
			quit();
			return false;
		}
		catch (Exception e) {
			log.write("SEVERE", "Execute Javascript: |"+params[0]+" |---Fail!");
			log.write("SEVERE", e.toString());
			e.printStackTrace();
			quit();
			return false;
		}
	}
	
	
	
	
	
	
	
}
