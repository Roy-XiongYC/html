<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--[if lt IE 9]>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/html5.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/respond.min.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/lib/PIE_IE678.js' type='js' directory=''/>"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<script type="text/javascript" src="<wy:url name='/static/admin/lib/jquery/1.9.1/jquery.min.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/layer/2.1/layer.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/h-ui/js/H-ui.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/h-ui.admin/js/H-ui.admin.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/icheck/jquery.icheck.min.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/jquery.validation/1.14.0/jquery.validate.min.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/jquery.validation/1.14.0/validate-methods.js' type='js' directory=''/>"></script> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/jquery.validation/1.14.0/messages_zh.min.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/laypage/1.2/laypage.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/datatables/1.10.0/jquery.dataTables.min.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/jquery/jquery.form.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/app-common.js' type='js' directory=''/>"></script>
<c:set var="webroot" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    var webroot = "${webroot}";
</script>
<script type="text/javascript">
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/* 修改密码 */
function change_pass(title,url,w,h){
	layer_show(title,url,w,h);
}
/* 个人信息 */		
function user_info(title,url,w,h){
	layer_show(title,url,w,h);
}
		
/* 自定义验证 不能等于某参数 */
jQuery.validator.addMethod('notEqual', function(value, element,param) {
	return value != param;
});
</script> 
<script type="text/javascript">
// var _hmt = _hmt || [];
// (function() {
//   var hm = document.createElement("script");
//   hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
//   var s = document.getElementsByTagName("script")[0]; 
//   s.parentNode.insertBefore(hm, s)})();
// var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
// document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
