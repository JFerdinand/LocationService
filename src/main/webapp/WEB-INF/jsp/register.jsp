<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
	<style type="text/css">
		body,html{
			margin:0;
            padding:0;
            overflow:hidden;
            width:100%;
            height:100%;
		}
		#repxbtn,#Phone,#photo,#repx,#pname,#pasw,#Company{
			font-family: '微软雅黑';
			font-size: 15px;
		}
		#main{
			width:100%;
			height:100%;
			position:relative;
			background: -webkit-linear-gradient(45deg, #00a2ca, #019eb5); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(45deg, #00a2ca, #019eb5); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(45deg, #00a2ca, #019eb5); /* Firefox 3.6 - 15 */
            background: linear-gradient(45deg, #00a2ca, #019eb5); /* 标准的语法 */
		}
		#login{
			width: 30%;
			height: 60%;
			background-color: white;
			border-radius: 5px;
			position:absolute;
			top:26.8%;
			left:37.3%;
		}
		.login-head{
			top:19.8%;
			left: 26.9%;
		}
		.all-All{
			display: inline-block;
			position: absolute;
		}
		.login-ce{
			top:9.8%;
			left:17.15%;

		}
		.login-lu{
			top:9.8%;
			right:17.15%;

		}
		a{
			text-decoration: none;
			font-family: '幼圆';
			font-size:17px;
			color:black;
		}
		#Company{
			width:64.5%;
			height:7.27%;
			position: absolute;
			top:20%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#Phone{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:33%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		
		#repx{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:46%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#pname{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:59%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#pasw{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:72%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#repxbtn{
			width:32.8%;
			height:7.1%;
			position: absolute;
			top:46%;
			right:17.2%;
			border-radius:0px 5px 5px 0px;
			border:1px #cccccc solid;
			background-color: #428bca;
			color: white;
		}
		#repsubmit{
			width:66.5%;
			height:8.27%;
			position: absolute;
			bottom:6%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
			background-color: #428bca;
			color: white;
		}
		input[type=text]:focus{
			transition:border linear .2s,box-shadow linear .5s;
			-moz-transition:border linear .2s,-moz-box-shadow linear .5s;
			-webkit-transition:border linear .2s,-webkit-box-shadow linear .5s;
			outline:none;border-color:#54a8ea; 
			box-shadow:0 0 6px #54a8ea; 
			-moz-box-shadow:0 0 6px #54a8ea;
			-webkit-box-shadow:0 0 6px #54a8ea;
			}
		.erro{
			position: absolute;
			color:red;
			font-weight: bold;
			font-family: '微软雅黑';
			font-size: 12px;
			left:17.2%;
		}
		#companyerro{
			top:28%;
		}
		#phoneerro{
			top:41%;
		}
		#messageerro{
			top:53%;
		}
		#pnameerro{
			top:67%;
		}
		#paswerro{
			top:80%;
		}
	</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.1.10.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/register.js"></script>
</head>
<body>
	<div id="main">
		<span class="all-All login-head"><img src="${pageContext.request.contextPath}/style/bjimgs/34.png" alt="" width="80%" height="80%"></span>
		<div id="login">
			<span class="all-All login-ce"><a href="${pageContext.request.contextPath}/login/login.do">登录</a></span>
			<span class="all-All login-lu"><a href="${pageContext.request.contextPath}/register.do">注册</a></span>
			<select id="Company"  style="color:#cccccc">
				<option id="sel" selected="selected">-请选择公司-</option>
				<c:forEach items="${companyList}" var="company">
				<option id="opt" value="${company.companyid}">${company.companyname}</option>
				</c:forEach>
			</select>
			<div id="companyerro" class="erro">*</div>
			<input type="text" id="Phone" value="手机号"  style="color:#cccccc">
			<div id="phoneerro" class="erro">*</div>
			<input type="text" id="repx" value="验证码"   style="color:#cccccc"><button id="repxbtn" onclick="sendMessage()">获取短信验证码</button>
			<div id="messageerro" class="erro">*</div>
			<input type="text" id="pname" value="用户名"  style="color:#cccccc">
			<div id="pnameerro" class="erro">*</div>
			<input type="text" id="pasw" value="密码"   style="color:#cccccc">
			<div id="paswerro" class="erro">*</div>
			<input type="submit" value="立即注册" id="repsubmit" onclick="submit()">
		</div>
	</div>
</body>
</html>