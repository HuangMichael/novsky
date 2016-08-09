<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/common-head.jsp" %>


<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="modal fade" id="user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                            <h4><i class="fa fa-table"></i>用户信息</h4>
                        </div>
                        <div class="box-body">
                            <a class="btn btn-default btn-xs" href="#createUser_modal" data-toggle="modal">

                                <i class="glyphicon glyphicon-plus"></i>
                                新建记录
                            </a>
                            <table id="datatable1" class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th data-column-id="id">序号</th>
                                    <th data-column-id="userName">用户名</th>
                                    <th data-column-id="person">人员</th>
                                    <th data-column-id="status">使用状态</th>
                                    <th data-column-id="location">位置</th>
                                    <th data-column-id="edit" data-formatter="edit">编辑</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${userList}" var="user" varStatus="s">
                                    <tr class="gradeX">
                                        <td>${s.index+1}</td>
                                        <td> ${user.userName}</td>
                                        <td> ${user.person.personName}</td>
                                        <td class="center"><c:if test="${user.status=='1'}">启用
                                        </c:if>
                                            <c:if test="${user.status!='1'}">禁用
                                        </td>
                                        </c:if>
                                        <td> ${user.location} </td>

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
<%@include file="../common/common-foot.jsp" %>

<script>
    $(document).ready(function () {
        $("#datatable1").bootgrid({
                    formatters: {
                        "edit": function (column, row) {
                            var conId = row.id;
                            return '<a class="btn btn-default btn-xs" onclick ="edit(' + conId + ')" title="编辑"><i class="glyphicon glyphicon-edit"></i></i>'
                        }
                    }
                }
        );
    });
    function save() {
        var url = "/user/save";
        var str = getFormJsonData("userForm");
        var obj = JSON.parse(str);
        $.post(url, {obj: obj}, function (data) {
            if (data.result) {
                $("#createUser_modal").modal("hide");
                showMessageBox("info", data["resultDesc"]);
            } else {
                showMessageBox("danger", data["resultDesc"]);
            }
        });
    }


    function edit(id) {
        $("#user_modal").modal("show");
    }
</script>

