package com.test;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.TestFrame.Mail;

public class testMail {

	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
		Mail mail = new Mail("smtp.sina.cn","tedwang@sina.cn","wsq851021","465");
		mail.setMail("tedwang", "tedwang@sina.cn", "tedwang@sina.cn", "", "report test", "软件测试报告");
		mail.send();

	}

}
