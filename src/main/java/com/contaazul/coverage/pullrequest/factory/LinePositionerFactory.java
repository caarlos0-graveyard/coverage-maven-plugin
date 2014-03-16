package com.contaazul.coverage.pullrequest.factory;

import org.eclipse.egit.github.core.CommitFile;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.git.OneToOneLinePositioner;
import com.contaazul.coverage.git.PatchLinePositioner;

public class LinePositionerFactory {
	public static LinePositioner build(CommitFile cf) {
		if ("added".equals( cf.getStatus() ))
			return new OneToOneLinePositioner();
		else
			return new PatchLinePositioner( cf.getPatch() );
	}
}
