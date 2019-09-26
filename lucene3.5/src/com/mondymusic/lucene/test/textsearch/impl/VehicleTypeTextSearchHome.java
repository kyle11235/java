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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

import com.uv.smp.exception.DatabaseException;
import com.uv.smp.exception.TextSearchException;
import com.uv.smp.model.vehicle.IVehicleType;
import com.uv.smp.model.vehicle.impl.VehicleTypeImpl;
import com.uv.smp.persistence.pojos.taxonomy.VehicleCategory;
import com.uv.smp.persistence.pojos.taxonomy.VehicleType;
import com.uv.smp.persistence.taxonomy.impl.VehicleTaxonomyHome;
import com.uv.smp.persistence.textsearch.IVehicleTypeTextSearchHome;
import com.uv.smp.persistence.textsearch.util.impl.AnalyzerFactory;
import com.uv.smp.util.LocaleUtils;
import com.uv.smp.util.logging.LogSetup;

public class VehicleTypeTextSearchHome extends ATextSearchHome<VehicleType, IVehicleType> implements IVehicleTypeTextSearchHome {

	private static Log log = LogFactory.getLog(ServiceTextSearchHome.class);
	
	private String basePath;
	
	private Object LOCK = new Object();

	public static final String FIELD_ID="id";
	public static final String FIELD_NAME="name";
	public static final String FIELD_NAME_DISPLAY="displayName";
	public static final String FIELD_TRANSLATIONS="translations";
	public static final String FIELD_VT_PATH="vtPath";
	public static final String FIELD_VT_PROPERTIES="vtProperties";
	public static final String FIELD_VISIBILITY="visibility";
	
