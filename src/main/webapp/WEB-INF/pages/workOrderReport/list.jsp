<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="js/jquery-treegrid/css/jquery.treegrid.css">
<link rel="stylesheet" href="http://yandex.st/highlightjs/7.3/styles/default.min.css">
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
                            <h4><i class="fa fa-table"></i>报修单信息</h4>
                        </div>
                        <div class="box-body">
                            <div id="contentDiv">
                                <div class="box-body">
                                    <div class="tabbable">
                                        <ul class="nav nav-tabs" id="myTab">
                                            <li class="active"><a href="#tab_1_0" data-toggle="tab">
                                                <i class="fa fa-home" id="eq"></i>报修单信息</a>
                                            </li>

                                            <li><a href="#pdf_view" data-toggle="tab">
                                                <i class="fa fa-flag" id="pdf-preview"></i>报修单预览</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="tab_1_0">
                                                <table id="fixListTable"
                                                       class=" table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" data-width="5%">序号</th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
                                                        <th data-column-id="eqName" data-width="10%">设备名称</th>
                                                        <th data-column-id="orderDesc" data-width="20%">故障描述</th>
                                                        <th data-column-id="location" data-width="10%">设备位置</th>
                                                        <th data-column-id="eqClass" data-width="8%">设备分类</th>
                                                        <th data-column-id="reportTime" data-width="10%">生成时间</th>
                                                        <th data-column-id="nodeState" data-width="5%">报修状态</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${workOrderReportDetailList}" var="d"
                                                               varStatus="ds">
                                                        <tr>
                                                            <td>${ds.index+1} </td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td class="hidden-xs hidden-sm">${d.orderDesc}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                <fmt:formatDate value="${d.reportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                                            </td>
                                                            <td>${d.nodeState}</td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="tab-pane fade" id="pdf_view">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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


<div class="modal fade " id="fix_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">报修车明细信息</h4>
            </div>
            <div class="modal-body" id="modal_div">
                <%@include file="form.jsp" %>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#fixListTable').bootgrid();

        $("#myTab a").on("click", function (e) {
            e.preventDefault();
            preview(1);
            $(this).tab('show');
        })


    });


    function finish(id) {
        var fixDesc = $("#fixDesc" + id).val();
        if (!fixDesc) {
            showMessageBox("danger", "请输入维修描述!");
            $("#fixDesc" + id).focus();
            return;
        }
        var url = "/workOrderFix/finishDetail";
        $.post(url, {fixId: id, fixDesc: fixDesc}, function (data) {
            (data.result) ? showMessageBox("info", data.resultDesc) : showMessageBox("danger", data.resultDesc);
        });

    }


    function pause(id) {
        var fixDesc = $("#fixDesc" + id).val();
        if (!fixDesc) {
            $("#fixDesc" + id).focus();
            return;
        }
        var url = "/workOrderFix/pauseDetail";
        $.post(url, {fixId: id, fixDesc: fixDesc}, function (data) {
            (data.result) ? showMessageBox("info", data.resultDesc) : showMessageBox("danger", data.resultDesc);
        });

    }


    /**
     * 全选 全不选
     * @param obj
     */
    function checkAll(obj) {
        var checkName = $(obj).attr("name");
        $("input[name^='" + checkName + "']").prop('checked', $(obj).prop('checked'));
    }


    /**
     * 生成维修单
     */
    function generateFixRpt() {
        var orderReportList = new Array();
        $("input[name^='check']").each(function () {
            var value = $(this).val();
            if ($(this).is(":checked") && !isNaN(value)) {
                orderReportList.push(value);
            } else {
                orderReportList.push(null);
            }

        });
        var ids = orderReportList.join(",");
        var url = "/workOrderReport/mapByType";
        $.post(url, {ids: ids}, function (data) {
            if (data) {
                showMessageBox("info", "维修报告单已生成!");
            }
        });
    }


    /**
     *
     * @param id 预览
     */
    function preview(id) {
        PDFObject.embed("/report/report.pdf", "#pdf_view",
                {
                    width: "100%",
                    height: "750px"
                }
        )
        ;
    }

</script>
