<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="historyInfo">
    <div class="row">
        <div class="col-md-12">
            <!-- BOX -->
            <div class="box border blue">
                <div class="box-body" style="padding: 20px">
                    <form class="form-horizontal" role="form" id="historyForm">
                        <div class="form-group">
                            <label class="col-md-1 control-label" for="eqCode">设备编号</label>

                            <div class="col-md-3">
                                <input class="form-control" id="eqCode" type="text" name="eqCode" v-model="e.eqCode"
                                       readonly/>
                            </div>
                            <label class="col-md-1 control-label" for="eqCode">设备名称</label>

                            <div class="col-md-3">
                                <input class="form-control" id="desciption" type="text" name="desciption"
                                       v-model="e.description" readonly/>
                            </div>
                            <label class="col-md-1 control-label" for="eqCode">设备位置</label>

                            <div class="col-md-3">
                                <input class="form-control" id="location" type="text" name="location"
                                       v-model="e.locations.description" readonly/>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>


    <table id="reportHistory" class=" table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th width="5%">序号</th>
            <th width="10%">跟踪号</th>
            <th width="20%">报修描述</th>
            <th width="10%">操作时间</th>
          <%--  <th width="20%">故障处理情况</th>--%>
            <th width="5%"> 状态</th>
            <th width="5%">跟踪</th>
        </tr>
        </thead>
        <tbody id="reportHistory_list" style="height: 100px;overflow: scroll">
        <tr v-for="h in histories" id="tr{{$index+1}}">
            <td>{{$index+1}}</td>
            <td>{{h[0]}}</td>
            <td>{{h[1]}}</td>
            <td>{{h[2]}}</td>
           <%-- <td>{{h[3]}}</td>--%>
            <td>
                <div v-if="h[4]==0">
                    报修车
                </div>
                <div v-if="h[4]==1">
                    已报修
                </div>
                <div v-if="h[4]==2">
                    已分配
                </div>
                <div v-if="h[4]==3">
                    已暂停
                </div>
                <div v-if="h[4]==4">
                    已完工
                </div>
            </td>
            <td><a class="btn btn-xs" @click="showDetailHistory(h[0])"><i class="glyphicon glyphicon-eye-open"></i></a>
            </td>
        </tr>
        </tbody>
    </table>
</div>