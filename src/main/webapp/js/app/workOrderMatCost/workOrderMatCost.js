/**
 * Created by Administrator on 2016/9/29.
 */



var locs = [];
var lines = [];


var bootGridCfg = {
    searchSettings: {
        delay: 100,
        characters: 3
    }
};
$(function () {


    $("select").select2({
        theme: "bootstrap"
    });

    $("#budgetDataTable").bootgrid(bootGridCfg);


    $(".dropzone").dropzone({
        paramName: "file", // The name that will be used to transfer the file
        maxFilesize: 0.5, // MB
        addRemoveLinks: true,
        dictResponseError: '上传文件错误!',
        //change the previewTemplate to use Bootstrap progress bars
        previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-sm progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
        complete: function () {
            $("#import_modal").modal("hide");
            // refresh();
        }
    });


    $('#import_modal').on('hide.bs.modal', function () {
        // 执行一些动作...

        console.log("刷新数据--------------");
    })

});


function refresh() {

    $("#main-content").load("workOrderMatCost/list", function () {
        showMessageBox("info", "数据已上传成功!");
    });
}
/**
 * 导入excel
 */
function importExcel() {
    $("#import_modal").modal("show");
}


/**
 * 导入excel
 */
function importExcelData() {

    var filePath = $("#theFilePath").val();
    console.log("filePath----------" + JSON.stringify(filePath));
    if (!file) {
        showMessageBox("danger", "请选择excel文件");
        return;
    }
    var url = "/workOrderMatCost/importExcel";
    $.post(url, {filePath: filePath}, function (data) {
        if (data.result) {
            showMessageBox("info", data['resultDesc']);
        } else {
            showMessageBox("danger", data['resultDesc']);
        }
    })
}