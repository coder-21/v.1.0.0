/**
 * Servlet implementation class ForgotPwdServlet
 * Author : Diana
 * Created Date : 27-10-2015
 * Copyright@ FindGoose
 */

package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.ForgotPwdService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.User_DButil;
import com.FG.utils.emailTempPassword.SendTempPassword;

@WebServlet(name = "ForgotPwdServlet", urlPatterns = {"/ForgotPwdServlet"})
public class ForgotPwdServlet extends HttpServlet {
       
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {	
		 String email = request.getParameter("userEmail");
		 if(email!=null) 
		 {
			 ForgotPwdService forgotPwdService = new ForgotPwdService();
			 boolean result = forgotPwdService.userExists(email);
			 if(result == true){
				 request.setAttribute("EmailInvalid", "false");
				 request.setAttribute("infoMessage", "A temporary password has been emailed to you.");
				 String userTempPwd = getTempPwd(email);
				 SendTempPassword.send(email, userTempPwd);
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_forgotPassword.jsp");
		         dispatcher.forward( request, response);
			 }
			 else{
				 request.setAttribute("EmailInvalid", "true");
				 request.setAttribute("errorMessage", "The email doesn't seem to be registered in our system."); 
		         RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_forgotPassword.jsp");
		         dispatcher.forward( request, response);
	//			 response.sendRedirect("Findgoose_Login.jsp");
			 }
		 }
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("userEmail")!=null) 
			processRequest(request, response);
	}
	
	private static String getTempPwd(String userEmail) {
		User_DButil user_dbutil = new User_DButil();
	  	UserRegistrationInfo user = user_dbutil.getUserByEmail(userEmail);
	  	if(user!=null && user.getUser_email().equals(userEmail)){	  		
	  		String userTempPwd = "=@$" + user.getUser_password();	  	
	  		return userTempPwd;
	  	}else return "";
	}

}
