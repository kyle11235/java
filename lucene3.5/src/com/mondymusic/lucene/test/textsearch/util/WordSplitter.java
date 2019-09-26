package com.uv.smp.persistence.textsearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.uv.smp.persistence.textsearch.util.impl.de.GermanCharUtil;

public class WordSplitter {

	private Set<String> words = null;
	private int minimumWordLength = 4;

	public WordSplitter(InputStream is, int minWordLength) throws IOException {
		this.minimumWordLength = minWordLength;
		this.words = loadFile(is, "utf-8");
	}

	private static Set<String> loadFile(InputStream is, String charset) throws IOException {
		InputStreamReader isr = null;
		BufferedReader br = null;
		Set<String> words = new TreeSet<String>();
		try {
			isr = new InputStreamReader(is, charset);
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				words.add(GermanCharUtil.normalize(line.trim().toLowerCase()));
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (Exception e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
		return words;
	}

	protected int getMinimumWordLength() {
		return minimumWordLength;
	}

	private boolean isWord(String s) {
		if (s == null) {
			return false;
		}
		if (s.trim().length() < getMinimumWordLength()) {
			return false;
		}
		if (words.contains(s.toLowerCase().trim())) {
			return true;
		}
		return false;
	}

	public List<String> findWords(String str) {
		final List<String> result = new ArrayList<String>();
		for(int i=0;i<str.length();i++) {
			for(int k=1;k<=str.length();k++) {
				if(i<k) {
					String left = str.substring(i,k);
					if(i<=k-minimumWordLength && isWord(left)) {
						if(!result.contains(left)) {
							result.add(left);
						}
					} else if (isNumber(left)) {
						if(!result.contains(left)) {
							result.add(left);
						}
					}
				}
			}
		}
		return result;
	}

	private boolean isNumber(String left) {
		try {
			NumberFormat.getNumberInstance().parse(left);
			return true;
		} catch (Exception e) {
		}
		return false;
	}


}
