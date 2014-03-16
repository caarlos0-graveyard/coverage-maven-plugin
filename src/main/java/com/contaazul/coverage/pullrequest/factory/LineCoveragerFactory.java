package com.contaazul.coverage.pullrequest.factory;

import org.eclipse.egit.github.core.CommitFile;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.cobertura.LineCoveragerImpl;
import com.contaazul.coverage.cobertura.entity.Clazz;

public final class LineCoveragerFactory {
	public final static LineCoverager build(CommitFile commitFile, ClazzMapper mapper) {
		final Clazz clazz = mapper.getClazz( commitFile.getFilename() );
		if (clazz == null)
			return null;
		return new LineCoveragerImpl( clazz );
	}
}