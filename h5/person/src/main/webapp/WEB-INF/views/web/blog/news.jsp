<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="" />
<%@ include file="/WEB-INF/views/web/blog/common/css-includes.jsp"%> 
<%@ include file="/WEB-INF/views/web/blog/common/javascript-includes.jsp"%> 
</head>

<body>
    <%@ include file="/WEB-INF/views/web/blog/common/header.jsp"%>
    <!--content start-->
    <div id="content">
       <!--left-->
         <div class="left" id="news">
           <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><a href="#">学无止境</a>><span>文章内容</span></div>
           </div>
           <c:choose>
           		<c:when test="${not empty _BLOG_NEWS_}">
					<div class="news_content">
						<div class="news_top">
							<h1>${_BLOG_NEWS_.learnTitle }</h1>
							<p>
								<span class="left sj">时间:<fmt:formatDate
										value="${_BLOG_NEWS_.learnTime }" pattern="yyyy-MM-dd" /></span><span
									class="left fl">所属:${_BLOG_NEWS_.learnAdd }</span> <span
									class="left author">${_BLOG_NEWS_.learnAuth }</span>
							</p>
							<div class="clear"></div>
						</div>
						<div class="news_text">${_BLOG_NEWS_.aboutContent }</div>
					</div>
				</c:when>
           		<c:otherwise>
           			 <div class="news_content">
           			 	 <div class="news_top">
                    <h1>文章找不到了</h1>
                    <div class="clear"></div>
                  </div>
           			 </div>
           		</c:otherwise>
           </c:choose>
         </div>
         <!--end left -->
         <!--right-->
           <%@ include file="/WEB-INF/views/web/blog/common/right.jsp"%> 
         <!--end  right-->
         <div class="clear"></div>
         
    </div>
    <!--content end-->
    <!--footer-->
   <%@ include file="/WEB-INF/views/web/blog/common/footer.jsp"%> 
    <!--footer end-->
</body>
</html>
