var dataTableName = '#pmDataTable';
var units = [];
var selectedIds = []; //获取被选择记录集合
var allSize = 0;
var unitDetail = null; //明细页面的模型
var vm = null; //明细页面的模型
var unit = null;

var pointer = 0;
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
$.ajaxSettings.async = false;
$(function () {
    //初始化从数据库获取列表数据
    //initLoadData("/outsourcingUnit/findAll", dataTableName);

    $("#dataTableName").bootgrid();

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
            saveUnit();
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

    console.log("units--------------" + JSON.stringify(units[0]));
    unitDetail = new Vue({
        el: "#detailForm",
        data: {
            unit: units[0]
        },
        methods: {
            previous: function (event) {
                if (pointer <= 0) {
                    showMessageBoxCenter("danger", "center", "当前记录是第一条");

                } else {
                    pointer = pointer - 1;
                    //判断当前指针位置

                    console.log("unit----------------------------" + JSON.stringify(unit));
                    unit = getUnitByIdRomote(selectedIds[pointer]);

                    console.log("unit---------------" + unit);
                    this.$set("unit", unit);
                }
            },
            next: function (event) {
                if (pointer >= selectedIds.length - 1) {
                    showMessageBoxCenter("danger", "center", "当前记录是最后一条");

                } else {
                    pointer = pointer + 1;
                    console.log("unit----------------------------" + JSON.stringify(unit));
                    unit = getUnitByIdRomote(selectedIds[pointer]);
                    this.$set("unit", unit);
                    //loadFixHistoryByEid(selectedIds[pointer]);
                }
            },
            checkUnitNo: function () {
                var unitNo = unitDetail.$get("unit.unitNo");
                if (checkUnitNo(unitNo)) {
                    showMessageBoxCenter("danger", "center", "单位编号不能重复");

                }
            }
        }
    });


    $('#myTab li:eq(2) a').on('click', function () {
        //首先判断是否有选中的
        var unit = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            unit = getUnitByIdRomote(selectedIds[0]);
            console.log("单位名称----" + unit.description);
        } else {
            //没有选中的 默认显示整个列表的第一条
            unit = units[0];
            //所有的都在选中列表中
            selectedIds = setAllInSelectedList(units);
        }
        unitDetail.$set("unit", unit);

    });

});


/*
 * @param eqs 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(units) {
    var selecteds = [];
    for (var x in units) {
        if (!isNaN(units[x]["id"])) {
            selecteds.push(units[x]["id"]);
        }
    }
    return selecteds;

}

/**
 * 根据ID获取设备信息
 * @param eqs 设备信息集合
 * @param eid 设备ID
 */
function getUnitByIdRomote(eid) {
    var unit = null;
    var url = "/outsourcingUnit/findById/" + eid;
    console.log("url-----------------" + url);
    $.getJSON(url, function (data) {
        unit = data;
    });
    return unit;
}

/**
 * 根据ID获取设备信息
 * @param uid
 * @return {*}
 */
function findUnitByIdLocal(uid) {
    var unit = null;
    for (var i in units) {
        if (units[i].id == uid) {
            unit = units[i];
            break;
        }
    }
    return unit;
}


formTab.on('click', function () {
    //首先判断是否有选中的
    var unit = null;
    if (selectedIds.length > 0) {
        //切换tab时默认给detail中第一个数据
        unit = findUnitByIdLocal(selectedIds[0]);
    } else {
        //没有选中的 默认显示整个列表的第一条
        unit = units[0];
        //所有的都在选中列表中
        selectedIds = setAllInSelectedList(units);
    }
    unitDetail.$set("unit", unit);

});


function add() {

    unitDetail.$set("unit", null);
    //设置设备状态和运行状态默认值
    unitDetail.$set("unit.status", 1);
    formTab.tab('show');

}


/**
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[0];
    var unit = findUnitByIdLocal(uid);
    if (uid) {
        unitDetail.$set("unit", unit);
        formTab.tab('show');

    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");

    }
}


/**
 * 保存设备信息
 */
function save() {
    $("#saveBtn").trigger("click");
}


function deleteUnit() {
    var uid = selectedIds[0];
    var url = "/outsourcingUnit/delete/" + uid;
    if (uid) {
        var confirm = window.confirm("确定要删除该记录么？");
        if (confirm) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (msg) {
                    showMessageBoxCenter("info", "center", "外委单位信息删除成功 ");
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
function saveUnit() {
    var objStr = getFormJsonData("detailForm");
    var outsourcingUnit = JSON.parse(objStr);
    console.log(JSON.stringify(outsourcingUnit));
    var url = "/outsourcingUnit/save";
    $.ajax({
        type: "post",
        url: url,
        data: outsourcingUnit,
        dataType: "json",
        success: function (data) {
            if (outsourcingUnit.id) {
                showMessageBox("info", "外委单位信息更新成功");
            } else {
                showMessageBox("info", "外委单位信息添加成功");
            }
        }, error: function (data) {
            if (outsourcingUnit.id) {
                showMessageBox("danger", "外委单位信息更新失败");
            } else {
                showMessageBox("info", "外委单位信息添加失败");
            }
        }
    });
}


/**
 *
 * @param url 数据接口路径
 * @param elementName 渲染元素名称
 */
function initLoadData(url, elementName) {
    console.log("初始化载入列表数据---" + url);
    $.getJSON(url, function (data) {
        units = data;
        allSize = data.length; //计算所有记录的个数
        if (dataTableName) {
            vm = new Vue({
                el: elementName,
                data: {
                    units: units
                }
            });
            //ajax载入设备信息  并且监听选择事件
            $(dataTableName).bootgrid({
                ajaxSettings: {
                    method: "GET",
                    cache: false
                },
                selection: true,
                multiSelect: true,
                rowSelect: true,
                keepSelection: true

            }).on("selected.rs.jquery.bootgrid", function (e, rows) {
                //如果默认全部选中
                if (selectedIds.length === units.length) {
                    selectedIds.clear();
                }
                for (var x in rows) {
                    if (rows[x]["id"]) {
                        selectedIds.push(rows[x]["id"]);
                    }
                }
                console.log("选择后的ID列表----------------" + selectedIds);
            }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
                for (var x in rows) {
                    selectedIds.remove(rows[x]["id"]);
                }
                console.log("取消选中后的ID列表----------------" + selectedIds);
            });
        }
    });
}


/**
 *
 * @param eqCode 设备编号
 * @returns {boolean} 检查设备编号是否唯一
 */
function checkUnitNo(unitCode) {
    var exists = false;
    var url = "/outSourcingUnit/unitNoExists/" + unitCode;
    $.getJSON(url, function (data) {
        exists = data
    });
    return exists;
}

function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");

    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var e = getUnitByIdRomote(selectedIds[pointer]);
        unitDetail.$set("unit", e);
        //  loadFixHistoryByEid(selectedIds[pointer]);
    }

}
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        var e = getUnitByIdRomote(selectedIds[pointer]);
        unitDetail.$set("unit", e);
        //loadFixHistoryByEid(selectedIds[pointer]);
    }
}


