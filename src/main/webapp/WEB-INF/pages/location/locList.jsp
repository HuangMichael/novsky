<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<table class="table table-striped table-bordered table-hover" style="max-height: 200px;overflow: scroll">
    <thead>
    <tr>
        <th>序号</th>
        <th>故障描述</th>
        <%--<th>报修人</th>--%>
        <th>报修时间</th>
        <th>维修状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${vworkOrderStepList}" var="e" varStatus="s">
        <tr class="gradeX">
            <td>${s.index+1}</td>
            <td>
                    ${e.orderDesc}
            </td>
            <td>
                    ${e.reportTime}
            </td>
            <td>
                <c:if test="${e.flowDesc==0}">报修车</c:if>
                <c:if test="${e.flowDesc==1}">已报修</c:if>
                <c:if test="${e.flowDesc==2}">已分配</c:if>
                <c:if test="${e.flowDesc==3}">已暂停</c:if>
                <c:if test="${e.flowDesc==4}">已完工</c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

