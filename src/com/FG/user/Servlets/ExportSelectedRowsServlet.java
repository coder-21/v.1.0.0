/*Author : Diana P Lazar
Date created : 1/05/2016
Copyright@ FindGoose
 */

package com.FG.user.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.company.CompanyInfo;
import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.utils.FG_Util;

import io.searchbox.client.JestClient;


/**
 * Servlet implementation class ExportSelectedRowsServlet
 */
@WebServlet(name = "ExportSelectedRowsServlet", urlPatterns = {"/ExportSelectedList"})
public class ExportSelectedRowsServlet extends HttpServlet {
	private static final long serialVersionUID = -2133134637529778097L;

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				 throws ServletException, IOException {
			 processRequest(request, response);
		}
		
		@SuppressWarnings("unchecked")
		protected void processRequest(HttpServletRequest request, HttpServletResponse response)
				 throws ServletException, IOException {	
			try {
				request.setAttribute("SearchPerformed", "true");
		        String arr = request.getParameter("hiddenSelectedCompanies4Export");
		       
		        String searchText=request.getParameter("searchText");
		        if(searchText==null) 
		        	searchText = (String) request.getSession().getAttribute("searchText");
		        ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
		        JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
		        
		        if(arr=="") 
		        {		   
		    		List<CompanyInfo> companybasicsearchResults =(List<CompanyInfo>) esUtil.getCompanySearchResultsFromIndexedCloudData(jestClient, searchText);
		    		if(companybasicsearchResults!=null)
		    			request.setAttribute("companybasicsearchResults", companybasicsearchResults);
		    		
			        request.setAttribute("isListNotSelected4Export", "true");
			        FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
			        		"/FindGoose_Dashboard.jsp");
		        }
		        else
		        {
		        	String[] arrSplit = arr.split(",");
		        	for (int i = 0; i < arrSplit.length; i++) {
						arrSplit[i]=arrSplit[i].trim();
					}
			        List<CompanyInfo> selectedCompanyList4Export = new ArrayList<CompanyInfo>();
					List<CompanyInfo> resultedCompanyList = (List<CompanyInfo>)request.getSession().getAttribute("searchResultTable");
					request.removeAttribute("searchResultTable");
			        for (int i = 0; i < resultedCompanyList.size(); i++) {
			        	for (int j = 0; j < arrSplit.length; j++) {							
			        		if(arrSplit[j].contains(resultedCompanyList.get(i).getName()))
			        		{
			        			selectedCompanyList4Export.add(resultedCompanyList.get(i));
			        		}
						}
					}
					request.setAttribute("selectedCompanyList4Export", selectedCompanyList4Export);
			        FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
			        		"/FindGoose_searchResultExcel.jsp");
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}