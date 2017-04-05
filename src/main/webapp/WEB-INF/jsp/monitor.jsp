<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-ui-slide.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/lay/modules/layer.js" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/monitor.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/js/lay/css/layui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/jquery-ui.css" />

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
		#bottom-side-one{
			width:8.3%;
			height:100%;
			background-color:#00a2ca; 
		}
		#bottom-side-two{
			width:24.9%;
			height:100%;
			position:absolute;
			top:0%;
			left:8.3%;
			border: 1px #d6d6d4 solid;
		}
		#bottom-side-three{
			width:66.8%;
			height:3.4%;
			position:absolute;
			top:0%;
			left:33.2%;
			border: 1px #d6d6d4 solid;
			border-left:none;
		}
		/* 下侧栏一icon */
		.bottom-one{
			top:2.3%;
			left:2.1%;
		}
		.bottom-two{
			top:10.3%;
			left:2.1%;
		}
		/* 下侧栏二loop*/
		#loophead-one{
			width:100%;
			height:3.4%;
			border-bottom: 1px #d6d6d4 solid;
		}
		#loophead-two{
			width:100%;
			display: inline-block;
			background-color: #f2f2f2;
			border-bottom: 1px #d6d6d4 solid;
			border-right: none;
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
		/* logo */
		.top-head-all{
			position:absolute;
			display: inline-block;
		}
		/* 下侧栏三*/
		.nav-sigh{
			top:15%;
			right:5%;
		}
		.nav-ask{
			top:15%;
			right:2%;
		}
		/* MAP */
		#map{
			width:66.8%;
			height:96.6%;
			position: absolute;
			right:0%;
			top:3.4%;
		}
	</style>
</head>
<body>
<div id="main">
		<!-- 下侧栏一 -->
		<div id="bottom-side-one">
			<span class="top-head-all bottom-one">
				<a href="javascript:monitor()">
					<img src="${pageContext.request.contextPath}/style/bjimgs/29.png" border="0" alt="">
				</a>
			</span>
			<span class="top-head-all bottom-two">
				<a href="javascript:clearsolve()">
					<img src="${pageContext.request.contextPath}/style/bjimgs/30.png" border="0" alt="">
				</a>
			</span>
		</div>
		<!-- 下侧栏二 -->
		<div id="bottom-side-two">
			<div id="loophead-one">
				<span class="top-head-all loop-one">
					<a href="">
						<img src="${pageContext.request.contextPath}/style/bjimgs/12.png"  border="0" alt="">
					</a>
				</span>
				<span class="top-head-all loop-two">1个图层0条记录</span>
				<span class="top-head-all loop-three">
					<a href="">
						<img src="${pageContext.request.contextPath}/style/bjimgs/13.png" border="0" alt="">
					</a>
				</span>
			</div>
			<p ><input style="color:#adadad;font-family:'黑体';font-size:16px" onfocus="fonthide()" onblur="fontshow()"  type="text" id="example_1" value="请点击选择时间" /></p>
			<div id="tree">
			</div>
		</div>
		<!-- 下侧栏三 -->
		<div id="bottom-side-three">
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
		<!-- MAP显示 -->
		<div id="map">
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
var map = new BMap.Map("map");    // 创建Map实例
map.centerAndZoom('北京', 12);  // 初始化地图,设置中心点坐标和地图级别
map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
map.disableDoubleClickZoom();
map.disablePinchToZoom(); 
changeMapStyle('midnight')

function changeMapStyle(style){
	map.setMapStyle({style:style});
}
</script>