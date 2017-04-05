<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.6.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/login/setpass.js"></script>
	<title>登录</title>
	<style type="text/css">
		body,html{
			margin:0;
            padding:0;
            width:100%;
            height:100%;
            min-width:1200px;
            min-height:300px;
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
		#pswtext{
			width:64.5%;
			height:6.27%;
			position: absolute;
			top:37%;
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
		input:focus{
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
			font-weight: bold;
			font-family: '微软雅黑';
		}
		#test-two{
			position: absolute;
			top:46%;
			left:17.2%;
			color:#D92B4B;
			font-weight: bold;
			font-family: '微软雅黑';
		}
	</style>
</head>
<body>
	<div id="main">
		<span class="all-All login-head"><img src="${pageContext.request.contextPath}/style/bjimgs/34.png" alt="" width="80%" height="80%"></span>
		<!-- 新加的-找回密码 -->		
			<div style="width: 30%;height:42%;background-color: white;border-radius: 10px;position:absolute;top:28.8%;left:35.3%;">
				<div style="width:100%;position:absolute;top:0%;left:0%;background-color:#00a2ca;color:white;font-family:'黑体';font-size: 15px; text-align: center;       
				 line-height: 40px;   border-radius:10px 10px 0px 0px">
					<span style="display:inline-block;position:absolute;top:10%;left:3%;"><a href="/webspring/login/login.do"><img src="${pageContext.request.contextPath}/style/bjimgs/38.png" alt="" width="50%"></a></span>
					<span>找回密码</span>
				</div>
				 <form action="">
					<input type="text" id="tophone" value="手机号" onfocus="setPhone()" onblur="getPhone()" style="width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:20%;left:24%;border:none; color:#cccccc" >
					<div id="test-three" style="width:50%;height:6%;position:absolute;top:29%;left:24%;border:none;font-size:12px;color:red"></div>

					<input type="text" id="code1" value="验证码" onfocus="setCode()" onblur="getCode()"  style="width:25%;height:8%;background-color:#f0f0f0;position:absolute;top:35.5%;left:24%;border:none; color:#cccccc" >

					<input type="button" onclick="createCode(this)" value="发送验证" style="width:21%;line-height: 24px; background-color:#00a2ca;position:absolute;top:36.5%;right:28%;border:none;color:white;font-family:'黑体';border-radius:5px">
					<div id="test-for" style="width:50%;height:6%;position:absolute;top:42%;left:24%;border:none;font-size:12px;color:red"></div>
					
					<input type="text"  id="pswtext" onfocus="hidepassword()"  onblur="getPass1()" value="请输入密码" style="width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:50%;left:24%;border:none; color:#cccccc" >
					<div id="test-five" style="width:50%;height:6%;position:absolute;top:59%;left:24%;border:none;font-size:12px;color:red"></div>

					<input type="text"  id="pswtext1" onfocus="hidepassword1()"  onblur="getPass2()" value="确认密码" style="width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:66%;left:24%;border:none; color:#cccccc" >
					<div id="test-six" style="width:50%;height:6%;position:absolute;top:75%;left:24%;border:none;font-size:12px;color:red"></div>

					<input  type="button" onclick="ConfirmPassWord()" value="确认修改" style="background-color:#00a2ca;position:absolute;left:35%;bottom:6%;width:30%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
				</form>
			</div>
		</div>	
</body>
</html>