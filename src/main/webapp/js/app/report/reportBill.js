/**
 * Created by huangbin on 2016/11/2.

 */


$(function () {


    //导出必须配置的两个量
    dataTableName = "#fixListTable";
    docName = "报修单信息";
    mainObject = "workOrderReport";


    var url = "/commonData/findVEqClass";
    $.getJSON(url, function (data) {
        eqClasses = data;
    });


    initSelect();

    var searchVue = new Vue({
        el: "#searchBox",
        data: {
            eqClasses: eqClasses
        }

    });

    searchModel = [
        {"param": "orderLineNo", "paramDesc": "跟踪号"},
        {"param": "orderDesc", "paramDesc": "故障描述"},
        {"param": "location", "paramDesc": "设备位置"},
        {"param": "eqClass", "paramDesc": "设备分类"}
    ];

    initBootGrid(dataTableName);

    search();


});
