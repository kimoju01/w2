<%--
  Created by IntelliJ IDEA.
  User: Hyeju
  Date: 2023-09-12
  Time: 오후 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Todo Login</title>
</head>
<body>
<!-- EL에서 제공하는 param 객체를 이용해 result라는 이름으로 전달한 값 확인 가능 -->
<!-- JSTL 이용해서 에러가 발생하는 경우에 다른 메시지 보여주도록 처리 -->
    <c:if test="${param.result == 'error'}">
        <h1>로그인 에러</h1>
    </c:if>

    <form action="/login" method="post">
        <input type="text" name="mid">
        <input type="text" name="mpw">
        <button type="submit">LOGIN</button>
    </form>
</body>
</html>
