var userList={};
$(function(){

	$("#tr_30").hide();
	$("#btnthree").on("click",function(){
		$.ajax({
			type:"post",
			url:"/webspring/user/authoritymanager.do",
			dataType:"json"
		}).done(function(b){
			userList=b;
			for(var i=0;i<b.length;i++){
				$("#tr_30").children().eq(1).text(b[i].USERNAME);
				$("#tr_30").children().find("span").text(b[i].rolename);				
				$("#tr_30").children().eq(3).find('button').attr("id",b[i].USERID);
				var a = $("#tr_30").html();
				$("#bodycthree").append("<tr class='tr_31'>"+a+"</tr>")
			}	
			aberrationU();
		})
	})
	$("#btnthree").on("click",function(){
		$(".tr_31").remove();
	})
})
var lock=1;//相当于一个锁，当满足条件时，打开，不满足条件时，关闭
function editpower(btn){
	if($(btn).text()=='编辑'){	
		var roleuser=$(btn).parents("tr").find("span").text();
		$(btn).parents("tr").find("span").hide();	
		$(btn).text("确定");
		$(btn).css("backgroundColor","#00a2ca");
		$(btn).css("color","white");
		$(btn).parents("tr").find("select").show();
		$(btn).parents("tr").find("select").val(roleuser);
	}else if($(btn).text()=="确定"){
   		$(btn).parents("tr").find("select").hide(); 
   		var roleuser=$(btn).parents("tr").find("span").text();
   		var roleid =$(btn).parents("tr").find("select option:selected").attr("val");//获取select标签的值
   		var roleidtext =$(btn).parents("tr").find("select option:selected").text();
   			$(btn).text("编辑");
   		   	$(btn).css("backgroundColor","#e5e5e5");
   		   	$(btn).css("color","black");
   		   	$(btn).parents("tr").find("span").text(roleidtext);
   	   		$(btn).parents("tr").find("span").show();
   	   		var userid =$(btn).attr("id");
   	   	    var user=$("#search-three").val();	
   	   		$.ajax({
   				type:"post",
   				url:"/webspring/user/authorityrevision.do",
   				dataType:"json",
   				data:{
   				  roleid:roleid,
   				  userid:userid,
   				}
   	   		
   			}).done(function(a){
   					for(var i=0;i<userList.length;i++){
   	   					if(user == userList[i].USERNAME){
   	   						userList[i].rolename=roleidtext;
   	   						userList[i].roleid=roleid;  						
   	   					 }
   	   				}  				  				
   			})
   			
	}
}

function setStyle(){
	if($("#search-three").val()=="请输入你要查找的用户名"){
		$("#search-three").val("");
	}
}
function getStyle(){
	if($("#search-three").val()==""){
		$("#search-three").val("请输入你要查找的用户名");
	}
}
//回车提交事件
$(window).keydown(function(e) {
    if (e.keyCode == "13") {//keyCode=13是回车键
    	searchUser();
    }
});
function searchUser(){
	var user=$("#search-three").val();	
	for(var i=0;i<userList.length;i++){
		if(user == userList[i].USERNAME){
			$(".tr_31").remove();
			$("#tr_30").children().eq(1).text(userList[i].USERNAME);
			$("#tr_30").children().find("span").text(userList[i].rolename);				
			$("#tr_30").children().eq(3).find('button').attr("id",userList[i].USERID);
			var a = $("#tr_30").html();
			$("#bodycthree").append("<tr class='tr_31'>"+a+"</tr>")
		}
	}
}
/*//删除用户
var buttn;
function clearpower(b){
	buttn=$(btn).attr("id");
	$("#mask_i").attr("onclick","confirmBtn()");//对id=mask_i的onclick的方法命名
   $("#mask").show();	
}
//确认删除用户
function confirmBtn(){
	$("#"+buttn).parents("tr").remove();
	buttn="";
	$("#mask_i").attr("onclick","");
	$("#mask").hide();
}
//取消
function hid(){
	$("#mask").hide();
}*/
function aberrationU(){
	var a = 0;
	$(".tr_31").each(function(){
		if(a==0){
			$(this).attr("style","");
			a++;
		}else{
			$(this).attr("style","background-color:#f2f2f2;");
			a=0;
		}
	})
}