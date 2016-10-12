<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<%@include file="../common/common-head.jsp" %>
<body>
<!-- HEADER -->
<%@include file="../common/common-navbar.jsp" %>
<!--/HEADER -->
<!-- PAGE -->
<section id="page">
    <!-- SIDEBAR -->
    <%@include file="../common/common-siderbar.jsp" %>
    <!-- /SIDEBAR -->
    <div id="main-content">
        <div class="container">
            <div class="row">
                <div id="content" class="col-lg-12">
                    <!-- PAGE HEADER-->
                    <%@include file="../common/common-breadcrumb.jsp" %>
                    <div class="clearfix">
                        <span class="date-range pull-right">
											<div class="btn-group">
												<a class="js_update btn btn-default" onclick="loadChartData(0)">当月</a>
												<a class="js_update btn btn-default" onclick="loadChartData(-1)">上月</a>
												<a id="reportrange" class="btn reportrange">
													<i class="fa fa-calendar"></i>
													<span>选择月份</span>
													<i class="fa fa-angle-down"></i>
												</a>
											</div>
										</span>
                        <!-- /DATE RANGE PICKER -->
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div id="highCharts0"></div>
                        </div>
                        <div class="col-md-6">
                            <div id="highCharts1"></div>
                        </div>
                        <div class="col-md-12">
                            <div id="highCharts2"></div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../common/common-foot.jsp" %>
<script type="text/javascript" src="js/Highcharts-4.2.4/js/highcharts.js"></script>
<script>
    $(document).ready(function () {
        App.setPage("inbox");  //Set current page
        App.init(); //Initialise plugins and elements


        Highcharts.setOptions({
            colors: ['#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#058DC7']
        });


        //默认加载当月数据
        loadChartData(0);
    });


    /**
     *
     * @param offset
     */
    function loadChartData(offset) {
        loadEqClassChart(offset);
        loadReportFinishChart(offset);
        loadLineChart(offset);
    }

    /**
     *加载设备分类统计
     * @param offset
     */
    function loadEqClassChart(offset) {


        /**
         *
         * @param chart2Data
         * @returns {*}
         */
        var newData = [];
        function assembleData(chart2Data) {
            var sumOther = 0;
            for (var x in chart2Data) {
                if (x < 5) {
                    newData[x] = {
                        name: chart2Data[x][1],
                        y: chart2Data[x][2]
                    }
                } else {
                    sumOther += chart2Data[x][2]?chart2Data[x][2]:0;
                }
            }
            newData[6] = {
                name: "其他分类",
                y: sumOther
            }
            return newData;
        }


        //ajax 请求当月设备分类前5
        var chart2Data = [];
        $.getJSON("/portal/findTopEqClass/" + offset, function (data) {
            chart2Data = assembleData(data);
        });
        var eqClassChartConfig = {
            chart: {
                type: 'pie'
            },
            title: {
                text: (new Date().getMonth() + 1 + offset) + '月报修按设备类型统计'
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
                data: newData
            }]
        }
        $('#highCharts0').highcharts(eqClassChartConfig);


    }


    /**
     *加载设备分类统计
     * @param offset
     */
    function loadReportFinishChart(offset) {
        var seriesOptions = [];
        var option0, option1;

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
                    if (data[x] && data[x]["reportNum"] && !isNaN(data[x]["reportNum"])) {
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
                    if (data[x] && data[x]["finishNum"] && !isNaN(data[x]["finishNum"])) {
                        finishNums.push(data[x]["finishNum"]);
                    } else {
                        finishNums.push(0);
                    }
                }
            });
            return finishNums;
        }

        var reportFinishChartConfig = {
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
        }
        $('#highCharts1').highcharts(reportFinishChartConfig);


    }


    /**
     * 根据线路统计各状态的订单数量
     * @param offset
     */
    function loadLineChart(offset) {


        function getReportNumByLine(offset) {
            var reportLineNum = [];
            var url = "/portal/getLineReportNum/";
            $.getJSON(url, function (data) {
                for (var x in data) {
                    reportLineNum[x] = data[x]["reportNum"];
                }
            });
            return reportLineNum;
        }


        function getAbortNumByLine(offset) {
            var abortLineNum = [];
            var url = "/portal/getLineAbortNum";
            $.getJSON(url, function (data) {
                for (var x in data) {
                    abortLineNum[x] = data[x]["abortNum"];
                }
            });
            return abortLineNum;
        }


        function getFixedNumByLine(offset) {
            var fixedLineNum = [];
            var url = "/portal/getLineFixedNum";
            $.getJSON(url, function (data) {
                for (var x in data) {
                    fixedLineNum[x] = data[x]["fixedNum"];
                }
            });
            return fixedLineNum;
        }


        function getFixingNumByLine(offset) {
            var fixingLineNum = [];
            var url = "/portal/getLineFixingNum";
            $.getJSON(url, function (data) {
                for (var x in data) {
                    fixingLineNum[x] = data[x]["fixingNum"];
                }
            });
            return fixingLineNum;
        }

        function getSuspendNumByLine(offset) {
            var suspendLineNum = [];
            var url = "/portal/getLineSuspendNum";
            $.getJSON(url, function (data) {
                for (var x in data) {
                    suspendLineNum[x] = data[x]["suspendNum"];
                }
            });
            return suspendLineNum;
        }


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
                text: (new Date().getMonth() + 1 + offset) + '月维修单状态按线别统计'
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
                data: getReportNumByLine(offset)

            }, {
                name: '维修中',
                data: getFixingNumByLine(offset)

            },

                {
                    name: '完工',
                    data: getFixedNumByLine(offset)

                },
                {
                    name: '暂停',
                    data: getSuspendNumByLine(offset)

                },
                {
                    name: '取消',
                    data: getAbortNumByLine(offset)

                }
            ]
        });
    }
</script>

<%--<script type="text/javascript" src="/js/app/portal/portal.js"></script>--%>
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script src="js/Highcharts-4.2.4/js/modules/exporting.js"></script>
</body>
</html>