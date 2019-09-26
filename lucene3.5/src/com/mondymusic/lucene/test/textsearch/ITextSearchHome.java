package com.uv.smp.persistence.textsearch;

import java.util.List;
import java.util.Locale;

import org.apache.lucene.search.Query;

public interface ITextSearchHome<T,U> {
		
	public void rebuildIndex(Locale locale) throws DatabaseException,TextSearchException;
	
	public void updateDocument(Locale locale, T t) throws TextSearchException;
	
	public List<U> search(Locale locale, Query q, Integer maxResults, Double cutoff) throws TextSearchException;

	
}
