package com.contaazul.coverage;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.github.GithubRepo;
import com.contaazul.coverage.maven.CoverageMavenProject;
import com.contaazul.coverage.pullrequest.PullRequestValidator;
import com.contaazul.coverage.pullrequest.PullRequestValidatorBuilder;

/**
 * 
 * @goal publish
 * @author carlos.becker
 * 
 */
public class CoveragePullRequestMojo extends AbstractMojo {

	private static final Logger logger = LoggerFactory
			.getLogger(CoveragePullRequestMojo.class);

	/**
	 * Set OAuth2 token
	 * 
	 * @parameter property="github.oauth2"
	 */
	private String oauth2;

	/**
	 * Github pull request ID
	 * 
	 * @parameter property="github.pullRequestId"
	 */
	private int pullRequestId;

	/**
	 * Github repository owner
	 * 
	 * @parameter property="github.repositoryOwner"
	 */
	private String repositoryOwner;

	/**
	 * Github repository name
	 * 
	 * @parameter property="github.repositoryName"
	 */
	private String repositoryName;

	/**
	 * The minimum % coverage accepted.
	 * 
	 * @parameter property="minCoverage" default-value="100"
	 */
	private int minimumCoverage;

	/**
	 * Wheter to break or not the build when low coverage.
	 * 
	 * @parameter property="fail" default-value="false"
	 */
	private boolean breakOnLowCov;
	
	/**
	 * Wheter to break or not the build when low coverage.
	 * 
	 * @parameter property="commentChunks" default-value="true"
	 */
	private boolean commentChunks;

	/**
	 * Maven project info.
	 * 
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * The projects in the reactor.
	 * 
	 * @parameter property="reactorProjects"
	 * @readonly
	 */
	private List<MavenProject> reactorProjects;

	@Override
	public void execute() {
		if (project.isExecutionRoot() && reactorProjects.isEmpty())
			return;
		logger.info("Executing on " + project);
		final PullRequestValidator pr = new PullRequestValidatorBuilder()
				.oauth2(oauth2)
				.pullRequest(pullRequestId)
				.repository(new GithubRepo(repositoryName, repositoryOwner))
				.minCoverage(minimumCoverage)
				.breakOnLowCov(breakOnLowCov)
				.commentChunks(commentChunks)
				.build();
		pr.validate(new CoverageMavenProject(project));

	}
}
