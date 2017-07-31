package com.FG.user.Servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.UserRegistrationInfo;
import com.FG.user.UserTeamTableInfo;
import com.FG.userTeam.services.UserTeamService;
import com.FG.utils.FG_Util;
import com.FG.utils.MyTeam_DButil;
import com.FG.utils.User_DButil;
import com.FG.utils.emailTempPassword.SendMemberAddedNotification;
import com.FG.utils.emailTempPassword.SendTempPassword;

@WebServlet(name = "MyTeamServlet", urlPatterns = { "/MyTeamServlet" })
public class MyTeamServlet extends HttpServlet {

	/**
	 * @author Suvarna
	 * MyTeam servlet implementation
	 */
	private static final long serialVersionUID = 12176956918067436L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MyTeam_DButil utilObj = new MyTeam_DButil();
		UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
		UserTeamTableInfo userTeam = utilObj.getUserTeam(user.getUser_email());
		String[] arrEmailSplit = null,arrNameSplit=null,arrRequests=null,arrSent=null;
		int members_Count;
		if(userTeam==null){
			userTeam = new UserTeamTableInfo(user.getUser_email(),user.getUser_firstname()+"_"+user.getUser_lastname(), "", "", "", "", 0);
			utilObj.addNewUser(userTeam);
		}
		String arr = userTeam.getTeam_members();
		arr.trim();
		arrEmailSplit = arr.split(",");
		arr = userTeam.getTeam_fullname();
		arr.trim();
		arrNameSplit = arr.split(",");
		arr = userTeam.getRequests_received();
		arr.trim();
		arrRequests = arr.split(",");
		arr = userTeam.getRequests_sent();
		arr.trim();
		arrSent = arr.split(",");
		members_Count = userTeam.getMembers_count();
		

		request.getSession().setAttribute("TeamSet", "true");
		request.getSession().setAttribute("TeamMembers", arrEmailSplit);
		request.getSession().setAttribute("memberCount", members_Count);

