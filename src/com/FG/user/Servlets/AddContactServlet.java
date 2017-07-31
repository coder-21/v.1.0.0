package com.FG.user.Servlets;

/**
 * @author Suvarna
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.FG.user.ImportContact;
import com.FG.utils.FG_Util;

/**
 * Servlet implementation class AddContactServlet
 * 
 * Fetch Json Objects from response as string
 * Display the contacts to be selected
 * Add the selected contacts to DB
 */
@WebServlet(name = "AddContacts", urlPatterns = {"/AddContacts"})
public class AddContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = request.getParameter("json");
        JSONObject jObj = new JSONObject(request.getParameter("json"));
        if(br != null){
            json = br.readLine();
        } 
        Iterator it = jObj.keys(); //gets all the keys

        while(it.hasNext())
        {
            String key = (String) it.next(); // get key
            Object o = jObj.get(key); // get value
            System.out.println(key + " : " +  o); // print the key and value
        }
        
 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 1. get received JSON data from request
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		  try {
			String decodedval=new java.net.URI(jb.toString()).getPath(); 
			decodedval=decodedval.replaceFirst("json=", "");

			JSONObject jsonObject = new JSONObject(decodedval);
		    Iterator it = jsonObject.keys(); //gets all the keys
		   
		    Object o=null;
		    
		   

	        while(it.hasNext())
	        {
	            String key = (String) it.next(); // get key
	             // get value
	            if(key.equals("feed")){
	            	o = jsonObject.get(key);
	            System.out.println(key + " : " +  o);
	            break;
	            }// print the key and value
	        }
	        
	        JSONObject jsonObj = new JSONObject(o.toString());
	        it=jsonObj.keys();
	        Set<String> keys=jsonObj.keySet();
	        Object entry=jsonObj.get("entry");
	        
	        JSONArray json= new JSONArray(entry.toString());


	            // looping through All Contacts
	        	ImportContact importList[]=new ImportContact[json.length()];
	                for(int i = 0; i < json.length(); i++){
	                  JSONObject contacts = json.getJSONObject(i);
	                  //[link, gd$phoneNumber, id, category, title, updated]

	                    
	                  	Object obj=contacts.get("gd$phoneNumber");
	                  	String keyvalue=obj.toString().replaceAll("\\[", "").replaceAll("\\]","");
	                  	JSONObject number= new JSONObject(keyvalue);
	                  	Object phNumber = number.get("$t");
	                  	
	                    obj= contacts.get("id");
	                    keyvalue=obj.toString().replaceAll("\\[", "").replaceAll("\\]","");
	                  	JSONObject id= new JSONObject(keyvalue);
	                    Object userId = id.get("$t"); 
	                    
	                    obj= contacts.get("title");
	                    keyvalue=obj.toString().replaceAll("\\[", "").replaceAll("\\]","");
	                  	JSONObject uname= new JSONObject(keyvalue);
	                    Object userName = uname.get("$t");
	                    importList[i]=  new ImportContact(userName.toString(), userId.toString(), phNumber.toString());
	                }
	            
		    request.getSession().setAttribute("GoogleContacts", importList);
		    FG_Util fg_util = new FG_Util(); fg_util.passSearchResultsToJSP(request, response,
					"/FindGoose_Dashboard.jsp");
		  } catch (Exception e) {
		    // crash and burn
			  e.printStackTrace();
		    throw new IOException("Error parsing JSON request string");
		  }
       
        // 2. initiate jackson mapper
//        ObjectMapper mapper = new ObjectMapper();
// 
//        // 3. Convert received JSON to Article
//        Article article = mapper.readValue(json, Article.class);
// 
//        // 4. Set response type to JSON
//        response.setContentType("application/json");            
// 
//        // 5. Add article to List<Article>
//        articles.add(article);
// 
//        // 6. Send List<Article> as JSON to client
//        mapper.writeValue(response.getOutputStream(), articles);
        }

}
