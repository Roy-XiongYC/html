<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/web/admin/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/admin/common/javascript-includes.jsp"%> 
<link rel="stylesheet" href="<wy:url name='/static/admin/h-ui.admin/css/H-ui.login.css' type='css' directory=''/> "/>
<title>登录</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" id="from" action="index.html" method="post">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input class="input-text size-L" type="text" id="yanzm" name="yanzm" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
          <img id="Image1" src="${webroot}/admin/code.do"> <a id="kanbuq" href="javascript:;" onclick="changeImg()">看不清，换一张</a> </div>
      </div>
      <div class="row cl"> 
        <div class="formControls col-xs-8 col-xs-offset-3">
          <label for="online" id="message" style="color: red;"></label>
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">person</div>
</body>
<script type="text/javascript">
$(function(){
	//如果在内嵌页面中,浏览器刷新页面
	if(window.parent.document.getElementById("iframe_box")){
		window.parent.location.href = location.href;
	}
});
$("#from").validate({
	rules:{
		username:{
			required:true
		},
		password:{
			required:true
		},
		yanzm:{
			notEqual : "验证码:",
			required:true,
			remote: {
			    url: "${webroot}/admin/vailYzm",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			        code: function() {
			            return $("#yanzm").val();
			        }
			    }
			},
		},
	},
	messages:{
		username:{
			required: "请填写用户名",
			minlength: "用户名长度不能小于4",
			maxlength: "用户名长度不能大于6"
		},
		password:{
			required: "请填写密码" 
		},
		yanzm:{
			notEqual: "请输入验证码",
			required: "请输入验证码",
			remote: "验证码不正确",
		},
	},
	errorLabelContainer: "#message",
	onkeyup:false,
	onfocusout:false,
	errorClass:'a',
	showErrors:function(errorMap,errorList) {
		$('#message').html('');
		this.defaultShowErrors(); 
		$('#message label:visible:eq(0)').nextAll().remove();//第一个li下所有跟随的同胞级li删除
	},
	submitHandler:function(form){
		login();
	}
});

function login(){
	$.ajax({
		url : "${webroot}/admin/login",
		type : "POST",
		data : {
			"username" : $('#username').val(),
			"password" : $('#password').val()
			},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if (data.success == true) {
				window.location = '${webroot}/admin/index.html';
			} else {
				$('#message').html('');
				$('#message').css("display","inline");
				$('#message').html(data.message);   
			}
		}
	});
}

//刷新验证码
function changeImg() {
	document.getElementById("Image1").src = "${webroot}/admin/code.do?"
			+ Math.random();
}
</script>
</html>