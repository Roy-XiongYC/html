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
<script type="text/javascript" src="<wy:url name='/static/blog/js/common.js' directory='' type='js'/>"></script>
<script type="text/javascript" src="<wy:url name='/static/blog/js/waterfall.js' directory='' type='js'/>" ></script>
</head>
<body>
    <!--header start-->
     <%@ include file="/WEB-INF/views/web/blog/common/header.jsp"%>
    <!--header end-->
    <!--content start-->
    <div id="content_xc">
         <div class="weizi">
           <div class="wz_text">当前位置：<a href="#">首页</a>><h1>相册展示</h1></div>
         </div>
         <div class="xc_content">
          <div class="con-bg">
              <div class="w960 mt_10">
               <div class="w650">
                <ul class="tips" id="wf-main" style="display:none" >
                        <c:forEach var="x" items="${_BLOG_XC_}" end="9">
                        	<li class="wf-cld"><img src="${x.img}"  width="${x.imgWidth}" height="${x.imgHeight}" alt="" a="a" /></li>
                        </c:forEach>
                         <c:forEach var="x" items="${_BLOG_XC_}" begin="10">
                        	<li class="wf-cld"><img rel="lazy" lazy_src="${x.img}"  width="${x.imgWidth}" height="${x.imgHeight}" b="b" alt="" /></li>
                        </c:forEach>
                    </ul>
               </div>
             </div>
           </div>
         </div>
    </div>
    <!--content end-->
<%@ include file="/WEB-INF/views/web/blog/common/footer.jsp"%> 
     <script type="text/javascript">
    var timer, m = 0, m1 = $("img[rel='lazy']").length;

    function fade() {

        $("img[rel='lazy']").each(function () {

            var $scroTop = $(this).offset();

            if ($scroTop.top <= $(window).scrollTop() + $(window).height()) {

                $(this).hide();

                $(this).attr("src", $(this).attr("lazy_src"));

                $(this).attr("top", $scroTop.top);

                $(this).removeAttr("rel");

                $(this).removeAttr("lazy_src");

                $(this).fadeIn(600);

                var _left = $(this).parent().parent().attr("_left");

                if (_left != undefined)

                    $(this).parent().parent().animate({ left: _left }, 400);

                m++;

            }

        });

        if (m < m1) { timer = window.setTimeout(fade, 300); }

        else { window.clearTimeout(timer); }

    }

    $(function () {

        $("#wf-main img[rel='lazy']").each(function (i) {

            var _left = $(this).parent().parent().css("left").replace("px", "");

            $(this).parent().parent().attr("_left", _left);

            $(this).parent().parent().css("left", 0);

        });

        fade();

    });

    $(".loading").hide();

    $("#wf-main").show();

    
    
</script>	
</body>
</html>