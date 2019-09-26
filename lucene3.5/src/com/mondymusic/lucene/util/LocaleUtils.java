package com.mondymusic.lucene.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleUtils {
	public static List<Locale> getAvailableLocales(){
		
		List<Locale>  out = new ArrayList<Locale>();
		out.add(new Locale("en_GB"));
		return out;
	}
	public static void main(String args[]){
		System.out.println(new Locale("en_GB").getDisplayLanguage());
		//alt shift X J
	}
	
}
