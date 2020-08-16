<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>看看自己</title>
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
         <div class="left" id="c_left">
           <div class="s_tuijian">
           <h2>文章<span>推荐</span></h2>
           </div>
          <div class="content_text">
           <!--wz-->
           <div class="wz" ng-repeat="x in learnData">
            <h3><a ng-href="${webroot}/blog/new/{{x.id}}.html" title="{{x.learnTitle}}"><span ng-bind="x.learnTitle"></span></a></h3>
             <dl>
               <dt><img ng-src="{{x.learnImg}}" width="200"  height="123" alt=""></dt>
               <dd>
                 <p class="dd_text_1"><span ng-bind-html="x.aboutContent |to_trusted |limitTo : 100 "></span>...</p>
               <p class="dd_text_2">
               <span class="left author" ng-bind="x.learnAuth"></span><span class="left sj">时间:<span ng-bind="x.learnTime |date:'yyyy-MM-dd'"></span></span>
               <span class="left fl">所属:<a href="#" title="{{x.learnAdd}}"><span ng-bind="x.learnAdd"></span></a></span><span class="left yd"><a href="${webroot}/blog/new/{{x.id}}.html" title="阅读全文">阅读全文</a>
               </span>
                <div class="clear"></div>
               </p>
               </dd>
               <div class="clear"></div>
             </dl>
            </div>
           <!--wz end-->
           </div>
         </div>
         <!--left end-->
         <!--right-->
         <%@ include file="/WEB-INF/views/web/blog/common/right.jsp"%> 
         <!--right end-->
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
			url:webroot+'/blog/learnData',
			method: 'POST',
			responseType:'json',
			data: {},
			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
		    transformRequest: transform
		}).success(function(response){
			if(response!=null&&response.success){
// 				$scope.learnData=$sce.trustAsHtml(eval("("+model.message+")"));
				$scope.learnData=eval("("+response.message+")");
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