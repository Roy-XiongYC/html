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
         <div class="left" id="riji">
           <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><h1>个人日记</h1></div>
           </div>
           <div class="rj_content" >
                <!--时光-->
              <div class="shiguang animated bounceIn" ng-repeat="x in rijiData">
                <div class="left sg_ico">
                <img ng-src="{{x.image}}" width="120" height="120" alt=""/>
                </div>
                <div class="right sg_text">
                <img src="<wy:url name='/static/blog/images/left.png' type='images' directory='' />" width="13" height="16" alt="左图标"/>
                     <span ng-bind-html="x.rijiContent|to_trusted"></span>
<!--                      <span ng-bind-html="">{{x.rijiContent}}</span> -->
                </div>
                <div class="clear"></div>
              </div>
              <!--时光 end-->
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
		$http({ 
			url:webroot+'/blog/rijiData',
			method: 'POST',
			responseType:'json',
			data: {},
			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
		    transformRequest: transform
		}).success(function(response){
			if(response!=null&&response.success){
				$scope.rijiData=eval("("+response.message+")");
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

