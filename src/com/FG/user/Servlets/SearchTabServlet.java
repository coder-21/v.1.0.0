package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.company.CompanyInfo;
import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;

import io.searchbox.client.JestClient;

/**
 * Servlet implementation of SearchTab 
 * class Author : Suvarna 
 * Created Date :  16-11-2015
 */

@WebServlet(name = "SearchTabServlet", urlPatterns = {"/BasicSearchResults"})
public class SearchTabServlet extends HttpServlet{

	private static final long serialVersionUID = -6263770085582360088L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {	
		try {
//			IndexWrite.getInstance().writeIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 String searchText=request.getParameter("searchText");
		 request.getSession().setAttribute("searchText", searchText);
//		 String passedToURL = lucene_PassRequest_n_returnNextJSP(request, searchText);
		 String passedToURL = es_PassRequest_n_returnNextJSP(request, searchText);
		 FG_Util fg_util = new FG_Util(); 
		 fg_util.passSearchResultsToJSP(request, response,passedToURL);
	}

	/**
	 * @param request
	 * @param searchText
	 * @return
	 */
//	@SuppressWarnings("unused")
//	private String lucene_PassRequest_n_returnNextJSP(HttpServletRequest request, String searchText) {
//		SearchUtil.setDocumentListFromLuceneSearch(searchText);
//		 if(SearchUtil.isDocumentNull == false)
//			 request.setAttribute("SearchPerformed", "true");
//		 else request.setAttribute("SearchReturnedNull", "true");
//		 UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
//		 String passedToURL = "";
//		 if(user == null)
//		 {
//			 passedToURL ="/FindGoose_SimpleSearchResults.jsp";
//		 }else passedToURL = "/FindGoose_Dashboard.jsp";
//		return passedToURL;
//	}
	
	/**
	 * @param request
	 * @param searchText
	 * @return
	 */
	private String es_PassRequest_n_returnNextJSP(HttpServletRequest request, String searchText) {		 
		 ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
		 JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
		 try {
			List<CompanyInfo> companybasicsearchResults =(List<CompanyInfo>) esUtil.getCompanySearchResultsFromIndexedCloudData(jestClient, searchText);
			if(companybasicsearchResults==null) 
			{
				System.out.println("No listings on this search(companyNames)"); //$NON-NLS-1$
				request.setAttribute("SearchReturnedNull", "true");
			}
			else{
				request.setAttribute("companybasicsearchResults", companybasicsearchResults);
				request.setAttribute("SearchPerformed", "true");
				System.out.println("Companies relative to keyword : " +searchText+ " are:");
				for (CompanyInfo companyName : companybasicsearchResults) {
				    System.out.println(companyName);//Calls the toString() of CompanyInfo.
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
		 String passedToURL = "";
		 if(user == null)
		 {
			 passedToURL ="/FindGoose_SimpleSearchResults.jsp";
		 }else passedToURL = "/FindGoose_Dashboard.jsp";
		return passedToURL;
	}
}
