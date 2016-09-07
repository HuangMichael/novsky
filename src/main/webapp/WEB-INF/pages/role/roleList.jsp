<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="roleListTable" cellpadding="0" cellspacing="0" border="0" class=" table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th data-column-id="id">序号</th>
			<th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false">ID
			</th>
			<th data-column-id="roleName">角色名</th>
			<th data-column-id="roleDesc">角色描述</th>
			<th data-column-id="status"> 使用状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${roleList}" var="role" varStatus="s">
			<tr class="gradeX">
				<td>${s.index+1}</td>
				<td>
					${role.id}
				</td>
				<td>
					<a onclick="showRole(${role.id})"> ${role.roleName}</a>
				</td>
				<td>
					${role.roleDesc}
				</td>
				<td class="center">
					<c:if test="${role.status==1}">启用</c:if>
					<c:if test="${role.status==0 }">禁用</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	</tfoot>
</table>