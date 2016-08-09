<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form id="userForm">
    <div class="form-group">
        <label for="userName">用户名</label>
        <input type="text" class="form-control" id="userName" name="userName" value="${user.useName}">
    </div>
    <div class="form-group">
        <label for="sortNo">排序</label>
        <input type="text" class="form-control" id="sortNo" name="sortNo" value="${user.useName}">
    </div>

    <div class="form-group">
        <label for="location">排序</label>
        <input type="text" class="form-control" id="location" name="sortNo" value="${user.useName}">
       <%-- <form:select path="locationsList" class="form-control" id="location" name="location"  itemValue="${user.location}">
            <form:options itemLabel="description" items="${locationsList}" itemValue="location"></form:options>
        </form:select>--%>
    </div>
    <div class="form-group">
        <label for="status">状态</label>
        <select class="form-control" id="status" name="status">
            <option value="1" selected>启用</option>
            <option value="0">禁用</option>
        </select>
    </div>
</form>
