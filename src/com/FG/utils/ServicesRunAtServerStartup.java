/*Author : Diana P Lazar
Date created : 6th Apr 2016
Copyright@ FindGoose

Services that needs to run at server startup
 */

package com.FG.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

//import com.FG.lucene.services.IndexWrite;

/**
 * Servlet implementation class ServicesRunAtServerStartup
 */
//@WebServlet(value="/ServicesRunAtServerStartup", loadOnStartup=0)
@WebServlet("/ServicesRunAtServerStartup")
public class ServicesRunAtServerStartup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServicesRunAtServerStartup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void init() throws ServletException {
//		IndexWrite.main(null);
//		CompanyEmployeesWrite.main(null);
//		CompanyInvestmentsWrite.main(null);
//		InvestorsWrite.main(null);
		try {
			writeLuceneResults();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void writeLuceneResults() throws Exception{
	        //establish the mysql connection
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.119:3306/test", "diana", "lazar");
	        StandardAnalyzer analyzer = new StandardAnalyzer();
	        
	        //create a file which will hold the index files.make sur the path is correct
	       
	        
	        
//	        URL fileurl=IndexWrite.class.getClassLoader().getResource(".//Index");
	        
	        
	        File file = new File("");//fileurl.getPath());
	        
	        
	        IndexWriterConfig config = new IndexWriterConfig(analyzer);
	        IndexWriter writer = new IndexWriter(FSDirectory.open(file.toPath()), config);
	        //String query = "select * from fg_companytable";
	        Statement statement = connection.createStatement();
	        WriteLuceneDocumentForCompany(writer, "select * from fg_companytable", statement);
	        
	        statement.close();
	        writer.close();
	        connection.close();
	}

	/**
	 * @param writer
	 * @param query
	 * @param statement
	 * @throws SQLException
	 * @throws IOException
	 */
	private void WriteLuceneDocumentForCompany(IndexWriter writer, String query, Statement statement)
			throws SQLException, IOException {
		ResultSet result = statement.executeQuery(query);
		//according to the results create the index files
		while (result.next()) {
		    System.out.println("Retrieved ID:["+result.getString("name")+"]");
		    Document document = new Document();
		//add the fields to the index as you required
		    document.add(new StringField("cid", result.getString("cid"), Field.Store.YES));
		    document.add(new StringField("name", result.getString("name"), Field.Store.YES));
		    document.add(new StringField("revenue", result.getString("revenue"), Field.Store.YES));
		    document.add(new StringField("marketcap", result.getString("marketcap"), Field.Store.YES));
		    document.add(new StringField("pe_ratio", result.getString("pe_ratio"), Field.Store.YES));
		    document.add(new StringField("location", result.getString("location"), Field.Store.YES));
		    document.add(new StringField("technology", result.getString("technology"), Field.Store.YES));
		    document.add(new StringField("industry", result.getString("industry"), Field.Store.YES));
		    document.add(new StringField("company_logo", result.getString("company_logo"), Field.Store.YES));
		    document.add(new StringField("headcount", result.getString("headcount"), Field.Store.YES));
		    document.add(new StringField("investors", result.getString("investors"), Field.Store.YES));
		    document.add(new StringField("location", result.getString("location"), Field.Store.YES));
		    document.add(new StringField("technology", result.getString("technology"), Field.Store.YES));
		    document.add(new StringField("investors", result.getString("investors"), Field.Store.YES));
		    document.add(new StringField("industry", result.getString("industry"), Field.Store.YES));
		    document.add(new TextField("content", result.getString("name")+" "+result.getString("investors"), Field.Store.NO));
		    
		    document.add(new StringField("crunchbase_link", result.getString("crunchbase_link"), Field.Store.YES));
		    document.add(new StringField("twitter_link", result.getString("twitter_link"), Field.Store.YES));
		    document.add(new StringField("angel_link", result.getString("angel_link"), Field.Store.YES));
		    document.add(new StringField("linkedin_link", result.getString("linkedin_link"), Field.Store.YES));
		    document.add(new StringField("blog_link", result.getString("blog_link"), Field.Store.YES));
		    writer.addDocument(document);
		    
		}
	}

}
