/*Author : Diana P Lazar
Date created : aug/01/2016
Copyright@ FindGoose
 */
package com.FG.user.Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.connector.ClientAbortException;

import com.FG.user.SaveUserDocumentService;
import com.FG.user.UserDocument;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;


@WebServlet(name="SaveUserDocumentServlet", urlPatterns = {"/MyDocuments"})
@MultipartConfig(maxFileSize = 16177215)
public class SaveUserDocumentServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	}
	
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	try {
        UserRegistrationInfo  user = (UserRegistrationInfo) request.getSession().getAttribute("user");
        SaveUserDocumentService saveUserDocumentService = new SaveUserDocumentService();
        Date userDocument_CreatedDate = new Date();
        InputStream inputStream = null;	// input stream of the upload file
		
		// obtains the upload file part in this multipart request
		Part filePart = request.getPart("uploadedFile");
		Path p = Paths.get(filePart.getSubmittedFileName());
		String fileExtn = ((String) p.getFileName().toString()).split("\\.")[1];
		
		String docType = "";
		if (filePart != null) {
			// prints out some information for debugging
//			System.out.println(fileExtn);
//			System.out.println(filePart.getName());
//			System.out.println(filePart.getSize());
//			System.out.println(filePart.getContentType());
			docType = filePart.getContentType();
			// obtains input stream of the upload file
			inputStream = filePart.getInputStream();
		}
        
	        String userDocumentName = request.getParameter("userDocumentName")+"."+fileExtn;
	        //File document = request.getParameter("userDocumentName");
	        UserDocument userDocument = new UserDocument(user.getUser_email(),userDocumentName, userDocument_CreatedDate, inputStream,docType);
	        
	        List<UserDocument> existingUserDocuments = (List<UserDocument>)saveUserDocumentService.getUserDocuments(user.getUser_email());
	        int userDocumentCount = existingUserDocuments.size();
	        if(userDocumentCount <2)
	        {
		        boolean result = saveUserDocumentService.saveUserDocument(userDocument);
		        saveUserDocumentService.setUserDocuments(saveUserDocumentService.getUserDocuments(user.getUser_email()));
		        List<UserDocument> userDocuments = (List<UserDocument>) saveUserDocumentService.getUserDocuments(user.getUser_email());
		        request.getSession().setAttribute("userDocuments", userDocuments);
		        List<UserDocument> sharedUserDocuments = (List<UserDocument>) saveUserDocumentService.getSharedUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
		        request.getSession().setAttribute("sharedUserDocuments", sharedUserDocuments);
		        userDocumentCount = userDocuments.size() + sharedUserDocuments.size();
		        if(result){
		        	request.setAttribute("userDocumentSaved", "true");
		        }
		    }else{	
		    	List<UserDocument> userDocuments = (List<UserDocument>) saveUserDocumentService.getUserDocuments(user.getUser_email());
		        request.getSession().setAttribute("userDocuments", userDocuments);
		        List<UserDocument> sharedUserDocuments = (List<UserDocument>) saveUserDocumentService.getSharedUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
		        request.getSession().setAttribute("sharedUserDocuments", sharedUserDocuments);
	        	request.setAttribute("userDocumentLimitExceeds", "true");
	        }
	        request.getSession().setAttribute("MyDocumentsClicked", "true");
        FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
	} catch (Exception e) {
		e.printStackTrace();
	}
}
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	processRequestUserDocuments(request, response,false);
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

public void processRequestUserDocuments(HttpServletRequest request, HttpServletResponse response, boolean callFromLoginStatus)
		 throws ServletException, IOException {
	try {
		SaveUserDocumentService saveUserDocumentService = new SaveUserDocumentService();
		String selectedDocumentID = null;
		UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
		if(request.getParameter("downloadDocumentSelected")!=null) 
		{
			selectedDocumentID = request.getParameter("selectedDocumentID");
			saveUserDocumentService.downloadUserDocument(selectedDocumentID, request,response);
		}
		else 
		{
			if(request.getParameter("removeDocumentSelected")!=null) 
			{
					selectedDocumentID = request.getParameter("selectedDocumentID");
					String removedDocName= saveUserDocumentService.getUserDocumentByDocId(selectedDocumentID).getUserDocumentName();
					boolean removeListSuccessful = saveUserDocumentService.removeUserDocument(selectedDocumentID);
					if(removeListSuccessful==true) 
						{
							request.setAttribute("documentRemoved", "true");
							request.setAttribute("removedDocumentName",removedDocName );
						}
			}	
			if(request.getParameter("removeSharedDocumentSelected")!=null) 
			{
					selectedDocumentID = request.getParameter("selectedSharedDocumentID");
					String removedDocName= saveUserDocumentService.getUserDocumentByDocId(selectedDocumentID).getUserDocumentName();
					String removedDocSharedByEmail = saveUserDocumentService.getUserDocumentByDocId(selectedDocumentID).getUserDocumentOwneremail();
					boolean removeListSuccessful = saveUserDocumentService.removeUserDocument(selectedDocumentID);
					if(removeListSuccessful==true) 
						{
							request.setAttribute("sharedDocumentRemoved", "true");
							request.setAttribute("removedSharedDocumentName",removedDocName );
							request.setAttribute("removedDocSharedByEmail",removedDocSharedByEmail );
							
						}
			}	
			  request.getSession().setAttribute("MyDocumentsClicked", "true");
		      List<UserDocument> userDocuments = (List<UserDocument>) saveUserDocumentService.getUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
		      request.getSession().setAttribute("userDocuments", userDocuments);
		      List<UserDocument> sharedUserDocuments = (List<UserDocument>) saveUserDocumentService.getSharedUserDocuments(user.getUser_email());//saveUserListService.getUserLists(user.getUser_email());
		      request.getSession().setAttribute("sharedUserDocuments", sharedUserDocuments);
		      if(callFromLoginStatus) ;
		      else
		      {FG_Util fg_util = new FG_Util(); 
		      fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");
		      //"/FindGoose_Home.jsp");}
		      }
		}		
	} catch (ClientAbortException e) {
		//e.printStackTrace();
	}catch (Exception e) {
		e.printStackTrace();
	}
}

}
