/*Author : Diana P Lazar
Date created : 25/Feb/2016
Copyright@ FindGoose
*/
package com.FG.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserList;

public class UserList_DBUtil {
	
	public UserList getUserListByListId(String listId) {
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 UserList userlist = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from UserList where list_id='"+listId+"'");
			 userlist = (UserList)query.uniqueResult();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return userlist;
	}

}
