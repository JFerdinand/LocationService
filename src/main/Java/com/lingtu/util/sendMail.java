package com.lingtu.util;




//SendMail.java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.*;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.mail.internet.MimeUtility;
import java.util.Date;

public class sendMail {

	
public static boolean sendText(String smtphost , String customMailBoxAddress,String username,String password,String serverMailBoxAddress,String subject, String strmailbody) {
    //这里面使用新浪作为发送邮件的邮件服务器，其他的smtp服务器可以到相关网站上查到。
    boolean rv = true;
	String host = smtphost;
    //发送方邮箱地址（如BlogJava2006@blog.com.cn.）
    String from = customMailBoxAddress;
    //收件人邮箱地址
    String to = serverMailBoxAddress;
    //发送者的邮箱用户名
    String user = username;
    //发送者的邮箱密码
    String ps = password;
    
    Properties props = new Properties();
    
    //设置发送邮件的邮件服务器的属性（这里使用新浪的smtp服务器）
    props.put("mail.smtp.host", host);
    //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有//这一条）
    props.put("mail.smtp.auth", "true");
    
    //用刚刚设置好的props对象构建一个session
    Session session = Session.getDefaultInstance(props);
    
    //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
    //用（有的时候网络连通性不够好，发送邮件可能会有延迟，在这里面会有所//提示，所以最好是加上这句，避免盲目的等待）
    session.setDebug(true);
    
    //定义消息对象
    MimeMessage message = new MimeMessage(session);
    try{
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        //设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText(strmailbody);
        multipart.addBodyPart(contentPart);
        //将multipart对象放到message中
        message.setContent(multipart);
        //发送邮件
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(host,username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        rv = true;
        
    }catch(Exception e){
    		rv = false;
    		e.printStackTrace();
    }
    
    return rv;
    
}
/**
 * 
 * 发送文件到指定的邮箱。
 * @param smtphost
 * @param fromail
 * @param username
 * @param password
 * @param tomail; 可以多个地址， 用; 分割。
 * @param subject
 * @param strmailbody
 * @return
 */
public static boolean sendFile(String smtphost , String fromail,String username,
		  String password,String tomail,String subject, String strmailbody,String strfile) {
	  boolean bret =false;
	   Properties props = new Properties();
	    
	  //设置发送邮件的邮件服务器的属性（这里使用新浪的smtp服务器）
    props.put("mail.smtp.host", smtphost);
    //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有//这一条）
    props.put("mail.smtp.auth", "true");
    
    //用刚刚设置好的props对象构建一个session
    Session session = Session.getDefaultInstance(props);
    session.setDebug(false);
    MimeMessage message = new MimeMessage(session);
    
    try{
        message.setFrom(new InternetAddress(fromail));
        String stra [] = tomail.split(";");
        for(int i=0;i<stra.length;i++)
        {
      	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(stra[i]));
        }
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        
        MimeMultipart   mp   =   new   MimeMultipart();   
        mp.setSubType("related");   
        MimeBodyPart   mbp1=   new   MimeBodyPart();   
        mbp1.setText(strmailbody);
        mp.addBodyPart(mbp1);   
        
        //增加附件。
        if(strfile!=null && strfile.length() > 0)
        {
      	  MimeBodyPart   mbp2=   new   MimeBodyPart(); 
      	  FileDataSource fds = new FileDataSource(strfile);
            //得到附件本身并至入BodyPart
      	  mbp2.setDataHandler(new DataHandler(fds));
      	  mbp2.setFileName(MimeUtility.encodeText("发布的乐曲"+new Date().toLocaleString()+".xls"));
        	  mp.addBodyPart(mbp2);   
        	 
        }
        
        //将multipart对象放到message中
        message.setContent(mp);
        //发送邮件
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(smtphost,username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        bret = true;
        
    }catch(Exception e){
  	  bret = false;
        e.printStackTrace();
    }
 
	  return bret;	  
}
	  

public static boolean sendHtml(String smtphost , String frommailaddr,String username,String password,String tomailaddr,String subject, String strmailbody) {
    //这里面使用新浪作为发送邮件的邮件服务器，其他的smtp服务器可以到相关网站上查到。
    boolean rv = true;
	String host = smtphost;
    //发送方邮箱地址（如BlogJava2006@blog.com.cn.）
    String from = frommailaddr;
    //收件人邮箱地址
    String to = tomailaddr;
    //发送者的邮箱用户名
    String user = username;
    //发送者的邮箱密码
    String ps = password;
    
    Properties props = new Properties();
    
    //设置发送邮件的邮件服务器的属性（这里使用新浪的smtp服务器）
    props.put("mail.smtp.host", host);
    //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有//这一条）
    props.put("mail.smtp.auth", "true");
    
    //用刚刚设置好的props对象构建一个session
    Session session = Session.getDefaultInstance(props);
    
    //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
    //用（有的时候网络连通性不够好，发送邮件可能会有延迟，在这里面会有所//提示，所以最好是加上这句，避免盲目的等待）
    session.setDebug(false);
    
    //定义消息对象
    MimeMessage message = new MimeMessage(session);
    try{
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
       
        
        MimeMultipart   mp   =   new   MimeMultipart();   
        
        mp.setSubType("related");   
        MimeBodyPart   mbp1=   new   MimeBodyPart();   
        String   html   = strmailbody;  
        mbp1.setContent(html,"text/html;charset=utf-8");   
        
        mp.addBodyPart(mbp1);   
        
        Multipart multipart = new MimeMultipart();
        //设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        //contentPart.setText(strmailbody);
        multipart.addBodyPart(contentPart);
        //将multipart对象放到message中
        message.setContent(mp);
        //发送邮件
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(host,username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        rv = true;
        
    }catch(Exception e){
    	rv = false;
        e.printStackTrace();
    }
    
    return rv;
    
}

public static boolean sendHtml(String smtphost , String customMailBoxAddress,String username,String password,String[] serverMailBoxAddress,String subject, String strmailbody) {
    //这里面使用新浪作为发送邮件的邮件服务器，其他的smtp服务器可以到相关网站上查到。
    boolean rv = true;
	String host = smtphost;
    //发送方邮箱地址（如BlogJava2006@blog.com.cn.）
    String from = customMailBoxAddress;
    //收件人邮箱地址
    String[] to = serverMailBoxAddress;
    //发送者的邮箱用户名
    String user = username;
    //发送者的邮箱密码
    String ps = password;
    
    Properties props = new Properties();
    
    //设置发送邮件的邮件服务器的属性（这里使用新浪的smtp服务器）
    props.put("mail.smtp.host", host);
    //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有//这一条）
    props.put("mail.smtp.auth", "true");
    
    //用刚刚设置好的props对象构建一个session
    Session session = Session.getDefaultInstance(props);
    
    //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
    //用（有的时候网络连通性不够好，发送邮件可能会有延迟，在这里面会有所//提示，所以最好是加上这句，避免盲目的等待）
    session.setDebug(false);
    
    //定义消息对象
    MimeMessage message = new MimeMessage(session);
    try{
        message.setFrom(new InternetAddress(from));
        
        for(int i=0;i<to.length;i++)
        {
      	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
        }
        
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
       
        
        MimeMultipart   mp   =   new   MimeMultipart();   
        
        mp.setSubType("related");   
        MimeBodyPart   mbp1=   new   MimeBodyPart();   
        String   html   = strmailbody;  
        mbp1.setContent(html,"text/html;charset=gb2312");   
        
        mp.addBodyPart(mbp1);   
        
        Multipart multipart = new MimeMultipart();
        //设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        //contentPart.setText(strmailbody);
        multipart.addBodyPart(contentPart);
        //将multipart对象放到message中
        message.setContent(mp);
        //发送邮件
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(host,username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        rv = true;
        
    }catch(Exception e){
    	rv = false;
        e.printStackTrace();
    }
    
    return rv;
    
}


public static boolean sendFileEx(String smtphost , String fromail,String username,
		  String password,String tomail,String subject, String strmailbody,String strfile, String strdispalyfile) {
	  boolean bret =false;
	   Properties props = new Properties();
	  //设置发送邮件的邮件服务器的属性（这里使用新浪的smtp服务器）
    props.put("mail.smtp.host", smtphost);
    //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有//这一条）
    props.put("mail.smtp.auth", "true");
    
    //用刚刚设置好的props对象构建一个session
    Session session = Session.getDefaultInstance(props);
    session.setDebug(false);
    MimeMessage message = new MimeMessage(session);
    
    try{
        message.setFrom(new InternetAddress(fromail));
        String stra [] = tomail.split(";");
        for(int i=0;i<stra.length;i++)
        {
      	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(stra[i]));
        }
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        
        MimeMultipart   mp   =   new   MimeMultipart();   
        mp.setSubType("related");   
        MimeBodyPart   mbp1=   new   MimeBodyPart();   
        mbp1.setText(strmailbody);
        mp.addBodyPart(mbp1);   
        
        //增加附件。
        if(strfile!=null && strfile.length() > 0)
        {
      	  MimeBodyPart   mbp2=   new   MimeBodyPart(); 
      	  FileDataSource fds = new FileDataSource(strfile);
            //得到附件本身并至入BodyPart
      	  mbp2.setDataHandler(new DataHandler(fds));
      	  mbp2.setFileName(MimeUtility.encodeText(strdispalyfile));
        	  mp.addBodyPart(mbp2);   
        }
        
        //将multipart对象放到message中
        message.setContent(mp);
        //发送邮件
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(smtphost,username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        bret = true;
        
    }catch(Exception e){
  	  bret = false;
        e.printStackTrace();
    }
 
	  return bret;	  
}
	
public static boolean sendCode(String email,String code){
	String title = "灵图";
	String content = "亲爱的用户：\n\t您好，欢迎您注册lingtuapp,您的验证码为："+code;
	System.out.println("亲爱的用户：\n\t您好，欢迎您注册lingtuapp,您的验证码为："+code);
	 return  sendMail.sendHtml("smtp.126.com" ,"sxsslxy_zy@126.com","sxsslxy_zy",
			  "zhangyao123",email,title,content);
}
public static void main(String args[])
{
	//String smtphost , String customMailBoxAddress,String username,String password,String[] serverMailBoxAddress,String subject, String strmailbody
  boolean bret =sendMail.sendHtml("smtp.126.com" ,"sxsslxy_zy@126.com","sxsslxy_zy",
		  "zhangyao123","418819382@qq.com","你是谁","你收到没？收到了?");

	System.out.println(bret);
}


}