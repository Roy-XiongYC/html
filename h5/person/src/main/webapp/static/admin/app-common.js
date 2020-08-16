var app = {
	description : 'App Common JS Library',
	version : '0.0.1'
};

app.webroot = function() {

	if (webroot == null) {
		return "";
	}
	return webroot;

};

// 格式化日期：年-月-日
app.inputDate = {
	myformatter : function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	},

	myparser : function(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	},
	myformatterTime : function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var h = date.getHours();
		var i = date.getMinutes();
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
				+ ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
	}
};


app.addButton = {
	update : function(method,id,width,height){
		return "<a title=\"编辑\" href=\"javascript:;\" onclick=\""+method+"('编辑','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>";
	},
	changePassword : function(method,id,width,height){
		return "<a title=\"重置密码\" href=\"javascript:;\" onclick=\""+method+"('重置密码','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe63f;</i></a>";
	},
	del : function(method,id){
		return "<a title=\"删除\" href=\"javascript:;\" onclick=\""+method+"('"+id+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
	},
	stop : function(method,id){
		return "<a style=\"text-decoration:none\" onClick=\""+method+"('"+id+"')\" href=\"javascript:;\" title=\"停用\"><i class=\"Hui-iconfont\">&#xe631;</i></a>";
	},
	start : function(method,id){
		return "<a style=\"text-decoration:none\" onClick=\""+method+"('"+id+"')\" href=\"javascript:;\" title=\"启用\"><i class=\"Hui-iconfont\">&#xe615;</i></a>";
	},
	group : function(method,id,width,height){
		return "<a title=\"修改用户组\" href=\"javascript:;\" onclick=\""+method+"('修改用户组','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6bd;</i></a>";
	},
	resource : function(method,id,width,height){
		return "<a title=\"配置权限\" href=\"javascript:;\" onclick=\""+method+"('配置权限','"+id+"','"+width+"','"+height+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe61d;</i></a>";
	}
};

/* closeParentLayer 需传入index  父页面需包含doQuery方法 */
app.messager = {
		success : function(msg){
			layer.msg(msg,{icon: 6,time:2000});
		},
		error : function(msg){
			layer.msg(msg,{icon: 5,time:2000});
		},
		closeParentLayer : function(index){
			if( typeof parent.doQuery === 'function' ){
			    //存在且是function
				parent.doQuery();
				setTimeout(function(){
					parent.layer.close(index);
//					parent.layer.closeAll();
				},2000); 
			}
		}
}
