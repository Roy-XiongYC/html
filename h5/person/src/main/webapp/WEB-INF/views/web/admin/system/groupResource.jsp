<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/web/admin/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/admin/common/javascript-includes.jsp"%> 
<script type="text/javascript" src="<wy:url name='/static/admin/lib/zTree/v3/js/jquery.ztree.core-3.5.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/zTree/v3/js/jquery.ztree.exedit-3.5.js' type='js' directory=''/>"></script>
<script type="text/javascript" src="<wy:url name='/static/admin/lib/zTree/v3/js/jquery.ztree.excheck-3.5.js' type='js' directory=''/>"></script>
<link rel="stylesheet" href="<wy:url name='/static/admin/lib/zTree/v3/css/demo.css' type='css' directory=''/>"></script>
<link rel="stylesheet" href="<wy:url name='/static/admin/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css' type='css' directory=''/>"></script>
<style type="text/css">
	  .radioBtn {height: 16px;vertical-align: middle;}
	  .checkboxBtn {vertical-align: middle;margin-right: 2px;}
	  </style>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add" >
		<div class="codeView">        
          <div class="cl">
          <div class="l" style="width:48%">
            <p><strong>已有权限</strong></p>
            <ul id="groupTree" class="ztree"></ul>
          </div>
          <div class="r" style="width:48%">
          	<p><strong>未有权限</strong></p>
          	<ul id="resourceTree" class="ztree"></ul>
          </div>
          </div>
          <div class="cl">
	          <div class="col-xs-8 col-sm-9 col-sm-offset-3" style="margin-left: 13.333333%;margin-top: 10px;width: 87.666667%;">
					<input class="btn btn-success radius" type="button" value="&nbsp;全部授权&nbsp;" onclick="authAll();">
					<input class="btn btn-danger radius" type="button" value="&nbsp;全部禁止&nbsp;" onclick="notAll();">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="subForm();">
					<input class="btn btn-secondary radius" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;" onclick="closeIframe();">
				</div>
          </div>
        </div>
	</form>
</article>
</body>
<SCRIPT type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name);
	var resourceSetting = {
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			},
			key : {
				name : "name"
			}
		},
		async : {
			enable : true,
			dataType : "json",
			otherParam : {
				"groupId" : "${sysGroup.groupId}",
				"accessAuth" : '0' // 0 - 未有权限参数     1- 已有权限参数     '' - 所有权限查询
			},
			url : "${webroot}/admin/groupResource/resourceData"
		},
		view : {
			showIcon : false
		},
		callback : {
			beforeDrag : beforeDrag,
			beforeDrop : beforeDrop
		}
	};

	var groupSetting = {
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			},
			key : {
				name : "name"
			}
		},
		async : {
			enable : true,
			dataType : "json",
			otherParam : {
				"groupId" : "${sysGroup.groupId}",
				"accessAuth" : '1' // 0 - 未有权限参数     1- 已有权限参数     '' - 所有权限查询
			},
			url : "${webroot}/admin/groupResource/resourceData"
		},
		view : {
			showIcon : false
		}
	};
	
	var allSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : ""
				},
				key : {
					name : "name"
				}
			},
			async : {
				enable : true,
				dataType : "json",
				otherParam : {
					"groupId" : "${sysGroup.groupId}",
					"accessAuth" : '' // 0 - 未有权限参数     1- 已有权限参数     '' - 所有权限查询
				},
				url : "${webroot}/admin/groupResource/resourceData"
			},
			view : {
				showIcon : false
			}
		};

	$(document).ready(function() {
		// 菜单树
		$.fn.zTree.init($("#resourceTree"), resourceSetting);
		$.fn.zTree.init($("#groupTree"), groupSetting);
	});

	function beforeDrag(treeId, treeNodes) {
		for (var i = 0, l = treeNodes.length; i < l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		return targetNode ? targetNode.drop !== false : true;
	}
	//全部授权
	function authAll(){
		$.fn.zTree.init($("#groupTree"), allSetting);
		$.fn.zTree.destroy("resourceTree");
	}
	//全部禁止
	function notAll(){
		$.fn.zTree.init($("#resourceTree"), allSetting);
		$.fn.zTree.destroy("groupTree");
	}
	//提交
	function subForm(){
		//获取已配置菜单
		var resource = '';
		$('#groupTree a').each(function(){
			resource += $(this).attr('title').substring(0,$(this).attr('title').indexOf('-')) + ",";
		});
		resource = resource.substring(0,resource.length-1);
		$.ajax({
			"type" : "post",
			"dataType" : "json",
			"url" : "${webroot}/admin/groupResource/saveResource",
			"data" : {
				"resource" : resource,
				"groupId" : "${sysGroup.groupId}",
			},
			"success" : function(data){
				app.messager.success('保存成功!');
				app.messager.closeParentLayer(index);
			}
		});
	}
	
	function closeIframe(){
		parent.layer.close(index);
	}
</SCRIPT>
</html>