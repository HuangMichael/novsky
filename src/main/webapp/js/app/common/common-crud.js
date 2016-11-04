/**
 * Created by Administrator on 2016/11/4.
 */


var mainObject = "";
var vdm = null; //定义form数据模型
var formName = "";
var selectedIds = [];
var pointer = null;
var dataTableName = "";
var ids = [];//所有的ID的集合
var docName = "";

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


/*$(function () {
 //设置数据有效性验证配置项


 //设置数据有效性验证配置项
 var validateOptions = {
 message: '该值无效',
 fields: {
 /!*            "applyDate": {
 message: '申请日期无效',
 validators: {
 notEmpty: {
 message: '申请日期不能为空!'
 }

 }
 },*!/
 "ecname": {
 message: '易耗品名称无效',
 validators: {
 notEmpty: {
 message: '易耗品名称不能为空!'
 }

 }
 },
 "epermited": {
 message: '有无用电许可证无效',
 validators: {
 notEmpty: {
 message: '有无用电许可证不能为空!'
 }

 }
 },
 "updateReason": {
 message: '申请原因无效',
 validators: {
 notEmpty: {
 message: '申请原因不能为空!'
 }

 }
 },
 "confirmReason": {
 message: '确认更新原因无效',
 validators: {
 notEmpty: {
 message: '确认更新原因不能为空!'
 }

 }
 },
 "applicant": {
 message: '填报人无效',
 validators: {
 notEmpty: {
 message: '填报人不能为空!'
 },
 stringLength: {
 min: 1,
 max: 20,
 message: '1到20个字符'
 }
 }
 },
 "applyDep": {
 message: '申请部门无效',
 validators: {
 notEmpty: {
 message: '申请部门不能为空!'
 },
 stringLength: {
 min: 1,
 max: 20,
 message: '1到20个字符'
 }
 }
 },
 "amount": {
 message: '申请数量无效',
 validators: {
 notEmpty: {
 message: '申请数量不能为空!'
 }
 }
 },

 "auditor": {
 message: '填报人无效',
 validators: {
 notEmpty: {
 message: '填报人不能为空!'
 }
 }
 },

 /!*"auditDate": {
 message: '审核日期无效',
 validators: {
 notEmpty: {
 message: '审核日期不能为空!'
 }
 }
 },*!/

 "amount": {
 message: '申请数量无效',
 validators: {
 notEmpty: {
 message: '申请数量不能为空!'
 }
 }
 },

 "vlocations.id": {
 message: '位置无效',
 validators: {
 notEmpty: {
 message: '位置不能为空!'
 }
 }
 },

 "fixAdvice": {
 message: '维修意见无效',
 validators: {
 notEmpty: {
 message: '维修意见不能为空!'
 }
 }
 },

 "leaderAdvice": {
 message: '领导意见无效',
 validators: {
 notEmpty: {
 message: '领导意见不能为空!'
 }
 }
 },
 "eqclass": {
 message: '设备分类无效',
 validators: {
 notEmpty: {
 message: '设备分类不能为空!'
 }
 }
 }
 }
 };

 locs = findMyLoc();

 eqClasses = findMyEqClass();

 vdm = new Vue({
 el: "#detailContainer",
 data: {
 budgetBill: null,
 locs: locs,
 eqClasses: eqClasses
 }
 });

 ids = findAllRecordId().sort();

 //初始化加载列表
 $(dataTableName).bootgrid({
 selection: true,
 multiSelect: true,
 rowSelect: false,
 keepSelection: true
 }).on("selected.rs.jquery.bootgrid", function (e, rows) {
 //如果默认全部选中
 if (selectedIds.length === bills.length) {
 selectedIds.clear();
 }
 for (var x in rows) {
 if (rows[x]["id"]) {
 selectedIds.push(rows[x]["id"]);
 }
 }
 }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
 for (var x in rows) {
 selectedIds.remove(rows[x]["id"]);
 }
 });


 // 监听切换tab的方法

 $(formTab).on('click', function () {
 //首先判断是否有选中的
 var object = null;
 if (selectedIds.length > 0) {
 //切换tab时默认给detail中第一个数据
 object = findById(selectedIds[0]);
 } else {
 //没有选中的 默认显示整个列表的第一条
 object = findById(ids[0]);
 //所有的都在选中列表中
 selectedIds = ids;
 }
 vdm.$set(getMainObject(), object);
 /!* vdm.$set("locs", locs);
 vdm.$set("eqClass", eqClass);*!/

 });

 $("select").select2({
 theme: "bootstrap",
 tags: "true",
 placeholder: "请选择...",
 allowClear: true
 });


 $("#" + formName)
 .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
 e.preventDefault();
 saveMainObject();
 });


 formTab.on('click', function () {
 // activeTab = "detail";
 //setFormReadStatus("#detailForm", formLocked);
 //首先判断是否有选中的
 var object = null;
 if (selectedIds.length > 0) {
 //切换tab时默认给detail中第一个数据
 object = findById(selectedIds[0]);
 } else {
 //没有选中的 默认显示整个列表的第一条
 object = findById(ids[0]);
 //所有的都在选中列表中
 selectedIds = (ids);
 }
 vdm.$set(this.getMainObject(), object);
 setFormReadStatus("#detailForm", true);
 });
 });*/


/**
 *查询所有的id
 * */
function findAllRecordId() {


    var url = getMainObject() + "/findAllIds";
    $.getJSON(url, function (data) {
        ids = data;
    });
    return ids;
}


/**
 *导出excel
 */
function exportExcel() {
    var param = $(dataTableName).bootgrid("getSearchPhrase");
    var columnSettings = $(dataTableName).bootgrid("getColumnSettings");

    var titles = [];
    var colNames = [];
    for (var x in columnSettings) {
        if (columnSettings[x] != undefined && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }
    docName = (docName) ? docName : "导出数据";
    var url = getMainObject() + "/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
    bootbox.confirm({
        message: "确定导出查询结果记录么?",
        buttons: {
            confirm: {
                label: '是',
                className: 'btn-success'
            },
            cancel: {
                label: '否',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                window.location.href = url;
            }
        }
    });

}
