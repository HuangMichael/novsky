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
                                                <i class="fa fa-home" id="eq"></i>维修单查询</a>
                                            </li>

                                            <li><a href="#pdf_view" data-toggle="tab">
                                                <i class="fa fa-flag" id="pdf-preview"></i>维修单预览</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="tab_1_0">
                                                <table id="fixListTable"
                                                       class="table table-striped table-bordered table-hover  table-responsive">
                                                    <thead>
                                                    <tr>
                                                        <th data-column-id="index" width="5%">序号</th>
                                                        <th data-column-id="id" data-visible="false" width="10%">跟踪号
                                                        </th>
                                                        <th data-column-id="orderLineNo" width="10%">跟踪号</th>
                                                        <th data-column-id="location" width="30%">设备位置</th>
                                                        <th data-column-id="eqName" width="10%">设备名称</th>
                                                        <th data-column-id="eqDesc" width="15%">故障描述</th>
                                                        <th data-column-id="eqClass" width="10%">设备分类</th>
                                                        <th data-column-id="status" width="5%">设备状态</th>
                                                        <th data-column-id="fixDesc" style="height:20px" width="20%">
                                                            维修描述
                                                        </th>
                                                        <th data-column-id="reportTime" width="5%">处理时间</th>
                                                        <th data-column-id="opMenus" data-formatter="opMenus"
                                                            data-sortable="false" width="5%">暂停&nbsp;取消&nbsp;完工
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${workOrderFixDetailListList}" var="d" varStatus="ds">
                                                        <tr style="display: none;" id="tr-${d.id}">
                                                            <td>${ds.index+1}</td>
                                                            <td>${d.id}</td>
                                                            <td>${d.orderLineNo}</td>
                                                            <td>${d.vlocations.locName}</td>
                                                            <td>${d.equipments.description}</td>
                                                            <td>${d.orderDesc}</td>
                                                            <td>${d.equipmentsClassification.description}</td>
                                                            <td>
                                                                <c:if test="${d.status=='0'}">
                                                                    <span class="badge badge-info">已分配</span>
                                                                </c:if>
                                                                <c:if test="${d.status=='1'}">
                                                                    <span class="badge badge-success" >已完工</span>
                                                                </c:if>
                                                                <c:if test="${d.status=='2'}">
                                                                        <span class="badge badge-important">已暂停</span>
                                                                </c:if>
                                                                <c:if test="${d.status=='3'}">
                                                                        <span class="badge badge-important"> 已取消</span>
                                                                </c:if>
                                                            </td>
                                                            <td>${d.fixDesc}</td>
                                                            <td><fmt:formatDate value="${d.reportTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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


<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<script type="text/javascript" src="js/app/fix/fix.js"></script>
