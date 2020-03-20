package com.haici.health.compoent.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.haici.health.component.utils.common.GlobalContext;
import com.haici.health.component.utils.common.HcContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 16:53
 * @Description:
 */
@Slf4j
public class WebLoggerMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        HcContext context = GlobalContext.getHcContext();
        boolean needLog = log.isDebugEnabled() || log.isInfoEnabled();
        if (!needLog) {
            return invocation.proceed();
        }
        LogInfo logInfo = new LogInfo();
        long start = System.currentTimeMillis();


        logInfo.setHcContext(context);
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            logInfo.setUrl(String.valueOf(request.getRequestURL()));
            logInfo.setHttpMethod(request.getMethod());
            Method method = invocation.getMethod();
            logInfo.setClassMethod(method.getDeclaringClass().getName() + "." + method.getName());
            String cotentType = request.getContentType();
            logInfo.setCotentType(cotentType);
            boolean isFile = false;
            if (null != cotentType) {
                isFile = cotentType.toLowerCase().contains("multipart");
            }

            if (!isFile) {
                Object[] objs = invocation.getArguments();
                if (null != objs) {
                    List<Object> argList = new ArrayList<>();
                    Arrays.stream(objs).forEach(o -> {
                        if (o instanceof ServletRequest) {
                            argList.add("ServletRequest");
                        } else if (o instanceof ServletResponse) {
                            argList.add("ServletResponse");
                        } else if (o instanceof HttpSession) {
                            argList.add("HttpSession");
                        } else {
                            argList.add(o);
                        }
                    });
                    logInfo.setArgs(argList);
                }

                logInfo.setInputParams(request.getParameterMap());

            }


        } catch (Exception e) {
            log.error("parse req has error", e);
        }

        try {
            Object result = invocation.proceed();
            logInfo.setResult(result);
            logInfo.setExecTime(System.currentTimeMillis() - start);
            return result;
        } catch (Exception e) {
            logInfo.setError(e);
            throw e;
        } finally {
            doLog(logInfo);
        }


    }

    private void doLog(LogInfo logInfo) {
        try {
            if (null != logInfo.getError()) {
                log.error(JSONObject.toJSONString(logInfo), logInfo.getError());
            } else {
                log.info(JSONObject.toJSONString(logInfo));
            }
        } catch (Exception e) {
            //Do nothing
        }
    }


    @Data
    public static class LogInfo {
        private HcContext hcContext;

        private String url;

        private String httpMethod;

        private String classMethod;

        private String cotentType;

        private List<Object> args;

        private Map<String, String[]> inputParams;

        private Long execTime;

        private Object result;

        private Exception error;


    }
}
