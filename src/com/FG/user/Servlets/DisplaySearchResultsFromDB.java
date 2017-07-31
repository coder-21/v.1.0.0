package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FG.user.UserRegistrationInfo;
import com.FG.utils.User_DButil;

/**
 * Servlet implementation class DisplaySearchResultsFromDB
 * Author : Diana
 * Created Date : 11-11-2015
 */

@WebServlet("/DisplaySearchResultsFromDB")
public class DisplaySearchResultsFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplaySearchResultsFromDB() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User_DButil user_dbutil = new User_DButil(); 
		String queryString = "from UserRegistrationInfo where email='"+request.getParameter("")+"'";
		List<UserRegistrationInfo> names = user_dbutil.getUserResultSets(queryString);
		request.getSession().setAttribute("names", names);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
