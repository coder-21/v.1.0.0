package com.FG.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.company.CompanyInfo;

public class Company_DButil {
	
	@SuppressWarnings("unchecked")
	public List<CompanyInfo> getCompanyResultSetsfromCompanyTable(String queryString){
		 List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery(queryString).list();					
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

	public CompanyInfo getCompanyByNameFromCompanyTable(String compName) {
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 CompanyInfo company = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from CompanyInfo where name='"+compName+"'");
			 company = (CompanyInfo)query.uniqueResult();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return company;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getInvestorsFromCOMPANYTable(){
		 List<String> list = new ArrayList<String>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery("select investors from CompanyInfo").list();					
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

	@SuppressWarnings("unchecked")
	public List<String> getCompaniesGivenTheInvestorfromCOMPANYTable(String investor){
		 List<String> list = new ArrayList<String>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery("select name from CompanyInfo where (investors like \'%"+investor+"%\')").list();					
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
	
	
}
