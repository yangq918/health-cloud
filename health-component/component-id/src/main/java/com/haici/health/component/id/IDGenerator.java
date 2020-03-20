package com.haici.health.component.id;

import java.util.List;

/**
 * ID生成接口
 * 
 * @author dengzhineng
 * @date: 2016年12月5日 下午3:51:00
 * @version 1.0
 * @since JDK 1.7
 */
public interface IDGenerator {

    /**
     * 
     * 适用于自增id
     * 
     * @author dengzhineng
     * @date: 2016年12月5日 下午7:57:26
     * @version 1.0
     * @param key
     * @return
     */
    public ID next(String key);

    /**
     * 按照指定格式生成自增编号
     * @author dengzhineng 
     * @date: 2017年5月8日 下午8:03:43
     * @version 1.0
     *
     * @param key
     * @param format 如：%1$05d
     * @return
     */
    public ID next(String key, String format);
    
    /**
     * 如果现有的逻辑不满足需求，可以实现IDHandler类
     * @author dengzhineng 
     * @date: 2017年5月8日 下午8:15:55
     * @version 1.0
     *
     * @param idHandler
     * @return
     */
    public ID next(String key, IDFormat iDFormat);
    
    /**
     * 根据key生成序列，并且这个序列有超时时间
     * @author dengzhineng 
     * @date: 2017年5月9日 上午11:02:08
     * @version 1.0
     *
     * @param key
     * @param timeout
     * @param iDFormat
     * @return
     */
    public ID next(String key, int timeout, IDFormat iDFormat);

    String nextSeq(String prefix);
}
