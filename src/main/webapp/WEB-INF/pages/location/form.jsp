<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form">
    <div class="row">
        <div class="col-md-8">
            <div class="form-group">
                <input class="form-control" id="lid" type="hidden" name="id" value="${locations.id}" readonly/>
                <label class="col-md-2 control-label" for="location">位置编号</label>

                <div class="col-md-4">
                    <input class="form-control" id="location" type="text" name="location"
                           value="${locations.location}" readonly/>
                </div>
                <label for="description" class="col-md-2 control-label">位置名称</label>

                <div class="col-md-4">
                    <input class="form-control" id="description" type="text"
                           name="description" value="${locations.description}" required/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label" for="superior">负责人员</label>

                <div class="col-md-4">
                    <input class="form-control" id="superior" type="text" name="superior"
                           value="${locations.superior}"/>

                    <input class="form-control" id="localLevel" type="hidden" name="localLevel"
                           value="${locations.locLevel}"/>

                    <input class="form-control" id="parent_id" type="hidden" name="parent"
                           value="${locations.parent}"/>
                    <input class="form-control" id="status" type="hidden" name="status"
                           value="1"/>
                </div>
            </div>
        </div>
    </div>
</form>