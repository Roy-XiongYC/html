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
				<input type="hidden" class="input-text" value="${blogUser.userId}" id="userId" name="userId">
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.userName}" placeholder="名称" id="userName" name="userName">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户标识：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.userSign}" placeholder="用户标识" id="userSign" name="userSign">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>职业：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.occupation}" placeholder="职业 多个使用、号隔开" id="occupation" name="occupation">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">简介：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.brief}" placeholder="简介" id="brief" name="brief">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">签名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.signature}" placeholder="签名" id="signature" name="signature">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">主题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.title}" placeholder="主题" id="title" name="title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">qq：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${blogUser.qq}" placeholder="qq" id="qq" name="qq">
			</div>
		</div>
				<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">头像：</label>
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
					<c:when test="${not empty blogUser.image}">
						<img id="showPic" src="${blogUser.image}" width="300" height="300">
					</c:when>
					<c:otherwise>
						<img id="showPic" src="" width="300" height="300" style="display: none;">
					</c:otherwise>
				</c:choose>
				<input type="hidden" class="input-text" id="image" name="image" value="${blogUser.image}">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">关于：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<!-- 加载编辑器的容器 -->
			    <script id="aboutContent" name="aboutContent" type="text/plain" disabled>
					${aboutContent}
   				</script>
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
 	<!-- 配置文件 -->
 	<script type="text/javascript" src="<wy:url name='/static/admin/lib/ueditor/1.4.3/ueditor.config.js' type='js' directory=''/>"></script> 
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<wy:url name='/static/admin/lib/ueditor/1.4.3/ueditor.all.js' type='js' directory=''/>"></script> 
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name);
var falg = false;//标识关闭页面时  是否需要删除图片
var ue = UE.getEditor('aboutContent');
$("#form-member-add").validate({
	rules:{
					userName:{
				required:true,
				maxlength:20
			},
					userSign:{
				required:true,
				maxlength:10
			},
					occupation:{
				required:true,
				maxlength:40
			},
					brief:{
				maxlength:10
			},
					signature:{
				maxlength:100
			},
					title:{
				maxlength:10
			},
					qq:{
				digits:true,
				rangelength:[9,9]
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
		if(!ue.hasContents() && ue.getContent().length>100){
			app.messager.error("内容太多,只能80个字符");
			return false;
		}
		$.ajax({
			"type" : 'post',
			"url" : "${webroot}/admin/blogUser/modify",
			"dataType" : "json",
			"data" : {
											userId:$('#userId').val(),
											userName:$('#userName').val(),
											userSign:$('#userSign').val(),
											occupation:$('#occupation').val(),
											brief:$('#brief').val(),
											signature:$('#signature').val(),
											title:$('#title').val(),
											image:$('#image').val(),
											qq:$('#qq').val(),
											aboutContent:ue.getContent()
								},
			"success" : function(resp) {
				if(resp!=null && resp.success){
					app.messager.success('成功!');
					falg = false;
					//如果在内嵌页面中,关闭子页面
					if(window.parent.document.getElementById("iframe_box")){
						app.messager.closeParentLayer(index); 
					}
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