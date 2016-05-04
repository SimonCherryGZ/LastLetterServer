package com.simoncherry.lastletter;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil{
	private static String username;
	private static String password;
	
	public MailUtil(String username, String password){
		MailUtil.username = username;
		MailUtil.password = password;
	}
	
	public void sendMail() throws Exception{
		final Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.sina.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", "simoncherry@sina.com");
		properties.put("mail.password", "VictorSvensson02");
		
		Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = properties.getProperty("mail.user");
                String password = properties.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
		
		Session session = Session.getInstance(properties, authenticator);
		session.setDebug(true);
		Transport ts = session.getTransport();
		ts.connect("smtp.sina.com", "simoncherry", "VictorSvensson02");
		Message message = createSimpleMail(session);
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	public static MimeMessage createSimpleMail(Session session) throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("simoncherry@sina.com"));
		//message.setRecipient(Message.RecipientType.TO, new InternetAddress("simoncherry@sina.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("798455757@qq.com"));
		message.setSubject("来自LastLetter的信件");
		message.setContent("你好\n    你的好友" + username + "在LastLetter网站中写了一封信，请凭密码"
				+ password + "登陆lastletter.vicp.net查看。",
				"text/html;charset=UTF-8");
		
		return message;
	}
}