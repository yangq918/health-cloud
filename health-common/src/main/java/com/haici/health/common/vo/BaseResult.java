package com.haici.health.common.vo;

import com.haici.health.common.exceptions.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 15:59
 * @Description:
 */
@Data
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 4967300318208858379L;

    protected int code = 0;
    protected String msg;
    protected T data;

    public BaseResult() {
    }

    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResult<T> succssResult(T data)
    {
        BaseResult<T> result = new BaseResult<>(0,null,data);
        return result;
    }

    public static <T> BaseResult<T> errorResult(int code,String msg,T data)
    {
        BaseResult<T> result = new BaseResult<>(0,msg,data);
        return result;
    }


    public T checkData() {
        if (code == 0) {
            return data;
        }
        throw new BizException(code, msg);
    }
}
