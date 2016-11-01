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
                        <%@include file="../common/common-menubar.jsp" %>
                        <div class="box-body">
                            <div id="contentDiv">
                                <div class="box-body">
                                    <div class="tabbable">
                                        <ul class="nav nav-tabs" id="myTab">
                                            <li class="active">
                                                <a href="#tab_1_0" data-toggle="tab">
                                                    <i class="fa fa-home" id="eq"></i>报修单信息</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="tab_1_0">

                                                <table id="fixListTable"
                                                       class="table table-bordered table-hover table-striped"
                                                       data-toggle="bootgrid" data-ajax="true"
                                                       data-url="/workOrderReport/data">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="id" data-width="10%">序号</th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
                                                        <th data-column-id="eqName" data-width="10%">设备名称</th>
                                                        <th data-column-id="orderDesc" data-width="20%">故障描述</th>
                                                        <th data-column-id="locName" data-width="10%">设备位置</th>
                                                        <th data-column-id="eqClass" data-width="8%">设备分类</th>
                                                        <th data-column-id="nodeTime" data-width="10%">生成时间</th>
                                                        <th data-column-id="nodeState" data-width="5%">报修状态</th>
                                                    </tr>
                                                    </thead>

                                                </table>
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

<div class="modal fade " id="fix_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">报修车明细信息</h4>
            </div>
            <div class="modal-body" id="modal_div">
                <%@include file="form.jsp" %>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {

        $("#fixListTable").bootgrid();


    });


    /**
     *导出excel
     */
    function exportExcel() {
        var eqName = $("#fixListTable").bootgrid("getSearchPhrase");
        var columnSettings = $("#fixListTable").bootgrid("getColumnSettings");

        var titles = [];
        var colNames = [];
        for (var x in columnSettings) {
            if (columnSettings[x] != undefined && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
                titles[x] = columnSettings[x]["text"];
                colNames[x] = columnSettings[x]["id"];
            }

        }

        var docName = "报修单信息";
        var url = "workOrderReportCart/exportExcel?eqName=" + eqName + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
        bootbox.confirm({
            message: "确定导出查询结果记录么？?",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-success'
                },
                cancel: {
                    label: '否',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    window.location.href = url;
                }
            }
        });

    }


</script>