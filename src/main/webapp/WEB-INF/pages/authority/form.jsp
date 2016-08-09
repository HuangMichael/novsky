<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form">
    <div class="form-group">
        <div class="col-md-12">
            <div class="form-group">


                <input class="form-control" id="lid" type="hidden" name="id" value="${locations.id}" readonly/>


                <label class="col-md-2 control-label"
                       for="location">位置编号</label>

                <div class="col-md-4">
                    <input class="form-control" id="location" type="text" name="location"
                           value="${locations.location}" readonly/>
                </div>
                <label for="description" class="col-md-2 control-label">位置名称</label>

                <div class="col-md-4">
                    <input class="form-control" id="description" type="text"
                           name="description" value="${locations.description}"/>
                    <input type="hidden" name="locationsId" value="20">
                </div>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-12">
            <div class="form-group">
                <label class="col-md-2 control-label"
                       for="location">地铁线路</label>

                <div class="col-md-4">
                    <form:select path="lineList" class="form-control" id="line_id" onchange="changeLine()">
                        <form:options itemLabel="description" items="${lineList}" itemValue="id"></form:options>
                    </form:select>
                </div>
                <label for="description" class="col-md-2 control-label">地铁车站</label>

                <div class="col-md-4">
                   <%-- <form:select path="stationList" class="form-control" id="station_id">
                        <form:options itemLabel="description" items="${stationList}" itemValue="id"
                                      cssClass="badge badge-info"></form:options>
                    </form:select>--%>

                    <select name="station_id" id="station_id" class="form-control">
                        <c:forEach items="${stationList}" var="s">
                            <option value="${s.id}"><span class="badge badge-success">${s.description}</span></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</form>
