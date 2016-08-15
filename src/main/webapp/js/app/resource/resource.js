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