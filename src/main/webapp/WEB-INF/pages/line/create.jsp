<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal myform" role="form" id="createForm">
    <div class="form-group">
        <label class="col-md-2 col-sm-2 col-lg-2" for="lineNo">线路编号</label>
        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="lineNo" name="lineNo" v-model="line.lineNo">
            <input type="hidden" class="form-control" id="lineId" name="lineId" v-model="line.id">
        </div>
        <div class="col-md-2 col-sm-2 col-lg-2">
            <label for="roleDesc">线路描述</label>
        </div>
        <div class="col-md-4 col-sm-4 col-lg-4">
            <input type="text" class="form-control" id="description" name="description" v-model="line.description">
        </div>
    </div>
</form>

