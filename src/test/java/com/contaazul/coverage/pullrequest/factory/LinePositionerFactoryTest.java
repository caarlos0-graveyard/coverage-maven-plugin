package com.contaazul.coverage.pullrequest.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.git.OneToOneLinePositioner;
import com.contaazul.coverage.git.PatchLinePositioner;

public class LinePositionerFactoryTest {
	@Mock
	private CommitFile file;

	@Before
	public void init() {
		initMocks( this );
	}

	@Test
	public void testAdded() throws Exception {
		when( file.getStatus() ).thenReturn( "added" );
		LinePositioner positioner = LinePositionerFactory.build( file );
		assertNotNull( positioner );
		assertTrue( positioner instanceof OneToOneLinePositioner );
	}
	
	@Test
	public void testPatch() throws Exception {
		when( file.getStatus() ).thenReturn( "" );
		when( file.getPatch() ).thenReturn( "" );
		LinePositioner positioner = LinePositionerFactory.build( file );
		assertNotNull( positioner );
		assertTrue( positioner instanceof PatchLinePositioner );
	}
}
