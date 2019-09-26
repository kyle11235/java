package com.uv.smp.persistence.textsearch.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.regex.RegexQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.uv.smp.exception.DatabaseException;
import com.uv.smp.exception.TextSearchException;
import com.uv.smp.persistence.impl.ABaseHome;
import com.uv.smp.persistence.textsearch.ITextSearchHome;
import com.uv.smp.persistence.textsearch.util.IAnalyzerFactory;
import com.uv.smp.util.LocaleUtils;

public abstract class ATextSearchHome<T,U> extends ABaseHome implements ITextSearchHome<T,U>{

	private static Log log = LogFactory.getLog(ATextSearchHome.class);
	
	public static final String INDEX_PATH_TEMP_PREFIX="_";
	
	protected Map<Locale,IndexWrapper> indexes = new HashMap<Locale, IndexWrapper>();
	
	public static final Version version = Version.LUCENE_35;

	@Autowired
	private IAnalyzerFactory analyzerFactory;
	
	
	public void init() throws CorruptIndexException, IOException{
		for(Locale locale : LocaleUtils.getAvailableLocales()) {
			cleanup(locale);
		}
	}
	
	public void cleanup(Locale locale) {
		File baseDir=new File(getIndexPath(locale));
		File latest = getLastestIndexFile(locale);
		if(latest!=null){
			for(File f : baseDir.listFiles()){
				if(f.isDirectory() && f.getName().compareTo(latest.getName())!=0){
					deleteDir(f);
				}
			}
		}
	}
	
	protected String getIndexPath(Locale locale){
		return getBasePath()+File.separator+locale.toString()+File.separator;
	}
	
	protected Analyzer getAnalyzer(Locale locale){
		return analyzerFactory.getAnalyzer(version,locale);
	}
	
	protected File getLastestIndexFile(Locale locale){
		File baseDir=new File(getIndexPath(locale));
		long last = 0l;
		if(baseDir.exists()) {
			File out = null;
			for(File f : baseDir.listFiles()) {
				if(f.isDirectory() && !f.getName().startsWith(INDEX_PATH_TEMP_PREFIX)) {
					long curr = 0;
					try {
						curr = Long.parseLong(f.getName());
					} catch (Exception e) {
					}
					if(curr > last) {
						last = curr;
						out = f;
					}
				}
			}
			log.info("directory :"+baseDir.getAbsolutePath()+" DOES  exist, latest index is: "+out);
			return out;
		} else {
			log.error("directory :"+baseDir.getAbsolutePath()+" does not exist!");
			return null;
		}
	}
	
	public void refreshIndex(Locale locale) {
		indexes.remove(locale);
	}

	public IndexWrapper getIndex(Locale locale) throws CorruptIndexException, IOException {
		//every time ,get the lastest one 
		File f=getLastestIndexFile(locale);
		return getIndex(locale,f);
	}
	
	public IndexWrapper getIndex(Locale locale, File f) throws CorruptIndexException, IOException {
		
		if(f==null) {
			return null;
		}
		
		IndexWrapper out = indexes.get(locale);
		
		if(out == null) {
			synchronized(this) {
				//out should be null, because every time after search or update,it removes the index wrapper
				out = indexes.get(locale);
				if(out == null) {
					//every time ,put the lastest one into the map
					out = createIndex(locale, f);
					indexes.put(locale, out);
				}
			}
		}
		return out;
	}
	
