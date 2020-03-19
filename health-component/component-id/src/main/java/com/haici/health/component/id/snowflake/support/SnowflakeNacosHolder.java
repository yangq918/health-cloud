package com.haici.health.component.id.snowflake.support;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.PreservedMetadataKeys;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/16 16:50
 * @Description:
 */
@Slf4j
public class SnowflakeNacosHolder {

    private static final String METADATA_KEY_LAST_UPDATE_TIME = "compontent.id.snowflake.matadata.lastUpdateTime";

    private static final String PREFIX_NACOS_SERVICE = "snowflake_id_";


    private String nacosAddress;

    private String ip;

    private int port;


    private String idName = "default";

    private String idGroup = "SNOWFLAKE_NODE_GROUP";


    private long lastUpdateTime;

    private int workerId;

    private NamingService naming;


    public SnowflakeNacosHolder(String nacosAddress, String ip, int port) {
        System.out.println(ip);
        System.out.println(port);
        this.nacosAddress = nacosAddress;
        this.ip = ip;
        this.port = port;
    }


    public boolean init() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosAddress);
            naming = NamingFactory.createNamingService(properties);
            List<Instance> instances = naming.getAllInstances(PREFIX_NACOS_SERVICE + idName, idGroup);

            boolean exsit = false;
            if (null != instances) {
                Instance exsitInstance = getInstance(instances);
                if (null != exsitInstance) {
                    exsit = true;
                }
            }
            if (!exsit) {
                register(naming);
            }
            initWorkerId(naming);

            scheduledUploadData();

            return true;
        } catch (NacosException e) {
            log.error("init has error", e);
        }
        return false;
    }

    private void scheduledUploadData() {
        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "snowflake-schedule-upload");
                thread.setDaemon(true);
                return thread;
            }
        }).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                updateNewData();
            }
        }, 1L, 30L, TimeUnit.SECONDS); //每30S上报数据
    }

    private void updateNewData() {
        if (System.currentTimeMillis() < lastUpdateTime) {
            return;
        }
        try {
            Instance instance = initInstance();
            naming.registerInstance(PREFIX_NACOS_SERVICE + idName, idGroup, instance);
            lastUpdateTime = Long.parseLong(instance.getMetadata().get(METADATA_KEY_LAST_UPDATE_TIME));
        } catch (NacosException e) {
            log.error("updateNewData has error", e);
        }
    }

    private void initWorkerId(NamingService naming) throws NacosException {
        List<Instance> instances;
        instances = naming.getAllInstances(PREFIX_NACOS_SERVICE + idName, idGroup);
        Instance exsitInstance = getInstance(instances);
        if (null != exsitInstance) {
            String lutStr = exsitInstance.getMetadata().get(METADATA_KEY_LAST_UPDATE_TIME);
            if (null != lutStr) {
                long lut = Long.parseLong(lutStr);
                if (lut > System.currentTimeMillis()) {
                    throw new IllegalStateException("init timestamp check error");
                }
            }
            workerId = Integer.parseInt(exsitInstance.getInstanceId());

            //web.info("init workerid:{}",workerId);
            System.out.println(workerId);
        }
    }

    private void register(NamingService naming) throws NacosException {
        Instance instance = initInstance();
        naming.registerInstance(PREFIX_NACOS_SERVICE + idName, idGroup, instance);
    }

    private Instance initInstance() {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setEphemeral(false);

        Map<String, String> metaData = new HashMap<>();
        metaData.put(PreservedMetadataKeys.INSTANCE_ID_GENERATOR, Constants.SNOWFLAKE_INSTANCE_ID_GENERATOR);
        metaData.put(METADATA_KEY_LAST_UPDATE_TIME, Objects.toString(System.currentTimeMillis()));
        instance.setMetadata(metaData);
        return instance;
    }

    private Instance getInstance(List<Instance> instances) {
        return instances.stream().filter(instance -> {
            return Objects.equals(instance.getIp(), ip) && Objects.equals(instance.getPort(), port);
        }).findFirst().orElse(null);
    }

    public int getWorkId() {
        return workerId;
    }
}
