<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>Заповнення рейтингу</title>

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
                    <c:when test="${requestScope.role=='[ROLE_HEAD_OF_GROUP]' || requestScope.role=='[ROLE_HEAD_OF_SO]'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                        <li><a href="/students"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item6.png"/><span class="text-uppercase">Мої студенти</span></a></li>
                        <li><a href="/check-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item4.png"/><span class="text-uppercase">Перевірити рейтинг</span></a></li>
                        <li><a href="/reports"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item5.png"/><span class="text-uppercase">Звіти</span></a></li>
                    </c:when>
                    <c:when test="${requestScope.role=='[ROLE_STUDENT]'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                    </c:when>
                </c:choose>
            </ul>
        </div>

        <div class="col-md-9">
            <div class="content">
               <%-- <h3 class="col-md-12 text-center" id="date">До завершення заповнення рейтингу залишилось</h3>
                <div id="clockdiv">
                    Днів: <span class="days"></span><br>
                    Годин: <span class="hours"></span><br>
                    Хвилин: <span class="minutes"></span><br>
                    Секунд: <span class="seconds"></span>
                </div>--%>
                <c:forEach items="${requestScope.rating}" var="rating"> <!--Block-->
                    <h3 class="col-md-12 text-center"><c:out value="${rating.name}"/></h3>

                    <div class="accordMenu">
                        <c:forEach items="${rating.subblock}" var="subblock"><!--Subblock-->
                        <section id="<c:out value="${subblock.id}"/>">
                            <h1 class="col-md-12">
                                <c:out value="${subblock.name}"/>
                                <a href="#<c:out value="${subblock.id}"/>" id="<c:out value="${subblock.id}"/>" class="block_show" onclick="handleSection(this)">
                                    <img class="img_nubip img-fluid" src="${contextPath}/pages/img/down.png" alt=""/>
                                </a>
                            </h1>

                            <div>
                                <c:set var="index" value="${0}"/>
                                <table id="myTable" align="center" class="table text-center">
                                    <thead class="text-center">
                                    <tr id="block_header">
                                        <th><spring:message code="in_order"/></th>
                                        <th><spring:message code="evaluation_criterion"/></th>
                                        <th><spring:message code="number_of_points"/></th>
                                        <th><spring:message code="answer"/></th>
                                        <th><spring:message code="comment"/></th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${subblock.paragraph}" var="paragraph"> <!--Parapgraph-->
                                        <tbody id="tBody">
                                        <tr>
                                            <td class="column"><c:out value="${index+1}"/></td>
                                            <td><c:out value="${paragraph.name}"/></td>
                                            <td><c:out value="${paragraph.score}"/></td>
                                            <c:choose>
                                                <c:when test="${!fn:contains(paragraph.name,'*')}">
                                                    <td>
                                                        <input type="checkbox" unchecked name="paragraphValue" value="<c:out value="${paragraph.score}"/>">
                                                    </td>
                                                </c:when>
                                                <c:when test="${fn:contains(paragraph.name,'*')}">
                                                    <td>
                                                        <input type="text" name="paragraphValue">
                                                    </td>
                                                    <td>
                                                        <input type="text" name="comment">
                                                    </td>
                                                </c:when>
                                            </c:choose>
                                        </tr>
                                        <c:set var="index" value="${index+1}"/>
                                        </tbody>
                                    </c:forEach> <!-- Paragraph -->
                                </table>
                            </div>
                        </section>
                        </c:forEach> <!-- Subblock -->
                    </div>
                </c:forEach> <!-- Block -->
                <div class="col-12 text-center">
                    <button class="btn btn-success btn-md" type="submit" onclick="submitData()">Надіслати</button>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="${contextPath}/pages/js/app.js"></script>
</body>
</html>
