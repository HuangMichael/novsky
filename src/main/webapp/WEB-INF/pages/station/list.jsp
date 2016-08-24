<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /SAMPLE BOX CONFIGURATION MODAL FORM-->
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
                            <h4><i class="fa fa-table"></i>车站信息</h4>
                        </div>
                        <div class="box-body">
                            <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                   class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th data-column-id="id">序号</th>
                                    <th data-column-id="stationNo">编号</th>
                                    <th data-column-id="description">线路</th>
                                    <th data-column-id="line-description">车站</th>
                                    <th data-column-id="status">启用状态</th>
                                    <th data-column-id="sortNo">排序</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${stationList}" var="station" varStatus="s">
                                    <tr class="gradeX">
                                        <td class="center">${s.index+1}</td>
                                        <td class="center">${station.stationNo}</td>
                                        <td class="center">${station.line.description}</td>
                                        <td class="center">${station.description}</td>
                                        <td>
                                            <c:if test="${station.status=='1'}"> <span
                                                class="badge badge-green">启用</span></td>
                                        </c:if>
                                        <c:if test="${station.status!='1'}"> <span
                                                class="badge badge-red">禁用</span></td></c:if>
                                        </td>
                                        <td class="center">${station.sortNo}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
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
<script>
    jQuery(document).ready(function () {
        /*App.setPage("dynamic_table");  //Set current page
         App.init(); //Initialise plugins and elements*/

        $("#datatable1").bootgrid();

        $("#saveBtn").on("click", function () {
            var userName = $("#userName").val();
            var sortNo = $("#sortNo").val();
            var status = $("#status  option:selected").val();
            var url = "/user/save";
            var user = {userName: userName, sortNo: sortNo, status: status};
            $.ajax({
                type: "post",
                url: url,
                data: user,
                dataType: "json",
                success: function (data) {
                    $.bootstrapGrowl("用户信息添加失败！", {
                        type: 'danger',
                        align: 'right',
                        stackup_spacing: 30
                    });

                }, error: function (data) {
                    $.bootstrapGrowl("用户信息添加失败！", {
                        type: 'danger',
                        align: 'right',
                        stackup_spacing: 30
                    });
                }
            });
        });


    });
</script>

