var zTree;
var demoIframe;
var setting = {
    check: {
        enable: false
    },
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
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
        onClick: function (event, treeId, treeNode, clickFlag) {
            var url = "/resource/detail/" + treeNode.id;
            $("#contentDiv").load(url);
            return true;
        }
    }
};
var zNodes = [];
$(document).ready(function () {
    $.ajaxSettings.async = false;
    var url = "/resource/findAll";
    var pid = 0;
    var obj = null;
    $.getJSON(url, function (data) {
        for (var x = 0; x < data.length; x++) {
            if (data) {
                obj = data[x];
                pid = (!obj["parent"]) ? 0 : obj["parent"].id;
                zNodes[x] = {id: obj.id, pId: pid, name: obj.description, open: !pid, isParent: obj["hasChild"]};
            } else {
                alert("信息加载出错");
            }
        }
    });
    var t = $("#tree");
    t = $.fn.zTree.init(t, setting, zNodes);
    demoIframe = $("#testIframe");
    demoIframe.bind("load", loadReady);
    var zTree = $.fn.zTree.getZTreeObj("tree");
    zTree.selectNode(zTree.getNodeByParam("id", zNodes[0].id));

    function loadReady() {
        var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
            htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
            maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
            h = demoIframe.height() >= maxH ? minH : maxH;
        if (h < 530) h = 530;
        demoIframe.height(h);
    }

    $('.modal').on('hide.bs.modal', function () {
        reload();
    })
})
;


function reload() {
    var selectedId = getSelectedNodeId();
    $("#contentDiv").load("/resource/detail/" + selectedId);

}


var flag = false;
/**
 *加载创建form
 *
 * */
function loadCreateForm() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    if (!id) {
        id = 0;
    }
    var url = "/resource/create/" + id;
    $("#contentDiv").load(url, function () {
        flag = true;
    });
}
/**
 *保存信息
 * */
function save() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var pid = selectedNode.id;
    var resourceName = $("#resourceName").val();
    var description = $("#description").val();
    var resourceUrl = $("#resourceUrl").val();
    var lid = $("#lid").val();
    pid = $("#pid").val();
    var status = $("#status").find("option:selected").val();
    var resourceLevel = $("#resourceLevel").find("option:selected").val();
    var sortNo = $("#sortNo").val();
    var url = "/resource/save";
    var operation = "添加";

    var resource = {
        resourceName: resourceName,
        description: description,
        resourceLevel: resourceLevel,
        resourceUrl: resourceUrl,
        lid: lid,
        pid: pid,
        status: status,
        sortNo: sortNo
    };
    $.ajax({
        type: "POST",
        url: url,
        data: resource,
        dataType: "json",
        success: function (msg) {
            if (lid) {
                operation = "更新";
            } else {
                var childZNode = {id: msg.id, pId: pid, name: description, open: true};//构造子节点
                var ztree = getTreeRoot();
                var parentZNode = ztree.getNodeByParam("id", getSelectedNodeId(), null); //获取父节点
                ztree.addNodes(parentZNode, childZNode, true);
                zTree.selectNode(zTree.getNodeByParam("id", msg.id));
            }
            $.bootstrapGrowl("资源信息" + operation + "成功！", {
                type: 'info',
                align: 'right',
                stackup_spacing: 30
            });
        }

        ,
        error: function () {
            $.bootstrapGrowl("资源信息" + operation + "失败！", {
                type: 'danger',
                align: 'right',
                stackup_spacing: 30
            });
        }
    })
    ;
}


/**
 *删除信息
 * */


function deleteObject() {
    if (!confirm("确定要删除该信息么？")) {
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    var url = "/resource/delete/" + id;
    $.getJSON(url, function (data) {

        if (data) {
            zTree.removeNode(selectedNode);
            $.bootstrapGrowl("资源信息删除成功！", {
                type: 'info',
                align: 'right',
                stackup_spacing: 30
            });
        } else {
            $.bootstrapGrowl("资源信息删除失败！", {
                type: 'danger',
                align: 'right',
                stackup_spacing: 30
            });
        }
    })
}
/**
 *当前选中树节点id
 * */
function getSelectedNodeId() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    return id;
}
/**
 * 返回当前选中节点
 * */
function getSelectedNode() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    return selectedNode;
}


/**
 * 返回当前树
 * */
function getTreeRoot() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    return zTree;
}