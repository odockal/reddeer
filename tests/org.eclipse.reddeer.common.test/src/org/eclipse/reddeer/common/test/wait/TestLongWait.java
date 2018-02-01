package org.eclipse.reddeer.common.test.wait;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.junit.Test;

public class TestLongWait {

	private static final Logger log = Logger.getLogger(TestLongWait.class);
	
	private class NeverFullfiledCondition extends AbstractWaitCondition {

		@Override
		public boolean test() {
			return false;
		}
	}
	
	@Test
	public void testLongWait() {
		log.info("Starting waiting test");
		new WaitUntil(new NeverFullfiledCondition(), TimePeriod.getCustom(1800), false);
		log.info("First Waiting successfully ended");
		new WaitUntil(new NeverFullfiledCondition(), TimePeriod.getCustom(1800), false);
		log.info("All Waiting successfully ended");
	}
}
