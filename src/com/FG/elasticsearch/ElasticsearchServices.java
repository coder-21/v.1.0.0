package com.FG.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class ElasticsearchServices {

    private static final String ID_NOT_FOUND = "<ID NOT FOUND>";

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

    public static void main(final String[] args) throws IOException, InterruptedException {

        final Client client = getClient();
        // Create Index and set settings and mappings
        final String indexName = "fgcompanies";
        final String documentType = "companies";
       // final String documentId = "1";
        //final String fieldName = "foo";
        //final String value = "bar";

        final IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
        if (res.isExists()) {
            final DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(indexName);
            delIdx.execute().actionGet();
        }

        final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);

        // MAPPING GOES HERE

        final XContentBuilder mappingBuilder = jsonBuilder().startObject().startObject(documentType)
                .startObject("_ttl").field("enabled", "true").field("default", "1s").endObject().endObject()
                .endObject();
        System.out.println(mappingBuilder.string());
        createIndexRequestBuilder.addMapping(documentType, mappingBuilder);

        // MAPPING DONE
        createIndexRequestBuilder.execute().actionGet();

        for(int i=0;i<10;i++){
        // Add documents
        final IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indexName, documentType,"doc"+i );
        // build json object
        final XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();
        contentBuilder.field("id", i);
        contentBuilder.field("name", String.valueOf(i));

        indexRequestBuilder.setSource(contentBuilder);
        indexRequestBuilder.execute().actionGet();
        }

        // Get document
        //System.out.println(getValue(client, indexName, documentType, "doc"+2 , String.valueOf(2)));
        searchDoc(String.valueOf(2),client);

            searchDoc(String.valueOf(2),client);
            
       
    }

       
    public static void searchDoc(String name,Client client){
    	SearchResponse response = client.prepareSearch("fgcompanies")
    		    .setSearchType(SearchType.QUERY_AND_FETCH)
    		    .setQuery(QueryBuilders.queryStringQuery(name).field("name"))
    		    .setFrom(0).setSize(60).setExplain(true)
    		    .execute()
    		    .actionGet();
    		SearchHit[] results = response.getHits().getHits();
    		for (SearchHit hit : results) {
     		  System.out.println(hit.getId());
//    		  Map<String,Object> result = hit.getSource();   //the retrieved document
    		}
    			    		
    }

}
