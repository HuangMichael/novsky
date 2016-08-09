<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//cdn.bootcss.com/jquery-bootgrid/1.3.1/jquery.bootgrid.css" rel="stylesheet">
<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="modal fade" id="createRole_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">新建数据过滤器</h4>
            </div>
            <div class="modal-body">
                <%-- <%@include file="form.jsp" %>--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- /SAMPLE BOX CONFIGURATION foMODAL FORM-->
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
                            <h4><i class="fa fa-table"></i>数据过滤器信息</h4>
                        </div>
                        <div class="box-body">

                            <div id="contentDiv">
                                <a class="btn btn-default btn-mini navbar-btn">信息列表
                                </a>
                                <a class="btn btn-default btn-mini navbar-btn" href="#createRole_modal"
                                   data-toggle="modal"
                                   class="config">
                                    新建记录
                                </a>
                                <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                       class="datatable table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th data-column-id="id">序号</th>
                                        <th data-column-id="description">数据过滤器名</th>
                                        <th data-column-id="entityName">数据过滤器描述</th>
                                        <th data-column-id="status">使用状态</th>
                                        <th data-column-id="sortNo">排序</th>
                                        <%-- <th>编辑</th>
                                         <th>删除</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${dataFilterList}" var="dataFilter" varStatus="s">
                                        <tr class="gradeX">
                                            <td>${s.index+1}</td>
                                            <td>
                                                    ${dataFilter.description}
                                            </td>
                                            <td>
                                                    ${dataFilter.entityName}
                                            </td>
                                            <td class="center">

                                                <c:if test="${dataFilter.status=='1'}"> <span
                                                    class="badge badge-green">启用</span></td>
                                            </c:if>
                                            <c:if test="${dataFilter.status!='1'}"> <span
                                                    class="badge badge-red">禁用</span></td></c:if>

                                            </td>
                                            <td class="center">${dataFilter.sortNo}</td>
                                                <%--<td class="center"><a href="#">编辑</a></td>
                                                <td class="center"><a href="#">删除</a></td>--%>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    </tfoot>
                                </table>
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
<script>
    $(document).ready(function () {
        $("#datatable1").bootgrid();
        $("#saveBtn").on("click", function () {
            var roleName = $("#roleName").val();
            var roleDesc = $("#roleDesc").val();
            var sortNo = $("#sortNo").val();
            var status = $("#status  option:selected").val();
            var url = "/role/save";
            var role = {roleName: roleName, roleDesc: roleDesc, sortNo: sortNo, status: status};
            $.ajax({
                type: "post",
                url: url,
                data: role,
                dataType: "json",
                success: function (data) {
                    $("#createRole_modal").modal("hide");
                    $.bootstrapGrowl("数据过滤器信息添加成功！", {
                        type: 'info',
                        align: 'right',
                        stackup_spacing: 30
                    });

                }, error: function (data) {
                    $.bootstrapGrowl("数据过滤器信息添加失败！", {
                        type: 'danger',
                        align: 'right',
                        stackup_spacing: 30
                    });
                }
            });
        });


        //点击显示数据过滤器详细信息

    });

    function showRole(roleId) {
        var url = "/role/detail/" + roleId;
        $("#contentDiv").load(url, function () {
            console.log("页面载入成功-------------------");
        });
    }
</script>

