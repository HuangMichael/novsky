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




var validateOptions = {
    message: '该值无效 ',
    fields: {
        "resourceCode": {
            message: '资源编号无效',
            validators: {
                notEmpty: {
                    message: '资源编号不能为空!'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '资源编号长度为1到10个字符'
                }
            }
        },

        "resourceName": {
            message: '资源名称无效',
            validators: {
                notEmpty: {
                    message: '资源名称不能为空!'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '资源名称长度为1到10个字符'
                }
            }
        },

        "description": {
            message: '资源描述无效',
            validators: {
                notEmpty: {
                    message: '资源描述不能为空!'
                },
                stringLength: {
                    min: 2,
                    max: 20,
                    message: '资源描述长度为2到20个字符'
                }
            }
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


    $('#form')
        .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
        e.preventDefault();
        save();
    });


    $("#saveBtn").on("click",function(){
        var resourceId = $("#resourceId").val();
        var resourceName = $("#resourceName").val();
        var resourceCode = $("#resourceCode").val();
        var description = $("#description").val();
        var resourceUrl = $("#resourceUrl").val();
        var iconClass = $("#iconClass").val();
        var parentId = getSelectedNodeId();
        var appName = $("#appName").val();

        var resource = {
            id: resourceId,
            resourceName: resourceName,
            description: description,
            resourceCode:resourceCode,
            resourceUrl: resourceUrl,
            iconClass: iconClass,
            parentId: parentId,
            appName: appName
        };
        var url = "resource/";
        if(!resource.id) {
            url += "save";
        }else{
            url+="update";
        }
        $.post(url,resource,function(obj){

            //构造一个子节点
            var childZNode = {
                id: resourceId,
                pId: parentId,
                name: description
            };
            if (obj.id) {
                updateNode(null, childZNode);
                showMessageBox("info", "资源信息更新成功")
            } else {
                addNodeAfterChangeOperation(null, childZNode, parentId);
                showMessageBox("info", "资源信息添加成功")
            }
        })
    })



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
    $("#parentId").val(id);
	$("#contentDiv").load(url);
}




/**
 * 新建记录
 */
function save() {


    $("#saveBtn").trigger("click");



}


/**
 *  删除位置信息
 */
function del() {
    if (!confirm("确定要删除该信息么？")) {
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    var url = "/resource/delete/" + id;
    $.getJSON(url, function (data) {
        if (data) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.removeNode(zTree.getSelectedNodes()[0]);
            zTree.selectNode(zTree.getNodeByParam("id", 1));
            showMessageBox("info", "资源信息删除成功!");
        } else {
            showMessageBox("danger","资源信息删除失败!");
        }
    })
}