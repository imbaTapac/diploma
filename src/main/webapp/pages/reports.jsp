<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- //TODO: when to the user shows the report and then he clicks to another, the previous must be hidden and his data must be dropped. -->

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Звіти</title>

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/report.css" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    <script src="${contextPath}/pages/js/report.js"></script>
</head>
<nav class="navbar navbar-expand-lg bg-blue">
    <a class="navbar-brand" href="/"><img class="img_nubip img-fluid" src="${contextPath}/pages/img/nubip_bottom_logo_ua.png" alt="" /></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1" aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item nav-item-style">
                <a class="nav-link" href="#"><img class="img-fluid" src="${contextPath}/pages/img/usericon.png" alt="" /> <c:out value="${sessionScope.student.getName()}"/></a>
            </li>
        </ul>
        <a class="btn  my-2 my-sm-0" type="button" href="/logout">ВИЙТИ <img class="img-fluid" src="${contextPath}/pages/img/exit.png" alt="" /></a>
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

        <div class="col-md-9" >
            <div class="content">
                <div class="row">
                    <div class="col-4">
                        <div class="report-top">
                            <div class="report-top-header">
                                <p class="text-center report_p">Звіти в EXCEL <img class=" img-fluid" src="${contextPath}/pages/img/excel-top.png" alt="" /></p>
                            </div>
                            <div class="report-top-body">
                                <button type="button" class="btn btn-bg-report col btn-sm" onclick="location.href='/report_by_group'">По всіх групах та їх студентах</button>
                                <button type="button" class="btn btn-bg-report col btn-sm" onclick="location.href='/avg_report_by_groups'">По групах загальний</button>
                                <button type="button" class="btn btn-bg-report col btn-sm">Студенти за курсом</button>
                            </div>
                        </div>
                    </div>

                    <div class="col-4 offset-md-4">
                        <div class="report-top">
                            <div class="report-top-header">
                                <p class="text-center report_p">Статистичні звіти <img class=" img-fluid" src="${contextPath}/pages/img/report-top.png" alt="" /></p>
                            </div>
                            <div class="report-top-body">
                                <button type="button" class="btn btn-bg-report col btn-sm anchor1" href="statreport1-anchor">Статистичний по групах</button>
                                <button type="button" class="btn btn-bg-report col btn-sm anchor2" href="statreport2-anchor">Статистичний по групах (загальний)</button>
                            </div>
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

        </div>
    </div>
</div>


</body>
</html>
