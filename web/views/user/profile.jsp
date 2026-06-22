<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人中心 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .profile-section { background:#fff; border-radius:10px; padding:30px; box-shadow:0 2px 10px rgba(0,0,0,0.08); max-width:800px; margin:0 auto 20px; }
        .section-title { font-size:18px; font-weight:600; color:#333; margin-bottom:20px; padding-bottom:10px; border-bottom:2px solid #1a73e8; }
        .order-card { border:1px solid #e0e0e0; border-radius:8px; margin-bottom:15px; overflow:hidden; }
        .order-header { display:flex; justify-content:space-between; align-items:center; padding:12px 16px; background:#f8f9fa; border-bottom:1px solid #e0e0e0; }
        .order-no { font-weight:600; color:#333; }
        .order-items { padding:12px 16px; }
        .order-item { display:flex; justify-content:space-between; align-items:center; padding:8px 0; border-bottom:1px dashed #eee; }
        .order-item:last-child { border-bottom:none; }
        .item-name { color:#555; }
        .item-price { color:#e53935; font-weight:500; }
        .order-footer { display:flex; justify-content:space-between; align-items:center; padding:12px 16px; background:#fafafa; border-top:1px solid #e0e0e0; }
        .total { font-size:18px; font-weight:700; color:#e53935; }
        .status { padding:4px 12px; border-radius:15px; font-size:12px; font-weight:600; }
        .status-pending { background:#fff3e0; color:#e65100; }
        .status-paid { background:#e8f5e9; color:#2e7d32; }
        .status-cancelled { background:#ffebee; color:#c62828; }
    </style>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <h2 class="page-title">个人中心</h2>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <div class="profile-section">
        <div class="section-title">基本信息</div>
        <form action="${pageContext.request.contextPath}/user/updateProfile" method="post">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" class="form-control" value="${user.username}" disabled>
            </div>
            <div class="form-group">
                <label>角色</label>
                <input type="text" class="form-control" value="${user.role == 'admin' ? '管理员' : '普通用户'}" disabled>
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email" class="form-control" value="${user.email}">
            </div>
            <div class="form-group">
                <label>手机号</label>
                <input type="text" name="phone" class="form-control" value="${user.phone}">
            </div>
            <button type="submit" class="btn btn-primary">保存修改</button>
        </form>
    </div>

    <div class="profile-section">
        <div class="section-title">我的订单</div>
        <c:if test="${empty orders}">
            <div style="text-align:center;color:#999;padding:20px;">暂无订单记录</div>
        </c:if>
        <c:if test="${not empty orders}">
            <c:forEach var="order" items="${orders}">
                <div class="order-card">
                    <div class="order-header">
                        <span class="order-no">订单号：${order.orderNo}</span>
                        <span class="status status-${order.status}">${order.statusDesc}</span>
                    </div>
                    <div class="order-items">
                        <c:forEach var="item" items="${order.items}">
                            <div class="order-item">
                                <span class="item-name">${item.ticketName} × ${item.quantity}</span>
                                <span class="item-price">¥${item.subtotal}</span>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="order-footer">
                        <span style="font-size:13px;color:#999;">下单时间：${order.createTime}</span>
                        <div style="display:flex;align-items:center;gap:10px;">
                            <span class="total">¥${order.totalPrice}</span>
                            <c:if test="${order.status == 'pending'}">
                                <a href="${pageContext.request.contextPath}/order/pay?id=${order.id}" class="btn btn-sm btn-success">立即支付</a>
                                <a href="${pageContext.request.contextPath}/order/cancel?id=${order.id}" class="btn btn-sm btn-danger" onclick="return confirm('确定取消订单？')">取消订单</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
