package com.uv.smp.persistence.textsearch.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.Query;

import com.uv.smp.exception.DatabaseException;
import com.uv.smp.exception.TextSearchException;
import com.uv.smp.persistence.pojos.Synonym;
import com.uv.smp.persistence.textsearch.ITextSearchHome;

public class ServiceSynonymSource extends ATextSearchHome<String,String> implements ITextSearchHome<String,String>{

	private static Log log = LogFactory.getLog(ServiceSynonymSource.class);
	
	private String basePath;
	public static final String FIELD_ID="id";
	public static final String FIELD_WORD="word";
	public static final String FIELD_SYNONYM="synonym";
	public static final int QUERY_COUNT=100;
	
	private Object LOCK=new Object();

	@Override
	public void rebuildIndex(Locale locale) throws DatabaseException,TextSearchException {
		try {
			synchronized (LOCK) {
				String newFolderName=System.currentTimeMillis()+"";
				String newIndexPath=getIndexPath(locale)+File.separator+INDEX_PATH_TEMP_PREFIX+newFolderName;
				File f = new File(newIndexPath);
				f.mkdirs();
				IndexWriter indexWriter = createIndex(locale, f).createWriter();
				String jql="FROM Synonym";
				List<Synonym> out=multiResult(jql, null, Synonym.class);
				for(Synonym s : out){
					Document doc1 = new Document();
					doc1.add(new Field(FIELD_WORD,s.getWord(),Field.Store.YES, Field.Index.ANALYZED));
					doc1.add(new Field(FIELD_SYNONYM,s.getSynonym(),Field.Store.YES, Field.Index.ANALYZED));
					indexWriter.addDocument(doc1);
					Document doc2 = new Document();
					doc2.add(new Field(FIELD_WORD,s.getSynonym(),Field.Store.YES, Field.Index.ANALYZED));
					doc2.add(new Field(FIELD_SYNONYM,s.getWord(),Field.Store.YES, Field.Index.ANALYZED));
					indexWriter.addDocument(doc2);
				}
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
	}

	@Override
	public void updateDocument(Locale locale, String t) {
		
	}

	@Override
	public List<String> search(Locale locale, Query query, Integer maxResults, Double cutoff) throws TextSearchException {
		try {
			List<String> out=new ArrayList<String>();
			for(Document doc : searchInternal(locale, query, maxResults, cutoff)) {
				out.add(doc.get(FIELD_SYNONYM));
			}
			return out;
		} catch (CorruptIndexException e) {
			log.error("corrupt index",e);
			throw new TextSearchException("corrupt index file");
		} catch (IOException e) {
			log.error("search from index error",e);
			throw new TextSearchException("search from index error");
		}
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public String getBasePath() {
		return basePath;
	}
	
}
