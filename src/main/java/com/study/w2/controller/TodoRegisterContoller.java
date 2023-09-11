package com.study.w2.controller;

import com.study.w2.TodoDTO;
import com.study.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoRegisterController", value = "/todo/register")
@Log4j2
public class TodoRegisterContoller extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 입력 화면 보여주기

        log.info("todo register GET..........");

        HttpSession session = req.getSession();

        // 기존에 JSESSIONID가 없는 새로운 사용자
        if(session.isNew()) {
            log.info("JSESSIONID 쿠키가 새로 만들어진 사용자");
            resp.sendRedirect("/login");
            return; // doGet() 메서드 즉시 종료함 (반환값 없는 return)
        }

        // JSESSIONID는 있지만 해당 세션 컨텍스트에 loginInfo라는 이름으로 지정된 객체가 없는 경우
        if(session.getAttribute("loginInfo") == null) {
            log.info("로그인한 정보가 없는 사용자");
            resp.sendRedirect("/login");
            return;
        }

        // 정상적인 경우라면 입력 화면으로
        req.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TodoDTO todoDTO = TodoDTO.builder()
                        .title(req.getParameter("title"))
                        .dueDate(LocalDate.parse(req.getParameter("dueDate"), DATETIMEFORMATTER))
                        .build();

        log.info("/todo/register POST.....");
        log.info(todoDTO);

        try {
            todoService.register(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/todo/list");

    }
}
