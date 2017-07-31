/*Author : Diana P Lazar
Date created : 09/19/2015
Copyright@ FindGoose
used for New Company Registration
 */

package com.FG.user.Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.company.CompanyProfile_userCreated;
import com.FG.user.RegisterService;
import com.FG.user.UserRegistrationInfo;

@WebServlet(name="RegisterCompanyServlet", urlPatterns = {"/RegisterCompanyServlet"})
public class RegisterCompanyServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean companyRegisteredStatus = false;
	
	public static boolean getCompanyRegisteredStatus() {
		return companyRegisteredStatus;
	}
	public static void setCompanyRegisteredStatus(boolean companyRegisteredStatus) {
		RegisterCompanyServlet.companyRegisteredStatus = companyRegisteredStatus;
	}
	

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	 processRequestForCompanyRegistration(request, response);
}

protected void processRequestForCompanyRegistration(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		UserRegistrationInfo user = (UserRegistrationInfo)request.getSession().getAttribute("user");
		CompanyProfile_userCreated.associatedUser = user.getUser_email();
		  request.setAttribute("associatedUser",CompanyProfile_userCreated.associatedUser);
	      
	      response.setContentType("text/html;charset=UTF-8");
	 	 String compName = request.getParameter("companyName");//the strings are same as ones in jsp IDs for each text
	 	 String compURL = request.getParameter("companyURL");
	 	 String description = request.getParameter("description");
	 	String location = request.getParameter("location");
	 	 String company_industryType = request.getParameter("industryTypes");
	 	Date comp_FG_RegisteredDate = new Date();
	 	String angelLink = request.getParameter("angelLink");
	 	String blogLink = request.getParameter("blogLink");
	 	String crunchbaseLink = request.getParameter("crunchbaseLink");
	 	String linkedinLink = request.getParameter("linkedinLink");
	 	String TwitterLink = request.getParameter("twitterLink");
	 	 	 	 
	 	String companyContactEmail = request.getParameter("contactEmail");
	 	UserRegistrationInfo currentUser = (UserRegistrationInfo)request.getSession().getAttribute("user");
	 	CompanyProfile_userCreated companyProfile = new CompanyProfile_userCreated(currentUser, compName,compURL,description,location,company_industryType,
	 					  comp_FG_RegisteredDate,angelLink,blogLink,crunchbaseLink,linkedinLink, TwitterLink,
	 					  companyContactEmail);	 	
	 	 try {	
	 		 RegisterService registerService = new RegisterService();
	 		 boolean result = registerService.registerCompany(companyProfile);
	 		 if(result && !getCompanyRegisteredStatus()){
	 			request.setAttribute("isRegistrationsuccess", "true");
	 			request.setAttribute("Registrationsuccess", companyProfile.company_Name + " has been registered successfully!");
	 			 RequestDispatcher dispatcher = request.getRequestDispatcher("/FindGoose_Dashboard.jsp");///FindGoose_Home.jsp");
	 	         dispatcher.forward( request, response);
	 		 }else if(!result){
	 			 request.setAttribute("isRegistrationError", "true");
	 			 request.setAttribute("RegistrationError", "Registration Unsuccessful as the Company attempted for registration already exists.");
	 			 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_NewCompRegistration.jsp");
	 	         dispatcher.forward( request, response);
	 		 }else if(getCompanyRegisteredStatus()){
	 			 request.setAttribute("isRegistrationError", "true");
	 			 request.setAttribute("RegistrationError", "Registration Unsuccessful. Please try again");
	 			 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_NewCompRegistration.jsp");
	 	         dispatcher.forward( request, response);
	 		 }
	 	 } finally {	
	 	 }

	}
	
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	processRequestForCompanyRegistration(request, response);
}
@Override
public String getServletInfo() {
	 return "Short description";
}
}
