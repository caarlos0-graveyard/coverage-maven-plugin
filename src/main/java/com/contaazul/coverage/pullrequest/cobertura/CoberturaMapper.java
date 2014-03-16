package com.contaazul.coverage.pullrequest.cobertura;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoberturaMapper {
	private static final Logger logger = LoggerFactory.getLogger( CoberturaMapper.class );

	public static Cobertura map(List<Cobertura> coverages) {
		logger.debug( "Total coverages to map: " + coverages.size() );
		final Cobertura coverage = new CoberturaImpl();
		for (Cobertura cov : coverages)
			coverage.incrementCoverage( cov.getCoverage() );
		logger.debug( "Mapped coverage: " + coverage );
		return coverage;
	}
}
