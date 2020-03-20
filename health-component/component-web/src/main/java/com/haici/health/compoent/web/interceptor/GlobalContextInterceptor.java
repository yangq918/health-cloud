package com.haici.health.compoent.web.interceptor;

import com.haici.health.component.utils.common.GlobalContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 21:49
 * @Description:
 */
public class GlobalContextInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        GlobalContext.unset();
    }
}
