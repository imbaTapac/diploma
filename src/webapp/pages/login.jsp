<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Вход в лк</title>

    <link href="${contextPath}/pages/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/common.css" rel="stylesheet">


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="${contextPath}/pages/css/bootstrap.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="${contextPath}/pages/css/jumbotron-narrow.css" />" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <c:url value="/j_spring_security_check" var="loginUrl" />

    <form method="POST" action="${loginUrl}" class="form-signin">
        <h2 class="form-heading" align="center">Введите свои данные для входа </h2>

        <div class="form-group ${error !=null ? 'has-error':''}">
           <!-- <span>${message}</span>-->

            <input name="username" type="text" class="form-control" placeholder="Имя пользователя"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Пароль"/>



            <span>${error}</span>
            <input type="hidden" name="${_csrf.parametrName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Создать аккаунт</a></h4>
        </div>

    </form>


    <div class="footer">
        <p>&copy; Tarasii 2017</p>
    </div>

</div>
<!-- /container -->


</body>
</html>