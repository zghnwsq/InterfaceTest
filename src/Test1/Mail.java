package Test1;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	//发件人邮箱密码，如果邮箱有设置smtp独立密码，则应使用独立授权码
	public String myEmailAccount="";
	public String myEmailPassword="";
	//SMTP服务器地址
	public String myEmailSMTPHost="";
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
	
	public Mail(String myEmailSMTPHost, String myEmailAccount,String myEmailPassword){
		this.myEmailSMTPHost = myEmailSMTPHost;
		this.myEmailAccount = myEmailAccount;
		this.myEmailPassword = myEmailPassword;
	}
	
	//Session服务器会话 sendMail发件人邮箱 recevieMail收件人邮箱
	public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMailAccount) throws UnsupportedEncodingException, MessagingException{
		//创建会话
		MimeMessage msg = new MimeMessage(session);
		//发件人
		msg.setFrom(new InternetAddress(sendMail,user,"UTF-8"));
		//收件人
		if(!receiveMailAccount.equals("")){
			new InternetAddress();
			InternetAddress[] internetAddressTo = InternetAddress.parse(receiveMailAccount);
			msg.setRecipients(MimeMessage.RecipientType.TO, internetAddressTo);
		}
		//抄送
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
	
	
	

	
	
	
	

}
