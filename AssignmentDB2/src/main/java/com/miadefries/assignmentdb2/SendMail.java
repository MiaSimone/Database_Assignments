/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miadefries.assignmentdb2;

/**
 *
 * @author miade
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class SendMail {
    
    private static final String url = "jdbc:postgresql://localhost:5432/assignment2";
    private static final String user = "postgres";
    private static final String password = "Cristine535";
    
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
    
    public ArrayList<String> findEmails() {
        ArrayList<String> emailList = new ArrayList();
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                .getConnection(url,
                user, password);
        System.out.println("Opened database successfully");
        Statement stmt = c.createStatement();
        ResultSet rs;
 
        rs = stmt.executeQuery("select (select "
                + "(select p3.mail from person p3 where p3.person_id = p2.person_id) "
                + "from patients p2 where p2.patient_id = p.patient_id)  "
                + "from prescription p where expired = CURRENT_DATE + integer '3';");
        while ( rs.next() ) {
            String email = rs.getString("mail");
            emailList.add(email);
            System.out.println(email);
        }
         c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inserted email successfully");
        return emailList;
    }
    
    
    public static void main(String[] args) {
        
        
        TimerTask task = new TimerTask() {
            public void run() {
                SendMail s = new SendMail();
                ArrayList<String> emails = s.findEmails();
                for (String email: emails){
                    //from,password,to,subject,message  
                    //send("fromaddress218@gmail.com","Fromfrom12","toaddress2@gmail.com","Prescription expires","Remeber to renew your prescription");  
                    send("fromaddress218@gmail.com","Fromfrom12",email,"Prescription expires","Remeber to renew your prescription");  
                    //change from, password and to  
                }
            }
        };
        
        Timer t = new Timer();
        t.schedule(task, 0, 86400); //send email every day
        //t.schedule(task, 0, 10000); //send email every 10 seconds
        
        
    }
    
}
