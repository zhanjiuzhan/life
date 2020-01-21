package org.jcl.life.result;

import org.springframework.util.ObjectUtils;

public class RetResult<D, R> {
    public static final String CODE200 = "200";
    public static final String CODE500 = "500";
    private String retCode;
    private D data;
    private R msg;

    public RetResult() {
        retCode = CODE200;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public R getMsg() {
        return msg;
    }

    public void setMsg(R msg) {
        this.msg = msg;
    }
}
