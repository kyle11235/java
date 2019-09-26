package com.mondymusic.lucene.persistence.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.mondymusic.lucene.util.IAnalyzerFactory;
import com.mondymusic.lucene.util.LocaleUtils;
import com.mondymusic.lucene.util.impl.AnalyzerFactory;

public abstract class ASearchHome<T, U> {

	// private static Log log = LogFactory.getLog(ASearchHome.class);

	public static final String INDEX_PATH_TEMP_PREFIX="_";

	private IAnalyzerFactory analyzerFactory = new AnalyzerFactory();

	protected Map<Locale, IndexWrapper> indexes = new HashMap<Locale, IndexWrapper>();

	public static final Version version = Version.LUCENE_35;

	public void init() throws CorruptIndexException, IOException {
		for (Locale locale : LocaleUtils.getAvailableLocales()) {
			cleanup(locale);
		}
	}

	public void cleanup(Locale locale) {
		File baseDir = new File(getIndexPath(locale));
		File latest = getLastestIndexFile(locale);
		if (latest != null) {
			for (File f : baseDir.listFiles()) {
				if (f.isDirectory() && f.getName().compareTo(latest.getName()) != 0) {
					deleteDir(f);
				}
			}
		}
	}

	public IAnalyzerFactory getAnalyzerFactory() {
		return analyzerFactory;
	}
	
	protected String getIndexPath(Locale locale) {
		return getBasePath() + File.separator + locale.toString() + File.separator;
	}

	protected File getLastestIndexFile(Locale locale) {
		File baseDir = new File(getIndexPath(locale));
		long last = 0l;
		if (baseDir.exists()) {
			File out = null;
			for (File f : baseDir.listFiles()) {
				//if it's building the new index,it will put the new index in the temp folder
				if (f.isDirectory()&& !f.getName().startsWith(INDEX_PATH_TEMP_PREFIX)) {
					long curr = 0;
					try {
						curr = Long.parseLong(f.getName());
					} catch (Exception e) {
						//log.debug(e.printStackTrace());
					}
					if (curr > last) {
						last = curr;
						out = f;
					}
				}
			}
			// log.info("directory :"+baseDir.getAbsolutePath()+" DOES  exist, latest index is: "+out);
			return out;
		} else {
			// log.error("directory :"+baseDir.getAbsolutePath()+" does not exist!");
			return null;
		}
	}

	public void refreshIndex(Locale locale) {
		// remove from map,when try to search,it will put a new one into the
		// map,the new one is the lastest index file
		indexes.remove(locale);
	}

	public IndexWrapper getIndex(Locale locale) throws CorruptIndexException, IOException {
		//use the latest file to get the index in the map, the map actually is a index pool
		File f = getLastestIndexFile(locale);
		return getIndex(locale, f);
	}

