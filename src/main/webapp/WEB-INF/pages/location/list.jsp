<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <h4><i class="fa fa-sitemap"></i>设备位置信息</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-body treeContainer" id="treeDiv">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="box border blue">
                    <%include file='../common/common-menubar.jsp' %>
                        <div class="box-body">
                            <button type="button" class="btn btn-sm myNavBtn active"
                                    onclick="loadCreateForm()"><i
                                    class="glyphicon glyphicon-glyphicon glyphicon-plus"></i>新建记录
                            </button>
                            <button type="button" class="btn btn-sm myNavBtn active"
                                    onclick="save()"><i
                                    class="glyphicon glyphicon-glyphicon glyphicon-floppy-disk"></i>保存记录
                            </button>
                            <button type="button" class="btn btn-sm myNavBtn active"
                                    onclick="deleteObject()"><i
                                    class="glyphicon glyphicon-glyphicon glyphicon-trash"></i>删除记录
                            </button>
                            <button type="button" class="btn btn-sm myNavBtn active"
                                    onclick="reportByLocation()"><i
                                    class="glyphicon glyphicon-glyphicon glyphicon-warning-sign"></i>位置报修
                            </button>
                        </div>
                    </div>
                    <div class="divide-2"></div>
                    <!-- BOX -->
                    <div id="contentDiv">
                        <%@include file="detail.jsp" %>
                    </div>
                </div>
            </div>
            <%@include file="../common/common-back2top.jsp" %>
        </div>
    </div>
    <!-- /CONTENT-->
</div>


<div class="modal fade " id="loc_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">请输入位置报修描述</h4>
            </div>
            <div class="modal-body">
                <%@include file="locationReport.jsp" %>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/app/locations/location.js"></script>
