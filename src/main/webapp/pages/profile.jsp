<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
        <html>
        <head>
        <meta charset="utf-8">
        <title>Мій профіль</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="${contextPath}/pages/css/style.css"/>
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
                <a class="nav-link" student-name href="#"><img class="img-fluid" src="${contextPath}/pages/img/usericon.png"/><c:out value=" ${student.getName()}"/></a>
            </li>
        </ul>
        <a class="btn  my-2 my-sm-0" type="button" href="/logout">ВИЙТИ<img class="img-fluid" src="${contextPath}/pages/img/exit.png" alt="" /></a>
    </div>
</nav>
<div class="container-fluid bgcolor">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-left">
                <c:choose>
                    <c:when test="${sessionScope.student.role.getAuthority() =='ROLE_HEAD_OF_GROUP' || sessionScope.student.role.getAuthority()=='ROLE_HEAD_OF_SO'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                        <li><a href="/my-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item7.png"/><span class="text-uppercase">Мій рейтинг</span></a></li>
                        <li><a href="/students"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item6.png"/><span class="text-uppercase">Мої студенти</span></a></li>
                        <li><a href="/check-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item4.png"/><span class="text-uppercase">Перевірити рейтинг</span></a></li>
                        <li><a href="/reports"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item5.png"/><span class="text-uppercase">Звіти</span></a></li>
                    </c:when>
                    <c:when test="${sessionScope.student.role.getAuthority()=='ROLE_STUDENT'}">
                        <li><a href="/welcome"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item1.png"/><span class="text-uppercase">Головна сторінка</span></a></li>
                        <li><a href="/profile"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item2.png"/><span class="text-uppercase">Профіль</span></a></li>
                        <li><a href="/rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item3.png"/><span class="text-uppercase">Заповнити рейтинг</span></a></li>
                        <li><a href="/my-rating"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/item7.png"/><span class="text-uppercase">Мій рейтинг</span></a></li>
                    </c:when>
                </c:choose>
            </ul>
        </div>
        <div class="col-md-9" >
            <div class="content">
                <div class="row">
                    <div class="col-3">
                        <div class="photo_user">
                            <img src="${contextPath}/pages/img/avatar_user.png" class="img-fluid" alt="" />
                        </div>
                        <div class="name_user">
                            <p class="text-center"><c:out value=" ${student.studentSurname} ${student.studentName}"/></p>
                        </div>
                    </div>
                    <div class="col items_block">
                        <div class="container">
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user1.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Країна
                                </div>
                                <div class="col-6 text_user_item2">
                                    Україна
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user2.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Місто
                                </div>
                                <div class="col-6 text_user_item2">
                                    Київ
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user3.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Університет
                                </div>
                                <div class="col-6 text_user_item2">
                                    НУБІП
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user4.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Факультет
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.group.faculty.name}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user5.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Спеціальність
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.specialty.name}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user6.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    ОКР
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.okr.name}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user7.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Курс
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.course}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user8.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Група
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.group.name}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user9.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Заповнював рейтинг?
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:choose>
                                        <c:when test="${student.ratings.size() != 0}">
                                            <a>Так</a>
                                        </c:when>
                                        <c:when test="${student.ratings.size() ==0}">
                                            <a>Ні</a>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user10.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Телефон
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.phone}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1 img_item">
                                    <img src="${contextPath}/pages/img/item_user11.png" class="img-fluid" alt="" />
                                </div>
                                <div class="col-5 text_user_item">
                                    Email
                                </div>
                                <div class="col-6 text_user_item2">
                                    <c:out value="${student.email}"/>
                                </div>
                            </div>
                        </div>

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