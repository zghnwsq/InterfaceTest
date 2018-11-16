package com.test;

import java.io.IOException;
import java.util.List;
import com.TestFrame.Excel;
import com.TestFrame.Report;
import com.TestFrame.Run;
import com.TestFrame.TestSuit;

public class testExcel {

	public static void main(String[] args) throws SecurityException, IOException {
//		System.out.println(System.getProperty("user.dir")); //当前用户路径
//		List<List<String>> res = Excel.readExcel("./testCase/Case2.xlsx", "testCase");
//		for(List<String> i:res) {
//			System.out.println(i.get(0).equals(""));
//			for(String j:i) {
//				System.out.print("|"+j+"|---");
//			}
//			System.out.println(i.size());
//			System.out.println("");
//		}
		
		TestSuit s = new TestSuit("./testCase/Case2.xlsx", "testCase");
		List<String[]> suit;
		suit = s.getTestSuit();
		for(String[] i: suit) {
			for(String j :i) {
				System.out.print("|"+j+"|---");
			}
			System.out.println("");
		}
//		Report r =  new Report(suit);
//		r.buildReport();
		
		
		
		//
//		List<String[]> s = Run.runTestSuit("./testCase/Case1.xlsx", "testCase");
//		for(String[] i : s){
//			for(String j : i){
//				System.out.println(j);
//			}
//		}
		
//		String a = "dfdfdfdfdf %name% ffjfjjfjff";
//		//a.replaceFirst("\\%name\\%", "<td></td>");
//		String b = a.replace("%name%", "<td></<td>");
//		System.out.println(b);
//		String src = "33333333444444444";
//		System.out.println(src.replace("33","f"));
//	

	}

}
