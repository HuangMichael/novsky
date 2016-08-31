var dataTableName = '#userDataTable';
var users = [];
var selectedIds = []; //获取被选择记录集合
var activePerson = []; //激活状态人员
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
$(function () {



    //初始化从数据库获取列表数据
    initLoadData("/user/findAll", dataTableName);
    $('#detailForm')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();

    });

    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });

    var url = "/commonData/findActivePerson";
    $.getJSON(url, function (data) {
        activePerson = data;
    });


    users = getAllUsers();

    vdm = new Vue({
        el: "#detailForm",
        data: {
            formLocked: formLocked,
            user: users[0],
            activePerson: activePerson,
            locs: locs
        }
    });


    //初始化人员信息


    formTab.on('click', function () {
        activeTab = "detail";
        setFormReadStatus("#detailForm", formLocked);
        //首先判断是否有选中的
        var user = null;
        if (selectedIds.length > 0) {
            user = getUserByIdInUsers(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            user = getUserByIdInUsers(users[0]["id"]);
            selectedIds = setAllInSelectedList(users);
        }

        console.log(JSON.stringify(user));
        vdm.user = user;
    });

    listTab.on('click', function () {
        activeTab = "list";
    });
    $('select').select2({theme: "bootstrap"});


    $("#saveBtn").on("click", function (data) {
        var userId = $("#userId").val();
        var userName = $("#userName").val();
        var personId = $("#person_id").val();
        var locationId = $("#locations_id").val();
        var status = $("#status").val();

        console.log("locationId--------"+locationId);

        var user = null;
        var url = "";
        if (userId) {
            user = {
                userId: userId,
                personId: personId,
                locationId: locationId,
                status:status
            }
            url = "user/update";
        } else {
            url = "user/createUser";
            user = {
                userName: userName,
                personId: personId,
                locationId: locationId
            }
        }
        $.post(url, user, function (data) {
            if (data.result) {
                showMessageBox("info", data.resultDesc);
            } else {
                showMessageBox("danger", data.resultDesc);
            }
        });
    })
});


/**
 *  ajax加载新增页面
 */
function loadNew() {
    $("#tab_1_1").load("/user/create");
    var newVue = new Vue({
        el: "#createForm",
        data: {
            user: null,
            locs: locs,
            activePerson: activePerson
        }
    });
    formTab.tab('show');

}


/**
 *
 * @param url 数据接口路径
 * @param elementName 渲染元素名称
 */
function initLoadData(url, elementName) {
    $.getJSON(url, function (data) {
        users = data;
        allSize = data.length; //计算所有记录的个数
        if (dataTableName) {
            vm = new Vue({
                el: elementName,
                data: {
                    users: users
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
                if (selectedIds.length === users.length) {
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
 * @param users 设备信息集合
 * @param eid 设备ID
 */
function getUserByIdInUsers(eid) {
    var user = null;
    var url = "/user/findById/" + eid;
    $.getJSON(url, function (data) {
        user = data;
    });
    return user;
}


function getAllUsers() {
    var users = null;
    var url = "/user/findAll";
    $.getJSON(url, function (data) {
        users = data;
    });
    return users;
}


/**
 *
 * @param users 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(users) {
    var selecteds = [];
    for (var x in users) {
        if (!isNaN(users[x]["id"])) {
            selecteds.push(users[x]["id"]);
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
        var user = getUserByIdInUsers(selectedIds[--pointer]);
        vdm.user = user;

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
        var user = getUserByIdInUsers(selectedIds[++pointer]);
        vdm.user = user;
    }
}


/**
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[pointer];
    var user = getUserByIdInUsers(uid);
    if (uid) {
        vdm.$set("user", user);
        formTab.tab('show');
        setFormReadStatus("#detailForm", false, ["userName"]);
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
/**
 * 查询人员信息列表
 */
function getPersonList() {
    var url = "/person/findActivePerson";
    $.getJSON(url, function (data) {
        activePerson = data;
        console.log("data----" + JSON.stringify(data));
    });


}
