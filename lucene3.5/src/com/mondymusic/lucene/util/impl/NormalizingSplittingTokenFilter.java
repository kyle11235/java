package com.mondymusic.lucene.util.impl;

import java.util.List;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

import com.mondymusic.lucene.util.INormalizerUtil;



public class NormalizingSplittingTokenFilter extends ASplittingTokenFilter {

	//private static Log log = LogFactory.getLog(NormalizingSplittingTokenFilter.class);
	
	private INormalizerUtil normalizer;
	
	public NormalizingSplittingTokenFilter(Version version, TokenStream ts, INormalizerUtil normalizer) {
		super(version, ts);
		this.normalizer = normalizer;
	}

	@Override
	protected List<String> split(String in) {
		List<String> out = normalizer.normalizeAndDecompose(in);
		return out;
	}

}