	private void addDocument(IndexWriter indexWriter, Locale locale, Long vehicleTypeId) throws DatabaseException, CorruptIndexException, IOException {
		log.debug("service text search - indexing services ... ");
		HashMap<String, Object> params = new HashMap<String, Object>();
		String query=
				" SELECT "+  
				"    vt.vehicletypeid,  vt.name,  vt.name, vt.materialized_path, vt.materialized_path "+  
				" FROM   "+
				" 	smp_vehicletype vt "+ 
				" WHERE  "+
				"     vt.vehicletypeid > :lastId AND " +
				" 	  vt.deleted IS NULL ";
				if(vehicleTypeId!=null){
					params.put("vehicleTypeId", vehicleTypeId);
					query=query+" AND vt.vehicletypeid = :vehicleTypeId  ";
				}
				query = query + 
				" GROUP BY  "+
				"     vt.vehicletypeid "+
				" ORDER BY "+
				"     vt.vehicletypeid ";


		int step = 50000;

		long lastId = -1l;
		
		while(true) {
			params.put("lastId",lastId);
			long start = System.currentTimeMillis();
			System.err.println("vehicle text search - selecting vehicletypes ... ");
			List<Object> os = nativeMultiQuery(query, params, Object.class, 0, step, false, null);
			System.err.println("vehicle text search - selecting vehicles ... ("+(System.currentTimeMillis()-start)+") ms");
			System.err.println("vehicle text search - indexing "+os.size()+" vehicletypes ");
			
			if(os.size()==0) {
				break;
			}

			log.debug("vehicle text search - indexing "+os.size()+" services ");
			for (Object o : os) {
				Object[] outArray = (Object[]) o;
				
				Long id=((BigInteger)outArray[0]).longValue();
				lastId = id;
				String vehicleTypeName=(String)outArray[1];
				String displayName=(String)outArray[2];
				String vtPath=(String)outArray[3];
				
				Document doc = new Document();
				doc.add(new NumericField(FIELD_ID, Field.Store.YES, true).setLongValue(id));
				doc.add(new Field(FIELD_VT_PATH,vtPath,Field.Store.YES, Field.Index.ANALYZED));

				if(vehicleTypeName!=null) {
					doc.add(new Field(FIELD_NAME,vehicleTypeName,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("vehicleTypeName is NULL");
				}
				if(displayName!=null) {
					doc.add(new Field(FIELD_NAME_DISPLAY,displayName,Field.Store.YES, Field.Index.ANALYZED));
				} else {
					log.warn("displayName is NULL");
				}
				log.debug("ADDING VEHICLE: id  "+lastId);
				log.debug("ADDING VEHICLE: n   "+vehicleTypeName);
				log.debug("ADDING VEHICLE: dn  "+displayName);
				indexWriter.addDocument(doc);
			}
		}
	}

	@Override
	public void rebuildIndex(Locale locale) throws DatabaseException, TextSearchException {
		log.debug("vehicle text search - full rebuild ... ");
		try {
			synchronized (LOCK) {
				String newFolderName=System.currentTimeMillis()+"";
				String newIndexPath=getIndexPath(locale)+File.separator+INDEX_PATH_TEMP_PREFIX+newFolderName;
				File f = new File(newIndexPath);
				f.mkdirs();
				IndexWriter indexWriter = createIndex(locale, f).createWriter();
				log.debug("vehicle text search - FULL REBUILD");
				addDocument(indexWriter, locale, null);
				log.debug("vehicle text search - full rebuild ... DONE");
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
		log.debug("finish rebuild vehicle type index===============================");
	}

	@Override
	public void updateDocument(Locale locale, VehicleType vehicleType) throws TextSearchException {
		if(vehicleType==null || vehicleType.getId()==null){
			throw new TextSearchException("missing parameter");
		}
		try {
			synchronized (LOCK) {
				
				IndexWriter indexWriter = getIndex(locale).createWriter();

				if(indexWriter!=null) {
					Long vehicleTypeId = vehicleType.getId();
					Query query=NumericRangeQuery.newLongRange(FIELD_ID, 1, vehicleTypeId-1, vehicleTypeId+1, false, false);
					log.debug("delete document service:"+vehicleTypeId);
					indexWriter.deleteDocuments(query);
					if(vehicleType.getDeleted()==null){
						addDocument(indexWriter, locale, vehicleTypeId);
						log.debug("delete document service and add new vehcileType:"+vehicleTypeId);
					}
					indexWriter.commit();
					indexWriter.close();
					refreshIndex(locale);
				}
			}
		} catch (Exception e) {
			log.error("update document vehicleType:"+vehicleType.getId()+" failed !",e);
			throw new TextSearchException("update document vehicleType:"+vehicleType.getId()+" failed !");
		}
	}

	@Override
	public List<IVehicleType> search(Locale locale, Query query, Integer maxResults, Double cutoff) throws TextSearchException {
		List<IVehicleType> out = new ArrayList<IVehicleType>();
		try {
			for(Document doc : searchInternal(locale, query , maxResults, cutoff)) {
				IVehicleType vehicleTypeImpl=new VehicleTypeImpl(Long.parseLong(doc.get(FIELD_ID)), doc.get(FIELD_NAME_DISPLAY), doc.get(FIELD_NAME_DISPLAY));
				out.add(vehicleTypeImpl);
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
	public List<IVehicleType> search(Locale locale, VehicleCategory vc, String queryString, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException {
		BooleanQuery outQuery=new BooleanQuery();

		if(vc != null) {
			String s = vc.getMaterializedPath();
			addPrefixQuery(outQuery, BooleanClause.Occur.MUST, FIELD_VT_PATH, s, 1f);
		}
		
		if(queryString == null){
			queryString = "";
		}
		if (queryString.length()>0) { 
			log.debug("non-empty query (regular)");
			BooleanQuery bq = new BooleanQuery();
			log.debug("adding: decomposing -->"+queryString+"<-- ");
			for(String qs : getAnalyzerFactory().getNormalizer(locale).normalizeAndDecompose(queryString)) {
				log.debug("adding: "+qs);
				addPrefixQuery(bq, BooleanClause.Occur.SHOULD, FIELD_NAME_DISPLAY, qs, 10);
				addFuzzyQuery(bq, BooleanClause.Occur.SHOULD, FIELD_NAME_DISPLAY, qs, 5);
				addTermQuery(bq, BooleanClause.Occur.SHOULD, FIELD_NAME_DISPLAY, qs, 30);
			}
			outQuery.add(bq,BooleanClause.Occur.MUST);
		}
		
		return search(locale,outQuery, maxResults, cutoff);
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	public static void main(String[] args){
		try {
			new LocaleUtils().setAvailableLocaleList("de_DE;en_GB");
			LogSetup.setupLogging();
			VehicleTypeTextSearchHome vts=new VehicleTypeTextSearchHome();
			VehicleTaxonomyHome vth=new VehicleTaxonomyHome();
			VehicleCategory vc=vth.getVehicleCategoryById(1l);
			vts.setBasePath("../apache-tomcat-7.0.20/var/lucene/vehicleTypes");
			vts.setAnalyzerFactory(new AnalyzerFactory());
			vts.init();
//			vts.rebuildIndex(new Locale("de","DE"));
			InputStreamReader isr=new InputStreamReader(System.in);
			BufferedReader reader=new BufferedReader(isr);
			while(true){
				String input=reader.readLine();
				if(input != null && input.length()>0){
					long start = System.currentTimeMillis();
					List<IVehicleType> res = vts.search(Locale.GERMANY, vc, input, true, 20, 0.5d);
					for(IVehicleType r : res) {
						System.out.println("=="+r.getName());
					}
					System.out.println("=========================================>");
					System.out.println(System.currentTimeMillis()-start);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
