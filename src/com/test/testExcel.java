package com.test;

import java.io.IOException;
import java.util.List;
import com.TestFrame.Excel;
import com.TestFrame.Run;
import com.TestFrame.TestSuit;

public class testExcel {

	public static void main(String[] args) throws SecurityException, IOException {
//		System.out.println(System.getProperty("user.dir")); //当前用户路径
//		List<List<String>> res = Excel.readExcel("./mail/config.xlsx", "mail");
//		for(List<String> i:res) {
//			for(String j:i) {
//				System.out.print(j+"|---|");
//			}
//			System.out.println("");
//		}
		
//		TestSuit s = new TestSuit("./testCase/Case1.xlsx", "testCase");
//		for(String[] i:s.getTestSuit()) {
//			for(String j :i) {
//				System.out.print(j+"---");
//			}
//			System.out.println("");
//		}
		
		//
		List<String[]> s = Run.runTestSuit("./testCase/Case1.xlsx", "testCase");
		for(String[] i : s){
			for(String j : i){
				System.out.println(j);
			}
		}
		
//		String a = "dfdfdfdfdf %name% ffjfjjfjff";
//		//a.replaceFirst("\\%name\\%", "<td></td>");
//		String b = a.replace("%name%", "<td></<td>");
//		System.out.println(b);
//		String src = "33333333444444444";
//		System.out.println(src.replace("33","f"));
//	

	}

}
