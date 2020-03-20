package com.haici.health.base.facade.vo;

import com.haici.health.component.utils.vo.BaseResult;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 16:03
 * @Description:
 */
public class BaseFacadeRespVo<T> extends BaseResult<T> {
    private static final long serialVersionUID = -113129032243278668L;


    public BaseFacadeRespVo() {
    }

    public BaseFacadeRespVo(int code, String msg) {
        super(code, msg);
    }

    public BaseFacadeRespVo(int code, String msg, T data) {
        super(code, msg, data);
    }

    public static <T> BaseFacadeRespVo<T> succssFacadeResp(T data) {
        BaseFacadeRespVo<T> result = new BaseFacadeRespVo<>(0, null, data);
        return result;
    }

    public static <T> BaseFacadeRespVo<T> errorFacadeResp(int code, String msg, T data) {
        BaseFacadeRespVo<T> result = new BaseFacadeRespVo<>(0, msg, data);
        return result;
    }
}
