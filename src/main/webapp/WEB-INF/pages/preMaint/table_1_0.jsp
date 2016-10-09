<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="pmDataTable" class=" table table-striped table-bordered table-hover" data-toggle="bootgrid"
       data-ajax="true" data-url="/preMaint/data">
    <thead>
    <tr>
        <th data-column-id="id" data-width="3%">序号</th>
        <th data-column-id="id" data-type="numeric" data-identifier="true" data-visible="false" data-width="10%">ID</th>
        <th data-column-id="pmCode" data-width="5%">编号</th>
        <th data-column-id="pmDesc" data-width="15%">名称</th>
        <th data-column-id="locName" data-width="10%">设备位置</th>
        <th data-column-id="eqName" data-width="10%">设备名称</th>
        <th data-column-id="eqClass" data-width="10%">设备分类</th>
        <th data-column-id="frequency" data-width="10%">频率</th>
        <th data-column-id="unit" data-width="10%">单位</th>
        <th data-column-id="outUnit" data-width="10%">维修单位</th>
        <th data-column-id="status" data-width="10%">状态</th>
    </tr>
    </thead>
</table>