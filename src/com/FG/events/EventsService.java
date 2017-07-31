/*Author : Diana P Lazar
Date created : aug/08/2016
Copyright@ FindGoose
*/

package com.FG.events;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.user.UserDocument;
import com.FG.utils.HibernateUtil;

public class EventsService {

	public boolean saveEventToDB(EventsEnrolled eventCreated) {
			Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
			
			 Transaction tx = null;	
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 session.saveOrUpdate(eventCreated);		
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

	@SuppressWarnings("unchecked")
	public List<EventsEnrolled> getUserCreatedEvents(String eventCreatorEmail) {
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 List<EventsEnrolled> events = null;
		 try{
			 tx = session.getTransaction();
			 tx.begin();
			 
			 Criteria criteria = session.createCriteria(UserDocument.class);
			 criteria.add(Restrictions.eq("eventCreatorEmail", eventCreatorEmail));
			 events = (List<EventsEnrolled>)criteria.list();
				 tx.commit();
		 }catch(Exception ex){
			 ex.printStackTrace();
			 if(tx!=null){
				 tx.rollback();
			 }
		 }finally{
			 session.close();
		 }
		 return events;
	}

	@SuppressWarnings("unchecked")
	public static List<EventsEnrolled> getListOfEventsInDB(){
		 List<EventsEnrolled> list = new ArrayList<EventsEnrolled>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = (List<EventsEnrolled>)session.createQuery("from EventsEnrolled").list();					
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
	
	public boolean removeSelectedEvent(String selectedEventId) {
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("delete from EventsEnrolled where eventId='"+selectedEventId+"'");
			 query.executeUpdate();
			 
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
			 return false;
		 } finally {
			 session.close();
		 }
		 return true;
	}
	
}
