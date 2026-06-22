<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="/views/common/styles.jsp" %>

<%-- 顶部导航栏 --%>
<nav class="navbar">
    <div class="navbar-inner">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/home/index" style="color:#fff;">
                <span style="font-weight:bold;">票务</span>系统
            </a>
        </div>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/home/index">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/home/index?type=concert">演唱会</a></li>
            <li><a href="${pageContext.request.contextPath}/home/index?type=体育">体育</a></li>
            <li><a href="${pageContext.request.contextPath}/home/index?type=电影">电影</a></li>
            <li><a href="${pageContext.request.contextPath}/home/index?type=演出">演出</a></li>
            <li><a href="${pageContext.request.contextPath}/order/cart">购物车</a></li>
        </ul>
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty sessionScope.userId}">
                    <span>欢迎，${sessionScope.username}</span>
                    <a href="${pageContext.request.contextPath}/user/profile">个人中心</a>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard" style="color:#ff8a65;">管理后台</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/user/logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/views/user/login.jsp">登录</a>
                    <a href="${pageContext.request.contextPath}/views/user/register.jsp">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<c:if test="${not empty sessionScope.cartSuccess}">
    <div style="max-width:1200px;margin:0 auto;padding:10px 20px;">
        <div class="alert alert-success" style="text-align:center;margin:10px auto;max-width:600px;">${sessionScope.cartSuccess}</div>
    </div>
    <c:remove var="cartSuccess" scope="session"/>
</c:if>
