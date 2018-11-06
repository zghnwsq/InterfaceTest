package com.TestFrame;

import java.util.List;

/***
 * 
 * @author ted
 * @version 1.0
 */

public class TestSiut {

	public String excel; //文件路径
	public String sheetName; //执行用例sheet名
	public List<String> testSiut;
	
	
	
	public TestSiut(String excel, String sheetName){
		this.excel = excel;
		this.sheetName = sheetName;	
	}
	
	/***
	 * 
	 * @return 返回testSiut，包含用例名，起止行数，执行起止时间，结果
	 */
	public List<String[]> getTestSiut(){
		
		
		
		return null;
	}
	
	public void run(){
		
	}
	

}
