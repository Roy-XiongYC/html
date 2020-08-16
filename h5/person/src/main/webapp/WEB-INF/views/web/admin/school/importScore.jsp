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
	<form action="" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form-member-add" >
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">项目：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<wy:select valueCol="project_id" 
				 descCol="project_name" tableName="t_school_project" pleaseSelect="请选择"
				 displayValue=""
				htmlAttribute="name='projectId' id='projectId'"></wy:select>
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">文件：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="btn-upload form-group"> <a
					href="javascript:void();" class="btn btn-primary radius"><i
						class="Hui-iconfont">&#xe642;</i> 浏览文件</a> <input type="file"
					name="file" id="file" class="input-file">
				</span>
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
			projectId:{
				required:true
			},
			},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		if ($("#file").val() == "") {
			app.messager.error("无文件上传！");
			return false;
		}
		$(document.forms[0]).ajaxSubmit({
			type : "post",
			url : "${webroot}/admin/schoolScore/dataimport",
			success : function(data) {
// 				app.messager.closeParentLayer(index); 
// 				closeIframe();
				if(data == null || data == ''){
					app.messager.error('导入失败！');
				}else {
					var dataJSON = $.parseJSON(data);
					if(dataJSON.success){
						app.messager.success('导入成功!');
						app.messager.closeParentLayer(index); 
					}else{
						app.messager.error('导入失败！');
					}
				}
			}
		});
		
		
	}
});

function closeIframe(){
	parent.layer.close(index);
}
</script>