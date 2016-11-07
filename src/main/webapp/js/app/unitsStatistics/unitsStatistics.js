$(document).ready(function () {
    App.setPage("inbox");  //Set current page
    App.init(); //Initialise plugins and elements
    Highcharts.setOptions({
        colors: ['#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#058DC7'],
        exporting: {
            enabled: true
        },
    });

    loadChartData(2016);
    //默认加载当月数据


    $("#currentMonth").on("click", function () {
        var reportMonth = addMonth(0);
        loadChartData(reportMonth);
        $("#setupDate").val(reportMonth);
    });
    $("#lastMonth").on("click", function () {
        var reportMonth = addMonth(-1);
        loadChartData(reportMonth);
        $("#setupDate").val(reportMonth);
    })


    $("#displayBtn").on("click", function () {
        var reportMonth = $("#setupDate").val();
        reportMonth = (!reportMonth) ? addMonth(0) : reportMonth;
        loadChartData(reportMonth);

    })
});


var dataMonth = [];

/**
 *
 * @param reportMonth
 */
function loadChartData(reportYear) {


    loadUnitsRankChart(reportYear);
    loadReportFinishChart(reportYear, 28);
    loadEfficiencyChart(reportYear);
}

/**
 *加载设备分类统计
 * @param reportMonth
 */
function loadUnitsRankChart(reportYear) {
    /**
     *
     * @param chart2Data
     * @returns {*}
     */
    var newData = [];

    function assembleData(chart2Data) {
        var sumOther = 0;
        chart2Data.forEach(function (e, i) {
            var obj = null;
            if (i < 5) {
                obj = {name: e["unitName"], y: e["fixNum"]};
                newData.push(obj);
            } else {
                sumOther += e["fixNum"];
            }

        });
        if (sumOther) {
            newData.push({
                name: "其他单位",
                y: sumOther
            })
            return newData;
        }
    }

    //ajax 请求当月设备分类前5
    var chart2Data = [];
    $.getJSON("/unitsStatistics/findByReportYear/" + reportYear, function (data) {
        chart2Data = assembleData(data);
    });
    var unitsRankChartConfig = {
        chart: {
            type: 'pie'
        },
        title: {
            text: reportYear + '年度维修数量前五名统计'
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
            name: '维修数量',
            colorByPoint: true,
            data: newData
        }]
    }
    $('#highCharts0').highcharts(unitsRankChartConfig);


}


/**
 *
 * @param year 根据年份获得有数据的月
 */
function getDataMonthByYear(year) {
    var url = "unitsStatistics/getDataMonthByYear/" + year;
    var months = [];
    $.getJSON(url, function (data) {
        months = data;
    });
    return months;
}

/**
 *加载设备分类统计
 * @param reportMonth
 */
