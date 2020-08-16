<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="" />
<meta name="description" content="" />
<%@ include file="/WEB-INF/views/web/blog/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/blog/common/javascript-includes.jsp"%> 
</head>

<body ng-app="myApp" ng-controller="myAppCtrl">
      <%@ include file="/WEB-INF/views/web/blog/common/header.jsp"%>
    <!--content start-->
    <div id="content">
       <!--left-->
         <div class="left" id="about_me">
           <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><h1>关于我</h1></div>
           </div>
           <div class="about_content" ng-bind-html="aboutContent">
             	
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
	
	app.controller('myAppCtrl', function($scope,$http,$sce) {
		$http({ 
			url:webroot+'/blog/aboutData',
			method: 'POST',
			responseType:'json',
			data: {},
			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
		    transformRequest: transform
		}).success(function(response){
			if(response!=null&&response.success){
				$scope.aboutContent = $sce.trustAsHtml(eval("("+response.message+")").aboutContent);
			}else{
            	return false;
			}
		}).error(function(response){
		});
	});
	
// 	app.filter('to_trusted',['$sce', function ($sce) {  
//         return function (text) {  
//             return $sce.trustAsHtml(text);  
//         }  
//     }] );
</script>
</html>
