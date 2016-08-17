<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="unitsDataTable" class=" table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="index" style="width:5%">序号</th>
        <th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false">ID</th>
        <th data-column-id="unitNo" width="10%">单位编号</th>
        <th data-column-id="description" width="20%">单位名称</th>
        <th data-column-id="linkman" width="10%">联系人</th>
        <th data-column-id="telephone" width="10%">电话</th>
        <th data-column-id="workDays" width="10%">工作制</th>
        <th width="5%" data-column-id="status" width="10%">使用状态</th>
    </tr>
    </thead>
    <tbody>
    <tr class="gradeX" v-for="unit in units">
        <td width="5%">{{$index+1}}</td>
        <td>
            {{unit.id}}
        </td>
        <td width="10%">
            {{unit.unitNo}}
        </td>
        <td width="20%">
            {{unit.description}}
        </td>
        <td width="10%">
            {{unit.linkman}}
        </td>
        <td width="10%">
            {{unit.telephone}}
        </td>
        <td width="10%">
            {{unit.workDays}}
        </td>
        <td width="10%">

            <div v-if="unit.status=='0'">
                禁用
            </div>
            <div v-if="unit.status=='1'">
                启用
            </div>
        </td>
    </tr>
    </tbody>
</table>