package com.FG.Subscription;

/*Author : Diana P Lazar
Date created : 06/20/2016
Copyright@ FindGoose
*/

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.Subscription.SubscribeToFGServlet;
import com.FG.utils.HibernateUtil;
public class SubscribeService {
	
public boolean subscribe(SubscriptionInfo subscriber){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	
	 if(isSubscriberExists(subscriber)) return false;	
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(subscriber);		
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

public boolean isSubscriberExists(SubscriptionInfo subscriber){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	 boolean result = false;
	 Transaction tx = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(SubscriptionInfo.class);
		 criteria.add(Restrictions.eq("email", subscriber.getEmail()));
		 
		 @SuppressWarnings("unchecked")
		List<SubscriptionInfo> u = (List<SubscriptionInfo>)criteria.list();
		 if(u!=null && u.size()>0)
		  { 
		      SubscribeToFGServlet.setSubscribeStatus(true);//Subscriber Exists... redirected to Subscribe page
			 result = true;
		  }else 
		  {
			  SubscribeToFGServlet.setSubscribeStatus(false);
			  result=false;
		  }
		 tx.commit();
	 }catch(Exception ex){
		 if(tx!=null){
			 tx.rollback();
		 }
	 }finally{
		 session.close();
	 }
	 return result;
}

}
