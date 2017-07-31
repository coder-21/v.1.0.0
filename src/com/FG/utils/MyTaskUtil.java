package com.FG.utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.TasksTableInfo;

public class MyTaskUtil {

	@SuppressWarnings("unchecked")
	public List<TasksTableInfo> getTasks( String execQuery){
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 List<TasksTableInfo> task = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery(execQuery);
			 task = (List<TasksTableInfo>)query.list();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return task;
	}
	public boolean createTask(TasksTableInfo task ){
		Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
				
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 session.saveOrUpdate(task);		
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

	public List<TasksTableInfo> getAssigned(String user_email) {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateStatus(int taskID, String taskState) {
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update TasksTableInfo set task_status = '"+ taskState + "' where task_id='"+taskID+"'";
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
	
}
