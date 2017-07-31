/*Author : Diana P Lazar
Date created : 18/11/2016
Copyright@ FindGoose
 */

package com.FG.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.searchbox.annotations.JestId;

@Entity
@Table(name="fg_investmenttable")
public class InvestmentInfo4ESIndexing {
	
	@JestId
	@Id 
    @Column (name="iid",unique=true, nullable=false)
	private int iid;
	private int cid;
	private String investee_name;
	private String amount;
	private String round;
	private String investor_type;
	private String year_founded;
	private int headcount;
	private String irevenue;
	public InvestmentInfo4ESIndexing(int iid, int cid, String amount, String round, String investor_type, String year_founded, int headcount, String irevenue) {
		this.iid = iid;
		this.cid = cid;
		this.amount = amount;
		this.round=round;
		this.investor_type = investor_type;
		this.year_founded = year_founded;
		this.headcount = headcount;
		this.irevenue = irevenue;
	}
	
	public String getInvestor_type() {
		return investor_type;
	}

	public void setInvestor_type(String investor_type) {
		this.investor_type = investor_type;
	}

	public String getYear_founded() {
		return year_founded;
	}

	public void setYear_founded(String year_founded) {
		this.year_founded = year_founded;
	}

	public int getHeadcount() {
		return headcount;
	}

	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}

	public String getIrevenue() {
		return irevenue;
	}

	public void setIrevenue(String irevenue) {
		this.irevenue = irevenue;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getInvestee_name() {
		return investee_name;
	}

	public void setInvestee_name(String investee_name) {
		this.investee_name = investee_name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public InvestmentInfo4ESIndexing() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.investee_name;
	}
}
