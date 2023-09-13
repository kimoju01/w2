package com.study.w2.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
@Log4j2
public class LoginListener implements HttpSessionAttributeListener {
    // HttpSession 관련 작업을 감시하는 리스너
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        // HttpSession에 setAttribute() 작업 감시

        String name = event.getName();
        Object obj = event.getValue();

        if(name.equals("loginInfo")) {
            log.info("A user logined..........");
            log.info(obj);
        }

    }
}
