<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${ticket.name} - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <c:if test="${empty ticket}">
        <div class="alert alert-error">票品不存在</div>
        <a href="${pageContext.request.contextPath}/ticket/list" class="btn btn-primary">返回列表</a>
    </c:if>

    <c:if test="${not empty ticket}">
        <div class="detail-wrapper">
            <div class="detail-img" style="display:flex;align-items:center;justify-content:center;">
                <c:choose>
                    <c:when test="${not empty ticket.image}">
                        <img src="${pageContext.request.contextPath}${ticket.image}" alt="${ticket.name}" style="width:100%;height:100%;object-fit:contain;border-radius:12px;background:#f5f5f5;">
                    </c:when>
                    <c:when test="${ticket.type == 'concert'}"><span class="tag-concert" style="font-size:36px;">演唱会</span></c:when>
                    <c:when test="${ticket.type == '体育'}"><span class="tag-sport" style="font-size:36px;">体育</span></c:when>
                    <c:when test="${ticket.type == '电影'}"><span class="tag-movie" style="font-size:36px;">电影</span></c:when>
                    <c:when test="${ticket.type == '演出'}"><span class="tag-show" style="font-size:36px;">演出</span></c:when>
                    <c:otherwise><span class="tag-other" style="font-size:36px;">票务</span></c:otherwise>
                </c:choose>
            </div>
            <div class="detail-info">
                <h1>${ticket.name}</h1>
                <span style="display:inline-block;padding:3px 12px;border-radius:12px;background:#e3f2fd;color:#1565c0;font-size:13px;">
                    <c:choose>
                        <c:when test="${ticket.type == 'concert'}">演唱会</c:when>
                        <c:when test="${ticket.type == '体育'}">体育</c:when>
                        <c:when test="${ticket.type == '电影'}">电影</c:when>
                        <c:when test="${ticket.type == '演出'}">演出</c:when>
                        <c:otherwise>${ticket.type}</c:otherwise>
                    </c:choose>
                </span>
                <div class="price">¥${ticket.price}</div>
                <div class="stock">
                    <c:choose>
                        <c:when test="${ticket.stock > 0}">库存：${ticket.stock} 张</c:when>
                        <c:otherwise><span class="tag-other" style="font-size:36px;">票务</span></c:otherwise>
                    </c:choose>
                </div>
                <div class="desc">${ticket.description}</div>
                <div class="actions">
                    <c:if test="${ticket.stock > 0}">
                        <form action="${pageContext.request.contextPath}/ticket/addToCart" method="get" style="display:flex;gap:10px;">
                            <input type="hidden" name="id" value="${ticket.id}">
                            <input type="number" name="quantity" value="1" min="1" max="${ticket.stock}" style="width:70px;padding:8px;border:1px solid #ddd;border-radius:5px;text-align:center;">
                            <button type="submit" class="btn btn-success btn-lg">加入购物车</button>
                        </form>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/ticket/list" class="btn btn-secondary btn-lg">返回列表</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
<jsp:include page="/views/common/footer.jsp"/>
