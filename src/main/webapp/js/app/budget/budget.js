var bills = [];
var selectedIds = [];
//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
var vdm = null;
//位置信息
var locs = [];
var eqClasses = [];

var ids = [];
var pointer = 0;
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
            "vlocations.id": {
                message: '位置无效',
                validators: {
                    notEmpty: {
                        message: '位置不能为空!'
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

    vdm = new Vue({
        el: "#detailContainer",
        data: {
            budgetBill: null,
            locs: locs,
            eqClasses: eqClasses
        }
    });

    ids = findAllIds().sort();

    mainObject = "budget";
    dataTableName = "#budgetDataTable";
    docName = "采购申请信息";
    searchModel = [
        {"param": "beginDate", "paramDesc": "申请日期"},
        {"param": "endDate", "paramDesc": "申请日期"},
        {"param": "accessoryName", "paramDesc": "配件名称"},
        {"param": "applyDep", "paramDesc": "申请部门"}
    ];
    initBootGrid(dataTableName);
    initSearchDate();
    search();
    // 监听切换tab的方法

    $(formTab).on('click', function () {

        //首先判断是否有选中的
        var budgetBill = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            budgetBill = getBudgetBillById(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            budgetBill = getBudgetBillById(ids[0]);
            //所有的都在选中列表中
            selectedIds = ids;
        }
        vdm.$set("budgetBill", budgetBill);
        vdm.$set("locs", locs);
        vdm.$set("eqClass", eqClass);

    });

    initSelect();
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveMainObject();
    });

    formTab.on('click', function () {
        //首先判断是否有选中的
        var budgetBill = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            budgetBill = getBudgetBillById(selectedIds[0]);
        } else {
            budgetBill = getBudgetBillById(ids[0]);
            selectedIds = (ids);
        }
        vdm.$set("budgetBill", budgetBill);
        setFormReadStatus("#detailForm", true);
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


/**
 * 根据ID获取
 * @param bid
 * @return {*}
 */
function getBudgetBillById(bid) {
    var budgetBill = null;
    var url = "/budget/findById/" + bid;
    $.getJSON(url, function (data) {
        budgetBill = data;
    });
    return budgetBill;
}

function add() {
    //重新建立模型 新建对象模型
    var newVue = new Vue({
        el: "#detailContainer",
        locs: locs,
        eqClasses: eqClasses
    });
    setFormReadStatus("#detailForm", false);
    clearForm("#detailForm");
    formTab.tab('show');
    formTab.data("status", "add");
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
 *获取服务器时间
 * */
function getServerDate() {

    var today = null;
    var url = "/commonData/getServerDate";
    $.getJSON(url, function (data) {
        today = data;
    });
    return today;
}


/**
 *查询所有的id
 * */
function findAllIds() {

    var ids = [];
    var url = "budget/findAllIds";
    $.getJSON(url, function (data) {
        ids = data;
    });
    return ids;
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

