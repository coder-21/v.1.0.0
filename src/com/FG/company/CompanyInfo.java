/*Author : Diana P Lazar
Date created : 28/01/2016
Copyright@ FindGoose
 */

package com.FG.company;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the fg_companytable database table.
 * 
 */
@Entity
@Table(name="fg_companytable")
//@NamedQuery(name="fg_companytable.findAll", query="SELECT f FROM fg_companytable f")
public class CompanyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int cid;

	@Column(name="angel_link")
	private String angelLink;

	@Column(name="blog_link")
	private String blogLink;

	@Column(name="company_link")
	private String companyLink;

	@Column(name="company_logo")
	private String companyLogo;

	@Column(name="crunchbase_link")
	private String crunchbaseLink;

	private String description;

	@Column(name="education_school")
	private String educationSchool;

	@Column(name="education_subject")
	private String educationSubject;

	@Column(name="experience_company")
	private String experienceCompany;

	@Column(name="experience_title")
	private String experienceTitle;

	private int headcount;

	private String industry;

	@Column(name="investment_amount")
	private String investmentAmount;

	@Column(name="investment_date")
	private String investmentDate;

	@Column(name="investment_round")
	private String investmentRound;

	private String investors;

	private String keywords;

	@Column(name="linkedin_link")
	private String linkedinLink;

	private String location;

	private String marketcap;

	private String name;

	@Column(name="pe_ratio")
	private BigDecimal peRatio;

	private String revenue;

	private String technology;

	@Column(name="total_companies")
	private int totalCompanies;

	@Column(name="twitter_link")
	private String twitterLink;

	@Temporal(TemporalType.DATE)
	@Column(name="year_founded")
	private Date yearFounded;

	public CompanyInfo() {
	}

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public String getCompanyLink() {
		return this.companyLink;
	}

	public void setCompanyLink(String companyLink) {
		this.companyLink = companyLink;
	}

	public String getCompanyLogo() {
		return this.companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
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

	public String getEducationSchool() {
		return this.educationSchool;
	}

	public void setEducationSchool(String educationSchool) {
		this.educationSchool = educationSchool;
	}

	public String getEducationSubject() {
		return this.educationSubject;
	}

	public void setEducationSubject(String educationSubject) {
		this.educationSubject = educationSubject;
	}

	public String getExperienceCompany() {
		return this.experienceCompany;
	}

	public void setExperienceCompany(String experienceCompany) {
		this.experienceCompany = experienceCompany;
	}

	public String getExperienceTitle() {
		return this.experienceTitle;
	}

	public void setExperienceTitle(String experienceTitle) {
		this.experienceTitle = experienceTitle;
	}

	public int getHeadcount() {
		return this.headcount;
	}

	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getInvestmentAmount() {
		return this.investmentAmount;
	}

	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getInvestmentDate() {
		return this.investmentDate;
	}

	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
	}

	public String getInvestmentRound() {
		return this.investmentRound;
	}

	public void setInvestmentRound(String investmentRound) {
		this.investmentRound = investmentRound;
	}

	public String getInvestors() {
		return this.investors;
	}

	public void setInvestors(String investors) {
		this.investors = investors;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLinkedinLink() {
		return this.linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMarketcap() {
		return this.marketcap;
	}

	public void setMarketcap(String marketcap) {
		this.marketcap = marketcap;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPeRatio() {
		return this.peRatio;
	}

	public void setPeRatio(BigDecimal peRatio) {
		this.peRatio = peRatio;
	}

	public String getRevenue() {
		return this.revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getTechnology() {
		return this.technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public int getTotalCompanies() {
		return this.totalCompanies;
	}

	public void setTotalCompanies(int totalCompanies) {
		this.totalCompanies = totalCompanies;
	}

	public String getTwitterLink() {
		return this.twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public Date getYearFounded() {
		return this.yearFounded;
	}

	public void setYearFounded(Date yearFounded) {
		this.yearFounded = yearFounded;
	}
	
	@Override
    public String toString() {
        return "Company : " + this.name + ":: CID :" + this.cid;
    }
	
	//for Elastic Search Indexing
	public CompanyInfo(int cid,String name,String revenue,String marketcap,BigDecimal pe_ratio,String location,String technology,String industry,String company_logo,int headcount, String investors, String crunchbase_link,
			String twitter_link, String angel_link, String linkedin_link, String blog_link) {
		this.cid = cid; 
		this.name = name; 
		this.revenue = revenue; 
		this.marketcap = marketcap; 
		this.peRatio = pe_ratio; 
		this.location = location;
		this.technology = technology; 
		this.industry = industry; 
		this.companyLogo = company_logo; 
		this.headcount=headcount; 
		this.investors = investors;
		this.crunchbaseLink = crunchbase_link;
		this.twitterLink=twitter_link; 
		this.angelLink=angel_link; 
		this.linkedinLink=linkedin_link; 
		this.blogLink=blog_link;
	}

}