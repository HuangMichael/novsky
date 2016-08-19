/**
 * Created by Administrator on 2016/8/15.
 */

var dataTableName = '#roleListTable';
var roles = [];
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
$.ajaxSettings.async = false;
var validateOptions = {
    message: '该值无效 ',
    fields: {
        userName: {
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
    $.getJSON(url, function (data) {
        roles = data;
        allSize = data.length; //计算所有记录的个数
        if (dataTableName) {
            vm = new Vue({
                el: elementName,
                data: {
                    roles: roles
                }
            });
            //ajax载入设备信息  并且监听选择事件
            $(dataTableName).bootgrid({
                    selection: true,
                    multiSelect: true,
                    rowSelect: false,
                    keepSelection: true
                }
            ).on("selected.rs.jquery.bootgrid", function (e, rows) {
                //如果默认全部选中
                if (selectedIds.length === roles.length) {
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
 * @param roles 设备信息集合
 * @param eid 设备ID
 */
function getRoleByIdInRoles(eid) {
    var user = null;
    var url = "/user/findById/" + eid;
    $.getJSON(url, function (data) {
        user = data;
    });
    return user;
}

/**
 *
 * @param roles 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(roles) {
    var selecteds = [];
    for (var x in roles) {
        if (!isNaN(roles[x]["id"])) {
            selecteds.push(roles[x]["id"]);
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
        var role = getRoleByIdInRoles(selectedIds[--pointer]);
        vdm.role = role;

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
        var role = getRoleByIdInRoles(selectedIds[++pointer]);
        vdm.role = role;
    }
}


/**
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[pointer];
    var role = getRoleByIdInRoles(uid);
    if (uid) {
        vdm.$set("role", role);
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
    initLoadData("/role/findActiveRole", dataTableName);
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();

    });

    vdm = new Vue({
        el: "#detailForm",
        data: {
            role: null
        }
    });

    //点击显示角色详细信息
    formTab.on('click', function () {
        activeTab = "detail";
        setFormReadStatus("#detailForm", formLocked);
        //首先判断是否有选中的
        var role = null;
        if (selectedIds.length > 0) {
            role = getRoleByIdInRoles(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            role = getRoleByIdInRoles(roles[0]["id"]);
            selectedIds = setAllInSelectedList(roles);
        }
        console.log(JSON.stringify(role));
        vdm.role = role;
    });

    listTab.on('click', function () {
        activeTab = "list";
    });
    $('select').select2({theme: "bootstrap"});
});
function save() {
    var roleId = $("#roleId").val();
    var roleName = $("#roleName").val();
    var roleDesc = $("#roleDesc").val();
    var status = "1";

    if (roleId) {
        var url = "/role/update";
    } else {
        var url = "/role/save";
    }
    var role = {roleId: roleId, roleName: roleName, roleDesc: roleDesc, status: status};
    $.post(url, role, function (data) {
        if (data) {
            showMessageBox("info", "角色信息保存成功！");
        } else {
            showMessageBox("danger", "角色信息保存失败！");
        }

    });
}

function showRole(roleId) {
    var url = "/role/detail/" + roleId;
    $("#contentDiv").load(url, function () {

    });
}


/**
 *  ajax加载新增页面
 */
function loadNew() {
    $("#tab_1_1").load("/role/create");
    var newVue = new Vue({
        el: "#createForm",
        data: {
            role: null
        }
    });
    formTab.tab('show');
}


/**
 * 根据ID获取设备信息
 * @param roles 设备信息集合
 * @param eid 设备ID
 */
function getRoleByIdInRoles(eid) {
    var role = null;
    var url = "/role/findById/" + eid;
    $.getJSON(url, function (data) {
        role = data;
    });
    return role;
}


function getAllRoles() {
    var roles = null;
    var url = "/role/findActiveRole";
    $.getJSON(url, function (data) {
        roles = data;
    });
    return roles;
}