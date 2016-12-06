package com.subway;/**
 * Created by Administrator on 2016/12/6.
 */

import com.subway.service.app.sysLog.SysLogService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * aop日志处理
 *
 * @author
 * @create 2016-12-06 9:10
 **/

@Aspect
@Component
public class WebLogAspect {

    @Autowired
    SysLogService sysLogService;
    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * com.subway.controller.*.*.*(..)) or execution(public * com.subway.controller.*.*.*())")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("来自 : " + request.getRemoteAddr() + "的请求 : " + request.getRequestURL().toString());
    }

    @AfterReturning(returning = "returnObject", pointcut = "webLog()")
    public void doAfterReturning(Object returnObject) throws Throwable {
        // 处理完请求，返回内容
        logger.info(returnObject != null ? "响应成功" : "响应失败");
        //如果响应失败  写入数据库
    }

}