	public IndexWrapper getIndex(Locale locale, File f) throws CorruptIndexException, IOException {

		if (f == null) {
			return null;
		}
		//get index from the pool
		IndexWrapper out = indexes.get(locale);

		//if it had updated the document or rebuilt the index,this would be null
		if (out == null) {
			synchronized (this) {
				out = indexes.get(locale);
				if (out == null) {
					// every time ,put the lastest one into the map
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

	protected void deleteDir(File file) {
		try {
			if (file.isDirectory()) {
				for (String child : file.list()) {
					deleteDir(new File(file, child));
				}
			}
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void refactorIndex(Locale locale, String newFolderName) throws CorruptIndexException, IOException {
		//the index has been built up,put the temp folder into use
		File tempFolder=new File(getIndexPath(locale)+File.separator+INDEX_PATH_TEMP_PREFIX+newFolderName);
		File newFolder=new File(getIndexPath(locale)+File.separator+newFolderName);
		tempFolder.renameTo(newFolder);
		//just remove the index from the map,next search will create a new one
		refreshIndex(locale);
		//delete old index files
		cleanup(locale);
	}

	/* abstract methods */

	public abstract String getBasePath();

	public abstract void rebuildIndex(Locale locale) throws Exception;

	public abstract void updateDocument(Locale locale, T t) throws Exception;

	public abstract List<U> search(Locale locale, Query query, Integer maxResults) throws Exception;

	public List<Document> searchInternal(Locale locale, Query query, Integer maxResults) throws IOException {

		// log.debug("searchInternal - query is: "+query);

		IndexWrapper indexWrapper = getIndex(locale);

		// log.debug("searchInternal - indexWrapper is: "+indexWrapper);
		List<Document> out = new ArrayList<Document>();

		if (indexWrapper == null) {
			return out;
		}
		// log.debug("searchInternal - searcher is: "+indexWrapper.getSearcher());
		// searcherWrapper.getSearcher().setSimilarity(new ServiceSimilarity());
		TopDocs results = indexWrapper.getSearcher().search(query, (maxResults != null ? maxResults.intValue() : 1000));

		// cobus sort: indexWrapper.getSearcher().search(query,
		// (maxResults!=null?maxResults.intValue():1000),sort);

		// cobus filter: TopDocs results =
		// indexWrapper.getSearcher().search(query, filter ,
		// (maxResults!=null?maxResults.intValue():1000));

		for (ScoreDoc sr : results.scoreDocs) {
			int docID = sr.doc;
			Document doc = indexWrapper.getSearcher().doc(docID);

			if (maxResults != null && out.size() >= maxResults) {
				break;
			}

			// log.debug(searcherWrapper.getSearcher().explain(query,docID));

			// log.info("cobus,score="+sr.score+" /// "+doc.get("weight")+" /// "+doc.get("name")+" /// "+doc.get("id"));

			out.add(doc);
		}
		return out;
	}

	/* searcher and writer */
	protected class IndexWrapper {

		private Locale locale;
		private IndexSearcher searcher;
		private File file;

		public IndexWrapper(Locale locale, File file) throws CorruptIndexException, IOException {
			this.file = file;
			this.locale = locale;
		}

		// get seatcher
		public IndexSearcher getSearcher() throws IOException {
			if (searcher == null) {
				// log.debug("creating index from directory: " +
				// file.getAbsolutePath());
				Directory d = FSDirectory.open(file);
				
				//the searcher will be in the index pool until the index wrapper is removed from the map
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

		// get a new writer,this is not in the index wrqpper pool,after updating the document, it will be closed
		public IndexWriter createWriter() throws IOException {
			Directory d = FSDirectory.open(file);
			IndexWriterConfig iwc = new IndexWriterConfig(version, getAnalyzer(locale));

			// CREATE_OR_APPEND
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(d, iwc);
			return writer;
		}

	}

	/* get analyzer */
	protected Analyzer getAnalyzer(Locale locale) {
		return analyzerFactory.getAnalyzer(version, locale);
	}

	/* add query */

	protected void addTermQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		// log.debug("adding term query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new TermQuery(t);
		q.setBoost(boost);
		parent.add(q, occur);
	}

	protected void addPrefixQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		// log.debug("adding prefix query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new PrefixQuery(t);
		q.setBoost(boost);
		parent.add(q, occur);
	}

	protected void addFuzzyQuery(BooleanQuery parent, BooleanClause.Occur occur, String fieldname, String term, float boost) {
		// log.debug("adding fuzzy query: '" +fieldname+"' / '"+term+"'");
		Term t = new Term(fieldname);
		t = t.createTerm(term);
		Query q = new FuzzyQuery(t);
		q.setBoost(boost);
		parent.add(q, occur);
	}

	/*
	 * where is RegexQuery protected void addRegexQuery(BooleanQuery parent,
	 * BooleanClause.Occur occur, String fieldname, String term, float boost) {
	 * //log.debug("adding regex query: '" +fieldname+"' / '"+term+"'"); Term t
	 * = new Term(fieldname); t = t.createTerm(term); RegexQuery q = new
	 * RegexQuery(t); q.setBoost(boost); parent.add(q,occur); }
	 */
	
	
	
	
}
