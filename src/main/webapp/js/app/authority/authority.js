var zTree;
var demoIframe;
var setting = {
        check: {
            enable: true,
            chkboxType: { "Y" : "p", "N" : "s" }
        },
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            onCheck: onCheck
        }
    }
var zNodes = [];
$(document).ready(function () {
    var url = "/resource/findApps";
    var pid = 0;
    var obj = null;
    $.getJSON(url, function (data) {
        for (var x = 0; x < data.length; x++) {
            if (data) {
                obj = data[x];
                pid = (!obj["parent"]) ? 0 : obj["parent"].id;
                zNodes[x] = {id: obj.id, pId: pid, name: obj.description, open: 1, isParent: 1};
            } else {
                alert("信息加载出错");
            }
        }
        var t = $("#tree");
        t = $.fn.zTree.init(t, setting, zNodes);
        demoIframe = $("#testIframe");
        demoIframe.bind("load", loadReady);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.selectNode(zTree.getNodeByParam("id", 1));

    });
    function loadReady() {
        var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
            htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
            maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
            h = demoIframe.height() >= maxH ? minH : maxH;
        if (h < 530) h = 530;
        demoIframe.height(h);
    }


    $("#authListTable").bootgrid();
    $("select").select2({theme: "bootstrap"});
});


/**
 *  授权
 */
function grant() {

    var roleId = $("#role_id").val();
    var resourceIds = checkedNodeIds;

    var url = "authority/grant";

    if (!roleId) {
        showMessageBox("danger", "请选择授权角色!");
        return;
    }

    if (!resourceIds) {
        showMessageBox("danger", "请选择授权的资源信息!");
        return;
    }
    var data = {
        roleId: roleId,
        resourceIds: resourceIds
    }


    $.post(url, data, function (value) {
        if (value.result) {
            loadAuthView();
            showMessageBox("info", value.resultDesc);

        } else {
            showMessageBox("danger", value.resultDesc);
        }
    });


    //先根据roleID获取role

    //保存set
}


function loadAuthView() {
    var roleId = $("#role_id").val();
    var url = "/authority/loadAuthView/" + roleId;
    $("#authViewDiv").load(url, function () {
        $("#authListTable").bootgrid();
       /* //设置已授权的选项选中
        var zTree = $.fn.zTree.getZTreeObj("tree");
        var authorities = checkedNodeIds.split(",");
        for (var id in authorities) {
            console.log("id----------------" + id);
            if (!isNaN(id)) {
                var node = zTree.getNodeByParam("id", id);
                zTree.selectNode(node);
            }
        }*/
    });


}

var checkedNodeIds = null;
function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].id + ",";
    }
    checkedNodeIds = v;
}