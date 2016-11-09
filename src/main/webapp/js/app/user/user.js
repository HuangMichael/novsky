var selectedIds = []; //获取被选择记录集合
var locs = [];
var pointer = 0;
var listTab = $('#myTab li:eq(0) a');
var formTab = $('#myTab li:eq(1) a');
var object = null;
formName = "#detailForm";
$.ajaxSettings.async = false;
var validationConfig = {
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
    dataTableName = '#userDataTable';
    docName = "用户信息";
    mainObject = "user";
    //初始化从数据库获取列表数据

    var url_location = "/commonData/findMyLoc";
    $.getJSON(url_location, function (data) {
        locs = data;
    });


    initBootGrid(dataTableName);
    initSelect.call();
    selectedIds = findAllRecordId();
    validateForm.call(validationConfig);

    vdm = new Vue({
        el: formName,
        data: {
            user: selectedIds[0],
            locs: locs
        }
    });
    showDetail();
});









