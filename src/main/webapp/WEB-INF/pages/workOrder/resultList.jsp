<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<table id="datatable1" cellpadding="0" cellspacing="0" border="0"
       class=" table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>选择</th>
        <th class="hidden-xs hidden-sm" data-column-id="id">序号</th>
        <th data-column-id="line">线路</th>
        <th data-column-id="station">车站</th>
        <th data-column-id="locations">设备位置</th>
        <th class="hidden-xs hidden-sm" data-column-id="equipmentsClassification">设备分类</th>
        <th data-column-id="orderDesc">故障描述</th>
        <th class="hidden-xs hidden-sm" data-column-id="reporter">报修人</th>
        <th class="hidden-xs hidden-sm" data-column-id="id">报修人电话</th>
        <th class="hidden-xs hidden-sm" data-column-id="id">报告时间</th>
        <th>状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${workOrderList}" var="workOrder" varStatus="w">
        <tr>
            <td><input name="workOrderSel" type="checkbox" data-woId="${workOrder.id}"></td>
            <td class="hidden-xs hidden-sm">${w.index+1}</td>
            <td>${workOrder.line.description}</td>
            <td>${workOrder.station.description}</td>
            <td>${workOrder.locations.description}</td>
            <td class="hidden-xs hidden-sm">${workOrder.equipmentsClassification.description}</td>
            <td>${workOrder.orderDesc}</td>
            <td class="hidden-xs hidden-sm">${workOrder.reporter}</td>
            <td class="hidden-xs hidden-sm">${workOrder.reportTelephone}</td>
            <td class="hidden-xs hidden-sm"><fmt:formatDate value="${workOrder.reportTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <c:if test="${workOrder.status=='0'}"> <span
                    class="badge badge-red">已报修</span></td>
            </c:if>
            <c:if test="${workOrder.status=='1'}"> <span
                    class="badge badge-green">维修中</span></td></c:if>
            <c:if test="${workOrder.status=='2'}"> <span
                    class="badge badge-success">已完工</span></td></c:if>
            <c:if test="${workOrder.status=='3'}"> <span
                    class="badge badge-orange">已评价</span></td></c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>



