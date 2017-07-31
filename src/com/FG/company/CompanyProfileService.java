/*Author : Diana P Lazar
Date created : 02/03/2016
Copyright@ FindGoose
 */


package com.FG.company;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.utils.HibernateUtil;

public class CompanyProfileService {

	@SuppressWarnings("unchecked")
	public static List<CompanyEmployeesInfo> getEmployeesForCompany(int selectedCompanyId) {
			Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 List<CompanyEmployeesInfo> u = null;
			 try{
				 tx = session.getTransaction();
				 tx.begin();
				 
				 Criteria criteria = session.createCriteria(CompanyEmployeesInfo.class);
				 criteria.add(Restrictions.eq("cid", selectedCompanyId));

				 u = (List<CompanyEmployeesInfo>)criteria.list();
				 tx.commit();
			 }catch(Exception ex){
				 ex.printStackTrace();
				 if(tx!=null){
					 tx.rollback();
				 }
			 }finally{
				 session.close();
			 }
			 return u;
	}
	
	public static int getCIDbyCompanyName(String selectedCompanyName) {
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 CompanyInfo companyInfo = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("from CompanyInfo where name='"+selectedCompanyName+"'");
			 companyInfo = (CompanyInfo)query.uniqueResult();
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return companyInfo.getCid();
	}

}
