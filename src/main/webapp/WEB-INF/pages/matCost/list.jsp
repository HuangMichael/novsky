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
                            <h4 class="appTitle"><i class="fa fa-sitemap"></i>物料消耗信息</h4>
                        </div>
                        <%@include file="../common/common-menubar.jsp" %>
                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active">
                                        <a href="#list_tab" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold">
                                            <i class="fa fa-home" id="list"></i>列表信息</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="list_tab"
                                         style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
                                        <div class="container-fluid">
                                            <table id="budgetDataTable"
                                                   class=" table table-striped table-bordered table-hover"
                                                   data-toggle="bootgrid" data-ajax="true"
                                                   data-url="/matCost/data">
                                                <thead>

                                                id, locName, ec_name, amount, apply_date, ec_type
                                                <tr>
                                                    <th data-column-id="id" data-type="numeric" data-identifier="true"
                                                        data-visible="false">ID
                                                    </th>
                                                    <th data-align="center" data-column-id="applyDate" data-width="5%">
                                                        采购日期
                                                    </th>
                                                    <th data-align="center" data-column-id="locName" data-width="10%">
                                                        位置
                                                    </th>
                                                    <th data-align="center" data-column-id="ecName" data-width="10%">
                                                        名称
                                                    </th>
                                                    <th data-align="center" data-column-id="amount" data-width="10%">
                                                        数量
                                                    </th>
                                                    <th data-align="center" data-column-id="ecType" data-width="10%">
                                                        分类
                                                    </th>
                                                </tr>
                                                </thead>

                                            </table>
                                        </div>
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
<script src="/js/app/eqUpdateBill/eqUpdateBill.js"></script>