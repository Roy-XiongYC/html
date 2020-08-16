<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/web/admin/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/admin/common/javascript-includes.jsp"%> 
<link rel="stylesheet" href="<wy:url name='/static/admin/lib/zoomer/zoomer.css' type='css' directory=''/> "/>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/zoomer/zoomer.js' type='js' directory=''/>"></script>
<style type="text/css">
/* 	 含table的一定要添加此属性  */
	#table1 tbody td{
		text-align: center;
	}
</style>
<wy:shiro name="">
	<script type="text/javascript">
		alert('1')
	</script>
</wy:shiro>
<body>
	<%@ include file="/WEB-INF/views/web/admin/common/breadcrumb.jsp"%>
	<div class="page-container">
	<form if="form1">
		<div class="text-c">
						<input type="text" class="input-text" style="width:250px" placeholder="输入用户名" id="userName" name="userName">
						<input type="text" class="input-text" style="width:250px" placeholder="输入标题" id="learnTitle" name="learnTitle">
						<button type="button" onclick="doQuery();return false;" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜</button>
			<button type="reset" class="btn btn-primary"> 重置</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="add('添加','${webroot}/admin/blogLearn/addPage?id=','900','600')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加</a></span></div>
	<table id="table1" class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="10">列表</th>
			</tr>
			<tr class="text-c">
										<th name="userName">用户名</th>
										<th name="learnTitle">标题</th>
										<th name="learnImg">文章图片</th>
										<th name="learnTime">发布时间</th>
										<th name="learnAuth">作者</th>
										<th name="learnAdd">文章转载地</th>
										<th name="learnIsZh">是否推荐</th>
										<th name="aboutContent">内容</th>
										<th name="createTime">创建时间</th>
										<th name="id">操作</th>
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
"sAjaxSource" : '${webroot}/admin/blogLearn/data',
"aoColumns" :[

	{"mData" : "userName",'sClass' : 'center'},				
	{"mData" : "learnTitle",'sClass' : 'center'},				
	{"mData" : "learnImg",'sClass' : 'center'},				
	{"mData" : "learnTime",'sClass' : 'center'},				
	{"mData" : "learnAuth",'sClass' : 'center'},				
	{"mData" : "learnAdd",'sClass' : 'center'},				
	{"mData" : "learnIsZh",'sClass' : 'center'},				
	{"mData" : "aboutContent",'sClass' : 'center'},				
	{"mData" : "createTime",'sClass' : 'center'},	
	{"mData" : "id",'sClass' : 'center'},		

],
"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	//操作行 3为减去隐藏td后的列
	/* Append the grade to the default row class name */
	$('td:eq(2)',nRow).html('<div class="zoomerImg"><img style="width: 40px;height: 40px;" src="'+aData.learnImg+'" alt="" /></div>');
	$('td:eq(3)',nRow).html(app.inputDate.myformatterTime(new Date(aData.learnTime)));
	$('td:eq(8)',nRow).html(app.inputDate.myformatterTime(new Date(aData.createTime)));
	$('td:eq(9)',nRow).html(app.addButton.update('edit',aData.id,'900','600')+app.addButton.del('del',aData.id));
	return nRow;
},
"fnServerData" : function(sSource, aoData, fnCallback) {
	$.ajax({
		"type" : 'post',
		"url" : sSource,
		"dataType" : "json",
		"data" : {
							userName : $('#userName').val(),
							learnTitle : $('#learnTitle').val(),
						sEcho : aoData[0].value,
			page : aoData[3].value, // iDisplayStart,  翻页第几条开始
			rp : aoData[4].value, // iDisplayLength  翻页每页展示数据
		},
		"success" : function(resp) {
			fnCallback(resp);
			$('.zoomerImg').Zoomer({speedView:200,speedRemove:400,altAnim:true,speedTitle:400,debug:false});
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
/*增加*/
function add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*删除*/
function del(id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/blogLearn/del",
			"dataType" : "json",
			"data" : {
					id : id
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
/*编辑*/
function edit(title,id,w,h){
	layer_show(title,'${webroot}/admin/blogLearn/addPage?id='+id,w,h);
}
function doQuery(){
	Datatables.api().ajax.reload();
}
</script>
</html>
