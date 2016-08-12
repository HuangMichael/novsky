<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<table class="table table-striped table-bordered table-hover" style="max-height: 200px;overflow: scroll">
    <thead>
    <tr>
        <th>序号</th>
        <th>故障描述</th>
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
                    ${e.node_state}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

