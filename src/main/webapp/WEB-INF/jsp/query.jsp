<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.1.10.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/query.js" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>
<title>查询统计</title>
<style type="text/css">
		body,html{
			margin:0;
            padding:0;
            width:100%;
            height:100%;
            overflow-x:hidden;
		}
		#map{
			width: 100%;
			height: 100%;
			overflow: hidden;
			margin:0;
			font-family:"微软雅黑";
		}
		#main{
			width:100%;
			height:100%;
			position:relative;
		}
		#left{
			width:10%;
			height:100%;
			background-color:#e8e8e8;
		}
		#right{
			width:90%;
			height:100%;
			background-color: white;
			position: absolute;
			left:10%;
			top:0%;
		}
		.btn{
			width:7.5%;
			height:3.38%;
			border-radius: 10px;
			position: absolute;
			left:1.2%;
			font-family: "幼圆";
			font-size: 14px;
			border: none;
		}
		#btnone{
			top:6.12%;
		}
		#btntwo{
			top:13.62%;
		}
		#btnthree{
			top:21.12%;
		}
		#btnfour{
			top:26.62%;
		}
		#btnfive{
			top:34.12%;
		}
		#btnsix{
			top:41.62%;
		}
		#btnseven{
			top:49.12%;
		}
		#search-two{
			width: 17%;
			height: 2.5%;
			position: absolute;
			top:4%;
			right:80%;
			border-radius: 5px;
			background-color: #e8e8e8;
			border: none;
		}
		#search-twoImg{
			position: absolute;
			top:4.5%;
			right:80.6%;
		}
		#fork{
			position: absolute;
			top:2.0%;
			right:5.6%;
		}
		th{
			font-size: 12px;
			font-family: "黑体";
			font-weight: normal;
		}
		#right-concent{
			width: 100%;
			position: absolute;
			top:10%;
			left:0%;
		}
		table{
			width:100%;
		}
		thead{
			background-color:#f2f2f2;
		}
		tbody{
			text-align: center;
		}
		a:link {
			font-size: 12px;
			color: #000000;
			text-decoration: none;
			}
			a:visited {
			font-size: 12px;
			color: #000000;
			text-decoration: none;
			}
			a:hover {
			font-size: 12px;
			color: #999999;
			text-decoration: underline;
		} 
		#aselect{
			width:8%;
			height:2.5%;
			background-color: #e8e8e8;
			border: none;
			position: absolute;
			top:4%;
			left:13%;
			font-family: "幼圆";
		}
		#bselect{
			width:8%;
			height:2.5%;
			background-color: #e8e8e8;
			border: none;
			position: absolute;
			top:4%;
			left:28%;
			font-family: "幼圆";
		}
		#cselect{
			width:8%;
			height:2.5%;
			background-color: #e8e8e8;
			border: none;
			position: absolute;
			top:4%;
			left:43%;
			font-family: "幼圆";
		}
		#dselect{
			width:8%;
			height:2.5%;
			background-color: #e8e8e8;
			border: none;
			position: absolute;
			top:4%;
			left:58%;
			font-family: "幼圆";
		}
	</style>
</head>
<body>
	<div id="main">
		<div id="left">
			<button id="btnone" onclick="change(this)" class="btn">定位统计</button>
			<button id="btntwo" onclick="change(this)" class="btn">报警统计</button>
		</div>
		<div id="right" >
			<input type="text" value="请输入设备名称" onfocus="fonthide()" onblur="fontshow()" id="search-two" style="color:#adadad;font-family:'黑体';font-size:16px">
			<a href="javascript:search()"><img id="search-twoImg" src="${pageContext.request.contextPath}/style/bjimgs/36.png" alt="" width="18px" ></a>
			<div id="right-concent" >
				<c:forEach items="${list}" var="c">
				<table border="0" class="everyinfo"  >
					<thead id="headc" style="display:block;" >
						<tr>
						<c:forEach items="${c.devicespecial[0]}"  var="entry">
						<c:if test="${entry.key!='relng'&&entry.key!='relat'&&entry.key!='deviceid'}">
							<th onclick="reveal(this)" style="width:300px;height:35px"><c:out value="${entry.key}" /></th>
						</c:if>
						</c:forEach>
							<th onclick="reveal(this)" style="width:300px;height:35px">
								位置
							</th>
						</tr>
					</thead>
					<tbody id="bodyc" style="overflow:auto;display:block;height:300px" class="bodyc">
						<c:forEach items="${c.devicespecial}"  var="c1">
							<tr>
							<c:forEach items="${c1}"  var="c2">
							<c:if test="${c2.key!='relng'&&c2.key!='relat'&&c2.key!='deviceid'}">
							<td style="width:300px">
							${c2.value}
							</td>
							</c:if>
							</c:forEach>
							<td style="width:300px" align="center">
							<a href="javascript:void(0)" style="background-color:#e5e5e5"  relng="${c1.relng}" relat="${c1.relat}">
							查看
							</a>
							</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:forEach>
			</div>
		</div>
		<div id="mask" style="width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000;display:none">
			<!-- 密码重置 -->
			 <div id="div1" style="width:30%;height:45%;border-radius:10px;background-color:white;opacity:1;position: absolute;top:20.5%;left:35%;">
				<div style="width:100%;border-radius:10px 10px 0px 0px;background-color:#00a2ca;line-height:40px;font-family:'黑体';font-size:20px;color:white;text-align:center;">
				位置显示
				<a href="javascript:maskhide()"><img id="fork" src="${pageContext.request.contextPath}/style/bjimgs/chabai_03.png" alt="" width="18px" ></a>
				</div>
				<div id="map">
				</div>
			</div>
			<!-- MAP显示 -->
		</div> 
	</div>
<script type="text/javascript">
	var map = new BMap.Map("map");
	map.centerAndZoom(new BMap.Point(116.404, 39.915),12);
	map.enableScrollWheelZoom(true);
	map.disableDoubleClickZoom(); 
	map.disablePinchToZoom() ;
</script>
</body>
</html>