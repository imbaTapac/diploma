<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="groups" value="${requestScope.groups}"/>
<c:set var="specialties" value="${requestScope.specialties}"/>

<c:url value="/save-user" var="finishRegisterUrl"/>

<spring:message htmlEscape="false" code="course" var="course"/>
<spring:message htmlEscape="false" code="phone" var="phone"/>
<spring:message htmlEscape="false" code="password" var="password"/>
<spring:message htmlEscape="false" code="confirm_password" var="confrimPassword"/>
<spring:message htmlEscape="false" code="roleCode" var="roleCode"/>
<spring:message htmlEscape="false" code="role_code_tooltip" var="roleCodeTooltip"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Завершення реєстрації</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/pages/css/style.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div class="bg_img">
    <div class="content-index">
        <div class="container-fluid ">
            <div class="row">
                <div class="col-md">
                    <div class="col-2 mx-auto">
                        <img src="${contextPath}/pages/img/student.png" class="img-fluid icon-index"/>
                    </div>
                    <h2 class="text-uppercase text-center h2-index">Завершення реєстрації</h2>
                    <div class="col-md-2 mx-auto">
                        <form:form id="userDTO" method="POST" modelAttribute="UserDTO" acceptCharset="UTF-8" action="${finishRegisterUrl}" class="form-signin">
                        <div class="form-group ${error != null ? 'has-error':''}">

                            <spring:bind path="course">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/course.png"/></i>
                                    <form:input path="course" class="form-control" placeholder="${course}" autofocus="true"/>
                                    <div>
                                        <form:errors path="course"/>
                                    </div>
                                </div>
                            </spring:bind>

                            <spring:bind path="phone">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/phone.png"/></i>
                                    <form:input type="text" path="phone" class="form-control" placeholder="${phone}"/>
                                    <div>
                                        <form:errors path="phone"/>
                                    </div>
                                </div>
                            </spring:bind>

                            <spring:bind path="specialtyName">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/specialty.png"/></i>
                                    <form:select path="specialtyName" title="Спеціальність" class="form-control group-name" items="${specialties}"/>
                                </div>
                            </spring:bind>

                            <spring:bind path="groupName">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/groups.png"/></i>
                                    <form:select path="groupName" title="Група" class="form-control group-name" items="${groups}"/>
                                </div>
                            </spring:bind>

                            <spring:bind path="roleCode">
                                <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <i class="glyphicon"><img class="img_nubip png" src="${contextPath}/pages/img/promocode.png"/></i>
                                    <form:input type="text" path="roleCode" title="${roleCodeTooltip}" class="form-control" placeholder="${roleCode}"/>
                                </div>
                            </spring:bind>

                            <spring:bind path="password">
                                    <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                        <i class="glyphicon"><img class="img_nubip" src="${contextPath}/pages/img/pass.png"/></i>
                                        <form:input path="password" type="password" class="form-control" placeholder="${password}"/>
                                        <div>
                                            <form:errors path="password"/>
                                        </div>
                                    </div>
                            </spring:bind>

                            <spring:bind path="confirmPassword">
                                    <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                        <i class="glyphicon"><img class="img_nubip confirm_password" src="${contextPath}/pages/img/confirm_pass.png"/></i>
                                        <form:input path="confirmPassword" type="password" class="form-control" placeholder="${confrimPassword}"/>
                                        <div>
                                            <form:errors path="confirmPassword"/>
                                        </div>
                                    </div>
                            </spring:bind>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="${contextPath}/pages/js/app.js"></script>
</body>
</html>
