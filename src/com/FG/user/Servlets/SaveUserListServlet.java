/*Author : Diana P Lazar
Date created : 01/12/2016
Copyright@ FindGoose
 */
package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.company.CompanyInfo;
import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.user.SaveUserListService;
import com.FG.user.UserList;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;

import io.searchbox.client.JestClient;


@WebServlet(name="SaveUserListServlet", urlPatterns = {"/MyLists"})
public class SaveUserListServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void init(ServletConfig config) throws ServletException {
//	    System.out.println("*** initializing controller servlet.");
	    super.init(config);
	}
	
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	try {
		request.setAttribute("SearchPerformed", "true");
        String arr = request.getParameter("hiddenSelectedCompanies4SaveAsList");
        arr=arr.trim();

        ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
        JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
        
        String searchText=request.getParameter("searchText");
        if(searchText==null) 
        {
        	searchText = (String) request.getSession().getAttribute("searchText");
        }
        
        UserRegistrationInfo  user = (UserRegistrationInfo) request.getSession().getAttribute("user");
        SaveUserListService saveUserListService = new SaveUserListService();
        if(arr=="") 
        {
        	request.setAttribute("isListNotSelected", true);
        }
        else
        {
	        String[] arrSplit = arr.split(",");
	        StringBuffer strBuff = new StringBuffer();
	        for (int i = 0; i < arrSplit.length; i++) {
	        	arrSplit[i] = arrSplit[i].trim();
	        	strBuff.append(arrSplit[i].toString() + ";");
			}
	        String str =strBuff.toString();
//	        String str = arrSplit.toString().replaceAll(",", ";");
	        String userListName = request.getParameter("userListName");
	        boolean result = saveUserListService.saveUserList(user,str,userListName);
	        if(result){	    		
	        	request.setAttribute("userListSaved", "true");
	        	request.setAttribute("SearchPerformed", "true");
	        	List<UserList> userLists = (List<UserList>) saveUserListService.getUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	            request.getSession().setAttribute("userLists", userLists);
	            List<UserList> sharedUserLists = (List<UserList>) saveUserListService.getSharedUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	            request.getSession().setAttribute("sharedUserLists", sharedUserLists);
	        }
	        else
	        	request.setAttribute("userListLimitExceeds", "true");   
        }
        List<CompanyInfo> companybasicsearchResults =(List<CompanyInfo>) esUtil.getCompanySearchResultsFromIndexedCloudData(jestClient, searchText);
        if(companybasicsearchResults!=null)
        	request.setAttribute("companybasicsearchResults", companybasicsearchResults);
        FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
        		"/FindGoose_Dashboard.jsp");
	} catch (Exception e) {
		e.printStackTrace();
	}
}
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	 processRequestUserLists(request, response,false);
	
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

/*the variable "callFromLoginStatus" is added to this method to differentiate the call directed from a login page or 
*a redirect or a performed action on MyList Dashlet in the main page
*/
public void processRequestUserLists(HttpServletRequest request, HttpServletResponse response,boolean callFromLoginStatus)
		 throws ServletException, IOException {
	try {
		String selectedListId = null;
		SaveUserListService saveUserListService = new SaveUserListService();
		UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
		if(request.getParameter("exportListSelected")!=null) 
		{
			selectedListId = request.getParameter("selectedListID");
			saveUserListService.exportUserList(selectedListId, request,response);
		}else{
		if(request.getParameter("editListSelected")!=null) 
		{
			selectedListId = request.getParameter("selectedListID");
			saveUserListService.editUserList(selectedListId);//needs to be implemented
			return;
		}
		String userListName="";		
		if(request.getParameter("removeListSelected")!=null) 
		{
			selectedListId = request.getParameter("selectedListID");
			if(saveUserListService.getUserListByListId(selectedListId).getList_name() !=null || !saveUserListService.getUserListByListId(selectedListId).getList_name().isEmpty())
				userListName = saveUserListService.getUserListByListId(selectedListId).getList_name();
			else userListName = selectedListId;
			boolean removeListSuccessful = saveUserListService.removeUserList(selectedListId);
			if(removeListSuccessful==true) 
			{
				if(userListName.isEmpty())
					request.setAttribute("removedListId", selectedListId);
				else
					request.setAttribute("removedListId", userListName);
				request.setAttribute("listRemoved", "true");
			}
		}
		if(request.getParameter("removeSharedList")!=null) 
		{
			selectedListId = request.getParameter("selectedListID");
			if(saveUserListService.getUserListByListId(selectedListId).getList_name() !=null || !saveUserListService.getUserListByListId(selectedListId).getList_name().isEmpty())
				userListName = saveUserListService.getUserListByListId(selectedListId).getList_name();
			else userListName = selectedListId;
			boolean removeListSuccessful = saveUserListService.removeSharedUserList(selectedListId);
			if(removeListSuccessful==true) 
			{
				if(userListName.isEmpty())
					request.setAttribute("removedListId", selectedListId);
				else
					request.setAttribute("removedListId", userListName);
				request.setAttribute("sharedListRemoved", "true");
			}
		}
		 request.getSession().setAttribute("MyListsClicked", "true");
	     List<UserList> userLists = (List<UserList>) saveUserListService.getUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	     request.getSession().setAttribute("userLists", userLists);
	     List<UserList> sharedUserLists = (List<UserList>) saveUserListService.getSharedUserLists(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
	     request.getSession().setAttribute("sharedUserLists", sharedUserLists);
	     //This is where the documents of companies for selected list is set for display in Reset page
	     if(callFromLoginStatus) ;
	     else if(request.getParameter("exportListSelected")!=null) ;
	     else
	     {
	      FG_Util fg_util = new FG_Util(); 
	      fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");//FindGoose_Home.jsp");
	     }
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
