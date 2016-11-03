<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
                            <h4><i class="fa fa-sitemap"></i>外委单位信息</h4>
                        </div>


                        <%@include file="../common/common-menubar.jsp" %>


                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active"><a href="#tab_1_0" data-toggle="tab">
                                        <i class="fa fa-home" id="unit"></i>外委单位信息</a>
                                    </li>
                                    <li><a href="#tab_1_1" data-toggle="tab">
                                        <i class="fa fa-flag" id="unitDetail"></i>外委单位明细信息</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab_1_0">
                                        <%@include file="table_1_0.jsp" %>
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_1">
                                        <%@include file="table_1_1.jsp" %>
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
</div>
<div class="modal fade " id="unit_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">设备信息</h4>
            </div>
            <div class="modal-body">
                <%@include file="form.jsp" %>
            </div>
        </div>
    </div>
</div>

<script src="/js/app/units/units.js"></script>