package com.FG.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import com.FG.Subscription.SubscriptionInfo;
import com.FG.company.CompanyEmployeesInfo;
import com.FG.company.CompanyInfo;
import com.FG.company.CompanyProfile_userCreated;
import com.FG.company.IndustryTypes;
import com.FG.company.Locations;
import com.FG.investor.InvestorInfo;
import com.FG.events.EventsEnrolled;
import com.FG.user.UserDocument;
import com.FG.user.UserList;
import com.FG.user.UserRegistrationInfo;
import com.FG.user.UserTeamTableInfo;
import com.FG.user.TasksTableInfo;
import com.FG.user.UserContactsInfo;
import com.FG.visitor.VisitorMessageInfo;

/*Author : Diana P Lazar
Date created : 10/08/2015
Copyright@ FindGoose
*/

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	static{
	    if (sessionFactory == null) {    	
	    	Configuration configuration = new Configuration();
	    	configuration.configure("hibernate.cfg.xml");
	    	configuration.addAnnotatedClass(UserRegistrationInfo.class);
	    	configuration.addAnnotatedClass(CompanyInfo.class);
	    	configuration.addAnnotatedClass(InvestorInfo.class);
	    	configuration.addAnnotatedClass(UserList.class);
	    	configuration.addAnnotatedClass(CompanyEmployeesInfo.class);
	    	configuration.addAnnotatedClass(UserTeamTableInfo.class);
	    	configuration.addAnnotatedClass(UserContactsInfo.class);
	    	configuration.addAnnotatedClass(IndustryTypes.class);
	    	configuration.addAnnotatedClass(Locations.class);
	    	configuration.addAnnotatedClass(CompanyProfile_userCreated.class);
	    	configuration.addAnnotatedClass(EventsEnrolled.class);
	    	configuration.addAnnotatedClass(SubscriptionInfo.class);
	    	configuration.addAnnotatedClass(UserDocument.class);
	    	configuration.addAnnotatedClass(TasksTableInfo.class);
	    	configuration.addAnnotatedClass(VisitorMessageInfo.class);
	    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	    	        .applySettings(configuration.getProperties()).build();
	    	 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    	 //sessionFactory.setAnnotatedClasses(new Class[] { UserRegistrationInfo.class });
	    }     
	}
    
public static Session openSession() {
    return sessionFactory.openSession();
}
}