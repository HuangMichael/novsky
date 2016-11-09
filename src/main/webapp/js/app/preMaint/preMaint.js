var dataTableName = '#pmDataTable';
var pms = [];
var selectedIds = []; //获取被选择记录集合
var pmDetail = null; //明细页面的模型
var vm = null; //明细页面的模型
var pm = null;
var locs = [];
var eqs = [];
var units = [];

var f_units = [];
var pointer = 0;
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
var object = null;
formName = "#detailForm";
$.ajaxSettings.async = false;
var validationConfig = {
    message: '该值无效 ',
    fields: {

        pmCode: {
            message: '预防性维修计划编号无效',
            validators: {
                notEmpty: {
                    message: '预防性维修计划编号不能为空!'
                },
                stringLength: {
                    min: 3,
                    max: 20,
                    message: '预防性维修计划编号长度为3到20个字符'
                }
            }
        },


        description: {
            message: '预防性维修计划描述无效',
            validators: {
                notEmpty: {
                    message: '预防性维修计划描述不能为空!'
                },
                stringLength: {
                    min: 3,
                    max: 20,
                    message: '预防性维修计划描述长度为3到20个字符'
                }
            }
        },
        "locations.id": {
            message: '设备位置无效',
            validators: {
                notEmpty: {
                    message: '设备位置不能为空!'
                }
            }
        },

        "equipment.id": {
            message: '设备无效',
            validators: {
                notEmpty: {
                    message: '设备不能为空!'
                }
            }
        },
        "frequency": {
            message: '频率设置无效',
            validators: {
                notEmpty: {
                    message: '频率不能为空!'
                },
                min: {
                    message: '频率必须大于0'
                }
            }
        },
        "unit": {
            message: '计划执行频率单位无效',
            validators: {
                notEmpty: {
                    message: '计划执行频率单位不能为空!'
                }
            }
        },
        "outUnit.id": {
            message: '维修单位无效',
            validators: {
                notEmpty: {
                    message: '维修单位不能为空!'
                }
            }
        }
    }
};
$(function () {


    docName = "预防性维修信息";
    mainObject = "preMaint";

    //初始化从数据库获取列表数据
    //initLoadData("/units/findAll", dataTableName);

    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });

    var unit_location = "/units" + "/findAll";
    $.getJSON(unit_location, function (data) {
        units = data;
    });


    var eqs_url = "/commonData/findMyEqs";
    $.getJSON(eqs_url, function (data) {
        eqs = data;
    });


    f_units = [{
        key: 0,
        text: "天"
    }, {
        key: 1,
        text: "月"
    }, {
        key: 2,
        text: "年"
    }];

    initBootGrid(dataTableName);
    initSelect();
    selectedIds = findAllRecordId();
    valiform(validationConfig);


    vdm = new Vue({
        el: "#detailForm",
        data: {
            pm: pms[0],
            locs: locs,
            units: units,
            eqs: eqs,
            f_units: f_units
        }
    });


    formTab.on('click', function () {
        //首先判断是否有选中的
        var object = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            object = findById(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            selectedIds = getAllId();
            object = findById(selectedIds[0]);
        }
        pmDetail.$set(mainObject, object);
        setFormReadStatus(formName, true);

    });


});


/**
 * 新增预防性维修计划
 */
/*function add() {
 setFormReadStatus(formName, false);
 vdm.$set(mainObject, null);
 //设置设备状态和运行状态默认值;
 formTab.tab('show');

 }*/


/**
 * 编辑设备信息
 */
function edit() {
    setFormReadStatus(formName, false);
    formTab.tab('show');
}


/**
 *
 * @returns {Array} 获取所有的id
 */
function getAllId() {
    var ids = [];
    var url = "/preMaint/selectAllId";
    $.getJSON(url, function (data) {
        ids = data;
    })
    return ids;
}

/**
 * 保存设备信息
 */
function save() {
    $("#saveBtn").trigger("click");
}


/**
 * 删除记录
 */
function del() {
    var bid = selectedIds[0];
    var url = "/preMaint/delete/" + bid;
    if (bid) {
        bootbox.confirm({
            message: "确定要删除该记录么？?",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-success'
                },
                cancel: {
                    label: '否',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    $.ajax({
                        type: "GET",
                        url: url,
                        success: function (msg) {
                            if (msg) {
                                showMessageBox("info", "预防性维修计划信息删除成功!");
                            }
                        },
                        error: function (msg) {
                            showMessageBox("danger", "预防性维修计划信息有关联数据，无法删除，请联系管理员");
                        }
                    });
                }
            }
        });
    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
    }
}


function createUnit() {
    var objStr = getFormJsonData("createForm");
    var outsourcingUnit = JSON.parse(objStr);
    console.log(JSON.stringify(outsourcingUnit));
    var url = getMainObject() + "/save";
    $.ajax({
        type: "post",
        url: url,
        data: outsourcingUnit,
        dataType: "json",
        success: function (data) {
            $("#unit_modal").modal("hide");
            if (data.id) {
                showMessageBox("info", "外委单位信息更新成功");
            } else {
                showMessageBox("info", "外委单位信息添加成功");
            }
        }, error: function (data) {
            if (data.id) {
                showMessageBox("danger", "外委单位信息更新失败");
            } else {
                showMessageBox("info", "外委单位信息添加失败");
            }
        }
    });
}


/**
 * 生成工单
 */
function popGen(pmId) {

    $("#confirm_modal").data("pmId", pmId).modal("show");
}


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







