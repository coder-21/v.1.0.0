/*Author : Diana P Lazar
Date created : 05/13/2016
Copyright@ FindGoose
*/

package com.FG.user.Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.events.EventsEnrolled;
import com.FG.events.EventsService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;
import com.FG.utils.StringUtil;

/**
 * Servlet implementation class EventsServlet
 */
@WebServlet(name="EventsServlet", urlPatterns = {"/Events"})
public class EventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequestForEvents(request, response, false);
	}

	/**
	 * @param request
	 * @param response
	 * @param callFromLoginStatus
	 * @throws ServletException
	 * @throws IOException
	 */
	public void processRequestForEvents(HttpServletRequest request, HttpServletResponse response,
			boolean callFromLoginStatus) throws ServletException, IOException {
		request.getSession().setAttribute("eventsClicked", "true");
		String selectedEventId = null; 
		EventsService eventService = new EventsService();
		if(request.getParameter("removeEventSelected")!=null)
		{
			selectedEventId = request.getParameter("selectedEventID");
			boolean removeEventSuccessful = eventService.removeSelectedEvent(selectedEventId);
			if(removeEventSuccessful==true) 
				{
					request.setAttribute("eventRemoved", "true");
				}
		}
		if(callFromLoginStatus) ;
	      else
	      {FG_Util fg_util = new FG_Util(); 
		  fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.getSession().setAttribute("eventsClicked", "true");
			UserRegistrationInfo  user = (UserRegistrationInfo) request.getSession().getAttribute("user");
			EventsService eventsService = new EventsService();
			
			String location = request.getParameter("locations");
			String eventName = request.getParameter("eventName");
			String eventURL = request.getParameter("eventURL");
			Date eventDate = StringUtil.processStringToDate(request.getParameter("datepicker"));
			
			EventsEnrolled eventCreated = new EventsEnrolled(eventName, location, eventDate, eventURL, user.getUser_email());
			boolean eventSaved = eventsService.saveEventToDB(eventCreated);
		
	        if(eventSaved){
	        	request.setAttribute("eventsCreated", "true");
	        }else{
		        request.setAttribute("eventsCreationFailed", "true");
		        request.setAttribute("FailedReason", "Event creation failed for unknown reason. Please try again!");
	        }
			
			FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,"/FindGoose_Dashboard.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
