package com.mondymusic.lucene.util;

import java.util.Locale;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;


public interface IAnalyzerFactory {
	
	public Analyzer getAnalyzer(Version version, Locale locale);

	public INormalizerUtil getNormalizer(Locale locale);

}
