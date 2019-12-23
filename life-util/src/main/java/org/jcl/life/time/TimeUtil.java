package org.jcl.life.time;

import org.jcl.life.string.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

final public class TimeUtil {
	public final static String defaultOneDateFmt = "yyyy/MM/dd";

	/**
	 * 根据format取得今天的日期
	 * @param format
	 * @return
	 */
	public static String getTodayTime(final String format) {
		SimpleDateFormat sdf =
				new SimpleDateFormat(StringUtils.isEmpty(format) ? defaultOneDateFmt : format);
		return sdf.format(new Date());
	}

	/**
	 * 取得今天的日期
	 * @return
	 */
	public static String getTodayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultOneDateFmt);
		return sdf.format(new Date());
	}
}
