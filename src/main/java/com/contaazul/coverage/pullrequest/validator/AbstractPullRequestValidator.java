package com.contaazul.coverage.pullrequest.validator;

import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.ClazzMapperImpl;
import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestComment;
import com.contaazul.coverage.pullrequest.UndercoveredException;
import com.contaazul.coverage.pullrequest.analyser.CommitFileAnalyser;
import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.CoberturaMapper;
import com.google.common.collect.Lists;

// XXX this class has too much responsibility.
public abstract class AbstractPullRequestValidator implements PullRequestValidator {
	private static final String MESSAGE = "The new lines added are with %.2f%% of %d%% minimum allowed code coverage.";
	private static final Logger logger = LoggerFactory.getLogger( PullRequestValidator.class );
	private final int minCoverage;
	private final GithubService gh;
	private ClazzMapper mapper;
	private ChunkBlammer chunkBlammer;

	public AbstractPullRequestValidator(GithubService gh, Coverage coverage, String srcFolder, int minCoverage) {
		this.gh = gh;
		this.minCoverage = minCoverage;
		this.mapper = new ClazzMapperImpl( coverage, srcFolder );
		this.chunkBlammer = new ChunkBlammer( minCoverage, gh );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.IPullRequestValidator#validate()
	 */
	@Override
	public void validate() {
		final List<Cobertura> coberturas = Lists.newArrayList();
		final CommitFileAnalyser analyser = new CommitFileAnalyser( mapper, chunkBlammer );
		for (CommitFile file : gh.getPullRequestCommitFiles())
			coberturas.add( analyser.analyse( file ) );
		checkTotalCoverage( CoberturaMapper.map( coberturas ) );
	}

	private void checkTotalCoverage(Cobertura cobertura) {
		logger.info( String.format( MESSAGE, cobertura.getCoverage(), minCoverage ) );
		if (cobertura.isLowerThan( minCoverage ))
			blameLowCoverage( cobertura );
	}

	private void blameLowCoverage(Cobertura cobertura) {
		gh.createComment( PullRequestComment.MSG );
		if (breakOnLowCoverage())
			throw new UndercoveredException( cobertura, minCoverage );
	}

	protected abstract boolean breakOnLowCoverage();
}
