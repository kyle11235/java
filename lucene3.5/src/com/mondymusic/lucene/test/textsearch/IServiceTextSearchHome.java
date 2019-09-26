package com.uv.smp.persistence.textsearch;

import java.util.List;
import java.util.Locale;

import com.uv.smp.exception.TextSearchException;
import com.uv.smp.model.service.IService;
import com.uv.smp.persistence.pojos.taxonomy.Service;
import com.uv.smp.persistence.pojos.taxonomy.ServiceCategory;
import com.uv.smp.persistence.pojos.taxonomy.VehicleType;

public interface IServiceTextSearchHome extends ITextSearchHome<Service, IService> {

	public List<IService> search(Locale locale, VehicleType vt, ServiceCategory scMain, ServiceCategory scSec, String searchTerm, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException;

	public List<IService> advancedSearch(Locale locale, boolean recursive, VehicleType vt, ServiceCategory scMain, ServiceCategory scSec, String queryString, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException;
	
}
