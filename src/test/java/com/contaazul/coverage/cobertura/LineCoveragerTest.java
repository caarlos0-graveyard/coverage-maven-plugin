package com.contaazul.coverage.cobertura;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.cobertura.entity.Condition;
import com.contaazul.coverage.cobertura.entity.Line;

public class LineCoveragerTest {

	private Clazz clazz;
	private LineCoverager cov;

	@Before
	public void init() {
		clazz = new Clazz();
	}

	@Test
	public void emptyClazz() throws Exception {
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ) == 100 );
	}

	@Test
	public void testInvalidLine() throws Exception {
		clazz.getLines().add( new Line( 2, 10, false, null, null ) );
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ) == 100 );
	}

	@Test
	public void testValidLineCovered() throws Exception {
		clazz.getLines().add( new Line( 1, 10, false, null, null ) );
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ).equals( 100 ) );
	}

	@Test
	public void testValidLineUncovered() throws Exception {
		clazz.getLines().add( new Line( 1, 0, false, null, null ) );
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ) == 0 );
	}

	@Test
	public void testValidBranchLine() throws Exception {
		clazz.getLines().add(
				new Line( 1, 10, true, "20% (1/10)", Arrays.asList( new Condition( 1, "jump", "20% (1/10)" ) ) ) );
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ) == 20 );
	}

	@Test
	public void testInvalidBranchLine() throws Exception {
		clazz.getLines().add( new Line( 1, 10, true, "20% (1/10)", null ) );
		cov = new LineCoveragerImpl( clazz );
		assertTrue( cov.getCoverage( 1 ) == 20 );
	}

	@Test(expected = CoveragerException.class)
	public void testNullClazz() throws Exception {
		cov = new LineCoveragerImpl( null );
		assertFalse( cov.getCoverage( 1 ) > -1 );
	}
}
