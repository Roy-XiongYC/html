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
</head>

<body ng-app="myApp" ng-controller="myAppCtrl">
    <%@ include file="/WEB-INF/views/web/blog/common/header.jsp"%>
    <!--content start-->
    <div id="say" >
     <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><h1>碎言碎语</h1></div>
      </div>
      	<div ng-repeat="x in shuoData">
          <ul class="say_box">
                <div class="sy">
                         <p><span ng-bind-html="x.shuoContent|to_trusted"></span></p>
                     </div>
                  <span class="dateview" ng-bind="x.createTime | date:'yyyy-MM-dd'"></span>
          </ul>
          </div>
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
		$http({ 
			url:webroot+'/blog/shuoData',
			method: 'POST',
			responseType:'json',
			data: {},
			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
		    transformRequest: transform
		}).success(function(response){
			if(response!=null&&response.success){
				$scope.shuoData=eval("("+response.message+")");
			}else{
            	return false;
			}
		}).error(function(response){
		});
	});
	
	app.filter('to_trusted',['$sce', function ($sce) {  
        return function (text) {  
            return $sce.trustAsHtml(text);  
        }  
    }] );
</script>
</html>
