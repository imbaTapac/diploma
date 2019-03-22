<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Перевірка рейтингу</title>

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${contextPath}/pages/js/rating.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-blue">
    <a class="navbar-brand" href="/"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png" alt="" /></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1" aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item nav-item-style">
                <a class="nav-link" href="#"><img class="img-fluid" src="${contextPath}/pages/img/usericon.png"/><c:out value="${sessionScope.student.getName()}"/></a>
            </li>
        </ul>
        <a class="btn  my-2 my-sm-0" type="button" href="/logout">ВИЙТИ<img class="img-fluid" src="${contextPath}/pages/img/exit.png" alt="" /></a>
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
            <c:set var="students" value="${requestScope.students}"/>
            <c:choose>
                <c:when test="${students.size() != 0}">
             <div class="mx-auto col-md-6">
                 <a class="btn btn-pri btn-proverka text-center" href="#">
                 Cтуденти які потребують перевірки
                 <img class="img_nubip img-fluid" src="${contextPath}/pages/img/proverka.png" alt="" />
                </a>
             </div>
             <table class="table text-center">
             <thead>
                <tr class="column_name">
                    <th>Порядковий номер</th>
                    <th>Прізвище</th>
                    <th>Ім'я</th>
                    <th>Телефон</th>
                    <th>Дії</th>
                </tr>
             </thead>
                <tbody id="tBody">
                <c:set var="counter" value="${1}"/>
                <c:forEach items="${requestScope.students}" var="students">
                <tr>
                    <td class="text-center"><c:out value="${counter}"/></td>
                    <td><c:out value="${students.studentSurname}"/></td>
                    <td><c:out value="${students.studentName}"/></td>
                    <td><c:out value="${students.phone}"/></td>
                    <td>
                        <button class="btn btn-pri btn-md check-btn" name="id" value="${students.id}">Перевірити рейтинг</button>
                    </td>
                <c:set var="counter" value="${counter+1}" />
                </c:forEach>
                </tbody>
            </table>
            </c:when>

                <c:when test="${students.size() == 0}">
                    <h2 align="center">Ще немає студентів, які заповнили рейтинг</h2>
                </c:when>
            </c:choose>
        </div>
</div>
    </div>
</div>
</body>
</html>