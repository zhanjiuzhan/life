package org.jcl.life.annotation;

import java.lang.annotation.*;

/**
 * @author chenglei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface Jsonp {
}
