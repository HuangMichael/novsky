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
							<h4 class="appTitle"><i class="fa fa-sitemap"></i>低值易耗品采购申请信息</h4>
						</div>
						<%@include file="../common/common-menubar.jsp"%>
						<div class="box-body">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active">
										<a href="#tab_1_0" data-toggle="tab" style="font-family: 微软雅黑;font-weight: bold">
											<i class="fa fa-home" id="eq"></i>低值易耗品采购申请信息</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane fade in active" id="tab_1_0" style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
										<div class="container-fluid">
											<table id="budgetDataTable" class=" table table-striped table-bordered table-hover" data-toggle="bootgrid" data-ajax="true" data-url="/ecbudget/data">
												<thead>
													<tr>
														<th data-align="center" data-column-id="applyDate" data-width="10%">采购日期</th>
														<th data-align="center" data-column-id="accessoryName" data-width="10%">配件名称</th>
														<th data-align="center" data-column-id="specifications" data-width="5%">规格型号</th>
														<th data-align="center" data-column-id="applyDep" data-width="10%">申请部门</th>
														<th data-align="center" data-column-id="locName" data-width="10%">位置</th>
														<th data-align="center" data-column-id="applicant" data-width="5%">申请人</th>
														<th data-align="center" data-column-id="amount" data-width="5%">申请数量</th>
														<th data-align="center" data-column-id="purpose" data-width="20%">用途</th>
														<th data-align="center" data-column-id="approver" data-width="5%">批准人</th>
														<th data-align="center" data-column-id="handler" data-width="5%">经办人</th>
														<th data-align="center" data-column-id="receiver" data-width="5%">接收人</th>
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
			</div>
		</div>
	</div>
	<%@include file="../common/common-back2top.jsp" %>
</div>
<script src="/js/app/ecbudget/ecbudget.js"></script>