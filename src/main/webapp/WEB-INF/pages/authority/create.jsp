<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="form.jsp" %>


<script type="text/javascript">
    $(function () {
        var lid = '${parent.id}';
        var url = "/locations/findById/" + lid;
        $.getJSON(url, function (data) {
            $("#line_id").val(data.line.id);
            $("#station_id").val(data.station.id);
        })
    })
</script>