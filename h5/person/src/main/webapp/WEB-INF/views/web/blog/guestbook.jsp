<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="个人博客" />
<meta name="description" content="" />
<%@ include file="/WEB-INF/views/web/blog/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/blog/common/javascript-includes.jsp"%> 
<style type="text/css">
	.send{width: 18%;float: right; padding-right: 15px;}
	.send button{line-height: 20px; width: 120px;}
	.msg{padding-left: 20px;color:#A3A1A1; width: 20%; float: left;}
</style>
</head>

<body ng-app="myApp" ng-controller="myAppCtrl">
     <%@ include file="/WEB-INF/views/web/blog/common/header.jsp"%>
    <!--content start-->
    <div id="content">
       <!--left-->
         <div class="left" id="guestbook">
           <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><h1>留言板</h1></div>
           </div>
           <div class="g_content" style="height: auto;">
             	<textarea id="text" style="width: 700px; height: 200px;" placeholder="你要对我说什么？"></textarea>
           </div>
           <div style="margin-top: 15px;">
           		<div class="msg"><label id="msg"></label></div>
           		<div class="send"><button ng-click="guest();">发送给我 </button></div>
           </div> 
         </div>
         <!--end left -->
         <!--right-->
         <%@ include file="/WEB-INF/views/web/blog/common/right.jsp"%> 
         <!--end  right-->
         <div class="clear"></div>
         
    </div> 
    <!--content end-->
     <%@ include file="/WEB-INF/views/web/blog/common/footer.jsp"%> 
</body>
<script type="text/javascript">
	var app = angular.module('myApp', []);
	app.config(function ($httpProvider) {
		   $httpProvider.defaults.transformRequest = function(data){
		       if (data === undefined) { 
		           return data;
		       }
		       return $.param(data);
		   }
	});
	var transform = function(data){
	       return $.param(data);
	};
	
	app.controller('myAppCtrl', function($scope,$http) {
		$scope.guest = function(){
			if($('#text').val() == ''){
				$('#msg').html('哎呀，什么都没写呢');
			}else{
				$http({ 
					url:webroot+'/blog/subGuest',
					method: 'POST', 
					responseType:'json',
					data: {
						subGuest : $('#text').val()
					},
					headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
				    transformRequest: transform
				}).success(function(response){
					if(response!=null&&response.success){
						$('button').attr('disabled',true);
						$('#msg').html('我会尽快回复你的');
					}else{
						$('#msg').html('哎呀，这个还在建设');
		            	return;
					}
				}).error(function(response){
				});
			}
		}
	});
</script>
</html>

