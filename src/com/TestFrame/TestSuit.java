package com.TestFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * 
 * @author ted
 * @version 1.0
 */

public class TestSuit {

	public String excel; //文件路径
	public String sheetName; //执行用例sheet名
	public List<String[]> testSuit;
	public List<List<String>> sheet;	
	
	public TestSuit(String excel, String sheetName){
		this.excel = excel;
		this.sheetName = sheetName;	
		testSuit = new ArrayList<String[]>();
	}
	
	/***
	 * 
	 * @return 返回testSiut，包含用例名，起止行数，执行起止时间，结果
	 */
	
	public List<String[]> getTestSuit(){
		int cusor=0;
		String caseName="";
		int initRow = 0;
		int endRow = 0;
		String begTime = "begTime";
		String endTime = "endTime";
		String result = "result";
		sheet = Excel.readExcel(excel, sheetName);
		Iterator<List<String>> it = sheet.iterator();
		while (it.hasNext()) {
			List<String> row = it.next();
			cusor = sheet.indexOf(row); //当前行在测试集中的下标
			if(row.get(0).toString().equals("TestCase")){
				//initRow++;
				continue;
			}else if(!row.get(0).toString().equals(caseName) && caseName.equals("")){ //第一个用例
				caseName = row.get(0).toString();
				initRow = cusor;
				endRow = cusor;
				if(cusor == sheet.size()-1) {
					testSuit.add(new String[]{caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
				}
			}else if(!row.get(0).toString().equals(caseName) && !caseName.equals("")){ //第二个用例开始		
				endRow = cusor-1;
				String[] tc = {caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result};
				testSuit.add(tc);
				caseName = row.get(0).toString();
				initRow = cusor;
				endRow = cusor;
				if(cusor == sheet.size()-1) {
					testSuit.add(new String[]{caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
				}				
			}else if(row.get(0).toString().equals(caseName) && cusor == sheet.size()-1){
				endRow = cusor;
				testSuit.add(new String[]{caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
			}else {
				endRow = cusor; //中间行
			}
		}		
		return testSuit;
	}
	
	/***
	 * 	
	 * @return 返回获取的用例集
	 */
	public List<List<String>> getTestCaseColletion(){	
			return sheet;
	}
	

}
