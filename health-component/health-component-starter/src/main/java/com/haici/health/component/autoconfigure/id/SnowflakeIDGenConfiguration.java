package com.haici.health.component.autoconfigure.id;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.fastjson.JSON;
import com.haici.health.component.autoconfigure.cache.RedisCacheClientConfiguration;
import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.id.snowflake.SnowflakeIDGenerator;
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

     @Autowired
     private ServerConfig serverConfig;





     @Bean
     public SnowflakeWorker snowflakeWorker(IDProperties idProperties, NacosRegistration registration)
     {
          System.out.println(JSON.toJSONString(registration));
          String nacosAddr = StringUtils.isEmpty(idProperties.getSnowflake().getNacosAddress())?serverAddr:idProperties.getSnowflake().getNacosAddress();


          SnowflakeWorker snowflakeWorker = new SnowflakeWorker(nacosAddr,registration.getHost(),serverConfig.getServerPort());

          return snowflakeWorker;
     }


     @Bean
     @ConditionalOnMissingBean(name = "idGenerator")
     public SnowflakeIDGenerator idGenerator(SnowflakeWorker snowflakeWorker)
     {
          return  SnowflakeIDGenerator.builder(snowflakeWorker).build();
     }



     @Bean
     public ServerConfig serverConfig()
     {
          return new ServerConfig();
     }

     public static class ServerConfig implements ApplicationListener<WebServerInitializedEvent>
     {

          private int serverPort;
          @Override
          public void onApplicationEvent(WebServerInitializedEvent event) {
               this.serverPort = event.getWebServer().getPort();
               System.out.println("Get WebServer port :"+ serverPort);
          }

          public int getServerPort() {
               return serverPort;
          }
     }



}
