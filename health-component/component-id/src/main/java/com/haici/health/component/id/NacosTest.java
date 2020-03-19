package com.haici.health.component.id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.PreservedMetadataKeys;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/16 15:17
 * @Description:
 */
public class NacosTest {

    public static void main(String[] args) throws NacosException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", "127.0.0.1:8848");

        NamingService naming = NamingFactory.createNamingService(properties);

        Instance instance = new Instance();
        instance.setIp("11.11.11.11");
        instance.setPort(8888);
        instance.setEphemeral(false);

        instance.setMetadata(new HashMap<String, String>());
        instance.getMetadata().put(PreservedMetadataKeys.INSTANCE_ID_GENERATOR, Constants.SNOWFLAKE_INSTANCE_ID_GENERATOR);


        naming.registerInstance("id-gen-cloud","ID-GROUP",instance);

        Instance instance2 = new Instance();
        instance2.setIp("11.11.11.11");
        instance2.setPort(8889);
        instance2.setEphemeral(false);
        instance2.setMetadata(new HashMap<String, String>());
        instance2.getMetadata().put(PreservedMetadataKeys.INSTANCE_ID_GENERATOR, Constants.SNOWFLAKE_INSTANCE_ID_GENERATOR);
        naming.registerInstance("id-gen-cloud","ID-GROUP", instance2);

        Instance instance3 = new Instance();
        instance3.setIp("11.11.11.11");
        instance3.setPort(8899);
        instance3.setEphemeral(false);
        instance3.setMetadata(new HashMap<String, String>());
        instance3.getMetadata().put(PreservedMetadataKeys.INSTANCE_ID_GENERATOR, Constants.SNOWFLAKE_INSTANCE_ID_GENERATOR);
        naming.registerInstance("id-gen-cloud", "ID-GROUP-1",instance3);

        System.out.println(JSON.toJSONString(naming.getAllInstances("id-gen-cloud","ID-GROUP-1")));

        //naming.deregisterInstance("id-gen-cloud", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(JSON.toJSONString(naming.getAllInstances("id-gen-cloud","ID-GROUP")));

        naming.subscribe("id-gen-cloud", new EventListener() {
            @Override
            public void onEvent(Event event) {
                System.out.println(((NamingEvent)event).getServiceName());
                System.out.println(((NamingEvent)event).getInstances());
            }
        });


        long current = System.currentTimeMillis();
        System.out.println(new Date());
        System.out.println(current);
        Thread.sleep(5000000);
    }
}
