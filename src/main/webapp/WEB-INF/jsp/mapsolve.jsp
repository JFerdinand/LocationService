<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/mapsolve.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/mapshow.js" charset="utf-8"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" /> 	
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/js/lay/css/layui.css" />
<title>Insert title here</title>
	<style type="text/css">
		/* 整体构架 */
		body,html{
			margin:0;
            padding:0;
            overflow:hidden;
            width:100%;
            height:100%;
		}
		#main{
			width:100%;
			height:100%;
			position:relative;
		}
		#top-nav{
			width:100%;
			height:3.4%;
			border-bottom: 1px #d4d4d4 solid;
		}
		#side{
			width:17.5%;
			height:96.6%;
			border-right: 1px #d4d4d4 solid;
		}
		 #map{
			width:82.5%;
			height:96.6%;
			position: absolute;
			top:3.4%;
			right: 0%;
		} 
		/* 导航 */
		.top-nav-all{
			width:100%;
			height:3.4%;
			border-right: 1px #d4d4d4 solid;
			position: absolute;
			top:0%;
		}
		.top-nav-one{
			width: 17.5%;
		}
		.top-nav-two{
			width: 21.5%;
			left:17.5%;
		}
		.top-nav-three{
			width: 17.4%;
			left:39%;
		}
		.top-nav-four{
			width: 4%;
			left:56.4%;
		}
		/* logo */
		.top-head-all{
			position:absolute;
			display: inline-block;
		}
		/* nav icon */
		.nav-one{
			top:14%;
			left:3.5%;
		}
		.nav-two{
			top:14%;
			left:85%;
		}
		.nav-textone{
			top:14%;
			left:14.5%;
			font-family: "黑体";
			font-size:18px;
		}
		.nav-texttwo{
			top:20%;
			left:8.5%;
			font-family: "黑体";
			font-size: 15px;
		}
		.nav-three{
			top:9%;
			left:28.5%;
		}
		.nav-four{
			top:14%;
			right:29%;
		}
		.nav-five{
			top:22%;
			right:16.5%;
		}
		.nav-six{
			top:20%;
			right:3%;
		}
		.nav-seven{
			top:16%;
			right:44%;
		}
		.nav-eight{
			top:16%;
			right:31%;
		}
		.nav-nine{
			top:16%;
			right:18%;
		}
		.nav-ten{
			top:14%;
			right:3%;
		}
		.nav-search{
			top:20%;
			right:30%;
		}
		.nav-sigh{
			top:0.5%;
			right:5%;
		}
		.nav-ask{
			top:0.5%;
			right:2%;
		}
		/* 边栏样式 */
		#sideone{
			width:100%;
			height:10%;
			border-bottom: 1px #d4d4d4 solid;
		}
		.sideicon-one{
			top:3.8%;
			left:0.5%;
		}
		.sideicon-two{
			top:3.9%;
			left:12%;
		}
		.sideicon-three{
			top:3.8%;
			left:14%;
		}
		.sideicon-four{
			top:3.8%;
			left:16%;
		}
		.sidetext-one{
			top:4.2%;
			left:2.3%;
			font-family: "黑体";
			font-size: 13px;
		}
		.sidetext-two{
			top:9.5%;
			left:2.3%;
		}
		.sidetext-two a{
			font-family: "幼圆";
			font-size: 18px;
			color: #00a2ca;
			text-decoration: none;
		}
		/* 下侧栏二loop*/
		#loophead-one{
			width:100%;
			height:3.4%;
			border-bottom: 1px #d6d6d4 solid;
		}
		#loophead-two{
			width:100%;
			background-color: #f2f2f2;
			border-bottom: 1px #d6d6d4 solid;
			border-right: none;
			display:inline-block;
		}
		#loopcontent{
			width:100%;
		}
		.loop-one{
			top:0.6%;
			left:3.5%;
		}
		.loop-two{
			top:0.8%;
			left:12.5%;
			font-size: 15px;
			font-family: "黑体";
		}
		.loop-three{
			top:0.7%;
			right:2.3%;
		}
	</style>
</head>
<body>
<div id="main">
<div id="top-nav">
<div class="top-nav-one top-nav-all">
	<span class="top-head-all nav-one">
		<a href="">
			<img src="${pageContext.request.contextPath}/style/bjimgs/12.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-textone">1个图层0条记录</span>
	<span class="top-head-all nav-two">
		<a href="">
			<img src="${pageContext.request.contextPath}/style/bjimgs/13.png" border="0" alt="">
		</a>
	</span>
</div>
<div class="top-nav-two top-nav-all">
	<span class="top-head-all nav-texttwo">北京市</span>
	<span class="top-head-all nav-three">
		<a href="javascript:void(0)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/24.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-four">
		<a href="javascript:hand()">
			<img src="${pageContext.request.contextPath}/style/bjimgs/14.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-five">
		<a href="javascript:void(0)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/15.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-six">
		<a href="javascript:distance()">
			<img src="${pageContext.request.contextPath}/style/bjimgs/16.png" border="0" alt="">
		</a>
	</span>
</div>
<div class="top-nav-three top-nav-all">
	<span class="top-head-all nav-seven">
		<a href="javascript:locationx()">
			<img src="${pageContext.request.contextPath}/style/bjimgs/17.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-eight">
		<a href="javascript:draw(BMAP_DRAWING_POLYLINE)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/18.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-nine">
		<a href="javascript:draw(BMAP_DRAWING_POLYGON)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/19.png" border="0" alt="">
		</a>
	</span>
	<span class="top-head-all nav-ten">
		<a href="javascript:draw(BMAP_DRAWING_CIRCLE)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/20.png" border="0" alt="">
		</a>
	</span>
</div>
<div class="top-nav-four top-nav-all">
	<span class="top-head-all nav-search">
		<a href="javascript:void(0)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/21.png" border="0" alt="">
		</a>
	</span>
</div>
<span class="top-head-all nav-sigh">
		<a href="javascript:void(0)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/22.png" border="0" alt="">
		</a>
</span>
<span class="top-head-all nav-ask">
		<a href="javascript:void(0)">
			<img src="${pageContext.request.contextPath}/style/bjimgs/23.png" border="0" alt="">
			</a>
	</span>
</div>
	<!-- 侧边栏显示 -->
<div id="side">
	<ul id="menu">
	
	</ul>
</div>
<!-- MAP显示 -->
<div id="map">

</div>
</div>
<script type="text/javascript">

var map = new BMap.Map("map");
map.centerAndZoom(new BMap.Point(116.404, 39.915),12);
map.enableScrollWheelZoom(true);
map.disableDoubleClickZoom(); 
map.disablePinchToZoom() ;
var pointarr = [];
</script >
</body>
</html>