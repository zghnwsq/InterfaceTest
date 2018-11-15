package com.TestFrame;

import java.text.DecimalFormat;
import java.util.List;

public class Report {
	
	public List<String[]> suit;
	public String caseRow = "";
	public String caseModel = "<tr>\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseName%</td>\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseBegTime%</td>\n" + 
    		"                        	<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseEndTime%</td>\n" + 
    		"                      		<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; %caseResult%</td>\n" + 
    		"					   </tr>";
	public String caseModelWithTh = "<tr>"
			+ "							<th rowspan=\"%moduleCount%\">moduleName</th>"
			+ "							<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseName%</td>\n"
			+ "							<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseBegTime%</td>\n"
			+ "							<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseEndTime%</td>\n"
			+ "							<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; %caseResult%</td>\n"
			+ "						 </tr>";
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
		for(String[] i : suit){
			//获取用例集的结果和用例起止时间
			if(i[5].equals("PASS") && suitResult.equals("PASS")){
				suitResult = "PASS";
				pass++ ;
			}else if(i[5].equals("PASS") && suitResult.equals("FAIL")){
				pass++;
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
			//生成用例部分的行
			caseRow = caseRow + caseModel.replaceFirst("%caseName%", i[0]);
			caseRow = caseRow.replaceFirst("%caseBegTime%", i[3]);
			caseRow = caseRow.replaceFirst("%caseEndTime%", i[4]);
			if(i[5].equals("PASS")) {
				caseRow = caseRow.replaceFirst("%caseResult%", "color:#00FF00\">" + i[5]);
			}else if(i[5].equals("FAIL")) {
				caseRow = caseRow.replaceFirst("%caseResult%", "color:#FF0000\">" + i[5]);
			}else {
				caseRow = caseRow.replaceFirst("%caseResult%", "\">" + i[5]);
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

}
