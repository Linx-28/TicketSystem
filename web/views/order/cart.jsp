<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <h2 class="page-title">我的购物车</h2>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">购物车是空的，快去选购吧！</div>
        <a href="${pageContext.request.contextPath}/ticket/list" class="btn btn-primary">去选购</a>
    </c:if>

    <c:if test="${not empty cartItems}">
        <c:forEach var="item" items="${cartItems}">
            <div class="cart-item">
                <div style="width:100px;height:100px;background:#e3f2fd;border-radius:8px;overflow:hidden;flex-shrink:0;border:1px solid #e0e0e0;">
                    <c:choose>
                        <c:when test="${not empty item.ticket.image}">
                            <img src="${pageContext.request.contextPath}${item.ticket.image}" alt="${item.ticket.name}" style="width:100%;height:100%;object-fit:contain;background:#f5f5f5;">
                        </c:when>
                        <c:otherwise>
                            <div style="width:100%;height:100%;display:flex;align-items:center;justify-content:center;font-size:36px;">🎫</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="item-info">
                    <h4>${item.ticket.name}</h4>
                    <div class="price">¥${item.ticket.price}</div>
                </div>
                <form action="${pageContext.request.contextPath}/order/updateCart" method="get" style="display:flex;align-items:center;gap:8px;">
                    <input type="hidden" name="ticketId" value="${item.ticket.id}">
                    <input type="number" name="quantity" value="${item.quantity}" min="0" max="${item.ticket.stock}" style="width:60px;padding:5px;border:1px solid #ddd;border-radius:4px;text-align:center;">
                    <button type="submit" class="btn btn-sm btn-secondary">更新</button>
                </form>
                <div style="font-weight:bold;color:#e53935;font-size:16px;min-width:80px;text-align:right;">
                    ¥${item.subtotal}
                </div>
                <a href="${pageContext.request.contextPath}/order/removeFromCart?ticketId=${item.ticket.id}" class="btn btn-sm btn-danger" onclick="return confirm('确定移除此商品？')">删除</a>
            </div>
        </c:forEach>

        <div class="cart-summary">
            <h3>合计：¥${cartTotal}</h3>
            <a href="${pageContext.request.contextPath}/order/checkout" class="btn btn-success btn-lg" style="margin-top:10px;">去结算</a>
        </div>
    </c:if>
</div>
<jsp:include page="/views/common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/common.js"></script>
