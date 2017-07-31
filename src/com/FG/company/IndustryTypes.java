/*Author : Diana P Lazar
Date created : 24/03/2016
Copyright@ FindGoose
 */

package com.FG.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.utils.HibernateUtil;

@Entity
@Table(name="fg_industrytypes")
public class IndustryTypes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)	
	private int industryTypeID;
	public IndustryTypes(String str) {
		this.industryType = str;// TODO Auto-generated constructor stub
	}
	public IndustryTypes() {
		// TODO Auto-generated constructor stub
	}
	public int getIndustryTypeID() {
		return industryTypeID;
	}
	public void setIndustryTypeID(int industryTypeID) {
		this.industryTypeID = industryTypeID;
	}

	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryTypes(String industryType) {
		this.industryType = industryType;
	}

	private String industryType;
	
	@Transient
	public static String[] types = {"Agriculture, Fishing, Forestry & Hunting",
								"Clean Energy Technology",
								"Construction & Real Estate",
								"Consumer Products",
								"Energy & Utilities (Traditional)",
								"Financial",
								"Healthcare",
								"Hospitality & Leisure",
								"Internet",
								"Manufacturing & Industrial",
								"Media",
								"Retail",
								"Services",
								"Social Services & Organizations",
								"Technology",
								"Transportation, Warehousing & Wholesale Distribution"};
	
	@SuppressWarnings("unchecked")
	public static List<IndustryTypes> getListOfIndustryTypes(){
		 List<IndustryTypes> list = new ArrayList<IndustryTypes>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery("from IndustryTypes").list();					
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
