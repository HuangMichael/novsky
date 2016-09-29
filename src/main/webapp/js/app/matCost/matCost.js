/**
 * Created by Administrator on 2016/9/29.
 */



var locs = [];
var lines = [];
var searchVue = null;
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