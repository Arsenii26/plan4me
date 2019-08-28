<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <div class="container">
        <a href="plans" class="navbar-brand"><img src="resources/images/icon.png" width="50px" height="50px"> <spring:message code="app.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">


                <li class="nav-item dropdown">
                    <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown">${pageContext.response.locale}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=fr">Française</a>
                    </div>
                </li>

            </ul>
        </div>

    </div>
</nav>
<script type="text/javascript">
    const localeCode = "${pageContext.response.locale}";
</script>
