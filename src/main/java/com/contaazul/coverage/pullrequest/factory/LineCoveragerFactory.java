package com.contaazul.coverage.pullrequest.factory;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.cobertura.LineCoveragerImpl;
import com.contaazul.coverage.cobertura.entity.Clazz;

public final class LineCoveragerFactory {
	private static final Logger logger = LoggerFactory.getLogger( LineCoveragerFactory.class );

	public final static LineCoverager build(CommitFile commitFile, ClazzMapper mapper) {
		logger.debug( "filename: " + commitFile.getFilename() );
		final Clazz clazz = mapper.getClazz( commitFile.getFilename() );
		if (clazz == null)
			return null;
		return new LineCoveragerImpl( clazz );
	}
}
