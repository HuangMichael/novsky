<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="stationListTable" class=" table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th data-column-id="id">序号</th>
			 <th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false">ID
			<th data-column-id="stationNo">编号</th>
			<th data-column-id="description">线路</th>
			<th data-column-id="line-description">车站</th>
			<th data-column-id="status">启用状态</th>
			<th data-column-id="sortNo">排序</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${stationList}" var="station" varStatus="s">
			<tr class="gradeX">
				<td class="center">${s.index+1}</td>
					<td class="center">${station.id}</td>
				<td class="center">${station.stationNo}</td>
				<td class="center">${station.line.description}</td>
				<td class="center">${station.description}</td>
				<td>
					<c:if test="${station.status=='1'}"> <span class="badge badge-green">启用</span></td>
				</c:if>
				<c:if test="${station.status!='1'}"> <span class="badge badge-red">禁用</span></td>
				</c:if>
				</td>
				<td class="center">${station.sortNo}</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	</tfoot>
</table>