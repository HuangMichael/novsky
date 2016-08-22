     <div class="box-body" style="padding: 5px 20px 5px 5px">
                            <div class="btn-group">
                            <c:forEach var="m" items="${appMenus}">
                                <button type="button" class="btn btn-sm myNavBtn active"
                                        onclick="loadNew()">
                                    <i class="glyphicon glyphicon-plus"></i>${m.resourceName}
                                </button>
                             </c:forEach>
                            </div>
                        </div>