/**
 * Created by Administrator on 2016/7/22.
 */


var expiredTab = $('#myTab li:eq(1) a');
$(document).ready(function () {

    docName = "维修单信息";
    mainObject = "workOrderFix";
    //导出必须配置的两个量
    dataTableName = "#fixListTable0";

    var dataTableName1 = "#expiredOrdersList";


    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });


    var url = "/commonData/findVEqClass";
    $.getJSON(url, function (data) {
        eqClasses = data;
    });


    initSelect();

    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            locs: locs,
            eqClasses: eqClasses
        }

    });

    searchModel = [
        {"param": "nodeState", "paramDesc": "工单状态"},
        {"param": "orderLineNo", "paramDesc": "跟踪号"},
        {"param": "orderDesc", "paramDesc": "故障描述"},
        {"param": "locName", "paramDesc": "设备位置"},
        {"param": "eqClass", "paramDesc": "设备分类"},
        {"param": "expired", "paramDesc": "是否超期"}

    ];
    initBootGrid(dataTableName);
    initBootGrid(dataTableName1);
    $("#expiredTip").html(getExpiredCount());

    //计算超期 并且没有完工的工单数量
    setInterval(function () {
        $("#expiredTip").fadeOut(1000).fadeIn(1000);
    }, 1000);


    search();


    $(":checkbox").on("click", function () {
        if ($(this).prop("checked")) {
            $(this).siblings().attr("checked",false);
            $(this).attr("checked",true);
        }
    })

    $("#saveFixDesc").on("click", function () {
        var orderId = $("#orderId").val();
        var operationType = $("#operationType").val();
        var operationDesc = $("#operationDesc").val();
        var fixDesc = $("#fixDesc").val();

        if (!fixDesc) {
            $("#fixDesc").css("border", "red dashed 1px");
            showMessageBox("danger", " 请输入维修描述");
            return;
        }
        dealResultDetail(orderId, operationType, operationDesc, fixDesc);

    });
    $("#myTab a").on("click", function (e) {
        e.preventDefault();
        $(this).tab('show');
    });


    expiredTab.on("click", function () {
        var searchPhase = "已派工,,,,,已超期,";
        $(dataTableName1).bootgrid("setSearchPhrase", searchPhase).bootgrid("reload");
        $("#expiredOrderSize").html(expiredCount);
    })
});


function dealResult(orderId, operationType, operationDesc) {
    $("#orderId").val(orderId);
    $("#operationType").val(operationType);
    $("#operationDesc").val(operationDesc);
    $("#fix_desc_modal").modal("show");
}


function dealResultDetail(orderId, operationType, operationDesc, fixDesc) {
    updateOrderStatus(orderId, operationType, operationDesc, fixDesc);
    $(dataTableName).bootgrid("reload");
    //更新超期
    $("#expiredTip").html(expiredCount);
}


/**
 *  调整维修期限
 * @param id
 */
function adjust(id) {
    var orderId = id;
    var url = "/workOrderFix/getCellingDate/" + orderId;
    $("#orderId").val(orderId);
    $.getJSON(url, function (data) {
        $("#fixAdjust0").val(transformDate(data));
        $("#fix_adjust_modal").modal("show");
    })
}


/**
 *
 * @param orderId
 * @param deadLine
 */
function confirmAdjust() {
    var url = "workOrderFix/updateDeadLine";
    var data = {
        orderId: $("#orderId").val(),
        deadLine: $("#fixAdjust1").val()
    };
    $.post(url, data, function (data) {
        if (data.result) {
            showMessageBox("info", data['resultDesc']);
        } else {
            showMessageBox("danger", data['resultDesc']);
        }
        $("#fix_adjust_modal").modal("hide");
        $('#fixListTable').bootgrid();
    });

}


function updateOrderStatus(orderId, operationType, operationDesc, fixDesc) {
    var url = "/workOrderFix/" + operationType;
    $.post(url, {fixId: orderId, fixDesc: fixDesc}, function (data) {
        $("#fix_desc_modal").modal("hide");
        $("#tr-" + orderId).html(operationDesc);
        $("#tr-" + orderId).html(fixDesc);
        (data.result) ? showMessageBox("info", data['resultDesc']) : showMessageBox("danger", data['resultDesc']);
    });
}


/**
 *
 * @param id 完工
 */
function finish() {
    var orderId = $(dataTableName).bootgrid("getSelectedRows")[0];
    if (!orderId) {
        showMessageBox("danger", "请选择一个要完工的工单！")
        return;
    }
    var operationType = "finishDetail";
    var operationDesc = "完工";
    dealResult(orderId, operationType, operationDesc);
}


/**
 *
 * @param id 暂停
 */
function pause() {
    var orderId = $(dataTableName).bootgrid("getSelectedRows")[0];
    if (!orderId) {
        showMessageBox("danger", "请选择一个要暂停的工单！")
        return;
    }
    var operationType = "pauseDetail";
    var operationDesc = "暂停";
    dealResult(orderId, operationType, operationDesc);
}
/**
 *
 * @param id 取消
 */
function abort() {
    var orderId = $(dataTableName).bootgrid("getSelectedRows")[0];
    if (!orderId) {
        showMessageBox("danger", "请选择一个要取消的工单！")
        return;
    }
    var operationType = "abortDetail";
    var operationDesc = "取消";
    dealResult(orderId, operationType, operationDesc);
}




