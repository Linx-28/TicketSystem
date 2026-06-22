<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>确认订单 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <h2 class="page-title">确认订单</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">购物车为空</div>
        <a href="${pageContext.request.contextPath}/ticket/list" class="btn btn-primary">去选购</a>
    </c:if>

    <c:if test="${not empty cartItems}">
        <div style="background:#fff;border-radius:10px;padding:20px;box-shadow:0 2px 10px rgba(0,0,0,0.08);margin-bottom:20px;">
            <h3 style="margin-bottom:16px;">订单明细</h3>
            <table>
                <thead>
                    <tr><th>票品</th><th>单价</th><th>数量</th><th>小计</th></tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.ticket.name}</td>
                            <td>¥${item.ticket.price}</td>
                            <td>${item.quantity}</td>
                            <td style="color:#e53935;font-weight:bold;">¥${item.subtotal}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div style="background:#fff;border-radius:10px;padding:20px;box-shadow:0 2px 10px rgba(0,0,0,0.08);text-align:center;">
            <h3 style="margin-bottom:16px;">总计：<span style="color:#e53935;">¥${cartTotal}</span></h3>
            <form action="${pageContext.request.contextPath}/order/submitOrder" method="post">
                <button type="submit" class="btn btn-success btn-lg" onclick="return confirm('确认提交订单？')">提交订单</button>
                <a href="${pageContext.request.contextPath}/order/cart" class="btn btn-secondary btn-lg">返回购物车</a>
            </form>
        </div>
    </c:if>
</div>
<jsp:include page="/views/common/footer.jsp"/>
