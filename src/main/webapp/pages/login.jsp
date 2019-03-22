<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/security/tags" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:url value="/j_spring_security_check" var="loginUrl" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>ІС "Визначення рейтингу студента"</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/pages/css/style.css"/>
</head>
<body>
<div class="bg_img">
    <div class="content-index">
        <div class="container-fluid ">
            <div class="row">
                <div class="col-md">
                    <div class="col-2  mx-auto">
                        <img src="${contextPath}/pages/img/student.png" class="img-fluid icon-index"/>
                    </div>
                    <h2 class="text-uppercase text-center h2-index">Онлайн-заповнення<br/>
                        рейтингу студента</h2>
                    <div class="col-md-2  mx-auto">
                        <form method="POST" action="${loginUrl}" class="form-signin">
                            <div class="form-group ${error !=null ? 'has-error':''}">
                            <div class="form-group inner-addon left-addon">
                                <i class="glyphicon"><img class="img_nubip" src="${contextPath}/pages/img/user.png"/></i>
                                <input name="username" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<spring:message code="username"/>" autofocus="true">
                            </div>

                            <div class="form-group inner-addon left-addon">
                                <i class="glyphicon"><img class="img_nubip" src="${contextPath}/pages/img/pass.png"/></i>
                                <input name="password" type="password" class="form-control" placeholder="<spring:message code="password"/>">
                            </div>
                                <span>${error}</span>
                                <input type="hidden" name="${_csrf.parametrName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-primary col-sm-12"><spring:message code="login"/></button>
                                <p class="text-center"><a href="/registration">Вперше на сайті?</a></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div id="footer-index">
                <div class="row">
                    <div class="col-md">
                        <img class="img_nubip" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png"/>
                    </div>
                    <div class="col-md-6">
                        <div class="mx-auto  text-center">
                            <a class="btn soc soc-y" href="https://www.youtube.com/"></a>
                            <a class="btn soc soc-f" href="https://www.facebook.com/"></a>
                            <a class="btn soc soc-i" href="https://www.instagram.com/"></a>
                        </div>
                    </div>
                    <div class="col-md">
                        <p class="text-right pdeveloper">
                            Website developed:
                            <a href="http://smav.com.ua/">smav.com.ua</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>