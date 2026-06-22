<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理后台 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin.css">
    <style>
        .admin-layout { min-height: calc(100vh - 64px); margin: 0; padding: 0; }
        .admin-sidebar { width: 240px; min-width: 240px; background: linear-gradient(180deg, #1a237e, #283593); color: #fff; padding-top: 20px; border-radius: 0; box-shadow: 2px 0 10px rgba(0,0,0,0.1); margin-left: 0; position: fixed; top: 64px; left: 0; height: calc(100vh - 64px); z-index: 100; }
        .admin-content { margin-left: 240px; padding: 28px; background: #f5f5f5; max-width: calc(100% - 240px); }
        .admin-header { display: flex; justify-content: center; align-items: center; margin-bottom: 28px; gap: 20px; }
        .admin-header h2 { font-size: 24px; font-weight: 700; color: #333; }
        .stats-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 28px; }
        .stat-card { background: #fff; border-radius: 12px; padding: 28px; box-shadow: 0 2px 10px rgba(0,0,0,0.06); text-align: center; }
        .stat-card .number { font-size: 36px; font-weight: 700; color: #1a73e8; }
        .stat-card .label { color: #666; font-size: 14px; margin-top: 8px; font-weight: 500; }
        .btn { display: inline-block; padding: 10px 24px; border-radius: 6px; border: none; font-size: 14px; font-weight: 500; cursor: pointer; text-align: center; text-decoration: none; color: #fff; }
        .btn-primary { background: linear-gradient(135deg, #1a73e8, #1557b0); }
        .btn-secondary { background: linear-gradient(135deg, #757575, #616161); }
    </style>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="admin-layout">
    <jsp:include page="/views/common/sidebar.jsp">
        <jsp:param name="nav" value="dashboard"/>
    </jsp:include>
    <div class="admin-content">
        <div class="admin-header">
            <h2>仪表盘</h2>
        </div>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="number">${ticketCount}</div>
                <div class="label">在售票品</div>
            </div>
            <div class="stat-card">
                <div class="number">${orderCount}</div>
                <div class="label">总订单数</div>
            </div>
            <div class="stat-card">
                <div class="number">${todayOrder}</div>
                <div class="label">今日订单</div>
            </div>
            <div class="stat-card">
                <div class="number">¥${totalSales}</div>
                <div class="label">总销售额</div>
            </div>
        </div>
        <div style="background:#fff;border-radius:10px;padding:20px;box-shadow:0 2px 8px rgba(0,0,0,0.06);">
            <h3 style="margin-bottom:10px;">快捷操作</h3>
            <div style="display:flex;gap:12px;justify-content:center;">
                <a href="${pageContext.request.contextPath}/admin/ticketManage" class="btn btn-primary">管理票品</a>
                <a href="${pageContext.request.contextPath}/admin/orderManage" class="btn btn-primary">管理订单</a>
                <a href="${pageContext.request.contextPath}/home/index" class="btn btn-secondary">返回前台</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
