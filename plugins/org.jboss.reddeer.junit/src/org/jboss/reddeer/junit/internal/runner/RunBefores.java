package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores extends Statement {
	private final Statement fNext;

	private final Object fTarget;

	private final List<FrameworkMethod> fBefores;

	public RunBefores(Statement next, List<FrameworkMethod> befores,
			Object target) {
		fNext = next;
		fBefores = befores;
		fTarget = target;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMethod before = null;
		try {
			for (FrameworkMethod bfr : fBefores) {
				before = bfr;
				before.invokeExplosively(fTarget);
			}
		} catch (Throwable throwable) {
			CaptureScreenshot capturer = new CaptureScreenshot();
			capturer.captureScreenshot(fTarget.getClass().getCanonicalName() + "-" + before.getName());
			throw throwable;
		}
		fNext.evaluate();
	}
}