
/**
 * Author : Diana
 * Created Date : 19-11-2015
 * This class retrieves Investors from the Company table and inserts 
 * the related companies for Investors in the Investors table in the DB
 */
package com.FG.investor;

import java.util.ArrayList;
import java.util.List;

import com.FG.utils.Company_DButil;
import com.FG.utils.Investor_DButil;
import com.FG.utils.StringUtil;

public class CreateInsertInvestors {
	
	public static void main(String[] args) {
		
		Company_DButil company_DButil = new Company_DButil();
		Investor_DButil investor_DButil = new Investor_DButil();
		List<String> investorsList = company_DButil.getInvestorsFromCOMPANYTable();
		String[] investorsSplitStringArray = investorsList.toString().replace(";",",").split(",");//investorsArrayStr.split(";");

		List<String> finalList = new ArrayList<String>();
		String investedCompaniesStr = "";
		for(String val : investorsSplitStringArray) 
		{
			val = val.replace("[", "");val = val.replace("]", "");
		  if(!(finalList.contains(val)) && !(val.equals(" "))) {
			val=val.substring(2);
		    finalList.add(val.trim());
//		    System.out.println("Investor Name : "+val);
		    List<String> InvestedCompanies = company_DButil.getCompaniesGivenTheInvestorfromCOMPANYTable(val);
		    investedCompaniesStr =  StringUtil.listToStrWithSemiColonSeperators(InvestedCompanies);
//		    System.out.println("Invested Companies : " +investorsStr);
		    investor_DButil.insertCompaniesIntoINVESTORTableGivenInvestorName(val,investedCompaniesStr);
		  }
		}
		}
		

}
