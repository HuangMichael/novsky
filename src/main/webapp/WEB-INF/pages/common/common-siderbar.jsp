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
        var modules = getAllModules(1);
        var html = '';
        var moduleId = null;
        var apps = [];
        for (var x in modules) {
            if (modules[x]["resourceDesc"]) {
                html += '<li class="has-sub">';
                html += '   <a><i class="' + modules[x]["iconClass"] + '"></i> <span class="menu-text">' + modules[x]["resourceDesc"] + '</span><span class="arrow"></span></a>';
                html += '     <ul class="sub" id="sub' + moduleId + '">';
                moduleId = modules[x]["id"];
                apps = getAppByModule(moduleId);
                for (var i in apps) {
                    if (apps[i]["resourceDesc"]) {
                        html += '       <li><a ' + apps[i]["resourceUrl"] + '><span class="sub-menu-text">' + apps[i]["resourceDesc"] + '</span></a></li>';
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
    function getAllModules(roleId) {
        var modules = [];
        $.ajaxSettings.async = false;
        var url = "resource/findResources/" + roleId;
        $.getJSON(url, function (data) {
            console.log("查询出的模块----" + JSON.stringify(data));
            modules = data;
        });
        return modules;
    }

    /**
     *
     * @returns {Array} 查询所有的一级模块
     */
    function getAppByModule(moduleId) {
        var modules = [];
        $.ajaxSettings.async = false;
        var url = "resource/findResources/1/" + moduleId;
        $.getJSON(url, function (data) {
            console.log("查询出的APP----" + JSON.stringify(data));
            modules = data;
        });
        return modules;
    }
</script>