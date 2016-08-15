/**
 * Created by Administrator on 2016/8/15.
 */

$(function () {
    $("#datatable1").bootgrid();
    //点击显示角色详细信息
});
function save() {
    var roleName = $("#roleName").val();
    var roleDesc = $("#roleDesc").val();
    var sortNo = $("#sortNo").val();
    var status = $("#status  option:selected").val();
    var url = "/role/save";
    var role = {roleName: roleName, roleDesc: roleDesc, sortNo: sortNo, status: status};
    $.ajax({
        type: "post",
        url: url,
        data: role,
        dataType: "json",
        success: function (data) {
            $.bootstrapGrowl("角色信息添加成功！", {
                type: 'info',
                align: 'right',
                stackup_spacing: 30
            });

        }, error: function (data) {
            $.bootstrapGrowl("角色信息添加失败！", {
                type: 'danger',
                align: 'right',
                stackup_spacing: 30
            });
        }
    });
}

function showRole(roleId) {
    var url = "/role/detail/" + roleId;
    $("#contentDiv").load(url, function () {

    });
}
