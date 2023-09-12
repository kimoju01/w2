package com.study.w2.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 모든 경로에 적용됨
@WebFilter(urlPatterns = {"/*"})
@Log4j2
public class UTF8Filter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

       log.info("UTF8 filter..........");

        HttpServletRequest req = (HttpServletRequest)request;
        req.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);

    }
}
