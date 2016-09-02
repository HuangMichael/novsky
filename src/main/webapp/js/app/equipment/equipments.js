var dataTableName = '#equipmentsDataTable';
var eqs = [];
var locs = [];
var eqClasses = [];
var eqStatuses = [];
var runStatus = []
var selectedIds = []; //获取被选择记录集合
var allSize = 0;
var vdm = null; //明细页面的模型
var vm = null; //明细页面的模型
var hm = null;
var formLocked = true;

var formStatusArray = ["READ", "CREATE", "EDIT", "SAVED", "DELETED"];

var formstatus = formStatusArray["READ"];

var activeTab = "list";

//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
//维修历史列表
var historyTab = $('#myTab li:eq(2) a');
var pointer = 0;
$.ajaxSettings.async = false;


var validateOptions = {
    message: '该值无效 ',
    fields: {
        "eqCode": {
            message: '设备编号无效',
            validators: {
                notEmpty: {
                    message: '设备编号不能为空!'
                },
                stringLength: {
                    min: 6,
                    max: 20,
                    message: '设备编号长度为6到20个字符'
                }
            }
        },
        "description": {
            message: '设备描述无效',
            validators: {
                notEmpty: {
                    message: '设备描述不能为空!'
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
        },
        "equipmentsClassification.id": {
            message: '设备分类无效',
            validators: {
                notEmpty: {
                    message: '设备分类不能为空!'
                }
            }
        },
        "status": {
            message: '设备状态无效',
            validators: {
                notEmpty: {
                    message: '设备状态不能为空!'
                }
            }
        }
        ,
        "running": {
            message: '运行状态无效',
            validators: {
                notEmpty: {
                    message: '运行状态不能为空!'
                }
            }
        }
    }
};
$(function () {
    //初始化从数据库获取列表数据
    initLoadData("/equipment/findMyEqs", dataTableName);
    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });
    var url = "/commonData/findVEqClass";
    $.getJSON(url, function (data) {
        eqClasses = data;
    });

    var url = "/commonData/getEqStatus";
    $.getJSON(url, function (data) {
        eqStatuses = data;
    });

    var url = "/commonData/getEqRunStatus";
    $.getJSON(url, function (data) {
        runStatus = data;
    });


    var eq = getEquipmentByIdInEqs(eqs[0]["id"]);
    vdm = new Vue({
        el: "#detailForm",
        data: {
            formLocked: formLocked,
            equipments: eq,
            locs: locs,
            eqClasses: eqClasses,
            eqStatuses: eqStatuses,
            runStatus: runStatus
        },
        methods: {
            previous: function (event) {
                if (pointer <= 0) {
                    showMessageBoxCenter("danger", "center", "当前记录是第一条");
                    return;
                } else {
                    pointer = pointer - 1;
                    //判断当前指针位置

                    var e = getEquipmentByIdInEqs(selectedIds[pointer])
                    vdm.$set("equipments", e);
                    hm.$set("e", e);
                    //loadFixHistoryByEid(selectedIds[pointer]);
                    hm.$set("histories", loadFixHistoryByEid(selectedIds[pointer]));
                }
            },
            next: function (event) {
                if (pointer >= selectedIds.length - 1) {
                    showMessageBoxCenter("danger", "center", "当前记录是最后一条");
                    return;
                } else {
                    pointer = pointer + 1;
                    var e = getEquipmentByIdInEqs(selectedIds[pointer])
                    vdm.$set("equipments", e);
                    hm.$set("e", e);
                    hm.$set("histories", loadFixHistoryByEid(selectedIds[pointer]));
                }
            },
            checkEqCode: function () {
                var eqCode = vdm.$get("equipments.eqCode");
                if (checkEqCode(eqCode)) {
                    showMessageBoxCenter("danger", "center", "设备编号不能重复");
                    return;
                }
            }
        }
    });

    hm = new Vue({
        el: "#historyInfo",
        data: {
            e: eqs[0],
            histories: loadFixHistoryByEid(eqs[0] ? eqs[0]["id"] : null)
        },
        methods: {
            showDetailHistory: function (data) {
                showDetail(data);
            }
        }
    });
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveEquipment();
    });
    listTab.on('click', function () {
        refresh();

    });


    formTab.on('click', function () {
        activeTab = "detail";
        setFormReadStatus("#detailForm", formLocked);
        //首先判断是否有选中的
        var eq = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            eq = getEquipmentByIdInEqs(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            eq = getEquipmentByIdInEqs(eqs[0]["id"])
            //所有的都在选中列表中
            selectedIds = setAllInSelectedList(eqs);
        }
        vdm.$set("equipments", eq);

    });


    listTab.on('click', function () {
        activeTab = "list";
    });

    historyTab.on('click', function () {
        activeTab = "history";
        //首先判断是否有选中的
        var equipments = vdm.equipments;
        var histories = loadFixHistoryByEid(equipments.id);
        var xx = $("#locations_id").find("option:selected").text().trim();
        hm.$set("e", equipments);
        hm.$set("e.locations.description", xx);

        hm.$set("histories", histories);
    });


    $('select').select2({theme: "bootstrap"});
    // 表单ajax提交


});


