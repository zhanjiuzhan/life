package org.jcl.life.result;

/**
 * @author chenglei
 */
public class RetResult<D, R> {
    public static final String CODE200 = "200";
    public static final String CODE500 = "500";
    private String retCode;
    private D data;
    private R msg;

    private RetResult() {
        retCode = CODE200;
    }

    public String getRetCode() {
        return retCode;
    }

    public RetResult<D, R> setRetCode(String retCode) {
        this.retCode = retCode;
        return this;
    }

    public D getData() {
        return data;
    }

    public RetResult<D, R> setData(D data) {
        this.data = data;
        return this;
    }

    public R getMsg() {
        return msg;
    }

    public RetResult<D, R> setMsg(R msg) {
        this.msg = msg;
        return this;
    }

    public static <D, R> RetResult getRetResult(Class<D> D, Class<R> R) {
        return new RetResult<D, R>();
    }

    public static <D> RetResult getRetResult(Class<D> D) {
        return new RetResult<D, String>();
    }

    public static  RetResult getRetResult() {
        return new RetResult<String, String>();
    }
}
