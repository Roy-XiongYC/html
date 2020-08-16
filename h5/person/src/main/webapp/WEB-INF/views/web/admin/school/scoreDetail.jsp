<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/web/admin/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/admin/common/javascript-includes.jsp"%> 
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add" >
		<input type="hidden" value="${schoolScore.id}" id="id">
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">学号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.studentId}" placeholder="学号" id="studentId" name="studentId">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>项目id：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.projectId}" placeholder="项目id" id="projectId" name="projectId">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>签到经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.signValue}" placeholder="签到经验值" id="signValue" name="signValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>视频资源学习经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.videoValue}" placeholder="视频资源学习经验值" id="videoValue" name="videoValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>非视频资源学习经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.nvideoValue}" placeholder="非视频资源学习经验值" id="nvideoValue" name="nvideoValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>课堂表现经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.classValue}" placeholder="课堂表现经验值" id="classValue" name="classValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>投票问卷经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.voteValue}" placeholder="投票问卷经验值" id="voteValue" name="voteValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>头脑风暴经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.brainValue}" placeholder="头脑风暴经验值" id="brainValue" name="brainValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>讨论答疑经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.discussValue}" placeholder="讨论答疑经验值" id="discussValue" name="discussValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>测试经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.testValue}" placeholder="测试经验值" id="testValue" name="testValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>作业小组任务经验值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.taskValue}" placeholder="作业小组任务经验值" id="taskValue" name="taskValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>校外指导打分：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.guidanceValue}" placeholder="校外指导打分" id="guidanceValue" name="guidanceValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>教师打分：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.teacherValue}" placeholder="教师打分" id="teacherValue" name="teacherValue">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>总评成绩：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.totalMark}" placeholder="总评成绩" id="totalMark" name="totalMark">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${schoolScore.createTime}" placeholder="" id="createTime" name="createTime">
			</div>
		</div>
				<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				<input class="btn btn-secondary radius" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;" onclick="closeIframe();">
			</div>
		</div>
	</form>
</article>
</body>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name);
$("#form-member-add").validate({
	rules:{
					id:{
				required:true
			},
					studentId:{
				required:true
			},
					projectId:{
				required:true
			},
					signValue:{
				required:true
			},
					videoValue:{
				required:true
			},
					nvideoValue:{
				required:true
			},
					classValue:{
				required:true
			},
					voteValue:{
				required:true
			},
					brainValue:{
				required:true
			},
					discussValue:{
				required:true
			},
					testValue:{
				required:true
			},
					taskValue:{
				required:true
			},
					guidanceValue:{
				required:true
			},
					teacherValue:{
				required:true
			},
					totalMark:{
				required:true
			},
					createTime:{
				required:true
			},
			},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/schoolScore/modify",
			"dataType" : "json",
			"data" : {
											id:$('#id').val(),
											studentId:$('#studentId').val(),
											projectId:$('#projectId').val(),
											signValue:$('#signValue').val(),
											videoValue:$('#videoValue').val(),
											nvideoValue:$('#nvideoValue').val(),
											classValue:$('#classValue').val(),
											voteValue:$('#voteValue').val(),
											brainValue:$('#brainValue').val(),
											discussValue:$('#discussValue').val(),
											testValue:$('#testValue').val(),
											taskValue:$('#taskValue').val(),
											guidanceValue:$('#guidanceValue').val(),
											teacherValue:$('#teacherValue').val(),
											totalMark:$('#totalMark').val(),
											createTime:$('#createTime').val(),
								},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('成功!');
					app.messager.closeParentLayer(index); 
				}else{
					app.messager.error(resp.message);
				}
			}
		});
	}
});

function closeIframe(){
	parent.layer.close(index);
}
</script>