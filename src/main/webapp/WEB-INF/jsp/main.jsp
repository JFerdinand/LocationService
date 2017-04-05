<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/privilege"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>位置服务平台</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/mapsolve.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/monitor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/main.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" /> 	
	
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
		#top-head{
			width:100%;
			height:8.3%;
			background-color:#242b31;
		}
		#top-nav{
			width:100%;
			height:3.4%;
			border-bottom: 1px #d4d4d4 solid;
		}
		#side{
			width:17.5%;
			height:88.3%;
			border-right: 1px #d4d4d4 solid;
		}
		 #map{
			width:82.5%;
			height:88.3%;
			position: absolute;
			top:11.7%;
			right: 0%;
		} 
		/* 导航 */
		.top-nav-all{
			width:100%;
			height:3.4%;
			border-right: 1px #d4d4d4 solid;
			position: absolute;
			top:8.3%;
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
		.top-head-logo{
			top:2.4%;
			left:3.6%;
		}
		.top-head-logotext{
			top:3%;
			left:7.1%;
		}
		/* logo menu */
		.top-head-logoone{
			top:2.5%;
			left:20.5%;
		}
		.top-head-logotwo{
			top:2.5%;
			left:33.1%;
		} 
		.top-head-logothree{
			top:2.5%;
			left:45.7%;
		} 
		.top-head-logofour{
			top:2.5%;
			left:58.3%;
		} 
		.top-head-logofive{
			top:2.5%;
			left:70.9%;
		}
		/* logo menu icon */
		.top-head-logosix{
			top:3%;
			left:21%;
		}
		.top-head-logoseven{
			top:3%;
			left:33.6%;
		}    
		.top-head-logoeight{
			top:3%;
			left:46.2%;
		}    
		.top-head-logonine{
			top:3%;
			left:58.8%;
		}    
		.top-head-logoten{
			top:3%;
			left:71.4%;
		}
	/* logo menu text */
		.top-head-logo-one{
			top:3.5%;
			left:23.5%;
		}
		.top-head-logo-two{
			top:3.5%;
			left:36.1%;
		}        
		.top-head-logo-three{
			top:3.5%;
			left:48.7%;
		}        
		.top-head-logo-four{
			top:3.5%;
			left:61.3%;
		}        
		.top-head-logo-five{
			top:3.5%;
			left:73.9%;
		}
		.top-head-setting{
			top:3%;
			right:2.5%;
		}
		.top-head-exit{
			top:3%;
			right:5.5%;
		}
		/* nav icon */
		.nav-one{
			top:22%;
			left:3.5%;
		}
		.nav-two{
			top:22%;
			left:85%;
		}
		.nav-textone{
			top:22%;
			left:14.5%;
			font-family: "黑体";
			font-size:18px;
		}
		.nav-texttwo{
			top:25%;
			left:8.5%;
			font-family: "黑体";
			font-size: 15px;
		}
		.nav-three{
			top:8%;
			left:28.5%;
		}
		.nav-four{
			top:15%;
			right:29%;
		}
		.nav-five{
			top:23%;
			right:16.5%;
		}
		.nav-six{
			top:22%;
			right:3%;
		}
		.nav-seven{
			top:19%;
			right:44%;
		}
		.nav-eight{
			top:19%;
			right:31%;
		}
		.nav-nine{
			top:19%;
			right:18%;
		}
		.nav-ten{
			top:16%;
			right:3%;
		}
		.nav-search{
			top:20%;
			right:30%;
		}
		.nav-sigh{
			top:9%;
			right:5%;
		}
		.nav-ask{
			top:9%;
			right:2%;
		}
		/* 边栏样式 */
		#sideone{
			width:100%;
			border-bottom: 1px #d4d4d4 solid;
		}
		.sideicon-one{ 
			top:12.8%;
			left:0.5%;
		}
		.sideicon-two{
			top:12.9%;
			left:12%;
		}
		.sideicon-three{
			top:12.8%;
			left:14%;
		}
		.sideicon-four{
			top:12.8%;
			left:16%;
		}
		.sidetext-one{
			top:13.2%;
			left:2.3%;
			font-family: "黑体";
			font-size: 13px;
		}
		.sidetext-two{
			top:17.5%;
			left:2.3%;
		}
		.sidetext-two a{
			font-family: "幼圆";
			font-size: 18px;
			color: #00a2ca;
			text-decoration: none;
		}
		#test-one{
			position: absolute;
			top:47%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-two{
			position: absolute;
			top:70%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-three{
			position: absolute;
			top:33%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-for{
			position: absolute;
			top:47%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-five{
			position: absolute;
			top:60%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-six{
			position: absolute;
			top:75%;
			left:19%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
	</style>
</head>
<body>
	<div id="main">
		<!-- 顶栏logo -->
		<div id="top-head">
			<span class="top-head-logo top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/01.png" alt=""></span>
			<span class="top-head-logotext top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/02.png" alt=""></span>
			<p:privilege target="1">
			<span class="top-head-logoone top-head-all top-head-js"><img src="${pageContext.request.contextPath}/style/bjimgs/03.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logotwo top-head-all top-head-js"><img src="${pageContext.request.contextPath}/style/bjimgs/03.png" alt=""></span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logothree top-head-all top-head-js"><img src="${pageContext.request.contextPath}/style/bjimgs/03.png" alt=""></span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logofour top-head-all top-head-js"><img src="${pageContext.request.contextPath}/style/bjimgs/03.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logofive top-head-all top-head-js"><img src="${pageContext.request.contextPath}/style/bjimgs/03.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logosix top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/05.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logoseven top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/05.png" alt=""></span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logoeight top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/05.png" alt=""></span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logonine top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/05.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logoten top-head-all"><img src="${pageContext.request.contextPath}/style/bjimgs/05.png" alt=""></span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logo-one top-head-all top-head-text">
				<a href="javascript:refresh(1)">
					<img src="${pageContext.request.contextPath}/style/bjimgs/06.png" border="0" alt="">
				</a>
			</span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logo-two top-head-all top-head-text">
				<a href="javascript:refresh(2)">
					<img src="${pageContext.request.contextPath}/style/bjimgs/07.png" border="0" alt="">
				</a>
			</span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logo-three top-head-all top-head-text">
				<a href="javascript:refresh(3)">
					<img src="${pageContext.request.contextPath}/style/bjimgs/08.png" border="0" alt="">
				</a>
			</span>
			</p:privilege>
			<p:privilege target="2">
			<span class="top-head-logo-four top-head-all top-head-text">
				<a href="">
					<img src="${pageContext.request.contextPath}/style/bjimgs/09.png" border="0" alt="">
				</a>
			</span>
			</p:privilege>
			<p:privilege target="1">
			<span class="top-head-logo-five top-head-all top-head-text">
				<a href="javascript:refresh(5)">
					<img src="${pageContext.request.contextPath}/style/bjimgs/10.png" border="0" alt="">
				</a>
			</span>
			</p:privilege>
			<span class="top-head-exit top-head-all" style="color:#FFFFFF">
			${username}
			<a href="javascript:exit()">
				退出
			</a>
			</span>
			<span class="top-head-setting top-head-all">
			<a href="javascript:set()">
				<img src="${pageContext.request.contextPath}/style/bjimgs/11.png" border="0" alt="">
			</a>
			</span>
		</div>
		<div id="main" class="main">
			<iframe id="show" src="/webspring/map/show.do" width="100%" height="100%" scrolling="no" frameborder="0"> </iframe>
		</div>
		<!-- 遮罩层 -->
		<div id="mask" style="width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000;display:none">

			<!-- 密码重置 -->
			 <div id="div1" style="width:30%;height:45%;border-radius:10px;background-color:white;opacity:1;position: absolute;top:37.5%;left:35%;">
				<div style="width:100%;border-radius:10px 10px 0px 0px;background-color:#242b31;line-height:40px;font-family:'黑体';font-size:20px;color:white;text-align:center;">
				基本设置
				<span style="display:inline-block;position:absolute;top:0.8%;right:2%"><a href="javascript:hidediv()"><img id="fork" src="${pageContext.request.contextPath}/style/bjimgs/chabai_03.png" alt="" width="60%" ></a></span>
				</div>
					<span onclick="updatePhone()" style="display:inline-block;position:absolute;top:14%;left:15%;font-family:'黑体';font-size:16px;"><a href="#" style="text-decoration:none;color:#adadad">手机号修改</a></span>
					<span onclick="updatePwd()" style="display:inline-block;position:absolute;top:14%;right:16%;font-family:'黑体';font-size:16px;"><a href="#" style="text-decoration:none;color:black">密码重置</a></span>
					<form action=""> 
						<input type="text"  id="tophone" value="手机号" onfocus="getPhone2()"onblur="ToPhone()"  style="width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:25%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px">
						<div id="test-three"></div>																		                                    
						<input type="text" id="code1" value="验证码" onfocus="getCode2()" onblur="ToCode()"  style="width: 30%; height: 8%; background-color:#f0f0f0;position:absolute;top:39%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px">
			
						<input type="button" onclick="code4(this)" value="发送验证"  style="background-color:#242b31;position:absolute;top:39%;right:19%;border-radius:5px;border:none;color:white;width:30%;height:8.5%;font-family:'黑体';font-size:15px;">
						<div id="test-for"></div>
											
			            <input type="text" id="psw1" value="请输入密码" onfocus="getNewPass()" onblur="newPass()" style="width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:53%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px" />			    
			            <div id="test-five"></div>
						
			            <input type="text" id="psw2" value="确认密码" onfocus="getAgainPass()" onblur="aginPass()" style="width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:67%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px" />			    
			            <div id="test-six"></div>
						<input type="button" onclick="ConfirmPass()" value="确认修改"  style="background-color:#242b31;position:absolute;bottom:10%;left:33%;border-radius:5px;border:none;color:white;width:30%;height:8%;font-family:'黑体';font-size:15px;">
					</form>
			</div>
			
			<!-- 手机号修改 -->
			<div id="div2" style="width:30%;height:35%;border-radius:10px;background-color:white;opacity:1;position: absolute;top:37.5%;left:35%;">
				<div style="width:100%;border-radius:10px 10px 0px 0px;background-color:#242b31;line-height:40px;font-family:'黑体';font-size:20px;color:white;text-align:center;">
				基本设置
				<span style="display:inline-block;position:absolute;top:0.8%;right:2%"><a href="javascript:hidediv()"><img id="fork" src="${pageContext.request.contextPath}/style/bjimgs/chabai_03.png" alt="" width="60%" ></a></span>
				</div>
					<span onclick="updatePhone()" style="display:inline-block;position:absolute;top:19%;left:15%;font-family:'黑体';font-size:16px;"><a href="#" style="text-decoration:none;color:black">手机号修改</a></span>
					<span onclick="updatePwd()" style="display:inline-block;position:absolute;top:19%;right:16%;font-family:'黑体';font-size:16px;"><a href="#" style="text-decoration:none;color:#adadad">密码重置</a></span>
					<form action="">
						<input type="text"  id="phone" value="手机号" onfocus="getPhone1()" onblur="setPhone()"  style="width: 60%; height: 12%; background-color:#f0f0f0;position:absolute;top:35%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px">
						<div id="test-one"></div>
						<input type="text" id="code" value="验证码" onfocus="getNum1()" onblur="setNum()" style="width: 30%; height: 12%; background-color:#f0f0f0;position:absolute;top:56%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px">
			
						<input type="button" onclick="code3(this)" value="发送验证码"  style="background-color:#242b31;position:absolute;top:56%;right:19%;border-radius:5px;border:none;color:white;width:30%;height:12.5%;font-family:'黑体';font-size:15px;">
			            <div id="test-two"></div>
						<input type="button" onclick="ConfirmPhone()" value="确认修改"  style="background-color:#242b31;position:absolute;bottom:9%;left:33%;border-radius:5px;border:none;color:white;width:30%;height:12%;font-family:'黑体';font-size:15px;">
					</form>
			</div>
		</div> 
	</div>
<script type="text/javascript">
	window.onload=function(){
		var x=document.getElementsByClassName("top-head-js");
		x.click
	}
</script>
</body>
</html>