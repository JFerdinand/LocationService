
function setUser(){
	if($("#mobile").val()=="请输入手机号或用户名"){
		$("#mobile").val("");
		$("#mobile").attr("style","");
	}
	$("#test-one").hide(); 
}
function getName(){
	 var userinfor=$("#mobile").val();
	 var aa ="请输入手机号或用户名";
	 var pre = new RegExp(/^1(3|4|5|7|8)\d{9}$/);//手机号的正则表达式
	 //只能为中文、字母、数字、下划线，2-15位，不能以数字或下划线开头
	 var lre = new RegExp(/^[a-zA-Z\u4e00-\u9fa5]{1}[a-zA-Z0-9_\u4e00-\u9fa5]{1,14}$/);//用户名正则表达式
     if(userinfor==""){
    	 $("#mobile").val("请输入手机号或用户名");
    	 $("#mobile").attr("style","color:#cccccc");
     }else if(userinfor == aa){
	    	$("#test-one").show();
			$("#test-one").html("请输入您的用户名或手机号"); 
	    }else if(lre.test(userinfor)==true|| pre.test(userinfor)== true){
    	    $("#test-one").hide(); 
		    return 1; 
        }else{
        	$("#test-one").show();
			$("#test-one").html("你输入的用户名或手机号不正确"); 
        } 
}
function setPass1(){
	if($("#psw").val()=="请输入密码"){
		$("#psw").val("");		
	}
	$("#psw").attr("style","");
	$("#psw").attr("type","password")
	$("#test-two").hide();
}
function getPassWord(){	
	var passWord=$("#psw").val();	
	if(passWord==""){
		$("#psw").attr("type","text");		
		$("#psw").val("请输入密码");
		$("#psw").attr("style","color:#cccccc");
	}else if(passWord.length<6||passWord.length>15){ 
		$("#test-two").show();
		$("#test-two").html("密码长度为6~15位");
	}else{
		$("#test-two").hide();
		return 1;
	}
}
//回车提交事件
$(window).keydown(function(e) {
    if (e.keyCode == "13") {//keyCode=13是回车键
    	onlogin();
    }
});
//点击登录，进行数据判断，以及页面跳转
function onlogin(){	
	  if(getName()==1 & getPassWord()== 1){
		  var passWord=$("#psw").val();
      	  var userName=$("#mobile").val();
          	$.ajax({
      			url:"/webspring/login/userinfor.do",
      			type:"post",
      			data:{
      				username:userName,
      				pass:passWord,   				
      			},
      			dataType:"json",
      			success:function(result){
      				if(result.AUTHORITY==10){
  				 		$("#test-two").show();
  				 		$("#test-two").html("用户名或密码错误");
  				 	}
      				else{
      					window.location.href="/webspring/main.do";
      				}
      			},
      			error:function(result){   			
      				alert("error");
      			}
  			})
       }
}   