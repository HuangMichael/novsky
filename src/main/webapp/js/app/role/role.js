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

var selectedUsersId = [];
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
    $.getJSON(url,
        function (data) {
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
                    rowSelect: true,
                    keepSelection: true
                }).on("selected.rs.jquery.bootgrid",
                    function (e, rows) {
                        //如果默认全部选中
                        if (selectedIds.length === roles.length) {
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
 * @param roles 设备信息集合
 * @param eid 设备ID
 */
function getRoleByIdInRoles(eid) {
    var user = null;
    var url = "/user/findById/" + eid;
    $.getJSON(url,
        function (data) {
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

    } else {
        //  pointer = pointer - 1;
        //判断当前指针位置
        var role = getRoleByIdInRoles(selectedIds[--pointer]);
        vdm.role = role;
        loadUsers(role.id);

    }

}
/**
 *  下一条
 */
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        var role = getRoleByIdInRoles(selectedIds[++pointer]);
        vdm.role = role;
        loadUsers(role.id);
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


    docName = "角色信息";
    mainObject = "role";
    formName = "#detailForm";
    initBootGrid(dataTableName);
    searchModel = [{"param": "roleName", "paramDesc": "角色名称"}, {"param": "roleDesc", "paramDesc": "角色描述"}];
    initSelect();
    $(formName).bootstrapValidator(validateOptions).on('success.form.bv',
        function (e) {
            e.preventDefault();
            saveMainObject(formName);

        });


    var ids = findAllRecordId();
    vdm = new Vue({
        el: formName,
        data: {
            role: findById(ids[0])
        }
    });
    showUsers();
});


function showUsers() {

    showDetail();
    loadUsers(vdm.role.id)
}

function showRole(roleId) {
    var url = "/role/detail/" + roleId;
    $("#contentDiv").load(url,
        function () {

        });
}

/**
 *  ajax加载新增页面
 */
function add() {
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
    $.getJSON(url,
        function (data) {
            role = data;
        });
    return role;
}

function getAllRoles() {
    var roles = null;
    var url = "/role/findActiveRole";
    $.getJSON(url,
        function (data) {
            roles = data;
        });
    return roles;
}

function addUsers() {
    var roleId = roles[pointer].id;
    var url = "role/popUsers/" + roleId;
    $("#mBody").load(url,
        function (data) {
            $("#userListModal").modal("show");
        });

    //ajax请求数据和页面 弹出
}

function confirmAddUsers() {
    var roleId = roles[pointer].id;
    $("#userListModal").modal("hide");
    var usersIdStr = selectedUsersId.join(",");
    // ajax将选中的用户进行与角色关联
    var url = "role/addUser";
    var data = {
        roleId: roleId,
        usersIdStr: usersIdStr
    };
    $.post(url, data, function (data) {
        if (data.result) {
            loadUsers();
            showMessageBox("info", data.resultDesc);
        }
    });
}

/**
 *
 * @param roleId
 * 根据角色载入包含用户
 */
function loadUsers(roleId) {
    if (!roleId) {
        showMessageBox("danger", "角色信息获取失败，请重新尝试!");
        return;
    }
    var url = "role/findUsersOfRole/" + roleId;
    var html = "";
    $.getJSON(url, function (data) {
        for (var x in data) {
            var id = data[x]['id'];
            if (id) {
                html += '<tr class="gradeX" id="tr' + id + '">';
                html += '<td>' + Number(Number(x) + Number(1)) + '</td>';
                html += '<td>' + data[x]['userName'] + '</td>';
                html += '<td><button class="btn btn-sm" onclick="removeUser(' + roleId + "," + id + ')"><i class="glyphicon glyphicon-remove"></i></button></td>';
                html += '</tr>';
            }
        }
        $("#usersDiv").html(html);
    });

}

/**
 *移除用户
 */
function removeUser(roleId, userId) {

    var url = "role/removeUser/";
    var data = {
        roleId: roleId,
        userId: userId
    };
    if (userId) {
        bootbox.confirm({
            message: "确定要从当前角色中删除该用户么？?",
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
                    $.post(url, data, function (msg) {
                        if (msg.result) {
                            showMessageBox("info", "用户移除成功!");
                            $("#tr" + userId).remove();
                        } else {
                            showMessageBox("danger", "用户移除失败!");
                        }
                    });
                }
            }
        });

    }


}