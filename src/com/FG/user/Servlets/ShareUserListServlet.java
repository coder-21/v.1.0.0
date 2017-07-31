/*Author : Diana P Lazar
Date created : feb 2016
Copyright@ FindGoose
 */

package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.SaveUserListService;
import com.FG.user.UserList;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;

/**
 * Servlet implementation class ShareUserListServlet
 */
@WebServlet("/ShareUserList")
public class ShareUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareUserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserList sharedList = new UserList();
		String sharedWithMemberId=request.getParameter("teamMembersDropdown");
		SaveUserListService saveUserListService =new SaveUserListService();
		UserRegistrationInfo user = (UserRegistrationInfo)request.getSession().getAttribute("user");
		request.getSession().setAttribute("MyListsClicked", "true");
		List<UserList> userlists = (List<UserList>)saveUserListService.getUserLists(user.getUser_email());
		request.getSession().setAttribute("userLists", userlists);
	    List<UserList> sharedUserLists = (List<UserList>) saveUserListService.getSharedUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	    request.getSession().setAttribute("sharedUserLists", sharedUserLists);
	    String sharedList_Comment = (String) request.getParameter("taskCommentTextAreaName");
	    //String selectedListIdSessionAttr = (String) request.getSession().getAttribute("selectedListIdSessionAttr");//userListIdHiddenInput");
	    request.getSession().removeAttribute("selectedListIdSessionAttr");
	    String selectedListId = (String) request.getParameter("selectedListIdOnModal");//shareListInputId");//userListIdHiddenInput");
	    selectedListId = selectedListId.trim();
	    for (int i = 0; i < userlists.size(); i++) {
			if(userlists.get(i).getList_id().equalsIgnoreCase(selectedListId.trim()) )
			{
				sharedList = userlists.get(i);
				
				boolean savedStatus = saveUserListService.saveSharedUserList(sharedList, sharedWithMemberId,sharedList_Comment);
				if(savedStatus==false) 
				{
					if(saveUserListService.getteamMemberCountSharedWithExceeded())
					{
						request.setAttribute("shareTeamMemberLimitExceeded", "You could upgrade to FG+ to share your lists to more than 2 teamMembers");
						saveUserListService.setTeamMemberCountSharedWithExceeded(false);
					}
					else if(saveUserListService.getListAlreadySharedWithTeamMember())
					{
						request.setAttribute("listAlreadyShared", "This list has already been shared with " + sharedWithMemberId+".<br>Please choose another team member to share with.");
					}else request.setAttribute("listAlreadyShared", "This list could not be shared. Please check your connections");
				}else 
				{
					List<UserList> userLists2 = (List<UserList>) saveUserListService.getUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
				      request.getSession().setAttribute("userLists", userLists2);
				      List<UserList> sharedUserLists2 = (List<UserList>) saveUserListService.getSharedUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
				      request.getSession().setAttribute("sharedUserLists", sharedUserLists2);
					request.setAttribute("listAlreadyShared", "This list has been shared with " + sharedWithMemberId);
				}
			}
		}
		FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
				"/FindGoose_Dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
