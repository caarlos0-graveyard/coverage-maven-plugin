package com.contaazul.coverage.pullrequest.validator;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestCommitComment;

public class ChunkBlammerTest {
	private ChunkBlammer blammer;

	@Mock
	private GithubService gh;

	@Mock
	private CommitFile file;

	@Before
	public void init() {
		initMocks( this );
		blammer = spy(new ChunkBlammer( 100, gh ));
	}

	@Test
	public void testBlame() throws Exception {
		blammer.blame( file, 42.0, 10 );
		verify( gh, times( 1 ) ).createComment( any( PullRequestCommitComment.class ) );
	}
}
