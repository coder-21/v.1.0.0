package com.FG.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Investor_DButil {

	public boolean insertCompaniesIntoINVESTORTableGivenInvestorName(String investorName, String investedCompaniesStr){
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 String queryStr = "update InvestorInfo set investedCompanies = '"+ investedCompaniesStr + "' where investor_name='"+investorName+"'";
			 Query query = session.createQuery(queryStr);
			 @SuppressWarnings("unused")
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
		 return true;
	}
}
