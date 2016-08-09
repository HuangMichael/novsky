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
        <div class="box-body">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active"><a data-toggle="tab" href="#tab_1_1">设备信息
                    <span class="badge badge-green" title="所有设备数量">${equipmentsList.size()}</span>
                    <%-- <span class="badge badge-red" title="维修设备数量">${fixingEqsList.size()}</span>--%>
                </a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active " id="tab_1_1">
                    <table id="eqtables" cellpadding="0" cellspacing="0" border="0"
                           class=" table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th data-column-id="index">序号</th>
                            <th data-column-id="id" data-visible="false">序号</th>
                            <th data-column-id="eqCode">设备编号</th>
                            <th data-column-id="eqName">设备名称</th>
                            <th data-column-id="locName">设备位置</th>
                            <th data-column-id="status">设备状态</th>
                            <th data-column-id="running">运行状态</th>
                            <th data-column-id="report" data-formatter="report">设备报修</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${equipmentsList}" var="equipment" varStatus="s">
                            <tr class="gradeX" data-rowId="${equipment.id}" id="tr${equipment.id}">
                                <td>${s.index+1}</td>
                                <td>${equipment.id}</td>
                                <td>
                                        ${equipment.eqCode}
                                </td>
                                <td>
                                        ${equipment.eqName}
                                </td>
                                <td>
                                        ${equipment.locName}
                                </td>
                                <td>
                                        ${equipment.status}
                                </td>
                                <td>
                                        ${equipment.running}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade " id="show_eq_modal" tabindex="-1" back-drop="false"
     role="dialog" aria-labelledby="fix_work_order">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fix_work_order">该设备已报修还未完工,要继续报修该设备么?</h4>
            </div>
            <div class="modal-body">
                <%@include file="reportedEqList.jsp" %>
            </div>
        </div>
    </div>
</div>
<div class="modal fade " id="show_loc_modal" tabindex="-1" back-drop="false"
     role="dialog" aria-labelledby="fix_work_order">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fix_work_order1">该报修流程还未完工,要继续报修么?</h4>
            </div>
            <div class="modal-body">
                <%@include file="reportedLocList.jsp" %>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/app/locations/location.detail.min.js"></script>
