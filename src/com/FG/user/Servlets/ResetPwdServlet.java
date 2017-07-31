package com.FG.user.Servlets;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FG.user.ResetPwdService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;
import com.FG.utils.User_DButil;

/**
 * Reset Password servlet implementation class Author : Suvarna Created Date :
 * 03-11-2015
 */
@WebServlet(name = "ResetPwdServlet", urlPatterns = {"/ResetPwdServlet"})
public class ResetPwdServlet extends HttpServlet {

	private static final long serialVersionUID = -2436132664546016140L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currPwd = request.getParameter("currPwd");
		String newPwd = request.getParameter("newPwd");
		Boolean prevPassResult;
		ResetPwdService serviceObj = new ResetPwdService();
		UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
		boolean result = serviceObj.authenticate(user, currPwd);
		
		if (result == false) {
			request.setAttribute("authenticateResult", "false");
			request.setAttribute("errorMessage", "Current password entered is incorrect");
			return;
		}
		String user_prevpassword = user.getUser_prevpassword();
		prevPassResult = serviceObj.checkWithPrevPwds(user.getUser_password(), user_prevpassword, newPwd);
		if (prevPassResult == false) {
			request.setAttribute("PrevPassResult", "false");
			return;
		}
		result = serviceObj.changePassword(user, newPwd);

		if (result == true){
			User_DButil utilObj=new User_DButil();
			user= utilObj.getUserByEmail(user.getUser_email());
			request.getSession().setAttribute("user", user);
			request.setAttribute("ResetPwdResult", "true");
		}
		else
			request.setAttribute("ResetPwdResult", "false");

		return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
	}

}
