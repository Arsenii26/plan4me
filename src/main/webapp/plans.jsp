<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Plans</title>
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a> </h3>
    <hr/>
    <h2>Plans</h2>

    <a href="plans?action=create">Add plan</a>
    <br>
    <br>
    <table border="1", cellspacing="0", cellpadding="8">
        <thead>
        <tr>
            <th>Date</th>
            <th>Plan</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${plans}" var="plan11">
            <jsp:useBean id="plan11" type="ca.arsenii.plan4me.model.PlanTo"/>
            <tr>
                <td>
                        ${fn:formatDateTime(plan11.localDateTime)}
                </td>
                <td>${plan11.plan}</td>
                <td><a href="plans?action=update&id=${plan11.id}">Update</a></td>
                <td><a href="plans?action=delete&id=${plan11.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
