package com.contaazul.coverage.pullrequest.cobertura;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.CoberturaImpl;

public class CoberturaTest {
	private Cobertura cobertura = null;

	@Before
	public void init() {
		cobertura = new CoberturaImpl();
	}

	@Test
	public void testWithoutIncrement() throws Exception {
		assertEquals( 100, cobertura.getCoverage(), 0.0001 );
		assertEquals( 0, cobertura.getLastLine() );
	}
	
	@Test
	public void testIncrement() throws Exception {
		cobertura.incrementCoverage( 80 );
		cobertura.incrementCoverage( 20 );
		assertEquals( 50.0, cobertura.getCoverage(), 0.0001 );
		assertEquals( 0, cobertura.getLastLine() );
	}

	@Test
	public void testIncrementWithLineNumber() throws Exception {
		cobertura.incrementCoverage( 2, 100 );
		cobertura.incrementCoverage( 5, 50 );
		assertEquals( 75.0, cobertura.getCoverage(), 0.001 );
		assertEquals( 5, cobertura.getLastLine() );
	}
}
