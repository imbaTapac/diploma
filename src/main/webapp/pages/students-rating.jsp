<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${contextPath}/pages/css/style.css" />
    <script type="text/javascript" src="${contextPath}/pages/js/students_rating.js"></script>
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
                <c:set var="id" value="${requestScope.rating[0].student.id}"/>
                <c:set var="total" value="${0.0}"/>
                <c:set var="counter" value="${1}"/>
                <h1 align="center">Рейтинг студента</h1>

                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Критерій оцінки</th>
                            <th>Дата</th>
                            <th>Кількість балів</th>
                            <th>Коментар</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.rating}" var="rating">
                    <tr class="bg-tr-blue">
                        <th  scope="row" colspan="5"><c:out value="${rating.paragraph.subblock.block.name}"/></th>
                    </tr>
                    <tr class="text-center">
                        <th  scope="row" colspan="5"><c:out value="${rating.paragraph.subblock.name}"/></th>
                    </tr>
                    <tr>
                        <td scope="row"><c:out value="${counter}"/></td>
                        <td><c:out value="${rating.paragraph.name}"/></td>
                        <td><c:out value="${rating.date}"/></td>
                        <td><c:out value="${rating.score}"/></td>
                        <td><c:out value="${rating.comment}"/></td>
                    </tr>
                    <c:set var="counter" value="${counter+1}" />
                    <c:set var="total" value="${total+rating.score}"/>
                    </c:forEach>

                    </tbody>
                </table>

                <div class="row justify-content-center raiting_margin">
                    <div class="col-3 text-center">Сумарно балів -   <span class="bg-blue span-bal"><fmt:formatNumber type="number" maxIntegerDigits = "3" value="${total}"/></span></div>
                    <div class="col-3 text-center">Кінцевий результат-  <span class="bg-green span-bal">
                        <c:if test="${total>10}">
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
                <div class="row justify-content-center raiting_margin">
                    <div class="col-3">
                        <button class="btn bg-green col btn-sm accept" value="${id}">Підтвердити рейтинг</button>
                    </div>
                    <div class="col-3">
                        <button class="btn bg-red col btn-sm" id="reject" onclick="showDropdown()">
                            <a class="anchor">Не підтверджувати рейтинг</a>
                        </button>
                    </div>
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
        </div>
    </div>


</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>