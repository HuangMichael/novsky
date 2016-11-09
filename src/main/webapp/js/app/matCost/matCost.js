/**
 * Created by Administrator on 2016/9/29.
 */



var locs = [];
var lines = [];
var searchVue = null;
var searchListVue = null;
var searchObject = null;

var dataTableName = "#matCostDataTable";

var bootGridCfg = {
    searchSettings: {
        delay: 100,
        characters: 3
    }
    ,
    templates: {
        search: ""

    }
};

var mcList = [];
$(function () {


    initSelect();


    locs = getMyLocs();
    lines = getMyLines();
    searchVue = new Vue({
        el: "#searchForm",
        data: {
            locs: locs,
            lines: lines
        }
    });


    searchListVue = new Vue({
        el: "#matCostList",
        data: {
            mcList: null
        }

    });


    //initData();


    $("#searchBtn").on("click", function () {
        search();
    });

    $("#locName").on("change", function () {
        search();
    });
    $("#ecName").on("change", function () {
        search();
    });
    $("#ecType").on("change", function () {
        search();
    });

    $(document).keyup(function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            //调用查询方法
            search();
        }
    });
    search();

});


/**
 * 查询我的位置
 */
function getMyLocs() {
    var url = "/matCost/findMyLocs";
    $.getJSON(url, function (data) {
        locs = data;
    });
    return locs;
}


/**
 * 查询所有线路
 */
function getMyLines() {
    var url = "/line/findAllLines";
    $.getJSON(url, function (data) {
        lines = data;
    });
    return lines;
}


/**
 * 获取查询模型
 */
function getSearchObject() {
    var ecType = $("#ecType").val();
    var line = $("#line").val();
    var locName = $("#locName").val();
    var ecName = $("#ecName").val();

    searchObject = {
        ecType: ecType,
        line: line,
        locName: locName,
        ecName: ecName
    };
    return searchObject;
}


/**
 * 初始化数据
 */
function initData() {
    search();
}


/**
 * 查询返回集合
 * @returns {Array}
 */
function search() {
    searchObject = getSearchObject();
    var url = "matCost/search";
    $.ajaxSettings.async = false;
    $.post(url, searchObject, function (data) {
        mcList = data;
        searchListVue.$set("mcList", mcList);
        // $("#budgetDataTable").bootgrid(bootGridCfg);
    });


    return mcList;
}


function loadPage() {
    var url = "matCost/loadPage";
    $("#ctb").load(url);
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

    var docName = "物资消耗信息";
    var url = "matCost/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
    bootbox.confirm({
        message: "确定导出查询结果记录么？?",
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