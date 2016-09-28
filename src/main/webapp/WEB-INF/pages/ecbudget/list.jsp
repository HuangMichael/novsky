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
										<a href="#list_tab" data-toggle="tab" style="font-family: 微软雅黑;font-weight: bold">
											<i class="fa fa-home" id="list"></i>低值易耗品列表信息</a>
									</li>
									<li>
										<a href="#detail_tab" data-toggle="tab" style="font-family: 微软雅黑;font-weight: bold">
											<i class="fa fa-home" id="detail"></i>低值易耗品明细信息</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane fade in active" id="list_tab" style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
										<div class="container-fluid">
											<table id="budgetDataTable" class=" table table-striped table-bordered table-hover" data-toggle="bootgrid" data-ajax="true" data-url="/ecbudget/data">
												<thead>
													<tr>
														<th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false">ID</th>
														<th data-align="center" data-column-id="applyDate" data-width="4%">采购日期</th>
														<th data-align="center" data-column-id="applicant" data-width="3%">申请人</th>
														<th data-align="center" data-column-id="ecname" data-width="6%">低值易耗品名称</th>
														<th data-align="center" data-column-id="amount" data-width="4%">申请数量</th>
														<th data-align="center" data-column-id="locName" data-width="6%">位置</th>
														<th data-align="center" data-column-id="epermited" data-width="5%">有无用电许可证</th>
														<th data-align="center" data-column-id="updateReason" data-width="5%">申请更新原因</th>
														<th data-align="center" data-column-id="auditDate" data-width="4%">填报日期</th>
														<th data-align="center" data-column-id="auditor" data-width="3%">填报人</th>
														<th data-align="center" data-column-id="confirmReason" data-width="5%">确认更新原因</th>
														<th data-align="center" data-column-id="fixAdvice" data-width="10%">综合维修中心意见</th>
														<th data-align="center" data-column-id="leaderAdvice" data-width="10%">领导批示</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
									<div class="tab-pane fade" id="detail_tab" style="color: #111;background-color: #fff;border-color: #d26911 #e5e5e5 ">
										<%@include file="detail.jsp"%>
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