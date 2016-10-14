/**
 * Created by Administrator on 2016/9/29.
 */



var locs = [];
var lines = [];


var bootGridCfg = {
    searchSettings: {
        delay: 100,
        characters: 3
    }
};
$(function () {


    $("select").select2({
        theme: "bootstrap"
    });

    $("#budgetDataTable").bootgrid(bootGridCfg);


});


/**
 * 导入excel
 */
function importExcel() {
    var url = "/workOrderMatCost/importExcel";
    $.getJSON(url, function (data) {
        if (data.result) {
            showMessageBox("info", data['resultDesc']);
        } else {
            showMessageBox("danger", data['resultDesc']);
        }
    })
}