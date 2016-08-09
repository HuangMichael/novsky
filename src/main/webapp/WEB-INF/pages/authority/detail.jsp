<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box border blue">
    <div class="box-body">
        <%@include file="form.jsp" %>
    </div>
</div>
<div class="box border blue">
    <div class="box-body">
        <div id="contentDiv">
            <div class="box border blue">
                <div class="box-title">
                    <h4><i class="fa fa-table"></i>设备信息</h4>
                </div>
            </div>
            <div class="box-body">
                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab_1_3" data-toggle="tab">已报修工单
                            <c:if test="${workOrderList0.size()!=0}">
                                <span class="badge badge-red">${workOrderList0.size()}</span>
                            </c:if>
                        </a></li>
                        <li><a href="#tab_1_4" data-toggle="tab">维修中工单
                            <c:if test="${workOrderList1.size()!=0}">
                                <span class="badge badge-green">${workOrderList1.size()}</span>
                            </c:if>
                        </a></li>
                        <li><a href="#tab_1_5" data-toggle="tab">已完成工单
                            <c:if test="${workOrderList2.size()!=0}">
                                <span class="badge badge-sucess">${workOrderList2.size()}</span>
                            </c:if>
                        </a></li>
                        <%-- <li><a href="#tab_1_6" data-toggle="tab">已评价工单
                             <c:if test="${workOrderList3.size()!=0}">
                                 <span class="badge badge-yellow">${workOrderList3.size()}</span>
                             </c:if>
                         </a></li>--%>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active " id="tab_1_3">
                            <table class="table table-striped table-bordered table-responsive table-condensed datatable"
                                   id="1table">
                                <thead>
                                <tr>
                                    <th style="width: 5%">序号</th>
                                    <th style="width: 10%">工单编号</th>
                                    <th style="width: 5%">线路</th>
                                    <th style="width: 10%">车站</th>
                                    <th style="width: 10%">位置</th>
                                    <th style="width: 25%">工单描述</th>
                                    <th style="width: 5%">报修人</th>
                                    <th style="width: 10%">报修人电话</th>
                                    <th style="width: 15%">报修时间</th>
                                    <th style="width: 5%">分配</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${workOrderList0}" var="workOrder" varStatus="w">
                                    <tr id="list0-${workOrder.id}">
                                        <td>${w.index+1}</td>
                                        <td>${workOrder.orderNo}</td>
                                        <td>${workOrder.locations.line.description}</td>
                                        <td>${workOrder.locations.station.description}</td>
                                        <td>${workOrder.locations.description}</td>
                                        <td>${workOrder.orderDesc}</td>
                                        <td>${workOrder.reporter}</td>
                                        <td>${workOrder.reportTelephone}</td>
                                        <td><fmt:formatDate value="${workOrder.reportTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                        <td><a class="btn btn-default btn-xs" href="#share_work_order_modal"
                                               data-toggle="modal" onclick="setPid0(${workOrder.id})">分配</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane " id="tab_1_4">
                            <table class="table table-striped table-bordered table-responsive table-condensed simple_page"
                                   id="2table">
                                <thead>
                                <tr>
                                    <th style="width: 5%">序号</th>
                                    <th style="width: 10%">工单编号</th>
                                    <th style="width: 5%">线路</th>
                                    <th style="width: 10%">车站</th>
                                    <th style="width: 10%">位置</th>
                                    <th style="width: 25%">工单描述</th>
                                    <th style="width: 10%">维修单位</th>
                                    <th style="width: 5%">时限</th>
                                    <th style="width: 15%">维修时间</th>
                                    <th style="width: 5%">完工</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${workOrderList1}" var="workOrder" varStatus="w">
                                    <tr id="list1-${workOrder.id}">
                                        <td>${w.index+1}</td>
                                        <td>${workOrder.orderNo}</td>
                                        <td>${workOrder.locations.line.description}</td>
                                        <td>${workOrder.locations.station.description}</td>
                                        <td>${workOrder.locations.description}</td>
                                        <td>${workOrder.orderDesc}</td>
                                        <td>${workOrder.workOrderMaintenance.outsourcingUnit.description}</td>
                                        <td>${workOrder.workOrderMaintenance.limitedHours}</td>
                                        <td><fmt:formatDate value="${workOrder.workOrderMaintenance.beginTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                        <td>
                                            <a type="button" class="btn btn-default btn-xs" id="finishBtn"
                                               onclick="setPid2(${workOrder.id})">完工</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane " id="tab_1_5">
                            <table class="table table-striped table-bordered table-responsive table-condensed simple_page"
                                   id="3table">
                                <thead>
                                <tr>
                                    <th style="width: 5%">序号</th>
                                    <th style="width: 10%">工单编号</th>
                                    <th style="width: 5%">线路</th>
                                    <th style="width: 10%">车站</th>
                                    <th style="width: 10%">位置</th>
                                    <th style="width: 25%">工单描述</th>
                                    <th style="width: 10%">维修单位</th>
                                    <th style="width: 5%">时限</th>
                                    <th style="width: 15%">完成时间</th>
                                    <th style="width: 5%">评价</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${workOrderList2}" var="workOrder" varStatus="w">
                                    <tr id="list2-${workOrder.id}">
                                        <td>${w.index+1}</td>
                                        <td>${workOrder.orderNo}</td>
                                        <td>${workOrder.locations.line.description}</td>
                                        <td>${workOrder.locations.station.description}</td>
                                        <td>${workOrder.locations.description}</td>
                                        <td>${workOrder.orderDesc}</td>
                                        <td>${workOrder.workOrderMaintenance.outsourcingUnit.description}</td>
                                        <td>${workOrder.workOrderMaintenance.limitedHours}</td>
                                        <td><fmt:formatDate value="${workOrder.workOrderMaintenance.endTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                        <td>
                                            <a class="btn btn-default btn-xs" href="#assess-workOrder"
                                               data-toggle="modal"
                                               class="config">
                                                评价
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <%-- <div class="tab-pane " id="tab_1_6">
                             <table class="table table-striped table-bordered table-responsive table-condensed simple_page"
                                    id="4table">
                                 <thead>
                                 <tr>
                                     <th>序号</th>
                                     <th>工单编号</th>
                                     <th>工单描述</th>
                                     <th>状态</th>
                                     <th>完成时间</th>
                                     <th>维修评价</th>
                                 </tr>
                                 </thead>
                                 <tbody>
                                 <c:forEach items="${workOrderList3}" var="workOrder" varStatus="w">
                                     <tr id="list2-${workOrder.id}">
                                         <td>${w.index+1}</td>
                                         <td>${workOrder.orderNo}</td>
                                         <td>${workOrder.orderDesc}</td>
                                         <td>${workOrder.orderDesc}</td>
                                         <td><fmt:formatDate value="${workOrder.workOrderMaintenance.endTime}"
                                                             pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                         <td>
                                             <div id="number-demo" style="cursor: pointer; width: 100px;">
                                                 <c:forEach begin="1"
                                                            end="${workOrder.workOrderMaintenance.assessLevel}">
                                                     <img src="js/jquery-raty/img/star-on.png">
                                                 </c:forEach>
                                             </div>
                                         </td>
                                     </tr>
                                 </c:forEach>
                                 </tbody>
                             </table>
                         </div>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    $(function () {
        var lid = '${locations.id}';
        if (lid == "") {
            return;
        }
        var url = "/locations/findById/" + lid;
        $.getJSON(url, function (data) {
            if (data.line) {
                $("#line_id").val(data.line.id);
            }
            if (data.station) {
                $("#station_id").val(data.station.id);
            }
        })
    })
</script>



