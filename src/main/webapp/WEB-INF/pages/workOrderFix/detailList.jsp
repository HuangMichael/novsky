<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<table id="detailList" cellpadding="0" cellspacing="0" border="0"
       class=" table table-striped table-bordered table-hover">
    <thead>
    <tr>

        <th data-column-id="index">序号</th>
        <th data-column-id="id" data-visible="false">序号</th>
        <th data-column-id="eqName">设备名称</th>
        <th data-column-id="location">设备位置</th>
        <th data-column-id="eqClass">设备种类</th>
        <th data-column-id="orderDesc">故障描述</th>
        <th class="hidden-xs hidden-sm" data-column-id="pause" data-formatter="transform"
            width="10%">
            转单
        </th>
    </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach items="${workOrderFixDetailList}" var="workOrder" varStatus="w">
        <tr>
            <td>${w.index+1}</td>
            <td>${workOrder.id}</td>
            <td>${workOrder.equipments.description}</td>
            <td>${workOrder.locations.description}</td>
            <td>${workOrder.equipmentsClassification.description}</td>
            <td>${workOrder.orderDesc}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>


    $(function () {

        $("#detailList").bootgrid(
                {
                    highlightRows: true,
                    formatters: {
                        "transform": function (column, row) {
                            var conId = row.id;

                            return '<a class="btn btn-default btn-xs" onclick ="transform(' + conId + ')">转单</a>';
                        }


                    }
                }
        ).on("selected.rs.jquery.bootgrid", function (e, rows) {
            for (var i = 0; i < rows.length; i++) {
                selectedId.push(rows[i].id);
            }

        }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
            var rowIds = [];
            for (var i = 0; i < rows.length; i++) {
                selectedId.remove(rows[i].id);
            }
        });


    });




</script>


