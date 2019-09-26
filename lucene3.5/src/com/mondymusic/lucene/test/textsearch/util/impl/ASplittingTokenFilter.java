package com.uv.smp.persistence.textsearch.util.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

public abstract class ASplittingTokenFilter extends TokenFilter {

	private static Log log = LogFactory.getLog(ASplittingTokenFilter.class);
	
	protected Version version;
	private List<String> strings;
	private Iterator<String> iter;
	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	
	
	public ASplittingTokenFilter(Version version, TokenStream ts) {
		super(ts);
		this.version = version;
		termAtt = addAttribute(CharTermAttribute.class); 
		offsetAtt = addAttribute(OffsetAttribute.class); 
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		if(strings == null) {
			//initialize from input:
			strings = new ArrayList<String>();
			while(input.incrementToken()) {
				StringBuffer sb = new StringBuffer();
				sb.append(termAtt.buffer());
				String s = sb.toString().substring(0,(offsetAtt.endOffset()-offsetAtt.startOffset())).trim();
				strings.addAll(split(s));
			}
			iter = strings.iterator();
		}
		if(iter.hasNext()) {
			clearAttributes();
			String s = iter.next();
			termAtt.resizeBuffer(s.length());
			termAtt.setLength(s.length());
			offsetAtt.setOffset(0, s.length());
			s.getChars(0, s.length(), termAtt.buffer(), 0);
			return true;
		}
		return false;
	}

	protected abstract List<String> split(String in);

}
