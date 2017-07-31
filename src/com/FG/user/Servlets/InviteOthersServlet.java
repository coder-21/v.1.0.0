package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;
import com.FG.utils.User_DButil;
import com.FG.utils.emailTempPassword.SendInviteToFG;

@WebServlet(name = "InviteOthersServlet", urlPatterns = { "/InviteOthersServlet" })
public class InviteOthersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String inviteEmail = req.getParameter("inviteEmail");
		User_DButil userUtil = new User_DButil();
		String userEmail=((UserRegistrationInfo) req.getSession().getAttribute("user")).getUser_email();
		UserRegistrationInfo userInvited = (UserRegistrationInfo) userUtil.getUserByEmail(inviteEmail);
		if(userInvited==null){
		SendInviteToFG.send(userEmail, inviteEmail);
		req.setAttribute("UserInvited", "yes");
		req.setAttribute("inviteEmail", inviteEmail);
		req.setAttribute("inviteothers", "true");
				}
		else{
			req.setAttribute("UserInvited", "no");
			req.setAttribute("inviteEmail", inviteEmail);
			req.setAttribute("inviteothers", "true");
		}
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(req, resp, "/FindGoose_Dashboard.jsp");	
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setAttribute("inviteothers", "true");
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(req, resp, "/FindGoose_Dashboard.jsp");	
	}

}
