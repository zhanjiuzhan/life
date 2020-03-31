package org.jcl.life.controller.parent;

import org.jcl.life.annotation.Jsonp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @author chenglei
 */
@ControllerAdvice(annotations = {Jsonp.class})
public class MyControllerAdvice extends AbstractJsonpResponseBodyAdvice {

    /**
     * AbstractJsonpResponseBodyAdvice 用来支持jsonp
     */
    public MyControllerAdvice() {
        super("callback", "jsonp");
    }
}
