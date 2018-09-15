<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/security/tags" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><spring:message code="title_login_page" /></title>

    <link href="${contextPath}/pages/css/common.css" rel="stylesheet">
    <link href="${contextPath}/pages/css/footer.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
	
	<span style="float: right">
    <a href="?lang=en"><img src="/pages/picture/united-kingdom_l.png" width="25" height="15"></a>
    <a href="?lang=ru"><img src="/pages/picture/russia_l.png" width="25" height="15"></a>
    <a href="?lang=uk"><img src="/pages/picture/ukraine_l.png" width="25" height="15"></a>
    </span>
	
    <c:url value="/j_spring_security_check" var="loginUrl" />

    <div class="main-img">
        <img src="/pages/picture/NULES_logo.png" class="image-center">
    </div>
    <h2 class="form-heading" align="center">Інформаційна система <br>"Визначення рейтингу студента"</h2>
    <form method="POST" action="${loginUrl}" class="form-signin">


        <div class="form-group ${error !=null ? 'has-error':''}">

            <input name="username" type="text" class="form-control" placeholder="<spring:message code="username" />"
                   autofocus="true"/>

            <input name="password" type="password" class="form-control" placeholder="<spring:message code="password" />"/>

            <span>${error}</span>
            <input type="hidden" name="${_csrf.parametrName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
        </div>

    </form>


    <footer class="page-footer font-small blue-grey lighten-5 mt-4">
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
<!-- /container -->


</body>
</html>