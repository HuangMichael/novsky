<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!-- /SAMPLE BOX CONFIGURATION MODAL FORM-->

<div class="container" id="equipmentsApp">
	<div class="row">
		<div id="content" class="col-lg-12">
			<!-- PAGE HEADER-->
			<%@include file="../common/common-breadcrumb.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<!-- BOX -->
					<div class="box border blue">
						<div class="box-title">
							<h4 class="appTitle"><i class="fa fa-sitemap"></i>新设备信息</h4>
						</div>
						<%@include file="../common/common-menubar.jsp"%>
						<div class="box-body">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active">
										<a href="#tab_1_0" data-toggle="tab" style="font-family: 微软雅黑;font-weight: bold">
											<i class="fa fa-home" id="eq"></i>设备信息</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane fade in active" id="tab_1_0" style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">

										<div class="container-fluid">

											<!-- Collect the nav links, forms, and other content for toggling -->
											<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
												<form class="navbar-form navbar-left" role="search">
													<div class="form-group">
														<label>设备编号</label>
														<input type="text" id="s_eqCode" class="form-control" placeholder="设备编号">
														<label>设备名称</label>
														<input type="text" id="s_eqName" class="form-control" placeholder="设备名称">
														<label>设备位置</label>
														<input type="text" id="s_locName" class="form-control" placeholder="设备位置">
														<label>设备分类</label>
														<input type="text" id="s_eqClass" class="form-control" placeholder="设备分类">
													</div>
													<button type="button" class="btn btn-default" id="searchBtn">查询</button>
												</form>
												<ul class="nav navbar-nav navbar-right">
													<li class="dropdown">
														<a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据导出 <span class="caret"></span></a>
														<ul class="dropdown-menu">
															<li>
																<a onclick="exportDoc('pdf')">导出pdf</a>
															</li>
															<li>
																<a onclick="exportDoc('csv')">导出csv</a>
															</li>
															<li>
																<a onclick="exportDoc('doc')">doc</a>
															</li>
														</ul>
													</li>
												</ul>
											</div>
											<!-- /.navbar-collapse -->
										</div>
										<!-- /.container-fluid -->

										<table id="equipmentsDataTable" class=" table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th width="5%">序号</th>
													<th width="15%">设备编号</th>
													<th width="20%">设备名称</th>
													<th width="10%">设备分类</th>
													<th width="20%">设备位置</th>
													<th width="10%">设备状态</th>
													<th width="10%">运行状态</th>
												</tr>
											</thead>
											<tbody id="tableContainer"></tbody>
											<tfoot>
											</tfoot>
										</table>
										<div id="paginator"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="../common/common-back2top.jsp" %>
</div>
<script src="/js/bootpag/jquery.bootpag.min.js"></script>
<script type="text/javascript" src="js/tableExport/tableExport.js"></script>
<script type="text/javascript" src="js/tableExport/jquery.base64.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/jspdf.js"></script>
<script type="text/javascript" src="js/tableExport/jspdf/libs/base64.js"></script>
<script type="text/javascript">
	$(function() {

		fistLoad();
		//初始化加载第一页前十
		var url = "eq/listVeq/1/10";
		var pageHtml = "";
		$.getJSON(url, function(data) {
			$('#paginator').bootpag({
				total: data.totalPages -1,
				page: 1,
				maxVisible: 10
			}).on('page', function(event, num) {
				$("#tableContainer").load("eq/loadPage/" + num + "/10");

			});

		})

		/*
		 * 查询方法
		 * */
		$("#searchBtn").on("click", function() {
			search();
		});

	});

	function fistLoad() {
		$("#tableContainer").load("eq/loadPage/1/10");
	}

	/**
	 * 根据条件查询
	 */
	function search() {
		var eqCode = $("#s_eqCode").val();
		var eqName = $("#s_eqName").val();
		var locName = $("#s_locName").val();
		var eqClass = $("#s_eqClass").val();
		var url = "/eq/search";
		var searchModel = {
			eqCode: eqCode
				/*			eqName: eqName,
							locName: locName,
							eqClass: eqClass*/
		}
		$.post(url, searchModel, function(data) {
			console.log("查询方法-----------------");
		});
	}
	/*
	 *导出数据
	 * */
	function exportDoc(docType) {
		$('#equipmentsDataTable').tableExport({
			type: docType,
			escape: false,
			consoleLog: true,
			htmlContent: true,
			unicode: false
		});

	}
</script>