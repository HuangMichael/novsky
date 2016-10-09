var dataTableName = '#pmDataTable';
var eqs = [];
var locs = [];
var eqClasses = [];
var eqStatuses = [];
var runStatus = [];
var selectedIds = []; //获取被选择记录集合
var allSize = 0;
var vdm = null; //明细页面的模型
var formLocked = true;

var pmStatuses = [];

var eqClasses = [];

var units = [];
var activeTab = "list";
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
var pointer = 0;
$.ajaxSettings.async = false;


var validateOptions = {
    message: '该值无效 ',
    fields: {
        "pmCode": {
            message: '编号无效',
            validators: {
                notEmpty: {
                    message: '编号不能为空!'
                },
                stringLength: {
                    min: 6,
                    max: 20,
                    message: '编号长度为6到20个字符'
                }
            }
        },
        "description": {
            message: '描述无效',
            validators: {
                notEmpty: {
                    message: '描述不能为空!'
                },
                stringLength: {
                    min: 2,
                    max: 20,
                    message: '设备描述长度为2到20个字符'
                }
            }
        },
        "locations.id": {
            message: '设备位置无效',
            validators: {
                notEmpty: {
                    message: '设备位置不能为空!'
                }
            }
        }
    }
};
$(function () {
    //初始化从数据库获取列表数据


    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });


    var url = "/commonData/findVEqClass";
    $.getJSON(url, function (data) {
        eqClasses = data;
    });


    pmStatuses = [
        {value: 1, text: "启动", selected: "selected"},
        {value: 0, text: "停止"}];

    units = [
        {value: 1, text: "日"},
        {value: 2, text: "周"},
        {value: 3, text: "月"},
        {value: 4, text: "年", selected: "selected"}
    ];


    //ajax载入设备信息  并且监听选择事件
    $(dataTableName).bootgrid({
            selection: true,
            multiSelect: true,
            rowSelect: false,
            keepSelection: true

        }
    ).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === eqs.length) {
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

    formTab.on('click', function () {


        activeTab = "detail";
        setFormReadStatus("#detailForm", formLocked);
        //首先判断是否有选中的
        var pm = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            console.log("选中---------" + selectedIds[0]);
            pm = getEntityById(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            //所有的都在选中列表中
            selectedIds = setAllInSelectedList();
            pm = getEntityById(selectedIds[0]);
            console.log("未选中---------" + selectedIds[0]);
        }

        vdm = new Vue({
            el: "#detailForm",
            data: {

                pm: pm,
                locs: locs,
                pmStatuses: pmStatuses,
                units: units,
                eqClasses: eqClasses
            }


        });

        //  vdm.$set("pm", pm);

    });


    $("select").select2({

        theme: "bootstrap"
    })
});


function add() {
    setFormReadStatus("#detailForm", false);
   // vdm.$set("pm", null);
    formTab.tab('show');
}


/**
 *  ajax加载新增页面
 */
function loadNew() {
    $("#tab_1_1").load("/equipment/create");
    formTab.tab('show');

}


/**
 * 保存设备信息
 */
var saveIndex = 0;
function save() {
    var objStr = getFormJsonData("detailForm");

    console.log("objStr-----------" + objStr);
    return;

}


function createEquipment() {
    var objStr = getFormJsonData("createForm");
    var equipments = JSON.parse(objStr);
    var id = equipments.id;
    var eqCode = equipments.eqCode;
    var purchasePrice = equipments.purchasePrice;
    var description = equipments.description;
    var manager = equipments.manager;
    var maintainer = equipments.maintainer;
    var productFactory = equipments.productFactory;
    var imgUrl = equipments.imgUrl;
    var originalValue = equipments.originalValue;
    var netValue = equipments.netValue;
    var purchaseDate = equipments.purchaseDate;
    var locations_id = equipments["locations.id"];
    var equipmentsClassification_id = equipments["equipmentsClassification.id"];
    var status = equipments.status;
    var eqModel = equipments.eqModel;
    var assetNo = equipments.assetNo;
    var manageLevel = equipments.manageLevel;
    var running = equipments.running;
    var warrantyPeriod = equipments.warrantyPeriod;
    var setupDate = equipments.setupDate;
    var productDate = equipments.productDate;
    var runDate = equipments.runDate;
    var expectedYear = equipments.expectedYear;
    var url = "/equipment/save";
    if (id && saveIndex > 0) {
        saveIndex = 0;
        return;
    }
    var data = {
        id: id,
        eqCode: eqCode,
        description: description,
        manager: manager,
        maintainer: maintainer,
        productFactory: productFactory,
        imgUrl: imgUrl,
        originalValue: originalValue,
        netValue: netValue,
        description: description,
        purchasePrice: purchasePrice,
        purchaseDate: purchaseDate,
        locations_id: locations_id,
        equipmentsClassification_id: equipmentsClassification_id,
        status: status,
        eqModel: eqModel,
        assetNo: assetNo,
        manageLevel: manageLevel,
        running: running,
        warrantyPeriod: warrantyPeriod,
        setupDate: setupDate,
        productDate: productDate,
        runDate: runDate,
        expectedYear: expectedYear
    };
    $.ajax({
        type: "POST", url: url, data: data, dataType: "JSON", success: function (msg) {
            if (id) {
                showMessageBox("info", "设备信息更新成功");
                changeValue(msg);
            } else {
                showMessageBox("info", "设备信息添加成功");
                refresh(msg);
            }
            //更新detailForm数据模型
            vdm.$set("equipments", msg);
            if (msg.warrantyPeriod) {
                vdm.$set("equipments.warrantyPeriod", transformYMD(msg.warrantyPeriod));
            }
            if (msg.purchaseDate) {
                vdm.$set("equipments.purchaseDate", transformYMD(msg.purchaseDate));
            }
            if (msg.setupDate) {
                vdm.$set("equipments.setupDate", transformYMD(msg.setupDate));
            }
            if (msg.productDate) {
                vdm.$set("equipments.productDate", transformYMD(msg.productDate));
            }
            if (msg.runDate) {
                vdm.$set("equipments.runDate", transformYMD(msg.runDate));
            }
            $("#detailForm #id").val(msg.id);

            saveIndex++;
        }
        ,
        error: function (msg) {
            if (id) {
                showMessageBox("danger", "设备信息更新失败")
            } else {
                showMessageBox("danger", "设备信息添加失败")
            }
        }
    })
}
/**
 *
 * @param url 数据接口路径
 * @param elementName 渲染元素名称
 */
