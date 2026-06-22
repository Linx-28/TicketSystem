<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - 页面未找到</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container" style="text-align:center;padding:100px 0;">
    <h1 style="font-size:80px;color:#ddd;">404</h1>
    <h2 style="margin:20px 0;color:#666;">页面未找到</h2>
    <p style="color:#999;margin-bottom:30px;">您访问的页面不存在或已被移除</p>
    <a href="${pageContext.request.contextPath}/home/index" class="btn btn-primary btn-lg">返回首页</a>
</div>
<jsp:include page="/views/common/footer.jsp"/>
