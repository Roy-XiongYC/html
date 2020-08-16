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
						<input type="text" class="input-text" style="width:250px" placeholder="输入学号" id="studentId" name="studentId">
						<input type="text" class="input-text" style="width:250px" placeholder="输入学生姓名" id="studentName" name="studentName">
						<wy:select valueCol="student_class"   descCol="student_class" tableName="t_school_student" pleaseSelect="请选择班级"
						 groupBy="student_class" displayValue="" htmlAttribute="name='studentClass' id='studentClass'"></wy:select>
						<wy:select valueCol="project_id"   descCol="project_name" tableName="t_school_project" pleaseSelect="请选择项目"
						 displayValue="" htmlAttribute="name='projectId' id='projectId'"></wy:select>
						<button type="button" onclick="doQuery();return false;" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜</button>
			<button type="reset" class="btn btn-primary"> 重置</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> </div>
	<table id="table1" class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="16">列表</th>
			</tr>
			<tr class="text-c">
										
										<th name="studentId">学号</th>
										<th name="studentName">姓名</th>
										<th name="studentClass">班级</th>
										<th name="projectName">项目</th>
										<th name="signValue">签到</th>
										<th name="videoValue">视频资源学习</th>
										<th name="nvideoValue">非视频资源学习</th>
										<th name="classValue">课堂表现</th>
										<th name="voteValue">投票问卷</th>
										<th name="brainValue">头脑风暴</th>
										<th name="discussValue">讨论答疑</th>
										<th name="testValue">测试</th>
										<th name="taskValue">作业小组任务</th>
										<th name="guidanceValue">校外指导打分</th>
										<th name="teacherValue">教师打分</th>
										<th name="totalMark">总评成绩</th>
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
"sAjaxSource" : '${pageContext.request.contextPath}/admin/schoolScore/data',
"aoColumns" :[

	{"mData" : "studentId",'sClass' : 'center','sWidth' : '120px'},				
	{"mData" : "studentName",'sClass' : 'center','sWidth' : '50px'},				
	{"mData" : "studentClass",'sClass' : 'center','sWidth' : '80px'},				
	{"mData" : "projectName",'sClass' : 'center','sWidth' : '100px'},				
	{"mData" : "signValue",'sClass' : 'center'},				
	{"mData" : "videoValue",'sClass' : 'center'},				
	{"mData" : "nvideoValue",'sClass' : 'center'},				
	{"mData" : "classValue",'sClass' : 'center'},				
	{"mData" : "voteValue",'sClass' : 'center'},				
	{"mData" : "brainValue",'sClass' : 'center'},				
	{"mData" : "discussValue",'sClass' : 'center'},				
	{"mData" : "testValue",'sClass' : 'center'},				
	{"mData" : "taskValue",'sClass' : 'center'},				
	{"mData" : "guidanceValue",'sClass' : 'center'},				
	{"mData" : "teacherValue",'sClass' : 'center'},				
	{"mData" : "totalMark",'sClass' : 'center'},				

],
"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	return nRow;
},
"fnServerData" : function(sSource, aoData, fnCallback) {
	$.ajax({
		"type" : 'post',
		"url" : sSource,
		"dataType" : "json",
		"data" : {
							studentId : $('#studentId').val(),
							studentName : $('#studentName').val(),
							studentClass : $('#studentClass').val(),
							projectId : $('#projectId').val(),
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
	if($('#studentClass').val() == ''){
		app.messager.error('请选择班级');
		return false;
	}
	if($('#projectId').val() == ''){
		app.messager.error('请选择项目');
		return false;
	}
	Datatables.api().ajax.reload();
	$('tbody').show();
}
</script>
</html>
