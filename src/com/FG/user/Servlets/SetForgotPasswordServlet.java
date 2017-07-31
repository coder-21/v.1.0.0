package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.UserRegistrationInfo;
import com.FG.utils.PasswordResetUtil;
import com.FG.utils.AESencription.AESencrp;

/**
 * Servlet implementation class ForgotPwdServlet
 * Author : Diana
 * Created Date : 02-11-2015
 */
@WebServlet("/SetForgotPasswordServlet")
public class SetForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetForgotPasswordServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
   		 throws ServletException, IOException {	
   	 UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");  
   	 String newpassword = request.getParameter("newpassword");	
   	 String user_prevpassword = user.getUser_prevpassword();
   	 boolean prev2pwds = validateNewPwdNotPrev2Pwds(user.getUser_password(),user_prevpassword,newpassword);
   	 if(prev2pwds)
   	 {
   		 PasswordResetUtil setNewPwdService = new PasswordResetUtil();
   		 setNewPwdService.updateDBwithNewPwd(user, newpassword);
   		 RequestDispatcher dispatcher = request.getRequestDispatcher("/FindGoose_Dashboard.jsp");
   		 dispatcher.forward( request, response);
   	 }else{
   		 request.setAttribute("newPwdIsPrev2Pwds", "true");
   		 RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_setForgotPassword.jsp");
  		 dispatcher.forward( request, response);
   	 }
   }
    
   private boolean validateNewPwdNotPrev2Pwds(String user_password, String user_prevpassword, String newpassword) {
	   String newpasswordEncrypted = "";
	   try {
		   newpasswordEncrypted = AESencrp.encrypt(newpassword);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	   if(user_prevpassword==null && user_password.matches(newpasswordEncrypted))
		return false;
	   else if(user_password.equals(newpasswordEncrypted) || user_password.equals(user_prevpassword))
		return false;
	   else return true;
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

}
