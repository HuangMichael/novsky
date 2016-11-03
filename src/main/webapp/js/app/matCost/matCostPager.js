/**
 * Created by Administrator on 2016/11/3.
 */


var url = "matCost/search";


/**
 * 获取查询参数
 * @returns {{locName: (*|jQuery), ecName: (*|jQuery), ecType: (*|jQuery)}}
 */
function getSearchParam() {
    var locName = $("#locName").val();
    var ecName = $("#ecName").val();
    var ecType = $("#ecType").val();
    var searchParam = {
        locName: locName,
        ecName: ecName,
        ecType: ecType
    }
    return searchParam;

}


var dataList = requestData(url, getSearchParam());


$(function () {
    callBackPagination();


    $("#searchBtn").on("click", function () {
        search();
    })
});


var limit = 10; //每页10条
var current = 1; //默认第0页


/**
 *分页回调函数
 */
function callBackPagination() {
    var totalCount = dataList.length;
    var showCount = dataList.length;

    createTable(current, limit, totalCount);
    $('#callBackPager').extendPagination({
        totalCount: totalCount,
        showCount: showCount,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            createTable(curr, limit, totalCount, dataList);
        }
    });
}


/**
 *
 * @param currPage
 * @param limit
 * @param total
 * @param data
 */
function createTable() {
    dataList = requestData(url, getSearchParam());
    var html = [];
    html.push(' <table  class="table table-bordered table-hover table-striped">');
    html.push(' <thead><tr>');
    html.push(' <th><input type="checkbox" /></th>');

    var colsConfig = getColsConfig();

    colsConfig.forEach(function (colConfig) {
        html.push(' <th>' + colConfig["title"] + '</th>');
    })
    html.push(' </tr></thead><tbody>');


    dataList.forEach(function (data) {
        html.push("<tr>");
        html.push('<td width="5%"><input type="checkbox" value="' + data["id"] + '" name="check' + data.id + '" /></td>');
        html.push('<td width="5%">' + data["id"] + '</td>');
        html.push('<td width="15%">' + data["applyDate"] + '</td>');
        html.push('<td width="15%">' + data["locName"] + '</td>');
        html.push('<td width="15%">' + data["ecName"] + '</td>');
        html.push('<td width="15%">' + data["amount"] + '</td>');
        html.push('<td width="15%">' + data["ecType"] + '</td>');
        html.push('</tr>');
    });
    html.push('</tbody></table>');
    var mainObj = $('#mainContent');
    mainObj.empty();
    mainObj.html(html.join(''));
}


/**
 *表格标题
 * @returns {string[]}
 */
function getColsConfig() {
    var colsConfig = [
        {title: "序号", colName: "id"},
        {title: "采购日期", colName: "applyDate"},
        {title: "位置", colName: "locName"},
        {title: "名称", colName: "ecName"},
        {title: "数量", colName: "amount"},
        {title: "分类", colName: "ecType"}
    ]
    return colsConfig;
}


/**
 *请求分页数据
 * @param url
 * @param searchParam 查询参数
 */
function requestData(url, searchParam) {
    var dataList = [];
    $.post(url, searchParam, function (data) {
        dataList = data.rows;
        console.log("this is my data-" + JSON.stringify(dataList))
    });
    return dataList;
}


function search() {
    console.log("this is search method-----------");

    var searchParam = getSearchParam();

    console.log("this is my searchParam-" + JSON.stringify(searchParam))

    dataList = requestData(url, searchParam);

    searchListVue.$()
}
