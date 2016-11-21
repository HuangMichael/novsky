var bills = [];
var selectedIds = [];
//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
var vdm = null;
//位置信息
var myEqs = [];
var locs = [];
var eqClasses = [];

var dataTableName = "#eqAddBillDataTable";
var ids = [];
var pointer = 0;
var newVue = null;
$(function () {
    //设置数据有效性验证配置项
    var validateOptions = {
        message: '该值无效 ',
        fields: {
            "applicant": {
                message: '申请人无效',
                validators: {
                    notEmpty: {
                        message: '申请人不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '1到20个字符'
                    }
                }
            },
            "applyDep": {
                message: '申请部门无效',
                validators: {
                    notEmpty: {
                        message: '申请部门不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '1到20个字符'
                    }
                }
            },
            "amount": {
                message: '申请数量无效',
                validators: {
                    notEmpty: {
                        message: '申请数量不能为空!'
                    }
                }
            },
            "accessoryName": {
                message: '配件名称无效',
                validators: {
                    notEmpty: {
                        message: '配件名称不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '1到20个字符'
                    }
                }
            },
            "specifications": {
                message: '规格型号无效',
                validators: {
                    notEmpty: {
                        message: '规格型号不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '1到20个字符'
                    }
                }
            },
            "purpose": {
                message: '用途无效',
                validators: {
                    notEmpty: {
                        message: '用途不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '1到50个字符'
                    }
                }
            },
            "location.id": {
                message: '位置无效',
                validators: {
                    notEmpty: {
                        message: '位置不能为空!'
                    }
                }
            },

            "eqCode": {
                message: '设备编号无效',
                validators: {
                    notEmpty: {
                        message: '设备编号不能为空!'
                    }
                }
            },

            "eqName": {
                message: '设备名称无效',
                validators: {
                    notEmpty: {
                        message: '设备名称不能为空!'
                    }
                }
            },

            "eqClass.id": {
                message: '设备分类无效',
                validators: {
                    notEmpty: {
                        message: '设备分类不能为空!'
                    }
                }
            },
            "approver": {
                message: '批准人无效',
                validators: {
                    notEmpty: {
                        message: '批准人不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '1到50个字符'
                    }
                }
            },
            "handler": {
                message: '经办人无效',
                validators: {
                    notEmpty: {
                        message: '经办人不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '1到50个字符'
                    }
                }
            },
            "receiver": {
                message: '接收人无效',
                validators: {
                    notEmpty: {
                        message: '接收人不能为空!'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '1到50个字符'
                    }
                }
            },
        }
    };


    locs = findMyLoc();
    eqClasses = findMyEqClass();
    ids = findAllRecordId();
    initSearchDate();
    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            locs: locs,
            eqClasses: eqClasses
        }

    });
    initBootGrid(dataTableName);
    // 监听切换tab的方法
    search();

    vdm = new Vue({
        el: "#detailForm",
        data: {
            eqAddBill: findById(ids[pointer]),
            locs: locs,
            eqClasses: eqClasses
        }
    });


    $(formTab).on('click', function () {

        //首先判断是否有选中的
        var eqAddBill = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            eqAddBill = findById(selectedIds[pointer]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            eqAddBill = getEqAddBillById(ids[pointer]);
            //所有的都在选中列表中
            selectedIds = ids;
        }
        /*   vdm = new Vue({
         el: "#detailForm",
         data: {
         eqAddBill: getEqAddBillById(ids[pointer]),
         locs: locs,
         eqClasses: eqClasses
         }
         });*/

        vdm.$set("eqAddBill", getEqAddBillById(ids[pointer]));
        vdm.$set("locs", locs);
        vdm.$set("eqClasses", eqClasses);
        setFormReadStatus("#detailForm", true);
    });

    $("select").select2({
        theme: "bootstrap"
    });

    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveObject();
    });
});


