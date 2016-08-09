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
                            <h4><i class="fa fa-table"></i>工单信息</h4>
                        </div>
                        <div class="box-body">
                            <a class="btn btn-default" href="#share-workOrder" data-toggle="modal"
                               class="config">
                                分配工单
                            </a>
                            <a class="btn btn-default" id="finishWorkOrderBtn"
                               class="config">
                                确认完工
                            </a>
                            <a class="btn btn-default" href="#assess-workOrder" data-toggle="modal"
                               class="config">
                                维修评价
                            </a>
                            <a class="btn btn-default" href="#add-cons">
                                添加耗材
                            </a>
                            <%-- <a class="btn btn-default" id="showSearch">
                                 高级搜索
                             </a>--%>

                            <div class="divide-10"></div>
                            <div class="box border blue" id="searchBar" style="padding-left:5px;display:none">
                                <div class="box-body">
                                    <a class="btn btn-default" id="searchBtn">查询</a>
                                </div>
                            </div>
                            <div id="resultListDiv">


                                <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                       class=" table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="hidden-xs hidden-sm" data-column-id="id">序号</th>
                                        <th data-column-id="line">线路</th>
                                        <th data-column-id="station">车站</th>
                                        <th data-column-id="locations">设备位置</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="equipmentsClassification">设备分类
                                        </th>
                                        <th data-column-id="orderDesc">故障描述</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="reporter">报修人</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="telephone">报修人电话</th>
                                        <th class="hidden-xs hidden-sm" data-column-id="reportTime">报告时间</th>
                                        <th data-column-id="status">状态</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${workOrderList}" var="workOrder" varStatus="w">
                                        <tr>
                                            <td class="hidden-xs hidden-sm">${w.index+1}</td>
                                            <td>${workOrder.locations.line.description}</td>
                                            <td>${workOrder.locations.station.description}</td>
                                            <td>${workOrder.locations.description}</td>
                                            <td class="hidden-xs hidden-sm">${workOrder.equipmentsClassification.description}</td>
                                            <td>${workOrder.orderDesc}</td>
                                            <td class="hidden-xs hidden-sm">${workOrder.reporter}</td>
                                            <td class="hidden-xs hidden-sm">${workOrder.reportTelephone}</td>
                                            <td class="hidden-xs hidden-sm"><fmt:formatDate
                                                    value="${workOrder.reportTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td>
                                                <c:if test="${workOrder.status=='0'}"> <span
                                                        class="badge badge-red">已报修</span>
                                                </c:if>
                                                <c:if test="${workOrder.status=='1'}"> <span
                                                        class="badge badge-green">维修中</span></c:if>
                                                <c:if test="${workOrder.status=='2'}"> <span
                                                        class="badge badge-success">已完工</span></c:if>
                                                <c:if test="${workOrder.status=='3'}"> <span
                                                        class="badge badge-orange">已评价</span></c:if>
                                            </td>
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

<div class="modal fade" id="share-workOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">工单分配</h4>
            </div>
            <div class="modal-body">
                <%@include file="fix_paper.jsp" %>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="finish-workOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">工单分配</h4>
            </div>
            <div class="modal-body">
                <%@include file="fix_paper.jsp" %>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="assess-workOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">维修评价</h4>
            </div>
            <div class="modal-body">
                <%@include file="assessForm.jsp" %>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="search-workOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">工单搜索</h4>
            </div>
            <div class="modal-body">
                <%@include file="../common/common-searchbar.jsp" %>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/common-foot.jsp" %>
<script type="text/javascript" src="js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/app/common/common-date-utils.js"></script>
<script type="text/javascript" src="js/bootstrap-growl/jquery.bootstrap-growl.min.js"></script>
<script type="text/javascript" src="js/app/common/common-show-message.js"></script>
<script type="text/javascript" src="js/app/common/common-tree-utils.js"></script>
<script type="text/javascript" src="js/app/common/common-form-utils.js"></script>
<script type="text/javascript" src="js/jquery.bootgrid-1.3.1/jquery.bootgrid.min.js"></script>
<script type="text/javascript" src="js/terebentina-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="js/app/workOrder/workOrder.js"></script>
<script type="text/javascript">
    $(function () {
        $("#datatable1").bootgrid();
    });

</script>

