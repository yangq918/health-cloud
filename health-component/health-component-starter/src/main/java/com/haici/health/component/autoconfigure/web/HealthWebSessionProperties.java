package com.haici.health.component.autoconfigure.web;

import com.haici.health.compoent.web.session.WebSessionProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/22 14:14
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "health.component.web")
public class HealthWebSessionProperties extends WebSessionProperties {
}
