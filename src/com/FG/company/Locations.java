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
@Table(name="fg_locations")
public class Locations implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)	
	private int locationsID;
	public Locations(String str) {
		this.location = str;// TODO Auto-generated constructor stub
	}
	public Locations() {
		// TODO Auto-generated constructor stub
	}
	public int getIndustryTypeID() {
		return locationsID;
	}
	public void setIndustryTypeID(int locationsID) {
		this.locationsID = locationsID;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	private String location;
	
	@Transient
	public static String[] locations = {"Alabama-United States",
			"Alaska-United States",
			"Arizona-United States",
			"Arkansas-United States",
			"California-United States",
			"Colorado-United States",
			"Connecticut-United States",
			"Delaware-United States",
			"Washington, D.C.-United States",
			"Florida-United States",
			"Georgia-United States",
			"Hawaii-United States",
			"Idaho-United States",
			"Illinois-United States",
			"Indiana-United States",
			"Iowa-United States",
			"Kansas-United States",
			"Kentucky-United States",
			"Louisiana-United States",
			"Maine-United States",
			"Maryland-United States",
			"Massachusetts-United States",
			"Michigan-United States",
			"Minnesota-United States",
			"Mississippi-United States",
			"Missouri-United States",
			"Montana-United States",
			"Nebraska-United States",
			"Nevada-United States",
			"New Hampshire-United States",
			"New Jersey-United States",
			"New Mexico-United States",
			"New York-United States",
			"North Carolina-United States",
			"North Dakota-United States",
			"Ohio-United States",
			"Oklahoma-United States",
			"Oregon-United States",
			"Pennsylvania-United States",
			"Puerto Rico-United States",
			"Rhode Island-United States",
			"South Carolina-United States",
			"South Dakota-United States",
			"Tennessee-United States",
			"Texas-United States",
			"Utah-United States",
			"Vermont-United States",
			"Virginia-United States",
			"Washington-United States",
			"West Virginia-United States",
			"Wisconsin-United States",
			"Wyoming-United States",
			"Alberta-Canada",
			"British Columbia-Canada",
			"Manitoba-Canada",
			"New Brunswick-Canada",
			"Newfoundland and Labrador-Canada",
			"Northwest Territories-Canada",
			"Nova Scotia-Canada",
			"Nunavut-Canada",
			"Ontario-Canada",
			"Prince Edward Island-Canada",
			"Quebec-Canada",
			"Saskatchewan-Canada",
			"Yukon-Canada",
			"UK-Europe",
			"Belgium-Europe",
			"Croatia-Europe",
			"Czech Republic-Europe",
			"Denmark-Europe",
			"Finland-Europe",
			"France-Europe",
			"Germany-Europe",
			"Hungary-Europe",
			"Iceland-Europe",
			"Ireland-Europe",
			"Italy-Europe",
			"Luxembourg-Europe",
			"Netherlands-Europe",
			"Norway-Europe",
			"Poland-Europe",
			"Portugal-Europe",
			"Romania-Europe",
			"Serbia-Europe",
			"Spain-Europe",
			"Sweden-Europe",
			"Europe (excluding UK)",
			"Australia-AsiaPacific",
			"China-AsiaPacific",
			"Hong Kong-AsiaPacific",
			"India-AsiaPacific",
			"Indonesia-AsiaPacific",
			"Japan-AsiaPacific",
			"Singapore-AsiaPacific",
			"South Korea-AsiaPacific",
			"Taiwan-AsiaPacific",
			"Thailand-AsiaPacific",
			"Asia-Pacific (excluding India)",												
			"Brazil-LatinAmerica",
			"Colombia-LatinAmerica",
			"Latin America (excluding Brazil)",
			"Middle East; Africa (all countries)",
			"Israel",
			"Turkey",};
	
	@SuppressWarnings("unchecked")
	public static List<Locations> getListOfLocations(){
		 List<Locations> list = new ArrayList<Locations>();
		 Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 list = session.createQuery("from Locations").list();					
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
