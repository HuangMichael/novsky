/**
 * Created by Administrator on 2016/8/15.
 */
var dataTableName = '#lineListTable';
var lines = [];
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

var type = [];

var selectedUsersId = [];
$.ajaxSettings.async = false;
var validateOptions = {
    message: '该值无效 ',
    fields: {
        lineName: {
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
            lines = data;
            allSize = data.length; //计算所有记录的个数
            if (dataTableName) {
                vm = new Vue({
                    el: elementName,
                    data: {
                        lines: lines
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
                        if (selectedIds.length === lines.length) {
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
 * @param lines 设备信息集合
 * @param eid 设备ID
 */
function getLineByIdInLines(eid) {
    var line = null;
    var url = "/line/findById/" + eid;
    $.getJSON(url,
        function (data) {
            line = data;
        });
    return line;
}

/**
 *
 * @param lines 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(lines) {
    var selecteds = [];
    for (var x in lines) {
        if (!isNaN(lines[x]["id"])) {
            selecteds.push(lines[x]["id"]);
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
        var line = getLineByIdInLines(selectedIds[--pointer]);
        vdm.line = line;

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
        var line = getLineByIdInLines(selectedIds[++pointer]);
        vdm.line = line;
    }
}

/**
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[pointer];
    var line = getLineByIdInLines(uid);
    if (uid) {
        vdm.$set("line", line);
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
    initLoadData("/line/findAllLines", dataTableName);
    $('#detailForm').bootstrapValidator(validateOptions).on('success.form.bv',
        function (e) {
            e.preventDefault();

        });

    vdm = new Vue({
        el: "#detailForm",
        data: {
            line: null,
            type:[{
                key:'1',value:'站区'

            },{
                key:'2',value:'段区'

            }]
        }
    });

    //点击显示角色详细信息
    formTab.on('click',
        function () {
            activeTab = "detail";
            setFormReadStatus("#detailForm", formLocked);
            //首先判断是否有选中的
            var line = null;
            if (selectedIds.length > 0) {
                line = getLineByIdInLines(selectedIds[0]);
            } else {
                //没有选中的 默认显示整个列表的第一条
                line = getLineByIdInLines(lines[0]["id"]);
                selectedIds = setAllInSelectedList(lines);
            }
            vdm.line = line;
        });

    listTab.on('click',
        function () {
            activeTab = "list";
        });
    $('select').select2({
        theme: "bootstrap"
    });
});

/**
 * 保存信息
 */
function save() {
    var lineId = $("#lineId").val();
    var lineNo = $("#lineNo").val();
    var description = $("#description").val();
    var type = $("#type").val();
    if (lineId) {
        var url = "/line/update";
    } else {
        var url = "/line/save";
    }
    var line = {
        id: lineId,
        lineNo: lineNo,
        type: type,
        sortNo: 1,
        description: description
    };

    $.post(url, line, function (data) {
        if (data) {
            showMessageBox("info", "线路信息保存成功！");
        } else {
            showMessageBox("danger", "线路信息保存失败！");
        }

    });
}

function showLine(lineId) {
    var url = "/line/detail/" + lineId;
    $("#contentDiv").load(url,
        function () {

        });
}

/**
 *  ajax加载新增页面
 */
function add() {


    $("#tab_1_1").load("/line/create");
    var newVue = new Vue({
        el: "#createForm",
        data: {
            line: null,
            type: [
                {value: 1, text: "站区", selected: "selected"},
                {value: 2, text: "段区"}]
        }
    });
    formTab.tab('show');
}

/**
 * 根据ID获取设备信息
 * @param lines 设备信息集合
 * @param eid 设备ID
 */
function getLineByIdInLines(eid) {
    var line = null;
    var url = "/line/findById/" + eid;
    $.getJSON(url,
        function (data) {
            line = data;
        });
    return line;
}

function getAllLines() {
    var lines = null;
    var url = "/line/findAllLines";
    $.getJSON(url,
        function (data) {
            lines = data;
        });
    return lines;
}