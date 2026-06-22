<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <h2 class="page-title">我的订单</h2>

    <c:if test="${empty orders}">
        <div class="alert alert-info">暂无订单</div>
        <a href="${pageContext.request.contextPath}/ticket/list" class="btn btn-primary">去购票</a>
    </c:if>

    <c:if test="${not empty orders}">
        <c:forEach var="order" items="${orders}">
            <div class="order-card">
                <div class="order-header">
                    <span class="order-no">订单号：${order.orderNo}</span>
                    <span class="status status-${order.status}">${order.statusDesc}</span>
                </div>
                <div class="order-items">
                    <c:forEach var="item" items="${order.items}" varStatus="vs">
                        ${item.ticketName} × ${item.quantity}<c:if test="${!vs.last}">、</c:if>
                    </c:forEach>
                </div>
                <div class="order-footer">
                    <span style="font-size:13px;color:#999;">下单时间：${order.createTime}</span>
                    <div>
                        <span class="total">¥${order.totalPrice}</span>
                        <c:if test="${order.status == 'pending'}">
                            <a href="${pageContext.request.contextPath}/order/pay?id=${order.id}" class="btn btn-sm btn-success" style="margin-left:10px;">立即支付</a>
                            <a href="${pageContext.request.contextPath}/order/cancel?id=${order.id}" class="btn btn-sm btn-danger" onclick="return confirm('确定取消订单？')">取消订单</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>
<jsp:include page="/views/common/footer.jsp"/>
