var selectedIds = []; //获取被选择记录集合
var locs = [];
var eqs = [];
var persons = [];
var pointer = 0;
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
var object = null;
formName = "#detailForm";
$.ajaxSettings.async = false;
var validationConfig = {
    message: '该值无效 ',
    fields: {
        userName: {
            message: '用户名号无效',
            validators: {
                notEmpty: {
                    message: '用户名!'
                },
                stringLength: {
                    min: 3,
                    max: 20,
                    message: '用户名长度为3到20个字符'
                }
            }
        }
    }
};

$(function () {
    dataTableName = '#userDataTable';
    docName = "用户信息";
    mainObject = "user";
    //初始化从数据库获取列表数据

    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });

    var person_location = "/commonData/findActivePerson";
    $.getJSON(person_location, function (data) {
        persons = data;
    });


    initBootGrid(dataTableName);
    initSelect.call();
    selectedIds = findAllRecordId();
    validateForm.call(validationConfig);

    vdm = new Vue({
        el: formName,
        data: {
            user: selectedIds[0],
            locs: locs,
            persons: persons
        }
    });
    showDetail();
});


/**
 * 生成工单
 */
function generateOrder() {
    var deadLine = $("#deadLine").val();
    console.log("deadLine---------" + deadLine);
    var pmId = $("#confirm_modal").data("pmId");

    var url = "/preMaint/genPmOrder";

    var obj = {
        pmId: pmId,
        deadLine: deadLine
    };
    $.post(url, obj, function (data) {

        if (data.result) {
            $("#confirm_modal").modal("hide");
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    });
}







