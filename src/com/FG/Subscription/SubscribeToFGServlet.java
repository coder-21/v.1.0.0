package com.FG.Subscription;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.utils.EmailValidator;

/*Author : Diana P Lazar
Date created : 06/20/2016
Copyright@ FindGoose
*/

@WebServlet(name="SubscribeToFGServlet", urlPatterns = {"/SubscribeToFG"})
public class SubscribeToFGServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean subscribeStatus = false;
	
	public static boolean getSubscribeStatus() {
		return subscribeStatus;
	}
	public static void setSubscribeStatus(boolean subscribeStatus) {
		SubscribeToFGServlet.subscribeStatus = subscribeStatus;
	}
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		 throws ServletException, IOException {
	 response.setContentType("text/html;charset=UTF-8");
//	 PrintWriter out = response.getWriter();
	 String name = request.getParameter("name");//the strings are same as ones in jsp IDs for each text
	 String email = request.getParameter("email");
	 SubscribeService subscribeService = new SubscribeService();
	 EmailValidator validator = EmailValidator.getInstance();
	 boolean validEmail = validator.validate(email);
	 String comment = request.getParameter("comment");
	 Date subscribedDate = new Date();
	 if(!validEmail)
	 {
		 request.setAttribute("isSubscriptionError", "true");
		 request.setAttribute("SubscriptionError", "Please enter a valid email.");
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/subscribe.jsp");
         dispatcher.forward( request, response);
	 }else{	 
		 SubscriptionInfo subscriber = new SubscriptionInfo(name, email, comment, subscribedDate);
		
		 try {	
			 boolean result = subscribeService.subscribe(subscriber);
			 if(result && !getSubscribeStatus()){
				request.getSession().setAttribute("subscriber", subscriber);
				request.setAttribute("subscriberEmail", subscriber.getEmail());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/thankyou.jsp");
	 	         dispatcher.forward( request, response);
	 		 }		 
			 else if(!result){
				 request.setAttribute("isSubscriptionError", "true");
				 request.setAttribute("SubscriptionError", "You have already subscribed.");
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/subscribe.jsp");
		         dispatcher.forward( request, response);
			 }else if(getSubscribeStatus()){
				 request.setAttribute("isSubscriptionError", "true");
				 request.setAttribute("SubscriptionError", "Something went wrong :( . Please try again!");
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/subscribe.jsp");
		         dispatcher.forward( request, response);
			 }
		 } finally {	
		 }
	 }
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
