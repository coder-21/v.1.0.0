/*
 * @Author: Suvarna
Copyright@ FindGoose

@Diana : Migration from lucene to Elasticsearch
 */

package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.company.CompanyEmployeesInfo;
import com.FG.company.CompanyInfo;
import com.FG.company.InvestmentInfo4ESIndexing;
import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.investor.InvestorInfo;
import com.FG.utils.FG_Util;

import io.searchbox.client.JestClient;

@WebServlet(name = "InterestsServlet", urlPatterns = { "/InterestsServlet" })

public class InterestsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5046106439934119380L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("InterestsClicked", "true");
		if (req.getParameter("company") != null) {
			processRequest(req, resp);
		}
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(req, resp, "/FindGoose_Dashboard.jsp");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setAttribute("CompanyProfileSelected", "true");
			request.removeAttribute("InterestsClicked");
			String selectedCompany = request.getParameter("company");
			
			//ES upgrade - start
    	    ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
    		JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
    		CompanyInfo companyInfo4SelectedCompanyName = esUtil.getCompanyInfo4SelectedCompanyFromIndexedCloudData(jestClient, selectedCompany);
    		if(companyInfo4SelectedCompanyName!=null)
    		{
    			request.setAttribute("companyInfo4SelectedCompanyName", companyInfo4SelectedCompanyName); //"selectedCompDocument"

    			List<CompanyEmployeesInfo> compEmployeesList = esUtil.getEmployeesSearchResultsFromIndexedCloudData(jestClient, selectedCompany);
    			if(compEmployeesList!=null)
    			{
    				request.setAttribute("EmployeesList4companyInfoSelected", compEmployeesList);
    				request.setAttribute("Employees4companyInfoSelectedIsNotnull", "true");//"employeesOfSelCompNotNull"
    			}
    			
    			List<InvestmentInfo4ESIndexing> compInvestmentsList = esUtil.getInvestmentsSearchResultsFromIndexedCloudData(jestClient, selectedCompany);
    			if(compInvestmentsList!=null)
    			{
    				request.setAttribute("InvestmentsList4companyInfoSelected", compInvestmentsList);
    				request.setAttribute("Investments4companyInfoSelectedIsNotnull", "true");//"InvestmentsOfSelCompNotNull"
    			}
    			
    			List<InvestorInfo> compInvestorsList = esUtil.getInvestorsSearchResultsFromIndexedCloudData(jestClient, selectedCompany);
    			if(compInvestorsList!=null)
    			{
    				request.setAttribute("InvestorsList4companyInfoSelected", compInvestorsList);
    				request.setAttribute("Investors4companyInfoSelectedIsNotnull", "true");
    				
    			}
    		}
    	  //ES upgrade - end
    		
	      //FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
