/**
 * Created by Administrator on 2016/8/15.
 */


var stations = [];
var selectedIds = []; //获取被选择记录集合
var locs = [];
var allSize = 0;
var vdm = null; //明细页面的模型
var vm = null; //明细页面的模型
var formLocked = true;
var activeTab = "list";
//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
//维修历史列表
var pointer = 0;
var lines = [];
var types = [];
var selectedId = [];
$.ajaxSettings.async = false;
var validateOptions = {
    message: '该值无效 ',
    fields: {
        stationName: {
            message: '用户名号无效',
            validators: {
                notEmpty: {
                    message: '用户名!'
                },
                stringLength: {
                    min: 3,
                    max: 20,
                    message: '用户名长度为3到20个字符'
                }
            }
        }
    }
};

/**
 *
 * @param url 数据接口路径
 * @param elementName 渲染元素名称
 */
function initLoadData(url, elementName) {
    $.getJSON(url,
        function (data) {
            stations = data;
            allSize = data.length; //计算所有记录的个数
            if (dataTableName) {
                vm = new Vue({
                    el: elementName,
                    data: {
                        stations: stations,
                        types: types
                    }
                });
                //ajax载入设备信息  并且监听选择事件
                $(dataTableName).bootgrid({
                    selection: true,
                    multiSelect: true,
                    rowSelect: false,
                    keepSelection: true
                }).on("selected.rs.jquery.bootgrid",
                    function (e, rows) {
                        //如果默认全部选中
                        if (selectedIds.length === stations.length) {
                            selectedIds.clear();
                        }
                        for (var x in rows) {
                            if (rows[x]["id"]) {
                                selectedIds.push(rows[x]["id"]);
                            }
                        }
                    }).on("deselected.rs.jquery.bootgrid",
                    function (e, rows) {
                        for (var x in rows) {
                            selectedIds.remove(rows[x]["id"]);
                        }
                    });
            }
        })
}
/**
 * 根据ID获取设备信息
 * @param stations 设备信息集合
 * @param eid 设备ID
 */
function getStationByIdInStations(eid) {
    var station = null;
    var url = "/station/findById/" + eid;
    $.getJSON(url,
        function (data) {
            station = data;
        });
    return station;
}

/**
 *
 * @param stations 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(stations) {
    var selecteds = [];
    for (var x in stations) {
        if (!isNaN(stations[x]["id"])) {
            selecteds.push(stations[x]["id"]);
        }
    }
    return selecteds;

}

/**
 *  上一条
 */
function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
        return;
    } else {
        //  pointer = pointer - 1;
        //判断当前指针位置
        var station = getStationByIdInStations(selectedIds[--pointer]);
        vdm.station = station;


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
        var station = getStationByIdInStations(selectedIds[++pointer]);
        vdm.station = station;

    }
}

/**
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[pointer];
    var station = getStationByIdInStations(uid);
    if (uid) {
        vdm.$set("station", station);
        formTab.tab('show');
        setFormReadStatus("#detailForm", false, null);
    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
}

function saveUser() {
    $("#saveBtn").trigger("click");
}

/**
 *
 * @param formId 设置form为只读
 */
function setFormReadStatus(formId, formLocked, except) {
    if (formLocked) {
        $(formId + " input").attr("readonly", "readonly");
        $(formId + " select").attr("disabled", "disabled");
    } else {
        $(formId + " input").attr("readonly", "readonly").removeAttr("readonly");
        $(formId + " select").attr("disabled", "disabled").removeAttr("disabled");
        // $(formId + " #status").attr("disabled", "disabled");
        for (var x in except) {
            $("#" + except[x]).attr("readonly", "readonly");
        }
    }
}

$(function () {

    mainObject = "station";
    dataTableName = '#stationListTable';
    formName = "#detailForm";
    searchModel = [
        {
            "param": "stationNo",
            "paramDesc": "车站编号"
        }, {"param": "description", "paramDesc": "车站名称"}, {"param": "line", "paramDesc": "线路"}];

    initBootGrid(dataTableName);
    initSelect();
    search();


    var url = "/line/findLines";
    $.getJSON(url, function (data) {
        lines = data;
    });

    types = [{"id": "1", "typeName": "站区"}, {"id": "2", "typeName": "段区"}];



    console.log(JSON.stringify(lines));
    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            lines: lines
        }
    });


    $('#detailForm').bootstrapValidator(validateOptions).on('success.form.bv',
        function (e) {
            e.preventDefault();

        });

    vdm = new Vue({
        el: "#detailForm",
        data: {
            station: null,
            lines: lines,
            types: types
        }
    });


});


/**
 * 根据id删除
 */
function del() {
    var selectedId = selectedIds[0];
    if (!selectedId) {
        showMessageBox("danger", "请选择一条后再进行删除!");
    }
    var url = "/station/delete/" + selectedId;
    if (selectedId) {
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
                                showMessageBox("info", "车站信息删除成功!");
                                $("tr[data-row-id='" + msg["resultDesc"] + "']").remove();
                            }
                        },
                        error: function (msg) {
                            showMessageBox("danger", "车站信息有关联数据，无法删除，请联系管理员");
                        }
                    });
                }
            }
        });
    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");

    }


}

function save() {
    var stationId = $("#stationId").val();
    var stationNo = $("#stationNo").val();
    var description = $("#description").val();
    var lineId = $("#lineId").val();
    var type = $("#type").val();
    var status = "1";

    if (stationId) {
        var url = "/station/update";
    } else {
        var url = "/station/save";
    }
    var station = {
        stationId: stationId,
        stationNo: stationNo,
        description: description,
        lineId: lineId,
        type: type,
        status: status
    };

    console.log("----------------------" + JSON.stringify(station));
    $.post(url, station,
        function (data) {
            if (data) {
                showMessageBox("info", "车站信息保存成功！");
            } else {
                showMessageBox("danger", "车站信息保存失败！");
            }

        });
}

function showStation(stationId) {
    var url = "/station/detail/" + stationId;
    $("#contentDiv").load(url,
        function () {

        });
}

/**
 *  ajax加载新增页面
 */
function add() {
    $("#tab_1_1").load("/station/create");
    var newVue = new Vue({
        el: "#createForm",
        data: {
            station: null,
            lines: lines,
            types: types
        }
    });
    formTab.tab('show');
}

/**
 * 根据ID获取设备信息
 * @param stations 设备信息集合
 * @param eid 设备ID
 */
function getStationByIdInStations(eid) {
    var station = null;
    var url = "/station/findById/" + eid;
    $.getJSON(url,
        function (data) {
            station = data;
        });
    return station;
}

function getAllStations() {
    var stations = null;
    var url = "/station/findActiveStation";
    $.getJSON(url,
        function (data) {
            stations = data;
        });
    return stations;
}


/**
 *导出excel
 */
function exportExcel() {
    var description = $(dataTableName).bootgrid("getSearchPhrase");
    var columnSettings = $(dataTableName).bootgrid("getColumnSettings");

    var titles = [];
    var colNames = [];
    for (var x in columnSettings) {
        if (columnSettings[x] != undefined && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }

    var docName = "车站信息";
    var url = "station/exportExcel?param=" + description + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
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


