/**
 * Created by Administrator on 2016/9/29.
 */



var locs = [];
var lines = [];
var searchVue = null;
var searchListVue = null;
var searchObject = null;
$(function () {
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
    $("#budgetDataTable").bootgrid(bootGridCfg);


    $("select").select2({
        theme: "bootstrap"
    });


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
            mcList: []
        }

    });


    initData();


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


function search() {
    searchObject = getSearchObject();
    var url = "matCost/search";
    $.ajaxSettings.async = false;
    $.post(url, searchObject, function (data) {
        searchListVue.$set("mcList", data);

    });
    $("#budgetDataTable").bootgrid();
}