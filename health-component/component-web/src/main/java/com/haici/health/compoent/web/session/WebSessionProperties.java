package com.haici.health.compoent.web.session;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/22 11:21
 * @Description:
 */
@Data
public class WebSessionProperties {

    private List<Session> sessions = new ArrayList<>();


    @Data
    public static class CommonSession {
        private String name;

        private String createBy;
    }


    @Data
    public static class Session extends CommonSession {
        private String path;
        private List<SessionType> types = new ArrayList<>();

        private Cookie cookie = new Cookie();

        private Header header = new Header();

    }


    @Data
    public static class Cookie extends CommonSession {

        private String domain;

        private String path;

        private String comment;

        private Boolean httpOnly;

        private Boolean secure;

        @DurationUnit(ChronoUnit.SECONDS)
        private Duration maxAge;
    }

    @Data
    public static class Header extends CommonSession {

    }

}
