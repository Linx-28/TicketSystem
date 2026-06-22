<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>票品管理 - 票务系统</title>
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
        .btn-primary { background: linear-gradient(135deg, #1a73e8, #1557b0); }
        .btn-success { background: linear-gradient(135deg, #43a047, #2e7d32); }
        .btn-danger { background: linear-gradient(135deg, #e53935, #c62828); }
        .btn-secondary { background: linear-gradient(135deg, #757575, #616161); }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; font-size: 14px; font-weight: 600; margin-bottom: 6px; color: #333; }
        .form-control { width: 100%; padding: 10px 12px; border: 1.5px solid #e0e0e0; border-radius: 8px; font-size: 14px; background: #f8f9fa; box-sizing: border-box; }
        .form-control:focus { outline: none; border-color: #1a73e8; }
        .modal-overlay { display: none; position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.6); z-index: 2000; align-items: center; justify-content: center; }
        .modal-overlay.show { display: flex; }
        .modal { background: #fff; border-radius: 16px; padding: 35px; width: 90%; max-width: 500px; max-height: 80vh; overflow-y: auto; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        .modal h3 { margin-bottom: 24px; font-size: 20px; font-weight: 700; color: #333; }
    </style>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="admin-layout">
    <jsp:include page="/views/common/sidebar.jsp">
        <jsp:param name="nav" value="ticket"/>
    </jsp:include>
    <div class="admin-content">
        <div class="admin-header">
            <h2>票品管理</h2>
            <button class="btn btn-success" onclick="showAddForm()">＋ 新增票品</button>
        </div>

        <div style="margin-bottom:20px;display:flex;justify-content:center;">
            <form action="${pageContext.request.contextPath}/admin/ticketManage" method="get" style="display:flex;align-items:center;max-width:460px;width:100%;">
                <input type="text" name="keyword" class="form-control" placeholder="搜索票品名称..." value="${keyword}" style="flex:1;padding:10px 14px;border:1.5px solid #e0e0e0;border-radius:8px 0 0 8px;font-size:14px;border-right:none;">
                <button type="submit" class="btn btn-primary" style="padding:10px 20px;border-radius:0 8px 8px 0;border:none;white-space:nowrap;">搜索</button>
                <c:if test="${not empty keyword}">
                    <a href="${pageContext.request.contextPath}/admin/ticketManage" class="btn btn-secondary" style="margin-left:10px;padding:10px 20px;">清除</a>
                </c:if>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>序号</th>
                    <th>名称</th>
                    <th>类型</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${tickets}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${t.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${t.type == 'concert'}">演唱会</c:when>
                                <c:when test="${t.type == '体育'}">体育</c:when>
                                <c:when test="${t.type == '电影'}">电影</c:when>
                                <c:when test="${t.type == '演出'}">演出</c:when>
                                <c:otherwise>${t.type}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>¥${t.price}</td>
                        <td>${t.stock}</td>
                        <td>
                            <c:choose>
                                <c:when test="${t.status == 'on_sale'}"><span style="color:#43a047;">在售</span></c:when>
                                <c:when test="${t.status == 'sold_out'}"><span style="color:#e53935;">售罄</span></c:when>
                                <c:otherwise><span style="color:#999;">下架</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/ticketEditPage?id=${t.id}" class="btn btn-sm btn-primary">编辑</a>
                            <a href="${pageContext.request.contextPath}/admin/ticketDelete?id=${t.id}" class="btn btn-sm btn-danger" onclick="return confirm('确定删除「${t.name}」？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty tickets}">
                    <tr><td colspan="7" style="text-align:center;color:#999;">暂无数据</td></tr>
                </c:if>
            </tbody>
        </table>

        <div class="modal-overlay ${(not empty editTicket or not empty error) ? 'show' : ''}" id="ticketFormModal">
            <div class="modal">
                <h3>${not empty editTicket ? '编辑票品' : '新增票品'}</h3>
                <c:if test="${not empty error}">
                    <div style="background:#ffebee;color:#c62828;padding:10px 14px;border-radius:8px;margin-bottom:16px;font-size:14px;">${error}</div>
                </c:if>
                <form action="${pageContext.request.contextPath}/admin/${not empty editTicket ? 'ticketEdit' : 'ticketAdd'}" method="post" enctype="multipart/form-data">
                    <c:if test="${not empty editTicket}">
                        <input type="hidden" name="id" value="${editTicket.id}">
                    </c:if>
                    <div class="form-group">
                        <label>票品名称</label>
                        <input type="text" name="name" class="form-control" value="${not empty editTicket ? editTicket.name : newTicket.name}" required>
                    </div>
                    <div class="form-group">
                        <label>类型</label>
                        <select name="type" class="form-control" required>
                            <c:set var="currentType" value="${not empty editTicket ? editTicket.type : newTicket.type}"/>
                            <option value="concert" ${currentType == 'concert' ? 'selected' : ''}>演唱会</option>
                            <option value="体育" ${currentType == '体育' ? 'selected' : ''}>体育</option>
                            <option value="电影" ${currentType == '电影' ? 'selected' : ''}>电影</option>
                            <option value="演出" ${currentType == '演出' ? 'selected' : ''}>演出</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>描述</label>
                        <textarea name="description" class="form-control">${not empty editTicket ? editTicket.description : newTicket.description}</textarea>
                    </div>
                    <div class="form-group">
                        <label>价格</label>
                        <input type="number" name="price" step="0.01" class="form-control" value="${not empty editTicket ? editTicket.price : newTicket.price}" required>
                    </div>
                    <div class="form-group">
                        <label>库存</label>
                        <input type="number" name="stock" class="form-control" value="${not empty editTicket ? editTicket.stock : newTicket.stock}" required>
                    </div>
                    <div class="form-group">
                        <label>图片</label>
                        <c:if test="${not empty editTicket.image}">
                            <div style="margin-bottom:8px;">
                                <img src="${pageContext.request.contextPath}${editTicket.image}" style="max-width:200px;max-height:120px;border-radius:8px;border:1px solid #eee;">
                            </div>
                        </c:if>
                        <input type="file" name="imageFile" accept="image/*" class="form-control" style="padding:10px;">
                        <input type="hidden" name="image" value="${not empty editTicket ? editTicket.image : newTicket.image}">
                    </div>
                    <c:if test="${not empty editTicket}">
                    <div class="form-group">
                        <label>状态</label>
                        <select name="status" class="form-control">
                            <option value="on_sale" ${editTicket.status == 'on_sale' ? 'selected' : ''}>在售</option>
                            <option value="sold_out" ${editTicket.status == 'sold_out' ? 'selected' : ''}>售罄</option>
                            <option value="off" ${editTicket.status == 'off' ? 'selected' : ''}>下架</option>
                        </select>
                    </div>
                    </c:if>
                    <div style="display:flex;gap:10px;margin-top:20px;">
                        <button type="submit" class="btn btn-primary">保存</button>
                        <button type="button" class="btn btn-secondary" onclick="hideForm()">取消</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
<script>
function showAddForm() {
    var modal = document.getElementById('ticketFormModal');
    modal.querySelector('h3').textContent = '新增票品';
    modal.querySelector('form').action = '${pageContext.request.contextPath}/admin/ticketAdd';
    var idInput = modal.querySelector('input[name="id"]');
    if (idInput) idInput.remove();
    modal.querySelector('input[name="name"]').value = '';
    modal.querySelector('select[name="type"]').value = 'concert';
    modal.querySelector('textarea[name="description"]').value = '';
    modal.querySelector('input[name="price"]').value = '';
    modal.querySelector('input[name="stock"]').value = '';
    var fileInput = modal.querySelector('input[name="imageFile"]');
    if (fileInput) fileInput.value = '';
    var imgPreview = modal.querySelector('.img-preview');
    if (imgPreview) imgPreview.remove();
    var statusGroup = modal.querySelector('select[name="status"]');
    if (statusGroup) statusGroup.closest('.form-group').remove();
    var errorDiv = modal.querySelector('div[style*="background:#ffebee"]');
    if (errorDiv) errorDiv.style.display = 'none';
    modal.classList.add('show');
}
function hideForm() {
    document.getElementById('ticketFormModal').classList.remove('show');
}
<c:if test="${not empty editTicket}">
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('ticketFormModal').classList.add('show');
    });
</c:if>
</script>
