package com.contaazul.coverage.pullrequest.analyser;

import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.cobertura.Cobertura;
import com.contaazul.coverage.pullrequest.cobertura.CoberturaMapper;
import com.contaazul.coverage.pullrequest.cobertura.NullCobertura;
import com.contaazul.coverage.pullrequest.factory.LineCoveragerFactory;
import com.contaazul.coverage.pullrequest.factory.LinePositionerFactory;
import com.contaazul.coverage.pullrequest.validator.ChunkBlammer;
import com.google.common.collect.Lists;

public class CommitFileAnalyser {
	private static final Logger logger = LoggerFactory.getLogger( CommitFileAnalyser.class );
	private ClazzMapper mapper;
	private ChunkBlammer blammer;
	private int minCoverage;

	public CommitFileAnalyser(ClazzMapper mapper, ChunkBlammer blammer, int minCoverage) {
		super();
		this.mapper = mapper;
		this.blammer = blammer;
		this.minCoverage = minCoverage;
	}

	public Cobertura analyse(CommitFile file) {
		if (file.getPatch() == null)
			return new NullCobertura();
		Cobertura cobertura = analyseFile( file );
		logger.debug( "File: " + file.getFilename() + " has " + cobertura.getCoverage() + "% coverage" );
		return cobertura;
	}

	private Cobertura analyseFile(CommitFile file) {
		final LinePositioner positioner = LinePositionerFactory.build( file );
		final LineCoverager coverager = LineCoveragerFactory.build( file, mapper );
		if (positioner == null || coverager == null)
			return new NullCobertura();
		return analyse( file, positioner, coverager );
	}

	private Cobertura analyse(CommitFile file, final LinePositioner positioner, final LineCoverager coverager) {
		final List<Cobertura> fileCoverage = Lists.newArrayList();
		final ChunkAnalyser analyser = new ChunkAnalyser( coverager, positioner, blammer, minCoverage );
		for (Map<Integer, Integer> chunk : positioner.getChunks())
			fileCoverage.add( analyser.analyse( chunk, file ) );
		return CoberturaMapper.map( fileCoverage );
	}
}
