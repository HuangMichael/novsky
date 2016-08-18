<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="detailForm">

    <div class="form-group">
        <label class="col-md-2 col-sm-2 col-lg-2" for="userName">用户名</label>

        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="userName" name="userName" v-model="user.userName" readonly>
        </div>
        <div class="col-md-2 col-sm-2 col-lg-2">
            <label for="person.id">人员</label>
        </div>
        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="person.id" name="person.id" v-model="user.person.id">
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-2 col-sm-2 col-lg-2">
            <label for="location">位置编号</label>
        </div>
        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="location" name="location" v-model="user.location">
        </div>

        <div class="col-md-2 col-sm-2 col-lg-2">
            <label for="status">用户状态</label>
        </div>
        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="status" name="status" v-model="user.status">
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" id="saveBtn" name="saveBtn" class="btn btn-primary btn-danger">保存记录
        </button>
    </div>
</form>

