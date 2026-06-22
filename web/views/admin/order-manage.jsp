<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单管理 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin.css">
    <style>
        .admin-layout { min-height: calc(100vh - 64px); margin: 0; padding: 0; }
        .admin-sidebar { width: 240px; min-width: 240px; background: linear-gradient(180deg, #1a237e, #283593); color: #fff; padding-top: 20px; border-radius: 0; box-shadow: 2px 0 10px rgba(0,0,0,0.1); margin-left: 0; position: fixed; top: 64px; left: 0; height: calc(100vh - 64px); z-index: 100; }
        .admin-content { margin-left: 240px; padding: 28px; background: #f5f5f5; max-width: calc(100% - 240px); }
        .admin-header { display: flex; justify-content: center; align-items: center; margin-bottom: 28px; gap: 20px; }
        .admin-header h2 { font-size: 24px; font-weight: 700; color: #333; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.06); }
        table th, table td { padding: 14px 18px; border-bottom: 1px solid #eee; font-size: 14px; text-align: left; }
        table th { background: linear-gradient(135deg, #f5f7fa, #eef1f5); font-weight: 600; color: #333; }
        table tr:hover { background: #f8f9fb; }
        .btn { display: inline-block; padding: 6px 14px; border-radius: 6px; border: none; font-size: 12px; font-weight: 500; cursor: pointer; text-align: center; text-decoration: none; color: #fff; }
        .btn-sm { padding: 6px 14px; font-size: 12px; }
        .btn-success { background: linear-gradient(135deg, #43a047, #2e7d32); }
        .btn-danger { background: linear-gradient(135deg, #e53935, #c62828); }
        .status { padding: 4px 14px; border-radius: 15px; font-size: 12px; font-weight: 600; }
        .status-pending { background: #fff3e0; color: #e65100; }
        .status-paid { background: #e8f5e9; color: #2e7d32; }
        .status-cancelled { background: #ffebee; color: #c62828; }
        .detail-row { display: none; }
        .detail-row.show { display: table-row; }
        .detail-content { padding: 16px 18px; background: #f8f9fa; }
        .detail-item { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #ddd; }
        .detail-item:last-child { border-bottom: none; }
        .toggle-btn { cursor: pointer; color: #1a73e8; font-size: 12px; background: none; border: none; padding: 4px 8px; }
        .toggle-btn:hover { text-decoration: underline; }
    </style>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="admin-layout">
    <jsp:include page="/views/common/sidebar.jsp">
        <jsp:param name="nav" value="order"/>
    </jsp:include>
    <div class="admin-content">
        <div class="admin-header">
            <h2>订单管理</h2>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>订单号</th>
                    <th>用户</th>
                    <th>金额</th>
                    <th>状态</th>
                    <th>下单时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orders}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${o.orderNo}</td>
                        <td>${o.username}</td>
                        <td>¥${o.totalPrice}</td>
                        <td>
                            <span class="status status-${o.status}">${o.statusDesc}</span>
                        </td>
                        <td>${o.createTime}</td>
                        <td>
                            <button class="toggle-btn" onclick="toggleDetail('detail-${o.id}')">查看详情</button>
                            <c:if test="${o.status == 'pending'}">
                                <a href="${pageContext.request.contextPath}/admin/orderProcess?id=${o.id}&status=paid" class="btn btn-sm btn-success" onclick="return confirm('确认标记为已支付？')">标记已支付</a>
                                <a href="${pageContext.request.contextPath}/admin/orderProcess?id=${o.id}&status=cancelled" class="btn btn-sm btn-danger" onclick="return confirm('确认取消订单？')">取消</a>
                            </c:if>
                            <c:if test="${o.status == 'paid'}">
                                <span style="color:#888;font-size:13px;">已完成</span>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="detail-row" id="detail-${o.id}">
                        <td colspan="7">
                            <div class="detail-content">
                                <strong>订单详情：</strong>
                                <c:forEach var="item" items="${o.items}">
                                    <div class="detail-item">
                                        <span>${item.ticketName} × ${item.quantity}</span>
                                        <span style="color:#e53935;font-weight:500;">¥${item.subtotal}</span>
                                    </div>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orders}">
                    <tr><td colspan="7" style="text-align:center;color:#999;">暂无订单</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
<script>
function toggleDetail(id) {
    var row = document.getElementById(id);
    row.classList.toggle('show');
}
</script>
