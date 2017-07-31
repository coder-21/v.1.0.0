package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.MyProfileService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;

@WebServlet(name = "MyProfileServlet", urlPatterns = { "/MyProfileServlet" })
public class MyProfileServlet extends HttpServlet{

	/**
	 * @author Suvarna
	 * User Profile servlet implementation
	 */
	private static final long serialVersionUID = 3126270463224997944L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("newPwd")!=null&&request.getParameter("newPwd").length()>0){
			ResetPwdServlet obj=new ResetPwdServlet();
			obj.processRequest(request, response);
		}
		else{
		UserRegistrationInfo userObj = (UserRegistrationInfo) request.getSession().getAttribute("user");
		if (userObj != null) {
			userObj.setUser_firstname(request.getParameter("firstName"));
			userObj.setUser_lastname(request.getParameter("lastName"));
			userObj.setUser_company(request.getParameter("company"));
			userObj.setUser_industries(request.getParameter("industryTypes"));
			new MyProfileService().updateProfile(userObj);
	}
		request.getSession().setAttribute("user", userObj);
		
		
		request.setAttribute("ProfileChanged", "true");
		
		}
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
}
}
