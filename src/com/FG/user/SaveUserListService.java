/*Author : Diana P Lazar
Date created : 01/12/2016
Copyright@ FindGoose
 */
package com.FG.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.company.CompanyInfo;
import com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud;
import com.FG.utils.FG_Util;
import com.FG.utils.HibernateUtil;
import com.FG.utils.UserList_DBUtil;

import io.searchbox.client.JestClient;
public class SaveUserListService {
	
private boolean userListLimitExceeded = false;
private int singleUserListPrefix = -1;

public boolean saveUserList(UserRegistrationInfo user, String arr, String userListName){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	String listID = getListID(user.getUser_email());
	if(listID==null) return false;
	String[] arrSingle = arr.split(";");
	StringBuffer companiesStr = new StringBuffer();
	for (int i = 0; i < arrSingle.length; i++) {
		companiesStr.append(arrSingle[i].trim() + ";");
	}
	UserList userList = new UserList(listID, companiesStr.toString(), user.getUser_email(),"","",userListName);
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(userList);		
		 tx.commit();
	 } catch (Exception e) {
		 if (tx != null) {
			 tx.rollback();
		 }
		 e.printStackTrace();
	 } finally {
		 session.close();
	 }	
	 return true;
}

private String getListID(String userEmail) {
	if(userList1Exists(userEmail)) 
		{
			if(userListLimitExceeded)
				return null;
			else if(singleUserListPrefix==2) return "1_"+userEmail;
			else return "2_"+userEmail;
		}
	else return "1_"+userEmail;
}

public boolean userList1Exists(String userEmail){
	Session session = HibernateUtil.openSession();
	 boolean result = false;
	 Transaction tx = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserList.class);
		 criteria.add(Restrictions.eq("user_email", userEmail));
		 
		 @SuppressWarnings("unchecked")
		List<UserList> u = (List<UserList>)criteria.list();
		 if(u.size()>=2) 
			 userListLimitExceeded = true;
		 if(u!=null && u.size()>0)
		  { 
			 if(u.size()==1) 
			 {
				 singleUserListPrefix = Integer.parseInt(u.get(0).getList_id().substring(0,1));
			 }
			 result = true;
		  }else 
		  {
			 result=false;
		  }
		 tx.commit();
	 }catch(Exception ex){
		 ex.printStackTrace();
		 if(tx!=null){
			 tx.rollback();
		 }
	 }finally{
		 session.close();
	 }
	 return result;
}

@SuppressWarnings("unchecked")
public List<UserList> getUserLists(String userEmail){
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 List<UserList> u = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserList.class);
		 criteria.add(Restrictions.eq("user_email", userEmail));

		 u = (List<UserList>)criteria.list();
		 tx.commit();
	 }catch(Exception ex){
		 ex.printStackTrace();
		 if(tx!=null){
			 tx.rollback();
		 }
	 }finally{
		 session.close();
	 }
	 return u;
}

@SuppressWarnings("unchecked")
public List<UserList> getSharedUserLists(String userEmail){
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 List<UserList> u = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserList.class);
		 criteria.add(Restrictions.like("list_sharedWith", "%"+userEmail+"%"));
		 u = (List<UserList>)criteria.list();
		 tx.commit();
	 }catch(Exception ex){
		 ex.printStackTrace();
		 if(tx!=null){
			 tx.rollback();
		 }
	 }finally{
		 session.close();
	 }
	 return u;
}


