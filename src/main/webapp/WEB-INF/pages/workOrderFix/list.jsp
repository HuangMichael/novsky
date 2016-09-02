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
                            <h4><i class="fa fa-sitemap"></i>维修单查询</h4>
                        </div>
                        <div class="box-body">
                            <div id="contentDiv">
                                <div class="box-body">
                                    <div class="tabbable">
                                        <ul class="nav nav-tabs" id="myTab">
                                            <li class="active"><a href="#tab_1_0" data-toggle="tab">
                                                <i class="fa fa-user" id="eq"></i>已派工</a>
                                            </li>
                                            <li><a href="#tab_1_1" data-toggle="tab">
                                                <i class="fa fa-home" id="eq1"></i>已完工</a>
                                            </li>
                                            <li><a href="#tab_1_2" data-toggle="tab">
                                                <i class="fa  fa-caret-square-o-right" id="eq2"></i>已暂停</a>
                                            </li>
                                            <li><a href="#tab_1_3" data-toggle="tab">
                                                <i class="fa  fa-remove" id="eq3"></i>已取消</a>
                                            </li>
                                             <li><a href="#report" data-toggle="tab">
                                                                                            <i class="fa  fa-remove" id="a"></i>报表信息</a>
                                                                                        </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="tab_1_0">
                                                <table id="fixListTable"
                                                       class="table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" data-width="3%">序号</th>
                                                        <th data-column-id="id" data-visible="false" data-width="10%">跟踪号
                                                        </th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>

                                                        <th data-column-id="eqName" data-width="7%">设备名称</th>
                                                            <th data-column-id="location" data-width="8%">设备位置</th>
                                                        <th data-column-id="eqDesc" data-width="15%">故障描述</th>
                                                        <th data-column-id="eqClass" data-width="5%">设备分类</th>
                                                        <th data-column-id="status" data-width="5%">设备状态</th>
                                                        <th data-column-id="deadLine" data-width="8%">维修期限</th>
                                                        <th data-column-id="reportTime" data-width="8%">处理时间</th>
                                                        <th data-column-id="opMenus" data-formatter="opMenus"
                                                            data-sortable="false" data-width="8%">暂停&nbsp;取消&nbsp;完工
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${workOrderFixDetailListList0}" var="d"
                                                               varStatus="ds">
                                                        <tr style="display: none;" id="tr-${d.id}">
                                                            <td>${ds.index+1}</td>
                                                            <td>${d.id}</td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.orderDesc}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                    ${d.nodeState}
                                                            </td>


                                                            <td><fmt:formatDate value="${d.deadLine}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                            <td><fmt:formatDate value="${d.reportTime}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="tab-pane fade in " id="tab_1_1">
                                                <table id="fixListTable1"
                                                       class="table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" data-width="5%">序号</th>
                                                        <th data-column-id="id" data-visible="false" data-width="10%">跟踪号
                                                        </th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
                                                        <th data-column-id="location" data-width="30%">设备位置</th>
                                                        <th data-column-id="eqName" data-width="10%">设备名称</th>
                                                        <th data-column-id="eqDesc" data-width="15%">故障描述</th>
                                                        <th data-column-id="eqClass" data-width="10%">设备分类</th>
                                                        <th data-column-id="status" data-width="5%">设备状态</th>
                                                        <th data-column-id="fixDesc" style="height:20px" data-width="20%">
                                                            维修描述
                                                        </th>
                                                        <th data-column-id="reportTime" data-width="5%">处理时间</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${workOrderFixDetailListList1}" var="d"
                                                               varStatus="ds">
                                                        <tr style="display: none;" id="tr-${d.id}">
                                                            <td>${ds.index+1}</td>
                                                            <td>${d.id}</td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td>${d.orderDesc}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                    ${d.nodeState}
                                                            </td>
                                                            <td>${d.fixDesc}</td>
                                                            <td><fmt:formatDate value="${d.reportTime}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="tab-pane fade in" id="tab_1_2">
                                                <table id="fixListTable2"
                                                       class="table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" data-width="5%">序号</th>
                                                        <th data-column-id="id" data-visible="false" data-width="10%">跟踪号
                                                        </th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
                                                        <th data-column-id="location" data-width="30%">设备位置</th>
                                                        <th data-column-id="eqName" data-width="10%">设备名称</th>
                                                        <th data-column-id="eqDesc" data-width="15%">故障描述</th>
                                                        <th data-column-id="eqClass" data-width="10%">设备分类</th>
                                                        <th data-column-id="status" data-width="5%">设备状态</th>
                                                        <th data-column-id="fixDesc" style="height:20px" data-width="20%">
                                                            维修描述
                                                        </th>
                                                        <th data-column-id="reportTime" data-width="5%">处理时间</th>
                                                        <th data-column-id="opMenus" data-formatter="opMenus"
                                                            data-sortable="false" data-width="5%">恢复&nbsp;取消&nbsp;完工
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${workOrderFixDetailListList2}" var="d"
                                                               varStatus="ds">
                                                        <tr style="display: none;" id="tr-${d.id}">
                                                            <td>${ds.index+1}</td>
                                                            <td>${d.id}</td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td>${d.orderDesc}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                    ${d.nodeState}
                                                            </td>
                                                            <td>${d.fixDesc}</td>
                                                            <td><fmt:formatDate value="${d.reportTime}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="tab-pane fade in " id="tab_1_3">
                                                <table id="fixListTable3"
                                                       class="table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" data-width="5%">序号</th>
                                                        <th data-column-id="id" data-visible="false" data-width="10%">跟踪号
                                                        </th>
                                                        <th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
                                                        <th data-column-id="location" data-width="30%">设备位置</th>
                                                        <th data-column-id="eqName" data-width="10%">设备名称</th>
                                                        <th data-column-id="eqDesc" data-width="15%">故障描述</th>
                                                        <th data-column-id="eqClass" data-width="10%">设备分类</th>
                                                        <th data-column-id="status" data-width="5%">设备状态</th>
                                                        <th data-column-id="fixDesc" style="height:20px" data-width="20%">
                                                            维修描述
                                                        </th>
                                                        <th data-column-id="reportTime" data-width="5%">处理时间</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${workOrderFixDetailListList3}" var="d"
                                                               varStatus="ds">
                                                        <tr style="display: none;" id="tr-${d.id}">
                                                            <td>${ds.index+1}</td>
                                                            <td>${d.id}</td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td>${d.orderDesc}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                    ${d.nodeState}
                                                            </td>
                                                            <td>${d.fixDesc}</td>
                                                            <td><fmt:formatDate value="${d.reportTime}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>

                                            <div class="tab-pane fade" id="report">
                                             <iframe id="reportFrame" data-width="100%" height="800" src="http://localhost:8080/web/ReportServer?reportlet=/equipment.cpt" style="border: none;"></iframe>
                                             </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../common/common-back2top.jsp" %>
</div>


<div class="modal fade " id="fix_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">维修单明细信息</h4>
            </div>
            <div class="modal-body" id="modal_div">
                <%@include file="form.jsp" %>
            </div>
        </div>
    </div>
</div>


<div class="modal fade " id="fix_desc_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fix_desc_modal_label">请输入维修描述</h4>
            </div>
            <div class="modal-body" id="fix_desc_modal_div">
                <%@include file="fixDescForm.jsp" %>
            </div>
        </div>
    </div>
</div>

<div class="modal fade " id="fix_adjust_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fix_adjust_modal_desc">请选择调整后的时间</h4>
            </div>
            <div class="modal-body" id="fix_adjust_modal_div">
                <%@include file="fixAdujstForm.jsp" %>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<script type="text/javascript" src="js/app/fix/fix.js"></script>
