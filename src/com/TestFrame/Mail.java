package com.TestFrame;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	//发件人邮箱密码，如果邮箱有设置smtp独立密码，则应使用独立授权码
	public String myEmailAccount="";
	public String myEmailPassword="";
	//SMTP服务器地址
	public String myEmailSMTPHost="";
	//SMTP端口
	public String smtpPort="";
	//发件人邮箱 应与登陆邮箱相同
	public String sendMail="";
	//收件人,逗号分隔
	public String receiveMailAccount="";
	//抄送
	public String copyMailAccount="";
	//邮件配置
	public Properties props;
	//昵称
	public String user="";
	//邮件标题
	public String title="";
	//邮件正文
	public String content="";
	
	public Mail(String myEmailSMTPHost, String myEmailAccount,String myEmailPassword, String smtpPort){
		this.myEmailSMTPHost = myEmailSMTPHost;
		this.myEmailAccount = myEmailAccount;
		this.myEmailPassword = myEmailPassword;
		if(!smtpPort.equals("")) {
			this.smtpPort = smtpPort;
		}else {
			this.smtpPort = "25";
		}
	}
	
	//设置邮件内容
	public void setMail(String user, String sendMail, String receiveMailAccount, String copyMailAccount, String title, String content) {
		this.user = user;
		this.receiveMailAccount = receiveMailAccount;
		this.copyMailAccount = copyMailAccount;
		this.title = title;
		this.content = content;
		this.sendMail = sendMail;
	}
	
	//Session服务器会话 sendMail发件人邮箱 recevieMail收件人邮箱
	public MimeMessage createMimeMessage(Session session) throws UnsupportedEncodingException, MessagingException{		
		//创建会话
		MimeMessage msg = new MimeMessage(session);
		//发件人
		msg.setFrom(new InternetAddress(sendMail,user,"UTF-8"));
		//收件人 逗号分隔?
		if(!receiveMailAccount.equals("")){
			new InternetAddress();
			InternetAddress[] internetAddressTo = InternetAddress.parse(receiveMailAccount);
			msg.setRecipients(MimeMessage.RecipientType.TO, internetAddressTo);
		}
		//抄送 逗号分隔?
		if(! copyMailAccount.equals("")){
			new InternetAddress();
			InternetAddress[] internetAddressCC = InternetAddress.parse(copyMailAccount);
			msg.setRecipients(MimeMessage.RecipientType.CC, internetAddressCC);
		}
		//设置标题
		msg.setSubject(title,"UTF-8");
		//邮件正文
		msg.setContent(content,"text/html;charset=UTF-8");
		//发件时间
		msg.setSentDate(new Date());
		//保存设置
		msg.saveChanges();		
		return msg;	
	}
	
	//发送邮件
	public void send() throws UnsupportedEncodingException, MessagingException {		
		//1.创建参数配置，用于连接邮件服务器的参数配置
		props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); //传输协议
		props.setProperty("mail.smtp.host", myEmailSMTPHost); //SMTP服务器地址
		props.setProperty("mail.smtp.auth", "true"); //需要认证
		
		//非SSL连接端口默认为25，可以不添加
		//SSL安全连接端口不同服务器不同
        //final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		
		//根据参数创建会话
		Session session = Session.getInstance(props);
		session.setDebug(true);  //设置debug模式
		
		//创建一封邮件
		MimeMessage message = createMimeMessage(session);
		//根据session，获取邮件传输对象
		Transport trans = session.getTransport();
		//使用邮箱账号和密码登陆，认证的邮箱必须与message中的发件人一致，否则报错
		//错误日志会在log中详细记录
		trans.connect(myEmailAccount, myEmailPassword);
		//发送邮件，message.getAllRecipients()获取到的是在创建邮件时添加的所有收件人
		trans.sendMessage(message, message.getAllRecipients());		
		//关闭连接
		trans.close();
	}
	
	
}
