package com.study.w2.controller;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 화면 보여주기

        log.info("login get..........");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 처리하기

        log.info("login post..........");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        // 사용자의 mid와 mpw를 수집해 이를 이용해 문자열 구성 (나중엔 DTO로 변경)
        String str = mid + mpw;

        HttpSession session = req.getSession();
        // 세션에 loginInfo 이름의 속성으로 str 값을 보관. key=loginInfo, value=str
        session.setAttribute("loginInfo", str);

        resp.sendRedirect("/todo/list");

    }
}
