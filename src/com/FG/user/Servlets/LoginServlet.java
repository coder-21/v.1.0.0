/*Author : Diana P Lazar
Date created : 10/20/2015
Copyright@ FindGoose

@ Suvarna:
 *My Team servlet call added
 *My Task servlet call added
 *My Task servlet call added
 *Dashboard refresh added
 */

package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.LoginService;
import com.FG.user.UserRegistrationInfo;
import com.FG.user.UserTeamTableInfo;
import com.FG.utils.CookieUtil;
import com.FG.utils.Messages;
import com.FG.utils.MyTeam_DButil;
import com.FG.utils.User_DButil;


@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = -5048348784961476453L;
	private final String COOKIE_TOKEN =Messages.getString("UTILS.cookieToken");
@SuppressWarnings("unused")
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {	
	if(request.getSession().getAttribute("user")==null){
	 String email = request.getParameter("email");	
	 String password = request.getParameter("password");
	 boolean result = false;
          
	 User_DButil user_DBUtil = new User_DButil();
	 UserRegistrationInfo user = user_DBUtil.getUserByEmail(email);
	 
	 String rememberMe = request.getParameter("rememberMeCheck");
	 LoginService loginService = new LoginService();
	 if(password.startsWith("=@$"))
	 {
		 result = loginService.authenticateWithTempPwd(user, email, password);
	 }else{
		 result = loginService.authenticate(user,email, password);
	 }
	 
	 if(result == true){
		 if(rememberMe!=null)
		 {
			 String cookieToken = CookieUtil.addCookieToResponse_and_DB(request, response, user);
			 boolean rememberMeSetStatus = user_DBUtil.saveUserRememberMeToken(user, cookieToken);
			 String str = CookieUtil.readCookieValue(request, COOKIE_TOKEN);
		 }
		 else 
		 {
			 CookieUtil.removeCookieFromResponse_and_DB(request, response,COOKIE_TOKEN);
			 boolean rememberMeSetStatus = user_DBUtil.saveUserRememberMeToken(user, "");
			
		 }
		 request.getSession().setAttribute("user", user);
		 request.getSession().setAttribute("InitialLoad",true);
		 UserRegistrationInfo.setCurrentUserFromSession(user);
		 String callingURL = null;
		 
		 initialiseDashboardDashletContents(request, response, user);
		 if(request.getSession().getAttribute("LoginSeeker") !=null) 
			 	callingURL =request.getSession().getAttribute("LoginSeeker").toString();
		 if(password.startsWith("=@$")) response.sendRedirect("Findgoose_setForgotPassword.jsp");
		 else if(callingURL!=null) //LoggedinRedirect
		 {
			 response.sendRedirect(callingURL);
		 }
		 else 
		 {
			 response.sendRedirect("FindGoose_Dashboard.jsp");
		 }
	 }
	 else{
		 request.setAttribute("isLoginError", "true");
		 request.setAttribute("errorMessage", "Invalid username or password"); 
         RequestDispatcher dispatcher = request.getRequestDispatcher("/Findgoose_Login.jsp");
         dispatcher.forward( request, response);
	 }
}
	else{
		initialiseDashboardDashletContents(request, response, (UserRegistrationInfo)request.getSession().getAttribute("user"));
		 request.getSession().setAttribute("InitialLoad",true);
		 response.sendRedirect("FindGoose_Dashboard.jsp");
	}
	
}

/**
 * @param request
 * @param response
 * @param user
 * @throws ServletException
 * @throws IOException
 * 
 * This method initiates all the required servlets 
 * for loading contents in each of Dashboard dashlets
 */
private void initialiseDashboardDashletContents(HttpServletRequest request, HttpServletResponse response,
		UserRegistrationInfo user) throws ServletException, IOException {
	processTeamRequest(user, request);
	 //Setting my team attributes and calling myTeam servlet
	 MyTeamServlet myTeamobj=new MyTeamServlet();
	 myTeamobj.processRequest(request, response);
	 //Setting my Task attributes and calling myTask servlet
	 MyTaskServlet myTaskobj=new MyTaskServlet();
	 myTaskobj.processRequest(request, response);
	 
	 //Setting My Lists attributes and calling myLists servlet
	 SaveUserListServlet myLists = new SaveUserListServlet();
	 myLists.processRequestUserLists(request, response,true);
	 
	//Setting My Documents attributes and calling myDocuments servlet
	 SaveUserDocumentServlet myDocuments = new SaveUserDocumentServlet();
	 myDocuments.processRequestUserDocuments(request, response, true);
	 
	//Setting My Documents attributes and calling myDocuments servlet
	 EventsServlet events = new EventsServlet();
	 events.processRequestForEvents(request, response, true);
	 
	//Setting My Contacts attributes and calling ContactsServlet
	 ContactsServlet contacts=new ContactsServlet();
	 contacts.viewContacts(request, response);
}

protected void processTeamRequest(UserRegistrationInfo user,HttpServletRequest request){
	MyTeam_DButil utilObj = new MyTeam_DButil();
	UserTeamTableInfo userTeam = utilObj.getUserTeam(user.getUser_email());
	if(userTeam!=null){
	String arr = userTeam.getTeam_members();
	arr.trim();
	String[] arrEmailSplit = arr.split(",");
	arr = userTeam.getTeam_fullname();
	arr.trim();
	String[] arrNameSplit = arr.split(",");
	arr = userTeam.getRequests_received();
	arr.trim();
	String[] arrRequests = arr.split(",");
	arr = userTeam.getRequests_sent();
	arr.trim();
	String[] arrSent = arr.split(",");
	int members_Count=userTeam.getMembers_count();

	request.getSession().setAttribute("TeamSet", "true");
	request.getSession().setAttribute("TeamMembers", arrEmailSplit);
	request.getSession().setAttribute("memberCount", members_Count);

	request.getSession().setAttribute("requestsReceived", arrRequests);
	request.getSession().setAttribute("teamMemberName", arrNameSplit);
	request.getSession().setAttribute("requestsSent", arrSent);
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
