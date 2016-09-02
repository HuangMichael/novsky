<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="equipmentsDataTable" class=" table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="index" data-width="3%">序号</th>
        <th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false" data-width="10%">ID</th>
        <th data-column-id="eqCode" data-width="5%" >设备编号</th>
        <th data-column-id="description"  data-width="25%">设备名称</th>
        <th data-column-id="equipClass" data-width="10%">设备分类</th>
        <th data-column-id="location" data-width="10%">设备位置</th>
        <th data-column-id="status" data-formatter="status" data-sortable="false" data-width="3%">设备状态</th>
        <th data-column-id="running" data-formatter="running" data-sortable="false" data-width="3%" >运行状态</th>
        <th data-column-id="report" data-formatter="report" data-sortable="false" data-width="3%" >报修</th>
    </tr>
    </thead>
    <tbody>
    <tr class="gradeX" data-rowId="{{eq.id}}" id="tr{{eq.id}}" v-for="eq in eqs">
        <td >{{ $index+1 }}</td>
        <td  >{{eq.id}}</td>
        <td  >{{eq.eqCode}}</td>
        <td  >{{eq.eqName}}</td>
        <td  >{{eq.eqClass}}</td>
        <td  ><a title="{{eq.locName}}">{{eq.locName}}</a></td>
        <td >
            {{eq.status}}
        </td>
        <td  >
            {{eq.running}}
        </td>
    </tr>
    </tbody>
    <tfoot>
    </tfoot>
</table>