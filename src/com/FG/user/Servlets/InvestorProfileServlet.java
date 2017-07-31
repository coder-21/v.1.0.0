/*Author : Diana P Lazar
Date created : 18/03/2016
Copyright@ FindGoose
 */

package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.investor.InvestorInfo;
import com.FG.utils.FG_Util;

import io.searchbox.client.JestClient;

/**
 * Servlet implementation class CompanyProfileServlet
 */
@WebServlet("/InvestorProfile")
public class InvestorProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvestorProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
   		 throws ServletException, IOException {
    	try {
    		  request.setAttribute("InvestorProfileSelected","true");
    	      String selectedInvestor = request.getParameter("selectedInvestorName");
//    	      Document selectedInvestorDocument = SearchUtil.getDocumentForSelectedInvestor(selectedInvestor);
//    	      request.setAttribute("selectedInvestorDocument",selectedInvestorDocument);

//   			...if(!SearchUtil.isInvestmentsDocumentNull)
//     	       request.setAttribute("InvestmentsOfSelCompNotNull", "true");
    	      
    	    //ES upgrade - start
	    	    ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
	    		JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
	    		InvestorInfo investorInfo4SelectedInvestorName = (InvestorInfo) esUtil.getInvestorInfo4SelectedCompanyInvestorFromIndexedCloudData(jestClient, selectedInvestor);
	    		request.setAttribute("selectedInvestorInfoProfile",investorInfo4SelectedInvestorName);//"selectedInvestorDocument"
//	    		if(investorInfo4SelectedInvestorName!=null)
//	    		{
//	    			List<InvestorInfo> compInvestorsList =(List<InvestorInfo>) esUtil.getInvestorsSearchResultsFromIndexedCloudData(jestClient, selectedInvestor);
//	    			if(compInvestorsList!=null)
//	    				request.setAttribute("Investors4companyInfoSelectedIsNotnull", "true");
//	    		}
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
