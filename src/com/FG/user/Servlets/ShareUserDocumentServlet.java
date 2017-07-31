/*Author : Diana P Lazar
Date created : Aug 5th 2016
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

import com.FG.user.SaveUserDocumentService;
import com.FG.user.UserDocument;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;

/**
 * Servlet implementation class ShareUserDocumentServlet
 */
@WebServlet("/ShareUserDocument")
public class ShareUserDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareUserDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDocument sharedDocument = new UserDocument();
		String sharedWithMemberId=request.getParameter("teamMembersDropdown");
		SaveUserDocumentService saveUserDocService = new SaveUserDocumentService();
		UserRegistrationInfo user = (UserRegistrationInfo)request.getSession().getAttribute("user");
		request.getSession().setAttribute("MyDocumentsClicked", "true");
		List<UserDocument> userDocuments = (List<UserDocument>)saveUserDocService.getUserDocuments(user.getUser_email());
		request.getSession().setAttribute("userDocuments", userDocuments);
	    List<UserDocument> sharedUserDocuments = (List<UserDocument>) saveUserDocService.getSharedUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	    request.getSession().setAttribute("sharedUserDocuments", sharedUserDocuments);
	    String sharedDocument_Comment = (String) request.getParameter("taskCommentTextAreaName");
	    int selectedDocumentId = (int) request.getSession().getAttribute("selectedDocumentIdSessionAttr");//userListIdHiddenInput");
	    request.getSession().removeAttribute("selectedDocumentIdSessionAttr");
	    for (int i = 0; i < userDocuments.size(); i++) {
			if(userDocuments.get(i).getUserDocumentId()==selectedDocumentId)
			{
				sharedDocument = userDocuments.get(i);
				SaveUserDocumentService ss =new SaveUserDocumentService();
				boolean savedStatus = ss.saveSharedUserDocument(sharedDocument, sharedWithMemberId,sharedDocument_Comment);
				if(savedStatus==false) 
				{
					if(ss.getteamMemberCountSharedWithExceeded())
					{
						request.setAttribute("shareTeamMemberLimitExceeded", "You could upgrade to FG+ to share your lists to more than 2 teamMembers");
						ss.setTeamMemberCountSharedWithExceeded(false);
					}
					else if(ss.getDocumentAlreadySharedWithTeamMember())
					{
						request.setAttribute("documentAlreadyShared", sharedDocument.getUserDocumentName()+" has already been shared with " + sharedWithMemberId+".<br>Please choose another team member to share with.");
					}else request.setAttribute("documentAlreadyShared", sharedDocument.getUserDocumentName()+" could not be shared. Please check your connections");
				}else 
				{
					List<UserDocument> userDocuments2 = (List<UserDocument>) ss.getUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
				      request.getSession().setAttribute("userDocuments", userDocuments2);
				      List<UserDocument> sharedUserDocuments2 = (List<UserDocument>) ss.getSharedUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
				      request.getSession().setAttribute("sharedUserDocuments", sharedUserDocuments2);
					request.setAttribute("documentAlreadyShared", sharedDocument.getUserDocumentName()+" has been shared with " + sharedWithMemberId);
				}
			}
		}
		FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
