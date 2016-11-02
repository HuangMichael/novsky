/**
 * Created by Administrator on 2016/11/2.
 */


$(function () {

    $("#fixListTable").bootgrid();


});


/**
 *导出excel
 */
function exportExcel() {
    var eqName = $("#fixListTable").bootgrid("getSearchPhrase");
    var columnSettings = $("#fixListTable").bootgrid("getColumnSettings");

    var titles = [];
    var colNames = [];
    for (var x in columnSettings) {
        if (columnSettings[x] != undefined && columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["identifier"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }

    }

    var docName = "报修单信息";
    var url = "workOrderReportCart/exportExcel?eqName=" + eqName + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
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


