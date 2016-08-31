<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form">
	<div class="row">
		<div class="col-md-8 col-sm-8 col-lg-8">
			<div class="form-group">
				<label class="col-md-2 col-sm-2 col-lg-2 control-label" for="location">位置编号</label>
				<div class="col-md-4 col-sm-4 col-lg-4 ">
					<input class="form-control" id="location" type="text" name="location" value="${locations.location}" readonly/>
				</div>

				<label for="description" class="col-md-2 col-sm-2 col-lg-2 control-label">位置名称</label>
				<div class="col-md-4 col-sm-4 col-lg-4">
					<input class="form-control" id="description" type="text" name="description" value="${locations.description}" required/>
				</div>

			</div>

			<div class="form-group">
				<label class="col-md-2 col-sm-2 col-lg-2 control-label" for="location">线路名称</label>
				<div class="col-md-4 col-sm-4 col-lg-4 ">

					<select id="line_id" name="line.id" class="form-control">
						<c:forEach var="l" items="${lineList}">
							<c:if test="${locations.line.id==l.id}">
								<option value="${l.id}" selected="selected">${l.description}</option>
							</c:if>
							<c:if test="${locations.line.id!=l.id}">
								<option value="${l.id}">${l.description}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>

				<label for="description" class="col-md-2 col-sm-2 col-lg-2 control-label">车站名称</label>
				<div class="col-md-4 col-sm-4 col-lg-4">

					<select id="station_id" name="station.id" class="form-control">
						<c:forEach var="s" items="${stationList}">
							<c:if test="${locations.station.id==s.id}">
								<option value="${s.id}" selected="selected">${s.line.description}${s.description}</option>
							</c:if>
							<c:if test="${locations.station.id!=s.id}">
								<option value="${s.id}">${s.line.description}${s.description}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 col-sm-2 col-lg-2 control-label" for="superior">负责人员</label>
				<div class="col-md-4 col-sm-4 col-lg-4">
					<input class="form-control" id="superior" type="text" name="superior" value="${locations.superior}" />
					<input class="form-control" id="lid" type="hidden" name="id" value="${locations.id}" readonly/>
					<input class="form-control" id="parent_id" type="hidden" name="parent" value="${locations.parent}" />
					<input class="form-control" id="status" type="hidden" name="status" value="1" />
				</div>
			</div>
		</div>
	</div>
</form>

<script>
	$(function() {
		$("select").select2({
			theme: "bootstrap"
		});
	});
</script>