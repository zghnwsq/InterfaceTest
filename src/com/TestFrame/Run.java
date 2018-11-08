package com.TestFrame;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Run {
	
	public String initTime;
	public String endTime;
	public String result;

	public static List<String[]> runTestSuit(String excel, String sheetName) throws SecurityException, IOException{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
		SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		Date suitBegTime = new Date();
		Log log = new Log(ft.format(suitBegTime), "INFO");
		Keyword k = new Keyword(log);
		TestSuit ts = new TestSuit(excel, sheetName);
		Param p = new Param();
		List<String[]> suit = ts.getTestSuit();
		//		 suit.get(0)[3]="";
		List<List<String>> cases = ts.getTestCaseColletion();
		for(String[] c : suit){
			c[3] = ft2.format(new Date()); //写入用例开始执行时间
			boolean result = true;						
			for(int i = Integer.valueOf(c[1]); i<=Integer.valueOf(c[2]) ;i++ ){
				String action = cases.get(Integer.valueOf(i)).get(1); //action
				//如果p1单元格为空,则填空字符串
				String p1="";
				if(cases.get(Integer.valueOf(i)).size() > 2){
					p1 = cases.get(Integer.valueOf(i)).get(2); //p1
					p1 = p.getParam(p1);
				}
				//如果p2单元格为空,则填空字符串
				String p2 = "";
				if(cases.get(Integer.valueOf(i)).size() > 3){
					p2 = cases.get(Integer.valueOf(i)).get(3); //p2
					p2 = p.getParam(p2);
				}
				//如果p3单元格为空,则填空字符串
				String p3 = "";
				if(cases.get(Integer.valueOf(i)).size() > 4){
					p3 = cases.get(Integer.valueOf(i)).get(4); //p3
					p3 = p.getParam(p3);
				}
				String params[] = {p1, p2, p3};
				result = k.keyword(action, params, p) && result;
			}
			c[4] = ft2.format(new Date()); //写入用例结束时间
			if(result){
				c[5] = "PASS";
			}else{
				c[5] = "FAIL";
			}
		}
		return suit; 
	}

}
