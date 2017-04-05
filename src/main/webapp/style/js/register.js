$(document).ready(function(){
	$(".erro").hide();
	
	$("#Company").focus(function(){
		$("#Company").attr("style","");
		$("#companyerro").hide();
	})
	$("#Company").blur(function(){
		var companyname = $("select option:selected").text();
		if(companyname=="-请选择公司-"){
			$("#Company").attr("style","color:#cccccc");
		}
		
	})
	$("#Phone").focus(function(){
		if($(this).val()=="手机号"){
			$(this).val("");
		}
		$("#Phone").attr("style","");
		$("#phoneerro").hide();
	})
	//验证手机号
	$("#Phone").blur(function(){
		var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号正则表达式
		var phone = $("#Phone").val();
		if(phone==""){
			$("#Phone").val("手机号");
			$("#Phone").attr("style","color:#cccccc");
		}else{
			if(pre.test(phone) == true){
				$.ajax({
					url:"/webspring/register/judgeUserPhone.do",
					type:"post",
					data:{
						phone:phone
					},
					dataType:"json",
					success:function(result){
						if(result==1){
							$("#phoneerro").show();
							$("#phoneerro").html("*手机号已被注册，请直接登录");
						}
					},
					error:function(){
						alert("error");
					}
				})
			}else{
				$("#phoneerro").show();
				$("#phoneerro").html("*手机号码错误，请重新确认");
			}
		}
	})
	$("#repx").focus(function(){
		if($(this).val()=="验证码"){
			$(this).val("");
		}
		$("#repx").attr("style","");
		$("#messageerro").hide();
	})
	$("#repx").blur(function(){
		if($(this).val()==""){
			$("#repx").val("验证码");
			$("#repx").attr("style","color:#cccccc");
		}
	})
	
	$("#pname").focus(function(){
		if($(this).val()=="用户名"){
			$(this).val("");
		}
		$("#pname").attr("style","");
		$("#pnameerro").hide();
	})
	//验证用户名
	$("#pname").blur(function(){
		var lre = new RegExp(/^[a-zA-Z\u4e00-\u9fa5]{1}[a-zA-Z0-9_\u4e00-\u9fa5]{1,14}$/);//用户名正则表达式
		var username = $("#pname").val();
		if(username==""){
			$("#pname").val("用户名");
			$("#pname").attr("style","color:#cccccc");
		}else if(username.length<2 || username.length>15){
			$("#pnameerro").show();
			$("#pnameerro").html("*用户名长度范围为2—15位");
		}else{
			if(lre.test(username) == true && username !="用户名"){
				$.ajax({
					url:"/webspring/register/judgeUserName.do",
					type:"post",
					data:{
						username:username
					},
					dataType:"json",
					success:function(result){
						if(result==1){
							$("#pnameerro").show();
							$("#pnameerro").html("*用户名已存在，请重新输入");
						}
					},
					error:function(){
						alert("error");
					}
				})
			}else{
				if(username.substring(0,1)=="_" || (!isNaN(username.substring(0,1)))){
					$("#pnameerro").show();
					$("#pnameerro").html("*用户名不能以数字或下划线开头");
				}else{
					$("#pnameerro").show();
					$("#pnameerro").html("*用户名由中文、字母、数字、下划线组成");
				}
			}
		}
	})
	$("#pasw").focus(function(){
		if($(this).val()=="密码"){
			$(this).val("");
		}
		$("#pasw").attr("type","password");
		$("#pasw").attr("style","");
		$("#paswerro").hide();
	})
	//验证密码
	$("#pasw").blur(function(){
		var password = $("#pasw").val();
		if(password==""){
			$("#pasw").attr("type","text");
			$("#pasw").val("密码");
			$("#pasw").attr("style","color:#cccccc");
		}else{
			if(password.length<6 || password.length>15){
				$("#paswerro").show();
				$("#paswerro").html("*请输入至少6位最多15位的密码");
			}
		}
	})
})

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage() {
	//向手机发送验证码
	var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号正则表达式
	var phone = $("#Phone").val();
	if($("#phoneerro").is(':hidden')){
		if(pre.test(phone) != true){
			$("#phoneerro").show();
			$("#phoneerro").html("*请输入手机号");
		}else{
			curCount = count;
			//设置button效果，开始计时
			$("#repxbtn").attr("disabled", "true");
			InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
			//向后台发送处理数据
			sendPhoneCode();
		}
	}
}
//timer处理函数
function SetRemainTime() {
	if (curCount == 0) {                
		window.clearInterval(InterValObj);//停止计时器
		$("#repxbtn").removeAttr("disabled");//启用按钮
		$("#repxbtn").text("发送验证码");
	}
	else {
		curCount--;
		$("#repxbtn").text("重新发送(" + curCount + ")");
	}
}
var code;//验证码
function sendPhoneCode(){ 
	var phone = $("#Phone").val();
    $.ajax({
		url:"/webspring/interface/sendphonecode.do",
		type:"post",
		data:{
			phone:phone,
		},
		dataType:"json",
		success:function(b){
			if(b.code!="0"){
				code=b.code;
				$("#repx").val(b.code);
				$("#repx").attr("style","");
			}
		},
		error:function(){
			alert("error");
			$("#repxbtn").text("获取短信验证码");
		}
	})
}

$(window).keydown(function(e) {
    if (e.keyCode == "13") {//keyCode=13是回车键
    	 submit();
    }
});


function submit(){
	var phone = $("#Phone").val();
	var repx = $("#repx").val();
	var username = $("#pname").val();
	var password = $("#pasw").val();
	var companyname = $("select option:selected").text();
	if($("#phoneerro").is(":hidden") && $("#messageerro").is(":hidden")
			&& $("#pnameerro").is(":hidden") && $("#paswerro").is(":hidden")){
		if(companyname=="-请选择公司-"){
			$("#companyerro").show();
			$("#companyerro").html("*请选择公司");
		}else if(phone=="手机号"){
			$("#phoneerro").show();
			$("#phoneerro").html("*请输入手机号");
		}else if(repx=="验证码"){
			$("#messageerro").show();
			$("#messageerro").html("*请输入验证码");
		}else if(username=="用户名"){
			$("#pnameerro").show();
			$("#pnameerro").html("*请输入用户名");
		}else if(password=="密码"){
			$("#paswerro").show();
			$("#paswerro").html("*请输入密码");
		}else if(repx!=code){
			$("#messageerro").show();
			$("#messageerro").html("*验证码错误，请重新输入");
		}else{
			var companyid =$("#Company").find("option:selected").attr("value");
			$.ajax({
				url:"/webspring/register/insterUser.do",
				type:"post",
				data:{
					companyid:companyid,
					phone:phone,
					username:username,
					password:password
				},
				dataType:"json",
				success:function(b){
					if(b.result==0){
						$("#messageerro").show();
						$("#messageerro").html(b.reason);
					}else{
						window.location.href="/webspring/login/login.do";
					}
				},
				error:function(){
					alert("error");
				}
			})	
		}
	}

	
}
