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
                <%-- <%@include file="form.jsp" %>--%>
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
                                <button type="button" class="btn btn-sm myNavBtn active" onclick="edit()">
                                    <i class="glyphicon glyphicon-edit"></i>编辑记录
                                </button>

                                <button type="button" class="btn btn-sm myNavBtn active" onclick="saveUser()">
                                    <i class="glyphicon glyphicon-save"></i>保存记录
                                </button>
                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="backwards()"><i
                                        class="glyphicon glyphicon-glyphicon glyphicon-backward"></i>上一条
                                </button>
                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="forwards()"><i
                                        class="glyphicon glyphicon-glyphicon glyphicon-forward"></i>下一条
                                </button>
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
                                                <th data-column-id="index">序号</th>
                                                <th data-column-id="id" data-type="numeric" data-identifier="true"
                                                    data-visible="false">ID
                                                </th>
                                                <th data-column-id="userName">用户名</th>
                                                <th data-column-id="person">人员</th>
                                                <th data-column-id="location">位置</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="gradeX" data-rowId="{{user.id}}" id="tr{{user.id}}"
                                                v-for="user in users">
                                                <td>{{$index+1}}</td>
                                                <td> {{user.id}}</td>
                                                <td> {{user.userName}}</td>
                                                <td> {{user.person.personName}}</td>
                                                <td>{{user.vlocations.locName}}</td>
                                            </tr>
                                            </tbody>
                                            <tfoot>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_1" style="padding: 20px">
                                        <%@include file="detail.jsp" %>
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


