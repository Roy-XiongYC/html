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
		<input type="hidden" class="input-text" value="${blogLink.id}" placeholder="" id="id" name="id">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>连接名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogLink.linkName}" placeholder="连接名称" id="linkName" name="linkName">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>连接地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogLink.linkUrl}" placeholder="连接地址" id="linkUrl" name="linkUrl">
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
					linkName:{
				required:true,
				maxlength:19
			},
					linkUrl:{
				required:true,
				url:true,
				maxlength:190
			},
			},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/blogLink/modify",
			"dataType" : "json",
			"data" : {
											id:$('#id').val(),
											linkName:$('#linkName').val(),
											linkUrl:$('#linkUrl').val()
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