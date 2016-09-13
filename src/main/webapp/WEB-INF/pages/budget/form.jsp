<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="v-bind" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal myform" role="form" id="detailForm">
	
	<fieldset class="form-group" id="a">
		<legend>申请人信息</legend>
		<div class="form-group">

			<label for="applicant " class="col-md-1 control-label ">申请人</label>
			<div class="col-md-3 ">
				<input class="form-control " id="applicant" type="text " name="applicant" required v-model="budgetBill.applicant" />
			</div>
			<label for="applyDep" class="col-md-1 control-label ">申请部门</label>
			<div class="col-md-3 ">
				<input class="form-control " id="applyDep" type="text " name="applyDep" required v-model="budgetBill.applyDep" />
			</div>

			<label for="applicant " class="col-md-1 control-label ">申请日期</label>
			<div class="col-md-3 ">
				 <input class="Wdate form-control" id="applyDate" onClick="WdatePicker({maxDate:'%y-%M-%d'})"
                       name="applyDate"
                       v-model="budgetBill.applyDate" style="height:34px;border:1px solid #cccccc"/>
			</div>
		</div>
	</fieldset>
	<fieldset class="form-group" id="a">
		<legend>配件信息</legend>
		<div class="form-group">
			<label class="col-md-1 control-label" for="accessoryName">配件名称</label>
			<div class="col-md-3">
				<input class="form-control" id="accessoryName" type="text" name="accessoryName" v-model="budgetBill.accessoryName" />
				<input class="form-control" id="id" type="hidden" name="id" v-model="budgetBill.id" />
			</div>
			<label class="col-md-1 control-label" for="accessoryName">规格型号</label>
			<div class="col-md-3">
				<input class="form-control" id="accessoryName" type="text" name="accessoryName" v-model="budgetBill.accessoryName" />

			</div>
			<label class="col-md-1 control-label" for="amount">数量</label>
			<div class="col-md-3">
				<input class="form-control" id="amount" type="number" name="amount" v-model="budgetBill.amount" value="1" />
			</div>
		</div>
	</fieldset>
	<fieldset class="form-group" id="a">
		<legend>用途信息</legend>
		<div class="form-group">
			<label class="col-md-1 control-label" for="purpose">用途</label>
			<div class="col-md-11">
				<textarea class="form-control" id="purpose" type="text" name="purpose" v-model="budgetBill.purpose" rows="6" />
			</div>
		</div>
		<div class="form-group">
			<label for="vlocations_id " class="col-md-1 control-label ">使用位置</label>
			<div class="col-md-3 ">
				<select v-model="budgetBill.locations.id " class="form-control " id="locations_id " name="locations.id " required style="width:100% " required>
					<template v-for="option in locs ">
						<option :value="option.id " v-if="option.id==budgetBill.locations.id " selected>
							{{option.locName }}
						</option>
						<option :value="option.id " v-else>
							{{option.locName }}
						</option>
					</template>
				</select>
			</div>
		
			<label for="vlocations_id " class="col-md-1 control-label ">使用设备分类</label>
			<div class="col-md-3 ">
				<select v-model="budgetBill.locations.id " class="form-control " id="locations_id " name="locations.id " required style="width:100% " required>
					<template v-for="option in locs ">
						<option :value="option.id " v-if="option.id==budgetBill.locations.id " selected>
							{{option.locName }}
						</option>
						<option :value="option.id " v-else>
							{{option.locName }}
						</option>
					</template>
				</select>
			</div>
			
			<label for="vlocations_id " class="col-md-1 control-label ">使用设备</label>
			<div class="col-md-3 ">
				<select v-model="budgetBill.locations.id " class="form-control " id="locations_id " name="locations.id " required style="width:100% " required>
					<template v-for="option in locs ">
						<option :value="option.id " v-if="option.id==budgetBill.locations.id " selected>
							{{option.locName }}
						</option>
						<option :value="option.id " v-else>
							{{option.locName }}
						</option>
					</template>
				</select>
			</div>
		</div>
	</fieldset>
	

	<fieldset class="form-group" id="a">
		<legend>批准信息</legend>
		<div class="form-group">
			<label class="col-md-1 control-label" for="approver">批准人</label>
			<div class="col-md-3">
				<input class="form-control" id="approver" type="text" name="approver" v-model="budgetBill.approver" />
			</div>
			<label for="handler " class="col-md-1 control-label ">经办人</label>
			<div class="col-md-3 ">
				<input class="form-control" id="handler" type="text" name="handler" v-model="budgetBill.handler" />
			</div>
			<label for="receiver" class="col-md-1 control-label ">接收人</label>
			<div class="col-md-3 ">
				<input class="form-control " id="receiver " type="text" name="receiver" required v-model="budgetBill.receiver " />
			</div>
		</div>
	</fieldset>

	<div class="modal-footer ">
		<button type="submit " id="saveBtn " name="saveBtn " class="btn btn-primary btn-danger ">保存记录
        </button>
	</div>
</form>