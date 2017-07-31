/**
 * Author:Suvarna
 * Date:Oct 3 2016
 */
package com.FG.user.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.utils.FG_Util;
import com.FG.utils.HibernateUtil;
import com.FG.utils.emailTempPassword.SendInviteToFG;
import com.FG.visitor.VisitorMessageInfo;

/**
 * Servlet implementation class VisitorMessageServlet
 */
@WebServlet("/VisitorMessageServlet")
public class VisitorMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitorMessageServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email,name,message;
		email=request.getParameter("Email");
		name=request.getParameter("Name");
		message=request.getParameter("Message");
		VisitorMessageInfo messageobj=new VisitorMessageInfo();
		messageobj.setVisitor_email(email);
		messageobj.setVisitor_name(name);
		messageobj.setVisitor_message(message);
		Session session = HibernateUtil.openSession();
		 Transaction tx = null;	
		 try {
			 tx = session.getTransaction();
			 tx.begin();
			 session.saveOrUpdate(messageobj);		
			 tx.commit();
		 } catch (Exception e) {
			 if (tx != null) {
				 tx.rollback();
			 }
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }	
		 SendInviteToFG sendMailobj=new SendInviteToFG();
		 sendMailobj.SendVisitorMessage(email, name, message);
		 
		 request.getSession().setAttribute("visitormessage", "true");
		 FG_Util utilObj=new FG_Util();
		 utilObj.passSearchResultsToJSP(request, response, "/FindGoose_WelcomePage.jsp");	
		 
	}

}
