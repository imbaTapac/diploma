<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Мій профіль</title>

    <link href="${contextPath}/pages/css/profile.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<c:set var="student" value="${requestScope.student}"></c:set>
<div class="wrapper">
<div class="container">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!--Название сайта и кнопка раскрытия меню для мобильных-->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Stud_Rating</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <c:choose>
                        <c:when test="${requestScope.role=='[ROLE_HEAD_OF_GROUP]' || requestScope.role=='[ROLE_HEAD_OF_SO]'}">
                            <li><a href="/welcome"><span class="glyphicon glyphicon-home"></span>Головна</a></li>
                            <li><a href="/profile"><span class="glyphicon glyphicon-user"></span>Профіль</a></li>
                            <li><a href="/rating"><span class="glyphicon glyphicon-pencil"></span>Заповнити рейтинг</a></li>
                            <li><a href="/students"><span class="glyphicon glyphicon-search"></span>Мої студенти</a></li>
                            <li><a href="/check_rating"><span class="glyphicon glyphicon-pencil"></span>Перевірити рейтинг</a></li>
                            <li><a href="/reports"><span class="glyphicon glyphicon-file"></span>Звіти</a></li>
                        </c:when>
                        <c:when test="${requestScope.role=='[ROLE_STUDENT]'}">
                            <li><a href="/welcome"><span class="glyphicon glyphicon-home"></span>Головна</a></li>
                            <li><a href="/profile"><span class="glyphicon glyphicon-user"></span>Профіль</a></li>
                            <li><a href="/rating"><span class="glyphicon glyphicon-pencil"></span>Заповнити рейтинг</a></li>
                        </c:when>
                    </c:choose>
                </ul>

                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="pull-right">
                        <div class="logout">
                            <sec:authorize access="isAuthenticated()">
                                <p><sec:authentication property="principal.username"/><a class="btn btn-sm btn-primary"
                                                                                         href="<c:url value="/logout" />"
                                                                                         role="button">Вийти</a></p>
                            </sec:authorize>
                        </div>
                    </div>
                </c:if>

                <div class="pull-right">
                    <div class="lang">
                        <ul>
                            <li><a href="?lang=en"><img src="/pages/picture/united-kingdom_l.png" width="25"
                                                        height="15"></a></li>
                            <li><a href="?lang=ru"><img src="/pages/picture/russia_l.png" width="25" height="15"></a>
                            </li>
                            <li><a href="?lang=uk"><img src="/pages/picture/ukraine_l.png" width="25" height="15"></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <div id="main">
        <div class="row" id="real-estates-detail">
            <div class="col-lg-4 col-md-4 col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <header class="panel-title">
                            <div class="text-center">
                                <strong>Користувач</strong>
                            </div>
                        </header>
                    </div>
                    <div class="panel-body">
                        <div class="text-center" id="author">
                            <img src="https://im0-tub-ua.yandex.net/i?id=78c3e9ababdb3d300f8473684cec6f4b&n=13">
                            <h3><c:out value="${student.studentName} ${student.studentSurname}"></c:out></h3>
                            <small class="label label-warning">Україна</small>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8 col-md-8 col-xs-12">
                <div class="panel">
                    <div class="panel-body">
                        <ul id="myTab" class="nav nav-pills">
                            <li class="active"><a href="#detail" data-toggle="tab">Про користувача</a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <hr>
                            <div class="tab-pane fade active in" id="detail">
                                <h4>Мій профіль</h4>
                                <table class="table table-th-block">
                                    <tbody>
                                    <td class="active">Країна:</td>
                                    <td>Україна</td>
                                    </tr>
                                    <tr>
                                        <td class="active">Місто:</td>
                                        <td>Київ</td>
                                    </tr>
                                    <tr>
                                        <td class="active">Університет:</td>
                                        <td>НУБІП</td>
                                    </tr>
                                    <tr>
                                        <td class="active">Факультет:</td>
                                        <td><c:out value="${student.group.faculty.name}"></c:out></td>
                                    </tr>
                                    <tr>
                                        <td class="active">Спеціальність:</td>
                                        <td><c:out value="${student.group.faculty.specialty[0].name}"></c:out></td>
                                    </tr>
                                    <tr>
                                        <td class="active">ОКР:</td>
                                        <td><c:out value="${student.okr.name}"></c:out> </td>
                                    </tr>
                                    <tr>
                                        <td class="active">Курс:</td>
                                        <td><c:out value="${student.course}"></c:out> </td>
                                    </tr>
                                    <tr>
                                        <td class="active">Група:</td>
                                        <td><c:out value="${student.group.name}"></c:out></td>
                                    </tr>
                                    <tr>
                                        <td class="active">Заповнював рейтинг?</td>
                                        <c:choose>
                                            <c:when test="${student.ratings.size() != 0}">
                                                <td>Так</td>
                                            </c:when>
                                            <c:when test="${student.ratings.size() ==0}">
                                                <td>Ні</td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                    <tr>
                                        <td class="active">Телефон:</td>
                                        <td><c:out value="${student.phone}"></c:out></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- /.main -->
</div>
    <footer class="page-footer font-small blue-grey lighten-5">
        <div class="footer-copyright text-center text-black-50 py-2">
            <p>&copy; Tarasii 2018 «НУБІП УКРАЇНИ»</p>
            <p class="font_8">Наші соц мережі</p>
            <div class="social">
                <ul>
                    <li>
                        <a href="https://www.facebook.com/nubip.edu.ua/?ref=br_rs" target="_blank"
                           data-content="https://www.facebook.com/nubip.edu.ua/?ref=br_rs" data-type="external"
                           rel="nofollow" id="LnkBr00imagelink" class="lb1imageItemlink">
                            <img id="LnkBr00imageimageimage" class="link" src="https://static.wixstatic.com/media/ed86bdfa6aecf88649d305e11d76ac33.wix_mp/v1/fill/w_23,h_23,al_c,usm_0.66_1.00_0.01/ed86bdfa6aecf88649d305e11d76ac33.wix_mp"></a>
                    </li>
                    <li>
                        <a href="https://www.youtube.com/channel/UC-U1fqRT0jeRLUUDcMq1uaw" target="_blank"
                           data-content="https://www.youtube.com/channel/UC-U1fqRT0jeRLUUDcMq1uaw" data-type="external"
                           rel="nofollow" id="LnkBr01imagelink" class="lb1imageItemlink">
                            <img id="LnkBr01imageimageimage" class="link" src="https://static.wixstatic.com/media/45bce1d726f64f1999c49feae57f6298.png/v1/fill/w_23,h_23,al_c,usm_0.66_1.00_0.01/45bce1d726f64f1999c49feae57f6298.png"></a>
                    </li>
                </ul>
            </div>
        </div>
    </footer>
</div>
</body>
</html>
