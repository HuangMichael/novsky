<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="lineListTable" cellpadding="0" cellspacing="0" border="0"
       class=" table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id">序号</th>
        <th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false">ID
        </th>
        <th data-column-id="description">线路名称</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${lineList}" var="line" varStatus="s">
        <tr class="gradeX">
            <td>${s.index+1}</td>
            <td>
                    ${line.id}
            </td>

            <td>
                    ${line.description}
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    </tfoot>
</table>
