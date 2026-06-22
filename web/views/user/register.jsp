<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="auth-wrapper">
    <div class="auth-container">
        <h2>用户注册</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/user/register" method="post" autocomplete="off">
            <div class="input-group">
                <label for="regUsername">用户名</label>
                <input type="text" id="regUsername" name="username" class="form-control" placeholder="请输入用户名" autocomplete="username" required>
            </div>
            <div class="input-group">
                <label for="regPassword">密码</label>
                <input type="password" id="regPassword" name="password" class="form-control" placeholder="至少3位" autocomplete="new-password" required>
            </div>
            <div class="input-group">
                <label for="regConfirm">确认密码</label>
                <input type="password" id="regConfirm" name="confirmPassword" class="form-control" placeholder="再次输入密码" autocomplete="new-password" required>
            </div>
            <div class="input-group">
                <label for="regEmail">邮箱*</label>
                <input type="email" id="regEmail" name="email" class="form-control" placeholder="选填" autocomplete="email">
            </div>
            <div class="input-group">
                <label for="regPhone">手机号*</label>
                <input type="text" id="regPhone" name="phone" class="form-control" placeholder="选填" autocomplete="tel">
            </div>
            <button type="submit" class="btn btn-primary">注 册</button>
        </form>
        <div class="auth-footer">
            已有账号？<a href="${pageContext.request.contextPath}/views/user/login.jsp">立即登录</a>
        </div>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
