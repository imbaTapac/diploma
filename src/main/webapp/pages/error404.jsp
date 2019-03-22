<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>Помилка</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/pages/css/errors.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<!--start-wrap--->
<div class="wrap">
    <!---start-header---->
    <div class="header">
        <div class="logo">
            <h1><a>Упс</a></h1>
        </div>
    </div>
    <!---End-header---->
    <!--start-content------>
    <div class="content">
        <img src="${contextPath}/pages/img/error-img.png" title="error" />
        <p><span><label>Упс...</label></span>Такої сторінки не існує</p>
        <a href="/welcome">На головну</a>
        <div class="copy-right">
            <p>&copy; 2018. All Rights Reserved | Design by Tarasii</p>
        </div>
    </div>
    <!--End-Cotent------>
</div>
<!--End-wrap--->
</body>
</html>

