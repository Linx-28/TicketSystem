<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- 管理后台侧边栏 --%>
<div class="admin-sidebar">
    <div class="sidebar-title">管理菜单</div>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="${param.nav == 'dashboard' ? 'active' : ''}">仪表盘</a>
    <a href="${pageContext.request.contextPath}/admin/ticketManage" class="${param.nav == 'ticket' ? 'active' : ''}">票品管理</a>
    <a href="${pageContext.request.contextPath}/admin/orderManage" class="${param.nav == 'order' ? 'active' : ''}">订单管理</a>
    <div style="margin-top:30px;">
        <a href="${pageContext.request.contextPath}/home/index">返回前台</a>
    </div>
</div>
