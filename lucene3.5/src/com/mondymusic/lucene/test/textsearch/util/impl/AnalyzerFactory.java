package com.uv.smp.persistence.textsearch.util.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

import com.uv.smp.persistence.textsearch.util.IAnalyzerFactory;
import com.uv.smp.persistence.textsearch.util.INormalizerUtil;
import com.uv.smp.persistence.textsearch.util.impl.de.GermanNormalizerUtil;

public class AnalyzerFactory implements IAnalyzerFactory {

	private static Map<String,INormalizerUtil> normalizers = new HashMap<String, INormalizerUtil>();
	
	static {
		normalizers.put("de", new GermanNormalizerUtil());
		normalizers.put("en", new DefaultNormalizerUtil());
	}
	
	@Override
	public Analyzer getAnalyzer(Version version, Locale locale) {
		Analyzer a = new NormalizingAnalyzer(version,getNormalizer(locale));
		return a;
	}
	
	@Override
	public INormalizerUtil getNormalizer(Locale locale) {
		INormalizerUtil inu = normalizers.get(locale.getLanguage().toLowerCase());
		return inu;
	}

		
}
