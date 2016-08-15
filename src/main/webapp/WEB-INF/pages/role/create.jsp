<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="v-bind" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>


<div class="tab-pane fade in active" id="tab_1_1">
    <div class="divide-10"></div>
    <div class="box-body" id="detailContainer">


        <form class="form-horizontal myform" role="form" id="createForm">
            <fieldset class="form-group" id="a">
                <legend>基本信息</legend>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="location">角色名称</label>
                    <div class="col-md-5">
                        <input class="form-control" id="location" type="text" name="location"/>
                    </div>
                    <label for="roleDesc" class="col-md-1 control-label">角色描述</label>

                    <div class="col-md-5">
                        <input class="form-control" id="roleDesc" type="text" name="roleDesc"/>
                        <input type="hidden" name="locationsId" value="20">
                    </div>
                </div>
                <div class="form-group">
                    <label for="status" class="col-md-1 control-label">是否启用</label>
                    <div class="col-md-5">
                        <select id="status" class="form-control">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>
            </fieldset>
            <div class="modal-footer">
                <button type="submit" id="saveBtn" name="saveBtn" class="btn btn-primary btn-danger">保存记录
                </button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        $('select').select2({theme: "bootstrap"});



       /* var cm = new Vue({
            el: "#createForm",
            data: {
                formLocked: formLocked,
                locs: locs,
                eqClasses: eqClasses,
                eqStatuses: eqStatuses,
                runStatus: runStatus,
                equipments: {
                    status: '1',
                    running: '1'
                }
            },
            methods: {
                checkEqCode: function () {
                    var eqCode = $("#eqCode").val();
                    if (checkEqCode(eqCode)) {
                        showMessageBoxCenter("danger", "center", "设备编号不能重复");
                        $("#eqCode").focus();
                        return;
                    }
                }
            }
        });

        $("#running").attr("readonly", "readonly");
        $('#createForm')
                .bootstrapValidator(validateOptions).on('success.form.bv', function (e) {
            e.preventDefault();
            createEquipment();
        });*/
    });

</script>
