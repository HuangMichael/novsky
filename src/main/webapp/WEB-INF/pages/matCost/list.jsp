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
                            <h4 class="appTitle"><i class="fa fa-sitemap"></i>物料消耗</h4>
                        </div>
                        <%@include file="../common/common-menubar.jsp" %>
                        <div class="box-body">
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active">
                                        <a href="#list_tab" data-toggle="tab"
                                           style="font-family: 微软雅黑;font-weight: bold">
                                            <i class="fa fa-home" id="list"></i>物料消耗</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="list_tab"
                                         style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
                                        <div class="container-fluid">
                                            <div style="margin-top: 20px;padding: 20px">
                                                <div class="form-inline" role="form" id="searchForm">
                                                    <div class="form-group">

                                                        <div class="input-group">
                                                            <label class="sr-only" for="ecType">分类</label>
                                                            <select v-model="ecType" class="form-control" id="ecType"
                                                                    name="locName"
                                                                    style="width:200px">
                                                                <option value="" selected>
                                                                    请选择分类
                                                                </option>
                                                                <option>
                                                                    物资
                                                                </option>
                                                                <option>
                                                                    易耗品
                                                                </option>

                                                            </select>
                                                        </div>
                                                        <%-- <div class="input-group">
                                                             <label class="sr-only" for="ecType">线路</label>
                                                             <select v-model="line" class="form-control" id="line"
                                                                     name="line"
                                                                     style="width:200px">
                                                                 <option value="" selected>
                                                                     请选择线路
                                                                 </option>
                                                                 <template v-for="line in lines">
                                                                     <option>
                                                                         {{line.description}}
                                                                     </option>
                                                                 </template>

                                                             </select>
                                                         </div>--%>
                                                        <div class="input-group">
                                                            <label class="sr-only" for="locName">位置</label>
                                                            <select v-model="locName" class="form-control" id="locName"
                                                                    name="locName"
                                                                    style="width:200px" required>
                                                                <option value="">
                                                                    请选择位置
                                                                </option>
                                                                <template v-for="loc in locs">
                                                                    <option>
                                                                        {{loc }}
                                                                    </option>
                                                                </template>
                                                            </select>
                                                        </div>
                                                        <div class="input-group">
                                                            <label class="sr-only" for="ecName">名称</label>
                                                            <input type="text" class="form-control" id="ecName"
                                                                   placeholder="请输入名称" value="">
                                                        </div>
                                                    </div>
                                                    <button type="button" class="btn btn-default" id="searchBtn">查询
                                                    </button>
                                                </div>
                                            </div>
                                            <table id="budgetDataTable"
                                                   class="table  table-striped  table-bordered table-hover"
                                                   data-toggle="bootgrid">
                                                <thead>
                                                <tr>
                                                    <th data-column-id="id" data-type="numeric" data-identifier="true"
                                                        data-visible="false" width="2%">序号
                                                    </th>
                                                    <th data-align="center" data-column-id="applyDate" width="10%">
                                                        采购日期
                                                    </th>
                                                    <th data-align="center" data-column-id="locName" width="20%">
                                                        位置
                                                    </th>
                                                    <th data-align="center" data-column-id="ecName" width="10%">
                                                        名称
                                                    </th>
                                                    <th data-align="center" data-column-id="amount" width="10%">
                                                        数量
                                                    </th>
                                                    <th data-align="center" data-column-id="ecType" width="10%">
                                                        分类
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody id="matCostList" v-for="mc in mcList">
                                                <tr>
                                                    <td>{{$index+1}}</td>
                                                    <td>{{mc.applyDate}}</td>
                                                    <td>{{mc.locName}}</td>
                                                    <td>{{mc.ecName}}</td>
                                                    <td>{{mc.amount}}</td>
                                                    <td>{{mc.ecType}}</td>
                                                </tr>
                                                </tbody>
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
<script src="/js/app/matCost/matCost.js"></script>