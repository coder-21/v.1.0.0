/*Author : Diana P Lazar
Date created : Aug/01/2016
Copyright@ FindGoose
 */
package com.FG.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.FG.utils.HibernateUtil;
public class SaveUserDocumentService {
	
@SuppressWarnings("unused")
private boolean userDocumentsLimitExceeded = false;
//private int singleUserListPrefix = -1;

private List<UserDocument> userDocuments = null;
// size of byte buffer to send file
private static final int BUFFER_SIZE = 4096;  

public boolean saveUserDocument(UserDocument userDocument){
	Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
	
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(userDocument);		
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

public boolean userDocument1Exists(String userEmail){
	Session session = HibernateUtil.openSession();
	 boolean result = false;
	 Transaction tx = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserList.class);
		 criteria.add(Restrictions.eq("user_email", userEmail));
		 
		 @SuppressWarnings("unchecked")
		List<UserDocument> userDoc = (List<UserDocument>)criteria.list();
		 if(userDoc.size()>=2) 
			 userDocumentsLimitExceeded = true;
		 if(userDoc!=null && userDoc.size()>0)
		  { 
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
public List<UserDocument> getUserDocuments(String userEmail){
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 List<UserDocument> u = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserDocument.class);
		 criteria.add(Restrictions.eq("userDocumentOwneremail", userEmail));
			 u = (List<UserDocument>)criteria.list();
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
public List<UserDocument> getSharedUserDocuments(String userEmail){
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;
	 List<UserDocument> u = null;
	 try{
		 tx = session.getTransaction();
		 tx.begin();
		 
		 Criteria criteria = session.createCriteria(UserDocument.class);
		 criteria.add(Restrictions.like("sharedWithIds", "%"+userEmail+"%"));
		 u = (List<UserDocument>)criteria.list();
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

public void setUserDocuments(List<UserDocument> userDocuments){
	this.userDocuments = userDocuments;
}

public List<UserDocument> getUserDocuments() {
	return userDocuments;
}

		public boolean removeUserDocument(String selectedUserDocumentId) {
			Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 Query query = session.createQuery("delete from UserDocument where userDocumentId='"+selectedUserDocumentId+"'");
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
		
				
		public boolean removeSharedUserDocument(int selectedDocumentId) {
			Session session = HibernateUtil.openSession();
			UserDocument shared_UserDocument = getUserDocumentByDocumentIntId(selectedDocumentId);
			UserRegistrationInfo currentUser = UserRegistrationInfo.getCurrentuser();
			String[] sharedWithArr = shared_UserDocument.getSharedWithIds().split(",");
			int userindex = -1;
			for (int i=0;i<sharedWithArr.length;i++) {
			    if (sharedWithArr[i].equalsIgnoreCase(currentUser.getUser_email())) {
			    	userindex = i;
			        break;
			    }
			}
			String[] sharedUserComment = shared_UserDocument.getSharedUserDocument_Comment().split(",");
			String corresponingUserListComment = sharedUserComment[userindex] + ",";
			
			String sharedWithMemberEmail = shared_UserDocument.getSharedWithIds().replaceFirst(currentUser.getUser_email() +",", "");
			String sharedWithMemberComments = shared_UserDocument.getSharedUserDocument_Comment().replaceFirst(corresponingUserListComment, "");

			shared_UserDocument.setSharedWithIds(sharedWithMemberEmail);
			shared_UserDocument.setSharedUserDocument_Comment(sharedWithMemberComments);
			 Transaction tx = null;	
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 session.saveOrUpdate(shared_UserDocument);		
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
		public boolean saveSharedUserDocument(UserDocument sharedUserDocument, String teamMemberEmail, String sharedDocument_Comment){
			Session session = HibernateUtil.openSession();//getSessionFactory().openSession();
			
			UserDocument shared_UserDocument = sharedUserDocument;
			String sharedWithMemberEmail = shared_UserDocument.getSharedWithIds();
			String sharedDocumentComment = shared_UserDocument.getSharedUserDocument_Comment();
//			int count = shared_UserList.getList_sharedWith().length() - shared_UserList.getList_sharedWith().replace("@", "").length();
			String[] sharedWith = sharedWithMemberEmail.split(",");
			if(sharedWith.length<=2)
			{
				if(shared_UserDocument.getSharedWithIds().equalsIgnoreCase(""))
				{
					sharedWithMemberEmail = teamMemberEmail.trim();
					sharedDocumentComment =sharedDocument_Comment.trim();
				}
				else if(shared_UserDocument.getSharedWithIds().toLowerCase().contains(teamMemberEmail.toLowerCase()))
					{
						setDocumentAlreadySharedWithTeamMember(true);
						return false;
					}
			}
			else //(sharedWith.length==2)
			{
				setTeamMemberCountSharedWithExceeded(true);
				return false;
			}
				sharedWithMemberEmail =	shared_UserDocument.getSharedWithIds()+"," +teamMemberEmail;
				sharedDocumentComment = shared_UserDocument.getSharedUserDocument_Comment()+","+sharedDocument_Comment;
				String[] sharedTeamArray = sharedWithMemberEmail.split(",");
				String[] sharedTeamCommentArray = sharedDocumentComment.split(",");
				sharedWithMemberEmail = processAndReturnUpdatedUserDocumentField(sharedTeamArray);
				sharedDocumentComment = processAndReturnUpdatedUserDocumentField(sharedTeamCommentArray);
			
			shared_UserDocument.setSharedWithIds(sharedWithMemberEmail);
			shared_UserDocument.setSharedUserDocument_Comment(sharedDocumentComment);
			 Transaction tx = null;	
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 session.saveOrUpdate(shared_UserDocument);		
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

		private String processAndReturnUpdatedUserDocumentField(String[] sharedArray) {
			String sharedField;
			List<String> list = Arrays.asList(sharedArray);
			Set<String> set = new LinkedHashSet<String>(list);
			String[] result = new String[set.size()];
			set.toArray(result);
			sharedField ="";
			for (String s : result) {
				if(sharedField.equalsIgnoreCase(""))
					sharedField = s;
				else  sharedField = sharedField + "," + s;
			}
			return sharedField;
		}

		private boolean isDocumentAlreadySharedWithTeamMember = false;
		private void setDocumentAlreadySharedWithTeamMember(boolean b) {
			isDocumentAlreadySharedWithTeamMember = b;
		}
		
		public boolean getDocumentAlreadySharedWithTeamMember()
		{
			return isDocumentAlreadySharedWithTeamMember;
		}
		
		
		private boolean isTeamMemberCountSharedWithExceeded = false;
		public void setTeamMemberCountSharedWithExceeded(boolean b) {
			isTeamMemberCountSharedWithExceeded = b;
		}
		
		public boolean getteamMemberCountSharedWithExceeded()
		{
			return isTeamMemberCountSharedWithExceeded;
		}


		public void downloadUserDocument(String selectedDocumentID, HttpServletRequest request,
				HttpServletResponse response) {
			UserDocument userDocument = getUserDocumentByDocumentIntId(Integer.parseInt(selectedDocumentID));
			boolean clientAbortExceptionCaught = false;
			try {
				Blob blob = new javax.sql.rowset.serial.SerialBlob(userDocument.getUserDocument());
				InputStream inputStream = blob.getBinaryStream();
				int fileLength = inputStream.available();		
				String mimeType = userDocument.getUserDocumentType();
				// set content properties and header attributes for the response
				response.setContentType(mimeType);
				response.setContentLength(fileLength);
             
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", userDocument.getUserDocumentName());
            response.setHeader(headerKey, headerValue);

            // writes the file to the client
            OutputStream outStream = response.getOutputStream();
             
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
             
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            //
            inputStream.close();
            outStream.close();             
			} catch(ClientAbortException e){
				clientAbortExceptionCaught = true;
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				if(!clientAbortExceptionCaught) ;
				else e.printStackTrace();
			}
			
		}
		
		public UserDocument getUserDocumentByDocumentIntId(int userDocumentId) {
			 Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 UserDocument userlist = null;
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 Query query = session.createQuery("from UserDocument where userDocumentId='"+userDocumentId+"'");
				 userlist = (UserDocument)query.uniqueResult();
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
		
		public UserDocument getUserDocumentByDocId(String selectedUserDocumentIntId) {
			Session session = HibernateUtil.openSession();
			 Transaction tx = null;
			 UserDocument userDocument = null;
			 try {
				 tx = session.getTransaction();
				 tx.begin();
				 Query query = session.createQuery("from UserDocument where userDocumentId='"+selectedUserDocumentIntId+"'");
				 userDocument = (UserDocument)query.uniqueResult();
				 tx.commit();
			 } catch (Exception e) {
				 if (tx != null) {
					 tx.rollback();
				 }
				 e.printStackTrace();
			 } finally {
				 session.close();
			 }
			 return userDocument;
		}
}
