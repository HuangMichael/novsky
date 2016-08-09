<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="modal fade" id="box-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">新建用户</h4>
            </div>
            <div class="modal-body">
                <form id="productCreateForm">
                    <div class="form-group">
                        <label for="userName">用户名</label>
                        <input type="text" class="form-control"
                               id="userName"
                               name="userName">
                    </div>
                    <div class="form-group">
                        <label for="sortNo">排序</label>
                        <input type="text" class="form-control" id="sortNo" name="sortNo">
                    </div>
                    <div class="form-group">
                        <label for="status">使用状态</label>
                        <select class="form-control" id="status" name="status">
                            <option value="0">禁用</option>
                            <option value="1">启用</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">保存</button>
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
                            <h4><i class="fa fa-table"></i>设备类别信息</h4>

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
                            <a class="btn btn-default btn-mini navbar-btn">信息列表
                            </a>
                            <a class="btn btn-default btn-mini navbar-btn" href="#box-config" data-toggle="modal"
                               class="config">
                                新建记录
                            </a>
                            <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                   class="datatable table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>设备类别名称</th>
                                    <th>使用状态</th>
                                    <th>排序</th>
                                    <th>编辑</th>
                                    <th>删除</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${equipmentTypeList}" var="equipmentType" varStatus="s">
                                    <tr class="gradeX">
                                        <td>${s.index+1}</td>
                                        <td>
                                                ${equipmentType.typeName}
                                        </td>
                                        <td class="center">${equipmentType.status}</td>
                                        <td class="center">${equipmentType.sortNo}</td>
                                        <td class="center"><a href="#">编辑</a></td>
                                        <td class="center"><a href="#">删除</a></td>
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
        /*App.setPage("equipment");  //Set current page
        App.init(); //Initialise plugins and elements*/
    });
</script>

