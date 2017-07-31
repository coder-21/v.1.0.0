package com.FG.user;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.utils.HibernateUtil;

public class MyProfileService {

	public boolean updateProfile(UserRegistrationInfo user){
		Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
		
		 
		
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 session.saveOrUpdate(user);		
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
