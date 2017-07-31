/*
Copyright@ FindGoose
@Author-Suvarna
Updated useremail to member email in the set text

Update for Event Notification - Diana
 */
package com.FG.utils.emailTempPassword;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.FG.events.EventsEnrolled;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.AESencription.AESencrp;

public class SendInviteToFG {
	private static String senderEmail = Messages.getString("SenderEmail"); //$NON-NLS-1$
	private static String senderEmailEncryptedPwd = Messages.getString("SenderEmailEncryptedPwd"); //$NON-NLS-1$
	private static String inviteToFGEmailSubject = Messages.getString("inviteToFGEmailSubject"); //$NON-NLS-1$
	private static String inviteToFGEmailMsg1 = Messages.getString("inviteToFGEmailMsg1"); //$NON-NLS-1$
	private static String notifyEventEmailSubject = Messages.getString("notifyEventEmailSubject"); //$NON-NLS-1$
	private static String notifyEventEmailMsg = Messages.getString("notifyEventEmailMsg"); //$NON-NLS-1$
	private static String notifyEventEmailMsg2 = Messages.getString("notifyEventEmailMsg2"); //$NON-NLS-1$
	private static String visitormessagesubject = Messages.getString("VisitorMessageSubject"); //$NON-NLS-1$	
    public static void send(String userEmail, String memberEmail)
    { 
     //create an instance of Properties Class   
     Properties props = new Properties();
     
     /* Specifies the IP address of your default mail server
     	   for e.g if you are using gmail server as an email sever
           you will pass smtp.gmail.com as value of mail.smtp host. 
           As shown here in the code. 
           Change accordingly, if your email id is not a gmail id
        */
     props.put("mail.smtp.host", "smtp.gmail.com"); 
     //below mentioned mail.smtp.port is optional
     props.put("mail.smtp.port", "587");		 
     props.put("mail.smtp.auth", "true");  
     props.put("mail.smtp.starttls.enable", "true"); 
     
     /* Pass Properties object(props) and Authenticator object   
           for authentication to Session instance 
        */
    Session session = Session.getInstance(props,new javax.mail.Authenticator()
    {
  	  protected PasswordAuthentication getPasswordAuthentication() 
  	  {
  		try {
  	 	 return new PasswordAuthentication(senderEmail,AESencrp.decrypt(senderEmailEncryptedPwd));
  		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
  	  }
   });    
   try
   { 
 	/* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
 	   */ 
	   MimeMessage message = new MimeMessage(session);
       message.setFrom(new InternetAddress(senderEmail));
       message.addRecipient(Message.RecipientType.TO,new InternetAddress(memberEmail));
       message.setSubject(inviteToFGEmailSubject);
       
       message.setText(inviteToFGEmailMsg1 + " : " +  userEmail);
       /* Transport class is used to deliver the message to the recipients */
       Transport.send(message);
    }
    catch(Exception e)
    {
    	 e.printStackTrace();
    }
  }

    //This method implement the mailing an event feature while a FG user selects an event to notify a team member or a non-FG user.
    public boolean notifyEvent(UserRegistrationInfo user, String notifiedEmail, String notifiedEvent_Comment, EventsEnrolled notifiedEvent)
    { 
     Properties props = new Properties();
     props.put("mail.smtp.host", "smtp.gmail.com"); 
     props.put("mail.smtp.port", "587");		 
     props.put("mail.smtp.auth", "true");  
     props.put("mail.smtp.starttls.enable", "true"); 

    Session session = Session.getInstance(props,new javax.mail.Authenticator()
    {
  	  protected PasswordAuthentication getPasswordAuthentication() 
  	  {
  		try {
  	 	 return new PasswordAuthentication(senderEmail,AESencrp.decrypt(senderEmailEncryptedPwd));
  		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
  	  }
   });    
   try
   { 
 	/* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
 	   */ 
	   MimeMessage message = new MimeMessage(session);
       message.setFrom(new InternetAddress(user.getUser_firstname()+"@findgoose.com",user.getUser_firstname() + " " + user.getUser_lastname() ));//sender = new InternetAddress("xyz@facebook.com","Facebook");
       message.addRecipient(Message.RecipientType.TO,new InternetAddress(notifiedEmail));
       message.setSubject(notifyEventEmailSubject);
       String messageContentStr = user.getUser_firstname() + " " + user.getUser_lastname()+" ( "+user.getUser_email()+" ) " + 
    		   					notifyEventEmailMsg + 
    		   					"Event Name: " + notifiedEvent.getEventName() + "; " +
    		   					"Event Location: " + notifiedEvent.getEventLocation() + "; " +
    		   					"Event Date: " + notifiedEvent.getEventDate() + "; " +
    		   					"Event URL: " + notifiedEvent.getEventURL() +
    		   					notifyEventEmailMsg2 +". " + notifiedEvent_Comment;
//       message.setText(user.getUser_firstname() + " " + user.getUser_lastname()+" ( "+user.getUser_email()+" ) " + notifyEventEmailMsg + notifiedEvent_Comment);
       message.setText(messageContentStr);
       /* Transport class is used to deliver the message to the recipients */
       Transport.send(message);
    }
    catch(Exception e)
    {
    	 return false;//e.printStackTrace();
    }
   return true;
  }
    
    public boolean SendVisitorMessage(String email, String name, String message){
    	Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587");		 
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.starttls.enable", "true"); 

       Session session = Session.getInstance(props,new javax.mail.Authenticator()
       {
     	  protected PasswordAuthentication getPasswordAuthentication() 
     	  {
     		try {
     	 	 return new PasswordAuthentication(senderEmail,AESencrp.decrypt(senderEmailEncryptedPwd));
     		} catch (Exception e) {
   			e.printStackTrace();
   			return null;
   		}
     	  }
      });    
      try
      { 
    	  /* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
 	   */ 
	   MimeMessage emailmessage = new MimeMessage(session);
       emailmessage.setFrom(new InternetAddress(email));
       emailmessage.addRecipient(Message.RecipientType.TO,new InternetAddress(senderEmail));
       emailmessage.setSubject(visitormessagesubject);
       
       emailmessage.setText("Message from "+name+"("+email+") : "+message );
       /* Transport class is used to deliver the message to the recipients */
       Transport.send(emailmessage);
       }
       catch(Exception e)
       {
       	 return false;//e.printStackTrace();
       }
      return true;
    }
}
