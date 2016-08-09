<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!-- /SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="container" id="equipmentsApp">
    <div class="row">
        <div id="content" class="col-lg-12">
            <!-- PAGE HEADER-->
            <%@include file="../common/common-breadcrumb.jsp" %>
            <div class="row">
                <div class="col-md-12">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-title">
                            <h4 class="appTitle"><i class="fa fa-sitemap"></i>设备信息</h4>
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
                                        <li><a onclick="deleteEq()" class="optionMenu"> <i class="glyphicon glyphicon-remove"></i>删除记录</a></li>
                                        <li class="divider"></li>
                                        <li><a onclick="abandonEq()" class="optionMenu"> <i class="glyphicon glyphicon-question-sign"></i>设备报废</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active"><a href="#tab_1_0" data-toggle="tab"
                                                          style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-home" id="eq"></i>设备信息</a>
                                    </li>

                                    <li><a href="#tab_1_1" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-flag" id="eqDetail"></i>设备详细信息</a>
                                    </li>
                                    <li><a href="#tab_1_3" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold"><i class="fa fa-lock"
                                                                                          id="history"></i>维修历史信息</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab_1_0"
                                         style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
                                        <%@include file="table_1_0.jsp" %>
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_1">
                                        <%@include file="table_1_1.jsp" %>
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_3">
                                        <%@include file="table_1_3.jsp" %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../common/common-back2top.jsp" %>
</div>


<div class="modal fade " id="show_eq_modal" tabindex="-1" back-drop="false"
     role="dialog" aria-labelledby="fix_work_order">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fix_work_order">该报修流程还未完工,要继续报修么?</h4>
            </div>
            <div class="modal-body">
                <%@include file="../location/reportedEqList.jsp" %>
            </div>
        </div>
    </div>
</div>

<div class="modal fade " id="track_eq_modal" tabindex="-1" back-drop="false"
     role="dialog" aria-labelledby="fix_work_order">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="track_work_order">查看当前设备维修进度</h4>
            </div>
            <div class="modal-body" id="fix-progress">
                <%@include file="table_1_2.jsp" %>
            </div>
        </div>
    </div>
</div>


<div class="modal fade " id="show_history_modal" tabindex="-1" back-drop="false"
     role="dialog" aria-labelledby="fix_work_order">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="history_work_order">查看维修历史</h4>
            </div>
            <div class="modal-body" id="fix-history">

            </div>
        </div>
    </div>
</div>


<script src="/js/app/equipment/equipments.js"></script>