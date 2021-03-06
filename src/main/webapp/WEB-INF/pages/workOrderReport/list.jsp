<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="js/jquery-treegrid/css/jquery.treegrid.css">
<link rel="stylesheet" href="http://yandex.st/highlightjs/7.3/styles/default.min.css">
<div class="container">
	<div class="row">
		<div id="content" class="col-lg-12">
			<!-- PAGE HEADER-->
			<%@include file="../common/common-breadcrumb.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<!-- BOX -->
					<div class="box border blue">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>报修单信息</h4>
						</div>

						<div class="box-body">
							<div id="contentDiv">
								<div class="box-body">
									<div class="tabbable">
										<ul class="nav nav-tabs" id="myTab">
											<li class="active">
												<a href="#tab_1_0" data-toggle="tab">
													<i class="fa fa-home" id="eq"></i>报修单信息</a>
											</li>

											<li>
												<a href="#pdf_view" data-toggle="tab">
													<i class="fa fa-flag" id="pdf-preview"></i>报修单预览</a>
											</li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane fade in active" id="tab_1_0">

												<table id="fixListTable" class="table table-bordered table-hover table-striped" data-toggle="bootgrid" data-ajax="true" data-url="/workOrderReportCart/data">
													<thead>
														<tr>
															<th data-column-id="orderLineNo" data-width="10%" data-searchable="false">跟踪号</th>
															<th data-column-id="eqName" data-width="10%" data-searchable="false">设备名称</th>
															<th data-column-id="orderDesc" data-width="20%" data-searchable="false">故障描述</th>
															<th data-column-id="locName" data-width="10%" data-searchable="false">设备位置</th>
															<th data-column-id="eqClass" data-width="8%" data-searchable="false">设备分类</th>
															<th data-column-id="nodeTime" data-width="10%" data-searchable="false">生成时间</th>
															<th data-column-id="nodeState" data-width="5%" data-searchable="false">报修状态</th>
														</tr>
													</thead>

												</table>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /BOX -->
				</div>
			</div>
			<%@include file="../common/common-back2top.jsp" %>
		</div>
		<!-- /CONTENT-->
	</div>
</div>

<div class="modal fade " id="fix_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel2">报修车明细信息</h4>
			</div>
			<div class="modal-body" id="modal_div">
				<%@include file="form.jsp" %>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>

<script type="text/javascript" src="js/tableExport/tableExport.js"></script>
<script type="text/javascript" src="js/tableExport/html2canvas.js"></script>
<script type="text/javascript" src="js/tableExport/jquery.base64.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/jspdf.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/libs/base64.js"></script>

<script>
	var tableName = "#fixListTable",
		tableContainer = "#tab_1_0";
	var searchParam = [
		"orderDesc",
		"locName"
	];
	var queryString = "";
	var baseQueryUrl = "workOrderReportCart/findByOrderDescAndLocName";
	$(function() {
		//初始化查询
		initDataList(tableName, tableContainer, "");
		//to do  change事件 发起查询  重新组装查询的字符串
		var searchArray = [];
	});

	function search() {
		queryString = assembleQueryString(searchParam);
		//initDataList(tableName, tableContainer, baseQueryUrl + queryString);
	}

	function exportDoc(docType) {

		$('#fixListTable').tableExport({
			type: docType,
			escape: 'false',
			filename: new Date().getFullYear(),
			consoleLog: 'true'
		});

	}

	function initDataList(tableName, tableContainer, url) {
		//如果传递了URL参数 说明是二次查询
		if(url) {
			$("#tab_1_0").load(url);
		}
		$(tableName).bootgrid({
			navigation: 3,
			padding: 2,
			columnSelection: !0,
			rowCount: [10, 15, 20, -1],
			selection: !1,
			multiSelect: !1,
			rowSelect: !1,
			keepSelection: !1,
			highlightRows: 1,
			sorting: true,
			columnSelection: 1
			/*templates: {
				search: '<div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" id="orderDesc" placeholder="维修描述" onClick="search()"/></div></div><div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" placeholder="位置描述" id="locName" onClick="search()"/></div></div>',
			}*/
		});

	}
	/*
	 *组装查询字符串
	 * 根据查询参数数组获取返回的查询字符串 get方式
	 * */

	function assembleQueryString(searchParam) {
		queryString = "?";
		var param = "",
			paramValue = "";
		for(var x = 0; x < searchParam.length; x++) {
			param = searchParam[x];
			paramValue = $("#" + searchParam[x]).val();
			if(paramValue) {
				queryString += param + "=" + paramValue + "&";
			}
		}
		queryString = queryString.substring(0, queryString.length - 1);

		console.log("queryString--------------------" + queryString);
		return queryString;
	}
</script>