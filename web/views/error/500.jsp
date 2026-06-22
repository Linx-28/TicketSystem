<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - 服务器内部错误</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>
<div class="container" style="text-align:center;padding:100px 0;">
    <h1 style="font-size:80px;color:#ddd;">500</h1>
    <h2 style="margin:20px 0;color:#666;">服务器内部错误</h2>
    <p style="color:#999;margin-bottom:30px;">抱歉，服务器遇到了问题，请稍后再试</p>
    <a href="${pageContext.request.contextPath}/home/index" class="btn btn-primary btn-lg">返回首页</a>
</div>
<jsp:include page="/views/common/footer.jsp"/>
