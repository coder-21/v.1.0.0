/*Author : Diana P Lazar
Date created : 05/05/2016
Copyright@ FindGoose
 */
package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.utils.FG_Util;


@WebServlet(name="DashboardServlet", urlPatterns = {"/MyDashboard"})
public class DashboardServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	}
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
		processRequest(request, response);
}

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	try {
		FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
	} catch (Exception e) {
		e.printStackTrace();
	}
}
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	 processRequest(request, response);
}
@Override
public String getServletInfo() {
	 return "Short description";
}
}
