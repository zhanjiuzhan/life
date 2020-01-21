package org.jcl.life.string;

final public class StringUtils {

    public static boolean isEmpty(final String str) {
        return null == str || str.length() <= 0;
    }

    public static boolean isNotEmpty(final String... str) {
        if (str.length == 1) {
            return !isEmpty(str[0]);
        } else {
            for (String s : str) {
                if (isEmpty(s)) {
                    return false;
                }
            }
            return true;
        }
    }
}
