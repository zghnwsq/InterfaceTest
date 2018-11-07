package com.TestFrame;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Run {
	
	public String initTime;
	public String endTime;
	public String result;

	public static void runTestSuit(String excel, String sheetName) throws SecurityException, IOException{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		Date suitBegTime = new Date();
		Log log = new Log(ft.format(suitBegTime), "INFO");
		TestSuit ts = new TestSuit(excel, sheetName);
		List<String[]> suit = ts.getTestSuit();
		//		 suit.get(0)[3]="";
		List<List<String>> cases = ts.getTestCaseColletion();
		 
	}

}
