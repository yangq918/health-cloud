package com.haici.health.component.autoconfigure.id;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.fastjson.JSON;
import com.haici.health.component.autoconfigure.cache.RedisCacheClientConfiguration;
import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.id.snowflake.SnowflakeIDGenerator;
import com.haici.health.component.id.snowflake.support.SnowflakeNacosHolder;
import com.haici.health.component.id.snowflake.support.SnowflakeWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 20:47
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(IDGenerator.class)
@Conditional(IDCondition.class)
public class SnowflakeIDGenConfiguration {

     @Value("${spring.cloud.nacos.discovery.server-addr:}")
     private String serverAddr;

     @Value("${server.port:8080}")
     private int port;


     @Bean
     public SnowflakeNacosHolder snowflakeNacosHolder(IDProperties idProperties, NacosRegistration registration)
     {
          String nacosAddr = StringUtils.isEmpty(idProperties.getSnowflake().getNacosAddress())?serverAddr:idProperties.getSnowflake().getNacosAddress();
          System.out.println("port:"+port);
          SnowflakeNacosHolder holder = new SnowflakeNacosHolder(nacosAddr,registration.getHost(),port);
          if(StringUtils.isNotEmpty(idProperties.getSnowflake().getIdName()))
          {
               holder.setIdName(idProperties.getSnowflake().getIdName());
          }
          if(StringUtils.isNotEmpty(idProperties.getSnowflake().getIdGroup()))
          {
               holder.setIdGroup(idProperties.getSnowflake().getIdGroup());
          }
          return holder;
     }

     @Bean
     public SnowflakeWorker snowflakeWorker(SnowflakeNacosHolder snowflakeNacosHolder)
     {
          SnowflakeWorker snowflakeWorker = new SnowflakeWorker(snowflakeNacosHolder);
          return snowflakeWorker;
     }


     @Bean
     @ConditionalOnMissingBean(name = "idGenerator")
     public SnowflakeIDGenerator idGenerator(SnowflakeWorker snowflakeWorker)
     {
          return  SnowflakeIDGenerator.builder(snowflakeWorker).build();
     }



}
