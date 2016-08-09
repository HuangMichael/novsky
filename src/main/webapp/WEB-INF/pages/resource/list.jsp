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
                            <h4><i class="fa fa-sitemap"></i>资源信息</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-body">
                            <ul id="tree" class="ztree" style="width:460px; overflow:auto;"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <div class="box border blue">
                        <div class="box-body">
                            <a type="button" class="btn  btn-mini btn-default" id="loadCreateBtn"
                               onclick="loadCreateForm()">新建记录</a>
                            <a type="button" class="btn  btn-mini btn-default" id="saveBtn" onclick="save()">保存记录</a>
                            <a type="button" class="btn  btn-mini btn-default" id="deleteBtn" onclick="deleteObject()">删除记录</a>
                        </div>
                    </div>
                    <div class="divide-2"></div>
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-body">
                            <div id="contentDiv">
                                <%@include file="detail.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../common/common-back2top.jsp" %>
        </div>
    </div>
    <!-- /CONTENT-->
</div>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script src="js/app/resource/resource.js"></script>

<%--<script type="text/javascript">
    jQuery(document).ready(function () {
        App.setPage("portal");  //Set current page
        App.init(); //Initialise plugins and elements
    });
</script>--%>