function loadReportFinishChart(year, unitId) {

    dataMonth = getDataMonthByYear(year);
    var seriesOptions = [];
    var option0, option1;

    option0 = {
        "name": "分配数量",
        "data": getDataDistributed(unitId, year)
    };
    option1 = {
        "name": "完工数量",
        "data": getDataFinished(unitId, year)
    };
    seriesOptions.push(option0);
    seriesOptions.push(option1);


    /**
     *
     * @returns {Array}
     */
    function getDataMonthByYear(year) {
        var title = [];
        if (!year) {
            year = new Date().getFullYear();
        }
        var url = "unitsStatistics/getDataMonthByYear/" + year;
        $.getJSON(url, function (data) {

            title = data;
        });
        return title;
    }


    /**
     *
     * @returns {Array} 获取已分配的数据
     * @param unitId 外委单位ID
     * @param year 年份
     */
    function getDataDistributed(unitId, year) {
        $.ajaxSettings.async = false;
        var url = "unitsStatistics/getDataDistributed/" + unitId + "/" + year;
        var distributedNum = [];
        dataMonth = getDataMonthByYear(year).sort();
        $.getJSON(url, function (data) {
            dataMonth.forEach(function (m, i) {
                if (data[i]) {
                    distributedNum [i] = data[i]["reportNum"];
                } else {
                    distributedNum [i] = 0;
                }
            });
        });
        return distributedNum;
    }


    function getDataFinished(unitId, year) {
        $.ajaxSettings.async = false;
        var url = "unitsStatistics/getDataFinished/" + unitId + "/" + year;
        var finishNum = [];
        dataMonth = getDataMonthByYear(year).sort();
        $.getJSON(url, function (data) {
            dataMonth.forEach(function (m, i) {
                if (data[i]) {
                    finishNum [i] = data[i]["reportNum"];
                } else {
                    finishNum [i] = 0;
                }
            });
        });
        return finishNum;
    }

    var reportFinishChartConfig = {
        chart: {
            type: 'column'
        },

        exporting: {
            enabled: true
        },
        title: {
            text: '2016年度维修统计'
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: getDataMonthByYear(year)
        },
        yAxis: {
            min: 0,
            title: {
                text: '工单数量(单位:个)'
            }
        },
        series: seriesOptions
    }
    $('#highCharts1').highcharts(reportFinishChartConfig);


}


/**
 * 根据线路统计各状态的订单数量
 * @param reportMonth
 */
function loadLineChart(reportMonth) {
    function loadByStatus(status) {
        var url = "/portal/getLineReportNum/" + reportMonth + "/" + status;
        var dataList = [];
        $.ajaxSettings.async = false;
        $.getJSON(url, function (data) {
            for (var x in data) {
                if (data[x]['num']) {
                    dataList[x] = data[x]['num'];
                }
            }
        });
        return dataList;
    }


    var orderStatus = ["待分配", "维修中", "完工", "暂停", "取消"];
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
    $('#highCharts2').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: reportMonth + '年度外委单位维修及时率统计'
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
        series: [
            {
                name: orderStatus[0],
                data: loadByStatus(0)

            }, {
                name: orderStatus[1],
                data: loadByStatus(1)

            },

            {
                name: orderStatus[2],
                data: loadByStatus(2)
            },
            {
                name: orderStatus[3],
                data: loadByStatus(3)

            },
            {
                name: orderStatus[4],
                data: loadByStatus(4)

            }
        ]
    });


}


/**
 *加载设备分类统计
 * @param reportMonth
 */
function loadEfficiencyChart(year) {


    var seriesOptions = [];
    var option0;

    option0 = {
        "name": "维修及时率排名",
        "data": getDataPercent(year)
    };
    seriesOptions.push(option0);


    /**
     *
     * @returns {Array}
     */
    function getTop5Units(year) {
        var units = [];
        if (!year) {
            year = new Date().getFullYear();
        }
        var url = "unitsStatistics/findEffRankByReportYear/" + year;
        $.getJSON(url, function (data) {
            data.forEach(function (e, i) {
                units[i] = e["unitName"];
            })
        });
        return units;
    }


    /**
     *
     * @returns {Array} 获取已分配的数据
     * @param unitId 外委单位ID
     * @param year 年份
     */
    function getDataPercent(year) {
        $.ajaxSettings.async = false;
        var url = "unitsStatistics/findEffRankByReportYear/" + year;
        var distributedNum = [];

        $.getJSON(url, function (data) {
            data.forEach(function (m, i) {

                distributedNum[i] = m["percent"];
            })

        });
        return distributedNum;
    }


    var reportFinishChartConfig = {
        chart: {
            type: 'column'
        },

        exporting: {
            enabled: true
        },
        title: {
            text: '2016年度外委单位维修及时率统计'
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: getTop5Units(year)
        },
        yAxis: {
            min: 0,
            title: {
                text: '维修及时率百分比(单位：%)'
            }
        },
        series: seriesOptions
    }
    $('#highCharts2').highcharts(reportFinishChartConfig);


}