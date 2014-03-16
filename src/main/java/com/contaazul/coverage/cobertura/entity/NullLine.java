package com.contaazul.coverage.cobertura.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class NullLine extends Line {
	@Override
	public int getNumber() {
		return -1;
	}

	@Override
	public int getHits() {
		return 1;
	}

	@Override
	public boolean isBranch() {
		return false;
	}

	@Override
	public String getConditionCoverage() {
		return null;
	}

	@Override
	public List<Condition> getConditions() {
		return Lists.newArrayList();
	}

	@Override
	public String toString() {
		return "NullLine [getNumber()=" + getNumber() + ", getHits()=" + getHits() + ", isBranch()=" + isBranch()
				+ ", getConditionCoverage()=" + getConditionCoverage() + ", getConditions()=" + getConditions() + "]";
	}
}
