<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${vequipmentsList}" var="v" varStatus="w">
	<tr class="gradeX">
		<td>${w.index+1}</td>
		<td>${v.eqCode}</td>
		<td>${v.eqName}</td>
		<td>${v.eqClass}</td>
		<td>${v.locName}</td>
		<td>${v.status}</td>
		<td>${v.running}</td>
	</tr>
</c:forEach>