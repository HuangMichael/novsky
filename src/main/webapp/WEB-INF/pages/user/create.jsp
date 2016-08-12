<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="createForm">
    <fieldset class="form-group" id="a">
        <div class="form-group">
            <label class="col-md-1" for="userName">用户名</label>

            <div class="col-md-3">
                <input type="text" class="form-control" id="userName" name="userName" v-model="{{user.useName}}">
            </div>
            <div class="col-md-1">
                <label for="person.id">人员</label>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" id="person.id" name="person.id" v-model="{{user.person.id}}">
            </div>
            <div class="col-md-1">
                <label for="sortNo">排序</label>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" id="sortNo" name="sortNo" v-model="{{user.useName}}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-1">
                <label for="location">位置编号</label>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" id="location" name="sortNo" v-model="{{user.useName}}">
            </div>
            <div class="col-md-1">
                <label for="status">状态</label>
            </div>
            <div class="col-md-3">
                <select class="form-control" id="status" name="status">
                    <option value="1" selected>启用</option>
                    <option value="0">禁用</option>
                </select>
            </div>
        </div>
    </fieldset>
</form>

