<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/web/admin/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/admin/common/javascript-includes.jsp"%> 
<style type="text/css">
/* 	 含table的一定要添加此属性  */
	#table1 tbody td{
		text-align: center;
	}
</style>
<body>
	<%@ include file="/WEB-INF/views/web/admin/common/breadcrumb.jsp"%>
	<div class="page-container">
	<form id="form1">
		<div class="text-c">
			<input type="text" class="input-text" style="width:250px" placeholder="输入用户名称" id="userName" name="userName">
			<button type="button" onclick="doQuery();return false;" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
			<button type="reset" class="btn btn-primary"> 重置</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="admin_add('添加','${webroot}/admin/user/addPage','500','300')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span></div>
	<table id="table1" class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="9">列表</th>
			</tr>
			<tr class="text-c">
					<th name="userName">用户名</th>
					<th name="emailAddr">邮箱</th>
					<th name="mpNo">手机号</th>
					<th name="userStatusZh">状态</th>
					<th name="groupName">所属用户组</th>
					<th name="createTime">创建时间</th>
					<th name="userId">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
</body>
<script type="text/javascript">
//重新加载方法  Datatables.api().ajax.reload();
var Datatables;
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
"sAjaxSource" : '${webroot}/admin/userData',
"aoColumns" :
[{"mData" : "userName",'sClass' : 'center'},
{"mData" : "emailAddr",'sClass' : 'center'},
{"mData" : "mpNo",'sClass' : 'center'},
{"mData" : "userStatusZh",'sClass' : 'center'},
{"mData" : "groupName",'sClass' : 'center'},
{"mData" : "createTime",'sClass' : 'center'},
{"mData" : "userId",'sClass' : 'center'}],
"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	//操作行 3为减去隐藏td后的列
	/* Append the grade to the default row class name */
	$(' td:eq(5)',nRow).html(app.inputDate.myformatterTime(new Date(aData.createTime)));
	var html = '';
	if(aData.userStatus=='1'){
		html = app.addButton.stop('admin_stop',aData.userId);
	}else{
		html = app.addButton.start('admin_start',aData.userId);
	}
	$(' td:eq(6)',nRow).html(html+app.addButton.update('admin_edit',aData.userId,'500','400')+app.addButton.changePassword('admin_password',aData.userId,'500','400')+app.addButton.del('admin_del',aData.userId)+app.addButton.group('admin_group',aData.userId,'500','200'));
	return nRow;
},
"fnServerData" : function(sSource, aoData, fnCallback) {
	$.ajax({
		"type" : 'post',
		"url" : sSource,
		"dataType" : "json",
		"data" : {
			userName : $('#userName').val(),
			sEcho : aoData[0].value,
			pageNum : aoData[3].value, // iDisplayStart,  翻页第几条开始
			pageSize : aoData[4].value, // iDisplayLength  翻页每页展示数据
		},
		"success" : function(resp) {
			fnCallback(resp);
		}
	});
}
});
$(function(){

$('#table1').on( 'click', 'tr', function () {
	if ( $(this).hasClass('selected') ) {
		$(this).removeClass('selected');
	}
	else {
		$('#table1 tr.selected').removeClass('selected');
		$(this).addClass('selected');
	}
});
});
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function admin_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function admin_del(id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/user/del",
			"dataType" : "json",
			"data" : {
					userId : id
			},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('已删除!');
					doQuery();
				}else{
					app.messager.error(resp.message);
				}
			}
		});
	});
}
/*管理员-编辑*/
function admin_edit(title,id,w,h){
	layer_show(title,'${webroot}/admin/user/addPage?userId='+id,w,h);
}
/*管理员-编辑用户组*/
function admin_group(title,id,w,h){
	layer_show(title,'${webroot}/admin/user/groupPage?userId='+id,w,h);
}
/*管理员-重置密码*/
function admin_password(title,id,w,h){
	layer.confirm('确认要重置密码吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/user/password",
			"dataType" : "json",
			"data" : {
					userId : id
			},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('重置成功!');
					doQuery();
				}else{
					app.messager.error(resp.message);
				}
			}
		});
	});
}
/*管理员-停用*/
function admin_stop(id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/user/stop",
			"dataType" : "json",
			"data" : {
					userId : id
			},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('已停用!');
					doQuery();
				}else{
					app.messager.error(resp.message);
				}
			}
		});
	});
}

/*管理员-启用*/
function admin_start(id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/user/start",
			"dataType" : "json",
			"data" : {
					userId : id
			},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('已启用!');
					// 重新加载方法
					doQuery();
				}else{
					app.messager.error(resp.message);
				}
			}
		});
	});
}
function doQuery(){
	Datatables.api().ajax.reload();
}
</script>
</html>