package org.jcl.life.exception;

import org.jcl.life.result.RetResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author chenglei
 */
@ControllerAdvice
@ResponseBody
public class ExceptionController {

    @ExceptionHandler(OtherException.class)
    public RetResult otherException(Exception e) {
        return RetResult.getRetResult().setMsg(e.toString());
    }
}
