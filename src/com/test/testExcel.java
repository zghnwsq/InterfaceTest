package com.test;

import java.util.List;

import com.TestFrame.Excel;
import com.TestFrame.TestSuit;

public class testExcel {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")); //当前用户路径
		List<List<String>> res = Excel.readExcel("./testCase/Case1.xlsx", "testCase");
		for(List<String> i:res) {
			for(String j:i) {
				System.out.print(j+"|---|");
			}
			System.out.println("");
		}
		
//		TestSuit s = new TestSuit("./testCase/Case1.xlsx", "testCase");
//		for(String[] i:s.getTestSuit()) {
//			for(String j :i) {
//				System.out.print(j+"---");
//			}
//			System.out.println("");
//		}

	}

}
