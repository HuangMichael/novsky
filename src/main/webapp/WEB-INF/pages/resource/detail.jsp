<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="authListTable" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th width="5%">序号</th>
        <th width="15%">资源名称</th>
        <th width="15%">资源描述</th>
        <th width="5%">资源级别</th>
        <th width="5%">使用状态</th>
        <th width="5%">排序</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="1,2,3,4,5,6,7,8,9,10" var="resource" varStatus="s">
        <tr class="gradeX">
            <td>${s.index+1}</td>
            <td>
                    ${resource}
            </td>
            <td>
                    ${resource}
            </td>
            <td>
                    ${resource}
            </td>
            <td class="center">
                    ${resource}
            </td>
            <td class="center">${resource}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
