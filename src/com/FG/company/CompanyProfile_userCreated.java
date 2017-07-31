/*Author : Diana P Lazar
Date created : 05/05/2016
Copyright@ FindGoose
 */

package com.FG.company;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.FG.user.UserRegistrationInfo;


/**
 * The persistent class for the fg_companytable database table.
 * 
 */
@Entity
@Table(name="fg_companyprofiletable")
//@NamedQuery(name="fg_companytable.findAll", query="SELECT f FROM fg_companyProfiletable f")
public class CompanyProfile_userCreated implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
//    @Column (name="company_id", nullable=false)
//	private String company_id;

	@Column (name="company_Name")
	public String company_Name;

	@Column (name="company_URL")
	public String companyURL;
	
    @Column (name="associated_User")
	public static String associatedUser;

    @Column (name="description")
	private String description;

	@Column(name="comp_location")
	private String compLocation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "compFGregister_date", nullable = false)
	private Date compFGregisterDate;
	
//	public String getCompanyId() {
//		return company_id;
//	}
//
//	public void setCompanyId(String companyId) {
//		this.company_id = companyId;
//	}

	public String getCompanyName() {
		return company_Name;
	}

	public void setCompanyName(String companyName) {
		this.company_Name = companyName;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}

	public static String getAssociatedUser() {
		return associatedUser;
	}

	public static void setAssociatedUser(String associatedUser) {
		CompanyProfile_userCreated.associatedUser = associatedUser;
	}

	public String getCompLocation() {
		return compLocation;
	}

	public void setCompLocation(String compLocation) {
		this.compLocation = compLocation;
	}

	public Date getCompFGregisterDate() {
		return compFGregisterDate;
	}

	public void setCompFGregisterDate(Date compFGregisterDate) {
		this.compFGregisterDate = compFGregisterDate;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getIndustry_Type() {
		return industry_Type;
	}

	public void setIndustry_Type(String industry_Type) {
		this.industry_Type = industry_Type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="angel_link")
	private String angelLink;
	
	@Column(name="blog_link")
	private String blogLink;
	
	@Column(name="crunchbase_link")
	private String crunchbaseLink;

	@Column(name="linkedin_link")
	private String linkedinLink;
	
	@Column(name="twitter_link")
	private String twitterLink;
	
	@Column(name="contact_Email")
	private String contactEmail;
	
	@Column(name="industry_Type")
	private String industry_Type;

	public CompanyProfile_userCreated() {
	}

	public CompanyProfile_userCreated(UserRegistrationInfo currentUser, String compName, String compURL,
			String description,String location, String company_industryType,
			Date comp_FG_RegisteredDate, String angelLink, String blogLink, 
			String crunchbaseLink, String linkedinLink, String twitterLink, 
			String companyContactEmail) {
		associatedUser = currentUser.getUser_email();
		//this.company_id= companyContactEmail + "_"+compName;
		this.company_Name = compName;
		this.companyURL = compURL;
		this.description = description;
		this.compLocation = location;
		this.industry_Type = company_industryType;
		this.angelLink = angelLink;
		this.blogLink=blogLink; 
		this.crunchbaseLink = crunchbaseLink;
		this.linkedinLink=linkedinLink;
		this.twitterLink=twitterLink;
		this.contactEmail = companyContactEmail;
		this.compFGregisterDate = comp_FG_RegisteredDate;
	}

	public String getAngelLink() {
		return this.angelLink;
	}

	public void setAngelLink(String angelLink) {
		this.angelLink = angelLink;
	}

	public String getBlogLink() {
		return this.blogLink;
	}

	public void setBlogLink(String blogLink) {
		this.blogLink = blogLink;
	}

	public String getCrunchbaseLink() {
		return this.crunchbaseLink;
	}

	public void setCrunchbaseLink(String crunchbaseLink) {
		this.crunchbaseLink = crunchbaseLink;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkedinLink() {
		return this.linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public String getTwitterLink() {
		return this.twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

}