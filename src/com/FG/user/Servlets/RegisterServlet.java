package com.FG.user.Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.RegisterService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.AESencription.AESencrp;

/*Author : Diana P Lazar
Date created : 10/20/2015
Copyright@ FindGoose
used for New User Registration
*/

@WebServlet(name="RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean userRegisteredStatus = false;
	public static boolean getuserRegisteredStatus() {
		return userRegisteredStatus;
	}
	
	public static void setuserRegisteredStatus(boolean userRegisteredStatus) {
		RegisterServlet.userRegisteredStatus = userRegisteredStatus;
	}
	
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	 response.setContentType("text/html;charset=UTF-8");
//	 PrintWriter out = response.getWriter();
	 String fName = request.getParameter("firstName");//the strings are same as ones in jsp IDs for each text
	 String lName = request.getParameter("lastName");
	 String email = request.getParameter("email");
	 String password = request.getParameter("passwordSignup");
	 String user_industries = request.getParameter("industryTypes");
	 String passwordEnc = "";
	 Date user_RegisteredDate = new Date();
	 try {
		passwordEnc = AESencrp.encrypt(password);
//		System.out.println("passwordEnc : "+passwordEnc);
	 } catch (Exception e) {
		e.printStackTrace();
	 }
	 String company = request.getParameter("company");
	 String newsletter = request.getParameter("newsletter");
	    if (request.getParameter("newsletter") == null){
	    	newsletter = "no";
	    }
	 UserRegistrationInfo user = new UserRegistrationInfo(fName,lName, email, passwordEnc,company,user_RegisteredDate,"BASIC",newsletter,"", user_industries);
	
	 try {	
		 RegisterService registerService = new RegisterService();
		 boolean result = registerService.register(user);
//		 if(result && !getuserRegisteredStatus()){
//			 request.getSession().setAttribute("user", user);
//			 RequestDispatcher dispatcher = request.getRequestDispatcher("/FindGoose_Home.jsp");
//	         dispatcher.forward( request, response);
//		 }
		 
		 if(result && !getuserRegisteredStatus()){
			request.getSession().setAttribute("user", user);
 			request.setAttribute("isRegistrationsuccess", "true");
 			request.setAttribute("Registrationsuccess", user.getUser_firstname() + " " + user.getUser_lastname() +", you(" + user.getUser_email()+ ") have been registered successfully!<br> Welcome to FindGoose!");
 			 RequestDispatcher dispatcher = request.getRequestDispatcher("/FindGoose_Dashboard.jsp");///FindGoose_Home.jsp");
 	         dispatcher.forward( request, response);
 		 }
		 
		 else if(!result){
			 request.setAttribute("isRegistrationError", "true");
			 request.setAttribute("RegistrationError", "Registration Unsuccessful as the user with the attempted email "+email+", already exists.");
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_Registration.jsp");
	         dispatcher.forward( request, response);
		 }else if(getuserRegisteredStatus()){
			 request.setAttribute("isRegistrationError", "true");
			 request.setAttribute("RegistrationError", "Registration Unsuccessful. Please try again");
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_Registration.jsp");
	         dispatcher.forward( request, response);
		 }
	 } finally {	
	 }
}
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	processRequest(request, response);
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
