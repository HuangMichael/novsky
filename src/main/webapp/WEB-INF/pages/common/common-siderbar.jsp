<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="sidebar" class="sidebar">
    <div class="sidebar-menu nav-collapse">
        <ul id="menuL1">
            <li class="has-sub">
                <a>
                    <i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">统计分析</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li><a href="/portal/index"><span class="sub-menu-text">我的门户</span></a></li>
                </ul>
            </li>
            <li class="has-sub">
                <a>
                    <i class="fa fa-group fa-fw"></i> <span class="menu-text">用户管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li><a data-url="/user/list"><span class="sub-menu-text">用户信息</span></a></li>
                    <li><a data-url="/person/list"><span class="sub-menu-text">人员信息</span></a></li>
                    <li><a data-url="/role/list"><span class="sub-menu-text">角色信息</span></a></li>
                    <li><a data-url="/authority/list"><span class="sub-menu-text">应用授权</span></a>
                    </li>
                </ul>
            </li>
            <li class="has-sub">
                <a>
                    <i class="fa fa-file-text fa-fw"></i> <span class="menu-text">设备管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li><a data-url="/location/list"><span class="sub-menu-text">位置信息</span></a>
                    </li>
                    <li><a data-url="/equipment/list"><span class="sub-menu-text">设备信息</span></a></li>

                </ul>
            </li>

            <li class="has-sub">
                <a>
                    <i class="fa fa-table fa-fw"></i> <span class="menu-text">维修管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li><a data-url="/workOrderReportCart/list"><span class="sub-menu-text">报修车信息</span></a></li>
                    <li><a data-url="/workOrderReport/list"><span class="sub-menu-text">报修单查询</span></a></li>
                    <li><a data-url="/workOrderDispatch/list"><span class="sub-menu-text">调度台信息</span></a></li>
                    <li><a data-url="/workOrderFix/list"><span class="sub-menu-text">维修单查询</span></a></li>
                </ul>
            </li>
            <li class="has-sub">
                <a>
                    <i class="fa fa-briefcase fa-fw"></i> <span class="menu-text">系统管理</span><span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li><a data-url="/equipmentsClassification/list"><span class="sub-menu-text">设备分类管理</span></a></li>
                    <li><a data-url="/outsourcingUnit/list"><span class="sub-menu-text">外委单位管理</span></a></li>
                    <li><a data-url="/line/list"><span class="sub-menu-text">线路管理</span></a></li>
                    <li><a data-url="/station/list"><span class="sub-menu-text">车站管理</span></a></li>
                </ul>
            </li>
        </ul>
        <!-- /SIDEBAR MENU -->
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {

        var modules = [];
        var url = "/findResources/1";
        $.getJSON(url, function (data) {
            modules = data;
        });

        var appVue = new Vue({});


        //

        console.log("请求加载菜单----------------");

        //


        $(".sub-menu-text").parent().on("click", function () {
            $(this).css("cursor", "hand");
            var url = $(this).data("url");
            if (url) {
                $("#main-content").load(url, function () {
                    $(this).removeData("url");
                });
            }
        });
    })


</script>