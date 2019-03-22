<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Моя група</title>

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>

<body>
<nav class="navbar navbar-expand-lg bg-blue">
    <a class="navbar-brand" href="/"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png" alt=""/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1" aria-controls="navbar1"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item nav-item-style">
                <a class="nav-link student-name" href="#"><img class="img-fluid" src="${contextPath}/pages/img/usericon.png" alt=""/><c:out value="${sessionScope.student.getName()}"/></a>
            </li>
        </ul>
        <a class="btn  my-2 my-sm-0" type="button" href="/logout">ВИЙТИ <img class="img-fluid" src="${contextPath}/pages/img/exit.png" alt=""/></a>
    </div>
</nav>

<div class="container-fluid bgcolor">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-left">
                <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                <li><a href="/students"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item6.png"/><span class="text-uppercase">Мої студенти</span></a></li>
                <li><a href="/check-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item4.png"/><span class="text-uppercase">Перевірити рейтинг</span></a></li>
                <li><a href="/reports"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item5.png"/><span class="text-uppercase">Звіти</span></a></li>
            </ul>
        </div>
        <div class="col-md-9">
            <div class="content">
                <c:set var="groups" scope="request" value="${requestScope.groups}"/>
                <c:forEach items="${groups}" var="group">
                    <c:set var="counter" value="${1}"/>
                    <div class="mx-auto text-center col-md-6">
                        <a class="btn btn-pri btn-proverka text-center" href="#" role="button">
                            <c:out value="Група ${group.name}"/>
                            <img class="img_nubip img-fluid" src="${contextPath}/pages/img/group.png"/>
                        </a>
                    </div>
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="students" colspan="5">Мої студенти</th>
                        </tr>
                        <tr>
                            <th>#</th>
                            <th>Прізвище</th>
                            <th>Ім'я</th>
                            <th>Телефон</th>
                            <th style="width: 30%">Статус</th>
                        </tr>
                        </thead>
                            <tbody id="tBody">
                            <c:forEach items="${group.students}" var="student">
                            <tr>
                                <td class="column"><c:out value="${counter}"/></td>
                                <td><c:out value="${student.studentSurname}"/></td>
                                <td><c:out value="${student.studentName}"/></td>
                                <td><c:out value="${student.phone}"/></td>
                                <c:choose>
                                    <c:when test="${student.isRatingFilled()}">
                                        <td class="text-danger status">Не заповнено</td>
                                    </c:when>

                                    <c:when test="${sessionScope.student.role.getAuthority() == 'ROLE_HEAD_OF_GROUP'}">
                                        <c:if test="${student.ratings[0].stageOfApprove >= 1}">
                                            <td class="text-success status">Перевірено</td>
                                        </c:if>
                                        <c:if test="${student.ratings[0].stageOfApprove == 0}">
                                            <td class="text-warning status">Потребує перевірки</td>
                                        </c:if>
                                    </c:when>

                                    <c:when test="${sessionScope.student.role.getAuthority() == 'ROLE_HEAD_OF_SO'}">
                                        <c:if test="${student.ratings[0].stageOfApprove == 2}">
                                            <td class="text-success status">Перевірено</td>
                                        </c:if>
                                        <c:if test="${student.ratings[0].stageOfApprove == 1}">
                                            <td class="text-warning status">Потребує перевірки</td>
                                        </c:if>
                                        <c:if test="${student.ratings[0].stageOfApprove == 0}">
                                            <td class="text-warning status">Потребує перевірки стростою</td>
                                        </c:if>
                                    </c:when>
                                </c:choose>
                            </tr>
                            <c:set var="counter" value="${counter+1}"/>
                            </c:forEach>
                            </tbody>
                    </table>
                </c:forEach>
            </div>

        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
