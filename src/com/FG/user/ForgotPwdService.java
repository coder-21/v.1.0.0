/*
 * Author : Diana
 * Created Date : 27-10-2015
 * Copyright@ FindGoose
 */
package com.FG.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.utils.HibernateUtil;

public class ForgotPwdService {
	
public boolean userExists(String email) {
	 UserRegistrationInfo user = getUserByEmail(email);		
	 if(user!=null && user.getUser_email().equals(email)){
		 return true;
	 }else{
		 return false;
	 }
}
public UserRegistrationInfo getUserByEmail(String email) {
	 Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 UserRegistrationInfo user = null;
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 Query query = session.createQuery("from UserRegistrationInfo where user_email='"+email+"'");
		 user = (UserRegistrationInfo)query.uniqueResult();
		 tx.commit();
	 } catch (Exception e) {
		 if (tx != null) {
			 tx.rollback();
		 }
		 e.printStackTrace();
	 } finally {
		 session.close();
	 }
	 return user;
}

}
