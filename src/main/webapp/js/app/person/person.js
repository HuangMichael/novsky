/**
 * Created by Administrator on 2016/7/11.
 */
var dataTableName = "#personListTable";
var selectedIds = [];
var personList = [];
var pointer = 0;
var listModel = null;
var personDetail = null;


//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');


//新建数据模型
var createModel = null;


$(function () {
    //ajax 请求personList集合
    var url = "/person/findAll";
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        personList = data;
    });

    //新建一个listModel
    listModel = new Vue({
        el: dataTableName,
        data: {
            personList: personList
        }
    });
    //新建一个listModel
    personDetail = new Vue({
        el: "#detailForm",
        data: {
            person: personList[0]
        }
    });

    $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        rowSelect: true,
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


});


function backwards() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
        return;
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var person = getPersonById(selectedIds[pointer]);
        personDetail.$set("person", person);
    }

}
function forwards() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");
        return;
    } else {
        pointer = pointer + 1;
        var person = getPersonById(selectedIds[pointer]);
        personDetail.$set("person", person);

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
function addNew() {
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
        .bootstrapValidator({
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
        }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        // Get the form instance
        createPerson();
    });
    formTab.tab('show');
}


/**
 *  造人方法
 */
function createPerson() {
    var person = createModel.$data.person;
    var url = "/person/save";
    $.post(url, {
        personNo: person.personNo,
        personName: person.personName,
        telephone: person.telephone,
        email: person.email,
        birthDate: person.birthDate,
        status: person.status
    }, function () {

    }).success(function (data) {
        showMessageBoxCenter("info", "center", "人员信息添加成功!");
    }).error(function (data) {
        showMessageBoxCenter("danger", "center", "人员信息添加失败!");
    });


}