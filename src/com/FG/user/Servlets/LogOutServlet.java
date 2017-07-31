/*Author : Diana P Lazar
Date created : 02 Mar 2015
Copyright@ FindGoose
 */
package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.utils.FG_Util;


@WebServlet(name = "LogOut", urlPatterns = {"/LogOut"})
public class LogOutServlet extends HttpServlet {

private static final long serialVersionUID = -5048348784961476453L;


@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
		processRequest(request, response);
}

@Override
public String getServletInfo() {
	 return "Short description";
}

protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	request.removeAttribute("user");
	request.getSession().removeAttribute("user");
	request.getSession().removeAttribute("LoginSeeker");
	request.getSession().invalidate();
	request.setAttribute("UserLoggedOut", "true");
	FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/Findgoose_Login.jsp");
}

@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		processRequest(req, resp);
	}
}
