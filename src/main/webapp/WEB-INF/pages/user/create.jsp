<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="detailForm">

    <div class="form-group">
        <label class="col-md-1" for="userName">用户名</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="userName" name="userName" v-model="user.useName">
        </div>
        <div class="col-md-1">
            <label for="person.id">人员</label>
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="person.id" name="person.id" v-model="user.person.id">
        </div>
        <div class="col-md-1">
            <label for="location">位置编号</label>
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="location" name="location" v-model="user.location">
        </div>
    </div>
</form>

