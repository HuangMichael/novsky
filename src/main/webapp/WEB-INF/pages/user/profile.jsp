<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <form class="form-horizontal" role="form" id="form">
   <div class="form-group">
    <div class="col-md-12">
     <div class="col-md-4">
      <img src="/img/avatars/avatar3.jpg" width="160px" height="160px" class="img-rounded img-responsive img-thumbnail" />
     </div>
     <div class="col-md-8">
      <div class="form-group">
       <label class="col-md-3 control-label">用户名</label>
       <div class="col-md-9">
        <input type="text" class=" form-control" value="${user.userName}" readonly="readonly" />
       </div>
      </div>
      <div class="form-group">
       <label class="col-md-3 control-label">原密码</label>
       <div class="col-md-9">
        <input type="password" class=" form-control" />
       </div>
      </div>
      <div class="form-group">
       <label class="col-md-3 control-label">新密码</label>
       <div class="col-md-9">
        <input type="password" class=" form-control" />
       </div>
      </div>
      <div class="form-group">
       <label class="col-md-3 control-label">确认密码</label>
       <div class="col-md-9">
        <input type="password" class=" form-control" />
       </div>
      </div>
     </div>
    </div>
   </div>
  </form>