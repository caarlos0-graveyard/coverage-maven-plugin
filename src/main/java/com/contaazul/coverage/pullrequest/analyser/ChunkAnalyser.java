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

	public ChunkAnalyser(LineCoverager coverager, LinePositioner positioner, ChunkBlammer blammer) {
		this.coverager = coverager;
		this.positioner = positioner;
		this.blammer = blammer;
	}

	public Cobertura analyse(Map<Integer, Integer> chunk, CommitFile file) {
		logger.debug( "Analysing chunk " + chunk );
		Cobertura coverage = getChunkCoverage( chunk, coverager );
		blammer.blame( file, coverage.getCoverage(), positioner.toPosition( coverage.getLastLine() ) );
		return coverage;
	}

	private Cobertura getChunkCoverage(Map<Integer, Integer> chunk, LineCoverager coverager) {
		final Cobertura chunkCoverage = new CoberturaImpl();
		for (Entry<Integer, Integer> line : chunk.entrySet())
			analyseLine( coverager, chunkCoverage, line.getKey() );
		return chunkCoverage;
	}

	private void analyseLine(LineCoverager coverager, Cobertura chunkCoverage, int line) {
		final Integer lineCoverage = coverager.getLineCoverage( line );
		if (lineCoverage != null)
			chunkCoverage.incrementCoverage( line, lineCoverage );
	}
}
