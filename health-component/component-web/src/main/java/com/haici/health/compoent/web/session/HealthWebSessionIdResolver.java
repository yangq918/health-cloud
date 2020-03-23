package com.haici.health.compoent.web.session;

import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/21 15:48
 * @Description:
 */
public class HealthWebSessionIdResolver implements HttpSessionIdResolver {

    private WebSessionProperties properties;


    private Map<String, List<HttpSessionIdResolver>> resolverMap = new LinkedHashMap<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();


    public HealthWebSessionIdResolver(WebSessionProperties properties) {
        Assert.notNull(properties, "session properties is must not null");
        this.properties = properties;
        init();
    }

    private void init() {

        List<WebSessionProperties.Session> sessions = properties.getSessions();
        if (null == sessions) {
            return;
        }
        sessions.forEach(session -> {
            String path = session.getPath();
            List<HttpSessionIdResolver> resolvers = initSessionIdResolver(session);
            resolverMap.put(path, resolvers);
        });
    }

    private List<HttpSessionIdResolver> initSessionIdResolver(WebSessionProperties.Session session) {

        List<SessionType> types = session.getTypes();
        if (CollectionUtils.isEmpty(types)) {
            types = new ArrayList<>();
            types.add(SessionType.COOKIE);
        }
        List<HttpSessionIdResolver> resolvers = new ArrayList<>();
        types.forEach(sessionType -> {

            HttpSessionIdResolver resolver = sessionType.initSessionIdResolver(session);
            if (null != resolver) {
                resolvers.add(resolver);
            }

        });

        return resolvers;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String uri = request.getRequestURI();

        String key = resolverMap.keySet().stream().filter(ele -> pathMatcher.match(ele, uri)).findFirst().orElse(null);
        if (null == key) {
            return new ArrayList<>();
        }
        List<HttpSessionIdResolver> resolvers = resolverMap.get(key);
        if (CollectionUtils.isEmpty(resolvers)) {
            return new ArrayList<>();
        }
        return resolvers.get(0).resolveSessionIds(request);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {

        String uri = request.getRequestURI();

        String key = resolverMap.keySet().stream().filter(ele -> pathMatcher.match(ele, uri)).findFirst().orElse(null);
        if (null == key) {
            return;
        }
        List<HttpSessionIdResolver> resolvers = resolverMap.get(key);
        if (!CollectionUtils.isEmpty(resolvers)) {
            resolvers.forEach(httpSessionIdResolver -> {
                httpSessionIdResolver.setSessionId(request, response, sessionId);
            });
        }

    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();

        String key = resolverMap.keySet().stream().filter(ele -> pathMatcher.match(ele, uri)).findFirst().orElse(null);
        if (null == key) {
            return;
        }
        List<HttpSessionIdResolver> resolvers = resolverMap.get(key);
        if (!CollectionUtils.isEmpty(resolvers)) {
            resolvers.forEach(httpSessionIdResolver -> {
                httpSessionIdResolver.expireSession(request, response);
            });
        }
    }
}
