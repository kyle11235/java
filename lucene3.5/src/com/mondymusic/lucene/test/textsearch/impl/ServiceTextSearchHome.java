package com.uv.smp.persistence.textsearch.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.uv.smp.exception.DatabaseException;
import com.uv.smp.exception.TextSearchException;
import com.uv.smp.model.service.IService;
import com.uv.smp.model.service.impl.ServiceImpl;
import com.uv.smp.persistence.pojos.taxonomy.Service;
import com.uv.smp.persistence.pojos.taxonomy.ServiceCategory;
import com.uv.smp.persistence.pojos.taxonomy.VehicleType;
import com.uv.smp.persistence.textsearch.IServiceChangeListener;
import com.uv.smp.persistence.textsearch.IServiceTextSearchHome;
import com.uv.smp.persistence.textsearch.ITextSearchHome;
import com.uv.smp.persistence.textsearch.util.SMPQueryParser;
import com.uv.smp.persistence.textsearch.util.impl.AnalyzerFactory;
import com.uv.smp.service.ITranslationService;
import com.uv.smp.util.LocaleUtils;
import com.uv.smp.util.logging.LogSetup;

public class ServiceTextSearchHome extends ATextSearchHome<Service, IService> implements IServiceChangeListener, IServiceTextSearchHome {

	private static Log log = LogFactory.getLog(ServiceTextSearchHome.class);
	
	private String basePath;
	
	private Float factor;
	@Autowired
	private ITextSearchHome<String,String> serviceSynonymSource;
	
	public static final String FIELD_ID="id";
	public static final String FIELD_NAME="name";
	public static final String FIELD_NAME_DISPLAY="displayName";
	public static final String FIELD_VC_PATHS="vcPaths";
	public static final String FIELD_VC_NAME="vehicleCategory";
	public static final String FIELD_SC_MAIN_PATH="scMainPath";
	public static final String FIELD_SC_MAIN_NAME="serviceCategoryName";
	public static final String FIELD_SC_SECONDARY_PATHS="scSecondaryPaths";
	public static final String FIELD_DVNS="dvns";
	public static final String FIELD_TRANSLATIONS="translations";
	public static final String FIELD_VISIBILITY="visibility";
	public static final String FIELD_WEIGHT="weight";
	
	public static final int QUERY_COUNT=100;
	private Object LOCK=new Object();
	
