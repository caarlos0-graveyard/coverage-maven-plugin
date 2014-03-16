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

	public CommitFileAnalyser(ClazzMapper mapper, ChunkBlammer blammer) {
		super();
		this.mapper = mapper;
		this.blammer = blammer;
	}

	public Cobertura analyse(CommitFile file) {
		logger.debug( "File: " + file.getFilename() );
		if (file.getPatch() == null)
			return new NullCobertura();
		return analyseChunks( file );
	}

	private Cobertura analyseChunks(CommitFile file) {
		final LinePositioner positioner = LinePositionerFactory.build( file );
		final LineCoverager coverager = LineCoveragerFactory.build( file, mapper );
		if (positioner == null || coverager == null)
			return new NullCobertura();
		return analyseFile( file, positioner, coverager );
	}

	private Cobertura analyseFile(CommitFile file, final LinePositioner positioner, final LineCoverager coverager) {
		final List<Cobertura> fileCoverage = Lists.newArrayList();
		final ChunkAnalyser analyser = new ChunkAnalyser( coverager, positioner, blammer );
		for (Map<Integer, Integer> chunk : positioner.getChunks())
			fileCoverage.add( analyser.analyse( chunk, file ) );
		return CoberturaMapper.map( fileCoverage );
	}
}
