package com.TestFrame;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

public class MainEntrance {

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
		List<String[]> suit = Run.runTestSuit("./testCase/Case2.xlsx", "testCase");
		//List<String[]> suit = Run.runTestSuit("./testCase/Case2.xlsx"); //运行整个sheet
		//获取用例集作为时间戳
		String suitBegTime = suit.get(0)[4];
		//创建html报告
		Report report = new Report(suit);
		String html = report.buildReport();
		//读取邮件配置
		List<List<String>> mailConfig = Excel.readExcel("./mail/config.xlsx", "mail");
		for(List<String> i:mailConfig) {
			if(i.get(0).trim().equals("AutoMail") && i.size()>1) {
				autoMail = i.get(1).trim();
			}else if(i.get(0).trim().equals("SmtpHost") && i.size()>1) {
				myEmailSMTPHost = i.get(1).trim();
			}else if(i.get(0).trim().equals("SmtpPort") && i.size()>1) {
				smtpPort = i.get(1).trim();
			}else if(i.get(0).trim().equals("MailAccount") && i.size()>1) {
				myEmailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("MailPassword") && i.size()>1) {
				myEmailPassword = i.get(1).trim();
			}else if(i.get(0).trim().equals("SendFrom") && i.size()>1) {
				user = i.get(1).trim();
			}else if(i.get(0).trim().equals("To") && i.size()>1) {
				receiveMailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("CC") && i.size()>1) {
				copyMailAccount = i.get(1).trim();
			}else if(i.get(0).trim().equals("Title") && i.size()>1) {
				title = i.get(1).trim();
			}
		}
		//写入html报告
		String htmlFileName = suitBegTime.trim().replaceAll("-", "_").replaceAll(":", "").replaceAll(" ", "") ;
		FileWrite.write("./report/Report_"+htmlFileName+".html", html);
		//根据邮件配置发送邮件
		if(autoMail.equals("on")) {
			title = title + " " + suitBegTime; //邮件标题加个时间戳
			Mail mail = new Mail(myEmailSMTPHost, myEmailAccount, myEmailPassword, smtpPort);
			mail.setMail(user, myEmailAccount, receiveMailAccount, copyMailAccount, title, html);
			try {
				mail.send();
				System.out.println("mail sent");
			} catch (MessagingException e) {
				System.out.println("send fail");
				e.printStackTrace();
			}
		}
	}

}
