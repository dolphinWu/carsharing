package com.ziroom.aspect;

import com.ziroom.service.log.LogService;
import com.ziroom.utils.IPKit;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * 请求的日志处理
 */
@Aspect
@Component
public class WebLogAspect {

    @Autowired
    private LogService logService;

    private static Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.ziroom.controller..*.*(..)) || execution(public * com.ziroom.api..*.*(..))")
    public void webLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ipAddr = IPKit.getIpAddrByRequest(request);
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        String uid = request.getParameter("uid");
        // 记录下请求内容
        LOGGER.info("URL : " + url);
        LOGGER.info("HTTP_METHOD : " + method);
        LOGGER.info("IP : " + ipAddr);
        LOGGER.info("CLASS_METHOD : " + classMethod);
        LOGGER.info("ARGS : " + args);
        LOGGER.info("uid : " + uid);
        try {
            //记录日志
            //logService.addLog(classMethod, args, ipAddr, NumberUtils.toInt(uid, -1));
        } catch (Exception e) {
            LOGGER.error("日志记录失败", e);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + ret);
        LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        startTime.remove();//用完之后记得清除，不然可能导致内存泄露;
    }

}
