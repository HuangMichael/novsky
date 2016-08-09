<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="form.jsp" %>


<div class="box border blue">
    <div class="box-title">
        <h4><i class="fa fa-table"></i>资源信息</h4>
    </div>
    <div class="box-body">

        <div id="contentDiv">
            <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                   class="datatable table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="15%">资源名</th>
                    <th width="15%">资源描述</th>
                    <th width="15%">资源路径</th>
                    <th width="5%">资源级别</th>
                    <th width="5%">使用状态</th>
                    <th width="5%">排序</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resourceList}" var="resource" varStatus="s">
                    <tr class="gradeX">
                        <td>${s.index+1}</td>
                        <td>
                                ${resource.resourceName}
                        </td>

                        <td>
                                ${resource.description}
                        </td>
                        <td>
                                ${resource.resourceUrl}
                        </td>
                        <td>
                                ${resource.resourceLevel}
                        </td>


                        <td class="center">
                            <c:if test="${resource.status=='1'}"> <span
                                class="badge badge-green">启用</span></td>
                        </c:if>
                        <c:if test="${resource.status!='1'}"> <span
                                class="badge badge-red">禁用</span></td></c:if>
                        </td>
                        <td class="center">${resource.sortNo}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        var resourceLevel = '${resource.resourceLevel}';
        $("#resourceLevel").val(resourceLevel);
    })
</script>