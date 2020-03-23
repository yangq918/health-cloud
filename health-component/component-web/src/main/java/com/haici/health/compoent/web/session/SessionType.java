package com.haici.health.compoent.web.session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/22 10:09
 * @Description:
 */
public enum SessionType {
    /**
     * cookie
     */
    COOKIE() {
        @Override
        public HttpSessionIdResolver initSessionIdResolver(WebSessionProperties.Session session) {
            WebSessionProperties.Cookie cookie = session.getCookie();
            PropertyMapper map = PropertyMapper.get();
            WebCookieSerializer serializer = new WebCookieSerializer();
            map.from(cookie.getName()).when(StringUtils::isEmpty).to((name) -> cookie.setName(session.getName()));
            map.from(cookie.getCreateBy()).when(StringUtils::isEmpty).to((createBy) -> cookie.setCreateBy(session.getCreateBy()));

            map.from(cookie.getName()).whenHasText().to(serializer::setName);
            map.from(cookie.getCreateBy()).whenHasText().to(serializer::setCreateBy);
            map.from(cookie::getDomain).whenNonNull().to(serializer::setDomainName);
            map.from(cookie::getPath).whenHasText().to(serializer::setCookiePath);
            map.from(cookie::getHttpOnly).whenNonNull().to(serializer::setUseHttpOnlyCookie);
            map.from(cookie::getSecure).whenNonNull().to(serializer::setUseSecureCookie);
            map.from(cookie::getMaxAge).whenNonNull().to((maxAge) -> serializer.setCookieMaxAge((int) maxAge.getSeconds()));
            CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
            resolver.setCookieSerializer(serializer);
            return resolver;
        }
    },
    /**
     * header
     */
    HEADER() {
        @Override
        public HttpSessionIdResolver initSessionIdResolver(WebSessionProperties.Session session) {

            WebSessionProperties.Header header = session.getHeader();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            WebCookieSerializer serializer = new WebCookieSerializer();
            map.from(header.getName()).when(StringUtils::isEmpty).to((name) -> header.setName(session.getName()));
            map.from(header.getCreateBy()).when(StringUtils::isEmpty).to((createBy) -> header.setCreateBy(session.getCreateBy()));

            WebHeaderHttpSessionIdResolver resolver = new WebHeaderHttpSessionIdResolver();

            map.from(header::getName).whenHasText().to(resolver::setName);
            map.from(header::getCreateBy).whenHasText().to(resolver::setCreateBy);

            return resolver;
        }
    };

    public HttpSessionIdResolver initSessionIdResolver(WebSessionProperties.Session session) {
        return null;
    }
}