function initLoadData(url, elementName) {
    $.getJSON(url, function (data) {
        eqs = data;
        allSize = data.length; //计算所有记录的个数
        if (dataTableName) {
            vm = new Vue({
                el: elementName,
                data: {
                    eqs: eqs
                }

            });
            //ajax载入设备信息  并且监听选择事件
            $(dataTableName).bootgrid({
                    selection: true,
                    multiSelect: true,
                    rowSelect: false,
                    keepSelection: true,
                    formatters: {
                        "report": function (column, row) {
                            return '<a class="btn btn-default btn-xs"  onclick="report(' + row.id + ')" title="报修" ><i class="glyphicon glyphicon-wrench"></i></a>'
                                + '<a class="btn btn-default btn-xs"  onclick="eqUpdate(' + row.id + ')" title="更新" ><i class="glyphicon glyphicon-retweet"></i></a>'
                        }
                    }
                }
            ).on("selected.rs.jquery.bootgrid", function (e, rows) {
                //如果默认全部选中
                if (selectedIds.length === eqs.length) {
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
    })
}
/**
 * 根据ID获取设备信息
 * @param eqs 设备信息集合
 * @param eid 设备ID
 */
function getEntityById(eid) {
    var pm = null;
    var url = "/preMaint/findById/" + eid;
    $.getJSON(url, function (data) {
        pm = data;
    });
    return pm;
}

/**
 *
 * @param eqs 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList() {
    var selecteds = [];
    //全选所有id
    var url = "preMaint/selectAllId";
    $.getJSON(url, function (data) {
        selecteds = data;
    });
    return selecteds;

}


/**
 * 根据设备ID载入维修历史信息
 */
function loadFixHistoryByEid(eid) {
    var url = "/equipment/getFixStepsByEid/" + eid;
    var histories = [];
    $.getJSON(url, function (data) {
        histories = data;
    });
    return histories;
}


/**
 * 根据设备ID查询设备更新信息s
 * @param eid
 * @return {Array}
 */
function loadUpdateHistoryByEid(eid) {
    var url = "/equipment/getUpdateHistoryById/" + eid;
    var records = [];
    $.getJSON(url, function (data) {
        records = data;
    });
    return records;
}


/**
 * 根据设备ID查询设备更新信息s
 * @param eid
 * @return {Array}
 */
function loadRecordsByEid(eid) {

    console.log("eid--------------" + eid);
    var url = "/equipment/getRecordsById/" + eid;
    var records = [];
    $.getJSON(url, function (data) {
        records = data.sort(sortArr);
    });
    return records;
}


/**
 *  弹出框显示维修历史明细信息
 */
function showDetail(orderLineNo) {
    if (orderLineNo) {
        var url = "/equipment/loadFixHistory/" + orderLineNo;
        $("#fix-history").load(url, function (data) {
            $("#show_history_modal").modal("show");
        });
    }
}

/**
 *
 * @param eqCode 设备编号
 * @returns {boolean} 检查设备编号是否唯一
 */
function checkEqCode(eqCode) {
    var exists = false;
    var url = "/equipment/checkEqCodeExists/" + eqCode;
    $.getJSON(url, function (data) {
        exists = data
    });
    return exists;
}

/**
 *  上一条
 */
function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");

    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var e = getEquipmentById(selectedIds[pointer]);
        vdm.$set("equipments", e);
        hm.$set("e", e);
        hm.$set("histories", loadFixHistoryByEid(selectedIds[pointer]));
    }

}
/**
 *  下一条
 */
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        var e = getEquipmentById(selectedIds[pointer]);
        vdm.$set("equipments", e);
        hm.$set("e", e);
        hm.$set("histories", loadFixHistoryByEid(selectedIds[pointer]));
    }
}


