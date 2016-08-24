<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form" method="post">
	<div class="form-group">
		<div class="col-md-12 col-sm-12 col-lg-12">
			<div class="form-group">
				<input class="form-control" id="resourceId" type="hidden" name="resourceId" value="${resource.id}" />
				<label class="col-md-1 col-sm-1 col-lg-1 control-label" for="classId">资源编号</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" id="parentId" type="hidden" name="parentId" value="${resource.parent.id}" />
					<input class="form-control" type="text" name="resourceCode" id="resourceCode" value="${resource.resourceCode}" />
				</div>
				<label for="description" class="col-md-1 control-label">资源名称</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" id="resourceName" type="text" name="resourceName" value="${resource.resourceName}" />
				</div>

				<label class="col-md-1 col-sm-1 col-lg-1 control-label" for="description">资源描述</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" type="text" name="description" id="description" value="${resource.description}" />
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-md-1 control-label">资源路径</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" id="resourceUrl" type="text" name="resourceUrl" value="${resource.resourceUrl}" />
				</div>
				<label class="col-md-1 col-sm-1 col-lg-1 control-label" for="appName">应用名称</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" type="text" name="appName" id="appName" value="${resource.appName}" />
				</div>
				<label for="iconClass" class="col-md-1 control-label">资源样式</label>
				<div class="col-md-3 col-sm-3 col-lg-3">
					<input class="form-control" id="iconClass" type="text" name="iconClass" value="${resource.iconClass}" />
				</div>
			</div>
		</div>
	</div>
	    <div class="modal-footer">
            <button type="submit" id="saveBtn" name="saveBtn" class="btn btn-primary btn-danger">保存记录
            </button>
        </div>
</form>