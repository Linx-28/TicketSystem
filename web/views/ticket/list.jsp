<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>票品列表 - 票务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container">
    <form action="${pageContext.request.contextPath}/ticket/search" method="get" class="search-box">
        <input type="text" name="keyword" class="form-control" placeholder="搜索票品..." value="${param.keyword}">
        <button type="submit" class="btn btn-primary">搜索</button>
    </form>

    <div class="category-tabs">
        <a href="${pageContext.request.contextPath}/home/index" class="${empty currentType ? 'active' : ''}">全部</a>
        <a href="${pageContext.request.contextPath}/home/index?type=concert" class="${currentType == 'concert' ? 'active' : ''}">演唱会</a>
        <a href="${pageContext.request.contextPath}/home/index?type=体育" class="${currentType == '体育' ? 'active' : ''}">体育</a>
        <a href="${pageContext.request.contextPath}/home/index?type=电影" class="${currentType == '电影' ? 'active' : ''}">电影</a>
        <a href="${pageContext.request.contextPath}/home/index?type=演出" class="${currentType == '演出' ? 'active' : ''}">演出</a>
    </div>

            <h2 class="page-title">
        <c:if test="${not empty keyword}">搜索："${keyword}"</c:if>
        <c:if test="${empty keyword}">
            <c:choose>
                <c:when test="${currentType == 'concert'}">演唱会票品</c:when>
                <c:when test="${currentType == '体育'}">体育票品</c:when>
                <c:when test="${currentType == '电影'}">电影票品</c:when>
                <c:when test="${currentType == '演出'}">演出票品</c:when>
                <c:otherwise>全部票品</c:otherwise>
            </c:choose>
        </c:if>
    </h2>

    <c:if test="${empty ticketList}"><div class="alert alert-info">暂无相关票品</div></c:if>
    <div class="card-grid">
        <c:forEach var="t" items="${ticketList}">
            <div class="card">
                <div class="card-img" style="display:flex;align-items:center;justify-content:center;">
                    <c:choose>
                        <c:when test="${not empty t.image}">
                            <img src="${pageContext.request.contextPath}${t.image}" alt="${t.name}" style="width:100%;height:200px;object-fit:contain;background:#f5f5f5;">
                        </c:when>
                        <c:when test="${t.type == 'concert'}"><span class="tag-concert">演唱会</span></c:when>
                        <c:when test="${t.type == '体育'}"><span class="tag-sport">体育</span></c:when>
                        <c:when test="${t.type == '电影'}"><span class="tag-movie">电影</span></c:when>
                        <c:when test="${t.type == '演出'}"><span class="tag-show">演出</span></c:when>
                        <c:otherwise><span class="tag-other">票务</span></c:otherwise>
                    </c:choose>
                </div>
                <div class="card-body">
                    <h3><a href="${pageContext.request.contextPath}/ticket/detail?id=${t.id}">${t.name}</a></h3>
                    <div class="meta">
                        <c:choose>
                            <c:when test="${t.type == 'concert'}">演唱会</c:when>
                            <c:when test="${t.type == '体育'}">体育</c:when>
                            <c:when test="${t.type == '电影'}">电影</c:when>
                            <c:when test="${t.type == '演出'}">演出</c:when>
                            <c:otherwise>${t.type}</c:otherwise>
                        </c:choose>
                        | 库存: ${t.stock}
                    </div>
                    <div class="price">¥${t.price}</div>
                    <a href="${pageContext.request.contextPath}/ticket/detail?id=${t.id}" class="btn btn-primary btn-sm">详情</a>
                    <a href="${pageContext.request.contextPath}/ticket/addToCart?id=${t.id}" class="btn btn-success btn-sm">加入购物车</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="/views/common/footer.jsp"/>
