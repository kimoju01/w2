package com.study.w2.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 해당 경로의 요청에 대해 doFilter() 실행한다.
@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // doFilter(): 추상메서드! 필터링이 필요한 로직 구현 필요

        log.info("login check filter..........");

        // Filter 인터페이스의 doFilter()는 HttpServletRequest/Response보다 더 상위 타입의 파라미터를 사용함
        // HttpSevletRequest는 ServletRequest를 상속받는다.
        // => HTTP 관련 작업 위해 다운 캐스팅 필요
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        HttpSession session = req.getSession();

        // 만약 세션에 loginInfo 이름의 값이 존재하지 않으면 /login으로 이동
        // 사용자가 /login에서 로그인 정보를 전달해서 로그인이 처리되면 이후로는 /t o d o/.. 경로를 이용할 수 있다.
        if(session.getAttribute("loginInfo") == null) {
            resp.sendRedirect("/login");
            return;
        }

        // doFilter()의 마지막엔 다음 필터나 목적지(서블릿, JSP)로 갈 수 있도록 FilterChain의 doFilter() 실행
        // 만약 문제가 생겨서 더 이상 진행할 수 없다면 다음 단계로 진행하지 않고 다른 방식으로 리다이렉트 처리 할 수 있다.
        chain.doFilter(request, response);

    }
}
