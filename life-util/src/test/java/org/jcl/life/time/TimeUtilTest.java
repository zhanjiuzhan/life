package org.jcl.life.time;

import org.junit.Test;

public class TimeUtilTest {

	@Test
	public void getTodayTimeTest() {
		System.out.println(TimeUtil.getTodayTime());
		System.out.println(TimeUtil.getTodayTime("yyyy-MM-dd"));
	}
}
