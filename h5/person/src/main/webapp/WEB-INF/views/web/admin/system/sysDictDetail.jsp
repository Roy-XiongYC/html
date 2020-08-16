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
			<input type="hidden" class="input-text" value="${sysDict.id}" placeholder="无意义主键" id="id" name="id">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>数据字典键：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysDict.dictCode }"
					placeholder="数据字典键" id="dictCode" name="dictCode">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>数据字典值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysDict.dictValue}"
					placeholder="数据字典值" id="dictValue" name="dictValue">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>数据字典描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"
					value="${sysDict.dictRemark}" placeholder="数据字典描述"
					id="dictRemark" name="dictRemark">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit"
					value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
					dictCode:{
				required:true,
				maxlength:40
			},
					dictValue:{
				required:true,
				maxlength:100
			},
					dictRemark:{
				required:true,
				maxlength:200
			},
			},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/sysDict/modify",
			"dataType" : "json",
			"data" : {
											id:$('#id').val(),
											dictCode:$('#dictCode').val(),
											dictValue:$('#dictValue').val(),
											dictRemark:$('#dictRemark').val()
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

if('${sysDict.id}' != ''){
	$('#dictCode').attr('disabled',true);
}

function closeIframe(){
	parent.layer.close(index);
}
</script>