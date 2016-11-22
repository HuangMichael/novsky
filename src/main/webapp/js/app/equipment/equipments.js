//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
//维修历史列表
var historyTab = $('#myTab li:eq(2) a');
var recordsTab = $('#myTab li:eq(3) a');
var pointer = 0;
var eqStatuses = [];
var runStatus = [];


var vdm = null;
var pointer = 0;
$(function () {
    var validateOptions = {
        message: '该值无效 ',
        fields: {
            "eqCode": {
                message: '设备编号无效',
                validators: {
                    notEmpty: {
                        message: '设备编号不能为空!'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '设备编号长度为6到20个字符'
                    }
                }
            },
            "description": {
                message: '设备描述无效',
                validators: {
                    notEmpty: {
                        message: '设备描述不能为空!'
                    },
                    stringLength: {
                        min: 2,
                        max: 20,
                        message: '设备描述长度为2到20个字符'
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
            "equipmentsClassification.id": {
                message: '设备分类无效',
                validators: {
                    notEmpty: {
                        message: '设备分类不能为空!'
                    }
                }
            },
            "status": {
                message: '设备状态无效',
                validators: {
                    notEmpty: {
                        message: '设备状态不能为空!'
                    }
                }
            }
            ,
            "running": {
                message: '运行状态无效',
                validators: {
                    notEmpty: {
                        message: '运行状态不能为空!'
                    }
                }
            }
        }
    };
    //初始化配置信息
    //主对象信息
    mainObject = "equipment";
    //列表id
    dataTableName = "#equipmentsDataTable";
    //导出文档配置信息
    docName = "设备信息";
    //表单界面信息
    formName = "#detailForm";
    //查询模型


    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });

    var url = "/commonData/getEqStatus";
    $.getJSON(url, function (data) {
        eqStatuses = data;
    });

    var url = "/commonData/getEqRunStatus";
    $.getJSON(url, function (data) {
        runStatus = data;
    });

    var url = "/commonData/findVEqClass";
    $.getJSON(url, function (data) {
        eqClasses = data;
    });


    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            locs: locs,
            eqClasses: eqClasses
        }

    });

    searchModel = [
        {"param": "eqCode", "paramDesc": "设备编号"},
        {"param": "eqName", "paramDesc": "设备名称"},
        {"param": "locName", "paramDesc": "设备位置"},
        {"param": "eqClass", "paramDesc": "设备分类"}
    ];

    vdm = new Vue({
        el: "#detailForm",
        data: {
            equipments: null,
            locs: locs,
            eqClasses: eqClasses,
            eqStatuses: eqStatuses,
            runStatus: runStatus
        }
    });


    //初始化加载列表
    initBootGridMenu(dataTableName);
    //验证保存信息
    validateForm(validateOptions);
    initSelect();
    search();
    // showDetail();
});
