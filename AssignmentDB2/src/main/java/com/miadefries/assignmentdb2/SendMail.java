/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miadefries.assignmentdb2;

/**
 *
 * @author miade
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class SendMail {
    
    public static void send(String from,String password,String to,String sub,String msg){  
        
        String host = "smtp.gmail.com";
          //Get properties object    
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");
        
        
          //get Session   
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("fromaddress218@gmail.com", "Fromfrom12");
            }
        });
          //compose message    
          try {    
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
              Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(sub);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);   
           System.out.println("message sent successfully");    
          } catch (MessagingException e) {throw new RuntimeException(e);}    
             
    }  
    
    public static void main(String[] args) {
        //from,password,to,subject,message  
        send("fromaddress218@gmail.com","Fromfrom12","toaddress2@gmail.com","hello javatpoint","How r u?");  
        //change from, password and to  
    }
    
}