		request.getSession().setAttribute("requestsReceived", arrRequests);
		request.getSession().setAttribute("teamMemberName", arrNameSplit);
		request.getSession().setAttribute("requestsSent", arrSent);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("acceptForm") != null) {
			accept(request, response, request.getParameter("acceptForm"));

		}
		if (request.getParameter("rejectForm") != null) {
			reject(request, response, request.getParameter("rejectForm"));

		}
		if (request.getParameter("addUser") != null) {
			
			// reject(request, response, request.getParameter("rejectForm"));
			String addUserEmail = request.getParameter("addUserEmail");
			addNewUser(request, response, addUserEmail);
			 FG_Util utilObj=new FG_Util();
			 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
		}

	}

	/**
	 * Steps: user A tries to add user B Check User A count<10 add user B email
	 * id to user A's pending list Add user A's email id to user B's request
	 * received list Send email notification to user B
	 * 
	 * @param request
	 * @param response
	 * @param addUserEmail
	 */

	private void addNewUser(HttpServletRequest request, HttpServletResponse response, String addUserEmail) {
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");

		MyTeam_DButil teamUtilObj = new MyTeam_DButil();
		User_DButil userUtil = new User_DButil();
		UserTeamService teamService = new UserTeamService();
		UserTeamTableInfo userTeam;
		
		int memberCount = (int) request.getSession().getAttribute("memberCount");
		if (memberCount < 10) {
			String userEmail = userA.getUser_email();

			UserTeamTableInfo userTeamB = new UserTeamTableInfo();
			String[] retrievedfromSession = (String[]) request.getSession().getAttribute("TeamMembers");
			String teamA = Arrays.toString(retrievedfromSession);

			if (teamUtilObj.getUserTeam(addUserEmail) == null) {
				UserRegistrationInfo userB = userUtil.getUserByEmail(addUserEmail);
				if (userB == null) {
					// print that the user must first register with FG and
					// invite to FG and redirect to invite
					request.setAttribute("MyTeamMessage", addUserEmail+" is not a FindGoose user. Invite to FindGoose using the invite option");
					request.setAttribute("MyTeamClicked", "yes");
					return;
				} else {
					userTeam = new UserTeamTableInfo(addUserEmail,
							userB.getUser_firstname() + "_" + userB.getUser_lastname(), "", "", "", "", 0);
					teamUtilObj.addNewUser(userTeam);

				}
			}

			retrievedfromSession = (String[]) request.getSession().getAttribute("requestsReceived");
			String receivedA = Arrays.toString(retrievedfromSession);
			retrievedfromSession = (String[]) request.getSession().getAttribute("requestsSent");
			String sentA = Arrays.toString(retrievedfromSession);
			if (teamA.contains(addUserEmail)) {
				// print that the user is already a team member
				request.setAttribute("MyTeamMessage", addUserEmail+" is already a team member");
				request.setAttribute("MyTeamClicked", "yes");
				return;
			}
			if (receivedA.contains(addUserEmail)) {
				// print that the userA must accept userB
				request.setAttribute("MyTeamMessage", addUserEmail+" has already requested to join your team. Kindly review");
				request.setAttribute("MyTeamClicked", "yes");
				return;
			} else if (sentA.contains(addUserEmail)) {
				request.setAttribute("MyTeamMessage", addUserEmail+" has already been requested");
				request.setAttribute("MyTeamClicked", "yes");
				return;
			}

			else {
				sentA = sentA.concat("," + addUserEmail);
				if(sentA.contains("[")){
					sentA=sentA.replace("[", "");
				}
				if(sentA.contains("]")){
					sentA=sentA.replace("]", "");
				}
				teamUtilObj.setRequests_Sent(userEmail, sentA);
				userTeamB = teamUtilObj.getUserTeam(addUserEmail);
				String receivedB = userTeamB.getRequests_received();
				receivedB = receivedB.concat("," + userEmail);
				teamUtilObj.setRequests_Received(addUserEmail, receivedB);
				// send mail to userB

				// change team set session value
				request.setAttribute("MyTeamMessage", " A request has been sent to "+addUserEmail);
				request.setAttribute("MyTeamClicked", "yes");
				try {
					processRequest(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				SendMemberAddedNotification.send(addUserEmail, userEmail);
			}
		}

	}

	// Decrease count of userA and UserB
	// Remove user B from userA's members and name
	// Remove userA from user B's members and name
	protected void removeUser(HttpServletRequest request, HttpServletResponse response, String userMember) {
		User_DButil userUtilObj = new User_DButil();
		MyTeam_DButil teamUtilObj = new MyTeam_DButil();
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
		String userEmail = userA.getUser_email();
		UserRegistrationInfo userB = userUtilObj.getUserByEmail(userMember);
		UserTeamTableInfo userTeamA = teamUtilObj.getUserTeam(userA.getUser_email());
		UserTeamTableInfo userTeamB = teamUtilObj.getUserTeam(userMember);
		// Decrement count
		teamUtilObj.setCount(userA.getUser_email(), userTeamA.getMembers_count() - 1);
		teamUtilObj.setCount(userMember, userTeamB.getMembers_count() - 1);
		// remove UserB from UserA's list
		String arr = userTeamA.getTeam_members();
		if (arr.contains("," + userMember)) {
			arr = arr.replace("," + userMember, "");
		} else if (arr.contains(userMember + ",")) {
			arr = arr.replace(userMember + ",", "");
		} else if (arr.contains(userMember)) {
			arr = arr.replace(userMember, "");
		}
		if(arr.contains("[")){
			arr=arr.replace("[", "");
		}
		if(arr.contains("]")){
			arr=arr.replace("]", "");
		}
		teamUtilObj.setDBTeam_members(userEmail, arr);
		arr = userTeamA.getTeam_fullname();
		String userName = userB.getUser_firstname() + "_" + userB.getUser_lastname();
		if (arr.contains("," + userName)) {
			arr = arr.replace("," + userName, "");
		} else if (arr.contains(userName + ",")) {
			arr = arr.replace(userName + ",", "");
		} else if (arr.contains(userName)) {
			arr = arr.replace(userName, "");
		}
		if(arr.contains("[")){
			arr=arr.replace("[", "");
		}
		if(arr.contains("]")){
			arr=arr.replace("]", "");
		}
		teamUtilObj.setDBTeam_Names(userEmail, arr);

		// remove userA from userB's list
		arr = userTeamB.getTeam_members();
		if (arr.contains("," + userEmail)) {
			arr = arr.replace("," + userEmail, "");
		} else if (arr.contains(userEmail + ",")) {
			arr = arr.replace(userEmail + ",", "");
		} else if (arr.contains(userEmail)) {
			arr = arr.replace(userEmail, "");
		}
		if(arr.contains("[")){
			arr=arr.replace("[", "");
		}
		if(arr.contains("]")){
			arr=arr.replace("]", "");
		}
		teamUtilObj.setDBTeam_members(userMember, arr);
		arr = userTeamB.getTeam_fullname();
		userName = userA.getUser_firstname() + "_" + userA.getUser_lastname();
		if (arr.contains("," + userName)) {
			arr = arr.replace("," + userName, "");
		} else if (arr.contains(userName + ",")) {
			arr = arr.replace(userName + ",", "");
		} else if (arr.contains(userName)) {
			arr = arr.replace(userName, "");
		}
		if(arr.contains("[")){
			arr=arr.replace("[", "");
		}
		if(arr.contains("]")){
			arr=arr.replace("]", "");
		}
		teamUtilObj.setDBTeam_Names(userMember, arr);
		request.setAttribute("MyTeamMessage", userMember+" has been removed from your team");
		request.setAttribute("MyTeamClicked", "yes");
		try {
			processRequest(request, response);
			 FG_Util utilObj=new FG_Util();
			 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("removeTeamMember")!= null) {
			String removeEmail = request.getParameter("selectedMemberID");
			removeUser(request, response, removeEmail);
		}
		else{
		request.setAttribute("MyTeamClicked", "yes");
		if (request.getSession().getAttribute("TeamSet") != "true") {
			processRequest(request, response);
		}
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param userMember
	 *            when user A accepts user B{ check if user A is already a
	 *            member of user B check the count of user B add User A's name
	 *            and email to user B's name list and email list remove user A's
	 *            name from pending list} { check count of user A<10 increment
	 *            count of user A add B's name and email to user A's name list
	 *            and email list remove user B's name from requests received
	 *            list}
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	protected void accept(HttpServletRequest request, HttpServletResponse response, String userMember)
			throws ServletException, IOException {
		User_DButil userUtilObj = new User_DButil();
		MyTeam_DButil teamUtilObj = new MyTeam_DButil();
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
		UserRegistrationInfo userB = userUtilObj.getUserByEmail(userMember);
		UserTeamTableInfo userTeamA = teamUtilObj.getUserTeam(userA.getUser_email());
		UserTeamTableInfo userTeamB = teamUtilObj.getUserTeam(userMember);
		String userEmail = userA.getUser_email();
		if (userTeamA.getMembers_count() < 10) {
			String arr = userTeamA.getTeam_members();
			if (arr.contains(userMember)) {

			} else {
				arr = arr + "," + userMember;
				userTeamA.setTeam_members(arr);
				teamUtilObj.setDBTeam_members(userEmail, arr);
				String nameArr = userTeamA.getTeam_fullname();
				nameArr = nameArr + "," + userB.getUser_firstname() + "_" + userB.getUser_lastname();
				teamUtilObj.setDBTeam_Names(userEmail, nameArr);
				teamUtilObj.setCount(userEmail, userTeamA.getMembers_count() + 1);
				// code to delete user B from pending lists
				arr = userTeamA.getRequests_received();
				if (arr.contains("," + userMember)) {
					arr = arr.replace("," + userMember, "");
				} else if (arr.contains(userMember + ",")) {
					arr = arr.replace(userMember + ",", "");
				} else if (arr.contains(userMember)) {
					arr = arr.replace(userMember, "");
				}
				teamUtilObj.setRequests_Received(userEmail, arr);
			}
		}
		if (userTeamB.getMembers_count() < 10) {
			String arr = userTeamB.getTeam_members();
			if (arr.contains(userEmail)) {

			} else {
				arr = arr + "," + userEmail;
				teamUtilObj.setDBTeam_members(userB.getUser_email(), arr);
				String nameArr = userTeamB.getTeam_fullname();
				nameArr = nameArr + "," + userA.getUser_firstname() + "_" + userA.getUser_lastname();
				teamUtilObj.setDBTeam_Names(userB.getUser_email(), nameArr);
				teamUtilObj.setCount(userB.getUser_email(), userTeamB.getMembers_count() + 1);
				// Code to delete user A from requests sent list
				arr = userTeamB.getRequests_sent();
				if (arr.contains("," + userEmail)) {
					arr = arr.replace("," + userEmail, "");
				} else if (arr.contains(userEmail + ",")) {
					arr = arr.replace(userEmail + ",", "");
				} else if (arr.contains(userEmail)) {
					arr = arr.replace(userEmail, "");
				}
				teamUtilObj.setRequests_Sent(userMember, arr);
			}
		}
		processRequest(request, response);
		request.setAttribute("MyTeamClicked", "yes");
		request.setAttribute("MyTeamMessage", userMember+" has been added to your team");
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param userMember
	 *            when user A rejects user B{ remove user B's name from user A's
	 *            received list remove user A's name from user B's sent list
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */

	protected void reject(HttpServletRequest request, HttpServletResponse response, String userMember)
			throws ServletException, IOException {
		MyTeam_DButil teamUtilObj = new MyTeam_DButil();
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
		UserTeamTableInfo userTeamA = teamUtilObj.getUserTeam(userA.getUser_email());
		UserTeamTableInfo userTeamB = teamUtilObj.getUserTeam(userMember);
		String arr = userTeamA.getRequests_received();
		if (arr.contains("," + userMember)) {
			arr = arr.replace("," + userMember, "");
		} else if (arr.contains(userMember + ",")) {
			arr = arr.replace(userMember + ",", "");
		} else if (arr.contains(userMember)) {
			arr = arr.replace(userMember, "");
		}
		teamUtilObj.setRequests_Received(userA.getUser_email(), arr);
		request.getSession().removeAttribute("requestsReceived");
		request.getSession().setAttribute("requestsReceived", arr);
		arr = userTeamB.getRequests_sent();
		if (arr.contains("," + userA.getUser_email())) {
			arr = arr.replace("," + userA.getUser_email(), "");
		} else if (arr.contains(userA.getUser_email() + ",")) {
			arr = arr.replace(userA.getUser_email() + ",", "");
		} else if (arr.contains(userA.getUser_email())) {
			arr = arr.replace(userA.getUser_email(), "");
		}
		teamUtilObj.setRequests_Sent(userMember, arr);
		request.setAttribute("MyTeamClicked", "yes");
		request.setAttribute("MyTeamMessage", " Request from "+userMember+"declined");
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");	
	}
}
