package com.FG.utils;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserContactsInfo;
import com.FG.user.UserTeamTableInfo;

public class Contacts_DBUtil {
	
	public ArrayList<UserContactsInfo>  getUserContacts(String email){
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 ArrayList<UserContactsInfo> contacts = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from UserContactsInfo where user_email='"+email+"'");
			contacts=(ArrayList<UserContactsInfo>) query.list();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return contacts;
		
	}
	}


