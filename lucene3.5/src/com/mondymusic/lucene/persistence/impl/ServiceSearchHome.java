package com.mondymusic.lucene.persistence.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

import com.mondymusic.lucene.model.service.IService;
import com.mondymusic.lucene.model.service.impl.ServiceImpl;
import com.mondymusic.lucene.persistence.IServiceHome;
import com.mondymusic.lucene.persistence.IServiceSearchHome;
import com.mondymusic.lucene.pojo.service.Service;
import com.mondymusic.lucene.test.textsearch.impl.ServiceSynonymSource;
import com.mondymusic.lucene.test.textsearch.util.SMPQueryParser;

public class ServiceSearchHome extends ASearchHome<Service, IService> implements IServiceSearchHome{

	private String basePath = "D:/1000/Lucene3.5/src/indexFile";

	private IServiceHome serviceHome = new ServiceHome();

	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_WEIGHT = "weight";

	//not used
	public static final String FIELD_TRANSLATIONS = "translations";
	
	private Object LOCK = new Object();

	private void addDocument(IndexWriter indexWriter, Locale locale, Long serviceId) throws CorruptIndexException, IOException {
		// log.debug("service text search - indexing services ... ");

		long start = System.currentTimeMillis();
		List<Service> serviceList = serviceHome.getServiceList();

		System.err.println("service text search - selecting services ... (" + (System.currentTimeMillis() - start) + ") ms");
		System.err.println("service text search - indexing " + serviceList.size() + " services ");

		// log.debug("service text search - indexing "+os.size()+" services ");
		for (Service s : serviceList) {

			Long id = s.getId();
			String name = s.getName();
			Integer weight = s.getWeight();

			Document doc = new Document();
			doc.add(new NumericField(FIELD_ID, Field.Store.YES, true).setLongValue(id));
			doc.add(new Field(FIELD_NAME, name, Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new NumericField(FIELD_WEIGHT, Field.Store.YES, true).setIntValue(weight));

			// cobus
			// doc.setBoost(weight);

			// log.debug("ADDING SERVICE: id  "+lastId);
			// log.debug("ADDING SERVICE: n   "+serviceName);
			// log.debug("ADDING SERVICE: weight "+weight);
			indexWriter.addDocument(doc);
		}
	}

	@Override
	public String getBasePath() {
		return basePath;
	}

	@Override
	public void rebuildIndex(Locale locale) throws Exception {
		// log.debug("service text search - full rebuild ... ");
		try {
			synchronized (LOCK) {
				// current time as folder name
				String newFolderName = System.currentTimeMillis() + "";
				// use temp folder in order not to disturb searching
				String newIndexPath = getIndexPath(locale) + File.separator + INDEX_PATH_TEMP_PREFIX + newFolderName;
				File f = new File(newIndexPath);
				f.mkdirs();
				IndexWriter indexWriter = createIndex(locale, f).createWriter();
				// log.debug("service text search - FULL REBUILD");
				addDocument(indexWriter, locale, null);
				// log.debug("service text search - full rebuild ... DONE");
				indexWriter.commit();
				indexWriter.close();
				// change the temp folder name and delete old index files
				refactorIndex(locale, newFolderName);
			}
		} catch (CorruptIndexException e) {
			// log.error("corrupt index",e);
			throw new Exception("corrupt index file");
		} catch (Exception e) {
			// log.error("rebuild index error ",e);
			throw new Exception("rebuild index error");
		}
		// log.debug("finish rebuild services index===============================");
	}

	@Override
	public List<IService> search(Locale locale, Query query, Integer maxResults) throws Exception {
		List<IService> out = new ArrayList<IService>();
		try {

			// cobus sort
			// Sort sort = new Sort(new SortField(FIELD_WEIGHT,new
			// WeightComparatorSource(),true));

			for (Document doc : searchInternal(locale, query, maxResults)) {
				IService serviceImpl = new ServiceImpl(Long.parseLong(doc.get(FIELD_ID)), doc.get(FIELD_NAME), Integer.parseInt(doc.get(FIELD_WEIGHT)));
				out.add(serviceImpl);
			}
		} catch (NumberFormatException e) {
			// log.error("number format error ",e);
			throw new Exception("number format error ");
		} catch (CorruptIndexException e) {
			// log.error("corrupt index",e);
			throw new Exception("corrupt index file");
		} catch (IOException e) {
			// log.error("search from index error",e);
			throw new Exception("search from index error");
		}
		return out;
	}
	public List<IService> search(Locale locale, boolean advanced,  String queryString,  Integer maxResults) throws Exception {

		BooleanQuery outQuery=new BooleanQuery();

		//advanced is to use query parser
		if(advanced) {
			if(queryString != null && queryString.trim().length()>0) {
				//log.debug("non-empty query (advanced) - with query string ");
				BooleanQuery bq = new BooleanQuery();
				try {
					
					//QueryParser qp = new SMPQueryParser(version,FIELD_TRANSLATIONS,getAnalyzer(locale),new String[]{FIELD_ID,FIELD_WEIGHT});
					QueryParser qp = new QueryParser(version, FIELD_NAME, getAnalyzer(locale));
					
					bq.add(qp.parse(queryString), BooleanClause.Occur.MUST);
				} catch (Exception e) {
					throw new Exception("unable to parse query",e);
				}
				outQuery.add(bq,BooleanClause.Occur.MUST);
			} else {
				//log.debug("non-empty query (advanced) - no restriction");
			}

		}//else use normalizer 
		else {
			if(queryString == null) queryString = "";
			if (queryString.length()>0) { 
				//log.debug("non-empty query (regular)");
				BooleanQuery bq = new BooleanQuery();
				//log.debug("adding: decomposing -->"+queryString+"<-- ");
				for(String qs : getAnalyzerFactory().getNormalizer(locale).normalizeAndDecompose(queryString)) {
					//log.debug("adding: "+qs);
					//IF IS NUMBER->DVNS
					if(isNumeric(qs)){
						//addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_DVNS, qs, 40);
					} else {
						//IS TEXT->TRANSLATIONS	
						//SYNONYM
						//addPrefixQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 10);
						//addFuzzyQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 5);
						//addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 30);
		
					}
				}
				outQuery.add(bq,BooleanClause.Occur.MUST);
			}
		}
		
		return search(locale,outQuery, maxResults);
	}
	@Override
	public void updateDocument(Locale locale, Service service) throws Exception {
		if (service == null || service.getId() == null) {
			throw new Exception("missing parameter");
		}
		try {
			synchronized (LOCK) {

				IndexWriter indexWriter = getIndex(locale).createWriter();

				if (indexWriter != null) {
					Query query = NumericRangeQuery.newLongRange(FIELD_ID, 1, service.getId() - 1, service.getId() + 1, false, false);
					// log.debug("delete document service:"+service.getId());
					indexWriter.deleteDocuments(query);

					addDocument(indexWriter, locale, service.getId());
					// log.debug("delete document service and add new sevice:"+service.getId());

					indexWriter.commit();
					indexWriter.close();
					// just remove the index from the map pool
					refreshIndex(locale);
				}
			}
		} catch (Exception e) {
			// log.error("update document service:"+service.getId()+" failed !",e);
			throw new Exception("update document service:" + service.getId() + " failed !");
		}
	}
	private boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	} 
}
