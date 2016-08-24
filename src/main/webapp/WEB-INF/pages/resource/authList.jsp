<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="authViewDiv">
    <table id="authListTable" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th width="5%" data-column-id="id">序号</th>
            <th width="15%" data-column-id="resourceCode">资源编号</th>
            <th width="15%" data-column-id="resourceName">资源名称</th>
            <th width="15%" data-column-id="resourceDesc">资源描述</th>
            <th width="5%" data-column-id="roleDesc">角色描述</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vRoleAuthViews}" var="auth" varStatus="s">
            <tr class="gradeX">
                <td>${s.index+1}</td>
                <td>
                        ${auth.resourceCode}
                </td>
                <td>
                        ${auth.resourceName}
                </td>
                <td>
                        ${auth.resourceDesc}
                </td>
                <td>
                        ${auth.role.roleDesc}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
