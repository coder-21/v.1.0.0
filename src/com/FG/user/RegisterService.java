package com.FG.user;

/*Author : Diana P Lazar
Copyright@ FindGoose

*/

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.company.CompanyProfile_userCreated;
import com.FG.user.Servlets.RegisterCompanyServlet;
import com.FG.user.Servlets.RegisterServlet;
import com.FG.utils.HibernateUtil;
public class RegisterService {
	
public boolean register(UserRegistrationInfo user){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	
	 if(isUserExists(user)) return false;	
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(user);		
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

public boolean registerCompany(CompanyProfile_userCreated companyProfile){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	
	 if(isCompanyExists(companyProfile)) 
		 return false;	
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(companyProfile);		
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

public boolean isUserExists(UserRegistrationInfo user){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	 boolean result = false;
	 Transaction tx = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserRegistrationInfo.class);
		 criteria.add(Restrictions.eq("user_email", user.getUser_email()));
		 
		 @SuppressWarnings("unchecked")
		List<UserRegistrationInfo> u = (List<UserRegistrationInfo>)criteria.list();
		 if(u!=null && u.size()>0)
		  { 
		      RegisterServlet.setuserRegisteredStatus(true);//User Exists... redirected to Registration page
			 result = true;
		  }else 
		  {
			  RegisterServlet.setuserRegisteredStatus(false);
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

public boolean isCompanyExists(CompanyProfile_userCreated company){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	 boolean result = false;
	 Transaction tx = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(CompanyProfile_userCreated.class);
		 criteria.add(Restrictions.eq("company_Name", company.getCompanyName()).ignoreCase());
		 
		 @SuppressWarnings("unchecked")
		List<CompanyProfile_userCreated> u = (List<CompanyProfile_userCreated>)criteria.list();
		 if(u!=null && u.size()>0)
		  { 
		      RegisterCompanyServlet.setCompanyRegisteredStatus(true);//User Exists... redirected to Registration page
			 result = true;
		  }else 
		  {
			  RegisterCompanyServlet.setCompanyRegisteredStatus(false);
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
