/**
 * Created by Administrator on 2016/7/11.
 */
var dataTableName = "#personListTable";
var selectedIds = [];
var personList = [];
var pointer = 0;
var listModel = null;
var personDetail = null;
var formLocked = true;
var vdm = null;

//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');


//新建数据模型
var createModel = null;

var validationSettings = {
    message: '该值无效 ',
    fields: {
        personNo: {
            message: '人员编号无效',
            validators: {
                notEmpty: {
                    message: '人员编号不能为空!'
                },
                stringLength: {
                    min: 2,
                    max: 10,
                    message: '人员编号长度为2到10个字符'
                }
            }
        },
        personName: {
            message: '人员名称无效',
            validators: {
                notEmpty: {
                    message: '人员名称不能为空!'
                },
                stringLength: {
                    min: 2,
                    max: 20,
                    message: '人员名称长度为2到20个字符'
                }
            }
        },
        telephone: {
            validators: {
                notEmpty: {
                    message: '电话号码不能为空'
                },
                digits: {
                    message: '请输入正确的电话号码'
                },
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '电话号码长度有误'
                }
            }
        },
        email: {
            validators: {
                emailAddress: {
                    message: '请输入正确的邮箱'
                }
            }
        }
    }
};


$(function () {
    //ajax 请求personList集合

    vdm = new Vue({
        el: "#detailForm",
        data: {
            formLocked: formLocked,
            person: getPersonById(personList[0]["id"])
        }
    });

    $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        rowSelect: false,
        keepSelection: true
    }).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        if (selectedIds.length === personList.length) {
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
    $('select').select2({theme: "bootstrap"});


    //初始化人员信息


    formTab.on('click', function () {
        activeTab = "detail";
        setFormReadStatus("#detailForm", true, []);
        //首先判断是否有选中的
        var person = null;
        if (selectedIds.length > 0) {
            person = getPersonByIdInPerson(selectedIds[0]);
        } else {
            //没有选中的 默认显示整个列表的第一条
            person = getPersonByIdInPerson(personList[0]["id"]);
            selectedIds = setAllInSelectedList(personList);
        }
        vdm.person = person;
    });


});


/**
 *
 * @param roles 所有的记录
 * @returns {Array}将所有的放入选中集合
 */
function setAllInSelectedList(personList) {
    var selecteds = [];
    for (var x in personList) {
        if (!isNaN(personList[x]["id"])) {
            selecteds.push(personList[x]["id"]);
        }
    }
    return selecteds;

}


function backwards() {
    console.log("上一条");
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");

    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var person = getPersonById(selectedIds[pointer]);
        vdm.$set("person", person);
    }

}
function forwards() {
    console.log("下一条");
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        var person = getPersonById(selectedIds[pointer]);
        vdm.$set("person", person);

    }
}


function del() {
    //判断选中的tab
    var eid = selectedIds[0];
    if (!eid) {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");
        return;
    }
    var url = "/person/delete/" + eid;
    if (eid) {
        var confirm = window.confirm("确定要删除该记录么？");
        if (confirm) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (msg) {
                    if (msg.result) {
                        showMessageBox("info", msg.resultDesc);
                        $("tr[data-row-id='" + msg.resultDesc + "']").remove();
                    }
                },
                error: function (msg) {
                    showMessageBox("danger", "人员信息有关联数据，无法删除，请联系管理员");
                }
            });
        }
    }
}

/**
 * 根据ID获取设备信息
 * @param eqs 设备信息集合
 * @param eid 设备ID
 */
function getPersonById(pid) {
    var person = null;
    var url = "/person/findById/" + pid;
    $.getJSON(url, function (data) {
        person = data;
    });
    return person;
}


/**
 *  新增人员信息
 */
function add() {
    //载入createFrom
    $("#tab_1_1").load("/person/loadCreateForm");
    //新建一个数据模型
    createModel = new Vue({
        el: "#createForm",
        data: {
            person: null
        }
    });
    //数据验证
    $('#createForm')
        .bootstrapValidator(validationSettings).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        // Get the form instance
        save();
    });
    formTab.tab('show');
}

/**
 * 根据ID获取设备信息
 * @param users 设备信息集合
 * @param eid 设备ID
 */
function getPersonByIdInPerson(eid) {
    var person = null;
    var url = "/person/findById/" + eid;
    $.getJSON(url, function (data) {
        person = data;
    });
    return person;
}


/**
 *
 * @returns {*}
 *
 * 获取所有人信息
 */
function getAllPerson() {
    var person = null;
    var url = "/person/findActivePerson";
    $.getJSON(url, function (data) {
        person = data;
    });
    return person;
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
 * 编辑设备信息
 */
function edit() {
    var uid = selectedIds[pointer];
    var person = getPersonByIdInPerson(uid);
    if (uid) {
        vdm.$set("person", person);
        formTab.tab('show');
        setFormReadStatus("#detailForm", false, ['personNo']);
    } else {
        showMessageBoxCenter("danger", "center", "请选中一条记录再操作");

    }
}


function save() {
    var personId = $("#personId").val();
    var personNo = $("#personNo").val();
    var personName = $("#personName").val();
    var telephone = $("#telephone").val();
    var email = $("#email").val();
    var birthDate = $("#birthDate").val();
    var status = $("#status").val();
    var url = "";
    var person = {
        personId: personId,
        personNo: personNo,
        personName: personName,
        telephone: telephone,
        email: email,
        birthDate: birthDate,
        status: status
    };
    if (personId) {
        url = "person/update";
    } else {
        url = "person/save";
    }
    $.post(url, person, function (data) {
        if (data.result) {
            showMessageBox("info", data.resultDesc);
        } else {
            showMessageBox("danger", data.resultDesc);
        }
    });
}


