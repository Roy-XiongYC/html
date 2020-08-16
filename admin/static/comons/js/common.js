var app = {
	description : 'App Common JS Library',
	version : '0.0.1'
};

app.webroot = function() {

	if (webroot == null) {
		return "";
	}
	return webroot;

};

// 格式化日期：年-月-日
app.inputDate = {
	myformatter : function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	},

	myparser : function(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	},
	myformatterTime : function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var h = date.getHours();
		var i = date.getMinutes();
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
				+ ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
	}
};

app.addButton = {
	update : function(method,url,id,width,height){
		return "<a title=\"编辑\" href=\"javascript:;\" onclick=\""+method+"('编辑','"+url+"','"+ id +"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>";
	},
	changePassword : function(method,id,width,height){
		return "<a title=\"重置密码\" href=\"javascript:;\" onclick=\""+method+"('重置密码','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe63f;</i></a>";
	},
	del : function(method,url,id){
		return "<a title=\"删除\" href=\"javascript:;\" onclick=\""+method+"(this,'"+url+"','"+id+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
	},
	stop : function(method,id){
		return "<a style=\"text-decoration:none\" onClick=\""+method+"('"+id+"')\" href=\"javascript:;\" title=\"停用\"><i class=\"Hui-iconfont\">&#xe631;</i></a>";
	},
	start : function(method,id){
		return "<a style=\"text-decoration:none\" onClick=\""+method+"('"+id+"')\" href=\"javascript:;\" title=\"启用\"><i class=\"Hui-iconfont\">&#xe615;</i></a>";
	},
	group : function(method,id,width,height){
		return "<a title=\"修改用户组\" href=\"javascript:;\" onclick=\""+method+"('修改用户组','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6bd;</i></a>";
	},
	resource : function(method,id,width,height){
		return "<a title=\"配置权限\" href=\"javascript:;\" onclick=\""+method+"('配置权限','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe61d;</i></a>";
	},
	updateStatus : function(title,icon,method,url,id,status){
		return "<a style=\"text-decoration:none\" href=\"javascript:;\" onClick=\""+method+"('"+title+"','"+url+"','"+id+"','"+status+"')\" href=\"javascript:;\" title=\"更新状态\"><i class=\"Hui-iconfont\">"+icon +"</i></a>";
	}
};

/* closeParentLayer 需传入index  父页面需包含doQuery方法 */
app.messager = {
		success : function(msg){
			layer.msg(msg,{icon: 6,time:2000});
		},
		error : function(msg){
			layer.msg(msg,{icon: 5,time:2000});
		},
		closeParentLayer : function(msg){
			layer.msg(msg,{icon: 6,time:2000});
			setTimeout(function(){
				layer_close();
			},2000); 
			if( typeof parent.doQuery === 'function' ){
			    //存在且是function
				parent.doQuery();
			}
		}
}

var fnRowCallback;
var aoColumns;
var sAjaxSource;
var Datatables;
var params;
Datatables = $("#table1").dataTable({
	//	"aoColumnDefs": [ 
	//		{ "bVisible": false, "aTargets": [  ] }
	//	], 
	"bStateSave": false,//缓存数据
	"bLengthChange" : false,
	"bFilter" : false,
	'bPaginate': true,
	"bInfo" : true, //是否显示页脚信息，DataTables插件左下角显示记录数
	"iDisplayLength" : 10,// 每页显示行数
	"bProcessing": true, //加载数据时显示正在加载信息   
	"bWidth" : false,
	"bServerSide": true,  //指定从服务器端获取数据 
	"sAjaxSource" : sAjaxSource,
	"aoColumns" :aoColumns,
	"fnRowCallback": fnRowCallback,
	"fnServerData" : function(sSource, aoData, fnCallback) {
		debugger;
		params = $('#queryform').serializeJSON();
		params.pageNum = aoData[3].value/10 +1, // iDisplayStart,  翻页第几条开始
		params.pageSize = aoData[4].value, // iDisplayLength  翻页每页展示数据
		$.ajax({
			"type" : 'GET',
			"url" : sSource,
			"dataType" : "json",
			"data" : params,
			"success" : function(resp) {
				var data = {};
				data.sEcho = aoData[0].value;
				data.aaData = resp.data.list;
				data.iTotalRecords = resp.data.total;
				data.iTotalDisplayRecords = resp.data.total;
				fnCallback(data);
			}
		});
	}
});	

function doQuery(){	
	Datatables.api().ajax.reload();
}

/*弹出层*/
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function edit(title,url,id,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: title,
		content: url +"?id=" + id, 
		success: function (layero, index) {
				console.log(index);
                    // 获取子页面的iframe
                var iframe = window['layui-layer-iframe' + index];
                // 向子页面的全局函数child传参
                //iframe.child(1);
				},
		end: function(){
			//刷新父页面
			//console.log(1111);
			//app.messager.closeParentLayer();
			//app.messager.closeParentLayer();
			//app.messager.closeParentLayer();
			parent.doQuery()
			},	
	});
}	

function del(obj,url,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'DELETE',
			url: url + id,
			dataType: 'json',
			success: function(data){
				console.log(data);
				if(data.code == '200'){
					doQuery();
					app.messager.success(data.message);
					//layer.msg('已删除!',{icon:1,time:1000});
				}
				
				//$(obj).parents("tr").remove();
				
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}

function updateStatus(title,url,id,status){
	layer.confirm(title,function(index){
		$.ajax({
			type: 'GET',
			url: url + id+'/'+status,
			dataType: 'json',
			success: function(data){
				console.log(data);
				if(data.code == '200'){
					doQuery();
					app.messager.success(data.message);
					//layer.msg('已删除!',{icon:1,time:1000});
				}
				
				//$(obj).parents("tr").remove();
				
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}

function save(url,params){
	//debugger;
	$.ajax({
		type:'POST',
		url: url,
		contentType:'application/json;charset=utf-8',  
		data:params,
		success: function(data,index){
			console.log(data);
			if(data.code == '200'){
				app.messager.closeParentLayer(data.message);
			}else{
				app.messager.error(data.message);
			}			
		},
		error:function(data) {
			console.log(data.msg);
		},
	});
}


