<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="form.jsp" %>
<%--
<div class="box border blue">
    <div class="box-title">
        <h4><i class="fa fa-table"></i>工单信息</h4>

        <div class="tools">
            <a href="#box-config" data-toggle="modal" class="config">
                <i class="fa fa-cog"></i>
            </a>
            <a href="javascript:" class="reload">
                <i class="fa fa-refresh"></i>
            </a>
            <a href="javascript:" class="collapse">
                <i class="fa fa-chevron-up"></i>
            </a>
            <a href="javascript:" class="remove">
                <i class="fa fa-times"></i>
            </a>
        </div>
    </div>
</div>
<div class="box-body">
    <div class="tabbable">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab_2_0" data-toggle="tab"><i
                    class="fa fa-envelope"></i> 设备信息</a>
            </li>
            <li><a href="#tab_1_2" data-toggle="tab"><i
                    class="fa fa-envelope"></i> 技术参数</a></li>
            <li><a href="#tab_1_3" data-toggle="tab">工单
                <c:if test="${locations.workOrderList.size()!=0}">
                    <span class="badge badge-red">${locations.workOrderList.size()}</span>
                </c:if>
            </a></li>
            <li><a href="#tab_1_4" data-toggle="tab">预防性维护</a></li>
            <li><a href="#tab_1_5" data-toggle="tab">设备历史</a></li>
            <li><a href="#tab_2_1" data-toggle="tab"><i
                    class="fa fa-home"></i> 设备参数</a></li>
            <li><a href="#tab_2_2" data-toggle="tab"><i
                    class="fa fa-envelope"></i> 设备备件</a></li>
            <li><a href="#tab_2_3" data-toggle="tab">部件及附属</a></li>
            <li><a href="#tab_2_4" data-toggle="tab">维修工单</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active " id="tab_2_0">
                &lt;%&ndash;  <div class="divide-10"></div>&ndash;%&gt;
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>设备名称</th>
                        <th>设备分类名称</th>
                        <th>使用状态</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${locations.equipmentsList}" var="equipment" varStatus="s">
                        <tr class="gradeX">
                            <td>${s.index+1}</td>
                            <td>
                                    ${equipment.description}
                            </td>
                            <td>
                                    ${equipment.equipmentsClassification.description}
                            </td>
                            <td>
                                <c:if test="${equipment.status=='1'}"> <span
                                        class="badge badge-green">启用</span></c:if>
                                <c:if test="${equipment.status=='0'}"> <span
                                        class="badge badge-red">禁用</span></c:if>
                            </td>
                            <td><a href="#">删除</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    </tfoot>
                </table>
            </div>
            <div class="tab-pane " id="tab_1_2">
                &lt;%&ndash;  <div class="divide-10"></div>&ndash;%&gt;
                <table class="table table-striped table-bordered table-responsive table-condensed"
                       id="table1">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>分类</th>
                        <th>参数</th>
                        <th>参数值</th>
                        <th>单位</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${locations.workOrderList}" var="workOrder" varStatus="w">
                        <tr>
                            <td>${w.index+1}</td>
                            <td>${workOrder.orderNo}</td>
                            <td>${workOrder.orderDesc}</td>
                            <td>${workOrder.sortNo}</td>
                            <td>${workOrder.status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_1_3">
                &lt;%&ndash;   <div class="divide-10"></div>&ndash;%&gt;
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>工单编号</th>
                        <th>工单描述</th>
                        <th>状态</th>
                        <th>报告人</th>
                        <th>报告时间</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${locations.workOrderList}" var="workOrder" varStatus="w">
                        <tr>
                            <td>${w.index+1}</td>
                            <td>${workOrder.orderNo}</td>
                            <td>${workOrder.orderDesc}</td>
                            <td>${workOrder.status}</td>
                            <td>${workOrder.reporter}</td>
                            <td>${workOrder.reportTime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_1_4">
                &lt;%&ndash;  <div class="divide-10"></div>&ndash;%&gt;
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>分类</th>
                        <th>参数</th>
                        <th>参数值</th>
                        <th>单位</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>空调设备</td>
                        <td>功率</td>
                        <td>2000w</td>
                        <td>瓦</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>照明设备</td>
                        <td>功率</td>
                        <td>1000w</td>
                        <td>瓦</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>除尘设备</td>
                        <td>功率</td>
                        <td>2200w</td>
                        <td>瓦</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_1_5">
                &lt;%&ndash;   <div class="divide-10"></div>&ndash;%&gt;
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>工单编号</th>
                        <th>描述</th>
                        <th>状态</th>
                        <th>状态时间</th>
                        <th>设备</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>201603150012</td>
                        <td>照明设备维修</td>
                        <td>已启动</td>
                        <td>2016-03-15 08:00:56</td>
                        <td>zm12322101</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>201603150012</td>
                        <td>照明设备维修</td>
                        <td>已启动</td>
                        <td>2016-03-15 08:00:56</td>
                        <td>zm12322101</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>201603150012</td>
                        <td>照明设备维修</td>
                        <td>已启动</td>
                        <td>2016-03-15 08:00:56</td>
                        <td>zm12322101</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_2_1">
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>分类</th>
                        <th>参数</th>
                        <th>参数值</th>
                        <th>单位</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>空调设备</td>
                        <td>额定功率</td>
                        <td>2000</td>
                        <td>w</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>空调设备</td>
                        <td>额定电压</td>
                        <td>200</td>
                        <td>v</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>空调设备</td>
                        <td>工作温度</td>
                        <td>-30-50</td>
                        <td>℃</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_2_2">
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>备件编号</th>
                        <th>描述</th>
                        <th>数量</th>
                        <th>库存余量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>KT22123</td>
                        <td>中央空调通风管</td>
                        <td>2</td>
                        <td>26</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>KT22128</td>
                        <td>中央空调风扇</td>
                        <td>1</td>
                        <td>32</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>KT22143</td>
                        <td>中央空调螺丝帽</td>
                        <td>24</td>
                        <td>426</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_2_3">
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>设备</th>
                        <th>描述</th>
                        <th>位置</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调01</td>
                        <td>会议室001西北角</td>
                        <td><span class="badge badge-green">启用</span></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调02</td>
                        <td>会议室001西南角</td>
                        <td><span class="badge badge-green">启用</span></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调03</td>
                        <td>会议室001东墙</td>
                        <td><span class="badge badge-red">停用</span></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab-pane " id="tab_2_4">
                <div class="divide-10"></div>
                <table class="table table-striped table-bordered table-responsive table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>工单编号</th>
                        <th>设备</th>
                        <th>描述</th>
                        <th>状态</th>
                        <th>状态时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>201603150012</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调01</td>
                        <td><span class="badge badge-green">已启动</span></td>
                        <th>2016-03-16 12:58:09</th>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>201603150014</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调02</td>
                        <td><span class="badge badge-red">已结束</span></td>
                        <td>2016-03-16 12:08:09</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>201603150015</td>
                        <td>中央空调</td>
                        <td>会议室001中央空调03</td>
                        <td><span class="badge badge-green">已启动</span></td>
                        <td>2016-03-18 12:18:09</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>--%>
