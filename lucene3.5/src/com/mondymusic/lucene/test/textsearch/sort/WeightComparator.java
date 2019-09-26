package com.uv.smp.persistence.textsearch.sort;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldComparator;


public   class  WeightComparator  extends  FieldComparator {

	@Override
	public int compare(int arg0, int arg1) {
		return 0;
	}

	@Override
	public int compareBottom(int arg0) throws IOException {
		return 0;
	}

	@Override
	public void copy(int arg0, int arg1) throws IOException {
		
	}

	@Override
	public void setBottom(int arg0) {
		
	}

	@Override
	public void setNextReader(IndexReader arg0, int arg1) throws IOException {
		
	}

	@Override
	public Object value(int arg0) {
		return null;
	}


}