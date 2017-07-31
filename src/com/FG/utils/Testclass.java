package com.FG.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.FG.company.IndustryTypes;
import com.FG.company.Locations;

public class Testclass {
	
	public static void main(String[] args) {
		
		for (String str :IndustryTypes.types) {
			IndustryTypes industryTypes_= new IndustryTypes(str);
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			
			session.beginTransaction();
			session.save(industryTypes_);
			session.getTransaction().commit();
			session.close();
		}
	
	for (String str :Locations.locations) {
		Locations locations= new Locations(str);
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(locations);
		session.getTransaction().commit();
		session.close();
	}
	}
}
