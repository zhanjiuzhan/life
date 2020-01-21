package org.jcl.life;

import java.util.UUID;

public class MyUtils {
    private MyUtils() {
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}
