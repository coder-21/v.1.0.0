package com.FG.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;


public class SearchUtil_2{
	private static IndexSearcher indexSearcher,indexSearcherInvestments = null;
	private static ScoreDoc[] hit,hitInvestments={};
	List<Map<String, Object>> doc;
	private static Document[] docInvestments;
	public static List<Map<String,Object>> docExportList;
	//docUserListCompanies;
	public static List<Map<String,Object>> docUserListCompanies;
	public static List<Map<String,Object>> mapList ;
	private static String[] selectedList = null;
	private static String selectedListDocIDs;
  
	public static String getSelectedListDocIDs() {
		String selListDocIDs = selectedListDocIDs;
		selectedListDocIDs = "";
		return selListDocIDs;
	}
	private static void setSelectedListDocIDs(StringBuffer selectedListDocIDs2) {
		selectedListDocIDs = selectedListDocIDs2.toString();
	}
	public static void setDocumentHitsSearcher(IndexSearcher searcher, ScoreDoc[] hits, Document[] d) {
		indexSearcher = searcher;		
		hit = hits;
		doc=d;
	}
	public static void setInvestmentsDocumentHits(IndexSearcher searcher, ScoreDoc[] hits, Document[] d) {
		indexSearcherInvestments = searcher;
		hitInvestments = hits;
		docInvestments=d;
	}
	
	public static IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}
	public static void setIndexSearcher(IndexSearcher indSearcher) {
		indexSearcher = indSearcher;
	}
	
	public static IndexSearcher getindexSearcherInvestments() {
		return indexSearcherInvestments;
	}
	public static void setindexSearcherInvestments(IndexSearcher indSearcher) {
		indexSearcherInvestments = indSearcher;
	}
	
	public static ScoreDoc[] getHit() {
		return hit;
	}
	public static ScoreDoc[] getHitInvestments() {
		return hitInvestments;
	}
	public static void setHit(ScoreDoc[] hits) {
		hit = hits;
	}
	public static Document[] getDoc() {
		return doc;
	}
	public static void setDoc(List<Map<String, Object>> mapList2) {
		doc = mapList2;
	}
	
	public static Document[] getInvestmentsDoc() {
		return docInvestments;
	}
	public static void setInvestmentsDoc(Document[] document) {
		docInvestments = document;
	}
	
