package com.haici.health.base.facade.vo;

import com.haici.health.component.utils.common.GlobalContext;
import com.haici.health.component.utils.common.HcContext;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/20 15:49
 * @Description:
 */
public class BaseFacadeReqVo<T> implements Serializable {
    private static final long serialVersionUID = -5312570298387162831L;

    /**
     * 上下文
     */
    protected HcContext context;

    /**
     * 请求数据
     */
    protected T data;

    /**
     * 构造函数
     */
    public BaseFacadeReqVo() {
        this(null);
    }

    public BaseFacadeReqVo(T data) {
        this(GlobalContext.getHcContext(),data);

    }

    public BaseFacadeReqVo(HcContext context, T data) {
        this.context = context;
        this.data = data;
    }

    public static <T> BaseFacadeReqVo<T> newInstance(T data)
    {
        BaseFacadeReqVo<T> reqVo = new BaseFacadeReqVo(data);
        return reqVo;
    }

    public HcContext getContext() {

        if(null==context)
        {
            context = GlobalContext.getHcContext();
        }
        return context;
    }

    public void setContext(HcContext context) {
        HcContext tempContext = GlobalContext.getHcContext();
        if(null!=context)
        {
            if(!Objects.equals(context,tempContext))
            {
                GlobalContext.setHcConext(context);
            }
        }
        this.context = context;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public <E> void proxySetAttr(String key, E value) {
        if (null != getContext()) {
            context.setAttr(key, value);
        }

    }

    public <E> E proxyGetAttr(String key) {
        if (null != getContext()) {
            return context.getAttr(key);
        }

        return null;

    }

    public Map<String, Object> proxyGetAttrs() {
        if (null != getContext()) {
            return context.getAttrs();
        }

        return null;

    }

    public void proxySetAttrs(Map<String, Object> attrs) {
        if (null != getContext()) {
            context.setAttrs(attrs);
        }

    }

    public String proxyGetMsgSeq() {
        if (null != getContext()) {
            return context.getMsgSeq();
        }

        return null;
    }

    public void proxySetMsgSeq(String msgSeq) {
        if (null != getContext()) {
            context.setMsgSeq(msgSeq);
        }

    }

    public String proxyGetClientIP() {
        if (null != getContext()) {
            return context.getClientIP();
        }
        return null;
    }

    public void proxySetClientIP(String clientIP) {
        if (null != getContext()) {
            context.setClientIP(clientIP);
        }

    }

}
