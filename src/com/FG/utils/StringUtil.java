/**
 * Author : Diana
 * Created Date : 18-11-2015
 */
package com.FG.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringUtil {

	public static String listToStrWithSemiColonSeperators(List<String> source) {
	    StringBuilder sb = new StringBuilder();
	    for (String s : source) {			
	    	sb.append(s);
	    	sb.append(";");
		}
	    return sb.toString();
	}
	
//	public static String eliminateDupStrInString(String[] investorIndividual){
//	
//	List<String> finalList = new ArrayList<String>();
//	String investorsStr = "";
//	for(String val : investorIndividual) 
//	{
//	  if(!finalList.contains(val)) {
//	    finalList.add(val);
//	    System.out.println("___________________________________________________________");
//	    System.out.println("Investor Name : "+val);
//	    List<String> InvestedCompanies = company_DButil.getCompaniesGivenTheInvestor(val);
//	    investorsStr =  StringUtil.Concat(InvestedCompanies);
//	    System.out.println("Invested Companies : " +investorsStr);
//	    investor_DButil.insertCompaniesIntoInvestorTableGivenInvestorName(val,investorsStr);
//	  }
//	}
//	}
	
	public static Date processStringToDate(String dateStr)
	{
		//surround below line with try catch block as below code throws checked exception
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static BigDecimal parseStr2BigDecimal(String string) {
		// Create a DecimalFormat that fits your requirements
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// parse the string
		BigDecimal bigDecimal;
		try {
			bigDecimal = (BigDecimal) decimalFormat.parse("10,692,467,440,017.120");
		
		return bigDecimal;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
