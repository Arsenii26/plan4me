<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/plan4me.common.js" defer></script>
<script type="text/javascript" src="resources/js/plan4me.users.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>


<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="plan.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">


            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-3">
                            <label for="startDate"><spring:message code="plan.startDate"/></label>
                            <input class="form-control" type="date" name="startDate" id="startDate">
                        </div>
                        <div class="col-3">
                            <label for="endtDate"><spring:message code="plan.endDate"/></label>
                            <input class="form-control" type="date" name="endtDate" id="endtDate">
                        </div>
                        <div class="col-3">
                            <label for="startTime"><spring:message code="plan.startTime"/></label>
                            <input class="form-control" type="date" name="startTime" id="startTime">
                        </div>
                        <div class="col-3">
                            <label for="endTime"><spring:message code="plan.endTime"/></label>
                            <input class="form-control" type="date" name="endTime" id="endTime">
                        </div>
                    </div>
                </form>
            </div>


            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="plan.filter"/>
                </button>
            </div>


        </div>
        <br/>


        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="plan.dateTime"/> </th>
                <th><spring:message code="plan.plan"/> </th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${plans}" var="plan">
                <jsp:useBean id="plan" type="ca.arsenii.plan4me.to.PlanTo"/>
                    <td>${fn:formatDateTime(plan.dateTime)}</td>
                    <td>${plan.plan}</td>
                    <td><a><span class="fa fa-pencil"></span> </a></td>
                    <td><a onclick="deleteRow(${plan.id})"><span class="fa fa-remove"></span> </a> </td>
            </c:forEach>
        </table>

    </div>

</div>


<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"><spring:message code="plan.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">x</button>
            </div>

            <div class="modal-body">

                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="plan.dateTime"/> </label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="plan.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="plan" class="col-form-label"><spring:message code="plan.plan"/> </label>
                        <input type="text" class="form-control" id="plan" name="plan"
                               placeholder="<spring:message code="plan.plan"/>">
                    </div>

                </form>

            </div>

        </div>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
