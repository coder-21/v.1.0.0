/*Author : Diana P Lazar
Date created : 11/Nov/2015
Copyright@ FindGoose
*/

package com.FG.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserRegistrationInfo;

public class User_DButil {

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

	@SuppressWarnings("unchecked")
	public List<UserRegistrationInfo> getListOfUsers(){
		 List<UserRegistrationInfo> list = new ArrayList<UserRegistrationInfo>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery("from UserRegistrationInfo").list();					
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserRegistrationInfo> getUserResultSets(String queryString){
		 List<UserRegistrationInfo> list = new ArrayList<UserRegistrationInfo>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery(queryString).list();					
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return list;
	}
	
	
	//This method Updates UserTable with user's RememberMe decision
	public boolean saveUserRememberMeToken(UserRegistrationInfo current_user,String cookieToken ){
		Session session = HibernateUtil.openSession();
		
		UserRegistrationInfo user = current_user;
		user.setUser_CookieToken(cookieToken);
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
