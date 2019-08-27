<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/plan4me.common.js" defer></script>
<script type="text/javascript" src="resources/js/plan4me.plans.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>



<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="plan.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="offset-1 col-2">
                            <label for="startDate"><spring:message code="plan.startDate"/></label>
                            <input class="form-control" name="startDate" id="startDate">
                        </div>
                        <div class="col-2">
                            <label for="endDate"><spring:message code="plan.endDate"/></label>
                            <input class="form-control" name="endDate" id="endDate">
                        </div>
                        <div class="offset-2 col-2">
                            <label for="startTime"><spring:message code="plan.startTime"/></label>
                            <input class="form-control" name="startTime" id="startTime">
                        </div>
                        <div class="col-2">
                            <label for="endTime"><spring:message code="plan.endTime"/></label>
                            <input class="form-control" name="endTime" id="endTime">
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
                <th><spring:message code="plan.dateTime"/></th>
                <th><spring:message code="plan.plan"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>


<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="plan.dateTime"/></label>
                        <input class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="plan.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="plan" class="col-form-label"><spring:message
                                code="plan.plan"/></label>
                        <input type="text" class="form-control" id="plan" name="plan"
                               placeholder="<spring:message code="plan.plan"/>">
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="plan"/>
</jsp:include>
</html>