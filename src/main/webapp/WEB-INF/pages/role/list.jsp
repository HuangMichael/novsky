<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="createRole_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">新建角色</h4>
            </div>
            <div class="modal-body">
                <form id="productCreateForm">
                    <div class="form-group">
                        <label for="roleName">角色名</label>
                        <input type="text" class="form-control"
                               id="roleName"
                               name="roleName">
                    </div>
                    <div class="form-group">
                        <label for="roleName">角色描述</label>
                        <input type="text" class="form-control"
                               id="roleDesc"
                               name="roleDesc">
                    </div>
                    <div class="form-group">
                        <label for="sortNo">排序</label>
                        <input type="text" class="form-control" id="sortNo" name="sortNo">
                    </div>
                    <div class="form-group">
                        <label for="status">角色状态</label>
                        <select class="form-control" id="status" name="status">
                            <option value="0">禁用</option>
                            <option value="1">启用</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn" onclick="save()">保存</button>
            </div>
        </div>
    </div>
</div>
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
                            <h4><i class="fa fa-table"></i>角色信息</h4>
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
                                       class=" table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th data-column-id="id">序号</th>
                                        <th data-column-id="roleName">角色名</th>
                                        <th data-column-id="roleDesc">角色描述</th>
                                        <th data-column-id="status"> 使用状态</th>
                                        <th data-column-id="sortNo">排序</th>
                                        <%-- <th>编辑</th>
                                         <th>删除</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${roleList}" var="role" varStatus="s">
                                        <tr class="gradeX">
                                            <td>${s.index+1}</td>
                                            <td>
                                                <a onclick="showRole(${role.id})"> ${role.roleName}</a>
                                            </td>
                                            <td>
                                                    ${role.roleDesc}
                                            </td>
                                            <td class="center">${role.status}</td>
                                            <td class="center">${role.sortNo}</td>
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
    $(function () {
        /* App.setPage("dynamic_table");  //Set current page
         App.init(); //Initialise plugins and elements
         */

        $("#datatable1").bootgrid();

        //点击显示角色详细信息

    });


    function save() {

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
                $.bootstrapGrowl("角色信息添加成功！", {
                    type: 'info',
                    align: 'right',
                    stackup_spacing: 30
                });

            }, error: function (data) {
                $.bootstrapGrowl("角色信息添加失败！", {
                    type: 'danger',
                    align: 'right',
                    stackup_spacing: 30
                });
            }
        });
    }

    function showRole(roleId) {
        var url = "/role/detail/" + roleId;
        $("#contentDiv").load(url, function () {

        });
    }
</script>

