package com.contaazul.coverage.pullrequest.validator;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.github.GithubService;

public class BuildBreakerPulRequestValidatorTest {
	@Mock
	private GithubService gh;
	
	@Mock
	private Coverage coverage;
	
	private String src = "target/tmp/src/main/java";
	private int minCov = 100;
	
	private AbstractPullRequestValidator validator;
	
	@Before
	public void init() {
		initMocks( this );
		validator = new BuildBreakerPullRequestValidator( gh, coverage, src, minCov );
	}
	
	@Test
	public void testNonBreak() throws Exception {
		assertTrue( validator.breakOnLowCoverage() );
	}
}
