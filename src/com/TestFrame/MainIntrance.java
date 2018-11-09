package com.TestFrame;

import java.io.IOException;
import java.util.List;

public class MainIntrance {

	public static void main(String[] args) throws SecurityException, IOException {
		String autoMail = "off";
		String myEmailSMTPHost = "";
		String myEmailAccount = "";
		String myEmailPassword = "";
		String smtpPort = "";
		String user = "";
		String receiveMailAccount = "";
		String copyMailAccount = "";
		String title = "";
		//运行用例集
		List<String[]> suit = Run.runTestSuit("./testCase/Case1.xlsx", "testCase");
		//获取用例集作为时间戳
		String suitBegTime = suit.get(0)[3];
		//创建html报告
		Report report = new Report(suit);
		String html = report.buildReport();
		//读取邮件配置
		List<List<String>> mailConfig = Excel.readExcel("./mail/config.xlsx", "mail");
		for(List<String> i:mailConfig) {
			if(i.get(0).trim().equals("AutoMail")) {
				autoMail = i.get(1).trim();
			}else if(i.get(0).trim().equals("SmtpHost")) {
				myEmailSMTPHost = i.get(1).trim();
			}else if(i.get(0).trim().equals("SmtpPort")) {
				smtpPort = i.get(1).trim();
			}else if(i.get(0).trim().equals("MailAccount")) {
				myEmailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("MailPassword")) {
				myEmailPassword = i.get(1).trim();
			}else if(i.get(0).trim().equals("SendFrom")) {
				user = i.get(1).trim();
			}else if(i.get(0).trim().equals("To")) {
				receiveMailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("CC")) {
				copyMailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("Title")) {
				title = i.get(1).trim();
			}
		}
		//写入html报告
		String htmlFileName = suitBegTime.trim().replaceAll("-", "_").replaceAll(":", "").replaceAll(" ", "") ;
		FileWrite.write("./html/Report_"+htmlFileName, html);
		//根据邮件配置发送邮件
		if(autoMail.equals("on")) {
			title = title + " " + suitBegTime; //邮件标题加个时间戳
			Mail mail = new Mail(myEmailSMTPHost, myEmailAccount, myEmailPassword, smtpPort);
			mail.setMail(user, myEmailAccount, receiveMailAccount, copyMailAccount, title, html);
		}
	}

}
