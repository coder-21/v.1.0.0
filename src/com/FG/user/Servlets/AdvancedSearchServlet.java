package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.utils.FG_Util;
//import com.FG.utils.SearchUtil;


/**
 * Servlet implementation of SearchTab 
 * class Author : Suvarna 
 * Created Date :  21-12-2015
 */

@WebServlet(name = "AdvancedSearchServlet", urlPatterns = {"/AdvancedSearchServlet"})
public class AdvancedSearchServlet extends HttpServlet{

	private static final long serialVersionUID = -6263770085582360088L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		try {
			//IndexWrite.getInstance().writeIndex();
	} catch (Exception e) {
		// TODO Auto-generated catch block
	}
//			e.printStackTrace();
//		}
		 processRequest(request, response) ;
		 
	}
	
	@SuppressWarnings("unused")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {	
		

		 String location=request.getParameter("location");
		 String industry=request.getParameter("industry");
		 String revenue=request.getParameter("revenue"); 
		 String technology=request.getParameter("technology");
		 //IndexRead reader= new IndexRead();
//		 String searchText="location:"+location+" AND industry:"+industry+" AND technology:"+technology;
		 //Document[] d=IndexRead.readIndex(searchText);
//		 SearchUtil.setDocumentListFromLuceneSearch(searchText);
		 request.setAttribute("SearchPerformed", "true");
		 FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
				 "/FindGoose_SimpleSearchResults.jsp");
//		 System.out.println("docs found");
//		 for(int i=0;i<d.length;i++){
//			 System.out.println(d[i].get("name"));
//		 }
	}
}