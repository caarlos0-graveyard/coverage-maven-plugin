package com.contaazul.coverage.pullrequest.validator;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestCommitComment;
import com.contaazul.coverage.github.PullRequestSHARetriever;

public class ChunkBlammer {
	private static final Logger logger = LoggerFactory.getLogger( ChunkBlammer.class );
	private final PullRequestSHARetriever shas;
	private final int minCoverage;
	private final GithubService gh;

	public ChunkBlammer(int minCoverage, GithubService gh) {
		super();
		this.minCoverage = minCoverage;
		this.gh = gh;
		this.shas = new PullRequestSHARetriever( gh );
	}

	public void blame(CommitFile cf, double coverage, int position) {
		logger.debug( "Blamming chunk on " + cf.getFilename() + " for the coverage " + coverage + " in position "
				+ position );
		final String sha = shas.get( cf );
		final String filename = cf.getFilename();
		comment( coverage, position, sha, filename );
	}

	private void comment(double coverage, int position, String sha, String filename) {
		PullRequestCommitComment comment = new PullRequestCommitComment( coverage, minCoverage, sha,
				filename, position );
		gh.createComment( comment );
	}
}
