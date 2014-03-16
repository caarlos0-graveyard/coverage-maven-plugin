package com.contaazul.coverage.pullrequest.analyser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.IgnoredCobertura;
import com.contaazul.coverage.pullrequest.cobertura.NullCobertura;
import com.contaazul.coverage.pullrequest.validator.ChunkBlammer;

public class CommitFileAnalyserTest {

	@Mock
	private ClazzMapper mapper;
	@Mock
	private ChunkBlammer blammer;
	private CommitFileAnalyser analyser;
	private int minCoverage = 100;

	@Before
	public void init() {
		initMocks( this );
		analyser = new CommitFileAnalyser( mapper, blammer, minCoverage );
	}

	@Test
	public void testNullPatch() throws Exception {
		CommitFile file = mock( CommitFile.class );
		when( file.getPatch() ).thenReturn( null );
		Cobertura cobertura = analyser.analyse( file );
		assertNotNull( cobertura );
		assertTrue( cobertura instanceof NullCobertura );
	}
	
	@Test
	public void testNullCoverager() throws Exception {
		CommitFile file = mock( CommitFile.class );
		when( file.getPatch() ).thenReturn( "" );
		Cobertura cobertura = analyser.analyse( file );
		assertNotNull( cobertura );
		assertTrue( cobertura instanceof IgnoredCobertura );
	}
}
