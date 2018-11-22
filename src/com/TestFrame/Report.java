package com.TestFrame;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {
	
	public List<String[]> suit;
	public String caseRow = "";
	public String caseModel = "<tr>\r\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseName%</td>\r\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseBegTime%</td>\r\n" + 
    		"                        	<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseEndTime%</td>\r\n" + 
    		"                      		<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; %caseResult%</td>\r\n" + 
    		"					   </tr>\r\n";
	public String caseModelWithTh = "<tr>\r\n"
			+ "							<th rowspan=\"%moduleCount%\">%moduleName%</th>"
			+ "							<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseName%</td>\r\n"
			+ "							<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseBegTime%</td>\r\n"
			+ "							<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseEndTime%</td>\r\n"
			+ "							<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; %caseResult%</td>\r\n"
			+ "						 </tr>\r\n";
	public String cm = "<tr>\r\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%moduleName%</td>\r\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseCount%</td>\r\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%casePass%</td>\r\n" +
    		"                        	<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; %moduleResult%</td>\r\n" + 
    		"                      		<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%moduleRatio%</td>\r\n" + 
    		"					   </tr>\r\n";
    public String html ="";
    
    
 	public Report(List<String[]> suit){
		this.suit = suit;
	}
 	
 	public String buildReport(){
 		String suitResult = "PASS"; //整个用例集结果
 		int pass = 0; //通过用例数
 		int fail = 0; //失败用例数
 		int noresult = 0; //没有结果
 		String suitBegTime = ""; //用例集开始时间
 		String suitEndTime = ""; //用例集结束时间
 		Map<String, String> module = new HashMap<String, String>();
 		String moduleName = "";
 		int moduleCaseCount = 0;
		for(String[] i : suit){
			//获取模块下用例集数量
			if(!i[0].equals("") && moduleName.equals("")) {  //第一个module
				moduleName = i[0];
				moduleCaseCount++;
			}else if(!i[0].equals("") && !moduleName.equals("") && !i[0].equals(moduleName)) {  //下一个module
				module.put(moduleName, String.valueOf(moduleCaseCount));
				moduleName = i[0];
				moduleCaseCount = 1;
			}else if(suit.indexOf(i)==suit.size()-1) {
				moduleCaseCount++;
				module.put(moduleName, String.valueOf(moduleCaseCount));
			}else {
				moduleCaseCount++;
			}
			
			//获取用例集的结果和用例起止时间
			if(i[6].equals("PASS") && suitResult.equals("PASS")){
				suitResult = "PASS";
				pass++ ;
			}else if(i[6].equals("PASS") && suitResult.equals("FAIL")){
				pass++;
			}else if(i[6].equals("")){
				noresult++;
			}else{
				suitResult = "FAIL";
				fail++;
			}
			
			if(suitBegTime.equals("")){
				suitBegTime = i[4];
			}
			suitEndTime = i[5];
		}

		//再次遍历,生成报告的用例表
		for(String[] j : suit){
			//生成用例部分的行
			if(!j[0].equals("")) {
				caseRow = caseRow+caseModelWithTh.replaceFirst("%moduleName%", j[0]);
				caseRow = caseRow.replaceFirst("%moduleCount%", module.get(j[0]));
			}else {
				caseRow = caseRow+caseModel;
			}
			caseRow = caseRow.replaceFirst("%caseName%", j[1]);
			caseRow = caseRow.replaceFirst("%caseBegTime%", j[4]);
			caseRow = caseRow.replaceFirst("%caseEndTime%", j[5]);
			if(j[6].equals("PASS")) {
				caseRow = caseRow.replaceFirst("%caseResult%", "color:#00FF00\">" + j[6]);
			}else if(j[6].equals("FAIL")) {
				caseRow = caseRow.replaceFirst("%caseResult%", "color:#FF0000\">" + j[6]);
			}else {
				caseRow = caseRow.replaceFirst("%caseResult%", "\">" + j[6]);
			}
		}
		//计算通过率
		int sum = pass+fail+noresult;
		String ratio ;
		if(sum >0) {
			DecimalFormat df = new DecimalFormat("0.00");
			ratio = df.format((float)pass/sum*100);
		}else {
			ratio = "-";
		}
		//根据模板替换数据
		html = ReadFile.readToString("./html/model.txt", "UTF-8");
		html = html.replace("%testSuitDuration%", suitBegTime+" - "+suitEndTime);
		html = html.replace("%casesCount%", String.valueOf(sum));
		html = html.replace("%pass%", String.valueOf(pass));
		html = html.replace("%fail%", String.valueOf(fail));
		html = html.replace("%noresult%", String.valueOf(noresult));
		if(suitResult.equals("PASS")){
			html = html.replace("%suitResult%", "color:#00FF00\">"+suitResult);
		}else if(suitResult.equals("FAIL")){
			html = html.replace("%suitResult%", "color:#FF0000\">"+suitResult);
		}else {
			html = html.replace("%suitResult%", "\">"+suitResult);
		}
		html = html.replace("%ratio%", ratio+"%");
		html = html.replace("%casesRow%", caseRow);		
		return html;		
 	}

 	public String buildSumReport(){
 		String suitResult = "PASS"; //整个用例集结果
 		int pass = 0; //通过用例数
 		int fail = 0; //失败用例数
 		int noresult = 0; //没有结果
 		String suitBegTime = ""; //用例集开始时间
 		String suitEndTime = ""; //用例集结束时间
 		Map<String, String> module = new HashMap<String, String>(); //模块名:模块用例数量
 		Map<String, String> modulePass = new HashMap<String, String>();	 //模块名:模块通过用例数	
 		String moduleName = "";
 		int moduleCaseCount = 0;
 		int modulePassCount = 0;
		for(String[] i : suit){
			//获取模块下用例集数量
			if(!i[0].equals("") && moduleName.equals("")) {  //第一个module
				moduleName = i[0];
				moduleCaseCount++;
				if(i[6].equals("PASS")) {
					modulePassCount++;
				}
			}else if(!i[0].equals("") && !moduleName.equals("") && !i[0].equals(moduleName)) {  //下一个module
				module.put(moduleName, String.valueOf(moduleCaseCount));
				modulePass.put(moduleName, String.valueOf(modulePassCount));
				modulePassCount = 0;
				moduleName = i[0];
				moduleCaseCount = 1;
				if(i[6].equals("PASS")) {
					modulePassCount++;
				}
			}else if(suit.indexOf(i)==suit.size()-1) {  //最后一行
				moduleCaseCount++;
				if(i[6].equals("PASS")) {
					modulePassCount++;
				}
				module.put(moduleName, String.valueOf(moduleCaseCount));
				modulePass.put(moduleName, String.valueOf(modulePassCount));
			}else {
				moduleCaseCount++;
				if(i[6].equals("PASS")) {
					modulePassCount++;
				}
			}

			//获取用例集的结果和用例起止时间
			if(i[6].equals("PASS") && suitResult.equals("PASS")){
				suitResult = "PASS";
				pass++ ;
			}else if(i[6].equals("PASS") && suitResult.equals("FAIL")){
				pass++;
			}else if(i[6].equals("")){
				noresult++;
			}else{
				suitResult = "FAIL";
				fail++;
			}
			
			if(suitBegTime.equals("")){
				suitBegTime = i[4];
			}
			suitEndTime = i[5];
		}

		//再次遍历,生成报告的用例表 废
		
		//装配moduleRow
		String moduleRow = "";
		for(String i:module.keySet()) {
			if(moduleRow.equals("")) {
				moduleRow = cm; //第一行则直接复制模板
			}else {
				moduleRow = moduleRow + cm; //其他行则新加一行模板行
			}
			//开始在新加的模板行上替换值
			moduleRow = moduleRow.replace("%moduleName%", i);
			moduleRow = moduleRow.replace("%caseCount%", module.get(i));
			moduleRow = moduleRow.replace("%casePass%", modulePass.get(i));
			if(modulePass.get(i).equals(module.get(i))) {
				moduleRow = moduleRow.replace("%moduleResult%", "color:#00FF00\">PASS");
			}else {
				moduleRow = moduleRow.replace("%moduleResult%", "color:#FF0000\">FAIL");
			}
			DecimalFormat df1 = new DecimalFormat("0.00");
			int mp = Integer.valueOf(modulePass.get(i));
			int ms = Integer.valueOf(module.get(i));
			String ratio = df1.format((float)mp/ms*100);
			moduleRow = moduleRow.replace("%moduleRatio%", ratio+"%");
		}

		//计算通过率
		int sum = pass+fail+noresult;
		String ratio ;
		if(sum >0) {
			DecimalFormat df = new DecimalFormat("0.00");
			ratio = df.format((float)pass/sum*100);
		}else {
			ratio = "-";
		}
		//根据模板替换数据
		html = ReadFile.readToString("./html/model.txt", "UTF-8");
		html = html.replace("%testSuitDuration%", suitBegTime+" - "+suitEndTime);
		html = html.replace("%casesCount%", String.valueOf(sum));
		html = html.replace("%pass%", String.valueOf(pass));
		html = html.replace("%fail%", String.valueOf(fail));
		html = html.replace("%noresult%", String.valueOf(noresult));
		if(suitResult.equals("PASS")){
			html = html.replace("%suitResult%", "color:#00FF00\">"+suitResult);
		}else if(suitResult.equals("FAIL")){
			html = html.replace("%suitResult%", "color:#FF0000\">"+suitResult);
		}else {
			html = html.replace("%suitResult%", "\">"+suitResult);
		}
		html = html.replace("%ratio%", ratio+"%");
		html = html.replace("%casesRow%", moduleRow);		
		return html;		
 	}
}
