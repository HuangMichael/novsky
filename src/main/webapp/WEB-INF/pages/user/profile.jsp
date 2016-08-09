<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form class="form-horizontal" role="form" id="form">
    <div class="form-group">
        <div class="col-md-12">
            <div class="col-md-4">
                <img src="/img/avatars/avatar3.jpg" width="80px" height="80px"
                     class="img-rounded img-responsive img-thumbnail"/>
            </div>
            <div class="col-md-8">
                <div class="form-group">
                    <label class="col-md-2 control-label">用户</label>

                    <div class="col-md-10">
                        <input type="text" class=" form-control" value="${user.userName}" readonly/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">人员</label>

                    <div class="col-md-10">
                        <input type="text" class="form-control" value="${user.person.personName}" readonly/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
