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
                            <h4><i class="fa fa-sitemap"></i>应用授权</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="box border blue">
                        <div class="row">
                            <div class="col-md-8">
                                <select class="form-control" id="role" name="resourceLevel">
                                    <option value="">--请选择角色--</option>
                                    <option value="1">管理员</option>
                                    <option value="2">超级管理员</option>
                                    <option value="3">普通用户</option>
                                </select>
                            </div>
                            <div class="col-md-4"><a class="btn  btn-sm btn-danger">授权</a></div>
                        </div>
                        <%-- <div class="col-md-1"><a class="btn btn-danger">授权</a></div>--%>
                        <div class="box-body treeContainer" id="treeDiv">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="box border blue">
                        <div class="box-body">
                            <%@include file="../resource/detail.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../common/common-back2top.jsp" %>
        </div>
    </div>
    <!-- /CONTENT-->
</div>
<script src="js/app/authority/authority.js"></script>
