var zTree;
var demoIframe;
var setting = {
    check: {
        enable: true
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
            $.getJSON(url, function (data) {
                fillForm(data, "#form");
                loadTable();
            });


            return true;
        }
    }
};
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
                zNodes[x] = {id: obj.id, pId: pid, name: obj.description, open: !pid, isParent: 1};
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


    $("select").select2({theme: "bootstrap"});
});