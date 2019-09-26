package com.uv.smp.persistence.textsearch.util.impl;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

import com.uv.smp.persistence.textsearch.impl.ServiceTextSearchHome;
import com.uv.smp.persistence.textsearch.util.INormalizerUtil;

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
		if(fieldname.compareTo(ServiceTextSearchHome.FIELD_TRANSLATIONS)==0) {
			ts = new NormalizingSplittingTokenFilter(version, ts, normalizer);
		}
		return ts;
	}

}
