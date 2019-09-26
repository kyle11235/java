package com.uv.smp.persistence.textsearch;

import java.util.Locale;

import com.uv.smp.exception.TextSearchException;
import com.uv.smp.persistence.pojos.taxonomy.Service;

public interface IServiceChangeListener {
	
	public void updateDocument(Locale locale, Service service) throws TextSearchException;
	
}
