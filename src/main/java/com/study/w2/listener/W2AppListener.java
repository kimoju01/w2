package com.study.w2.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Log4j2
public class W2AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 프로젝트 실행 시 실행

        log.info("----------init----------");
        log.info("----------init----------");
        log.info("----------init----------");

        // ServletContext? 현재 웹 애플리케이션 내 모든 자원들을 같이 사용하는 공간
        // setAttribute()를 통해 객체를 저장하면 모든 컨트롤러나 JSP, EL 등에서 사용할 수 있다.
        // 파라미터인 ServletContextEvent를 통해 얻을 수 있다.
        ServletContext servletContext = sce.getServletContext();
        // EL에서 ${appName}으로 활용할 수 있다.
        servletContext.setAttribute("appName", "W2");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 프로젝트 종료 시 실행

        log.info("----------destroy----------");
        log.info("----------destroy----------");
        log.info("----------destroy----------");

    }
}
