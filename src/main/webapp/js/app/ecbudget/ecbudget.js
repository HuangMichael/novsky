var bills = [];
var selectedIds = [];
//数据列表
var listTab = $('#myTab li:eq(0) a');
//数据列表
var formTab = $('#myTab li:eq(1) a');
var vdm = null;
//位置信息
var locs = [];
var eqClass = [];
$(function() {

	//设置数据有效性验证配置项
	var validateOptions = {
		message: '该值无效 ',
		fields: {

			"applyDate": {
				message: '申请日期无效',
				validators: {
					notEmpty: {
						message: '申请日期不能为空!'
					}

				}
			},
			"ecName": {
				message: '易耗品名称无效',
				validators: {
					notEmpty: {
						message: '易耗品名称不能为空!'
					}

				}
			},
			"epermited": {
				message: '有无用电许可证无效',
				validators: {
					notEmpty: {
						message: '有无用电许可证不能为空!'
					}

				}
			},
			"updateReason": {
				message: '更新原因无效',
				validators: {
					notEmpty: {
						message: '更新原因不能为空!'
					}

				}
			},
			"applicant": {
				message: '申请人无效',
				validators: {
					notEmpty: {
						message: '申请人不能为空!'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '1到20个字符'
					}
				}
			},
			"applyDep": {
				message: '申请部门无效',
				validators: {
					notEmpty: {
						message: '申请部门不能为空!'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '1到20个字符'
					}
				}
			},
			"amount": {
				message: '申请数量无效',
				validators: {
					notEmpty: {
						message: '申请数量不能为空!'
					}
				}
			},
			"accessoryName": {
				message: '配件名称无效',
				validators: {
					notEmpty: {
						message: '配件名称不能为空!'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '1到20个字符'
					}
				}
			},
			"specifications": {
				message: '规格型号无效',
				validators: {
					notEmpty: {
						message: '规格型号不能为空!'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '1到20个字符'
					}
				}
			},
			"purpose": {
				message: '用途无效',
				validators: {
					notEmpty: {
						message: '用途不能为空!'
					},
					stringLength: {
						min: 1,
						max: 50,
						message: '1到50个字符'
					}
				}
			},
			"locations": {
				message: '位置无效',
				validators: {
					notEmpty: {
						message: '位置不能为空!'
					}
				}
			},
			"eqclass": {
				message: '设备分类无效',
				validators: {
					notEmpty: {
						message: '设备分类不能为空!'
					}
				}
			},
			"approver": {
				message: '批准人无效',
				validators: {
					notEmpty: {
						message: '批准人不能为空!'
					},
					stringLength: {
						min: 1,
						max: 50,
						message: '1到50个字符'
					}
				}
			},
			"handler": {
				message: '经办人无效',
				validators: {
					notEmpty: {
						message: '经办人不能为空!'
					},
					stringLength: {
						min: 1,
						max: 50,
						message: '1到50个字符'
					}
				}
			},
			"receiver": {
				message: '接收人无效',
				validators: {
					notEmpty: {
						message: '接收人不能为空!'
					},
					stringLength: {
						min: 1,
						max: 50,
						message: '1到50个字符'
					}
				}
			},
		}
	};

	locs = findMyLoc();

	eqClass = findMyEqClass();

	vdm = new Vue({
		el: "#detailContainer",
		data: {
			budgetBill: null,
			locs: locs,
			eqClass: eqClass
		}
	});

	//初始化加载列表
	$("#budgetDataTable").bootgrid({
		selection: true,
		multiSelect: true,
		rowSelect: false,
		keepSelection: true
	}).on("selected.rs.jquery.bootgrid", function(e, rows) {
		//如果默认全部选中
		if(selectedIds.length === bills.length) {
			selectedIds.clear();
		}
		for(var x in rows) {
			if(rows[x]["id"]) {
				selectedIds.push(rows[x]["id"]);
			}
		}
	}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
		for(var x in rows) {
			selectedIds.remove(rows[x]["id"]);
		}
	});
	// 监听切换tab的方法

	$(formTab).on('click', function() {

		//首先判断是否有选中的
		var budgetBill = null;
		if(selectedIds.length > 0) {
			//切换tab时默认给detail中第一个数据
			//eq = getEquipmentByIdInEqs(selectedIds[0]);
			console.log("显示第一条-------------" + selectedIds[0])
			budgetBill = findById(selectedIds[0]);

			console.log("显示第一条" + JSON.stringify(budgetBill));
		} else {
			//没有选中的 默认显示整个列表的第一条
			//budgetBill = getEquipmentByIdInEqs(eqs[0]["id"])
			//所有的都在选中列表中
			/*selectedIds = setAllInSelectedList(eqs);*/
		}
		vdm.$set("budgetBill", budgetBill);
		vdm.$set("locs", locs);
		vdm.$set("eqClass", eqClass);

	});

	$("select").select2({
		theme: "bootstrap"
	});

	$('#detailForm')
		.bootstrapValidator(validateOptions).on('success.form.bv', function(e) {
			e.preventDefault();
			save();
		});
});

function add() {

	//重新建立模型 新建对象模型

	var newVue = new Vue({
		el: "#createContainer",
		budgetBill: null,
		locs: locs,
		eqClass: eqClass
	});

	formTab.tab('show');
	//alert("add一条记录");
}
/**
 *保存或者更新
 * */
function save() {

	alert(123123123);
	var objStr = getFormJsonData("detailForm");
	var budgetBill = JSON.parse(objStr);
	console.log(JSON.stringify(budgetBill));
	var url = "ecbudget/save";
	$.post(url, budgetBill, function(data) {
		if(data.result) {
			showMessageBox(data.resultDesc);
		}
	});
}

function edit() {
	alert("del一条记录");
}

function del() {
	alert("del一条记录");
}
/**
 *根据id查询
 * */
function findById(id) {
	var budgetBill = null;
	var url = "budget/findById/" + 1;
	$.getJSON(url, function(data) {
		budgetBill = data;
	});

	console.log(JSON.stringify(budgetBill));
	return budgetBill;

}
/**
 *查询我的位置
 * */
function findMyLoc() {
	var url_location = "/commonData/findMyLoc";
	$.getJSON(url_location, function(data) {
		locs = data;
	});
	return locs;
}

/**
 *查询我的位置
 * */
function findMyEqClass() {
	var url_eqclass = "/commonData/findVEqClass";
	$.getJSON(url_eqclass, function(data) {
		eqClass = data;
	});
	return eqClass;
}

/**
 *获取服务器时间
 * */
function getServerDate() {

	var today = null;
	var url = "/commonData/getServerDate";
	$.getJSON(url, function(data) {
		today = data;
	});
	return today;
}