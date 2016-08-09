<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="unitDetailForm">
    <div class="form-group">
        <div class="col-md-12">
            <div class="form-group">
                <label for="unitNo" class="col-md-1 control-label">单位编号</label>

                <div class="col-md-3">
                    <input class="form-control" name="unitNo" id="unitNo" v-model="unit.unitNo" required  lazy @change="checkUnitNo()"/>
                    <input class="form-control" type="hidden" name="id" id="id" v-model="unit.id"/>
                </div>

                <label for="description" class="col-md-1 control-label">单位名称</label>

                <div class="col-md-3">
                    <input class="form-control" id="description" name="description" v-model="unit.description"
                           required/>
                </div>
                <label for="linkman" class="col-md-1 control-label">联系人</label>

                <div class="col-md-3">
                    <input class="form-control" name="linkman" id="linkman" v-model="unit.linkman"/>
                </div>
            </div>
            <div class="form-group">
                <label for="telephone" class="col-md-1 control-label">联系电话</label>

                <div class="col-md-3">
                    <input class="form-control" id="telephone" name="telephone" v-model="unit.telephone"/>
                </div>
                <label for="status" class="col-md-1 control-label">状态</label>

                <div class="col-md-3">
                    <select class="form-control" id="status" name="status" style="width:100%" v-model="unit.status">
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" id="saveBtn" name="saveBtn" class="btn btn-primary btn-danger">保存记录
        </button>
    </div>
</form>