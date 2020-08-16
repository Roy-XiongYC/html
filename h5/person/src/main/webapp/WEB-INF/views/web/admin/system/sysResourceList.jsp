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
						<input type="text" class="input-text" style="width:250px" placeholder="输入菜单名称" id="resourceName" name="resourceName">
						<button type="button" onclick="doQuery();return false;" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜菜单</button>
						<button type="reset" class="btn btn-primary"> 重置</button>
		</div>
	</form>
	<div class="cl pd-5 mt-20"> <span class="l"></span></div>
	<table id="table1" class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="9">列表</th>
			</tr>
			<tr class="text-c">
										<th name="resourceCode">菜单编号</th>
										<th name="resourceName">菜单名称</th>
										<th name="resourceTypeZh">菜单类型</th>
										<th name="url">菜单路径</th>
										<th name="parentResourceId">父菜单Id</th>
										<th name="orderNumber">序号</th>
								</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
</body>
<script type="text/javascript">
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
"sAjaxSource" : '${webroot}/admin/sysResource/data',
"aoColumns" :[

	{"mData" : "resourceCode",'sClass' : 'center'},				
	{"mData" : "resourceName",'sClass' : 'center'},				
	{"mData" : "resourceTypeZh",'sClass' : 'center'},				
	{"mData" : "url",'sClass' : 'center'},				
	{"mData" : "parentResourceId",'sClass' : 'center'},				
	{"mData" : "orderNumber",'sClass' : 'center'},				

],
"fnServerData" : function(sSource, aoData, fnCallback) {
	$.ajax({
		"type" : 'post',
		"url" : sSource,
		"dataType" : "json",
		"data" : {
							resourceName : $('#resourceName').val(),
						sEcho : aoData[0].value,
			page : aoData[3].value, // iDisplayStart,  翻页第几条开始
			rp : aoData[4].value, // iDisplayLength  翻页每页展示数据
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
function doQuery(){
	Datatables.api().ajax.reload();
}
</script>
</html>
