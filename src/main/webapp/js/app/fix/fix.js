/**
 * Created by Administrator on 2016/7/22.
 */
$(document).ready(function () {
    $('#fixListTable').bootgrid({
        formatters: {

            /*     "fixDesc": function (column, row) {
             return '<input id="fixDesc' + row.id + '" type="text" style="height: 25px">'
             },*/
            "opMenus": function (column, row) {
                return '<a class="btn btn-default btn-xs"  onclick="pause(' + row.id + ')" title="暂停" ><i class="glyphicon glyphicon-pause"></i></a>' +
                    '<a class="btn btn-default btn-xs"  onclick="abort(' + row.id + ')" title="取消" ><i class="glyphicon glyphicon glyphicon-remove-circle"></i></a>' +
                    '<a class="btn btn-default btn-xs"  onclick="finish(' + row.id + ')" title="完工" ><i class="glyphicon glyphicon glyphicon-ok"></i></a>'
            }
        }
    });

    $('#fixListTable1').bootgrid();

    $('#fixListTable2').bootgrid({
        formatters: {

            /*     "fixDesc": function (column, row) {
             return '<input id="fixDesc' + row.id + '" type="text" style="height: 25px">'
             },*/
            "opMenus": function (column, row) {
                return '<a class="btn btn-default btn-xs"  onclick="pause(' + row.id + ')" title="恢复" ><i class="glyphicon glyphicon-pause"></i></a>' +
                    '<a class="btn btn-default btn-xs"  onclick="abort(' + row.id + ')" title="取消" ><i class="glyphicon glyphicon glyphicon-remove-circle"></i></a>' +
                    '<a class="btn btn-default btn-xs"  onclick="finish(' + row.id + ')" title="完工" ><i class="glyphicon glyphicon glyphicon-ok"></i></a>'
            }
        }
    });

    $('#fixListTable3').bootgrid();

    /*
     $('#fixDescForm')
     .bootstrapValidator({
     message: '该值无效 ',
     fields: {
     "fixDesc": {
     message: '维修描述无效',
     validators: {
     notEmpty: {
     message: '维修描述不能为空!'
     },
     stringLength: {
     min: 1,
     max: 200,
     message: '维修描述为1到200个字符'
     }
     }
     }
     }
     }).on('success.form.bv', function (e) {
     // Prevent form submission
     e.preventDefault();
     // Get the form instance


     });
     */


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
        preview(1);
        $(this).tab('show');
    })
});


var orderId = null;
var operationType = null;
var operationDesc = null;
function dealResult(orderId, operationType, operationDesc) {
    $("#orderId").val(orderId);
    $("#operationType").val(operationType);
    $("#operationDesc").val(operationDesc);
    $("#fix_desc_modal").modal("show");
}


function dealResultDetail(orderId, operationType, operationDesc, fixDesc) {
    updateOrderStatus(orderId, operationType, operationDesc, fixDesc);
}


/**
 *
 * @param id 完工
 */
function finish(id) {
    var orderId = id;
    var operationType = "finishDetail";
    var operationDesc = "完工";
    dealResult(orderId, operationType, operationDesc);
}


function updateOrderStatus(orderId, operationType, operationDesc, fixDesc) {
    var url = "/workOrderFix/" + operationType;
    $.post(url, {fixId: orderId, fixDesc: fixDesc}, function (data) {
        $("#fix_desc_modal").modal("hide");
        $("#tr-" + orderId).html(operationDesc);
        $("#tr-" + orderId).html(fixDesc);
        (data.result) ? showMessageBox("info", data.resultDesc) : showMessageBox("danger", data.resultDesc);
    });
}
/**
 *
 * @param id 暂停
 */
function pause(id) {
    var orderId = id;
    var operationType = "pauseDetail";
    var operationDesc = "暂停";
    dealResult(orderId, operationType, operationDesc);
}
/**
 *
 * @param id 取消
 */
function abort(id) {
    var orderId = id;
    var operationType = "abortDetail";
    var operationDesc = "取消";
    dealResult(orderId, operationType, operationDesc);
}

/**
 *
 * @param id 预览
 */
function preview(id) {
    PDFObject.embed("/report/fixReport.pdf", "#pdf_view",
        {
            width: "100%",
            height: "750px"
        }
    );
}
