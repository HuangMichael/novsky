<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="sidebar" class="sidebar">
    <div class="sidebar-menu nav-collapse">
        <ul id="menuL1">
        </ul>
        <!-- /SIDEBAR MENU -->
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        //先查询出所有的模块
        var modules = getAllModules("admin");
        var html = '';
        var moduleId = null;
        var apps = [];
        for (var x in modules) {
            if (modules[x][2]) {
                html += '<li class="has-sub">';
                html += '   <a><i class="' + modules[x][3] + '"></i> <span class="menu-text">' + modules[x][2] + '</span><span class="arrow"></span></a>';
                html += '     <ul class="sub" id="sub' + moduleId + '">';
                moduleId = modules[x][0];
                console.log("moduleId---"+moduleId);
                apps = getAppByModule("admin", moduleId);
                for (var i in apps) {
                    if (apps[i][3]) {
                        html += '       <li><a ' + apps[i][3] + '><span class="sub-menu-text">' + apps[i][2] + '</span></a></li>';
                    }
                }
                html += '     </ul>';
                html += '</li>';
            }
        }
        $("#menuL1").append(html);
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


    /**
     *
     * @returns {Array} 查询所有的一级模块
     */
    function getAllModules(userName) {
        var modules = [];
        $.ajaxSettings.async = false;
        var url = "authority/loadAuthView/1/" + userName;
        console.log(url);
        $.getJSON(url, function (data) {
            modules = data;
        });
        return modules;
    }

    /**
     *
     * @returns {Array} 查询所有的一级模块
     */
    function getAppByModule(userName, moduleId) {
        var modules = [];
        $.ajaxSettings.async = false;
        var url = "authority/loadAuthView/2/" + moduleId + "/" + userName;
        console.log(url);
        $.getJSON(url, function (data) {
            modules = data;
        });
        return modules;
    }
</script>