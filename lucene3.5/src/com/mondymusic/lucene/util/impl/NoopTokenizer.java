package com.mondymusic.lucene.util.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public class NoopTokenizer extends Tokenizer {

	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private boolean ok = true;
	private List<String> strings ;
	private Iterator<String> iter ;
	
	public NoopTokenizer(Reader input) {
		super(input);
		termAtt = addAttribute(CharTermAttribute.class); 
		offsetAtt = addAttribute(OffsetAttribute.class); 
	}

	@Override
	public boolean incrementToken() throws IOException {
		if(strings == null) {
			StringBuffer sb = new StringBuffer();
			int a = 0; 
			char[] cbuf = new char[1000];
			while((a=input.read(cbuf))>-1) {
				sb.append(cbuf, 0, a);
			}
			String s = sb.toString().toLowerCase();
			strings = new ArrayList<String>();
			for(String st : s.split("\\s")) {
				if(st.trim().length()>0) {
					strings.add(st.trim());
				}
			}
			iter = strings.iterator();
		}
		if(iter.hasNext()) {
			clearAttributes();
			String s = iter.next();
			int length = s.length();
			termAtt.resizeBuffer(length);
			termAtt.append(s);
			offsetAtt.setOffset(0, length);
			return true;
		} else {
			return false;
		}
	}

}