/**
 * @param eqs 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(bugetBills) {
    var selecteds = [];
    for (var x in bugetBills) {
        if (!isNaN(bugetBills[x]["id"])) {
            selecteds.push(bugetBills[x]["id"]);
        }
    }
    return selecteds;

}


function add() {
    //重新建立模型 新建对象模型
    newVue = new Vue({
        el: "#detailContainer",
        data: {
            myEqs: myEqs,
            locs: locs,
            eqClasses: eqClasses,
            eqAddBill: null
        }
    });
    setFormReadStatus("#detailForm", false);
    clearForm("#detailForm");
    formTab.tab('show');
    formTab.data("status", "add");
}

function eqUpdateAdd(eid) {
    //重新建立模型 新建对象模型
    $.getJSON("eqAddBill/create/" + eid, function (data) {
        vdm.$set("eqAddBill", data);
    });
    setFormReadStatus("#detailForm", false);
    formTab.tab('show');
    $("#locName").attr("readonly", "readonly");
    $("#eqClass").attr("readonly", "readonly");

    //alert("add一条记录");
}
/**
 *保存或者更新
 * */
function saveObject() {
    var objStr = getFormJsonData("detailForm");
    var eqAddBill = JSON.parse(objStr);
    console.log(JSON.stringify(eqAddBill));
    var url = "eqAddBill/save";
    $.post(url, eqAddBill, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            formTab.data("status", "saved");
        } else {

            showMessageBox("danger", data["resultDesc"]);
        }
    });
}


function save() {
    $("#saveBtn").trigger("click");
}

function edit() {
    setFormReadStatus("#detailForm", false);
}

/**
 *查询我的位置
 * */
function findMyLoc() {
    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });
    return locs;
}

/**
 *查询我的位置
 * */
function findMyEqClass() {
    var url_eqclass = "/commonData/findVEqClass";
    $.getJSON(url_eqclass, function (data) {
        eqClass = data;
    });
    return eqClass;
}


/**
 *
 * @param formId 设置form为只读
 */
function setFormReadStatus(formId, formLocked) {
    if (formLocked) {
        $(formId + " input ").attr("readonly", "readonly");
        $(formId + " textarea ").attr("readonly", "readonly");
        $(formId + " select").attr("disabled", "disabled");
    } else {
        $(formId + " input").attr("readonly", "readonly").removeAttr("readonly");
        $(formId + " select").attr("disabled", "disabled").removeAttr("disabled");
        $(formId + " textarea").attr("readonly", "readonly").removeAttr("readonly");

    }
}


function clearForm(formId) {
    $(formId + " input ").val("");
    $(formId + " textarea ").val("");
    $(formId + " select").val("");
}

function changeLoc(a) {
    var locationsId = $(a).val();
    var url = "/eqAddBill/findCByLocId/" + locationsId;
    $.getJSON(url, function (data) {
        vdm.$set("eqClasses", data);
    });
    var url = "/eqAddBill/findEqBy/" + locationsId + "/" + eqClasses[0].id;
    $.getJSON(url, function (data) {
        vdm.$set("myEqs", data);
    });

    //查询出该位置下对应的分类
}


/**
 *
 * @param a
 */
function changeEqc(a) {
    var lid = $("#locName").val();//获取位置id
    var cid = $(a).val();
    var url = "/eqAddBill/findEqBy/" + lid + "/" + cid;
    $.getJSON(url, function (data) {
        vdm.$set("myEqs", data);
    });
}


/**
 *导出excel
 */
function exportExcel() {
    var param = $(dataTableName).bootgrid("getSearchPhrase");
    var columnSettings = $(dataTableName).bootgrid("getColumnSettings");

    var titles = [];
    var colNames = [];
    for (var x in columnSettings) {
        if (columnSettings[x] != undefined && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }

    var docName = "设备新置申请信息";
    var url = "eqAddBill/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
    bootbox.confirm({
        message: "确定导出查询结果记录么？?",
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
                window.location.href = url;
            }
        }
    });

}
