package com.linkbit.beidou.filters;


import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.service.app.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by HUANGBIN on 2016/3/1 0001.
 */

@Component("sessionFilter")
@WebFilter
@Order(1)
public class SessionFilter implements javax.servlet.Filter {
    @Autowired

    ResourceService resourceService;

    Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器----------------------init");
    }

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        HttpSession httpSession = request.getSession(false);


        //将公共资源加入表中
        if (url.equals("/checkLogin") || url.equals("/") || url.endsWith("index.jsp")) {
            filterChain.doFilter(request, response);
        } else if (url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".ico")) {
            filterChain.doFilter(request, response);
        } else {
            if (httpSession.getId() != null) {
                filterChain.doFilter(request, response);
            } else {
                logger.info("非法偷渡--------------------" + url);
                response.sendRedirect("/");
            }
        }
    }

    @Override
    public void destroy() {
        logger.info("过滤器----------------------destroy");
    }
}

