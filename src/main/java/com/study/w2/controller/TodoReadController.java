package com.study.w2.controller;

import com.study.w2.dto.TodoDTO;
import com.study.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("todo read..........");

        try {
            Long tno = Long.valueOf(req.getParameter("tno"));
            TodoDTO todoDTO = todoService.get(tno);

            // 데이터 담기
            req.setAttribute("dto", todoDTO);

            // 현재 요청에 있는 모든 쿠키 중 'viewTodos' 이름을 가진 쿠키 찾기 -> 이전 조회 목록 기록하기 위해 사용
            Cookie viewTodoCookie = findCookie(req.getCookies(), "viewTodos");
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;

            // 이전 조회 목록에 현재 조회한 글의 번호가 이미 포함되어 있는지 확인
            if(todoListStr != null && todoListStr.indexOf(tno + "-") >= 0) {
                // 이미 조회한 글이면 exist를 true로 설정
                exist = true;
            }

            log.info("exist: " + exist);

            // 이전 조회 목록에 현재 조회한 글의 번호가 없다면 수행
            if(!exist) {
                // 이전 조회 목록에 현재 조회한 글 번호 추가
                todoListStr += tno + "-";
                // 쿠키의 값, 경로, 유효 기간 업데이트
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setPath("/");
                viewTodoCookie.setMaxAge(60*60*24);
                // 쿠키를 응답에 추가해 클라이언트에게 보냄
                resp.addCookie(viewTodoCookie);
            }

            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("list error");
        }

    }

    // 매개변수 cookieName과 일치하는 쿠키를 찾아내는 메서드
    private Cookie findCookie(Cookie[] cookies, String cookieName) {

        Cookie targetCookie = null;

        if(cookies != null && cookies.length > 0) {
            for(Cookie ck : cookies) {
                // 각 쿠키(ck)의 이름(ck.getName())을 현재 처리 중인 cookieName과 비교한다
                if(ck.getName().equals(cookieName)) {
                    // 이름이 일치하는 쿠키를 찾으면 targetCookie에 해당 쿠키 저장 후 루프 종료
                    targetCookie = ck;
                    break;
                }
            }
        }

        // 일치하는 쿠키가 없어서 targetCookie가 여전히 null인 경우
        if(targetCookie == null) {
            // 새로운 빈 쿠키 이름은 cookieName, 경로는 /, 유효기간은 1일(60*60*24초 = 86400초)로 생성한다.
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }

        // 찾은 쿠키 또는 새로 생성한 쿠키 반환
        return targetCookie;

    }
}
