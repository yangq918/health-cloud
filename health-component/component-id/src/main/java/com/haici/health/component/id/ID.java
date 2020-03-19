package com.haici.health.component.id;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author dengzhineng
 * @version 1.0
 * @date: 2016年12月5日 下午3:49:35
 * @since JDK 1.7
 */
public class ID implements Externalizable {
    private static final long serialVersionUID = 1L;
    /**
     * 生成的ID值
     */
    public long id;
    /**
     * 生成该ID的时间（毫秒数）
     */
    public long timeMillis;

    public ID(long id, long timeMillis) {
        super();
        this.id = id;
        this.timeMillis = timeMillis;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    @Override
    public String toString() {
        return "ID [id=" + id + ", timeMillis=" + timeMillis + "]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeLong(id);
        out.writeLong(timeMillis);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.id = in.readLong();
        this.timeMillis = in.readLong();

    }

}
