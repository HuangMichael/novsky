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
                            <h4><i class="fa fa-users"></i>用户信息</h4>
                        </div>

                        <div class="box-body" style="padding: 5px 20px 5px 5px">
                            <!-- Split button -->
                            <div class="btn-group">

                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="loadNew()">
                                    <i class="glyphicon glyphicon-plus"></i>新建记录
                                </button>
                                <button type="button" class="btn btn-sm myNavBtn active" onclick="editEq()">
                                    <i class="glyphicon glyphicon-edit"></i>编辑记录
                                </button>

                                <button type="button" class="btn btn-sm myNavBtn active" onclick="saveEq()">
                                    <i class="glyphicon glyphicon-save"></i>保存记录
                                </button>

                                <button type="button" class="btn btn-sm myNavBtn active" onclick="deleteEq()">
                                    <i class="glyphicon glyphicon-remove"></i>删除记录
                                </button>

                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="backwards()"><i
                                        class="glyphicon glyphicon-glyphicon glyphicon-backward"></i>上一条
                                </button>
                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="forwards()"><i
                                        class="glyphicon glyphicon-glyphicon glyphicon-forward"></i>下一条
                                </button>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-sm myNavBtn dropdown-toggle active"
                                            data-toggle="dropdown">
                                        操作菜单 <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a onclick="deleteEq()" class="optionMenu"> <i
                                                class="glyphicon glyphicon-remove"></i>删除记录</a></li>
                                        <li class="divider"></li>
                                        <li><a onclick="abandonEq()" class="optionMenu"> <i
                                                class="glyphicon glyphicon-question-sign"></i>设备报废</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active"><a href="#tab_1_0" data-toggle="tab"
                                                          style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-home" id="eq"></i>用户信息</a>
                                    </li>
                                    <li><a href="#tab_1_1" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-flag" id="eqDetail"></i>用户详细信息</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab_1_0">
                                        <table id="userDataTable"
                                               class=" table table-striped table-bordered table-hover">
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
                                                    <td> ${user.location}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                            <tfoot>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_1" style="padding: 20px">
                                         <%@include file="create.jsp" %>
                                    </div>
                                </div>
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
<%@include file="../common/common-foot.jsp" %>
<script type="text/javascript" src="/js/app/user/user.js"></script>


