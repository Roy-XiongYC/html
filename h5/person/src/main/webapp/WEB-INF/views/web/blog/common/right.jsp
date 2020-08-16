<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--right-->
         <div class="right" id="c_right">
          <div class="s_about">
          <h2>关于博主</h2>
           <img src="${_BLOG_USER_.blogUser.image }" width="230" height="230" alt="博主"/>
           <p>博主：${_BLOG_USER_.blogUser.userName }</p>
           <p>职业：${_BLOG_USER_.blogUser.occupation}</p>
           <p>简介：${_BLOG_USER_.blogUser.brief}</p>
           <p>
           <a href="${_BLOG_USER_.blogUser.qq }" title="联系博主"><span class="left b_1"></span></a>
<!--            <a href="#" title="加入QQ群，一起学习！"><span class="left b_2"></span></a> -->
           <div class="clear"></div>
           </p>
          </div>
          <!--栏目分类-->
           <div class="lanmubox">
              <div class="hd">
               <ul><li>精心推荐</li><li>最新文章</li><li class="hd_3">随机文章</li></ul>
              </div>
              <div class="bd">
                <!-- 精心推荐 -->
                <c:choose>
                <c:when test="${not empty _BLOG_USER_.recomLearn}">
                	<ul>
                		<c:forEach var="learn" items="${_BLOG_USER_.recomLearn}">
                			<li><a href="${webroot}/blog/new/${learn.id}.html" title="${learn.learnTitle }">${learn.learnTitle }</a></li>
                		</c:forEach>
					</ul>
                </c:when>
                <c:otherwise>
                	<ul></ul>
                </c:otherwise>
                 </c:choose>
                <!-- 最新文章 -->
                 <c:choose>
                 <c:when test="${not empty _BLOG_USER_.newLearn}">
                	<ul>
                		<c:forEach var="learn" items="${_BLOG_USER_.newLearn}">
                			<li><a href="${webroot}/blog/new/${learn.id}.html" title="${learn.learnTitle }">${learn.learnTitle }</a></li>
                		</c:forEach>
					</ul>
                </c:when>
                <c:otherwise>
                	<ul></ul>
                </c:otherwise>
                 </c:choose>
                <!-- 随机文章 -->
                <c:choose>
                 <c:when test="${not empty _BLOG_USER_.ranLearn}">
                	<ul>
                		<c:forEach var="learn" items="${_BLOG_USER_.ranLearn}">
                			<li><a href="${webroot}/blog/new/${learn.id}.html" title="${learn.learnTitle }">${learn.learnTitle }</a></li>
                		</c:forEach>
					</ul>
                </c:when>
                <c:otherwise>
                	<ul></ul>
                </c:otherwise>
                 </c:choose>
              </div>
           </div>
           <!--end-->
           <div class="link">
            <h2>友情链接</h2>
			<c:choose>
			<c:when test="${not empty _BLOG_USER_.linkList}">
				<c:forEach var="link" items="${_BLOG_USER_.linkList}">
					<p><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></p>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p></p>
			</c:otherwise>
			</c:choose>
           </div>
         </div>
         <!--right end-->