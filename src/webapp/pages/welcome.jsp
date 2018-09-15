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

    <title><spring:message code="title_main_page"/></title>


    <link href="${contextPath}/pages/css/welcome.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
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

    <div class="row">
        <div class="col-6 col-md-3">
            <textarea rows="5">Інформаційна система "Визначення рейтингу студента" призначена для полегшення збіру даних студентів про їх культурно-масове життя та їх перевірку</textarea>
        </div>

        <!-- Catousel -->
        <div class="col-12 col-sm-6 col-md-9">
            <div id="thumbnail-preview-indicators" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#thumbnail-preview-indicators" data-slide-to="0" class="active">
                        <div class="thumbnail">
                            <img class="img-responsive"
                                 src="https://nubip.edu.ua/sites/default/files/imagecache/slide/18_07_pogoni.jpg">
                        </div>
                    </li>
                    <li data-target="#thumbnail-preview-indicators" data-slide-to="1">
                        <div class="thumbnail">
                            <img class="img-responsive"
                                 src="https://nubip.edu.ua/sites/default/files/imagecache/slide/18_05_22_fleshmob_nubip_02.jpg">
                        </div>
                    </li>
                    <li data-target="#thumbnail-preview-indicators" data-slide-to="2">
                        <div class="thumbnail">
                            <img class="img-responsive"
                                 src="https://nubip.edu.ua/sites/default/files/imagecache/slide/18_09_01_den_znan.jpg">
                        </div>
                    </li>
                </ol>
                <div class="carousel-inner">
                    <div class="item slides active">
                        <div class="slide-1"></div>
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>Підсумки військових зборів</h1>
                                <p>Студенти кафедри військової підготовки завершили польові збори прийняттям присяги на вірність українському народов</p>
                                <p class="lead"><a class="btn btn-lg btn-link" href="#" role="button"></a></p>
                            </div>
                        </div>
                    </div>
                    <div class="item slides">
                        <div class="slide-2"></div>
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>120 років НУБіП</h1>
                                <p>Дві тисячі студентів на флешмобі відобразили малий герб НУБіП</p>
                                <p><a class="btn btn-lg btn-link" href="#" role="button"></a></p>
                            </div>
                        </div>
                    </div>
                    <div class="item slides">
                        <div class="slide-3"></div>
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>1 вересня в НУБіП України</h1>
                                <p>ректор Станіслав Ніколаєнко вручив ключ знань першокурсникам!</p>
                                <p><a class="btn btn-lg btn-link" href="#" role="button"></a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="left carousel-control" href="#thumbnail-preview-indicators" role="button"
                   data-slide="prev"><span
                        class="glyphicon glyphicon-chevron-left"></span></a>
                <a class="right carousel-control" href="#thumbnail-preview-indicators" role="button"
                   data-slide="next"><span
                        class="glyphicon glyphicon-chevron-right"></span></a>
            </div>
        </div>

    </div>

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