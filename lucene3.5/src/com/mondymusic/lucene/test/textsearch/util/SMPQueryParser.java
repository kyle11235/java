package com.uv.smp.persistence.textsearch.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.CharStream;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParserTokenManager;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class SMPQueryParser extends QueryParser {

	private String[] numericFields;

	public SMPQueryParser(CharStream stream) {
		super(stream);
	}

	public SMPQueryParser(QueryParserTokenManager tm) {
		super(tm);
	}

	public SMPQueryParser(Version matchVersion, String f, Analyzer a, String[] numericFields) {
		super(matchVersion, f, a);
		this.numericFields = numericFields;
	}

	@Override
	protected Query newRangeQuery(String field, String part1, String part2, boolean inclusive) {
		if (isNumericField(field)) {
			return NumericRangeQuery.newLongRange(field, Long.parseLong(part1), Long.parseLong(part2), inclusive, inclusive);
		} else {
			return super.newRangeQuery(field, part1, part2, inclusive);
		}
	}

	@Override
	protected Query newTermQuery(Term term) {
		if (isNumericField(term.field())) {
			return NumericRangeQuery.newLongRange(term.field(), Long.parseLong(term.text()) - 1, Long.parseLong(term.text()) + 1, false, false);
		} else {
			return super.newTermQuery(term);
		}
	}

	private boolean isNumericField(String field){
		boolean isNumeric = false;
		if(field!=null && field.trim().length()>0){			
			for (String numeriField : numericFields) {
				if (field.compareTo(numeriField) == 0) {
					isNumeric = true;
					break;
				}
			}
		}
		return isNumeric;
	}
	
	public String[] getNumericFields() {
		return numericFields;
	}

	public void setNumericFields(String[] numericFields) {
		this.numericFields = numericFields;
	}

}
