<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form">
    <div class="form-group">
        <div class="col-md-12">
            <div class="form-group">
                <label class="col-md-1 control-label"
                       for="resourceName">资源名称</label>

                <div class="col-md-5">
                    <input class="form-control" id="resourceName" type="text" name="resourceName"
                           value="${resource.resourceName}"/>
                </div>
                <label for="resourceUrl" class="col-md-1 control-label">资源路径</label>
                <div class="col-md-5">
                    <input class="form-control" id="resourceUrl" type="text" name="resourceUrl" value="${resource.resourceUrl}"/>
                    <input class="form-control" id="pid" type="hidden" name="pid" value="${resource.parent.id}"/>
                    <input class="form-control" id="lid" type="hidden" name="lid" value="${resource.id}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-1 control-label"
                       for="description">资源描述</label>

                <div class="col-md-5">
                    <input class="form-control" id="description" type="text" name="description"
                           value="${resource.description}"/>
                </div>
                <label for="resourceLevel" class="col-md-1 control-label">资源级别</label>
                <div class="col-md-5">
                    <select class="form-control" id="resourceLevel" name="resourceLevel">
                        <option value="">--请选择资源级别--</option>
                        <option value="1">系统</option>
                        <option value="2">模块</option>
                        <option value="3">应用</option>
                        <option value="4">菜单</option>
                    </select>

                </div>
            </div>
            <div class="form-group">
                <label class="col-md-1 control-label"
                       for="sortNo">排序</label>

                <div class="col-md-5">
                    <input class="form-control" id="sortNo" type="number" name="sortNo"
                           value="${resource.sortNo}"/>
                </div>
                <label for="status" class="col-md-1 control-label">是否启用</label>

                <div class="col-md-5">
                    <form:select path="resource.status" itemValue="${resource.status}" cssClass="form-control"
                                 id="status">
                        <form:option value="1">是</form:option>
                        <form:option value="0">否</form:option>
                    </form:select>
                </div>
            </div>
        </div>
    </div>
</form>


