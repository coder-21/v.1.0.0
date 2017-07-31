package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.UserContactsInfo;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.Contacts_DBUtil;
import com.FG.utils.FG_Util;
import com.FG.utils.HibernateUtil;
import com.FG.utils.emailTempPassword.SendInviteToFG;

/**
 * Servlet implementation class ContactsServlet
 */
@WebServlet("/ContactsServlet")
public class ContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("removeContactSelected")!=null){
			int id=Integer.parseInt(request.getParameter("selectedContactID"));
			String contactEmail=request.getParameter("selectedContactemail");
			removeContact(id);
			request.setAttribute("ContactMessage", "Contact "+contactEmail+" has been removed successfully from your contacts list");
		}
		else if(request.getParameter("InviteToFG")!=null){
			String contactEmail=request.getParameter("selectedContactemail");
			UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
			String userEmail=userA.getUser_email();
			SendInviteToFG.send(contactEmail, userEmail);
			request.setAttribute("ContactMessage", "Contact "+contactEmail+" has been requested to join FindGoose");
		}
		viewContacts(request, response);
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");
	}

	protected void viewContacts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("contactsSet", "true");
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
		Contacts_DBUtil utilObj= new Contacts_DBUtil();
		request.getSession().setAttribute("userContacts", utilObj.getUserContacts(userA.getUser_email()));
	}

	
	protected void removeContact(int id) {
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 Query query = session.createQuery("delete from UserContactsInfo where contact_id='"+id+"'");
			 query.executeUpdate();
			 
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
			
		 } finally {
			 session.close();
		 }
		 
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String name=request.getParameter("contactName");
		String number=request.getParameter("contactNumber");
		String email=request.getParameter("contactEmail");
		UserRegistrationInfo userA = (UserRegistrationInfo) request.getSession().getAttribute("user");
		UserContactsInfo contactInfo=new UserContactsInfo();
		contactInfo.setContact_email(email);
		contactInfo.setContact_name(name);
		contactInfo.setUser_email(userA.getUser_email());
		contactInfo.setContact_number(number);
		
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 session.saveOrUpdate(contactInfo);		
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }	
		 request.setAttribute("ContactMessage", "Contact "+email+"has been added successfully to your contacts list");
		 viewContacts(request, response);
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_Dashboard.jsp");
		
	}

}