	private void addDocument(IndexWriter indexWriter,Locale locale, Long serviceId) throws DatabaseException, CorruptIndexException, IOException{
		log.debug("service text search - indexing services ... ");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("locale",locale.toString());
		params.put("translationType", ITranslationService.TRANSLATION_TYPE_NAME_DISPLAY);
		String query=
				" SELECT "+  
				"    service.serv_id,  service.name,  st1.translation, service.visible,  service.materialized_path, "+  
				"     group_concat(distinct vc.materialized_path SEPARATOR ' '),  " +
				" 	  group_concat(distinct sv.dvn SEPARATOR ' '), "+  
				"     group_concat(distinct st.translation SEPARATOR ' '),  " +
				"	  group_concat(distinct schs.materialized_path SEPARATOR ' '), "+  
				"     concat(vc1.name, ' ', vc.name),   "+
				"     concat(sc.name), "+
				"     service.weight "+
				" FROM   "+
				" smp_service service "+ 
				" LEFT JOIN smp_servicecategory sc ON sc.id = service.main_service_category "+ 
				" LEFT JOIN smp_service_vehiclecategory sv ON sv.serviceid = service.serv_id  "+
				" LEFT JOIN smp_vehiclecategory vc ON sv.vehiclecategoryid = vc.vehiclecategoryid "+
				" LEFT JOIN smp_vehiclecategory vc1 ON vc.parentid = vc1.vehiclecategoryid "+
				" LEFT JOIN smp_service_trans st ON st.srv_id = service.serv_id  "+
				" LEFT JOIN smp_service_trans st1 ON st1.srv_id = service.serv_id  "+
				" LEFT JOIN smp_taxonomy_translation_type tt ON st1.type = tt.id "+
				" LEFT JOIN smp_servicecategory_to_service schs ON schs.serviceid = service.serv_id "+
				" WHERE  "+
				"     st.locale = :locale AND "+
				"     st.deleted IS NULL AND "+
				"     st1.locale = :locale AND "+
				"     st1.deleted IS NULL AND "+
				"     tt.translation_type_name = :translationType AND " +
				"     service.serv_id > :lastId AND " +
				" 	  service.deleted IS NULL ";
				if(serviceId!=null){
					params.put("serviceId", serviceId);
					query=query+" AND service.serv_id= :serviceId  ";
				}
				query = query + 
				" GROUP BY  "+
				"     service.serv_id "+
				" ORDER BY "+
				"     service.serv_id ";


		int step = 50000;

		long lastId = -1l;
		
		while(true) {
			params.put("lastId",lastId);
			long start = System.currentTimeMillis();
			System.err.println("service text search - selecting services ... ");
			List<Object> os = nativeMultiQuery(query, params, Object.class, 0, step, false, null);
			System.err.println("service text search - selecting services ... ("+(System.currentTimeMillis()-start)+") ms");
			System.err.println("service text search - indexing "+os.size()+" services ");
			
			if(os.size()==0) {
				break;
			}

			log.debug("service text search - indexing "+os.size()+" services ");
			for (Object o : os) {
				Object[] outArray = (Object[]) o;
				
				Long id=((BigInteger)outArray[0]).longValue();
				lastId = id;
				String serviceName=(String)outArray[1];
				String displayName=(String)outArray[2];
				Integer visible=((Byte)outArray[3]).intValue();
				String scMainPath=(String)outArray[4];
				String vcPaths=(String)outArray[5];
				Integer weight=((Integer)outArray[11]).intValue();
				
				if(vcPaths == null) {
					vcPaths = "";
				} else if (vcPaths.length()%40 != 39) {
					throw new RuntimeException("wrong materialized path: "+vcPaths);
				}
				String dvns="";
				try {
					dvns = (String)outArray[6];	
				} catch (ClassCastException cce) {
					dvns = new String((byte[])outArray[6]);
				}
				if(dvns==null) {
					dvns = "";
				}
				String translations=(String)outArray[7];
				String scSecondaryPaths=(String)outArray[8];
				String vcName=(String)outArray[9];
				if(vcName==null) {
					vcName = "";
				}
				String scName=(String)outArray[10];

				Document doc = new Document();
				doc.add(new NumericField(FIELD_ID, Field.Store.YES, true).setLongValue(id));
				if(serviceName!=null) {
					doc.add(new Field(FIELD_NAME,serviceName,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("serviceName is NULL");
				}
				if(displayName!=null) {
					doc.add(new Field(FIELD_NAME_DISPLAY,displayName,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("displayName is NULL");
				}
				if(visible!=null) {
					doc.add(new NumericField(FIELD_VISIBILITY, Field.Store.YES, true).setIntValue(visible));
				} else {
					log.warn("visible is NULL");
				}
	
				doc.add(new Field(FIELD_SC_MAIN_PATH,scMainPath,Field.Store.YES, Field.Index.ANALYZED));
	
				doc.add(new Field(FIELD_VC_PATHS,vcPaths,Field.Store.YES, Field.Index.ANALYZED));
	
				doc.add(new Field(FIELD_DVNS,dvns,Field.Store.YES, Field.Index.ANALYZED));

				doc.add(new NumericField(FIELD_WEIGHT, Field.Store.YES, true).setIntValue(weight));
				
				//cobus
				doc.setBoost(weight*factor);
				
				if(translations!=null) {
					doc.add(new Field(FIELD_TRANSLATIONS,translations,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("translations is NULL");
				}
	
				if(scSecondaryPaths != null) {
					doc.add(new Field(FIELD_SC_SECONDARY_PATHS,scSecondaryPaths,Field.Store.YES, Field.Index.ANALYZED));
				}

				doc.add(new Field(FIELD_VC_NAME,vcName,Field.Store.YES, Field.Index.ANALYZED));
				if(scName != null) {
					doc.add(new Field(FIELD_SC_MAIN_NAME,scName,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("service category name for: "+serviceName+" is NULL");
				}
				log.debug("ADDING SERVICE: id  "+lastId);
				log.debug("ADDING SERVICE: n   "+serviceName);
				log.debug("ADDING SERVICE: dn  "+displayName);
				log.debug("ADDING SERVICE: t   "+translations);
				log.debug("ADDING SERVICE: v   "+visible);
				log.debug("ADDING SERVICE: sc1 "+scMainPath);
				log.debug("ADDING SERVICE: sc2 "+scSecondaryPaths);
				log.debug("ADDING SERVICE: weight "+weight);
				indexWriter.addDocument(doc);
			}
		}
	}
	
	@Override
	public void rebuildIndex(Locale locale) throws DatabaseException,TextSearchException {
		log.debug("service text search - full rebuild ... ");
		try {
			synchronized (LOCK) {
				String newFolderName=System.currentTimeMillis()+"";
				String newIndexPath=getIndexPath(locale)+File.separator+INDEX_PATH_TEMP_PREFIX+newFolderName;
				File f = new File(newIndexPath);
				f.mkdirs();
				IndexWriter indexWriter = createIndex(locale, f).createWriter();
				log.debug("service text search - FULL REBUILD");
				addDocument(indexWriter, locale, null);
				log.debug("service text search - full rebuild ... DONE");
				indexWriter.commit();
				indexWriter.close();
				refactorIndex(locale,newFolderName);
			}
		} catch (CorruptIndexException e) {
			log.error("corrupt index",e);
			throw new TextSearchException("corrupt index file");
		} catch (Exception e) {
			log.error("rebuild index error ",e);
			throw new TextSearchException("rebuild index error");
		}
		log.debug("finish rebuild services index===============================");
	}

	@Override
	public void updateDocument(Locale locale, Service service) throws TextSearchException{
		if(service==null || service.getId()==null){
			throw new TextSearchException("missing parameter");
		}
		try {
			synchronized (LOCK) {
				
				IndexWriter indexWriter = getIndex(locale).createWriter();

				if(indexWriter!=null) {
					Query query=NumericRangeQuery.newLongRange(FIELD_ID, 1, service.getId()-1, service.getId()+1, false, false);
					log.debug("delete document service:"+service.getId());
					indexWriter.deleteDocuments(query);
					if(service.getDeleted()==null){
						addDocument(indexWriter, locale, service.getId());
						log.debug("delete document service and add new sevice:"+service.getId());
					}
					indexWriter.commit();
					indexWriter.close();
					refreshIndex(locale);
				}
			}
		} catch (Exception e) {
			log.error("update document service:"+service.getId()+" failed !",e);
			throw new TextSearchException("update document service:"+service.getId()+" failed !");
		}
	}
	
	@Override
	public List<IService> search(Locale locale, Query query, Integer maxResults, Double cutoff) throws TextSearchException  {
		List<IService> out = new ArrayList<IService>();
		try {
			
			//cobus sort
			//Sort sort = new Sort(new SortField(FIELD_WEIGHT,new WeightComparatorSource(),true));
			
			for(Document doc : searchInternal(locale, query, maxResults, cutoff)) {
				IService serviceImpl=new ServiceImpl(Long.parseLong(doc.get(FIELD_ID)), doc.get(FIELD_NAME_DISPLAY), doc.get(FIELD_NAME_DISPLAY), Integer.parseInt(doc.get(FIELD_WEIGHT)),doc.get(FIELD_SC_MAIN_NAME), 0d, Boolean.parseBoolean(doc.get(FIELD_VISIBILITY)));
				log.debug("main path:"+doc.get(FIELD_SC_MAIN_PATH)+" secondary path:"+doc.get(FIELD_SC_MAIN_PATH));
				out.add(serviceImpl);
			}
		} catch (NumberFormatException e) {
			log.error("number format error ",e);
			throw new TextSearchException("number format error ");
		} catch (CorruptIndexException e) {
			log.error("corrupt index",e);
			throw new TextSearchException("corrupt index file");
		} catch (IOException e) {
			log.error("search from index error",e);
			throw new TextSearchException("search from index error");
		}
		return out;
	}
	
	@Override
	public List<IService> advancedSearch(Locale locale, boolean recursive, VehicleType vt, ServiceCategory scMain, ServiceCategory scSec, String queryString, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException {
		return searchInternal(locale, true, recursive, vt, scMain, scSec, queryString, visible, maxResults, cutoff);
	}

	@Override
	public List<IService> search(Locale locale, VehicleType vt, ServiceCategory scMain, ServiceCategory scSec, String queryString, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException {
		return searchInternal(locale, false, false, vt, scMain, scSec, queryString, visible, maxResults, cutoff);
	}
	
	private List<IService> searchInternal(Locale locale, boolean advanced, boolean recursive, VehicleType vt, ServiceCategory scMain, ServiceCategory scSec, String queryString, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException {

		BooleanQuery outQuery=new BooleanQuery();

		// SERVICE CATEGORY (IF ANY)
		if(scMain != null && scSec !=null) {
			throw new TextSearchException("you have to decide!");
		} else if(scMain != null) {
			String s = scMain.getMaterializedPath();
			if(recursive) {
				addPrefixQuery(outQuery, BooleanClause.Occur.MUST, FIELD_SC_MAIN_PATH, s, 1f);
			} else {
				addRegexQuery(outQuery, BooleanClause.Occur.MUST, FIELD_SC_MAIN_PATH, s+"\\.\\d{19}", 1f);
			}
		} else if(scSec != null) {
			String s = scSec.getMaterializedPath();
			if(recursive) {
				addPrefixQuery(outQuery, BooleanClause.Occur.MUST, FIELD_SC_SECONDARY_PATHS, s, 1f);
			} else {
				addRegexQuery(outQuery, BooleanClause.Occur.MUST, FIELD_SC_SECONDARY_PATHS, s+"\\.\\d{19}", 1f);
			}
		}
		
		//VEHICLE TYPE MPATHS TRIM(0,39)
		if(vt!=null){
			String s = vt.getMaterializedPath();
			if(s.length() < 39) {
				addPrefixQuery(outQuery, BooleanClause.Occur.MUST, FIELD_VC_PATHS, s, 1f);
			} else {
				s = s.substring(0,39);
				addTermQuery(outQuery, BooleanClause.Occur.MUST, FIELD_VC_PATHS, s, 1f);
			}
		}

		//SERVICE VISIBILITY
		if(visible!=null){
			Query visibleQuery=NumericRangeQuery.newIntRange(FIELD_VISIBILITY, 0,1, !visible, visible);
			outQuery.add(visibleQuery, BooleanClause.Occur.MUST);
		}

		
		
		if(advanced) {
			if(queryString != null && queryString.trim().length()>0) {
				log.debug("non-empty query (advanced) - with query string ");
				BooleanQuery bq = new BooleanQuery();
				try {
					QueryParser qp = new SMPQueryParser(version,FIELD_TRANSLATIONS,getAnalyzer(locale),new String[]{FIELD_ID,FIELD_VISIBILITY});
					bq.add(qp.parse(queryString), BooleanClause.Occur.MUST);
				} catch (Exception e) {
					throw new TextSearchException("unable to parse query",e);
				}
				outQuery.add(bq,BooleanClause.Occur.MUST);
			} else {
				log.debug("non-empty query (advanced) - no restriction");
			}

		} else {
			if(queryString == null) queryString = "";
			if (queryString.length()>0) { 
				log.debug("non-empty query (regular)");
				BooleanQuery bq = new BooleanQuery();
				log.debug("adding: decomposing -->"+queryString+"<-- ");
				for(String qs : getAnalyzerFactory().getNormalizer(locale).normalizeAndDecompose(queryString)) {
					log.debug("adding: "+qs);
					//IF IS NUMBER->DVNS
					if(isNumeric(qs)){
						addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_DVNS, qs, 40);
						addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 60);
					} else {
						//IS TEXT->TRANSLATIONS	
						//SYNONYM
						addPrefixQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 10);
						addFuzzyQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 5);
						addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, qs, 30);
						Query synonymQuery = new FuzzyQuery(new Term(ServiceSynonymSource.FIELD_WORD,qs));
						List<String> synonymOut=serviceSynonymSource.search(locale, synonymQuery, 100, null);
						if(synonymOut!=null && synonymOut.size()>0){
							for(String s : synonymOut){
								addPrefixQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, s, 30);
								addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_TRANSLATIONS, s, 70);
							}
						}
					}
				}
				outQuery.add(bq,BooleanClause.Occur.MUST);
			}
		}
		
		return search(locale,outQuery, maxResults, cutoff);
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public String getBasePath() {
		return basePath;
	}

	public void setServiceSynonymSource(ITextSearchHome<String,String> serviceSynonymSource) {
		this.serviceSynonymSource = serviceSynonymSource;
	}

	public ITextSearchHome<String,String> getServiceSynonymSource() {
		return serviceSynonymSource;
	}
	
	private boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	} 
	
	public static void main(String[] args){
		try {
			
			new LocaleUtils().setAvailableLocaleList("de_DE;en_GB");
			
			LogSetup.setupLogging();
//			VehicleTaxonomyHome vth = new VehicleTaxonomyHome();
//			VehicleType vt= vth.getVehicleTypeById(71938l);
			//vt.setMaterializedPath("0000000000000001040.0000000000000001278.0000000000000001283.0000000000000001284.0000000000000000992");
			ServiceSynonymSource sss=new ServiceSynonymSource();
			sss.setBasePath("../apache-tomcat-7.0.20/var/lucene/synonyms");
			/**
			sss.rebuildIndex(Locale.GERMANY);
			 * 
			 */
			ServiceTextSearchHome sts=new ServiceTextSearchHome();
			sts.setBasePath("../apache-tomcat-7.0.20/var/lucene/services");
			sts.setAnalyzerFactory(new AnalyzerFactory());
			sts.setServiceSynonymSource(sss);
			sts.init();
			//sts.setBasePath("./lucene/services_short");
//			sts.rebuildIndex(new Locale("de","DE"));
			/**
			sts.updateDocument(Locale.GERMANY, service);
			 * 
			 */
			InputStreamReader isr=new InputStreamReader(System.in);
			BufferedReader reader=new BufferedReader(isr);
			while(true){
				String input=reader.readLine();
				if(input != null && input.length()>0){
					long start = System.currentTimeMillis();
					List<IService> res = sts.search(Locale.GERMANY, null,null,null,input,true,null, 0.7d);
					for(IService r : res) {
						System.out.println("=="+r.getId()+"  "+r.getName()+" ");
					}
					System.out.println("=========================================>");
					System.out.println(System.currentTimeMillis()-start);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Float getFactor() {
		return factor;
	}

	public void setFactor(Float factor) {
		this.factor = factor;
	}
	

	
	
	
	
	
	
}
