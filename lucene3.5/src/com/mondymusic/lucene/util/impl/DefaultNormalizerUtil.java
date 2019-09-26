package com.mondymusic.lucene.util.impl;

import java.util.ArrayList;
import java.util.List;

import com.mondymusic.lucene.util.INormalizerUtil;


public class DefaultNormalizerUtil implements INormalizerUtil {

	@Override
	public List<String> normalizeAndDecompose(String in) {
		return normalizeAndSplit(in);
	}

	@Override
	public String normalize(String in) {
		return in;
	}

	@Override
	public List<String> normalizeAndSplit(String in) {
		List<String> out = new ArrayList<String>();
		for(String s : in.toLowerCase().split("[\\s\\p{Punct}/]")) {
			if(s.length()>0) {
				out.add(s);
			}
		}
		return out;
	}

}
