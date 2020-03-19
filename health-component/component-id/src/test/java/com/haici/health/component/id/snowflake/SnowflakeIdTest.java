package com.haici.health.component.id.snowflake;

import com.haici.health.component.id.snowflake.support.SnowflakeWorker;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 14:35
 * @Description:
 */

public class SnowflakeIdTest {

    public static void main(String[] args) {
        SnowflakeWorker worker  = new SnowflakeWorker("127.0.0.1:8848","158.0.0.1",0);
       // ID id = worker.get();
        //System.out.println(id.id);
        long begin = System.currentTimeMillis();
        for(int i=0;i<1000000;i++)
        {
           System.out.println(worker.get().id);
        }

        long end = System.currentTimeMillis();
        System.out.println("--"+(end-begin));
    }
}
