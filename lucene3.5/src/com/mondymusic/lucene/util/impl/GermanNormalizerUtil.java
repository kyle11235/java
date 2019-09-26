package com.mondymusic.lucene.util.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GermanNormalizerUtil extends DefaultNormalizerUtil {

	private static WordSplitter wordSplitter;
	//private static Log log = LogFactory.getLog(GermanNormalizerUtil.class);
	
	static {
		try {
			wordSplitter = new WordSplitter(GermanNormalizerUtil.class.getResourceAsStream("words.txt"), 3);
		} catch (IOException e) {
		}
	}
	
	@Override
	public String normalize(String in) {
		return GermanCharUtil.normalize(in);
	}
	
	@Override
	public List<String> normalizeAndDecompose(String in) {
		List<String> out = new ArrayList<String>();
		in = GermanCharUtil.normalize(in);
		for(String s : super.normalizeAndDecompose(in)) {
			out.addAll(wordSplitter.findWords(s));
		}
		out.add(in);
		return out;
	}
	
	
}
