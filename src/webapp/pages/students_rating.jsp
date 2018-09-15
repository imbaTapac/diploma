<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>Перевірка рейтингу студента</title>

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/pages/js/students_rating.js"></script>
</head>
<div class="wrapper">
<div class="container">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!--Название сайта и кнопка раскрытия меню для мобильных-->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Stud_Rating</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="/welcome"><span class="glyphicon glyphicon-home"></span>Головна</a></li>
                    <li><a href="/profile"><span class="glyphicon glyphicon-user"></span>Профіль</a></li>
                    <li><a href="/rating"><span class="glyphicon glyphicon-pencil"></span>Заповнити рейтинг</a></li>
                    <li><a href="/students"><span class="glyphicon glyphicon-search"></span>Мої студенти</a></li>
                    <li><a href="/check_rating"><span class="glyphicon glyphicon-pencil"></span>Перевірити рейтинг</a></li>
                    <li><a href="/reports"><span class="glyphicon glyphicon-file"></span>Звіти</a></li>
                </ul>

                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="pull-right">
                        <div class="logout">
                            <sec:authorize access="isAuthenticated()">
                                <p><sec:authentication property="principal.username"/><a class="btn btn-sm btn-primary" href="<c:url value="/logout" />" role="button">Вийти</a></p>
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
        <c:set var="id" value="${requestScope.rating[0].student.id}"></c:set>
        <c:set var="total" value="${0.0}"></c:set>
        <h1 align="center">Рейтинг студента</h1>

        <table id="myTable" class="main-table" border="10">

            <tr class="column_name">
                <th>№ П/П</th>
                <th>Критерій оцінки</th>
                <th>Дата</th>
                <th>Кількість балів</th>
                <th>Коментар</th>
            </tr>

            <tbody id="tBody">
            <c:set var="counter" value="${1}"></c:set>
            <c:forEach items="${requestScope.rating}" var="rating">

                <tr>
                    <th colspan="5" id="block_name" name="block_name"><c:out value="${rating.paragraph.subblock.block.name}"></c:out></th>
                </tr>

                <tr>
                    <th colspan="5"><c:out value="${rating.paragraph.subblock.name}"></c:out></th>
                </tr>


                <tr><td class="column"><c:out value="${counter}"></c:out></td><td><c:out value="${rating.paragraph.name}"></c:out></td><td><c:out value="${rating.date}"></c:out></td><td><c:out value="${rating.score}"></c:out></td><td><c:out value="${rating.comment}"></c:out></td></tr>
                <c:set var="counter" value="${counter+1}" />
                <c:set var="total" value="${total+rating.score}"/>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <div class="outer">
        <div class="inline">
            Сумарно балів<br> <fmt:formatNumber type="number" maxIntegerDigits = "3" value="${total}"></fmt:formatNumber>
        </div>
        <div class="inline">
            Кінцевий результат<br>
            <c:if test="${total>10}">
                <fmt:formatNumber type="number" value="${10}" maxIntegerDigits="3"></fmt:formatNumber>
            </c:if>
            <c:if test="${total<10}">
                <fmt:formatNumber type="number" value="${total}" maxIntegerDigits="3"></fmt:formatNumber>
            </c:if>
        </div>
    </div>

    <div class="row justify-content-center" align="center">
        <button class="btn btn-success btn-md accept" value="${id}">Підтвердити рейтинг</button>
        <button class="btn btn-danger btn-md" id="reject" onclick="showDropdown()"><a class="anchor" href="rejection-anchor">Не підтверджувати рейтинг</a></button>
    </div>

    <a id="rejection-anchor"></a>
    <div class="form-group">
        <div class="rejection-dropdown animate fadeInUpBig" align="center" #rejection>
            <textarea class="form-control form-control-md" id="reason" type="text" value="Вкажіть причину" rows="3"></textarea>
            <button class="btn btn-success btn-md to-modal" data-toggle="modal" data-target="#exampleModal">Надіслати</button>
        </div>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Підтвердження змін</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Після підтвердження внесення змін, відмінити їх будет неможливо. Ви впевнені?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Ні</button>
                    <button type="button" class="btn btn-primary reject-btn" value="${id}">Так</button>
                </div>
            </div>
        </div>
    </div>

</div>
    <footer class="page-footer font-small blue-grey lighten-5">
        <div class="footer-copyright container-fluid text-center text-black-50 py-2">
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