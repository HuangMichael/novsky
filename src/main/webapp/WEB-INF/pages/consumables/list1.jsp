<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/common-head.jsp" %>
<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<div class="modal fade" id="createUser_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">新建易耗品</h4>
            </div>
            <div class="modal-body">
                <form id="consumablesForm">
                    <div class="form-group">
                        <label for="description">易耗品名称</label>
                        <input type="text" class="form-control" id="description" name="description">
                        <input type="hidden" class="form-control" id="type" name="type" value="1">
                    </div>
                    <div class="form-group">
                        <label for="price">单价</label>
                        <input type="text" class="form-control" id="price" name="price">
                    </div>
                    <div class="form-group">
                        <label for="amount">数量</label>
                        <input type="text" class="form-control" id="amount" name="amount">
                    </div>
                    <div class="form-group">
                        <label for="unit">单位</label>
                        <input type="text" class="form-control" id="unit" name="unit">
                        <input type="hidden" class="form-control" id="conid" name="conid">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn" onclick="save()">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="draw_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">领取易耗品</h4>
            </div>
            <div class="modal-body">
                <form id="drawForm">
                    <div class="form-group">
                        <label for="amount">领取人</label>
                        <input type="text" class="form-control" id="drawer" name="drawer" required>
                        <input type="hidden" class="form-control" id="type2" name="type" value="1">
                    </div>
                    <div class="form-group">
                        <label for="amount">数量</label>
                        <input type="number" class="form-control" id="draw_amount" name="amount" min="1">
                        <input type="hidden" class="form-control" id="consId" name="consumables.id">

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveDraw" onclick="saveDraw()">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="draw_history_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">领取历史记录</h4>
            </div>
            <div class="modal-body">
                <table id="draw_history_table" cellpadding="0" cellspacing="0" border="0" style="overflow: scroll"
                       class=" table table-striped table-bordered table-hover">
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
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
                            <h4><i class="fa fa-table"></i>易耗品信息</h4>
                        </div>
                        <div class="box-body">
                            <a class="btn btn-default btn-mini">信息列表
                            </a>
                            <a class="btn btn-default btn-mini" href="#createUser_modal" data-toggle="modal">
                                新建记录
                            </a>
                            <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                                   class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>

                                    <th><input type="checkbox"/></th>
                                    <th data-column-id="id">序号</th>
                                    <th data-column-id="consNo">编码</th>
                                    <th data-column-id="description">名称</th>
                                    <th data-column-id="price">单价</th>
                                    <th data-column-id="amount">数量</th>
                                    <th data-column-id="total">总价</th>
                                    <th data-column-id="draw" data-formatter="draw" data-sortable="false">领用</th>
                                    <th data-column-id="link" data-formatter="link" data-sortable="false">查看领用记录</th>
                                    <%-- <th data-column-id="status">使用状态</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${consumablesList}" var="consumables" varStatus="s">
                                    <tr class="gradeX">
                                        <td><input type="checkbox"/></td>
                                        <td>${consumables.id}</td>
                                        <td>
                                                ${consumables.consNo}
                                        </td>
                                        <td>
                                                ${consumables.description}
                                        </td>
                                        <td>
                                                ${consumables.price}元
                                        </td>
                                        <td>
                                                ${consumables.amount}${consumables.unit}
                                        </td>
                                        <td>
                                                ${consumables.amount*consumables.price}元
                                        </td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
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
<script type="text/javascript" src="/js/jquery.bootgrid-1.3.1/jquery.bootgrid.min.js"></script>
<script>
    $(document).ready(function () {
        $("#datatable1").bootgrid({
            formatters: {
                "draw": function (column, row) {
                    var conId = row.id;
                    return '<a class="btn btn-default btn-xs" onclick ="draw(' + conId + ')">领用</a>';
                },
                "link": function (column, row) {
                    var conId = row.id;
                    return '<a class="btn btn-default btn-xs" onclick ="showList(' + conId + ')">领用记录</a>';
                }
            }
        });
    });


    /**
     *添加易耗品信息
     */
    function save() {
        var array = $("#consumablesForm").serializeArray();
        var objStr = '{';
        for (var x in array) {
            if (array[x]["name"] && array[x]["value"]) {
                objStr += '"' + array[x]["name"] + '"';
                objStr += ":";
                objStr += '"' + array[x]["value"] + '",';
            }
        }
        objStr = objStr.substring(0, objStr.length - 1);
        objStr += '}';
        var consumables = JSON.parse(objStr);

        var url = "/consumables/save";
        $.ajax({
            type: "POST",
            url: url,
            data: consumables,
            dataType: "JSON",
            success: function (msg) {
                $.bootstrapGrowl("易耗品信息添加成功", {
                    type: 'info',
                    align: 'right',
                    stackup_spacing: 30
                });
            },
            error: function (msg) {
                $.bootstrapGrowl("易耗品信息添加失败", {
                    type: 'danger',
                    align: 'right',
                    stackup_spacing: 30
                });
            }
        });
    }


    var consId = null;
    /**
     *
     * @param id 领取易耗品信息
     */
    function draw(id) {
        $("#draw_modal").modal("show");
        consId = id;
        $("#consId").val(consId);
    }


    /**
     *
     * @param id 根据易耗品信息显示领用记录
     */
    function showList(id) {
        $("#draw_history_table").html("");
        var html = "<tr> ";
        html += "<th >序号</th>";
        html += "<th>易耗品名称</th>";
        html += " <th>经手人</th>";
        html += "<th >领取人</th>";
        html += "<th >领取时间</th>";
        html += "</tr>";
        if (id) {
            var url = "/drawList/findByCons/" + id;
            $.getJSON(url, function (data) {
                for (var i = data.length - 1; i >= 0; i--) {
                    html += "<tr>";
                    html += "<td>" + parseInt(data.length - i) + "</td>";
                    html += "<td>" + data[i]['consumables']['description'] + "</td>";
                    html += "<td>" + data[i]['handler'] + "</td>";
                    html += "<td>" + data[i]['drawer'] + "</td>";
                    html += "<td>" + transformDate(data[i]['handleTime']) + "</td>";
                    html += "</tr>";
                }
                $("#draw_history_table").append(html);
            });
            $("#draw_history_modal").modal("show");
        }
    }


    function saveDraw() {
        var array = $("#drawForm").serializeArray();
        var objStr = '{';
        for (var x in array) {
            if (array[x]["name"] && array[x]["value"]) {
                objStr += '"' + array[x]["name"] + '"';
                objStr += ":";
                objStr += '"' + array[x]["value"] + '",';
            }
        }
        objStr = objStr.substring(0, objStr.length - 1);
        objStr += '}';

        console.log(objStr);
        var drawList = JSON.parse(objStr);
        var url = "/consumables/draw/" + consId;
        $.ajax({
            type: "POST",
            url: url,
            data: drawList,
            dataType: "JSON",
            success: function (msg) {
                if (msg.result) {
                    $.bootstrapGrowl("易耗品领取成功", {
                        type: 'info',
                        align: 'right',
                        stackup_spacing: 30
                    });

                    consId = null;
                } else {
                    $.bootstrapGrowl("易耗品领取失败", {
                        type: 'info',
                        align: 'right',
                        stackup_spacing: 30
                    });
                }

            },
            error: function (msg) {
                $.bootstrapGrowl("易耗品信息领取失败", {
                    type: 'danger',
                    align: 'right',
                    stackup_spacing: 30
                });
            }
        });
    }
</script>

