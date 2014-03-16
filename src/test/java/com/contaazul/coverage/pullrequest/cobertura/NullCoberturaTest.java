package com.contaazul.coverage.pullrequest.cobertura;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.NullCobertura;

public class NullCoberturaTest {
	private Cobertura cobertura;

	@Before
	public void init() {
		cobertura = new NullCobertura();
	}

	@Test
	public void testNullCobertura() throws Exception {
		cobertura.incrementCoverage( 10 );
		cobertura.incrementCoverage( 10, 30 );
		assertEquals( 100.0, cobertura.getCoverage(), 0.0001 );
		assertEquals( -1, cobertura.getLastLine() );
		assertTrue( cobertura.isLowerThan( 42 ) );
	}
}
