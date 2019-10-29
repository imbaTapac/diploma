<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="role" value="${sessionScope.student.role.getAuthority()}"/>

<!DOCTYPE html>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Мій рейтинг</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
    <link href="${contextPath}/pages/datepicker/css/datepicker.css" rel="stylesheet">
    <script src="${contextPath}/pages/datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${contextPath}/pages/js/my-rating.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-blue">
    <a class="navbar-brand" href="/"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png" alt=""/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1" aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item nav-item-style">
                <a class="nav-link" href="#"><img class="img-fluid" src="${contextPath}/pages/img/usericon.png" alt=""/><c:out value="${sessionScope.student.getName()}"/></a>
            </li>
        </ul>
        <a class="btn  my-2 my-sm-0" type="button" href="/logout">ВИЙТИ <img class="img-fluid" src="${contextPath}/pages/img/exit.png" alt=""/></a>
    </div>
</nav>
<div class="container-fluid bgcolor">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-left">
                <c:choose>
                    <c:when test="${role =='ROLE_HEAD_OF_GROUP' || role =='ROLE_HEAD_OF_SO'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                        <li><a href="/my-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item7.png"/><span class="text-uppercase">Мій рейтинг</span></a></li>
                        <li><a href="/students"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item6.png"/><span class="text-uppercase">Мої студенти</span></a></li>
                        <li><a href="/check-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item4.png"/><span class="text-uppercase">Перевірити рейтинг</span></a></li>
                        <li><a href="/reports"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item5.png"/><span class="text-uppercase">Звіти</span></a></li>
                    </c:when>
                    <c:when test="${role =='ROLE_STUDENT'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                        <li><a href="/my-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item7.png"/><span class="text-uppercase">Мій рейтинг</span></a></li>
                    </c:when>
                </c:choose>
            </ul>
        </div>
        <div class="col-md-9">
            <div class="content">
                <c:set var="total" value="${0.0}"/>
                <c:set var="counter" value="${1}"/>
                <h1 class="header" align="center">Мій рейтинг</h1>
                <div class="row justify-content-end custom-month-picker">
                    <div>
                        <label id="month-pick" for="month-picker">Виберіть місяць:</label>
                        <input id="month-picker" type="month" name="month">
                    </div>
                </div>
                <div id="table-wrapper">
                <c:choose>
                <c:when test="${requestScope.ratings.size()!= 0}">
                <div id="table-div">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th class="criteria">Критерій оцінки</th>
                            <th>Дата</th>
                            <th>Кількість балів</th>
                            <th>Коментар</th>
                            <th>Статус</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.ratings}" var="rating">
                            <tr class="bg-tr-blue">
                                <th scope="row" colspan="6"><c:out value="${rating.paragraph.subblock.block.name}"/></th>
                            </tr>
                            <tr class="text-center">
                                <th scope="row" colspan="6"><c:out value="${rating.paragraph.subblock.name}"/></th>
                            </tr>
                            <tr>
                                <td scope="row"><c:out value="${counter}"/></td>
                                <td class="criteria"><c:out value="${rating.paragraph.name}"/></td>
                                <td><c:out value="${rating.date}"/></td>
                                <td><c:out value="${rating.score}"/></td>
                                <td><c:out value="${rating.comment}"/></td>
                                <c:choose>
                                    <c:when test="${rating.stageOfApprove == -1}">
                                        <td class="text-dark status">Відхилено</td>
                                    </c:when>
                                    <c:when test="${rating.stageOfApprove == 0}">
                                        <td class="text-warning status">Потребує перевірки старостою</td>
                                    </c:when>
                                    <c:when test="${rating.stageOfApprove == 1}">
                                        <td class="text-danger status">Потребує перевірки головою СО</td>
                                    </c:when>
                                    <c:when test="${rating.stageOfApprove == 2}">
                                        <td class="text-success status">Перевірено</td>
                                    </c:when>
                                </c:choose>
                            </tr>
                            <c:set var="counter" value="${counter+1}"/>
                            <c:set var="total" value="${total+rating.score}"/>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="row justify-content-center raiting_margin">
                    <div id="sum" class="col-3 text-center">Сумарно балів -
                        <span id="result" class="bg-blue span-bal">
                            <fmt:formatNumber type="number" maxIntegerDigits="3" value="${total}"/>
                        </span>
                    </div>
                    <div id="final" class="col-3 text-center">Кінцевий результат- <span id="final-result" class="bg-green span-bal">
                        <c:if test="${total>=10}">
                            <fmt:formatNumber type="number" value="${10}" maxIntegerDigits="3"/>
                        </c:if>
                        <c:if test="${total<10 && total>0}">
                            <fmt:formatNumber type="number" value="${total}" maxIntegerDigits="3"/>
                        </c:if>
                        <c:if test="${total<0}">
                            <fmt:formatNumber type="number" value="${0}" maxIntegerDigits="3"/>
                        </c:if>
                        </span>
                    </div>
                </div>
                </div>
            </c:when>
            <c:when test="${requestScope.ratings.size() == 0}">
                <h2 class="header" align="center">Ви ще не заповнювали рейтинг</h2>
            </c:when>
            </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>
