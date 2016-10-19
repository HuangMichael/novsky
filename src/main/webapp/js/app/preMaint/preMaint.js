var dataTableName = '#pmDataTable';
var pms = [];
var selectedIds = []; //获取被选择记录集合
var allSize = 0;
var pmDetail = null; //明细页面的模型
var vm = null; //明细页面的模型
var pm = null;
var locs = [];
var eqs = [];
var units = [];

var f_units = [];
var pointer = 0;
var statuses = [];
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
$.ajaxSettings.async = false;
$(function () {
    //初始化从数据库获取列表数据
    //initLoadData("/outsourcingUnit/findAll", dataTableName);

    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });

    var unit_location = "/outsourcingUnit/findAll";
    $.getJSON(unit_location, function (data) {
        units = data;
    });


    var eqs_url = "/commonData/findMyEqs";
    $.getJSON(eqs_url, function (data) {
        eqs = data;
    });

    statuses = [{
        key: 0,
        text: "未执行"
    }, {
        key: 1,
        text: "已执行"
    }];

    f_units = [{
        key: 1,
        text: "天"
    }, {
        key: 2,
        text: "周"
    }, {
        key: 3,
        text: "月"
    }, {
        key: 4,
        text: "季度"
    }, {
        key: 5,
        text: "年"
    }];

    selectedIds = getAllId();

    $(dataTableName).bootgrid({
            selection: true,
            multiSelect: true,
            rowSelect: false,
            keepSelection: true
        }
    ).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === pms.length) {
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

    $('select').select2({theme: "bootstrap"});
    // 表单ajax提交
    $('#detailForm')
        .bootstrapValidator({
            message: '该值无效 ',
            fields: {
                unitNo: {
                    message: '单位编号无效',
                    validators: {
                        notEmpty: {
                            message: '单位编号不能为空!'
                        },
                        stringLength: {
                            min: 3,
                            max: 20,
                            message: '单位编号长度为3到20个字符'
                        }
                    }
                },
                description: {
                    message: '单位名称无效',
                    validators: {
                        notEmpty: {
                            message: '单位名称不能为空!'
                        },
                        stringLength: {
                            min: 2,
                            max: 20,
                            message: '单位名称长度为2到20个字符'
                        }
                    }
                },
                "status": {
                    message: '单位状态无效',
                    validators: {
                        notEmpty: {
                            message: '单位状态不能为空!'
                        }
                    }
                }
            }
        })
        .on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();
            save();
        });


    $('#createForm')
        .bootstrapValidator({
            message: '该值无效 ',
            fields: {
                unitNo: {
                    message: '单位编号无效',
                    validators: {
                        notEmpty: {
                            message: '单位编号不能为空!'
                        },
                        stringLength: {
                            min: 3,
                            max: 20,
                            message: '单位编号长度为3到20个字符'
                        }
                    }
                },
                description: {
                    message: '单位名称无效',
                    validators: {
                        notEmpty: {
                            message: '单位名称不能为空!'
                        },
                        stringLength: {
                            min: 2,
                            max: 20,
                            message: '单位名称长度为2到20个字符'
                        }
                    }
                },
                "status": {
                    message: '单位状态无效',
                    validators: {
                        notEmpty: {
                            message: '单位状态不能为空!'
                        }
                    }
                }
            }
        })
        .on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();
            createUnit();
        });

    console.log("units--------------" + JSON.stringify(pms[0]));
    pmDetail = new Vue({
        el: "#detailForm",
        data: {
            pm: pms[0],
            locs: locs,
            units: units,
            statuses: statuses,
            eqs: eqs,
            f_units: f_units
        },
        methods: {
            previous: function (event) {
                if (pointer <= 0) {
                    showMessageBoxCenter("danger", "center", "当前记录是第一条");
                } else {
                    pointer = pointer - 1;
                    //判断当前指针位置
                    pm = getUnitByIdRomote(selectedIds[pointer]);
                    this.$set("pm", pm);
                }
            },
            next: function (event) {
                if (pointer >= selectedIds.length - 1) {
                    showMessageBoxCenter("danger", "center", "当前记录是最后一条");
                } else {
                    pointer = pointer + 1;
                    console.log("pm----------------------------" + JSON.stringify(pm));
                    pm = getUnitByIdRomote(selectedIds[pointer]);
                    this.$set("pm", pm);
                }
            }
        }
    });


    formTab.on('click', function () {
        //首先判断是否有选中的
        var pm = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            pm = getPmByIdRomote(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            pm = pms[0];
            //所有的都在选中列表中
            selectedIds = setAllInSelectedList(pms);
        }
        pmDetail.$set("pm", pm);
        setFormReadStatus("#detailForm", true);

    });
});


/*
 * @param eqs 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(pms) {
    var selecteds = [];
    for (var x in pms) {
        if (!isNaN(pms[x]["id"])) {
            selecteds.push(pms[x]["id"]);
        }
    }
    return selecteds;

}

/**
 * 根据ID获取设备信息
 * @param uid
 * @return {*}
 */
function getPmByIdRomote(pid) {
    var pm = null;
    var url = "/preMaint/findById/" + pid;
    $.getJSON(url, function (data) {
        console.log("----------------" + JSON.stringify(data));
        pm = data;
    });
    return pm;
}


function add() {

    pmDetail.$set("pm", null);
    //设置设备状态和运行状态默认值;
    formTab.tab('show');

}


/**
 * 编辑设备信息
 */
function edit() {
    setFormReadStatus("#detailForm", false);
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


function del() {
    var uid = selectedIds[0];
    var url = "/preMaint/delete/" + uid;
    if (uid) {
        var confirm = window.confirm("确定要删除该记录么？");
        if (confirm) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (msg) {
                    showMessageBoxCenter("info", "center", "预防性维修信息删除成功");
                },
                error: function (msg) {
                    showMessageBoxCenter("danger", "center", "删除失败，请联系管理员操作!");
                }
            });
        } else {
            showMessageBoxCenter("danger", "center", "请选中一条记录再操作");

        }
    }
}


function createUnit() {
    var objStr = getFormJsonData("createForm");
    var outsourcingUnit = JSON.parse(objStr);
    console.log(JSON.stringify(outsourcingUnit));
    var url = "/outsourcingUnit/save";
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
 * 保存信息
 */
function save() {
    var objStr = getFormJsonData("detailForm");
    var pm = JSON.parse(objStr);
    console.log(JSON.stringify(pm));
    var url = "/preMaint/save";
    $.post(url, pm, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("dander", data["resultDesc"]);
        }
    });
}


/**
 * 上一条
 */
function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");

    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var pm = getPmByIdRomote(selectedIds[pointer]);
        pmDetail.$set("pm", pm);

    }

}
/**
 * 下一条
 */
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");
    } else {
        pointer = pointer + 1;
        var pm = getPmByIdRomote(selectedIds[pointer]);
        pmDetail.$set("pm", pm);
    }
}


