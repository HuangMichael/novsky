$(function () {
    Highcharts.setOptions({
        colors: ['#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#058DC7']
    });
    var url = "/line/findAllLines";
    var lines = [];
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        for (var x in data) {
            if (data[x]['description']) {
                lines[x] = data[x]['description'];
            }
        }
    });
    $('#highcharts1').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text:  (new Date().getMonth() + 1)+'月维修单状态按线别统计'
        },
        xAxis: {
            categories: lines,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '工单数量(单位:个)'
            }
        },
        lang: {
            noData: "当前无显示数据"
        },
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '待分配',
            data: getReportNumByLine()

        }, {
            name: '维修中',
            data: getFixingNumByLine()

        },

            {
                name: '完工',
                data: getFixedNumByLine()

            },
            {
                name: '暂停',
                data: getSuspendNumByLine()

            },
            {
                name: '取消',
                data: getAbortNumByLine()

            }
        ]
    });


    //ajax 请求当月设备分类前5
    var chart2Data = [];
    $.getJSON("/portal/findTopEqClass", function (data) {
        chart2Data = data;
    });


    $('#highcharts2').highcharts({
        chart: {
            type: 'pie'
        },
        title: {
            text: (new Date().getMonth() + 1) + '月报修按设备类型统计'
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    format: '{point.name}: {point.y}'
                }
            }
        },
        exporting: {
            enabled: true
        },
        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>报修: <b>{point.y}</b>单/共<b>{point.total}</b>单<b>/占比:{point.percentage:.1f}%</b>'
        },
        series: [{
            name: '报修数量',
            colorByPoint: true,
            data: chart2Data
        }]
    });


    var seriesOptions = [];
    var option0, option1, months;

    option0 = {
        "name": "报修数量",
        "data": get3MonthReportNum()
    };
    option1 = {
        "name": "完工数量",
        "data": get3MonthFinishNum()
    };
    seriesOptions.push(option0);
    seriesOptions.push(option1);


    $('#highcharts0').highcharts({
        chart: {
            type: 'column'
        },

        exporting: {
            enabled: false
        },
        title: {
            text: '最近3个月报修完成情况统计'
        },
        /*    subtitle: {
         text: get3MonthTitle()
         },*/
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: get3MonthTitle()
        },
        yAxis: {
            min: 0,
            title: {
                text: '工单数量(单位:个)'
            }
        },
        series: seriesOptions
    });
    loadReportCartNum();


});

function loadReportCartNum() {
    var url = "/workOrderReportCart/findMyCartSize";
    $.getJSON(url, function (data) {
        if (data) {
            $("#reportOrderSize").html(data);
        }
    })
}

function get3MonthTitle() {
    var title = [];
    var date = new Date();
    title.push((date.getMonth() - 1) + "月");
    title.push(date.getMonth() + "月");
    title.push((date.getMonth() + 1) + "月");
    return title;
}


function get3MonthReportNum() {
    $.ajaxSettings.async = false;
    var url = "/workOrderReport/sel3mRptNum";
    var reportNums = [];
    $.getJSON(url, function (data) {
        for (var x = 0; x < 3; x++) {
            if (!isNaN(data[x]["reportNum"]) && data[x]["reportNum"]) {
                reportNums.push(data[x]["reportNum"]);
            } else {
                reportNums.push(0);
            }
        }
    });
    return reportNums;
}

function get3MonthFinishNum() {
    $.ajaxSettings.async = false;
    var url = "/workOrderReport/sel3mFinishNum";
    var finishNums = [];
    $.getJSON(url, function (data) {
        for (var x = 0; x < 3; x++) {
            if (!isNaN(data[x]["finishNum"]) && data[x]["finishNum"]) {
                finishNums.push(data[x]["finishNum"]);
            } else {
                finishNums.push(0);
            }
        }
    });
    return finishNums;
}


function getReportNumByLine() {
    var reportLineNum = [];
    var url = "/portal/getLineReportNum";
    $.getJSON(url, function (data) {
        for (var x in data) {
            reportLineNum[x] = data[x]["reportNum"];
        }
    });
    return reportLineNum;
}


function getAbortNumByLine() {
    var abortLineNum = [];
    var url = "/portal/getLineAbortNum";
    $.getJSON(url, function (data) {
        for (var x in data) {
            abortLineNum[x] = data[x]["abortNum"];
        }
    });
    return abortLineNum;
}


function getFixedNumByLine() {
    var fixedLineNum = [];
    var url = "/portal/getLineFixedNum";
    $.getJSON(url, function (data) {
        for (var x in data) {
            fixedLineNum[x] = data[x]["fixedNum"];
        }
    });
    return fixedLineNum;
}


function getFixingNumByLine() {
    var fixingLineNum = [];
    var url = "/portal/getLineFixingNum";
    $.getJSON(url, function (data) {
        for (var x in data) {
            fixingLineNum[x] = data[x]["fixingNum"];
        }
    });
    return fixingLineNum;
}

function getSuspendNumByLine() {
    var suspendLineNum = [];
    var url = "/portal/getLineSuspendNum";
    $.getJSON(url, function (data) {
        for (var x in data) {
            suspendLineNum[x] = data[x]["suspendNum"];
        }
    });
    return suspendLineNum;
}