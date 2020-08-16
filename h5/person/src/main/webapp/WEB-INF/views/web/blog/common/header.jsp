<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!--header start-->
    <div id="header">
      <h1>${_BLOG_USER_.blogUser.title }</h1>
      <p>${_BLOG_USER_.blogUser.signature }</p>    
    </div>
     <!--header end-->
    <!--nav-->
     <div id="nav">
        <ul>
         <li><a href="${webroot}/blog/index.html">首页</a></li>
         <li><a href="${webroot}/blog/about.html">关于我</a></li>
         <li><a href="${webroot}/blog/shuo.html">碎言碎语</a></li>
         <li><a href="${webroot}/blog/riji.html">个人日记</a></li>
         <li><a href="${webroot}/blog/xc.html">相册展示</a></li>
<%--          <li><a href="${webroot}/blog/learn.html">学无止境</a></li> --%>
         <li><a href="${webroot}/blog/guestbook.html">留言板</a></li>
         <div class="clear"></div>
        </ul>
      </div>
      <!--nav end-->