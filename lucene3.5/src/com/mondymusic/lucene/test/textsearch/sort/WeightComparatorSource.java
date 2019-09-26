package com.uv.smp.persistence.textsearch.sort;

import java.io.IOException;

import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;



//cobus
	public   class  WeightComparatorSource  extends  FieldComparatorSource {

		@Override
		public FieldComparator newComparator(String arg0, int arg1, int arg2, boolean arg3) throws IOException {
			return new WeightComparator();
		}
		
	}
	
