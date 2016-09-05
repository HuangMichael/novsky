function transformDate(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    dateStr += date.getFullYear() + "-";
    dateStr += parseInt(date.getMonth() + 1) + "-";
    dateStr += date.getDate() + " ";
    dateStr += (date.getHours() < 10) ? "0" + date.getHours() + ":" : date.getHours() + ":";
    dateStr += (date.getMinutes() < 10) ? "0" + date.getMinutes() + ":" : date.getMinutes() + ":";
    dateStr += (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
    return dateStr
}

function transformYMD(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    dateStr += date.getFullYear() + "-";
    dateStr += (parseInt(date.getMonth() + 1) < 10) ? "0" + parseInt(date.getMonth() + 1) + "-" : parseInt(date.getMonth() + 1) + "-";
    dateStr += (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
    return dateStr
}


function transformDateHMS(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    if (date) {
        dateStr += (date.getHours() < 10) ? "0" + date.getHours() + ":" : date.getHours() + ":";
        dateStr += (date.getMinutes() < 10) ? "0" + date.getMinutes() + ":" : date.getMinutes() + ":";
        dateStr += (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
    }
    return dateStr;
}


function getFormJsonData(formId) {
    var array = $("#" + formId).serializeArray();
    var objStr = "{";
    for (var x in array) {
        var name = array[x]["name"];
        var value = array[x]["value"];
        if (name && value) {
            objStr += '"' + name + '"';
            objStr += ":";
            objStr += '"' + value + '",'
        }
    }
    objStr = objStr.substring(0, objStr.length - 1);
    objStr += "}";
    return objStr
}
function fillForm(data, formId) {
    var formArray = $(formId).serializeArray();
    var attrName;
    for (var x in formArray) {
        attrName = formArray[x]["name"];
        if (attrName) {
            $("input[name='" + attrName + "']").val(data[attrName] ? data[attrName] : null)
        }
    }
}
function showMessageBox(type, message) {
    $.bootstrapGrowl(message, {type: type, align: "right", stackup_spacing: 30})
}
function loadTableData(t, c, d) {
    var thead = $("#thead");
    var tbody = $("#tbody");
    if (thead) {
        var html = "<tr>";
        for (var x in c) {
            if (c[x]["title"]) {
                html += ('<th  data-column-id="' + c[x]["name"] + '">' + c[x]["title"] + "</th>")
            }
        }
        html += "</tr>";
        thead.html(html)
    }
    var html1 = "<tr>";
    for (var i in d) {
        for (var a in c) {
            if (d[i][c[a].name]) {
                html1 += "<td>" + d[i][c[a].name] + "</td>"
            }
        }
        html1 += "<tr>"
    }
    tbody.html(html1)
}
function addNodeAfterOperation(tree, childNode) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var parentZNode = zTree.getNodeByParam("id", getSelectedNodeId(), null);
    zTree.addNodes(parentZNode, childNode, true);
    zTree.selectNode(zTree.getNodeByParam("id", childNode.id))
}

/**
 *
 * @param tree 树节点
 * @param childNode 子节点
 * @param parentId 重新选择的上级
 */
function addNodeAfterChangeOperation(tree, childNode, parentId) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var parentZNode = zTree.getNodeByParam("id", parentId, null);
    zTree.addNodes(parentZNode, childNode, true);
    zTree.selectNode(zTree.getNodeByParam("id", childNode.id))
}


function updateNode(tree, nodeName) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var node = getSelectedNode();
    node.name = nodeName.name;
    zTree.updateNode(node)
}

function getTreeRoot() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    return zTree;
}
function getSelectedNodeId() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    return id
}
function getSelectedNode() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    return selectedNode
}
/**
 *
 * @param type  info  danger
 * @param message center
 */
function showMessageBox(type, message) {
    $.bootstrapGrowl(message, {type: type, align: "right", stackup_spacing: 30})
};


/**
 *
 * @param type info  danger
 * @param position left center right
 * @param message
 */
function showMessageBoxCenter(type, position, message) {
    $.bootstrapGrowl(message, {type: type, align: position, stackup_spacing: 30})
};


/*
Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
*/


/**
 * 移除指定的元素
 * */
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


/**
 * 移除指定的元素
 * */
Array.prototype.removeButThis = function (val) {
    for (var x in this) {
        //如果值有重复
        if (this[x] == val) {
            this.clear();
            this.push(val);
        }

    }
}

Array.prototype.clear = function () {
    this.splice(0, this.length);
    console.log(this); // 输出 []，空数组，即被清空了
}


var replaceNull = function (obj) {

    var result = "";
    return result = (!obj) ? "" : obj;
}


/**
 *  返回bootgrid 列表中所有的ID数组
 */
function getAllTableIdsByTableId(tableName) {
    var records = [];
    $("#" + tableName + " input[type='checkbox']").each(function (i) {
        records.push($(this).val());
    });
    console.log(records);
    return records;

}


Array.prototype.unique = function () {
    var n = {}, r = []; //n为hash表，r为临时数组
    for (var i = 0; i < this.length; i++) //遍历当前数组
    {
        if (!n[this[i]]) //如果hash表中没有当前项
        {
            n[this[i]] = true; //存入hash表
            r.push(this[i]); //把当前数组的当前项push到临时数组里面
        }
    }
    return r;
}


