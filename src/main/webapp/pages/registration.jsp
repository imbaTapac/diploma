<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:url value="/registration" var="registerUrl"/>
<spring:message htmlEscape="false" code="username" var="username"/>
<spring:message htmlEscape="false" code="ldap_email" var="ldap_email"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Реєстрація користувача</title>
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
                    <h2 class="text-uppercase text-center h2-index">Реєстрація нового користувача</h2>
                    <div class="col-md-2  mx-auto">
                        <form:form method="POST" modelAttribute="LDAPUser" action="${registerUrl}" class="form-signin">
                        <div class="form-group ${error != null ? 'has-error':''}">
                            <spring:bind path="login">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip" src="${contextPath}/pages/img/user.png"/></i>

                                    <form:input type="text" path="login" class="form-control"
                                                placeholder="${username}"
                                                autofocus="true"/>
                                    <form:errors path="login"/>
                                </div>
                            </spring:bind>

                            <spring:bind path="email">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/email.png"/></i>

                                    <form:input type="text" path="email" class="form-control"
                                                aria-describedby="emailHelp"
                                                placeholder="${ldap_email}"/>
                                    <form:errors path="email"/>
                                </div>
                            </spring:bind>
                            <span>${error}</span>
                        </div>
                        <button type="submit" class="btn btn-primary col-sm-12"><spring:message code="registration"/></button>
                    </div>
                    </form:form>
                </div>
            </div>
        </div>
        <div id="footer-index">
            <div class="row">
                <div class="col-md">
                    <img class="img_nubip-footer" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png">
                </div>
                <div class="col-md-6">
                    <div class="mx-auto  text-center">
                        <a class="btn soc soc-y" href="https://www.youtube.com/"></a>
                        <a class="btn soc soc-f" href="https://www.facebook.com/"></a>
                        <a class="btn soc soc-i" href="https://www.instagram.com/"></a>
                        <span><br>Desing by smav.com.ua. Develop by Tarasii</span>
                        <span><br>© 2018-2019. All Rights Reserved</span>
                    </div>
                </div>
                <div class="col-md"></div>
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