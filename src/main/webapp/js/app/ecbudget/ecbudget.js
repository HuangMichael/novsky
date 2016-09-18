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
var budgetBills = [];
var ids = [];
var pointer = 0;
$(function () {
    //设置数据有效性验证配置项


    //设置数据有效性验证配置项
    var validateOptions = {
        message: '该值无效 ',
        fields: {
            "applyDate": {
                message: '申请日期无效',
                validators: {
                    notEmpty: {
                        message: '申请日期不能为空!'
                    }

                }
            },
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
                message: '更新原因无效',
                validators: {
                    notEmpty: {
                        message: '更新原因不能为空!'
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

            "auditDate": {
                message: '填报日期无效',
                validators: {
                    notEmpty: {
                        message: '填报日期不能为空!'
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

            "locations": {
                message: '位置无效',
                validators: {
                    notEmpty: {
                        message: '位置不能为空!'
                    }
                }
            },

            "fixAdvice": {
                message: '维修建议无效',
                validators: {
                    notEmpty: {
                        message: '维修建议不能为空!'
                    }
                }
            },

            "leaderAdvice": {
                message: '领导建议无效',
                validators: {
                    notEmpty: {
                        message: '领导建议不能为空!'
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

    //初始化加载列表
    $("#budgetDataTable").bootgrid({
        selection: true,
        multiSelect: true,
        rowSelect: false,
        keepSelection: true
    }).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === bills.length) {
            selectedIds.clear();
        }
        for (var x in rows) {
            if (rows[x]["id"]) {
                selectedIds.push(rows[x]["id"]);
            }
        }
    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        for (var x in rows) {
            selectedIds.remove(rows[x]["id"]);
        }
    });
    // 监听切换tab的方法

    $(formTab).on('click', function () {

        //首先判断是否有选中的
        var budgetBill = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            budgetBill = findById(selectedIds[0]);
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

    $("select").select2({
        theme: "bootstrap"
    });

    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        save();
    });


    formTab.on('click', function () {
        // activeTab = "detail";
        //setFormReadStatus("#detailForm", formLocked);
        //首先判断是否有选中的
        var budgetBill = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            budgetBill = getBudgetBillById(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条

            console.log("ids[0]---------------" + ids[0]);
            budgetBill = getBudgetBillById(ids[0]);
            //所有的都在选中列表中
            selectedIds = (ids);
        }
        vdm.$set("budgetBill", budgetBill);
        $("#confirmReason").select2("val", [1, 2, 3]);
        $("#updateReason").select2("val", [1, 2, 3]);
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
    var url = "/ecbudget/findById/" + bid;
    $.getJSON(url, function (data) {
        budgetBill = data;
    });
    return budgetBill;
}


/**
 *  上一条
 */
function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
        return;
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var budgetBill = getBudgetBillById(selectedIds[pointer]);
        vdm.$set("budgetBill", budgetBill);
    }
}
/**
 *  下一条
 */
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");
        return;
    } else {
        pointer = pointer + 1;
        var budgetBill = getBudgetBillById(selectedIds[pointer]);
        vdm.$set("budgetBill", budgetBill);
    }
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
    //alert("add一条记录");
}
/**
 *保存或者更新
 * */
function save() {
    var objStr = getFormJsonData("detailForm");
    var budgetBill = JSON.parse(objStr);
    console.log(JSON.stringify(budgetBill));

    console.log("updateReason------------------------" + $("#updateReason").val().toString());
    console.log("confirmReason------------------------" + $("#confirmReason").val().toString());

    budgetBill.confirmReason = $("#confirmReason").val().toString();
    budgetBill.updateReason = $("#updateReason").val().toString();
    var url = "ecbudget/save";
    $.post(url, budgetBill, function (data) {
        if (data.result) {
            showMessageBox("info", data.resultDesc);
        } else {

            showMessageBox("danger", data.resultDesc);
        }
    });
}

/**
 * 编辑记录 使文本框可编辑
 */
function edit() {
    setFormReadStatus("#detailForm", false);
}


/**
 * 删除记录
 */
function del() {
    //判断选中的tab
    var bid = selectedIds[0];
    if (!bid) {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
    var url = "/ecbudget/delete/" + bid;
    if (bid) {
        var confirm = window.confirm("确定要删除该记录么？");
        if (confirm) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (msg) {
                    if (msg) {
                        showMessageBox("info", "低值易耗品采购信息删除成功!");
                        $("#budgetDataTable").bootgrid("reload");
                    }
                },
                error: function (msg) {
                    showMessageBox("danger", "低值易耗品采购信息有关联数据，无法删除，请联系管理员");
                }
            });
        }
    }
}
/**
 *根据id查询
 * */
function findById(id) {
    var budgetBill = null;
    var url = "ecbudget/findById/" + id;
    $.getJSON(url, function (data) {
        budgetBill = data;
    });

    console.log(JSON.stringify(budgetBill));
    return budgetBill;

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
