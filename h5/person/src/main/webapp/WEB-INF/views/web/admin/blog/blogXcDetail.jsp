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
			<input type="hidden" class="input-text" value="${blogXc.id}" placeholder="" id="id" name="id">
			<input type="hidden" class="input-text" value="${blogXc.userId}" placeholder="" id="userId" name="userId">
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>相册图片宽度：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogXc.imgWidth}" placeholder="相册图片宽度" id="imgWidth" name="imgWidth">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>相册图片高度：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogXc.imgHeight}" placeholder="相册图片高度" id="imgHeight" name="imgHeight">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>图片：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="codeView">
					<span class="btn-upload"> <a href="javascript:void();" class="btn btn-primary radius upload=btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
					<input id="file" type="file" name="file" class="input-file" onchange="upload();">
					</span>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"></label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose>
					<c:when test="${not empty blogXc.img}">
						<img id="showPic" src="${blogXc.img}" width="300" height="300">
					</c:when>
					<c:otherwise>
						<img id="showPic" src="" width="300" height="300" style="display: none;">
					</c:otherwise>
				</c:choose>
				<input type="hidden" class="input-text" id="image" name="image">
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
var falg = false;//标识关闭页面时  是否需要删除图片
$("#form-member-add").validate({
	rules:{
					image:{
				required:true
			},
					imgWidth:{
				required:true,
				number:true,
				maxlength:3
			},
					imgHeight:{
				required:true,
				number:true,
				maxlength:3
			},
			},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		if($('#image').val()==''){
			app.messager.error("请选择图片");
			return false;
		}
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/blogXc/modify",
			"dataType" : "json",
			"data" : {
											id:$('#id').val(),
											userId:$('#userId').val(),
											img:$('#image').val(),
											imgWidth:$('#imgWidth').val(),
											imgHeight:$('#imgHeight').val()
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


//异步上传文件

function upload() {
	if ($("#file").val() == "") {
		app.messager.error("无文件上传！");
		return false;
	}
	if($('#image').val() != ''){
		deleteImg();
	}
	if (valid()) {
		$(document.forms[0]).ajaxSubmit({
			type : "post",
			url : "${webroot}/admin/file/uploadFile",
			success : function(data) {
				if(data.success){
					falg = true;
					$("#showPic").show();
					$("#showPic").attr('src', data.content.show);
					$('#image').val(data.content.file);
				}else{
					app.messager.error(data.message);
				}
			}
		});
	}
};

function valid() {
	if ($("#file").val() == null || $("#file").val() == "") {
		return true;
	}
	var str = $("#file").val().toLowerCase();
	var fileLast = /\.[^\.]+/.exec(str);
	if (fileLast == null || fileLast == "") {
		app.messager.error("格式错误，请上传图片格式");
		return false;
	}
	if (".jpg" != fileLast && ".png" != fileLast && ".bmp" != fileLast
			&& ".jpeg" != fileLast) {
		app.messager.error("格式错误，请上传图片格式");
		return false;
	}
	if ($("#file").val().length > 100) {
		app.messager.error("上传图片的文件名过长，请重新选择上传图片!");
		return false;
	}
	return true;
}

function deleteImg(){
	$.ajax({
		"type" : 'post',
		"url" : "${webroot}/admin/file/deleteFile",
		"dataType" : "json",
		"data" : {
			fileName:$('#image').val()
		},
		"success" : function(resp) {
		}
	});
}

// 页面关闭事件
window.onunload = function(){
	if(falg){
		deleteImg();
	}
};

function closeIframe(){
	if(falg){
		deleteImg();
	}
	parent.layer.close(index);
}
</script>