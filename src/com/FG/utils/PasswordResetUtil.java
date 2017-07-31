package com.FG.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserRegistrationInfo;
import com.FG.utils.AESencription.AESencrp;

public class PasswordResetUtil {

/*    Follow the steps below before executing the UPDATE command: On MysqlWorkbench

    Go to Edit --> Preferences
    Click "SQL Editor" tab and uncheck "Safe Updates" check box
    Query --> Reconnect to Server // logout and then login
    
    if teh above steps are not performed in mysql, An Update exception occurs*/

	public boolean updateDBwithNewPwd(UserRegistrationInfo user, String newpassword) {
		String oldPwd = user.getUser_password();
		String encryptNewPassword = "";
		try {
			encryptNewPassword = AESencrp.encrypt(newpassword);
		 } catch (Exception e) {
			e.printStackTrace();
		 }		
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update UserRegistrationInfo set user_prevpassword = '"+ oldPwd + "',user_password='"+encryptNewPassword+"' where user_email='"+user.getUser_email()+"'";
			 Query query = session.createQuery(queryStr);
			 int result = query.executeUpdate();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return true;
	}
}
