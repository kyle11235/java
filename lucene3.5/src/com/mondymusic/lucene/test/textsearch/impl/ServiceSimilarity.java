package com.uv.smp.persistence.textsearch.impl;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.DefaultSimilarity;

public class ServiceSimilarity extends DefaultSimilarity{

	private static final long serialVersionUID = 3852372882521406619L;

	@Override
	public float computeNorm(String field, FieldInvertState state) {
		return super.computeNorm(field, state);
	}

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		return super.queryNorm(sumOfSquaredWeights);
	}

	@Override
	public float tf(float freq) {
		return 1f;
	}

	@Override
	public float sloppyFreq(int distance) {
		return super.sloppyFreq(distance);
	}

	@Override
	public float idf(int docFreq, int numDocs) {
		return 1f;
	}

	@Override
	public float coord(int overlap, int maxOverlap) {
		return super.coord(overlap, maxOverlap);
	}

	@Override
	public void setDiscountOverlaps(boolean v) {
		super.setDiscountOverlaps(v);
	}

	@Override
	public boolean getDiscountOverlaps() {
		return true;
	}
	
}
