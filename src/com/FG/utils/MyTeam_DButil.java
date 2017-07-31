package com.FG.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserRegistrationInfo;
import com.FG.user.UserTeamTableInfo;
import com.FG.utils.AESencription.AESencrp;

public class MyTeam_DButil {

	public UserTeamTableInfo getUserTeam(String email){
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 UserTeamTableInfo user = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from UserTeamTableInfo where user_email='"+email+"'");
			 user = (UserTeamTableInfo)query.uniqueResult();
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
	
	public boolean setDBTeam_members(String userEmail, String teamMemberArr) {
			
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update UserTeamTableInfo set team_members = '"+ teamMemberArr + "' where user_email='"+userEmail+"'";
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
	public boolean setDBTeam_Names(String userEmail, String teamNameArr) {
		
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update UserTeamTableInfo set team_fullname = '"+ teamNameArr + "' where user_email='"+userEmail+"'";
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
public boolean setCount(String userEmail, int count) {
		
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update UserTeamTableInfo set members_count = '"+ count + "' where user_email='"+userEmail+"'";
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
public void setRequests_Received(String userEmail, String arrReceived) {
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 String queryStr = "update UserTeamTableInfo set requests_received = '"+ arrReceived + "' where user_email='"+userEmail+"'";
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
}
public void setRequests_Sent(String userEmail, String arrSent) {
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 String queryStr = "update UserTeamTableInfo set requests_sent = '"+ arrSent + "' where user_email='"+userEmail+"'";
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
}

public boolean addNewUser(UserTeamTableInfo userTeam){
	
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(userTeam);		
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
