<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="form.jsp" %>
<div class="tabbable">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#tab_1_1" data-toggle="tab"><i
                class="fa fa-home"></i>数据过滤器信息</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane  in active" id="tab_1_1">
            <div class="divide-10"></div>
            <div class="box border blue">
                <div class="box-title">
                    <h4><i class="fa fa-table"></i>数据过滤器包含人员信息</h4>
                </div>
            </div>
            <div class="box-body">
                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab_2_0" data-toggle="tab"><i
                                class="fa fa-envelope"></i> 人员信息</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active " id="tab_2_0">
                            <table class="table table-striped table-bordered table-responsive table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>用户名</th>
                                    <th>使用状态</th>
                                    <th>排序</th>
                                    <%-- <th>编辑</th>
                                     <th>删除</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${role.userList}" var="user" varStatus="s">
                                    <tr class="gradeX">
                                        <td>${s.index+1}</td>
                                        <td>
                                                ${user.userName}
                                        </td>
                                        <td class="center">${user.status}</td>
                                        <td class="center">${user.sortNo}</td>
                                            <%--<td class="center"><a href="#">编辑</a></td>
                                            <td class="center"><a href="#">删除</a></td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>