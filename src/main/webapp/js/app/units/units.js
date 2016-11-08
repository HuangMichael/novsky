var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');


var validateOptions = {
    message: '该值无效 ',
    fields: {
        unitNo: {
            message: '单位编号无效',
            validators: {
                notEmpty: {
                    message: '单位编号不能为空!'
                },
                stringLength: {
                    min: 3,
                    max: 20,
                    message: '单位编号长度为3到20个字符'
                }
            }
        },
        description: {
            message: '单位名称无效',
            validators: {
                notEmpty: {
                    message: '单位名称不能为空!'
                },
                stringLength: {
                    min: 2,
                    max: 20,
                    message: '单位名称长度为2到20个字符'
                }
            }
        },
        "status": {
            message: '单位状态无效',
            validators: {
                notEmpty: {
                    message: '单位状态不能为空!'
                }
            }
        }
    }
}


$(function () {

    dataTableName = "#unitsDataTable";
    mainObject = "units";
    formName = "#unitDetailForm";
    $(formName)
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        saveMainObject();
    });


    initBootGrid(dataTableName);


    ids = findAllRecordId();

    vdm = new Vue({

        el: formName,
        data: null


    });



    console.log(ids);

    formTab.on('click', function () {
        //首先判断是否有选中的
        var object = null;
        if (selectedIds.length > 0) {
            //切换tab时默认给detail中第一个数据
            object = findById(selectedIds[0]?selectedIds[0]:ids[0]);
        } else {
            object = findById(ids[0]);
            selectedIds = (ids);
        }
        vdm.$set(mainObject, object);
        setFormReadStatus(formName, true);
    });
})


function add() {
    formTab.tab('show');
    formTab.data("status", "add");
}