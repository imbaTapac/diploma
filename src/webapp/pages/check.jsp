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
    </div>

    <div class="content">
        <form method="GET" action="getData">
            <b><p align="center" ><spring:message code="block_1"/></p></b>
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
                <tr><td class="column" name="id_paragraph"></td><td name="name"><spring:message code="paragraph_1_1_1"/></td><td name="score"></td> <td> <input type="checkbox" unchecked value="10.00"></td></tr>
            </table>
            <input align="center" type="submit" onclick="getData1()">
        </form>
    </div>

    <b><p><spring:message code="tooltip"/> </p></b>

</div>

</body>
</html>