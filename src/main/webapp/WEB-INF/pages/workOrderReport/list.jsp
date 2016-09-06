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
															<th data-column-id="orderLineNo" data-width="10%">跟踪号</th>
															<th data-column-id="eqName" data-width="10%">设备名称</th>
															<th data-column-id="orderDesc" data-width="20%">故障描述</th>
															<th data-column-id="locName" data-width="10%">设备位置</th>
															<th data-column-id="eqClass" data-width="8%">设备分类</th>
															<th data-column-id="nodeTime" data-width="10%">生成时间</th>
															<th data-column-id="nodeState" data-width="5%">报修状态</th>
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
	$(function() {
		$("#fixListTable").bootgrid({
			navigation: 3,
			padding: 2,
			columnSelection: !0,
			rowCount: [8, 15, 20, -1],
			selection: !1,
			multiSelect: !1,
			rowSelect: !1,
			keepSelection: !1,
			highlightRows: 1,
			sorting: true,
			columnSelection: 1,
			templates: {
				search: '<div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" id="orderDesc" placeholder="维修描述" /></div></div><div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" placeholder="位置描述" id="locName"/></div></div>',
			}
		});


	});

	function search(searchStr) {

		searchStr = searchStr.substring(0, searchStr.length - 1);
		var searchArray = searchStr.split("&");
		console.log("调用查询方法--------------" + searchStr);
		var url = "workOrderReport/findByOrderDescAndLocName" + "?" + searchStr;

	
		$("#tab_1_0").load(url);
		$("#fixListTable").bootgrid({
			navigation: 3,
			padding: 2,
			columnSelection: !0,
			rowCount: [8, 15, 20, -1],
			selection: !1,
			multiSelect: !1,
			rowSelect: !1,
			keepSelection: !1,
			highlightRows: 1,
			sorting: true,
			columnSelection: 1,
	
			templates: {
				search: '<div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" id="orderDesc" placeholder="维修描述" /></div></div><div class="{{css.search}}"><div class="input-group"><input type="text" class="{{css.searchField}}" placeholder="位置描述" id="locName"/></div></div>',
			}
		});



	}

	function exportDoc(docType) {

		$('#fixListTable').tableExport({
			type: docType,
			escape: 'false',
			filename: new Date().getFullYear(),
			consoleLog: 'true'
		});

	}
</script>