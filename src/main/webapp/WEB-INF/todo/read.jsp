<%--
  Created by IntelliJ IDEA.
  User: Hyeju
  Date: 2023-09-08
  Time: 오후 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Read</title>
</head>
<body>
    <div>
        <input type="text" name="tno" value="${dto.tno}" readonly>
    </div>
    <div>
        <input type="text" name="title" value="${dto.title}" readonly>
    </div>
    <div>
        <input type="date" name="dueDate" value="${dto.dueDate}">
    </div>
    <div>
        <input type="checkbox" name="finished" ${dto.finished ? "checked" : ""} readonly>
    </div>
    <div>
      <a href="/todo/modify?tno=${dto.tno}">MODIFY/REMOVE</a>
      <a href="/todo/list">LIST</a>
    </div>
</body>
</html>