	public IndexWrapper createIndex(Locale locale, File f) throws CorruptIndexException, IOException {
		IndexWrapper out = new IndexWrapper(locale, f);
		return out;
	}
	
	
	protected void deleteDir(File file){
		try {
			if(file.isDirectory()){
				for(String child : file.list()){
					deleteDir(new File(file,child));
				}
			}
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void refactorIndex(Locale locale,String newFolderName) throws TextSearchException, CorruptIndexException, IOException{
		File tempFolder=new File(getIndexPath(locale)+File.separator+INDEX_PATH_TEMP_PREFIX+newFolderName);
		File newFolder=new File(getIndexPath(locale)+File.separator+newFolderName);
		tempFolder.renameTo(newFolder);
		refreshIndex(locale);
		cleanup(locale);
	}
	
	public abstract String getBasePath();
	
	@Override
	public abstract void rebuildIndex(Locale locale) throws DatabaseException,TextSearchException;

	@Override
	public abstract void updateDocument(Locale locale, T t) throws TextSearchException;
	
	@Override
	public abstract List<U> search(Locale locale, Query query, Integer maxResults, Double cutoff) throws TextSearchException;

	public List<Document> searchInternal(Locale locale, Query query, Integer maxResults, Double cutoff) throws TextSearchException, IOException {

		log.debug("searchInternal - query is: "+query);
		
		IndexWrapper indexWrapper = getIndex(locale);

		log.debug("searchInternal - indexWrapper is: "+indexWrapper);
		List<Document> out = new ArrayList<Document>();

		if(indexWrapper==null){			
			return out;
		}
		log.debug("searchInternal - searcher is: "+indexWrapper.getSearcher());
		//searcherWrapper.getSearcher().setSimilarity(new ServiceSimilarity());
		TopDocs results = indexWrapper.getSearcher().search(query, (maxResults!=null?maxResults.intValue():1000));
		
		// sort: indexWrapper.getSearcher().search(query,  (maxResults!=null?maxResults.intValue():1000),sort);
		
		// filter: TopDocs results = indexWrapper.getSearcher().search(query, filter , (maxResults!=null?maxResults.intValue():1000));
		
		double maxScore = Double.MIN_VALUE;
		
		for(ScoreDoc sr : results.scoreDocs){
			int docID = sr.doc;
			Document doc = indexWrapper.getSearcher().doc(docID);
			if(maxScore < 0) {
				maxScore = sr.score*(cutoff==null?0d:cutoff.doubleValue());
			} else {
				if(maxResults != null && out.size()>=maxResults) {
					break;
				} else if (sr.score < maxScore) {
					break;
				}
			}
			//log.debug(searcherWrapper.getSearcher().explain(query,docID));		
			
			log.info(",score="+sr.score+" /// "+doc.get("weight")+" /// "+doc.get("name")+" /// "+doc.get("id"));


			out.add(doc);
		}		
		return out;
	}
	

	protected class IndexWrapper {
		
		private Locale locale;
		private IndexSearcher searcher;
		private File file;
		
		public IndexWrapper(Locale locale, File file) throws CorruptIndexException, IOException {
			this.file = file;
			this.locale = locale;
		}

		public IndexSearcher getSearcher() throws IOException {
			if(searcher==null) {
				log.debug("creating index from directory: "+file.getAbsolutePath());
				Directory d = FSDirectory.open(file);
				this.searcher = new IndexSearcher(IndexReader.open(d, true));
			}
			return searcher;
		}

		public void setSearcher(IndexSearcher searcher) {
			this.searcher = searcher;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public IndexWriter createWriter() throws IOException {
			Directory d = FSDirectory.open(file);
			IndexWriterConfig iwc = new IndexWriterConfig(version, getAnalyzer(locale));
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(d,iwc);
			return writer;
		}

		
	}
	
	public IAnalyzerFactory getAnalyzerFactory() {
		return analyzerFactory;
	}

	public void setAnalyzerFactory(IAnalyzerFactory analyzerFactory) {
		this.analyzerFactory = analyzerFactory;
	}
	
	protected void addTermQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		log.debug("adding term query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new TermQuery(t);
		q.setBoost(boost);
		parent.add(q,occur);
	}
	
	protected void addPrefixQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		log.debug("adding prefix query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new PrefixQuery(t);
		q.setBoost(boost);
		parent.add(q,occur);
	}

	protected void addFuzzyQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		log.debug("adding fuzzy query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new FuzzyQuery(t);
		q.setBoost(boost);
		parent.add(q,occur);
	}
	
	protected void addRegexQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		log.debug("adding regex query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		RegexQuery q = new RegexQuery(t);
		q.setBoost(boost);
		parent.add(q,occur);
	}
}
