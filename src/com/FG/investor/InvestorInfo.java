/*Author : Diana P Lazar
Date created : 28/01/2016
Copyright@ FindGoose
 */

package com.FG.investor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_investortable")
public class InvestorInfo {
	
	@Id 
    @Column (name="investor_name",unique=true, nullable=false)
	private String investor_name;
	private String investedCompanies;
	private String investorType;
	public InvestorInfo(String investor_name, String investedCompanies, String investorType) {
		this.investor_name = investor_name;
		this.investedCompanies = investedCompanies;
		this.investorType=investorType;
	}
	
	public InvestorInfo() {
		// TODO Auto-generated constructor stub
	}
	public String getInvestor_name() {
		return investor_name;
	}
	public void setInvestor_name(String investor_name) {
		this.investor_name = investor_name;
	}
	public String getInvestedCompanies() {
		return investedCompanies;
	}
	public void setInvestedCompanies(String investedCompanies) {
		this.investedCompanies = investedCompanies;
	}
	public String getInvestorType() {
		return investorType;
	}
	public void setInvestorType(String investorType) {
		this.investorType = investorType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.investor_name;
	}
}
