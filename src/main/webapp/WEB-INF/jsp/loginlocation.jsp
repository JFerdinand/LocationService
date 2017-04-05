<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.1.10.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/login/login.js"></script>
	<title>登录</title>
	<style type="text/css">
		body,html{
			margin:0;
            padding:0;
            overflow:hidden;
            width:100%;
            height:100%;
		}
		#mobile,#psw,.onece,.onecd{
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
			height: 50%;
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
		.login-lu{
			top:9.8%;
			left:17.15%;

		}
		.login-ce{
			top:9.8%;
			right:17.15%;

		}
		a{
			text-decoration: none;
			font-family: '幼圆';
			font-size:17px;
			color:black;
		}
		#mobile{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:22%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#psw{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:37%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
		}
		#repsubmit{
			width:66.5%;
			height:8.27%;
			position: absolute;
			top:60%;
			left:17.2%;
			border-radius: 5px;
			border:1px #cccccc solid;
			background-color: #428bca;
			color: white;
		}
		.onecc{
			top:52%;
			left:17.2%; 
		}
		.onece{
			top:52%;
			left:22.2%;
			color: #cccccc; 

		}
		.onecd{
			top:52%;
			right:17.2%; 
			color: #cccccc; 
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
		input[type=password]:focus{
			transition:border linear .2s,box-shadow linear .5s;
			-moz-transition:border linear .2s,-moz-box-shadow linear .5s;
			-webkit-transition:border linear .2s,-webkit-box-shadow linear .5s;
			outline:none;border-color:#54a8ea; 
			box-shadow:0 0 6px #54a8ea; 
			-moz-box-shadow:0 0 6px #54a8ea;
			-webkit-box-shadow:0 0 6px #54a8ea;
			}
		#test-one{
			position: absolute;
			top:30%;
			left:17.2%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
		#test-two{
			position: absolute;
			top:46%;
			left:17.2%;
			color:#D92B4B;
			font-family: '微软雅黑';
		}
	</style>
</head>
<body >
	<div id="main" >
		<span class="all-All login-head"><img src="${pageContext.request.contextPath}/style/imgs/34.png" alt="" width="80%" height="80%"></span>
		<div id="login">
			<span class="all-All login-lu" id="luce"><a href="${pageContext.request.contextPath}/login/login.do">登录</a></span>
			<span class="all-All login-ce" id="lucu"><a href="${pageContext.request.contextPath}/register.do">注册</a></span>
			<form action="">
			<input type="text" id="mobile" value="请输入手机号或用户名" onfocus="setUser()" onblur="getName()" style="color:#cccccc">
			<div id="test-one"></div>
			<input type="text" id="psw" value="请输入密码" onfocus="setPass1()" onblur="getPassWord()"  style="color:#cccccc">
			<div id="test-two"></div>
			<span class="all-All onecd"><a href="${pageContext.request.contextPath}/set/password.do">忘记密码？</a></span>
			<input type="button"  onclick="onlogin()" value="登录" id="repsubmit">
			</form>
		</div>
	</div>
</body>
</html>
