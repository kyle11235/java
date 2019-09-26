package com.mondymusic.lucene.util.impl;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

import com.mondymusic.lucene.persistence.impl.ServiceSearchHome;
import com.mondymusic.lucene.util.INormalizerUtil;


public class NormalizingAnalyzer extends Analyzer {

	private INormalizerUtil normalizer;
	private Version version;
	
	public NormalizingAnalyzer(Version version, INormalizerUtil normalizer) {
		this.normalizer = normalizer;
		this.version = version;
	}
	
	@Override
	public TokenStream tokenStream(String fieldname, Reader reader) {
		TokenStream ts = new NoopTokenizer(reader);
		//if ServiceSearchHome.FIELD_TRANSLATIONS
		if(fieldname.compareTo(ServiceSearchHome.FIELD_TRANSLATIONS)==0) {
			ts = new NormalizingSplittingTokenFilter(version, ts, normalizer);
		}
		return ts;
	}

}
