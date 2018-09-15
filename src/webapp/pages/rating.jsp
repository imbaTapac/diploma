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

    <title>Заповнення рейтингу</title>

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/pages/js/app.js"></script>
</head>
<body>
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
            <table id="myTable" align="center" class="main-table" border="10">

                <tr id ="block_header">
                    <th><spring:message code="in_order"/></th>
                    <th><spring:message code="evaluation_criterion"/></th>
                    <th><spring:message code="number_of_points"/></th>
                    <th><spring:message code="answer"/></th>
                    <th>Ваш коментар</th>
                </tr>

                <tbody id="tBody">

                <tr>
                    <th colspan="5" id="block_name" name="block_name" ><spring:message code="block_1"/></th>
                </tr>

                <tr>
                    <th colspan="5"><spring:message code="subblock_1.1"/></th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_1_1"/></td><td>10.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="10.00"></td></tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_1_2"/></td><td>9.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="9.00"></td></tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_1_3"/></td><td>8.55</td><td><input type="checkbox" unchecked name="paragraphValue" value="8.55"></td></tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_1_4"/></td><td>8.15</td><td><input type="checkbox" unchecked name="paragraphValue" value="8.15"></td></tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_1_5"/></td><td>7.20</td><td><input type="checkbox" unchecked name="paragraphValue" value="7.20"></td></tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_1_6"/></td><td>6.25</td><td><input type="checkbox" unchecked name="paragraphValue" value="6.25"></td></tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_1_7"/></td><td>5.80</td><td><input type="checkbox" unchecked name="paragraphValue" value="5.80"></td></tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_1_8"/></td><td>5.55</td><td><input type="checkbox" unchecked name="paragraphValue" value="5.55"></td></tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_1_9"/></td><td>4.50</td><td><input type="checkbox" unchecked name="paragraphValue" value="4.5"></td></tr>
                <tr><td class="column">10</td><td><spring:message code="paragraph_1_1_10"/></td><td>4.25</td><td><input type="checkbox" unchecked name="paragraphValue" value="4.25"></td></tr>
                <tr><td class="column">11</td><td><spring:message code="paragraph_1_1_11"/></td><td>3.55</td><td><input type="checkbox" unchecked name="paragraphValue" value="3.55"></td></tr>
                <tr><td class="column">12</td><td><spring:message code="paragraph_1_1_12"/></td><td>2.55</td><td><input type="checkbox" unchecked name="paragraphValue" value="2.55"></td></tr>
                <tr><td class="column">13</td><td><spring:message code="paragraph_1_1_13"/></td><td>0.25</td><td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">14</td><td><spring:message code="paragraph_1_1_14"/></td><td>0.15</td><td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>

                <tr>
                    <th colspan="5"><spring:message code="subblock_1.2"/> </th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_2_1"/></td><td>7.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="7.00"></td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_2_2"/></td><td>6.00</td><td> <input type="checkbox" unchecked name="paragraphValue" value="6.00"></td></tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_2_3"/></td><td>4.80</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="4.80"></td></tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_2_4"/></td><td>4.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="4.00"></td></tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_2_5"/></td><td>3.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="3.00"></td></tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_2_6"/></td><td>1.10</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="1.10"></td></tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_2_7"/></td><td>1.10</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="1.10"></td></tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_2_8"/></td><td>1.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="1.00"></td></tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_2_9"/></td><td>0.15</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>

                <tr>
                    <th colspan="5"><spring:message code="subblock_1.3"/> </th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_3_1"/></td><td>6.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="6.00"></td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_3_2"/></td><td>5.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="5.00"> </td> </tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_3_3"/></td><td>4.40</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="4.40"> </td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_3_4"/></td><td>4.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="4.00"></td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_3_5"/></td><td>1.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="1.00"> </td> </tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_3_6"/></td><td>0.15</td> <td> <input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>

                <tr>
                    <th colspan="5"><spring:message code="subblock_1.4"/> </th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_4_1"/></td><td>4.25</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="4.25">  </td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_4_2"/></td><td>3.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.50">  </td> </tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_4_3"/></td><td>2.75</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.75"> </td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_4_4"/></td><td>2.30</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.30">  </td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_4_5"/></td><td>0.25</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_4_6"/></td><td>0.20</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_4_7"/></td><td>0.20</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_4_8"/></td><td>0.15</td> <td> <input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_4_9"/></td><td>-10.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="-10.00">  </td> </tr>
                <tr><td class="column">10</td><td><spring:message code="paragraph_1_4_10"/></td><td>-5.00</td> <td> <input type="checkbox" unchecked name="paragraphValue" value="-5.00"> </td> </tr>

                <tr>
                    <th colspan="5" id="block_name" name="block_name">Блок 2. Навчально-наукова робота</th>
                </tr>

                <tr>
                    <th colspan="5">Блок 2.1 Особисті досягнення</th>
                </tr>

                <tr><td class="column">1</td><td>Переможці і призери міжнародних олімпіад</td><td>10.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="10.00"> </td> </tr>
                <tr><td class="column">2</td><td>Переможці ІІ етапу Всеукраїнських студентських олімпіад (1 місце)</td><td>9.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="9.00"></td> </tr>
                <tr><td class="column">3</td><td>Отримання студентом патенту на винахід (корисну модель)</td><td>6.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="6.50"></td> </tr>
                <tr><td class="column">4</td><td>Переможці І етапу студентської Всеукраїнської олімпіади (1 місце)</td><td>5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.00"></td> </tr>
                <tr><td class="column">5</td><td>Призери ІІ етапу Всеукраїнських студентських олімпіад (2, 3 місця)</td><td>4.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="4.50"></td> </tr>
                <tr><td class="column">6</td><td>Учасник ІІ етапу Всеукраїнських студентських олімпіад</td><td>3.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.50"></td> </tr>
                <tr><td class="column">7</td><td>Участь в олімпіадах Університетського рівня</td><td>1.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="1.50"></td></tr>
                <tr><td class="column">8</td><td>Публікація одноосібної статті, тези</td><td>2.30</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.30"></td></tr>
                <tr><td class="column">9</td><td>Кращий читач наукової бібліотеки (ІІ-1,75 ІІІ-1,50)</td><td>2.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.00"></td></tr>
                <tr><td class="column">10</td><td>Публікація статті в співавторстві</td><td>1.15</td> <td><input type="checkbox" unchecked name="paragraphValue" value="1.15"></td></tr>

                <tr>
                    <th colspan="5">Блок 2.2 Конференції</th>
                </tr>

                <tr><td class="column">1</td><td>Виступ на конференціях що проходять за межами території України (на іноземній мові)</td><td>7.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="7.50"> </td> </tr>
                <tr><td class="column">2</td><td>Виступ на конференціях, що проходять в Україні</td><td>5.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.50"></td> </tr>
                <tr><td class="column">3</td><td>Виступ на конференціях на рівні Університету</td><td>3.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.50"></td> </tr>
                <tr><td class="column">4</td><td>Участь на конференціях, що проходять за межами території України (на іноземній мові)</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>
                <tr><td class="column">5</td><td>Участь на конференціях що проходить на рівні України</td><td>2.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.50"></td> </tr>
                <tr><td class="column">6</td><td>Участь на конференціях на рівні Університету</td><td>0.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="0.50"></td> </tr>

                <tr>
                    <th colspan="5">Блок 2.3 Інтелектуальні конкурси</th>
                </tr>

                <tr><td class="column">1</td><td>Перемога в Інтелектуальних битвах між академічними групами (ІІ-4,50 ІІІ-4,00)</td><td>5.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="5.00"> </td> </tr>
                <tr><td class="column">2</td><td>Зайняте місце в Інтелектуальних битвах між академічними групами (IV-3,75 V-3,50 VI-3,25)</td><td>3.75</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.75"></td> </tr>
                <tr><td class="column">3</td><td>Зайняте місце в Інтелектуальних битвах між академічними групами(VII-3,0 VIII-2,75)</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>
                <tr><td class="column">4</td><td>Зайняте місце в Інтелектуальних битвах між академічними групами(VIII-XVI)</td><td>2.20</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.20"></td> </tr>
                <tr><td class="column">5</td><td>Перемога в "ЩО ДЕ КОЛИ?" "брейн ринг" та заходів клубу заінтересами  (II-1,50 III-0,75)*</td><td>2.00</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td> </tr>
                <tr><td class="column">6</td><td>Участь у заходах що проводять клуби за інтересами*</td><td>0.25</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td> </tr>
                <tr><td class="column">7</td><td>Вчинення порушення, що передбачає дисциплінарне стягнення увигляді зауваження, догани, не поселення в гуртожиток знаступного навчального року, розірвання договору найму напроживання. Свідоме ігнорування та невиконання розпорядженькерівництва факультету (ННІ), університету. Свідоме розміщеннянедостовірної інформації щодо отриманих балів</td><td>-10.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-10.00"></td></tr>
                <tr><td class="column">8</td><td>За відмову від участі у змаганнях в складі збірної команди НУБіПУкраїни за кожний тур, гру, зустріч–за поданням викладачівкафедри фізичного виховання відповідальних за підготовку збірнихкомандуніверситету з видів спорту і списків збірних командуніверситету затверджених проректором університету</td><td>-5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-5.00"></td></tr>

                <tr>
                    <th colspan="5" id="block_name" name="block_name">Блок 3. Спортивно масова робота та військово-патріотичне виховання</th>
                </tr>

                <tr>
                    <th colspan="5">Блок 3.1 Членство в збірних</th>
                </tr>

                <tr><td class="column">1</td><td>Капітан збірної команди України, МОН, Києва</td><td>10.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="10.00"> </td> </tr>
                <tr><td class="column">2</td><td>Капітан збірної Університету</td><td>8.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="8.50"></td> </tr>
                <tr><td class="column">3</td><td>Член І збірної Університету (Бере участь в 65-70% змагань)</td><td>7.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="7.00"></td> </tr>
                <tr><td class="column">4</td><td>Член ІІ збірної Університету (Бере участь в 65-70% змагань)</td><td>6.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="6.00"></td> </tr>
                <tr><td class="column">5</td><td>Капітан збірної факультету (ННІ)</td><td>5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.00"></td> </tr>
                <tr><td class="column">6</td><td>Член збірної факультету (ННІ) (Бере участь в 65-70% змагань)</td><td>4.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="4.00"></td> </tr>
                <tr><td class="column">7</td><td>Курсант кафедри військової підготовки</td><td>3.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.50"></td></tr>
                <tr><td class="column">8</td><td>Капітан збірної гуртожитку</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td></tr>
                <tr><td class="column">9</td><td>Член збірної гуртожитку (Бере участь в 65-70% змагань)</td><td>2.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.00"></td></tr>
                <tr><td class="column">10</td><td>Член спортивної секції (за умови реєстрації і відвідування не менше 50%)</td><td>1.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="1.50"></td></tr>

                <tr>
                    <th colspan="5">Блок 3.2 Особисті здобутки</th>
                </tr>

                <tr><td class="column">1</td><td>Перемога збірної університету на рівні України м. Києва (ІІ-5,00 ІІІ-4,00)</td><td>6.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="6.00"> </td> </tr>
                <tr><td class="column">2</td><td>Перемога збірної факультету на змагання що проводяться на рівніУніверситету(ІІ-3,50 ІІІ-2,80)</td><td>4.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="4.00"></td> </tr>
                <tr><td class="column">3</td><td>Перемога збірної гуртожитку на рівні гуртожитків (ІІ-2,50 ІІІ-1,80)</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>

                <tr>
                    <th colspan="5">Блок 3.3 Змагання</th>
                </tr>

                <tr><td class="column">1</td><td>Перемога в змаганні найспортивніша академічна група(ІІ-4,50 ІІІ-4,00)</td><td>5.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="5.00"> </td> </tr>
                <tr><td class="column">2</td><td>Зайняте місце в змаганні найспортивніша академічна група(IV-3,75V-3,50 VI-3,25)</td><td>3.75</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.75"></td> </tr>
                <tr><td class="column">3</td><td>Зайняте місце в змаганні найспортивніша академічна група (VII-3,0VIII-2,75)</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>
                <tr><td class="column">4</td><td>Зайняте місце в змаганні найспортивніша академічна група (VIII-XVI)</td><td>2.20</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.20"></td> </tr>
                <tr><td class="column">5</td><td>Перемога в спортивних заходах що проводить СО, СР, кафедра ФП(II-1,00 III-0,75)*</td><td>1.50</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td> </tr>
                <tr><td class="column">6</td><td>Учасник спортивних заходів що проводить СО, СР, кафедра ФП*</td><td>0.20</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td> </tr>
                <tr><td class="column">7</td><td>Вболівання за збірну Університету *</td><td>0.10</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">8</td><td>Вболівання за збірну факультету (ННІ)*</td><td>0.05</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">9</td><td>Вчинення порушення, що передбачає дисциплінарне стягнення увигляді зауваження, догани, не поселення в гуртожиток знаступного навчального року, розірвання договору найму напроживання. Свідоме ігнорування та невиконання розпорядженькерівництва факультету (ННІ), університету. Свідоме розміщеннянедостовірної інформації щодо отриманих балів</td><td>-10.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-10.00"></td></tr>
                <tr><td class="column">10</td><td>За відмову від участі у змаганнях в складі збірної команди НУБіП України за кожний тур, гру, зустріч – за поданням викладачівкафедри фізичного виховання відповідальних за підготовку збірнихкоманд університету з видів спорту і списків збірних командуніверситету затверджених проректором університету</td><td>-5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-5.00"></td></tr>

                <tr>
                    <th colspan="5" id="block_name" name="block_name">Блок 4. Культурно-мистецька робота</th>
                </tr>

                <tr>
                    <th colspan="5">Блок 4.1 Членство в колективах, командах</th>
                </tr>

                <tr><td class="column">1</td><td>Учасник основного складу народного колективу (Бере участь в 75 %заходів)</td><td>10.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="10.00"> </td> </tr>
                <tr><td class="column">2</td><td>Учасник основного складу художнього колективу (Бере участь в 75 %заходів)</td><td>8.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="8.50"></td> </tr>
                <tr><td class="column">3</td><td>Учасник збірної команди КВН Університету</td><td>4.25</td> <td><input type="checkbox" unchecked name="paragraphValue" value="4.25"></td> </tr>
                <tr><td class="column">4</td><td>Учасник збірної команди КВН факультету (ННІ)</td><td>3.25</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.25"></td> </tr>

                <tr>
                    <th colspan="5">Блок 4.2 Участь в культурно мистецькому житті Університету</th>
                </tr>

                <tr><td class="column">1</td><td>Отримання призового місця на  міжнародному рівні (ІІ-7,75 ІІІ-7,25)</td><td>8.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="8.00"> </td> </tr>
                <tr><td class="column">2</td><td>Представлення університету на міжнародному рівні</td><td>6.75</td> <td><input type="checkbox" unchecked name="paragraphValue" value="6.75"></td> </tr>
                <tr><td class="column">3</td><td>Отримання призового місця на Всеукраїнському рівні (ІІ-6,00 ІІІ-5,25)</td><td>6.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="6.50"></td> </tr>
                <tr><td class="column">4</td><td>Представлення університету на Всеукраїнському рівні</td><td>5.50</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.50"></td> </tr>
                <tr><td class="column">5</td><td>Отримання гран-прі в заходах що організовує СО Університету,кафедра культурології (Голосіївська весна)</td><td>5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.00"></td> </tr>
                <tr><td class="column">6</td><td>Отримання призових місць в заходах що організовує СО Університету, кафедра культурології (ІІ-3,00 ІІІ-2,00)</td><td>4.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="4.00"></td> </tr>
                <tr><td class="column">7</td><td>Отримання призових місць в заходах що організовує СО Університету, кафедра культурології (IV-XVI)</td><td>1.15</td> <td><input type="checkbox" unchecked name="paragraphValue" value="1.15"></tr>
                <tr><td class="column">8</td><td>Участь у мистецьких заходах на рівні університету*</td><td>1.00</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">9</td><td>Участь у мистецьких заходах на рівні факультету (ННІ)*</td><td>0.30</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>
                <tr><td class="column">10</td><td>Участь у мистецьких заходах на рівні гуртожитку*</td><td>0.10</td> <td><input type="text" name="paragraphValue"></td><td><input type="text" name="coment"></td></tr>

                <tr>
                    <th colspan="5">Блок 4.3 Мистецькі баталії</th>
                </tr>

                <tr><td class="column">1</td><td>Переможці конкурсу "Краса НУБіП" (ІІ-4,50 ІІІ-4,00)</td><td>5.00</td><td><input type="checkbox" unchecked name="paragraphValue" value="5.00"> </td> </tr>
                <tr><td class="column">2</td><td>Переможці конкурсу "Пісенних Баталій" найспівочішої академічноїгрупи (ІІ-4,50 ІІІ-4,00)</td><td>5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="5.00"></td> </tr>
                <tr><td class="column">3</td><td>Зайняте місце в конкурсу "Пісенних Баталій"(IV-3,75 V-3,50 VI-3,25)</td><td>3.75</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.75"></td> </tr>
                <tr><td class="column">4</td><td>Учасник конкурсу "Краса НУБіП"</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>
                <tr><td class="column">5</td><td>Зайняте місце в конкурсу "Пісенних Баталій" (VII-3,0 VIII-2,75)</td><td>3.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="3.00"></td> </tr>
                <tr><td class="column">6</td><td>Отримання призових місць в заходах що організовує СО Університету, кафедра культурології (ІІ-3,00 ІІІ-2,00)</td><td>2.20</td> <td><input type="checkbox" unchecked name="paragraphValue" value="2.20"></td> </tr>
                <tr><td class="column">7</td><td>Вчинення порушення, що передбачає дисциплінарне стягнення увигляді зауваження, догани, не поселення в гуртожиток з наступного навчального року, розірвання договору найму на проживання.Свідоме ігнорування та невиконання  розпоряджень керівництва факультету (ННІ), університету. Свідоме розміщення недостовірної інформації щодо отриманих балів</td><td>-10.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-10.00"></tr>
                <tr><td class="column">8</td><td>За відмову від участі у змаганнях в складі збірної команди НУБіП України за кожний тур, гру, зустріч – за поданням викладачів кафедри фізичного виховання відповідальних за підготовку збірних команд університету з видів спорту і списків збірних команд університету затверджених проректором університету</td><td>-5.00</td> <td><input type="checkbox" unchecked name="paragraphValue" value="-5.00"></td></tr>

                </tbody>
            </table>
            <%--<input align="center" type="submit" onclick="getData1()">GetStaticData</input>--%>


        </div>

        <div class="col-md-12 text-center">
            <b><p class="pagination-label">Перейти до блоку</p></b>
            <ul class="pagination pagination-lg pager" id="myPager"></ul>

        </div>

        <div align="center">
            <button class="btn btn-success btn-md" type="submit" onclick="getData2()">Надіслати</button>
        </div>

    </div>

    <b><p class="notice"><spring:message code="tooltip"/> </p></b>

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
