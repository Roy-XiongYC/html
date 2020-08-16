<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="breadcrumb">
	<c:choose>
		<c:when test="${not empty _userResource_.breadcrumb.breadcrumb}">
			<i class="Hui-iconfont">&#xe67f;</i>首页
			<c:forEach var="breadc" items="${_userResource_.breadcrumb.breadcrumb}">
				<span class="c-gray en">&gt;</span>${breadc.text}
					<c:choose>
					<c:when test="${not empty breadc.children}">
						<c:forEach var="breadchildren" items="${breadc.children}">
							<span class="c-gray en">&gt;</span>${breadchildren.text}
			            </c:forEach>
					</c:when>
					<c:otherwise>
						<c:set var="selPath" value="${breadc.url}" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a class="btn btn-success radius r"
				style="line-height: 1.6em; margin-top: 3px"
				href="javascript:location.replace(location.href);" title="刷新"> <i
				class="Hui-iconfont">&#xe68f;</i>
			</a>
		</c:when>
		<c:otherwise>
			<%--如果面包屑不存在，则跳转到首页，防止访问非法路径--%>
			<script language="javascript" type="text/javascript">
				window.location.href = "${webroot}/admin/index.html";
			</script>
		</c:otherwise>
	</c:choose>
</nav>
