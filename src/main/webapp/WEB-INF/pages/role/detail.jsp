<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="detailForm">
	<div class="form-group">
		<label class="col-md-2 col-sm-2 col-lg-2" for="roleName">角色名称</label>
		<div class="col-md-3 col-sm-3 col-lg-3">
			<input type="text" class="form-control" id="roleName" name="roleName" v-model="role.roleName">
			<input type="hidden" class="form-control" id="roleId" name="roleId" v-model="role.id">
		</div>
		<div class="col-md-2 col-sm-2 col-lg-2">
			<label for="roleDesc">角色描述</label>
		</div>
		<div class="col-md-3 col-sm-3 col-lg-3">
			<input type="text" class="form-control" id="roleDesc" name="roleDesc" v-model="role.roleDesc">
		</div>
		<div class="col-md-2 col-sm-2 col-lg-2">
			<button type="button" class="btn btn-sm myNavBtn active" onclick="addUsers()">
                 <i class="glyphicon glyphicon-plus"></i>添加用户
             </button>
		</div>
	</div>

</form>

<table id="myUsers" class="table table-striped table-bordered table-hover table-condensed">
	<thead>
		<tr>
			<th data-column-id="id">序号</th>
			<th data-column-id="userName">用户名</th>
			<th data-column-id="remove">移除用户</th>
		</tr>
	</thead>
	<tbody id="usersDiv">

	</tbody>
	<tfoot>
	</tfoot>
</table>