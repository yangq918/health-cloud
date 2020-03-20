package com.haici.health.component.utils.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/20 15:18
 * @Description:
 */
@Data
public class BaseVo implements Serializable {

    private static final long serialVersionUID = -7473695078919699254L;

    /**创建时间**/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    protected Date createTime;

    /***扩展字段 json**/
    protected String extFields;

}
