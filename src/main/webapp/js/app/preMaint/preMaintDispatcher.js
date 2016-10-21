$(document).ready(function () {
    $('#pmOrderList').bootgrid({
        columnSelection: 1,
        rowCount: [10, 20, 25, -1],
        formatters: {
            "opMenus": function (column, row) {
                return '<a class="btn btn-default btn-xs"  onclick="finish(' + row.id + ')" title="完工" ><i class="glyphicon glyphicon glyphicon-ok"></i></a>';
            }
        }
    });
});