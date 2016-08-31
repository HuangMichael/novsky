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
										<table id="eqTable" class=" table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th width="5%">序号</th>
													<th width="10%">设备编号</th>
													<th width="20%">设备名称</th>
													<th width="10%">设备分类</th>
													<th width="15%">设备位置</th>
													<th width="10%">设备状态</th>
													<th width="10%">运行状态</th>
													<th width="10%">报修</th>
												</tr>
											</thead>
											<tbody id="tableContainer">
												<tr>
													<td>1</td>
													<td>123123123</td>
													<td>light</td>
													<td>light</td>
													<td>hujjj</td>
													<td>normal</td>
													<td>normal</td>
													<td>yes</td>
												</tr>
												<tr>
													<td>2</td>
													<td>123123123</td>
													<td>light</td>
													<td>light</td>
													<td>hujjj</td>
													<td>normal</td>
													<td>normal</td>
													<td>yes</td>
												</tr>
												<tr>
													<td>3</td>
													<td>123123123</td>
													<td>light</td>
													<td>light</td>
													<td>hujjj</td>
													<td>normal</td>
													<td>normal</td>
													<td>yes</td>
												</tr>
												<tr>
													<td>4</td>
													<td>123123123</td>
													<td>light</td>
													<td>light</td>
													<td>hujjj</td>
													<td>normal</td>
													<td>normal</td>
													<td>报修</td>
												</tr>
											</tbody>
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
<script type="text/javascript">
	$(function() {

		$('#paginator').bootpag({
			total: 23,
			page: 3,
			maxVisible: 10
		}).on('page', function(event, num) {
			console.log("num----" + num);
			//根据条件查询组装为html
			$("#tableContainer").html("第 " + num + "页内容"); // or some ajax content loading...
		});
	});

	//载入页面
	function loadPage(pageIndex, pageSize) {
		var url = "/eq/listVeq/" + startIndex + "/" + pageSize;
		$.getJSON(url, function(data) {

		});
	}
</script>