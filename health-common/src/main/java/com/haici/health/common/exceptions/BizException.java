package com.haici.health.common.exceptions;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/20 16:03
 * @Description:
 */
public class BizException extends RuntimeException {


    private static final long serialVersionUID = 7184311750797793283L;
    /**
     * 具体异常码
     */
    protected int code;

    /**
     * 异常信息
     */
    protected String msg;


    public BizException(int code, String msg, Object... args) {
        super(BizException.formatMsg(msg, args));
        this.code = code;
        this.msg = super.getMessage();

    }

    private static String formatMsg(String msg, Object... args) {
        try {
            return String.format(msg, args);
        } catch (Exception e) {
            return msg;
        }
    }


    public BizException(Throwable cause,int code, String msg,Object... args ) {
        this(code, msg,args);
        this.initCause(cause);
    }

}
