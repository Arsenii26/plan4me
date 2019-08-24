<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Plan</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="WEB-INF/jsp/index.jsp">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create plan' : 'Edit plan'}</h2>

    <jsp:useBean id="plan" type="ca.arsenii.plan4me.model.Plan" scope="request"/>

    <form method="post" action="plans">

        <input type="hidden" name="id" value="${plan.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${plan.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Plan:</dt>
            <dd><input type="text" value="${plan.plan}" size=40 name="plan" required></dd>
        </dl>

        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
