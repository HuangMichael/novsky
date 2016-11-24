/**
 * Created by Administrator on 2016/11/4.
 */


var mainObject = "";
var exportObject = mainObject;
var vdm = null; //定义form数据模型
var formName = "";
var selectedIds = [];
var pointer = null;
var dataTableName = "";
var ids = [];//所有的ID的集合
var docName = "";
var formTab = null;
var locs = [];
var stations = [];
var searchModel = [];

/**
 *
 * @returns {string}
 * 获取主对象
 */
function getMainObject() {
    return mainObject;
}


function getDataTableName() {
    return dataTableName;
}


/**
 *
 * @param validationConfig
 */
function validateForm(validationConfig) {
    $(formName)
        .bootstrapValidator(validationConfig)
        .on('success.form.bv', function (e) {
            e.preventDefault();
            saveMainObject(formName);
        });
}

/**
 *保存或者更新 后刷新数据列表
 * */
function saveMainObject(formName) {
    var objStr = getFormDataAsJSON(formName);
    var object = JSON.parse(objStr);
    console.log("save" + JSON.stringify(object));
    var url = getMainObject() + "/save";
    $.post(url, object, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            setFormReadStatus(formName, true);
            formTab.data("status", "saved");
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"]);
            setFormReadStatus(formName, false);
        }
    });
}


function saveTree(formName, childZNode) {
    var objStr = getFormJsonData(formName);
    var object = JSON.parse(objStr);
    var url = mainObject + "/save";

    $.ajax({
        type: "POST", url: url, data: object, dataType: "JSON", success: function (obj) {

            if (object.id) {
                updateNode(null, childZNode);
                showMessageBox("info", "信息更新成功");
            } else {
                addNodeAfterChangeOperation(null, childZNode, parent);
                showMessageBox("info", "信息添加成功")
            }
        }, error: function (msg) {
            if (object.id) {
                showMessageBox("danger", "信息更新失败")
            } else {
                showMessageBox("danger", "信息添加失败")
            }
        }
    })
}


/**
 *根据id查询返回对象
 * */
function findById(id) {
    var mainObject = getMainObject();
    var object = null;
    var url = mainObject + "/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *  上一条 记录
 */
function backwards() {
    console.log("from crud----backwards");
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var object = findById(selectedIds[pointer]);
        vdm.$set(getMainObject(), object);
    }
}
/**
 *  下一条记录
 */
function forwards() {
    console.log("from crud----forwards");
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        var object = findById(selectedIds[pointer]);
        vdm.$set(getMainObject(), object);
    }
}


/**
 * 新增预防性维修计划
 */
function add() {
    setFormReadStatus(formName, false);
    vdm.$set(mainObject, null);
    //设置设备状态和运行状态默认值;
    formTab.tab('show');

}


/**
 * 编辑记录 使文本框可编辑
 */
function edit() {
    setFormReadStatus(formName, false);
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
    //删除时判断当前form的状态
    var status = formTab.data("status");
    if (status == "add") {
        showMessageBoxCenter("danger", "center", "新建记录未保存，无需删除该记录!");
        return;
    }
    //判断选中的tab
    var bid = selectedIds[0];
    if (!bid) {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
    var url = getMainObject() + "/delete/" + bid;
    if (bid) {
        bootbox.confirm({
            message: "确定要删除该记录么?",
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
                                showMessageBox("info", "信息删除成功!");
                                $(dataTableName).bootgrid("reload");
                            }
                        },
                        error: function (msg) {
                            showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
                        }
                    });
                }
            }
        });
    }
}


/**
 * 显示明细信息
 */
