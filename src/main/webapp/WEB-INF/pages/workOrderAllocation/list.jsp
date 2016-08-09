<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="row">
        <div id="content" class="col-lg-12">
            <!-- PAGE HEADER-->
            <%@include file="../common/common-breadcrumb.jsp" %>
            <div class="row">
                <div class="col-md-12">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-title">
                            <h4><i class="fa fa-table"></i>工单分配信息</h4>
                        </div>
                        <div class="box-body">

                            <div class="box-body">
                                <a type="button" class="btn  btn-xs btn-default" id="shareBtn"
                                   onclick="share()"><i class="glyphicon glyphicon-adjust">分配工单</i></a>
                            </div>
                            <div id="resultListDiv">
                                <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                       class=" table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th data-column-id="index" data-identifier="true">序号</th>
                                        <th data-column-id="id" data-visible="false">序号</th>
                                        <%-- <th data-column-id="orderNo">维修单号</th>
                                         <th class="hidden-xs hidden-sm" data-column-id="orderLineNo">维修单行号</th>--%>
                                        <th data-column-id="eqName">设备名称</th>
                                        <th data-column-id="location">设备位置</th>
                                        <th data-column-id="eqClass">设备种类</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="reporter">报告人</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="reportTime">报告时间</th>
                                        <th data-column-id="orderDesc">故障描述</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tbody">


                                    <c:forEach items="${workOrderReportList}" var="workOrder" varStatus="w">
                                        <tr>
                                            <td>${w.index+1}</td>
                                            <td>${workOrder.id}</td>
                                                <%--<td>${workOrder.workOrderReport.orderNo}</td>
                                                <td class="hidden-xs hidden-sm">${workOrder.orderLineNo}</td>--%>
                                            <td>${workOrder.equipments.description}</td>
                                            <td>${workOrder.locations.description}</td>
                                            <td>${workOrder.equipmentsClassification.description}</td>
                                            <td class="hidden-xs hidden-sm">${workOrder.workOrderReport.reporter}</td>
                                            <td class="hidden-xs hidden-sm"><fmt:formatDate
                                                    value="${workOrder.workOrderReport.reportTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td class="hidden-xs hidden-sm">${workOrder.orderDesc}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div id="callBackPager"></div>
                        </div>
                    </div>
                    <!-- /BOX -->
                </div>
            </div>
            <%@include file="../common/common-back2top.jsp" %>
        </div>
        <!-- /CONTENT-->
    </div>
</div>
<%@include file="../common/common-foot.jsp" %>
<script type="text/javascript">


    $(function () {
        $("#datatable1").bootgrid(
                {
                    selection: true,
                    multiSelect: true,
                    rowSelect: true,
                    keepSelection: true,
                    formatters: {
                        "orderDesc": function (column, row) {
                            var conId = row.id;
                            return '<input type="text" id="orderDesc' + conId + '"  class="form-control"   style="height:24px"/>';
                        }
                    }
                }
        ).on("selected.rs.jquery.bootgrid", function (e, rows) {
            for (var i = 0; i < rows.length; i++) {
                selectedId.push(rows[i].id);
            }
        }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
            for (var i = 0; i < rows.length; i++) {
                selectedId.remove(rows[i].id);
            }
        });
    });


    function save() {
        var orderReportList = new Array();
        $("input[id^='orderDesc']").each(function () {

            var i = 0;
            var name = $(this).attr("id");
            var id = name.substring(9, name.length);
            var orderDesc = $(this).val();

            var obj = {
                id: id,
                orderDesc: orderDesc
            }

            if (id) {
                orderReportList.push(obj);
            }

        });
        for (var x in orderReportList) {
            var obj = orderReportList[x];
            var url = "/workOrderReport/saveOrderDesc";
            $.post(url, {id: obj.id, orderDesc: obj.orderDesc}, function (data) {


            });
        }
        $("#resultListDiv").load("/workOrderReport/showUpdated");
        showMessageBox("info", "故障描述已更新");
    }


    var selectedId = [];
    function share() {
        var url = "/workOrderAllocation/allocate";
        var array = selectedId.join(',');
        var ids = selectedId.join(',');
        if (!ids) {
            showMessageBox("danger", "请选择需要操作的记录!");
            return;
        }
        $.post(url, {array: array}, function (data) {
            showMessageBox("info", "维修工单已生成!");
        });
    }


</script>

