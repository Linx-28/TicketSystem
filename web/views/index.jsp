<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>票务系统 - 在线购票</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .type-section { margin-bottom: 30px; }
        .type-title { font-size: 20px; font-weight: 700; color: #333; margin-bottom: 16px; padding-left: 12px; border-left: 4px solid #1a73e8; }
    </style>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>

<div class="container">
    <%-- 搜索框 --%>
    <div style="margin: 20px 0;">
        <form action="${pageContext.request.contextPath}/ticket/search" method="get" class="search-box">
            <input type="text" name="keyword" class="form-control" placeholder="搜索您感兴趣的演出、演唱会..." value="${param.keyword}">
            <button type="submit" class="btn btn-primary">搜索</button>
        </form>
    </div>

    <h2 class="page-title">
        <c:choose>
            <c:when test="${not empty param.keyword}">搜索："${param.keyword}"</c:when>
            <c:when test="${currentType == 'concert'}">演唱会票品</c:when>
            <c:when test="${currentType == '体育'}">体育票品</c:when>
            <c:when test="${currentType == '电影'}">电影票品</c:when>
            <c:when test="${currentType == '演出'}">演出票品</c:when>
            <c:otherwise><span class="tag-other">票务</span></c:otherwise>
        </c:choose>
    </h2>

    <c:if test="${empty ticketList}">
        <div class="alert alert-info">暂无相关票品</div>
    </c:if>

    <c:choose>
        <c:when test="${not empty currentType}">
            <div class="card-grid">
                <c:forEach var="ticket" items="${ticketList}">
                    <div class="card">
                        <div class="card-img" style="display:flex;align-items:center;justify-content:center;">
                            <c:choose>
                                <c:when test="${not empty ticket.image}">
                                    <img src="${pageContext.request.contextPath}${ticket.image}" alt="${ticket.name}" style="width:100%;height:200px;object-fit:contain;background:#f5f5f5;">
                                </c:when>
                                <c:when test="${ticket.type == 'concert'}"><span class="tag-concert">演唱会</span></c:when>
                                <c:when test="${ticket.type == '体育'}"><span class="tag-sport">体育</span></c:when>
                                <c:when test="${ticket.type == '电影'}"><span class="tag-movie">电影</span></c:when>
                                <c:when test="${ticket.type == '演出'}"><span class="tag-show">演出</span></c:when>
                                <c:otherwise><span class="tag-other">票务</span></c:otherwise>
                            </c:choose>
                        </div>
                        <div class="card-body">
                            <h3><a href="${pageContext.request.contextPath}/ticket/detail?id=${ticket.id}">${ticket.name}</a></h3>
                            <div class="meta">
                                <c:choose>
                                    <c:when test="${ticket.type == 'concert'}">演唱会</c:when>
                                    <c:when test="${ticket.type == '体育'}">体育</c:when>
                                    <c:when test="${ticket.type == '电影'}">电影</c:when>
                                    <c:when test="${ticket.type == '演出'}">演出</c:when>
                                    <c:otherwise>${ticket.type}</c:otherwise>
                                </c:choose>
                                | 库存: ${ticket.stock}
                            </div>
                            <div class="price">¥${ticket.price} <small>起</small></div>
                            <a href="${pageContext.request.contextPath}/ticket/detail?id=${ticket.id}" class="btn btn-primary btn-sm">查看详情</a>
                            <a href="${pageContext.request.contextPath}/ticket/addToCart?id=${ticket.id}" class="btn btn-success btn-sm">加入购物车</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="tType" items="${typeOrder}">
                <c:if test="${not empty groupedTickets[tType]}">
                    <div class="type-section">
                        <h3 class="type-title">
                            <c:choose>
                                <c:when test="${tType == 'concert'}">演唱会</c:when>
                                <c:when test="${tType == '体育'}">体育</c:when>
                                <c:when test="${tType == '电影'}">电影</c:when>
                                <c:when test="${tType == '演出'}">演出</c:when>
                                <c:otherwise>${tType}</c:otherwise>
                            </c:choose>
                        </h3>
                        <div class="card-grid">
                            <c:forEach var="ticket" items="${groupedTickets[tType]}">
                                <div class="card">
                                    <div class="card-img" style="display:flex;align-items:center;justify-content:center;">
                                        <c:choose>
                                            <c:when test="${not empty ticket.image}">
                                                <img src="${pageContext.request.contextPath}${ticket.image}" alt="${ticket.name}" style="width:100%;height:200px;object-fit:contain;background:#f5f5f5;">
                                            </c:when>
                                            <c:when test="${ticket.type == 'concert'}"><span class="tag-concert">演唱会</span></c:when>
                                            <c:when test="${ticket.type == '体育'}"><span class="tag-sport">体育</span></c:when>
                                            <c:when test="${ticket.type == '电影'}"><span class="tag-movie">电影</span></c:when>
                                            <c:when test="${ticket.type == '演出'}"><span class="tag-show">演出</span></c:when>
                                            <c:otherwise><span class="tag-other">票务</span></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="card-body">
                                        <h3><a href="${pageContext.request.contextPath}/ticket/detail?id=${ticket.id}">${ticket.name}</a></h3>
                                        <div class="meta">
                                            <c:choose>
                                                <c:when test="${ticket.type == 'concert'}">演唱会</c:when>
                                                <c:when test="${ticket.type == '体育'}">体育</c:when>
                                                <c:when test="${ticket.type == '电影'}">电影</c:when>
                                                <c:when test="${ticket.type == '演出'}">演出</c:when>
                                                <c:otherwise>${ticket.type}</c:otherwise>
                                            </c:choose>
                                            | 库存: ${ticket.stock}
                                        </div>
                                        <div class="price">¥${ticket.price} <small>起</small></div>
                                        <a href="${pageContext.request.contextPath}/ticket/detail?id=${ticket.id}" class="btn btn-primary btn-sm">查看详情</a>
                                        <a href="${pageContext.request.contextPath}/ticket/addToCart?id=${ticket.id}" class="btn btn-success btn-sm">加入购物车</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/views/common/footer.jsp"/>