function showDetail() {
    var object = null;
    if (selectedIds.length > 0) {
        //切换tab时默认给detail中第一个数据
        object = findById(selectedIds[0]);
    } else {
        //没有选中的 默认显示整个列表的第一条
        object = findById(selectedIds[0]);
        //所有的都在选中列表中
        // selectedIds = (ids);
    }
    vdm.$set(getMainObject(), object);
    setFormReadStatus(formName, true);
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

/**
 *查询所有的id
 * */
function findAllRecordId() {
    var url = getMainObject() + "/findAllIds";
    $.getJSON(url, function (data) {
        ids = data;
    });
    return ids;
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
        if (columnSettings[x] != undefined && columnSettings[x]["text"] != "" && columnSettings[x]["id"] != "" && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }
    docName = (docName) ? (docName) : ("导出数据");
    var url = getMainObject() + "/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
    url = url.trim();
    bootbox.confirm({
        message: "确定导出查询结果记录么?",
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


/**
 *导出excel
 */
function exportExcelByName(dataTableName, nodeState, docName) {
    var param = $(dataTableName).bootgrid("getSearchPhrase");
    var columnSettings = $(dataTableName).bootgrid("getColumnSettings");

    var titles = [];
    var colNames = [];
    for (var x in columnSettings) {
        if (columnSettings[x] != undefined && columnSettings[x]["text"] != "" && columnSettings[x]["id"] != "" && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }
    var url = getMainObject() + "/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames + "&nodeState=" + nodeState;
    url = url.trim();
    bootbox.confirm({
        message: "确定导出查询结果记录么?",
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


/**
 *  初始化下拉选择组件
 */
function initSelect() {


    $("select").select2({
        theme: "bootstrap",
        tags: "true",
        placeholder: "请选择...",
        allowClear: true
    });
}


/**
 * 初始化bootgrid表格 并监听选择时间
 */
function initBootGrid(dataTableName) {
    var config = {
        selection: true,
        multiSelect: true,
        sorting: true
    }
    //初始化加载列表
    $(dataTableName).bootgrid(config).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === 0) {
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
}


/**
 * 初始化bootgrid表格 并监听选择时间
 */
function initBootGridMenu(dataTableName, config) {

    if (!config) {

        console.log("use default config-------------");
        config = {
            selection: true,
            multiSelect: true,
            sorting: true,
            rowSelect: true,
            keepSelection: true,
            navigation: 0

        }
    }
    //初始化加载列表
    $(dataTableName).bootgrid(config).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === 0) {
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
}


/**
 * 查询我的位置
 */
function getMyLocs() {
    var url = "/commonData/findMyLoc";
    $.getJSON(url, function (data) {
        locs = data;
    });
    return locs;
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
function findEqClass() {
    var url_eqclass = "/commonData/findEqClass";
    $.getJSON(url_eqclass, function (data) {
        eqClasses = data;
    });
    return eqClasses;
}

/**
 * 查询我的位置
 */
function findMyEqs() {
    var url = "/commonData/findMyEqs";
    $.getJSON(url, function (data) {
        locs = data;
    });
    return locs;
}

/**
 * 查询所有线路
 */
function getAllLines() {
    var url = "/line/findAllLines";
    $.getJSON(url, function (data) {
        lines = data;
    });
    return lines;
}


/**
 * 查询我的位置
 */
function getAllStations() {
//初始化为第一条线路id
    var url = "/station/findByStatus";
    $.getJSON(url, function (data) {
        stations = data;
    });
    return stations;
}


function search() {
    //组装模型
    var params = $("#searchBox :input");
    var searchParams = "";
    $.each(params, function (i, p) {
        var id = $(p).attr("id");
        if (!$(p).is(":button")) {
            var value = $(p).val().trim();
            searchParams += value + ",";
        }

    });

    console.log("sort-----------" + JSON.stringify($(dataTableName).bootgrid("getSortDictionary")));
    ;

    $(dataTableName).bootgrid("setSearchPhrase", searchParams).bootgrid("reload");
}


/**
 * 初始化查询起始日期
 */
function initSearchDate() {
    $("#beginDate ").val(addMonthToday(-3, new Date()));
    $("#endDate").val(transformYMD(new Date().getTime()));
}


$(function () {

    //监听回车查询
    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            $("#searchBtn").click();
        }
    });

});