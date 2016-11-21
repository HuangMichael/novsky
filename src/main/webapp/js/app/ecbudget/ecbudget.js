var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');

var vdm = null;
$(function () {


    var validateOptions = {
        message: '该值无效',
        fields: {
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


    mainObject = "ecbudget";
    dataTableName = "#ecBudgetDataTable";
    docName = "易耗品采购申请信息";
    formName = "#detailForm";
    searchModel = [
        {"param": "locName", "paramDesc": "位置"},
        {"param": "eqName", "paramDesc": "消耗品名称"},
        {"param": "eqClass", "paramDesc": "设备分类"}
    ];


    var locs = getMyLocs();
    // var eqClasses = findMyEqClass();


    vdm = new Vue({
        el: "#detailContainer",
        data: {
            budgetBill: null,
            locs: locs
        }
    });


    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            locs: locs
        }
    });


    initBootGrid(dataTableName);
    validateForm(validateOptions);
    initSearchDate();
    initSelect();
    search();
});


/**
 * 查询我的位置
 */
function getMyLocs() {
    var url = "/commonData/findMyLoc";
    $.getJSON(url, function (data) {
        locs = data;

        // console.log("data-----"+JSON.stringify(data));
    });
    return locs;
}