package com.TestFrame;

import java.util.List;

public class Report {
	
	public List<String[]> suit;
	
 	public Report(List<String[]> suit){
		this.suit = suit;
	}
 	
 	public void buildReport(){
 		String suitResult = ""; //整个用例集结果
 		int pass = 0; //通过用例数
 		int fail = 0; //失败用例数
 		int noresult = 0; //没有结果
 		String suitBegTime = ""; //用例集开始时间
 		String suitEndTime = ""; //用例集结束时间
		for(String[] i : suit){
			if(i[5].equals("PASS") && suitResult.equals("PASS")){
				suitResult = "PASS";
				pass++ ;
			}else if(i[5].equals("")){
				noresult++;
			}else{
				suitResult = "FAIL";
				fail++;
			}
			if(suitBegTime.equals("")){
				suitBegTime = i[3];
			}
			suitEndTime = i[4];	
		}
		
		
 	}

}
