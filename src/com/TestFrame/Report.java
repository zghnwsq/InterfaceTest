package com.TestFrame;

import java.util.List;

public class Report {
	
	public List<String[]> suit;
	public String caseRow = "";
	public String caseModel = "<tr>\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseName%</td>\n" + 
    		"                        	<td height=\"28\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseBegTime%</td>\n" + 
    		"                        	<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc;\">%caseEndTime%</td>                   \n" + 
    		"                      		<td bgcolor=\"#FFFFFF\" align=\"center\" style=\"border:1px solid #ccc; color:#00FF00 \">%caseResult%</td>\n" + 
    		"					</tr>";
    public String html="";
    
 	public Report(List<String[]> suit){
		this.suit = suit;
	}
 	
 	public String buildReport(){
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
			caseRow = caseRow + caseModel.replaceFirst("%caseName%", i[0]);
			caseRow = caseRow +caseRow.replaceFirst("%caseBegTime%", i[3]);
			caseRow = caseRow +caseRow.replaceFirst("%caseEndTime%", i[4]);
			caseRow = caseRow +caseRow.replaceFirst("%caseResult%", i[5]);
		}
		int sum = pass+fail+noresult;
		float ratio ;
		if(sum >0) {
			ratio = pass/sum*100 ;
		}else {
			ratio = 0;
		}
		html = ReadFile.readToString("./html/model", "UTF-8");
		html.replaceFirst("%testSuitDuration%", suitBegTime+" - "+suitEndTime);
		html.replaceFirst("%casesCount%", String.valueOf(sum));
		html.replaceFirst("%pass%", String.valueOf(pass));
		html.replaceFirst("%fail%", String.valueOf(fail));
		html.replaceFirst("%noresult%", String.valueOf(noresult));
		html.replaceFirst("%suitResult%", String.valueOf(suitResult));
		html.replaceFirst("%ratio%", String.valueOf(ratio)+"%");
		html.replaceFirst("%casesRow%", caseRow);
		
		return html;		
 	}

}
