<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/common-head.jsp" %>
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
                            <h4><i class="fa fa-users"></i>车站</h4>
                        </div>

                        <%@include file="../common/common-menubar.jsp"%>
                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active"><a href="#tab_1_0" data-toggle="tab"
                                                          style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-home" id="eq"></i>车站信息</a>
                                    </li>
                                    <li><a href="#tab_1_1" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold">
                                        <i class="fa fa-flag" id="eqDetail"></i>车站详细信息</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab_1_0">
                                        <%@include file="stationList.jsp" %>
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
<script type="text/javascript" src="/js/app/station/station.js"></script>
