package com.haici.health.component.id.helper;

import com.haici.health.component.id.IDGenerator;
import lombok.Setter;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 21:14
 * @Description:
 */
public class MessageIdHelper {

    @Setter
    private IDGenerator idGenerator;

    @Setter
    private String prefix;

    public String generateMsgId()
    {
        return  idGenerator.nextSeq(prefix);
    }
}
