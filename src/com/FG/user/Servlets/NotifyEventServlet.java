/*Author : Diana P Lazar
Date created : Aug 14th 2016
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

import com.FG.events.EventsEnrolled;
import com.FG.events.EventsService;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;
import com.FG.utils.emailTempPassword.SendInviteToFG;

/**
 * Servlet implementation class ShareUserDocumentServlet
 */
@WebServlet("/NotifyEvent")
public class NotifyEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotifyEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String notifiedEvent_Comment = (String) request.getParameter("eventCommentTextAreaName");
		UserRegistrationInfo user = (UserRegistrationInfo)request.getSession().getAttribute("user");
		
		EventsService eventService = new EventsService();
		EventsEnrolled notifiedEvent = new EventsEnrolled();
		
		request.getSession().setAttribute("eventsClicked", "true");
		List<EventsEnrolled> allEventsFromDB = (List<EventsEnrolled>)eventService.getListOfEventsInDB();
		request.setAttribute("allEventsFromDB", allEventsFromDB);
	    int selectedEventId = (int) request.getSession().getAttribute("selectedEventIdSessionAttr");
	    for (int i = 0; i < allEventsFromDB.size(); i++) {
			if(allEventsFromDB.get(i).getEventId()==selectedEventId)
			{
				notifiedEvent = allEventsFromDB.get(i);
				String notifiedEmail = request.getParameter("teamMembersDropdown");
				if(notifiedEmail==null || notifiedEmail=="")
					notifiedEmail = request.getParameter("friendEmail");
				if(notifiedEmail=="select")
					notifiedEmail = request.getParameter("friendEmail");
				SendInviteToFG sendToEmailService = new SendInviteToFG();
				boolean userNotifiedSuccess = sendToEmailService.notifyEvent(user, notifiedEmail, notifiedEvent_Comment, notifiedEvent);
				if(userNotifiedSuccess)
					request.setAttribute("userNotifiedSuccess","true");
				request.setAttribute("notifiedEmail", notifiedEmail);
			}
		}
	    List<EventsEnrolled> allEventsFromDB2 = (List<EventsEnrolled>) eventService.getListOfEventsInDB();
	    request.getSession().setAttribute("allEventsFromDB", allEventsFromDB2);
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