function addNew() {
    setFormReadStatus("#detailForm", false);
    formstatus = formStatusArray["CREATE"];
    var status = [
        {value: 1, text: "投用", selected: "selected"},
        {value: 0, text: "维修"},
        {value: 2, text: "报废"}];
    var running = [{value: 1, text: "运行"}, {value: 0, text: "停止"}];
    vdm.$set("equipments", null);
    vdm.$set("locs", locs);
    vdm.$set("eqClasses", eqClasses);
    vdm.$set("status", status);
    vdm.$set("running", running);
    //设置设备状态和运行状态默认值
    vdm.$set("equipments.locations.id", null);
    vdm.$set("equipments.equipmentsClassification.id", null);
    vdm.$set("equipments.status", 1);
    vdm.$set("equipments.running", 1);
    formTab.tab('show');
}


/**
 *  ajax加载新增页面
 */
function loadNew() {
    $("#tab_1_1").load("/equipment/create");
    formTab.tab('show');

}

var reportId;
function report(id) {
    var status = "0";
    var path = "/equipment/findById/" + id;
    $.getJSON(path, function (data) {
        status = data["status"]
    });
    var curl = "/workOrderReportCart/loadReportedEqPage/" + id;
    if (status == "0") {
        $("#eqList").load(curl, function (data) {
            $("#show_eq_modal").modal("show");
            eqId = id;
            reportId = id;
        })
    } else {
        equipReport(id)
    }
}

function equipReport(id) {
    var url = "/workOrderReportCart/add2Cart";
    $.post(url, {equipmentId: id}, function (data) {
        var size = $("#reportOrderSize").html();
        if (!size) {
            size = 0
        }
        $("#reportOrderSize").html(parseInt(size) + 1);
        showMessageBox("info", "已将设备报修加入到维修车!")
    })
}
function continueEqReport() {
    $("#show_eq_modal").modal("hide");
    equipReport(reportId);
}

//跟踪设备维修进度
function track(eid) {
//载入时先清空
    $(".ystep1").empty();
    //获取各个维修节点的状态
    var steps = [];
    var url = "/equipment/getFixSteps/" + eid;
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        if (data) {
            for (var x in data) {
                steps[x] = {
                    title: data[x][1],
                    content: transformDate(data[x][0]) + data[x][1],
                    status: data[x][2]
                }
            }
            $(".ystep1").loadStep({
                size: "large",
                color: "green",
                steps: steps
            });
            if (steps.length > 0) {
                $(".ystep1").setStep(getCurrentStep(steps));
                $("#track_eq_modal").modal("show");
            } else {
                var m = showMessageBoxCenter("danger", "center", "当前设备不在维修流程中");
                if (m) {
                    return;
                }
            }
        }
        else {
            showMessageBox("danger", "当前设备不在报修流程中");
            return;
        }
    });

}


/**
 *
 * @param steps
 * 获取当前status为0的节点
 */
function getCurrentStep(steps) {
    var index = -1;
    for (var x in steps) {
        if (steps[x]["status"] == '0') {
            index = x++;
            return index;
        } else {
            return steps.length
        }
    }
    return index;
}

/**
 * 保存设备信息
 */
var saveIndex = 0;
function saveEquipment() {
    var objStr = getFormJsonData("detailForm");
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
    console.log("初始化载入列表数据---" + url);
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
function getEquipmentByIdInEqs(eid) {
    var equipment = null;
    var url = "/equipment/findById/" + eid;
    $.getJSON(url, function (data) {
        equipment = data;
    });
    return equipment;
}

/**
 * 根据ID获取设备信息
 * @param eqs 设备信息集合
 * @param eid 设备ID
 */
function findEquipmentByIdInEqs(eid) {
    var equipment = null;
    for (var i in eqs) {
        if (eqs[i].id == eid) {
            equipment = eqs[i];
            break;
        }
    }
    return equipment;
}


/**
 *
 * @param eqs 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(eqs) {
    var selecteds = [];
    for (var x in eqs) {
        if (!isNaN(eqs[x]["id"])) {
            selecteds.push(eqs[x]["id"]);
        }
    }
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
        return;
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var e = getEquipmentByIdInEqs(selectedIds[pointer]);
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
        return;
    } else {
        pointer = pointer + 1;
        var e = getEquipmentByIdInEqs(selectedIds[pointer])
        vdm.$set("equipments", e);
        hm.$set("e", e);
        hm.$set("histories", loadFixHistoryByEid(selectedIds[pointer]));
    }
}

/**
 * 编辑设备信息
 */
function editEq() {
    setFormReadStatus("#detailForm", false);
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveEquipment();
    });


    $("#eqCode").attr("readonly", "readonly");
    var eid = selectedIds[0];
    var eq = getEquipmentByIdInEqs(eid);
    if (eid) {
        vdm.$set("equipments", eq);
        formTab.tab('show');

    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
}


/**
 * 保存设备信息
 */
function saveEq() {
    $("#saveBtn").trigger("click");
}

function deleteEq() {
    if (activeTab != "list") {
        showMessageBox("info", "请到列表中选中再进行删除操作!");
        return;
    }
    ;
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
        $(formId + " #status").attr("disabled", "disabled");
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
    })
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
        }
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
    })
    return disabled;
}
