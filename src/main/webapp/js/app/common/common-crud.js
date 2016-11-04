/**
 * Created by Administrator on 2016/11/4.
 */


var mainObject = "";
var vdm = null; //定义form数据模型
var formName = "";
var selectedIds = [];
var pointer = null;

/**
 *
 * @returns {string}
 * 获取主对象
 */
function getMainObject() {


    return mainObject;
}


/**
 *保存或者更新 后刷新数据列表
 * */
function saveMainObject() {
    var objStr = getFormJsonData(formName);
    var object = JSON.parse(objStr);
    var url = getMainObject() + "/save";
    $.post(url, object, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            setFormReadStatus("#" + formName, true);
            formTab.data("status", "saved");
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"]);
            setFormReadStatus("#" + formName, false);
        }
    });
}


/**
 *根据id查询返回对象
 * */
function findById(id) {
    var mainObject = getMainObject();
    var object = null;
    var url = mainObject + "/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *  上一条 记录
 */
function preRecord() {
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        var object = findById(selectedIds[pointer]);
        vdm.$set(getMainObject(), object);
    }
}
/**
 *  下一条记录
 */
function nextRecord() {
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        var object = findById(selectedIds[pointer]);
        vdm.$set(getMainObject(), object);
    }
}
