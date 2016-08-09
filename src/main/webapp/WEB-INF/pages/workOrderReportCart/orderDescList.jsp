<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<table id="fillTable"
       class="table table-responsive table-condensed table-striped  table-hover">
    <thead>
    <tr id="trr2">
        <th>序号</th>
        <th>设备编号</th>
        <th>设备名称</th>
        <th>设备位置</th>
        <th>设备分类</th>
        <th>故障描述</th>
    </tr>
    </thead>
    <tbody id="tbody2">
    <c:forEach items="${workOrderReportCartList}" var="workOrder" varStatus="w">
        <tr id="tr-${w.index+1}" class="gradeX">
            <td>${w.index+1}</td>
            <td>${workOrder.equipments.eqCode}</td>
            <td>${workOrder.equipments.description}</td>
            <td>${workOrder.vlocations.locName}</td>
            <td>${workOrder.equipmentsClassification.description}</td>
            <td><input type="text" id="orderDesc${workOrder.id}" class="form-control" style="height:28px"
                       value="${workOrder.orderDesc}" onchange="changeContent(${workOrder.id})"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>
    /**
     *
     * @param id  报修明细ID
     * @param orderDesc 故障描述内容
     */
    function changeContent(id) {
        var orderDesc = $("#orderDesc" + id).val();
        if (!orderDesc) {
            showMessageBox("danger", "故障描述不能为空,请输入故障描述 !");
            return;
        }
        $.ajaxSettings.async = false;
        var url = "/workOrderReportCart/updateOrderDesc";
        $.post(url, {id: id, orderDesc: orderDesc}, function (data) {
            if (data) {
                showMessageBox("info", "故障描述更新成功!");
                return;
            }
        });
    }
</script>


