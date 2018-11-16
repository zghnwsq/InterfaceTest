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
		String caseName = "";
		String[] firstCaseOfModule = {"",""};
		int initRow = 0;
		int endRow = 0;
		String begTime = "begTime";
		String endTime = "endTime";
		String result = "result";
		sheet = Excel.readExcel(excel, sheetName); //读取excel,得到List<List<String>>
		//遍历获得testSuit信息(用例指针范围)
		Iterator<List<String>> it = sheet.iterator();
		while (it.hasNext()) {
			//ModuleName: row.get(0).toString()
			List<String> row = it.next();
			cusor = sheet.indexOf(row); //当前行在测试集中的下标
			if(row.get(1).toString().equals("TestCase")){
				//initRow++;
				continue;
			}else if(!row.get(1).toString().equals(caseName) && caseName.equals("")){ //第一个用例第一行
				caseName = row.get(1).toString();
				initRow = cusor;
				endRow = cusor;
				firstCaseOfModule[0] = row.get(0).toString();
				firstCaseOfModule[1] = row.get(1).toString();
				//如果该用例只有一行
				if(cusor == sheet.size()-1) {
					testSuit.add(new String[]{row.get(0).toString(), caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
				}
			}else if(!row.get(1).toString().equals(caseName) && !caseName.equals("")){ //下一个用例第一行		
				endRow = cusor-1;
				//保存上一个用例
				if(firstCaseOfModule[1].equals(caseName)) {
					testSuit.add(new String[]{firstCaseOfModule[0], caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
				}else {
					testSuit.add(new String[]{"", caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});				
				}
				caseName = row.get(1).toString();
				initRow = cusor;
				endRow = cusor;
				//如果该用例只有一行
				if(cusor == sheet.size()-1) {
					testSuit.add(new String[]{row.get(0).toString(), caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});
				}
				if(!row.get(0).toString().equals("")) {
					firstCaseOfModule[0] = row.get(0).toString();
					firstCaseOfModule[1] = row.get(1).toString();
				}
			}else if(row.get(1).toString().equals(caseName) && cusor == sheet.size()-1){
				//最后一行
				endRow = cusor;				
				testSuit.add(new String[]{row.get(0).toString(), caseName, String.valueOf(initRow), String.valueOf(endRow), begTime, endTime, result});				
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
