/*Author : Diana P Lazar
Date created : 28/01/2016
Copyright@ FindGoose
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

/**
 * Servlet implementation class CompanyProfileServlet
 */
@WebServlet("/CompanyProfile")
public class CompanyProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
   		 throws ServletException, IOException {
    	try {
    		  request.setAttribute("CompanyProfileSelected","true");
    	      String selectedCompanyName = request.getParameter("selectedCompanyName");
//    	      String selectedCompCIDstr = request.getParameter("selectedCompanyCID");
//    	      Document selectedCompDocument = SearchUtil.getDocumentForSelectedCompany(selectedCompanyName);
//    	      request.setAttribute("selectedCompDocument",selectedCompDocument);
//   			  SearchUtil.setDocumentListFromLuceneSearch(selectedCompCIDstr);
//   			  if(!SearchUtil.isDocumentNull)
//    	       request.setAttribute("employeesOfSelCompNotNull", "true");
//   			  SearchUtil.setInvestmentsDocumentListFromLuceneSearch(selectedCompCIDstr);
//   			if(!SearchUtil.isInvestmentsDocumentNull)
//     	       request.setAttribute("InvestmentsOfSelCompNotNull", "true");
    	      
    	      //ES upgrade - start
	    	    ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
	    		JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
	    		CompanyInfo companyInfo4SelectedCompanyName = esUtil.getCompanyInfo4SelectedCompanyFromIndexedCloudData(jestClient, selectedCompanyName);
	    		if(companyInfo4SelectedCompanyName!=null)
	    		{
	    			request.setAttribute("companyInfo4SelectedCompanyName", companyInfo4SelectedCompanyName); //"selectedCompDocument"

	    			List<CompanyEmployeesInfo> compEmployeesList =(List<CompanyEmployeesInfo>) esUtil.getEmployeesSearchResultsFromIndexedCloudData(jestClient, selectedCompanyName);
	    			if(compEmployeesList.size()>0)
	    			{
	    				request.setAttribute("EmployeesList4companyInfoSelected", compEmployeesList);
	    				request.setAttribute("Employees4companyInfoSelectedIsNotnull", "true");//"employeesOfSelCompNotNull"
	    			}
	    			
	    			List<InvestmentInfo4ESIndexing> compInvestmentsList =(List<InvestmentInfo4ESIndexing>) esUtil.getInvestmentsSearchResultsFromIndexedCloudData(jestClient, selectedCompanyName);
	    			if(compInvestmentsList.size()>0)
	    			{
	    				request.setAttribute("InvestmentsList4companyInfoSelected", compInvestmentsList);
	    				request.setAttribute("Investments4companyInfoSelectedIsNotnull", "true");//"InvestmentsOfSelCompNotNull"
	    			}
	    			
	    			List<InvestorInfo> compInvestorsList = (List<InvestorInfo>)esUtil.getInvestorsSearchResultsFromIndexedCloudData(jestClient, selectedCompanyName);
	    			if(compInvestorsList.size()>0)
	    			{
	    				request.setAttribute("InvestorsList4companyInfoSelected", compInvestorsList);
	    				request.setAttribute("Investors4companyInfoSelectedIsNotnull", "true");
	    				
	    			}
	    		}
	    	  //ES upgrade - end
	    		
    	      FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
   }
    
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
   		 throws ServletException, IOException {
   	 		processRequest(request, response);
   }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
