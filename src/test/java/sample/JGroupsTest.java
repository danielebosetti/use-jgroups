package sample;

import org.jgroups.util.Util;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JGroupsTest {

	private static final Logger LOG = LoggerFactory.getLogger(JGroupsTest.class);

	@Test
	public void testName() throws Exception {
		for (int i = 0; i < 100; i++) {
			long start = System.currentTimeMillis();
			Util.getAllAvailableInterfaces();
			long delta = System.currentTimeMillis() - start;
			LOG.info("delta(msec)={}", delta);
		}
	}
}
