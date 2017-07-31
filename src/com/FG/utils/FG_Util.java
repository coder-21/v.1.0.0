/**
 * Author : Diana
 * Created Date : 10-04-2016
 * @copyright FindGoose
 * 
 * Suvarna:
 * Set the attribute for session
 */
package com.FG.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FG_Util {
	
	//This method posts the request attributes required to dynamically display the search content in the same jsp from which the search is performed.
		public void passSearchResultsToJSP(HttpServletRequest request, HttpServletResponse response, String redirect2JSP) 
				throws ServletException, IOException {
			//Setting session attribute to restrict page reload 
			request.getSession().setAttribute("InitialLoad",true);
			RequestDispatcher dispatcher = request.getRequestDispatcher(redirect2JSP);
	         dispatcher.forward( request, response);
		}

}
