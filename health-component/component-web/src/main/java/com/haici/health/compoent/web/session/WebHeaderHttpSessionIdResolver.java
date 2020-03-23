package com.haici.health.compoent.web.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/22 13:07
 * @Description:
 */
@Slf4j
public class WebHeaderHttpSessionIdResolver implements HttpSessionIdResolver {

    private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";

    private String name = HEADER_X_AUTH_TOKEN;

    private String createBy;

    public WebHeaderHttpSessionIdResolver() {
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        this.name = name;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(this.getHeaderName(request));
        return (headerValue != null) ? Collections.singletonList(headerValue) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.getHeaderName(request), sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.getHeaderName(request), "");
    }


    public String getHeaderName(HttpServletRequest request) {
        if (createBy != null) {
            StringBuilder headerName = new StringBuilder(100);
            headerName.append(this.name);
            List<String> createByList = Arrays.asList(createBy.split(","));
            for (String paramName : createByList) {
                String tmp = request.getParameter(paramName);
                if (StringUtils.isBlank(tmp)) {
                    throw new IllegalStateException("can't find session param:" + paramName);
                } else {
                    headerName.append("_").append(tmp);
                }
            }
            log.debug("header name:" + headerName.toString());
            return headerName.toString();
        }
        log.debug("header name:" + name);
        return this.name;
    }
}
