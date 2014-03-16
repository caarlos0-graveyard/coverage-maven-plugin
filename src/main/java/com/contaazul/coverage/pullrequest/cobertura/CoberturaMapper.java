package com.contaazul.coverage.pullrequest.cobertura;

import java.util.List;

public class CoberturaMapper {
	public static Cobertura map(List<Cobertura> coverages) {
		Cobertura coverage = new CoberturaImpl();
		for (Cobertura cov : coverages) {
			coverage.incrementCoverage( cov.getCoverage() );
		}
		return coverage;
	}
}
