package com.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;

import com.TestFrame.Excel;
import com.TestFrame.Report;
import com.TestFrame.Run;
import com.TestFrame.TestSuit;

public class testExcel {

	public static void main(String[] args) throws SecurityException, IOException {
		System.out.println(System.getProperty("user.dir")); //当前用户路径
		List<List<String>> res = Excel.readExcel("./mail/config.xlsx", "mail");
		for(List<String> i:res) {
//			System.out.println(i.get(0).equals(""));
			for(String j:i) {
//				System.out.print(j.equals(""));
				System.out.print("|"+j+"|---");
			}
			System.out.println(i.size());
			System.out.println("");
		}
		
		
//		Map<String, List<List<String>>> res = Excel.readExcelSheets("./mail/config.xlsx");
//		for(String i : res.keySet()) {
//			System.out.println(i);
//			for(List<String> j:res.get(i)) {
//				for(String k : j){
//					System.out.print("|"+k+"|---");
//				}
//				System.out.println("");
//			}
//		}
//		System.out.println("");
		
//		TestSuit s = new TestSuit("./testCase/Run.xlsx");
//		Map<String, List<String[]>> r = s.getTestSuits(); 
//		for(String i : r.keySet()) {
//			System.out.println(i);
//			for(String[] j : r.get(i)) {
//				for(String k : j) {
//					System.out.print("|"+k+"|---");
//				}
//				System.out.println("");
//			}
//			System.out.println("");
//		}
//		
//		File file = new File("D:\\20171126\\Git\\InterfaceTest\\testCase\\Run_result.xlsx");
//		System.out.println(file.exists());
//		System.out.println(file.isFile());
//		System.out.println(file.delete());
		
		
		
		
//		for(List<List<String>> i:res) {
//			//System.out.println(i.get(0).equals(""));
//			for(List<String> j:i) {
//				for(String k : j){
//					System.out.print("|"+k+"|---");
//				}
//				System.out.println("");
//			}
//			//System.out.println(i.size());
//			System.out.println("");
//		}
		
		
//		TestSuit s = new TestSuit("./testCase/Case2.xlsx", "sheet1");
//		List<String[]> suit;
//		suit = s.getTestSuit();
//		for(String[] i: suit) {
//			for(String j :i) {
//				System.out.print("|"+j+"|---");
//			}
//			System.out.println("");
//		}
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
//		List<String[]> a = new ArrayList<String[]>();
//		String[] b = {"PASS","hahaha"};
//		a.add(b);
//		Excel.writeExcel(a, "./testCase/Case2.xlsx", "testCase");
		
		

	}

}
