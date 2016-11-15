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
                                            <%--         <div style="margin-top: 20px;padding: 20px">
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
                                                     </div>--%>
                                            <table id="matCostDataTable"
                                                   class="table table-striped table-bordered table-hover"
                                                   data-toggle="bootgrid" data-ajax="true" data-url="/matCost/data">
                                                <thead>
                                                <tr>
                                                    <th data-column-id="id" data-width="5%">序号</th>
                                                    <th data-column-id="id" data-type="numeric" data-identifier="true"
                                                        data-visible="false" data-width="5%">ID
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
                                            </table>
                                            <%-- <div id="mainContent"></div>
                                             <div id="callBackPager"></div>--%>
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
<%--
<script src="/js/bootstrap-pager/js/extendPagination.js"></script>
<script type="text/javascript">

    var locs = [];
    var searchListVue = null;

    var searchVue = null;

    $(function () {


        $("select").select2({
            theme: "bootstrap"
        });


        locs = getMyLocs();

        searchVue = new Vue({
            el: "#searchForm",
            data: {
                locs: locs
            }
        });


        searchListVue = new Vue({
            el: "#mainContent",
            data: {
                mcList: null
            }

        });


        callBackPagination();
    });

    function callBackPagination() {

        //初始化请求数量

        var page = getDataPage();
        var totalCount = page.rows.length;
        var showCount = totalCount;
        var limit = 10;

        createTable(1, limit, totalCount,page.rows);
        $('#callBackPager').extendPagination({
            totalCount: totalCount,
            showCount: showCount,
            limit: limit,
            callback: function (curr, limit, totalCount) {

                console.log("第" + curr + "页:");
                console.log("每页" + limit + "条:");
                console.log("共" + totalCount + "条");
                createTable(curr, limit, totalCount);
            }
        });
    }

    function getSearchParam() {
        var locName = $("#locName").val();
        var ecName = $("#ecName").val();
        var ecType = $("#ecType").val();
        var searchParam = {
            locName: locName,
            ecName: ecName,
            ecType: ecType
        }
        return searchParam;

    }


    /**
     * 查询我的位置
     */
    function getMyLocs() {
        var url = "/matCost/findMyLocs";
        $.getJSON(url, function (data) {
            locs = data;
        });
        return locs;
    }


    /**
     *
     * */
    function createTable(currPage, limit, total, dataList) {
        /* var url = "matCost/search";
         var searchParam = getSearchParam();
         searchParam.current = currPage;
         searchParam.rowCount = limit;
         $.post(url, searchParam, function (data) {
         if (data && data.rows) {
         searchListVue.$set("mcList", data.rows);
         }
         });*/


        var html = [], showNum = limit;
        if (total - (currPage * limit) < 0) {
            showNum = total - ((currPage - 1) * limit);
        }



        html.push(' <table  class="table table-bordered table-hover table-striped">');
        html.push(' <thead><tr>');
        html.push(' <th><input type="checkbox" /></th>');

        var colsConfig = getColsConfig();

        colsConfig.forEach(function (colConfig) {
            html.push(' <th>' + colConfig["title"] + '</th>');
        })
        html.push(' </tr></thead><tbody>');


        dataList.forEach(function (data) {
            html.push("<tr>");
            html.push('<td width="5%"><input type="checkbox" value="' + data["id"] + '" name="check' + data.id + '" /></td>');
            html.push('<td width="5%">' + data["id"] + '</td>');
            html.push('<td width="15%">' + data["applyDate"] + '</td>');
            html.push('<td width="15%">' + data["locName"] + '</td>');
            html.push('<td width="15%">' + data["ecName"] + '</td>');
            html.push('<td width="15%">' + data["amount"] + '</td>');
            html.push('<td width="15%">' + data["ecType"] + '</td>');
            html.push('</tr>');
        });
        html.push('</tbody></table>');
        var mainObj = $('#mainContent');
        mainObj.empty();
        mainObj.html(html.join(''));


    }


    /**
     *表格标题
     * @returns {string[]}
     */
    function getColsConfig() {
        var colsConfig = [
            {title: "序号", colName: "id"},
            {title: "采购日期", colName: "applyDate"},
            {title: "位置", colName: "locName"},
            {title: "名称", colName: "ecName"},
            {title: "数量", colName: "amount"},
            {title: "分类", colName: "ecType"}
        ]
        return colsConfig;
    }


    function getDataPage() {

        var page = null;
        var url = "matCost/search";
        var searchParam = getSearchParam();
        $.post(url, searchParam, function (data) {
            page = data;
        });
        return page;
    }
</script>--%>
