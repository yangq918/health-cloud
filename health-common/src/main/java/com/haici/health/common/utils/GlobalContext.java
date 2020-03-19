package com.haici.health.common.utils;

import com.haici.health.common.vo.HcContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/20 15:55
 * @Description:
 */
public class GlobalContext extends ConcurrentHashMap<String, Object> {


    private static final long serialVersionUID = 8915281748336008896L;

    protected static final InheritableThreadLocal<GlobalContext> THREAD_LOCAL = new InheritableThreadLocal<GlobalContext>(){
        @Override
        protected GlobalContext initialValue() {
            return new GlobalContext();
        }
    };

    /**
     * 全局hccontext key
     */
    public static final String HC_CONTEXT_KEY = "GLOBAL_HC_CONTEXT_LOCAL_KEY";


    /**
     * 获取当前上下文
     *
     * @return
     */
    public static GlobalContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * 返回HcContext，可能为null
     *
     * @return HcContext，可能为null
     */
    public static HcContext getHcContext() {
        GlobalContext global = THREAD_LOCAL.get();
        return (HcContext) global.get(GlobalContext.HC_CONTEXT_KEY);
    }


    /**
     * 设置HcContext
     *
     * @param hcConext
     */
    public static void setHcConext(HcContext hcConext) {
        GlobalContext global = THREAD_LOCAL.get();
        global.put(GlobalContext.HC_CONTEXT_KEY, hcConext);
    }

    /**
     * 清空本地变量
     */
    public static void unset() {
        THREAD_LOCAL.remove();
    }
}
