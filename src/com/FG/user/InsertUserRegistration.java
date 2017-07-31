package com.FG.user;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.FG.company.IndustryTypes;
import com.FG.company.Locations;

public class InsertUserRegistration {
	
//	public static void main(String[] args) {
	public void main2(String[] args) {
//		for (int i = 0; i < 40; i++) {
			
//		UserRegistrationInfo userRegistrationInfo =  new UserRegistrationInfo("G" + i, "B"+i, "GB"+i+".findgoose@gmail.com", i+"password", i+"FG", new Date(), i+"basic");
		UserRegistrationInfo userRegistrationInfo =  new UserRegistrationInfo("G", "B", "GB.findgoose@gmail.com", "password", "FG", new Date(), "basic","yes","","");
//		userRegistrationInfo.setUser_id(0);
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(userRegistrationInfo);
		session.getTransaction().commit();
		session.close();
//	}
	}
	
	public static void main(String[] args) {
//		public void main2(String[] args) {
//			for (int i = 0; i < 40; i++) {
				
//			UserRegistrationInfo userRegistrationInfo =  new UserRegistrationInfo("G" + i, "B"+i, "GB"+i+".findgoose@gmail.com", i+"password", i+"FG", new Date(), i+"basic");
//			UserList userList =  new UserList("1_GB.findgoose@gmail.com","24,30", "GB.findgoose@gmail.com");
//			userRegistrationInfo.setUser_id(0);
			
		
//			CompanyEmployeesInfo cei= new CompanyEmployeesInfo(2,"one" , "two" , "three", "five" , "four");
			
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
		
//		}
		}

}
