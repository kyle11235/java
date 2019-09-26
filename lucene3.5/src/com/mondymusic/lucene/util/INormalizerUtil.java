package com.mondymusic.lucene.util;

import java.util.List;

public interface INormalizerUtil {
	public List<String> normalizeAndDecompose(String in);

	public List<String> normalizeAndSplit(String in);

	public String normalize(String in);

}
