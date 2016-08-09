/**
 * Created by Administrator on 2016/6/29.
 */
$(document).ready(function () {
    $("#datatable1").bootgrid({
        formatters: {
            "edit": function (column, row) {
                var conId = row.id;
                return '<a class="btn btn-default btn-xs" onclick ="edit(' + conId + ')" title="编辑"><i class="glyphicon glyphicon-edit"></i></i>'
            }
        }
    });
});

function save() {
    var url = "/user/save";
    var str = getFormJsonData("userForm");
    var obj = JSON.parse(str);
    $.post(url, {obj: obj}, function (data) {
        if (data.result) {
            $("#createUser_modal").modal("hide");
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    });
}


function edit(id) {
    $("#user_modal").modal("show");
}