package com.haici.health.component.cache;

import java.util.List;

public interface CacheClient {
    <T> T get(String key);
    
    <T> T getNoPrefix(String key);
    
    <T> boolean set(String key, T o);

    /**
     * 
     * @param key
     * @param o
     * @param timeout
     *            单位s
     * @return
     */
    <T> boolean set(String key, T o, int timeout);
   
    boolean delete(String key);
    
    /**
     * 
     * @author dengzhineng 
     * @date: 2017年3月15日 下午5:14:09
     * @version 1.0
     *
     * @param key
     * @return
     */
    public long incr(String key);
    
    /**
     * 
     * @author dengzhineng 
     * @date: 2017年7月13日 下午4:59:15
     * @version 1.0
     *
     * @param key
     * @param expire -1：代表key永久 ， 0：代表不改變超時時間 
     * @return
     */
    public long incr(String key, int expire);
    /**
     * 
     * @author dengzhineng 
     * @date: 2017年3月16日 下午9:32:58
     * @version 1.0
     *
     * @param key
     * @return
     */
    public Long getIncrValue(String key);
    
    /**
     * 设置incr值
     * @author dengzhineng 
     * @date: 2017年6月9日 下午6:51:05
     * @version 1.0
     *
     * @param key
     * @param value
     * expire expire -1：代表key永久 ， 0：代表不改變超時時間 
     */
    public void setIncrValue(String key, long value, int expire);


    /**
     *
     * @param script
     * @param keys
     * @param args
     * @param clz
     * @param <T>
     * @return
     */
     <T> T eval(String script, List<String> keys, List<String> args,Class<T> clz);
    
    /**
     * exists
     * @author dengzhineng 
     * @date: 2017年8月28日 下午4:12:46
     * @version 1.0
     *
     * @param key
     * @return
     */
    Boolean exists(final String key);
    
    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    public void setExpire(String key, int expire);
    /**
     * 获取真实的缓存对象
     * @author dengzhineng 
     * @date: 2017年3月16日 下午8:00:19
     * @version 1.0
     *
     * @return
     */
    public Object getCacheObject();
}