public boolean editUserList(String ListId) {
	// TODO Auto-generated method stub
	return false;
}

		public boolean removeUserList(String selectedListId) {
			Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 Query query = session.createQuery("delete from UserList where list_id='"+selectedListId+"'");
				 query.executeUpdate();
				 
				 tx.commit();
			 } catch (Exception e) {
				 if (tx != null) {
					 tx.rollback();
				 }
				 e.printStackTrace();
				 return false;
			 } finally {
				 session.close();
			 }
			 return true;
		}
		
		public UserList getUserListByListId(String listId) {
			 Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 UserList userlist = null;
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 Query query = session.createQuery("from UserList where list_id='"+listId+"'");
				 userlist = (UserList)query.uniqueResult();
				 tx.commit();
			 } catch (Exception e) {
				 if (tx != null) {
					 tx.rollback();
				 }
				 e.printStackTrace();
			 } finally {
				 session.close();
			 }
			 return userlist;
		}
		
		public void exportUserList(String selectedListId, HttpServletRequest request, HttpServletResponse response) {
			UserList_DBUtil userListDBUtil = new UserList_DBUtil();
			UserList userList = userListDBUtil.getUserListByListId(selectedListId);
			String arr = userList.getCompany_docId();
			String[] arrSplit = arr.split(";");
//	        SearchUtil.setExportListString(arrSplit);
			List<CompanyInfo> selectedCompanyList4Export = new ArrayList<CompanyInfo>();
			for (int i = 0; i < arrSplit.length; i++) 
			{
				arrSplit[i]=arrSplit[i].trim();
				try 
				{
					ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
			        JestClient jestClient = esUtil.getJESTclient4MainIndexInAmazonCloud();
					CompanyInfo comp = (CompanyInfo) esUtil.getCompanyInfo4SelectedCompanyFromIndexedCloudData(jestClient, arrSplit[i]);
					selectedCompanyList4Export.add(comp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//List<CompanyInfo> resultedCompanyList = (List<CompanyInfo>)request.getSession().getAttribute("searchResultTable");
//			request.removeAttribute("searchResultTable");
//	        for (int i = 0; i < resultedCompanyList.size(); i++) {
//	        	for (int j = 0; j < arrSplit.length; j++) {							
//	        		if(arrSplit[j].contains(resultedCompanyList.get(i).getName()))
//	        		{
//	        			selectedCompanyList4Export.add(resultedCompanyList.get(i));
//	        		}
//				}
//			}
			request.setAttribute("selectedCompanyList4Export", selectedCompanyList4Export);
        	try {
        		FG_Util fg_util = new FG_Util(); 
				fg_util.passSearchResultsToJSP(request, response,"/FindGoose_searchResultExcel.jsp");
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public boolean removeSharedUserList(String selectedListId) {
			Session session = HibernateUtil.openSession();
			UserList_DBUtil userListDBUtil = new UserList_DBUtil();
			UserList shared_UserList = userListDBUtil.getUserListByListId(selectedListId);
			UserRegistrationInfo currentUser = UserRegistrationInfo.getCurrentuser();
			String[] sharedWithArr = shared_UserList.getList_sharedWith().split(",");
			int userindex = -1;
			for (int i=0;i<sharedWithArr.length;i++) {
			    if (sharedWithArr[i].trim().equalsIgnoreCase(currentUser.getUser_email())) {
			    	userindex = i;
			        break;
			    }
			}
			String[] sharedUserComment = shared_UserList.getSharedList_Comment().split(",");
			String corresponingUserListComment = sharedUserComment[userindex] + ",";
			
			String userEmailStrToRemove = "";
			if(sharedWithArr.length>1) userEmailStrToRemove=currentUser.getUser_email()+",";
			else userEmailStrToRemove=currentUser.getUser_email();
			
			String sharedWithMemberEmail = shared_UserList.getList_sharedWith().trim().replaceFirst(userEmailStrToRemove, "");
			String sharedWithMemberComments = shared_UserList.getSharedList_Comment().replaceFirst(corresponingUserListComment, "");

			shared_UserList.setList_sharedWith(sharedWithMemberEmail);
			shared_UserList.setSharedList_Comment(sharedWithMemberComments);
			 Transaction tx = null;	
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 session.saveOrUpdate(shared_UserList);		
				 tx.commit();
			 } catch (Exception e) {
				 if (tx != null) {
					 tx.rollback();
				 }
				 e.printStackTrace();
			 } finally {
				 session.close();
			 }	
			 return true;
		}
		
		//This method adds a shared list to a team member
		public boolean saveSharedUserList(UserList sharedUserList, String teamMemberEmail, String sharedList_Comment){
			Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
			
			UserList shared_UserList = sharedUserList;
			String sharedWithMemberEmail = shared_UserList.getList_sharedWith();
			String sharedListComment = shared_UserList.getSharedList_Comment();
//			int count = shared_UserList.getList_sharedWith().length() - shared_UserList.getList_sharedWith().replace("@", "").length();
			String[] sharedWith = sharedWithMemberEmail.split(",");
			if(sharedWith.length<=2)
			{
				if(shared_UserList.getList_sharedWith().equalsIgnoreCase(""))
				{
					sharedWithMemberEmail = teamMemberEmail.trim()+",";
					sharedListComment =sharedList_Comment.trim()+",";
				}
				else if(shared_UserList.getList_sharedWith().toLowerCase().contains(teamMemberEmail.toLowerCase()))
					{
						setListAlreadySharedWithTeamMember(true);
						return false;
					}
				else{
						if(sharedWith.length==2)
						{
							setTeamMemberCountSharedWithExceeded(true);
							return false;
						}else{
							sharedWithMemberEmail =	shared_UserList.getList_sharedWith()+teamMemberEmail+",";
							sharedListComment = shared_UserList.getSharedList_Comment()+sharedList_Comment+",";
						}
					}
			}
			shared_UserList.setList_sharedWith(sharedWithMemberEmail);
			shared_UserList.setSharedList_Comment(sharedListComment);
			 Transaction tx = null;	
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 session.saveOrUpdate(shared_UserList);		
				 tx.commit();
			 } catch (Exception e) {
				 if (tx != null) {
					 tx.rollback();
				 }
				 e.printStackTrace();
			 } finally {
				 session.close();
			 }	
			 return true;
		}

		@SuppressWarnings("unused")
		private String processAndReturnUpdatedUserListField(String[] sharedArray) {
			String sharedField;
			List<String> list = Arrays.asList(sharedArray);
			Set<String> set = new LinkedHashSet<String>(list);
			String[] result = new String[set.size()];
			set.toArray(result);
			sharedField ="";
			for (String s : result) {
				if(sharedField.equalsIgnoreCase(""))
					sharedField = s;
				else  sharedField = sharedField+","+s+"," ;
			}
			return sharedField;
		}

		private boolean isListAlreadySharedWithTeamMember = false;
		private void setListAlreadySharedWithTeamMember(boolean b) {
			isListAlreadySharedWithTeamMember = b;
		}
		
		public boolean getListAlreadySharedWithTeamMember()
		{
			return isListAlreadySharedWithTeamMember;
		}
		
		
		private boolean isTeamMemberCountSharedWithExceeded = false;
		public void setTeamMemberCountSharedWithExceeded(boolean b) {
			isTeamMemberCountSharedWithExceeded = b;
		}
		
		public boolean getteamMemberCountSharedWithExceeded()
		{
			return isTeamMemberCountSharedWithExceeded;
		}

		public List<String> getListOfUserListIds(UserRegistrationInfo userFromSession){
			UserRegistrationInfo  user = userFromSession;
			List<UserList> userLists = getUserLists(user.getUser_email());
			int userlistSize = userLists.size();
			List<String> userListsIds = new ArrayList<String>();
			for (int i = 0; i < userlistSize; i++) {
		      UserList ul = userLists.get(i);
		      userListsIds.add(ul.getList_id());
		    }
			return userListsIds;
		}
		
		public List<String> getListOfSharedUserListIds(UserRegistrationInfo userFromSession){
			UserRegistrationInfo  user = userFromSession;
			List<UserList> sharedUserLists = getSharedUserLists(user.getUser_email());
			int userlistSize = sharedUserLists.size();
			List<String> sharedUserListsIds = new ArrayList<String>();
			for (int i = 0; i < userlistSize; i++) {
		      UserList ul = sharedUserLists.get(i);
		      sharedUserListsIds.add(ul.getList_id());
		    }
			return sharedUserListsIds;
		}
		
		public List<CompanyInfo[]> getListOfCompaniesForEachUserList_ES(UserRegistrationInfo userFromSession)
		{
			ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
			List<CompanyInfo[]> listOfCompanyInfosArrForEachUserList = new ArrayList<CompanyInfo[]>();
			UserRegistrationInfo  user = userFromSession;
			List<UserList> userLists = getUserLists(user.getUser_email());
			int userlistSize = userLists.size();
			for (int i = 0; i < userlistSize; i++) {
			   UserList ul = userLists.get(i);
			   String[] selectedListIDCompanies = (ul.getCompany_docId()).split(";");
			   CompanyInfo[] companies = (CompanyInfo[]) esUtil.getUserListCompanyInfosArray(selectedListIDCompanies);//SearchUtil.getUserListDocArray(selectedListIDCompanies);
			   listOfCompanyInfosArrForEachUserList.add(companies);
			 }
			return listOfCompanyInfosArrForEachUserList;
		}
		
		public List<CompanyInfo[]> getListOfCompaniesForEachSharedUserList_ES(UserRegistrationInfo userFromSession)
		{
			ElasticsearchUtil_4AmazonCloud esUtil = new ElasticsearchUtil_4AmazonCloud();
			List<CompanyInfo[]> listOfCompanyInfosArrForEachSharedUserList = new ArrayList<CompanyInfo[]>();
			UserRegistrationInfo  user = userFromSession;
			List<UserList> sharedUserLists = getSharedUserLists(user.getUser_email());
			int sharedUserlistSize = sharedUserLists.size();
			for (int i = 0; i < sharedUserlistSize; i++) {
			   UserList ul = sharedUserLists.get(i);
			   String[] selectedListIDCompanies = (ul.getCompany_docId()).split(";");
			   CompanyInfo[] companies = (CompanyInfo[]) esUtil.getUserListCompanyInfosArray(selectedListIDCompanies);//SearchUtil.getUserListDocArray(selectedListIDCompanies);
			   listOfCompanyInfosArrForEachSharedUserList.add(companies);
			 }
			return listOfCompanyInfosArrForEachSharedUserList;
		}
		
}
