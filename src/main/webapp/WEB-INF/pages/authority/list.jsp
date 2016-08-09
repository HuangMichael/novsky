<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/js/zTree_v3-master/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/jquery-raty/jquery.raty.min.js"></script>
<div class="container">
    <div class="row">
        <div id="content" class="col-lg-12">
            <!-- PAGE HEADER-->
            <%@include file="../common/common-breadcrumb.jsp" %>
            <div class="row">
                <div class="col-md-12">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-title">
                            <h4><i class="fa fa-sitemap"></i>角色授权</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">

                <div class="col-md-4">
                    <!-- BOX -->
                    <div class="box">
                        <div class="box-body">
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>选择</th>
                                    <th>序号</th>
                                    <th>用户组名</th>
                                    <th>组描述</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${groupsList}" var="groups" varStatus="s">
                                    <tr class="gradeX">
                                        <td><input type="checkbox" name="selGroup" data-groupId="${groups.id}"/></td>
                                        <td>${s.index+1}</td>
                                        <td class="center">${groups.groupName}</td>
                                        <td class="center">${groups.description}</td>
                                        <td>
                                            <c:if test="${groups.status=='1'}"> <span
                                                class="badge badge-green">启用</span></td>
                                        </c:if>
                                        <c:if test="${groups.status!='1'}"> <span
                                                class="badge badge-red">禁用</span></td></c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <!-- BOX -->
                    <div class="box">
                        <div class="box-body">
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class=" table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>选择</th>
                                    <th>序号</th>
                                    <th>用户组名</th>
                                    <th>组描述</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${groupsList}" var="groups" varStatus="s">
                                    <tr class="gradeX">
                                        <td><input type="checkbox" name="selGroup" data-groupId="${groups.id}"/></td>
                                        <td>${s.index+1}</td>
                                        <td class="center">${groups.groupName}</td>
                                        <td class="center">${groups.description}</td>
                                        <td>
                                            <c:if test="${groups.status=='1'}"> <span
                                                class="badge badge-green">启用</span></td>
                                        </c:if>
                                        <c:if test="${groups.status!='1'}"> <span
                                                class="badge badge-red">禁用</span></td></c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <!-- BOX -->
                    <div class="box border blue">
                        <div class="box-body">
                            <ul id="tree" class="ztree" style="width:460px; overflow:auto;"></ul>
                        </div>
                    </div>
                </div>


            </div>
            <%@include file="../common/common-back2top.jsp" %>
        </div>
    </div>
    <!-- /CONTENT-->
</div>
<%--<div class="modal fade " id="wo_modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">设备报修工单信息</h4>
            </div>
            <div class="modal-body">
                <%@include file="../workOrder/form.jsp" %>
            </div>
        </div>
    </div>
</div>--%>
<script src="js/app/authority/authority.js"></script>
