package com.FG.utils.emailTempPassword;

/*The sender email needs to be authenticated, esp if its a gmail account. 
We are using gmail account, so we will have to authenticate mail services of the 
gmail account, then;login to the sending gmail account, 
and access this link--> https://www.google.com/settings/security/lesssecureapps
	and select "Turn On"
"If this is not done, the email feature throws an exception as below:"
"javax.mail.AuthenticationFailedException: 534-5.7.14 <https://accounts.google.com/ContinueSignIn?sarp=1&scc=1&plt=AKgnsbsNX
*/
/*Author : Diana P Lazar
Date created : 10/29/2015
Copyright@ FindGoose
 */

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.FG.user.LoginService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.AESencription.AESencrp;


public class SendTempPassword 
{ 	
	private static String senderEmail = Messages.getString("SenderEmail"); //$NON-NLS-1$
	private static String senderEmailEncryptedPwd = Messages.getString("SenderEmailEncryptedPwd"); //$NON-NLS-1$
	private static String tempPwdEmailSubject = Messages.getString("TempPwdEmailSubject"); //$NON-NLS-1$
	private static String tempPwdEmailMsg1 = Messages.getString("TempPwdEmailMsg1"); //$NON-NLS-1$
		
    public static void send(String userEmail, String userTempPwd)
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
       message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));
       message.setSubject(tempPwdEmailSubject);
       
       message.setText(tempPwdEmailMsg1 + " : " + userTempPwd);
       /* Transport class is used to deliver the message to the recipients */
       Transport.send(message);
    }
    catch(Exception e)
    {
    	 e.printStackTrace();
    }
  }

	
}