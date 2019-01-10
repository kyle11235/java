package com.oracle.hcm.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.StringUtils;

import com.oracle.hcm.model.Employee;
import com.oracle.hcm.util.Config;

public class EmployeeSearchService {

	private static Logger logger = Logger.getLogger(EmployeeSearchService.class);
	private SolrClient solrj = new HttpSolrClient.Builder(Config.getValue("solr.url")).build();

	public List<Employee> getEmployees(String q) {
		List<Employee> out = new LinkedList<Employee>();
		Set<Integer> set = new HashSet<Integer>();

		SolrQuery query = new SolrQuery();
		query.setQuery(q);
		query.setRows(Integer.parseInt(Config.getValue("query.rows")));
		query.setHighlight(true);
		query.setParam("hl.fl", "*");
		try {
			QueryResponse response = solrj.query(query);
			SolrDocumentList results = response.getResults();
			logger.debug("solr got results size=" + results.size());
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			for (int i = 0; i < results.size(); ++i) {
				SolrDocument doc = results.get(i);
				Integer personNumber = Integer.valueOf((String) doc.getFieldValue("id"));
				if (set.contains(personNumber)) {
					continue;
				}
				Employee employee = new Employee();
				employee.setPerson_number(personNumber);
				// highlights
				Map<String, List<String>> highlightsInEnglish = highlighting.get((String) doc.getFieldValue("docid"));
				Map<String, List<String>> highlightsInJapanese = new HashMap<String, List<String>>();
				for (Entry<String, List<String>> entry : highlightsInEnglish.entrySet()) {
					String translation = Config.getValue("highlight." + entry.getKey());
					if (!StringUtils.isEmpty(translation)) {
						highlightsInJapanese.put(translation, entry.getValue());
					}
				}
				employee.setHighlights(highlightsInJapanese);

				out.add(employee);
				set.add(personNumber);
				if (set.size() >= Integer.parseInt(Config.getValue("query.count"))) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
}
