<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Звіти</title>

    <link href="${contextPath}/pages/css/report.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.17/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.flash.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>

    <link href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.10.17/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.10.17/css/dataTables.bootstrap.min.css" rel="stylesheet">

    <script src="${contextPath}/pages/js/report.js"></script>
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
                                <li><a href="?lang=en"><img src="/pages/picture/united-kingdom_l.png" width="25" height="15"></a></li>
                                <li><a href="?lang=ru"><img src="/pages/picture/russia_l.png" width="25" height="15"></a></li>
                                <li><a href="?lang=uk"><img src="/pages/picture/ukraine_l.png" width="25" height="15"></a></li>
                            </ul>
                        </div>
                    </div>

                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="row">
            <div class="col-4 pull-left">
                <p class="report-name text-danger">Звіти в Excel</p>
                <div class="btn-group">
                    <button class="btn-primary btn-md" onclick="location.href='/report_by_group'">По всіх групах та їх студентах</button>
                    <button class="btn-primary btn-md" onclick="location.href='/avg_report_by_groups'">По групах загальний</button>
                    <button class="btn-primary btn-md">Студенти за курсом</button>
                </div>
            </div>
            <div class="col-4 pull-right">
               <p class="report-name text-danger">Статистичні звіти</p>
                <div class="btn-group">
                    <button class="btn-primary btn-md anchor1" href="statreport1-anchor">Статистичний по групах</button>
                    <button class="btn-primary btn-md anchor2" href="statreport2-anchor">Статистичний по групах (загальний)</button>
                </div>
            </div>
        </div>

        <a id="statreport1-anchor"></a>
        <div class="form-group">
            <div class="statreport1-dropdown animate fadeInUpBig" align="center" #statreport1>
                <b><p id="block_name" align="center" >По групах (загальний)</p></b>
                <table id="example1" class="table table-striped table-bordered" border="10"  width="60%">
                    <thead>
                    <tr id="columns">
                        <th>№ <br>П/П</th>
                        <th>Група</th>
                        <th>Середнє значення</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>

        <a id="statreport2-anchor"></a>
        <div class="form-group">
            <div class="statreport2-dropdown animate fadeInUpBig" align="center" #statreport2>
                <b><p id="block_name" align="center" >По групах (загальний)</p></b>
                <table id="example2" class="table table-striped table-bordered" border="10"  width="60%">
                    <thead>
                    <tr id="columns">
                        <th>№ <br>П/П</th>
                        <th>Прізвище</th>
                        <th>Ім'я</th>
                        <th>Група</th>
                        <th>Блок</th>
                        <th>Підблок</th>
                        <th>Пункт</th>
                        <th>Бал</th>
                    </tr>
                    </thead>
                </table>
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
