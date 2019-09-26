package com.uv.smp.persistence.textsearch.util;

import java.util.List;

public interface INormalizerUtil {
	
	public List<String> normalizeAndDecompose(String in);

	public List<String> normalizeAndSplit(String in);

	public String normalize(String in);


}
