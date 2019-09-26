package com.mondymusic.lucene.persistence;

import java.util.List;
import java.util.Locale;

import org.apache.lucene.search.Query;

public interface ISearchHome<T, U> {
	
	public void rebuildIndex(Locale locale)throws Exception;

	public void updateDocument(Locale locale, T t)throws Exception;

	public List<U> search(Locale locale, Query q, Integer maxResults)throws Exception;

}
