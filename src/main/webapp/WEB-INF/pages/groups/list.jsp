<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="createGroup_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">新建用户</h4>
            </div>
            <div class="modal-body">
                <%@include file="form.jsp" %>
            </div>

        </div>
    </div>
</div>

<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="modal fade" id="addUsers_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <%@include file="../user/simple-user-list.jsp" %>
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
                            <h4><i class="fa fa-table"></i>用户组信息</h4>

                            <div class="tools hidden-xs">
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
                        <div class="box-body">
                            <a class="btn btn-default btn-mini ">信息列表
                            </a>
                            <a class="btn btn-default btn-mini " href="#createGroup_modal" data-toggle="modal"
                               class="config">
                                新建记录
                            </a>

                            <a class="btn btn-default btn-mini " href="#addUsers_modal" data-toggle="modal"
                               class="config">
                                添加用户
                            </a>
                            <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                   class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th data-column-id="id">选择</th>
                                    <th data-column-id="id">序号</th>
                                    <th data-column-id="groupName">用户组名</th>
                                    <th data-column-id="description">组描述</th>
                                    <th data-column-id="users">组用户</th>
                                    <th data-column-id="status">状态</th>
                                    <th data-column-id="status" data-formatter="addUser">添加人员</th>
                                    <%--  <th data-column-id="sortNo">排序</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${groupsList}" var="groups" varStatus="s">
                                    <tr class="gradeX">
                                        <td><input type="checkbox" name="selGroup" data-groupId="${groups.id}"/></td>
                                        <td>${groups.id}</td>
                                        <td class="center">${groups.groupName}</td>
                                        <td class="center">${groups.description}</td>
                                        <td class="center">
                                            <c:forEach items="${groups.users}" var="gu">
                                                <a>${gu.userName}</a> &nbsp;
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:if test="${groups.status=='1'}"> <span
                                                class="badge badge-green">启用</span></td>
                                        </c:if>
                                        <c:if test="${groups.status!='1'}"> <span
                                                class="badge badge-red">禁用</span></td></c:if>
                                        </td>
                                            <%-- <td class="center">${groups.sortNo}</td>--%>
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
<script type="text/javascript" src="js/app/common/common-form-utils.js"></script>
<script type="text/javascript" src="js/app/common/common-show-message.js"></script>

<script>
    $(document).ready(function () {
        $("#datatable1").bootgrid({
            formatters: {
                "addUser": function (column, row) {
                    var conId = row.id;
                    return '<a class="btn btn-default btn-xs" onclick ="addUser(' + conId + ')">添加用户</a>';
                }
            }
        });
    });


    function save() {
        var jsonStr = getFormJsonData("groupForm");
        var groups = JSON.parse(jsonStr);
        var url = "/groups/save";
        $.post(url, groups,
                function (data) {
                    if (data) {
                        $("#createGroup_modal").modal("hide");
                        $.bootstrapGrowl("用户组信息添加成功！", {
                            type: 'info',
                            align: 'right',
                            stackup_spacing: 30
                        });
                    } else {
                        $.bootstrapGrowl("用户组信息添加失败！", {
                            type: 'danger',
                            align: 'right',
                            stackup_spacing: 30
                        });
                    }
                });
    }


    function addUser(conId) {

        groupIdStrArray = conId;
        $("#addUsers_modal").modal("show");

    }
    var groupIdStrArray = "";
    $("[name='selGroup']:checkbox").on("click", function () {
        if ($(this).is(":checked")) {
            groupIdStrArray = $(this).attr("data-groupId");
            $(':checkbox[name=selGroup]').removeAttr('checked');
            $(this).attr('checked', 'checked');

        }
        console.log("选中的值groupId--------------------" + groupIdStrArray);
    })
</script>

