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
		onClick: function(event, treeId, treeNode, clickFlag) {
			var url = "/resource/detail/" + treeNode.id;
			$("#contentDiv").load(url);
			return true;
		}
	}
};
var zNodes = [];
$(document).ready(function() {
	$.ajaxSettings.async = false;
	var url = "/resource/findAll";
	var pid = 0;
	var obj = null;
	$.getJSON(url, function(data) {
		for(var x = 0; x < data.length; x++) {
			if(data) {
				obj = data[x];
				pid = (!obj["parent"]) ? 0 : obj["parent"].id;
				zNodes[x] = {
					id: obj.id,
					pId: pid,
					name: obj.description,
					open: !pid,
					isParent: 0
				};
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
			maxH = Math.max(bodyH, htmlH),
			minH = Math.min(bodyH, htmlH),
			h = demoIframe.height() >= maxH ? minH : maxH;
		if(h < 530) h = 530;
		demoIframe.height(h);
	}

});

var flag = false;
/**
 * 新建记录
 */
function add() {
	var tree = getTreeRoot();
	var selectedNode = tree.getSelectedNodes()[0];
	var id = selectedNode.id;
	if(!id) {
		id = 0
	}
	var url = "/resource/create/" + id;
	$("#contentDiv").load(url);
}

/**
 * 新建记录
 */
function save() {

	var resourceId = $("#resourceId").val();
	var resourceName = $("#resourceName").val();
	var description = $("#description").val();
	var resourceUrl = $("#resourceUrl").val();
	var iconClass = $("#iconClass").val();
	var parentId = $("#parentId").val();
	var appName = $("#appName").val();

	var resource = {
		id: resourceId,
		resourceName: resourceName,
		description: description,
		resourceUrl: resourceUrl,
		iconClass: iconClass,
		parentId: parentId,
		appName: appName
	}

	console.log("resource---------" + JSON.stringify(resource));

	var url = "resource/";

	if(!resource.id) {
		url += "save";
	}else{
		url+="update";
	}
	$.post(url,resource,function(data){
		
		if(data.result){
			showMessageBox("info",data.resultDesc);
		}else{
			showMessageBox("danger",data.resultDesc);
		}
		
	})

}