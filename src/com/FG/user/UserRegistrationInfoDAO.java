package com.FG.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserRegistrationInfoDAO {

	public static int register(UserRegistrationInfo userRegInfo){  
		  
		  int i =0;
		  SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			i = (Integer)session.save(userRegInfo);
			session.getTransaction().commit();
		  
		    
		  return i;  
		 }  
	
}
