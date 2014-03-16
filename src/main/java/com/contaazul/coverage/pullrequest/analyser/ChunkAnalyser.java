package com.contaazul.coverage.pullrequest.analyser;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.CoberturaImpl;
import com.contaazul.coverage.pullrequest.validator.ChunkBlammer;

public class ChunkAnalyser {
	private static final Logger logger = LoggerFactory.getLogger( ChunkAnalyser.class );
	private LinePositioner positioner;
	private LineCoverager coverager;
	private final ChunkBlammer blammer;
	private int minCoverage;

	public ChunkAnalyser(LineCoverager coverager, LinePositioner positioner, ChunkBlammer blammer, int minCoverage) {
		this.coverager = coverager;
		this.positioner = positioner;
		this.blammer = blammer;
		this.minCoverage = minCoverage;
	}

	public Cobertura analyse(Map<Integer, Integer> chunk, CommitFile file) {
		logger.debug( "Analysing chunk " + chunk );
		final Cobertura coverage = getChunkCoverage( chunk, coverager );
		logger.info( "Chunck " + chunk + " has " + coverage.toString() + " coverage " + coverage.getCoverage() );
		if (coverage.isLowerThan( minCoverage ))
			blammer.blame( file, coverage.getCoverage(), positioner.toPosition( coverage.getLastLine() ) );
		return coverage;
	}

	private Cobertura getChunkCoverage(Map<Integer, Integer> chunk, LineCoverager coverager) {
		final Cobertura coverage = new CoberturaImpl();
		for (Entry<Integer, Integer> line : chunk.entrySet())
			analyseLine( coverager, coverage, line.getKey() );
		return coverage;
	}

	private void analyseLine(LineCoverager coverager, Cobertura chunkCoverage, int line) {
		final Integer coverage = coverager.getCoverage( line );
		logger.debug( "Line " + line + " has " + coverage + " coverage " );
		if (coverage != null)
			chunkCoverage.incrementCoverage( line, coverage );
	}
}
