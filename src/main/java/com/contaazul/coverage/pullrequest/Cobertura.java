package com.contaazul.coverage.pullrequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Cobertura {
	final Logger logger = LoggerFactory.getLogger(Cobertura.class);

	void incrementCoverage(double coverage);

	double getCoverage();

	void incrementCoverage(int line, double coverage);

	int getLastLine();

	boolean isLowerThan(int allowed);

	boolean isCounted();
}
