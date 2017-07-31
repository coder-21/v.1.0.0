package com.FG.user.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.FG.user.TasksTableInfo;
import com.FG.user.UserRegistrationInfo;
import com.FG.utils.FG_Util;
import com.FG.utils.HibernateUtil;
import com.FG.utils.MyTaskUtil;

/**
 * Servlet implementation class MyTaskServlet
 */
@WebServlet("/MyTaskServlet")
public class MyTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyTaskServlet() {
        super();
        
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	MyTaskUtil utilObj= new MyTaskUtil();
    	UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
    	List<TasksTableInfo> tasksLists=utilObj.getTasks( "from TasksTableInfo where task_assignto='"+user.getUser_email()+"'");
    	List<TasksTableInfo> assignedLists=utilObj.getTasks( "from TasksTableInfo where task_createdby='"+user.getUser_email()+"'");
    
    	request.getSession().setAttribute("TasksSet", "true");
		request.getSession().setAttribute("Tasks", tasksLists);
		request.getSession().setAttribute("AssignedTasks",assignedLists);
    	
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("TaskID")!=null||request.getAttribute("TaskID")!=null){
			int taskID=Integer.parseInt(request.getParameter("TaskID"));
			if(request.getParameter("taskStatusDropdown")!=null){
			String taskState= (String) request.getParameter("taskStatusDropdown");
			MyTaskUtil utilObj=new MyTaskUtil();
			utilObj.updateStatus(taskID,taskState);
			request.setAttribute("TaskMessage", "The task "+taskID+" has been updated successfully");
			}
			
		}
		
		processRequest(request, response);
		
		request.setAttribute("TasksClicked", "true");
		
		FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
				"/FindGoose_Dashboard.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
	String member=	(String)request.getParameter("teamMembersDropdown");
	String taskName=	(String)request.getParameter("taskName");
	String taskDescription=	(String)request.getParameter("taskDescriptionTextAreaName");
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	TasksTableInfo task= new TasksTableInfo();
	task.setCreatedby(user.getUser_email());
	task.setAssignto(member);
	task.setDesc(taskDescription);
	task.setStatus("open");
	task.setTask_date(sqlDate);
	task.setTask_name(taskName);
	Session session = HibernateUtil.openSession();
	 Transaction tx = null;	
	 try {
		 tx = session.getTransaction();
		 tx.begin();
		 session.saveOrUpdate(task);		
		 tx.commit();
	 } catch (Exception e) {
		 if (tx != null) {
			 tx.rollback();
		 }
		 e.printStackTrace();
	 } finally {
		 session.close();
	 }	
	 request.setAttribute("TaskMessage", "The task "+taskName+" has been created");
	 request.setAttribute("TasksClicked", "true");
	 processRequest(request, response);
	 FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
				"/FindGoose_Dashboard.jsp");
	}

}
