package com.mondymusic.lucene.util.impl;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class GermanCharUtil {
	
	private static String chars = 
			"\u00DF" +  //szlig
			"\u00C4" +  // auml
			"\u00E4" +  
			"\u00D6" +  // ouml
			"\u00F6" +
			"\u00DC" +  // uuml
			"\u00FC" +
			"";
	
	private static String[] replace = new String[] { "ss", "Ae", "ae", "Oe", "oe", "Ue", "ue" };

	public static String normalize(String in) {
		StringBuffer sb = new StringBuffer();
		CharacterIterator ci = new StringCharacterIterator(in.toLowerCase());
		char c;
		while((c=ci.current())!=CharacterIterator.DONE) {
			int x = chars.indexOf(c);
			if(x>-1) {
				sb.append(replace[x]);
			} else {
				sb.append(c);
			}
			ci.next();
		}
		return sb.toString();
	}
	
}
