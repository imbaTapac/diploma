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

    <title><spring:message code="title_main_page"/> </title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/pages/resources/js/bootstrap.min.js"></script>

    <link href="${contextPath}/pages/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="${contextPath}/pages/css/bootstrap.css" />" rel="stylesheet">

    <link href="${contextPath}/pages/css/style.css" rel="stylesheet">
</head>
<body>


<div class="container">

    <div class="page-header">

        <span class="lang">
            <a href="?lang=en"><img src="/pages/picture/united-kingdom_l.png" width="25" height="15"></a>
            <a href="?lang=ru"><img src="/pages/picture/russia_l.png" width="25" height="15"></a>
            <a href="?lang=uk"><img src="/pages/picture/ukraine_l.png" width="25" height="15"></a>


        <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <sec:authorize access="isAuthenticated()">

            <spring:message code="principal.username"/>:<sec:authentication property="principal.username" />
            <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a>
          </span>
        </sec:authorize>

    </c:if>


</div>

        <div class="content">
        <form>
            <b><p id="block_name" align="center" ><spring:message code="block_1"/></p></b>
            <table id="myTable" align="center" class="main-table" border="10">

                <tr>
                    <th colspan="4"><spring:message code="subblock_1.1"/></th>
                </tr>

                <tr>
                    <th><spring:message code="in_order"/></th>
                    <th><spring:message code="evaluation_criterion"/></th>
                    <th><spring:message code="number_of_points"/></th>
                    <th><spring:message code="answer"/></th>
                </tr>
                <tr><td class="column">1</td><td name="name"><spring:message code="paragraph_1_1_1"/></td><td>10,00</td> <td> <input type="checkbox" unchecked name="score" value="10.00"> </td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_1_2"/></td><td>9,00</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_2" value="9.00"></td> </tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_1_3"/></td><td>8,55</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_3" value="8.55"></td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_1_4"/></td><td>8,15</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_4" value="8.15"></td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_1_5"/></td><td>7,20</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_5" value="7.20"></td> </tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_1_6"/></td><td>6,25</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_6" value="6.25"></td> </tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_1_7"/></td><td>5,80</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_7" value="5.80"></td></tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_1_8"/></td><td>5,55</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_8" value="5.55"></td></tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_1_9"/></td><td>4,50</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_9" value="4.5"></td></tr>
                <tr><td class="column">10</td><td><spring:message code="paragraph_1_1_10"/></td><td>4,25</td> <td><input type="checkbox" unchecked name="1.1.10" value="4.25"></td></tr>
                <tr><td class="column">11</td><td><spring:message code="paragraph_1_1_11"/></td><td>3,55</td> <td><input type="checkbox" unchecked name="paraghaph_1_1_1" value="3.55"></td></tr>
                <tr><td class="column">12</td><td><spring:message code="paragraph_1_1_12"/></td><td>2,55</td> <td> <input type="checkbox" unchecked name="paraghaph_1_1_12" value="2.55"> </tr>
                <tr><td class="column">13</td><td><spring:message code="paragraph_1_1_13"/></td><td>0,25</td> <td><input type="text" pattern="\d [0-9]" name="paraghaph_1_1_13"></td></tr>
                <tr><td class="column">14</td><td><spring:message code="paragraph_1_1_14"/></td><td>0,15</td> <td><input type="text" name="paraghaph_1_1_14"></td></tr>
                <tr><td class="column">15</td><td><spring:message code="paragraph_1_1_15"/></td><td>-1,00</td> <td><input type="text" name="paraghaph_1_1_15" value="-1.00"></td></tr>




                <tr>
                    <th colspan="4"><spring:message code="subblock_1.2"/> </th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_2_1"/></td><td>7,00</td> <td> <input type="checkbox" unchecked name="paragraph_1_2_1" value="7.00"></td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_2_2"/></td><td>6,00</td><td> <input type="checkbox" unchecked value="6.00"></td></tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_2_3"/></td><td>4,80</td> <td> <input type="checkbox" unchecked value="4.80"></td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_2_4"/></td><td>4,00</td> <td> <input type="checkbox" unchecked value="4.00"> </td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_2_5"/></td><td>3,00</td> <td> <input type="checkbox" unchecked value="3.00"> </td> </tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_2_6"/></td><td>1,10</td> <td> <input type="checkbox" unchecked value="1.10"> </td> </tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_2_7"/></td><td>1,10</td> <td> <input type="checkbox" unchecked value="1.10"></td></tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_2_8"/></td><td>1,00</td> <td> <input type="checkbox" unchecked value="1.00"> </td></tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_2_9"/></td><td>0,15</td> <td><input type="text"></td></tr>


                <tr>
                    <th colspan="4"><spring:message code="subblock_1.3"/> </th>
                </tr>

                </tr>
                <tr><td class="column">1</td><td><spring:message code="paragraph_1_3_1"/></td><td>6,00</td> <td> <input type="checkbox" unchecked value="6.00"></td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_3_2"/></td><td>5,00</td> <td> <input type="checkbox" unchecked value="5.00"> </td> </tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_3_3"/></td><td>4,40</td> <td> <input type="checkbox" unchecked value="4.40"> </td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_3_4"/></td><td>4,00</td> <td> <input type="checkbox" unchecked value="4.00"></td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_3_5"/></td><td>1,00</td> <td>  <input type="checkbox" unchecked value="1.00"> </td> </tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_3_6"/></td><td>0,15</td> <td><input type="text"></td> </tr>


                <tr>
                    <th colspan="4"><spring:message code="subblock_1.4"/> </th>
                </tr>

                <tr><td class="column">1</td><td><spring:message code="paragraph_1_4_1"/></td><td>4,25</td> <td> <input type="checkbox" unchecked value="4.25">  </td> </tr>
                <tr><td class="column">2</td><td><spring:message code="paragraph_1_4_2"/></td><td>3,50</td> <td>  <input type="checkbox" unchecked value="3.50">  </td> </tr>
                <tr><td class="column">3</td><td><spring:message code="paragraph_1_4_3"/></td><td>2,75</td> <td> <input type="checkbox" unchecked value="2.75"> </td> </tr>
                <tr><td class="column">4</td><td><spring:message code="paragraph_1_4_4"/></td><td>2,30</td> <td> <input type="checkbox" unchecked value="2.30">  </td> </tr>
                <tr><td class="column">5</td><td><spring:message code="paragraph_1_4_5"/></td><td>0,25</td> <td><input type="text" value="0.25"></td> </tr>
                <tr><td class="column">6</td><td><spring:message code="paragraph_1_4_6"/></td><td>0,20</td> <td><input type="text" value="0.20"></td> </tr>
                <tr><td class="column">7</td><td><spring:message code="paragraph_1_4_7"/></td><td>0,20</td> <td><input type="text" value="0.20"></td> </tr>
                <tr><td class="column">8</td><td><spring:message code="paragraph_1_4_8"/></td><td>0,15</td> <td> <input type="text" value="0.15"></td> </tr>
                <tr><td class="column">9</td><td><spring:message code="paragraph_1_4_9"/></td><td>-10,00</td> <td> <input type="checkbox" unchecked value="-10.00">  </td> </tr>
                <tr><td class="column">10</td><td><spring:message code="paragraph_1_4_10"/></td><td>-5,00</td> <td> <input type="checkbox" unchecked value="-5.00"> </td> </tr>

            </table>
            <input align="center" type="submit" onclick="getData1()">
        </form>
    </div>

    <b><p><spring:message code="tooltip"/> </p></b>

</div>
<script src="/pages/js/app.js"></script>
</body>
</html>