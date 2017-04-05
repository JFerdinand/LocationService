function exit(){
	$.ajax({
		url:"/webspring/exit.do",
		type:"post",
		dataType:"json",
		data:{
			newPhone:newPhone,
		}
	}).done(function(b){
		window.location.reload();
	})
}
function set(){	
	document.getElementById("mask").style.display="";
	$("#div1").hide();
	$("#div2").show();
	displayDataPhone();
	displayDataPass();
	/*遮罩显示与否*/
	window.onload=function(){
		document.getElementById("mask").style.display="block";
	}

	/*取消遮罩*/
	function btnCancel() {
		// body...
		document.getElementById("mask").style.display="none";
	}
}
function updatePhone(){
	$("#div1").hide();
	$("#div2").show();
	displayDataPass();
	
}
function updatePwd(){
	$("#div2").hide();
	$("#div1").show();
	displayDataPhone();
}
function changeImage(){
	  document.getElementById("imgflag").src="bjimgs/04.png";
	}
//==================================重置手机号==================================================
var newPhone;
var identify;
//鼠标聚焦事件，
function getPhone1(){
	if($("#phone").val()=="手机号"){
		$("#phone").val("");
	}
	$("#phone").attr("style","width:60%;height:12%;background-color:#f0f0f0;position:absolute;top:35%;left:20%;border:none;font-family:'黑体';font-size:16px")
	$("#test-one").text("");	
	$("#test-one").hide();	
}
//鼠标离焦事件，获取电话，并进行判断
var biaoshi=0;
function setPhone(){
	newPhone=$("#phone").val();
	var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号的正则表达式
	if( newPhone==""){		
		$("#phone").val("手机号");
		$("#phone").attr("style","width: 60%; height: 12%; background-color:#f0f0f0;position:absolute;top:35%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px")
	}else if(pre.test(newPhone) == false){
		$("#test-one").show();
		$("#test-one").html("你输入的手机号码格式不对");
	}else{		
		$.ajax({
			url:"/webspring/index/getphone.do",
			type:"post",
			dataType:"json",
			data:{
				newPhone:newPhone,
			},
		   success:function(result){
			   if(result==0){
				   $("#test-one").show();
				   $("#test-one").html("你输入的手机号已经被注册"); 
			   }else if(result==1){
				   $("#test-one").hide();
				   biaoshi=1;
				  
			   }							
		  },
		})
		
	}
}
//鼠标聚焦：对验证码的样式修改
function getNum1(){
	if($("#code").val()=="验证码"){
		$("#code").val("");
	}
	$("#code").attr("style","width: 30%; height: 12%; background-color:#f0f0f0;position:absolute;top:56%;left:20%;border:none;font-family:'黑体';font-size:16px");
}
//鼠标离焦：获取验证码的值
function setNum(){
    identify=$("#code").val();
	if(identify==""){
		$("#code").val("验证码")
		$("#code").attr("style","width: 30%; height: 12%; background-color:#f0f0f0;position:absolute;top:56%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	}else if(identify=="验证码"){
		$("#test-two").show();
		$("#test-two").html("请输入验证码");
	}else{
		$("#test-two").hide();
		return 1;
	}
}
//发送验证码触发的事件，并对提示信息进行判断，以及调用发送验证码的方法
function code3(e){
	var a = $("#phone").val();
	var b = $("#test-one").text();
	if(a=="手机号"){
		$("#test-one").show();
		$("#test-one").text("请输入您的手机号");
	}else if(b==""){
		createCode(e,0)
	}
}
var countdown=60; 
var suo = 0;//标识符，无实际意义，相当一个锁，达到条件解锁，不达到条件，上锁

//短信验证，设置短信重发的时间
function createCode(val,a){
	if(suo==0){
		   identCode(a);//调用像后台请求验证码的方法
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
 
//发送短信验证码的请求，
function identCode(a){
	$.ajax({
		url:"/webspring/index/sendPhoneCode.do",
		type:"post",
		dataType:"json",
		success:function(result){
			if(a==0){
				//手机修改
				$("#code").val(result)
				$("#code").attr("style","width: 30%; height: 12%; background-color:#f0f0f0;position:absolute;top:56%;left:20%;border:none;font-family:'黑体';font-size:16px");
			}else if(a==1){
				//密码重置
				$("#code1").val(result);
				$("#code1").attr("style","width: 30%; height: 8%; background-color:#f0f0f0;position:absolute;top:39%;left:20%;border:none;font-family:'黑体';font-size:16px");
			}
			
			
		}
	}) 
}
//判断验证码是否正确，并进行页面跳转
function ConfirmPhone(){
	 identify=$("#code").val();
	 newPhone=$("#phone").val();
	 if(newPhone=="手机号"){
		 $("#test-one").show();
		 $("#test-one").html("请输入您的手机号");
	 }else if(biaoshi==1 & setNum()==1){
		    $("#test-one").hide();
			$.ajax({
				url:"/webspring/index/newPsassPhone.do",
				type:"post",
				dataType:"json",
				data:{
					newPhone:newPhone,
				},
				success:function(result){
					if(identify != result.idnum){
						$("#test-two").show();
						$("#test-two").html("您输入的验证码不正确,请重新输入");
					}else{
                         if(result.num == 1){
							 window.location.href="/webspring/main.do";
                          }
						 
					 }
				},
				error:function(result){ 
					alert("error");
				}
			}) 
		}			
}
//=============================密码重置===========================================
var newPhone1;//手机号
var identify1;//验证码
var passwords;//密码
var passwords1;//密码
//对手机号的输入框的样式进行设置
function getPhone2(){
	if($("#tophone").val()=="手机号"){
		$("#tophone").val("");
		$("#tophone").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:25%;left:20%;border:none;font-family:'黑体';font-size:16px");
	}
	$("#test-three").text("");
	$("#test-three").hide();
}
//验证手机号的正则表达式
function ToPhone(){
	 newPhone1=$("#tophone").val();
	var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号的正则表达式
	if(newPhone1==""){
		$("#tophone").val("手机号");
		$("#tophone").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:25%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	}else if(pre.test(newPhone1) == false){
		$("#test-three").show();
		$("#test-three").html("你输入的手机号码格式不对")
	}else{
		$("#test-three").hide();
		return 1;
	}
}
//发送验证码触发的事件，并对提示信息进行判断，以及调用发送验证码的方法
function code4(e){
	var a = $("#tophone").val();
	var b = $("#test-three").text();
	if(a=="手机号"){
		$("#test-three").show();
		$("#test-three").text("请输入您的手机号");
	}else if(b==""){
		createCode(e,1)
	}
}
//对验证码的样式进行设置
function getCode2(){
	if($("#code1").val()=="验证码"){
		$("#code1").val("");
	}
	$("#code1").attr("style","width: 30%; height: 8%; background-color:#f0f0f0;position:absolute;top:39%;left:20%;border:none;font-family:'黑体';font-size:16px");
}
function ToCode(){
	 identify1=$("#code1").val();
	if(identify1==""){
		$("#code1").val("验证码");
		$("#code1").attr("style","width: 30%; height: 8%; background-color:#f0f0f0;position:absolute;top:39%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	}else if(identify1=="验证码"){
		$("#test-for").show();
		$("#test-for").html("请输入你的验证码");
	}else{
		$("#test-for").hide();
		return 1;
	}
}
//对密码的输入框样式进行设置
function getNewPass(){
	if($("#psw1").val()=="请输入密码"){
		$("#psw1").val("");		
	}
	$("#psw1").attr("type","password");
	$("#psw1").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:53%;left:20%;border:none;font-family:'黑体';font-size:16px");
}
//对密码的输入的格式样式进行设置，将text类型改为password的类型
function newPass(){
	 passwords =$("#psw1").val();
	if(passwords==""){
		$("#psw1").val("请输入密码");
		$("#psw1").attr("type","text");
		$("#psw1").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:53%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	}else if(passwords.length<6||passwords.length>15){
		$('#test-five').show();
		$('#test-five').html("密码长度为6~15位");
	}else{
		$('#test-five').hide();
		return 1;
	}
	
}
//确认密码的样式进行设置
function getAgainPass(){
	if($("#psw2").val()=="确认密码"){
		$("#psw2").val("");		
	}
	$("#psw2").attr("type","password");
	$("#psw2").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:67%;left:20%;border:none;font-family:'黑体';font-size:16px");
}
//对密码的输入的格式样式进行设置，将text类型改为password的类型
function aginPass(){	
	 passwords1 =$("#psw2").val();
	if(passwords1==""){
		$("#psw2").val("确认密码");
		$("#psw2").attr("type","text");
		$("#psw2").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:67%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	}else if(passwords1.length<6||passwords1.length>15){		
		$('#test-six').show()
		$('#test-six').html("密码长度为6~15位");
	}else{
		$('#test-six').hide();
		return 1;
	}
}
//判断验证码是否正确，并进行页面跳转
function ConfirmPass(){
	 identify1=$("#code1").val();
	 newpass =$("#psw1").val();
	 aginpass =$("#psw2").val();
	 newPhone1=$("#tophone").val();
	if(ToPhone() == 1 & newPass()==1 & aginPass()==1 &ToCode()==1){
		if(newpass != aginpass){
			$("#test-six").show();
			$("#test-six").html("两次输入密码不一致，请重新输入");
		}else{
			$("#test-six").hide();
			$.ajax({
				url:"/webspring/index/newPsass.do",
				type:"post",
				dataType:"json",
				data:{
					newPhone:newPhone1,
					newpass:newpass,
					identify1:identify1,
				},
				success:function(result){
					if(result==4){
						$("#test-six").show();
						$("#test-six").html("您输入的电话号码不存在");
					}else if(result==3){
						$("#test-six").show();
						$("#test-six").html("验证码不正确,请重新输入");
					}else if(result==2){
						$("#test-six").show();
						$("#test-six").html("新密码不能与旧密码一致，请重新输入");
					}else if(result==1){
						alert("修改完成后请重新登录");
						window.location.href="/webspring/main.do";
					}
					
				},
				error:function(result){ 
					alert("error");
				}
			}) 	
		}
		
      }
			
}

function hidediv(){
	$("#mask").hide();
}
//设置修改手机号的初始样式
function displayDataPhone(){
	$("#phone").val("手机号");
	$("#phone").attr("style","width: 60%; height: 12%; background-color:#f0f0f0;position:absolute;top:35%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	$("#test-one").hide();
	
	$("#code").val("验证码");
	$("#test-two").hide();
	$("#code").attr("style","width: 30%; height: 12%; background-color:#f0f0f0;position:absolute;top:56%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
}
//设置修改密码的初始样式
function displayDataPass(){
	$("#tophone").val("手机号");
	$("#test-three").hide();
	$("#tophone").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:25%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	
	$("#code1").val("验证码");
	$("#test-for").hide();
	$("#code1").attr("style","width: 30%; height: 8%; background-color:#f0f0f0;position:absolute;top:39%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	
	$("#psw1").val("请输入密码");
	$("#psw1").attr("type","text");
	$("#psw1").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:53%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	$("#test-five").hide();
	
	$("#psw2").val("确认密码");
	$("#psw2").attr("type","text");
	$("#psw2").attr("style","width: 60%; height: 8%; background-color:#f0f0f0;position:absolute;top:67%;left:20%;border:none;color:#adadad;font-family:'黑体';font-size:16px");
	$("#test-six").hide();
	
}