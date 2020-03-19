package com.haici.health.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/20 15:40
 * @Description:
 */
@Data
public class HcContext implements Serializable {

    private static final long serialVersionUID = -2083461530530278152L;

    private Map<String, Object> attrs;
    private String msgSeq;
    private String clientIP;// 用户IP

    public void setAttr(String key, Object value) {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }
        this.attrs.put(key, value);
    }

    public <T> T getAttr(String key) {
        if (attrs != null) {
            return (T) this.attrs.get(key);
        }
        return null;
    }


    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    public String getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }
}
