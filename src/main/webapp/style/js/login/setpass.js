
function setPhone(){
	if($("#tophone").val()=="手机号"){
		$("#tophone").val("");
		$("#tophone").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:20%;left:24%;border:none;");
	} 
   $("#test-three").hide();
}
function  getPhone(){
	var phone=$("#tophone").val();
	var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号的正则表达式
	if(phone == ""){
		$("#tophone").val("手机号");
		 $("#tophone").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:20%;left:24%;border:none; color:#cccccc");
	}else{
		if(pre.test(phone)== false){
			$("#test-three").show();
			$("#test-three").html("您输入的手机号码不正确");
		}else{
			$("#test-three").hide();
			return 1;
		}
	}
}

var countdown=60; 
var suo = 0;//标识符，无实际意义，相当一个锁，达到条件解锁，不达到条件，上锁
//短信验证，设置短信重发的时间
function createCode(val){
	if(getPhone() ==1){	
		if(suo==0){
			   identCode();//调用像后台请求验证码的方法
			}
			if(countdown == 0) { 
					val.removeAttribute("disabled");
					suo=0;
					val.value="发送验证码"; 
					countdown = 60;			
				}else { 
					val.setAttribute("disabled", true); 
					val.value="重新发送(" + countdown + ")";
					countdown--; 
					setTimeout(function() {
						suo=1;
					createCode(val)
					 
				},1000) 
			}
		}
} 

//发送短信验证码的请求，
function identCode(){
	$.ajax({
		url:"/webspring/index/sendPhoneCode.do",
		type:"post",
		dataType:"json",
		success:function(result){
			$("#code1").val(result);
			$("#code1").attr("style","width:25%;height:8%;background-color:#f0f0f0;position:absolute;top:35.5%;left:24%;border:none;");
		}
		
	}) 
}

function setCode(){
	if($("#code1").val()=="验证码"){
		$("#code1").val("")
		$("#code1").attr("style","width:25%;height:8%;background-color:#f0f0f0;position:absolute;top:35.5%;left:24%;border:none;");
	}
   $("#test-for").hide();
}
function getCode(){
	var code=$("#code1").val();
	 if(code==""){
		 $("#code1").val("验证码");
		 $("#code1").attr("style","width:25%;height:8%;background-color:#f0f0f0;position:absolute;top:35.5%;left:24%;border:none; color:#cccccc");
	 }else if(code=="验证码"){
		 $("#test-for").show();
	    $("#test-for").html("请输入验证码");
	 }else{
		 $("#test-for").hide();
		 return 1;
	 }
}

function hidepassword(){
	if($("#pswtext").val()=="请输入密码"){
		$("#pswtext").val("");
	}
	$("#pswtext").attr("type","password")
	$("#pswtext").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:50%;left:24%;border:none;");
	$("#test-five").hide();
	
}
function getPass1(){
	var pass1 =$("#pswtext").val();
	if(pass1==""){
		$("#pswtext").attr("type","text");
		$("#pswtext").val("请输入密码");
		$("#pswtext").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:50%;left:24%;border:none;color:#cccccc");
	}else if(pass1.length<6 || pass1.length>15){
		$("#test-five").show();
		$("#test-five").html("密码长度为6-15位，请重新输入");
	}else{
		$("#test-five").hide();
		return 1;
	}
}

function hidepassword1(){
	if($("#pswtext1").val()=="确认密码"){
		$("#pswtext1").val("");
	}
	$("#pswtext1").attr("type","password")
	$("#pswtext1").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:66%;left:24%;border:none;");
	$("#test-six").hide();
}
function getPass2(){
	var pass2 =$("#pswtext1").val();
	if(pass2 == ""){
		$("#pswtext1").attr("type","text")
		$("#pswtext1").val("确认密码");
		$("#pswtext1").attr("style","width:50%;height:8%;background-color:#f0f0f0;position:absolute;top:66%;left:24%;border:none;color:#cccccc");
	}else if(pass2.length<6 || pass2.length>15){
		$("#test-six").show();
		$("#test-six").html("密码长度为6-15位，请重新输入");
	}else{
		$("#test-six").hide();
		return 1;
	}
}

//回车提交事件
$(window).keydown(function(e) {
    if (e.keyCode == "13") {//keyCode=13是回车键
    	ConfirmPassWord();
    }
});

function ConfirmPassWord(){
	var phone=$("#tophone").val();
	var code=$("#code1").val();	
	var pass1 =$("#pswtext").val();
	var pass2 =$("#pswtext1").val();
	if(getPhone()==1 & getCode()==1 & getPass1()==1 & getPass2()==1){
		if(pass1 != pass2){
			$("#test-six").show();
			$("#test-six").html("你两次输入的密码不一致，请重新输入");
		}else{
			$("#test-six").hide();
			$.ajax({
				url:"/webspring/set/bypass.do",
				type:"post",
				dataType:"json",
				data:{
					newPhone:phone,
					newpass:pass2,
				},
				success:function(result){
					if(code != result.idnum){
						$("#test-six").show();
						$("#test-six").html("您输入的验证码不正确,请重新输入");
					}else{
						if(result.num == 2){
							$("#test-six").show();
							$("#test-six").html("新密码不能与旧密码一致，请重新输入");						
						}else if(result.num ==3){
							$("#test-six").show();
							$("#test-six").html("您输入的电话号码不存在");
						}else if(result.num ==1){
							window.location.href="/webspring/login/login.do";
						}
						 
					}
				},
				error:function(result){ 
					alert("error");
				}
			}) 	
		}
	}
}