//	This method sets lucene search result Document for Basic Search of Company/Investor
	//This method is also used to access 
	//1)Team members  &
	public static void setDocumentListFromLuceneSearch1(String query)
	{
	try {
		String fileurlStr = "";
		   if(isInteger(query,10))
				{fileurlStr = ".//LuceneCompanyEmployeesDocument";}
				else {fileurlStr = ".//LuceneIndex";}
//		   URL fileurl=IndexRead.class.getClassLoader().getResource(fileurlStr);
		   URL fileurl=SearchUtil_2.class.getResource("SearchUtil.class");
		   
		   Path path = Paths.get(fileurl.toURI());
		   String pathStr = (path.getParent().getParent().getParent().getParent().getParent() + "\\LuceneDocs" + fileurlStr).toString();
	       StandardAnalyzer analyzer = new StandardAnalyzer();
	       Directory directory = FSDirectory.open(new File(pathStr).toPath());
	      //The column to search on
	       Query docQuery = new QueryParser("content", analyzer).parse(query);
	       int hitsPerPage = 50;
	       IndexReader reader=DirectoryReader.open(directory);
	       IndexSearcher searcher = new IndexSearcher(reader);
	       TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
	       searcher.search(docQuery, collector);
	       ScoreDoc[] hits = collector.topDocs().scoreDocs;
	       doc = new Document[hits.length];
	       setDocumentHitsSearcher(searcher, hits,doc);
	       for(int i=0;i<hits.length;++i) {
	           int docId = hits[i].doc;
	            	doc[i] = indexSearcher.doc(docId);
				} 
	       setDocumentIsNull(doc);
	       setDoc(doc);
		}
		catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Client getClient() {
        final Settings.Builder settings = Settings.builder();
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
        try {
			transportClient = TransportClient.builder().build()
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		        
		return transportClient;
    }
	public static void setDocumentListFromLuceneSearch(String query)
	{
	try {
		
		SearchResponse response = getClient().prepareSearch("fgcompanies")
    		    .setSearchType(SearchType.QUERY_AND_FETCH)
    		    .setQuery(QueryBuilders.queryStringQuery(query).field("_all"))
    		    .setFrom(0).setSize(60).setExplain(true)
    		    .execute()
    		    .actionGet();
    		SearchHit[] results = response.getHits().getHits();
    		for (SearchHit hit : results) {
     		  System.out.println(hit.getSourceAsString()); 
    		  Map<String,Object> result = hit.getSource();
    		  mapList.add(result);
    		}
    			
	       setDocumentIsNull(mapList);
	       setDoc(mapList);
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	//This method is also used to access 
		//2) Investments of the Company
		public static void setInvestmentsDocumentListFromLuceneSearch(String query)
		{
		try {
			String fileurlStr = ".//LuceneCompanyInvestmentsDocument";
//			URL fileurl=IndexRead.class.getClassLoader().getResource(fileurlStr);
			URL fileurl=SearchUtil_2.class.getResource("SearchUtil.class");

			Path path = Paths.get(fileurl.toURI());
			   String pathStr = (path.getParent().getParent().getParent().getParent().getParent() + "\\LuceneDocs" + fileurlStr).toString();
		       StandardAnalyzer analyzer = new StandardAnalyzer();
		       Directory directory = FSDirectory.open(new File(pathStr).toPath());
		      //The column to search on
		       Query docQuery = new QueryParser("content", analyzer).parse(query);
		       int hitsPerPage = 50;
		       IndexReader reader=DirectoryReader.open(directory);
		       IndexSearcher searcher = new IndexSearcher(reader);
		       TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		       searcher.search(docQuery, collector);
		       ScoreDoc[] hits = collector.topDocs().scoreDocs;
		       docInvestments = new Document[hits.length];
		       setInvestmentsDocumentHits(searcher, hits,docInvestments);
		       for(int i=0;i<hits.length;++i) {
		           int docId = hits[i].doc;
		           docInvestments[i] = indexSearcherInvestments.doc(docId);
					} 
		       setInvestmentsDocumentNull(docInvestments);
		       setInvestmentsDoc(docInvestments);
			}
			catch (IOException | ParseException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static boolean isDocumentNull,isInvestmentsDocumentNull=true;
	private static void setDocumentIsNull(List<Map<String, Object>> maplist) {
		if(maplist.size()<=0) isDocumentNull = true;
		else isDocumentNull = false;		
	}
	private static void setInvestmentsDocumentNull(Document[] doc2) {
		if(docInvestments.length<=0) isInvestmentsDocumentNull = true;
		else isInvestmentsDocumentNull = false;		
	}
	
	public static void passSearchResultsToJSP(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, String redirect2JSP) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher(redirect2JSP);//request.getRequestDispatcher(redirect2JSP);
         dispatcher.forward( request, response);
	}
	
	public static void setExportListString(String[] arrSplit) { 
		selectedList = arrSplit;		
	}
		
//This method returns the Selected list of Company Documents from the Lucene Search-Results
	
	public static List<Map<String, Object>> getExportListDocArray() {
		
			StringBuffer selectedListDocIDs = new StringBuffer();
			 
		       if(selectedList!=null)
		       {	
		    	   
		    	   for (int i = 0; i < selectedList.length; i++) 
		    	   {
		    		   SearchResponse response = getClient().prepareSearch("fgcompanies")
				    		    .setSearchType(SearchType.QUERY_AND_FETCH)
				    		    .setQuery(QueryBuilders.queryStringQuery(selectedList[i]).field("_all"))
				    		    .setFrom(0).setSize(60).setExplain(true)
				    		    .execute()
				    		    .actionGet();
				    		SearchHit[] results = response.getHits().getHits();
				    		 Map<String,Object> result=null;
				    		for (SearchHit hit : results) {
				    		  result = hit.getSource(); //the retrieved document
				    		  if((result.get("name").toString()).equalsIgnoreCase(selectedList[i])){
				    			  docExportList.add(result);
				    			  selectedListDocIDs.append(hit.getId());
				    		  }
				    		  
				    		}			       
		    	   }
		       }
		       setSelectedListDocIDs(selectedListDocIDs);
		
			
			return docExportList;
	}
	
	//This method returns the details of Companies in the Selected list in Lucene Document format
		public static List<Map<String, Object>> getUserListDocArray(String[] selectedListCompanies) {
			
				
			       if(selectedListCompanies!=null)
			       {	
			    	   for (int i = 0; i < selectedListCompanies.length; i++) 
			    	   {
			    		   SearchResponse response = getClient().prepareSearch("fgcompanies")
					    		    .setSearchType(SearchType.QUERY_AND_FETCH)
					    		    .setQuery(QueryBuilders.queryStringQuery(selectedListCompanies[i]).field("_all"))
					    		    .setFrom(0).setSize(60).setExplain(true)
					    		    .execute()
					    		    .actionGet();
					    		SearchHit[] results = response.getHits().getHits();
					    		 Map<String,Object> result=null;
					    		for (SearchHit hit : results) {
					    		  System.out.println(hit.getSourceAsString());
					    		  result = hit.getSource(); //the retrieved document
					    		  if((result.get("name").toString()).equalsIgnoreCase(selectedListCompanies[i])){
					    			  docUserListCompanies.add(result);
					    		  }
					    		  
					    		}			       
			    	   }
			       }
				
				
				return docUserListCompanies;
		}
	
	public static IndexSearcher getCompaniesDocumentIndexSearcher()
	{
		IndexSearcher searcher=null;
		String fileurlStr = ".//LuceneIndex";
//		URL fileurl=IndexRead.class.getClassLoader().getResource(fileurlStr);
		URL fileurl=SearchUtil_2.class.getResource("SearchUtil.class");

		try {
		Path path = Paths.get(fileurl.toURI());
		   String pathStr = (path.getParent().getParent().getParent().getParent().getParent() + "\\LuceneDocs" + fileurlStr).toString();
		
	    	Directory directory = FSDirectory.open(new File(pathStr).toPath());
			
			IndexReader reader=DirectoryReader.open(directory);
			searcher = new IndexSearcher(reader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return searcher;		
	}
	
	//This method returns the Lucene Document for the company selected from the search Results on WebApp
			public static Map<String, Object> getDocumentForSelectedCompany(String selectedCompany) {
				SearchResponse response = getClient().prepareSearch("fgcompanies")
		    		    .setSearchType(SearchType.QUERY_AND_FETCH)
		    		    .setQuery(QueryBuilders.queryStringQuery(selectedCompany).field("_all"))
		    		    .setFrom(0).setSize(60).setExplain(true)
		    		    .execute()
		    		    .actionGet();
		    		SearchHit[] results = response.getHits().getHits();
		    		 Map<String,Object> result=null;
		    		for (SearchHit hit : results) {
		    		  System.out.println(hit.getSourceAsString());
		    		  result = hit.getSource(); //the retrieved document
		    		  if((result.get("name").toString()).equalsIgnoreCase(selectedCompany)){
		    			  return result;
		    		  }
		    		  
		    		}

					return null;
			}
	

		@SuppressWarnings("resource")
		public static boolean isInteger(String s, int radix) {
		    Scanner sc = new Scanner(s.trim());
		    if(!sc.hasNextInt(radix)) return false;
		    // we know it starts with a valid int, now make sure
		    // there's nothing left!
		    sc.nextInt(radix);
		    return !sc.hasNext();
		}
		
		//This method returns the Lucene Document for the Investor selected from the search Results on WebApp
		public static Map<String, Object> getDocumentForSelectedInvestor(String selectedInvestor) {
			SearchResponse response = getClient().prepareSearch("fginvestors")
	    		    .setSearchType(SearchType.QUERY_AND_FETCH)
	    		    .setQuery(QueryBuilders.queryStringQuery(selectedInvestor).field("_all"))
	    		    .setFrom(0).setSize(60).setExplain(true)
	    		    .execute()
	    		    .actionGet();
	    		SearchHit[] results = response.getHits().getHits();
	    		 Map<String,Object> result=null;
	    		for (SearchHit hit : results) {
	    		  System.out.println(hit.getSourceAsString());
	    		  result = hit.getSource(); //the retrieved document
	    		  if((result.get("investor_name").toString()).equalsIgnoreCase(selectedInvestor)){
	    			  return result;
	    		  }	    		  
	    		}
				return null;
		
		}
}
