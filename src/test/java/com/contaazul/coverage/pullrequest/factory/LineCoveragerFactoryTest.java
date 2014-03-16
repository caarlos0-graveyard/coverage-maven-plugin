package com.contaazul.coverage.pullrequest.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.cobertura.entity.Clazz;

public class LineCoveragerFactoryTest {
	@Mock
	private ClazzMapper mapper;

	@Mock
	private CommitFile file;

	@Before
	public void init() {
		initMocks( this );
	}

	@Test
	public void testValidClazz() throws Exception {
		when( mapper.getClazz( anyString() ) ).thenReturn( new Clazz() );
		LineCoverager coverager = LineCoveragerFactory.build( file, mapper );
		assertNotNull( coverager );
	}

	@Test
	public void testNullClazz() throws Exception {
		when( mapper.getClazz( anyString() ) ).thenReturn( null );
		LineCoverager coverager = LineCoveragerFactory.build( file, mapper );
		assertNull( coverager );
	}
}
