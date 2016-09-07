/**
 * Created by Administrator on 2016/8/15.
 */

var dataTableName = '#stationListTable';
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
    initLoadData("/station/findActiveStation", dataTableName);
    var url = "/line/findLines";
    $.getJSON(url, function (data) {
        lines = data;
    });

    types = [{"id": "1", "typeName": "站区"}, {"id": "2", "typeName": "段区"}];


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

    //点击显示角色详细信息
    formTab.on('click',
        function () {
            activeTab = "detail";
            setFormReadStatus("#detailForm", formLocked);
            //首先判断是否有选中的
            var station = null;
            if (selectedIds.length > 0) {
                station = getStationByIdInStations(selectedIds[0]);
            } else {
                //没有选中的 默认显示整个列表的第一条
                station = getStationByIdInStations(stations[0]["id"]);
                selectedIds = setAllInSelectedList(stations);
            }
            vdm.station = station;

        });

    listTab.on('click',
        function () {
            activeTab = "list";
        });
    $('select').select2({
        theme: "bootstrap"
    });
});


function del() {

    var selectedId = vdm.station.id

    var url = "/station/delete/" + selectedId;


    $.getJSON(url,function(data){


    });
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


