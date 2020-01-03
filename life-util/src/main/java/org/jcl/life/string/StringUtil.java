package org.jcl.life.string;

final public class StringUtil {

	/**
	 * 判断对象是否有效
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(final Object obj) {
		return null == obj || "".equals(obj);
	}
}
