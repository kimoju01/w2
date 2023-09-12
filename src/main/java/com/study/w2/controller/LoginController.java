package com.study.w2.controller;

import com.study.w2.dao.MemberDAO;
import com.study.w2.dto.MemberDTO;
import com.study.w2.service.MemberService;
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
//        String str = mid + mpw;

        try {
            // 정상적으로 변경된 경우에는 HttpSession을 이용해서 loginInfo 이름으로 객체 저장
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);
            HttpSession session = req.getSession();
            // 세션에 loginInfo 이름의 속성으로 memberDTO 값을 보관. key=loginInfo, value=memberDTO
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");
        } catch (Exception e) {
            // 예외가 발생한 경우에는 /login으로 이동.
            // /login으로 이동할 때 result 파라미터를 전달해서 에러 발생 함께 전달함
            resp.sendRedirect("/login?result=error");
        }

    }
}
