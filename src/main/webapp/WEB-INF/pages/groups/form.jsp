<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="groupForm" method="post">
    <div class="form-group">
        <div class="col-md-12">
            <div class="form-group">
                <label for="groupName" class="col-md-2 control-label">用户组名称</label>

                <div class="col-md-10">
                    <input class="form-control" name="groupName" id="groupName" value="${group.groupName}"/>
                    <input class="form-control" name="id" id="id" value="${group.id}" type="hidden"/>
                </div>
            </div>
            <div class="form-group">
                <label for="description" class="col-md-2 control-label">用户组描述</label>

                <div class="col-md-10">
                    <input class="form-control" id="description" name="description" value="${group.description}"/>
                </div>
            </div>


            <div class="form-group">
                <label for="description" class="col-md-2 control-label">是否启用</label>

                <div class="col-md-10">
                    <select class="form-control" id="status" name="status">
                        <option value="1" selected>是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn" onclick="save()">保存</button>
            </div>
        </div>
    </div>
</form>



