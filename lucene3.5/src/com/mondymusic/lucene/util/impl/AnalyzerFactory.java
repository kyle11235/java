package com.mondymusic.lucene.util.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.mondymusic.lucene.util.IAnalyzerFactory;
import com.mondymusic.lucene.util.INormalizerUtil;
public class AnalyzerFactory implements IAnalyzerFactory {
	 
	//static load words only one time
	private static Map<String, INormalizerUtil> normalizers = new HashMap<String, INormalizerUtil>();

	static {
		normalizers.put("de", new GermanNormalizerUtil());
		normalizers.put("en", new DefaultNormalizerUtil());
	}

	@Override
	public Analyzer getAnalyzer(Version version, Locale locale) {
		
		
		return new NormalizingAnalyzer(version,getNormalizer(locale));
		//return new StandardAnalyzer(version);
	}

	@Override
	public INormalizerUtil getNormalizer(Locale locale) {
		INormalizerUtil inu = normalizers.get(locale.getLanguage().toLowerCase());
		return inu;
	}
	

}
