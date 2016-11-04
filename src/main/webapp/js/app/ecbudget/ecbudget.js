var bills = [];
var selectedIds = [];
//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
formTab = $('#myTab li:eq(1) a');
var vdm = null;
//位置信息
var locs = [];
var eqClasses = [];
dataTableName = "#ecBudgetDataTable";
formName = "#detailForm";
mainObject = "ecbudget";
docName = "易耗品采购申请信息";
var ids = [];
var pointer = 0;
$(function () {
    //设置数据有效性验证配置项


    //设置数据有效性验证配置项
    var validateOptions = {
        message: '该值无效',
        fields: {
            /*            "applyDate": {
             message: '申请日期无效',
             validators: {
             notEmpty: {
             message: '申请日期不能为空!'
             }

             }
             },*/
            "ecname": {
                message: '易耗品名称无效',
                validators: {
                    notEmpty: {
                        message: '易耗品名称不能为空!'
                    }

                }
            },
            "epermited": {
                message: '有无用电许可证无效',
                validators: {
                    notEmpty: {
                        message: '有无用电许可证不能为空!'
                    }

                }
            },
            "updateReason": {
                message: '申请原因无效',
                validators: {
                    notEmpty: {
                        message: '申请原因不能为空!'
                    }

                }
            },
            "confirmReason": {
                message: '确认更新原因无效',
                validators: {
                    notEmpty: {
                        message: '确认更新原因不能为空!'
                    }

                }
            },
            "applicant": {
                message: '填报人无效',
                validators: {
                    notEmpty: {
                        message: '填报人不能为空!'
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

            "auditor": {
                message: '填报人无效',
                validators: {
                    notEmpty: {
                        message: '填报人不能为空!'
                    }
                }
            },

            /*"auditDate": {
             message: '审核日期无效',
             validators: {
             notEmpty: {
             message: '审核日期不能为空!'
             }
             }
             },*/

            "amount": {
                message: '申请数量无效',
                validators: {
                    notEmpty: {
                        message: '申请数量不能为空!'
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

            "fixAdvice": {
                message: '维修意见无效',
                validators: {
                    notEmpty: {
                        message: '维修意见不能为空!'
                    }
                }
            },

            "leaderAdvice": {
                message: '领导意见无效',
                validators: {
                    notEmpty: {
                        message: '领导意见不能为空!'
                    }
                }
            },
            "eqclass": {
                message: '设备分类无效',
                validators: {
                    notEmpty: {
                        message: '设备分类不能为空!'
                    }
                }
            }
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

    initBootGrid(dataTableName);


    $(formTab).on('click', function () {

        showDetail();
        vdm.$set("locs", locs);
        vdm.$set("eqClass", eqClass);


    });


    initSelect();


    $(formName)
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveMainObject(formName);
    });


    formTab.on('click', function () {

    });
});


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
    //alert("add一条记录");
}

/**
 *保存或者更新
 * */
function save() {
    $("#saveBtn").trigger("click");
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
    var url = "ecbudget/findAllIds";
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



