<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="detailForm">
	<div class="form-group">
		<label class="col-md-1 col-sm-1 col-lg-1" for="lineNo">线路编号</label>
		<div class="col-md-3 col-sm-3 col-lg-3">
			<input type="text" class="form-control" id="lineNo" name="lineNo" v-model="line.lineNo">
			<input type="hidden" class="form-control" id="lineId" name="lineId" v-model="line.id">
		</div>
		<div class="col-md-1 col-sm-1 col-lg-1">
			<label for="roleDesc">线路描述</label>
		</div>
		<div class="col-md-3 col-sm-3 col-lg-3">
			<input type="text" class="form-control" id="description" name="description" v-model="line.description">
		</div>
		<label class="col-md-1 control-label" for="running">类型</label>
		<div class="col-md-3">
			<select class="form-control" id="type" name="type" required v-model="line.type" style="width:100%" required>
				<template v-for="option in type ">
					<option :value="option.key" v-if="option.key == line.type" selected>
						{{ option.value }}
					</option>
					<option :value="option.key" v-else>
						{{ option.value }}
					</option>
				</template>
			</select>
		</div>
	</div>

</form>