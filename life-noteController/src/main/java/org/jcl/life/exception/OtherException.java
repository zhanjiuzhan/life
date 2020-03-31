package org.jcl.life.exception;


/**
 * @author chenglei
 */
public class OtherException extends Exception {

    private String msg;

    public OtherException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
