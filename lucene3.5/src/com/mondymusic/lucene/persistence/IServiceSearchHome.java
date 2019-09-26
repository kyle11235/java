package com.mondymusic.lucene.persistence;

import java.util.List;
import java.util.Locale;

import com.mondymusic.lucene.model.service.IService;
import com.mondymusic.lucene.pojo.service.Service;

public interface IServiceSearchHome  extends ISearchHome<Service, IService> {

	public List<IService> search(Locale locale, boolean advanced,  String queryString,  Integer maxResults) throws Exception; 
	
	
}
