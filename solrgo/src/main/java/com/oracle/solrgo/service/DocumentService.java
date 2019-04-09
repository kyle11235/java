package com.oracle.solrgo.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.oracle.solrgo.model.Document;
import com.oracle.solrgo.util.Config;

public class DocumentService {

	private static Logger logger = Logger.getLogger(DocumentService.class);
	private SolrClient solrj = new HttpSolrClient.Builder(Config.getValue("solr.url")).build();

	public List<Document> getDocuments(String q) {
		List<Document> out = new LinkedList<Document>();

		SolrQuery query = new SolrQuery();
		query.setQuery(q);
		try {
			QueryResponse response = solrj.query(query);
			SolrDocumentList results = response.getResults();
			for (int i = 0; i < results.size(); ++i) {
				SolrDocument doc = results.get(i);
				logger.debug(doc);
				Document document = new Document();
				document.setId(Integer.valueOf((String) doc.getFieldValue("id")));
				document.setName((String) doc.getFieldValue("name"));
				document.setInfo((String) doc.getFieldValue("info"));
				document.setUrl((String) doc.getFieldValue("url"));
				out.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
}
