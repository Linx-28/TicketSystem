<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="auth-wrapper">
    <div class="auth-container">
        <h2>用户登录</h2>
        <c:if test="${not empty param.msg}">
            <div class="alert alert-success">${param.msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/user/login" method="post" autocomplete="off">
            <div class="input-group">
                <label for="loginUsername">用户名</label>
                <input type="text" id="loginUsername" name="username" class="form-control" placeholder="请输入用户名" autocomplete="username" required>
            </div>
            <div class="input-group">
                <label for="loginPassword">密码</label>
                <input type="password" id="loginPassword" name="password" class="form-control" placeholder="请输入密码" autocomplete="new-password" required>
            </div>
            <button type="submit" class="btn btn-primary">登 录</button>
        </form>
        <div class="auth-footer">
            还没有账号？<a href="${pageContext.request.contextPath}/views/user/register.jsp">立即注册</a>
        </div>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
