/*Author : Diana P Lazar
Date created : 02/02/2016
Copyright@ FindGoose
 */

package com.FG.company;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the fg_employeetable_org database table.
 * 
 */
@Entity
@Table(name="fg_employeetable")
//@NamedQuery(name="FgEmployeetableOrg.findAll", query="SELECT f FROM FgEmployeetableOrg f")
public class CompanyEmployeesInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int eid;
	private int cid;
	private String designation;
	private String education;
	private String experience;
	private String linkedin;
	private String name;

	public CompanyEmployeesInfo() {
	}

	public CompanyEmployeesInfo(int cid, String designation, String education, String experience,
			String linkedin, String name) {
//		this.eid = eid;
		this.cid = cid;
		this.designation = designation;		
		this.education = education;
		this.experience = experience;
		this.linkedin = linkedin;
		this.name = name;
	}
	
	public int getEid() {
		return this.eid;
	}

//	public void setEid(int eid) {
//		this.eid = eid;
//	}

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getLinkedin() {
		return this.linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//for Elastic Search Indexing
		public CompanyEmployeesInfo(int eid, int cid, String designation, String education, String experience,
				String linkedin, String name) {
			this.eid = eid;
			this.cid = cid;
			this.designation = designation;		
			this.education = education;
			this.experience = experience;
			this.linkedin = linkedin;
			this.name = name;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.name;
		}

}