/**
 * 保存设备信息
 */
function save() {
    $("#saveBtn").trigger("click");
}

function del() {
    if (activeTab != "list") {
        showMessageBox("info", "请到列表中选中再进行删除操作!");
        return;
    }
    //判断选中的tab
    var eid = selectedIds[0];
    if (!eid) {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
    var url = "/equipment/delete/" + eid;
    if (eid) {
        var confirm = window.confirm("确定要删除该记录么？");
        if (confirm) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (msg) {
                    if (msg.result) {
                        showMessageBox("info", "设备信息删除成功 ");
                        //删除完之后将该行数据从table中移除
                        $("tr[data-row-id='" + msg.resultDesc + "']").remove();
                    }
                },
                error: function (msg) {
                    showMessageBox("danger", "设备信息有关联数据，无法删除，请联系管理员");
                }
            });
        }
    }
}


/**
 *
 * @param formId 设置form为只读
 */
function setFormReadStatus(formId, formLocked) {
    if (formLocked) {
        $(formId + " input").attr("readonly", "readonly");
        $(formId + " select").attr("disabled", "disabled");
    } else {
        $(formId + " input").attr("readonly", "readonly").removeAttr("readonly");
        $(formId + " select").attr("disabled", "disabled").removeAttr("disabled");
    }
}


/**
 *  设备报废
 */
function abandonEq() {
    var status = vdm.equipments.status;
    if (status === '2') {
        showMessageBoxCenter("danger", "center", "当前设备状态已经是报废!");
        return;
    }
    var eid = selectedIds[0];
    var url = "/equipment/abandon/" + eid;
    $.getJSON(url, function (data) {
        if (data) {
            $("#status").removeAttr("disabled");
            $("#status").val(data);
            $("#status").attr("disabled");
            showMessageBoxCenter("info", "center", "设备状态已更新为报废!");
        }
    });

}


/**
 *
 * @param url 重新载入数据
 * @returns {Array}
 */
function reload(url) {
    var dataList = [];
    $.getJSON(url, function (data) {
        dataList = data;
    });
    return dataList;
}


function refresh(data) {
    var index = $(dataTableName).bootgrid("getTotalRowCount"); //获取所有行数
    var nextIndex = parseInt(index + 1);
    if (data) {
        var obj = {
            index: nextIndex,
            id: data.id,
            eqCode: data.eqCode,
            description: data.description,
            equipClass: data.equipmentsClassification.description,
            location: data.locations.description,
            status: '投用',
            running: '运行',
            report: '<a class="btn btn-default btn-xs"  onclick="report(' + data.id + ')" title="报修"><i class="glyphicon glyphicon-wrench"></i></a>',
            track: '<a class="btn btn-default btn-xs"  onclick="track(' + data.id + ')" title="追踪" disabled="setTrackStatus(' + data.id + ')"><i class="glyphicon glyphicon-map-marker"></i></a>'
        };
        $(dataTableName).bootgrid("append", [obj]);
    }
}


/**
 *
 * @param data 动态更新列表数据
 */
function changeValue(data) {
    var trId = data.id;
    $("tr[data-row-id='" + trId + "'] td:eq(2)").html(data.eqCode);
    $("tr[data-row-id='" + trId + "'] td:eq(3)").html(data.description);
    $("tr[data-row-id='" + trId + "'] td:eq(4)").html(data.equipmentsClassification.description);
    $("tr[data-row-id='" + trId + "'] td:eq(5)").html(data.vlocations.locName);
    $("tr[data-row-id='" + trId + "'] td:eq(6)").html(eqStatuses[data.status]["value"]);
    $("tr[data-row-id='" + trId + "'] td:eq(7)").html(runStatus[data.running]["value"]);

}


/**
 * 新建之前清空表单
 */
/*
 function clearForm() {

 $("#detailForm select").find("")
 }*/


function setTrackStatus(id) {
    $.ajaxSettings.async = false;
    var disabled = "";
    var url = "/equipment/findById/" + id;
    $.getJSON(url, function (data) {
        if (data.status === '2') {
            disabled = "disabled";
        }
    });
    return disabled;
}


/**
 * 编辑设备信息
 */
function edit() {
    setFormReadStatus("#detailForm", false);
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        save();
    });
    var eid = selectedIds[0];
    var pm = getEntityById(eid);
    if (eid) {
        vdm.$set("pm", pm);
        formTab.tab('show');

    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");

    }
}
