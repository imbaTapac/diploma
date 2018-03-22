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

    <title><spring:message code="title_login_page" /></title>

    <link href="${contextPath}/pages/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/common.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="${contextPath}/pages/css/bootstrap.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="${contextPath}/pages/css/jumbotron-narrow.css" />" rel="stylesheet">

</head>

<body>

<div class="container">
	
	<span style="float: right">
    <a href="?lang=en"><img src="/pages/picture/united-kingdom_l.png" width="25" height="15"></a>
    <a href="?lang=ru"><img src="/pages/picture/russia_l.png" width="25" height="15"></a>
    <a href="?lang=uk"><img src="/pages/picture/ukraine_l.png" width="25" height="15"></a>
    </span>
	
    <c:url value="/j_spring_security_check" var="loginUrl" />

    <form method="POST" action="${loginUrl}" class="form-signin">
        <h2 class="form-heading" align="center"><spring:message code="enter_data" /></h2>

        <div class="form-group ${error !=null ? 'has-error':''}">
           <%--<span>${message}</span>--%>

            <input name="username" type="text" class="form-control" placeholder="<spring:message code="username" />"
                   autofocus="true"/>

            <input name="password" type="password" class="form-control" placeholder="<spring:message code="password" />"
            />



            <span>${error}</span>
            <input type="hidden" name="${_csrf.parametrName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
            <h4 class="text-center"><a href="${contextPath}/registration"><spring:message code="create_acc" /></a></h4>
        </div>

    </form>


    <div class="footer">
        <p>&copy; Tarasii 2018</p>
    </div>

</div>
<!-- /container -->


</body>
</html>