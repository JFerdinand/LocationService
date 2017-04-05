$(function(){
	$(".edit").hide();
	$("#btnone").css("backgroundColor","#00a2ca");
	$("#btnone").css("color","white");
	$("#department").show();
	$("#headcone_ed").hide();
	$("#bodycone_ed").hide();
	$.ajax({
		url:"/webspring/allocation/development.do",
		type:"post",
		data:null,
		dataType:"json",
	}).done(function(e){
		//加载部门组列表
		for(var i=0;i<e.developmentList.length;i++){
			//加载部门
			$("#headcone_ed").children().children().eq(1).find("span").text(e.developmentList[i].development);
			$("#headcone_ed").children().children().eq(1).find("input").attr("value",e.developmentList[i].developid);
			$("#headcone_ed").find("a").text(e.developmentList[i].developmentcount);
			var a = $("#headcone_ed").html();
			var b = "";
			for(var j=0;j<e.developmentList[i].developmentList.length;j++){
				//加载组别
				$("#bodycone_ed").find("span").text(e.developmentList[i].developmentList[j].development);
				$("#bodycone_ed").find("input").attr("value",e.developmentList[i].developmentList[j].developid);
				b += $("#bodycone_ed").html();
				
			}
			$("#tabone").append("<thead class='headc'>"+a+"</thead><tbody class='bodyc'>"+b+"</tbody>");
		}
		$(".bodyc").hide();
	})
	
	$("#add_01").focus(function(){
		$("#error").hide();
		if($("#add_01").val()=="部门名称"){
			$("#add_01").val("");
			$("#add_01").css("color","");
		}
	})
	$("#add_01").blur(function(){
		if($("#add_01").val()==""){
			$("#add_01").val("部门名称");
			$("#add_01").css("color","#cccccc");
		}
	})
	$("#add_02").focus(function(){
		$("#error_02").hide();
		$("#add_02").css("color","");
	})
	$("#add_02").blur(function(){
		if($('#add_02 option:selected').text()=="-请选择部门-"){
			$(this).css("color","#cccccc");
		}else{
			var e =$('#add_02 option:selected').attr("val");
			$("#id_03").val(e);
		}
	})
	$("#add_03").focus(function(){
		$("#error_03").hide();
		if($("#add_03").val()=="组别名称"){
			$("#add_03").css("color","");
			$("#add_03").val("");
		}	
	})
	$("#add_03").blur(function(){
		if($("#add_03").val()==""){
			$("#add_03").val("组别名称");
			$("#add_03").css("color","#cccccc");
		}
	})
})
//组别显示与隐藏
function reveal(e){
	if($(e).parent().find("a").text()!=0){
		if($(e).parents("thead").next("tbody").is(":hidden")){
			$(e).parents("thead").next("tbody").show();
		}else{
			$(e).parents("thead").next("tbody").hide();
		}
	}
}
var obj;//修改的部门组对象
//修改部门，弹出修改框
function editdem(e){
	obj=e;
	var a = $(e).parents("tr").children().eq(1).find("span").text();
	var b = $(e).parents("tr").children().eq(1).find("input").val();
	$("#title_01").text("修改部门");
	$("#add_01").val(a);
	$("#add_01").css("color","");
	$("#id_01").val(b);
	$("#addtrue_01").attr("onclick","edittruedem()");
	$("#adddem").show();

}
//修改组别，弹出修改框
function editgroup(e){
	obj=e;
	var a = $(e).parents("tr").children().eq(1).find("span").text();
	var b = $(e).parents("tr").children().eq(1).find("input").val();
	var c = $(e).parents("tbody").prev("thead").children().children().eq(1).find("span").text();
	$("#title_02").text("修改组别");
	$("#add_02").hide();
	$("#mr_02").attr("placeholder",c);
	$("#mr_02").show();
	$("#add_03").val(a);
	$("#add_03").css("color","");
	$("#id_03").val(b);
	$("#addtrue_03").attr("onclick","edittruegp()");
	$("#addgroup").show();

}
//对修改后的部门信息提交
function edittruedem(){
	var developid = $("#id_01").val();
	var development = $("#add_01").val();
	var parentid = 0;
	if(development=="部门名称"){
		$("#error").text("*请输入部门名称");
		$("#error").show();
	}else{
		$.ajax({
			url:"/webspring/department/editdem.do",
			type:"post",
			data:{
				developid:developid,
				development:development,
				parentid:parentid
			},
			dataType:"json",
		}).done(function(b){
			if(b==1){
				$(obj).parents("tr").children().eq(1).find("span").text(development);
				hiddem()
			}else{
				$("#error").text("*该部门已存在");
				$("#error").show();
			}
		})
	}
}
//对修改后的组别信息提交
function edittruegp(){
	var developid = $("#id_03").val();
	var development = $("#add_03").val();
	var parentid = $(obj).parents("tbody").prev("thead").children().children().eq(1).find("input").val();
	if(development=="组别名称"){
		$("#error_03").text("*请输入组别名称");
		$("#error_03").show();
	}else{
		$.ajax({
			url:"/webspring/department/editdem.do",
			type:"post",
			data:{
				developid:developid,
				development:development,
				parentid:parentid
			},
			dataType:"json",
		}).done(function(b){
			if(b==1){
				$(obj).parents("tr").find("span").text(development);
				hidgp();
			}else{
				$("#error_03").text("*该组别已存在");
				$("#error_03").show();
			}
		})
	}
}
//隐藏部门修改框
function hiddem(){
	$("#title_01").text("添加部门");
	$("#add_01").val("部门名称");
	$("#add_01").css("color","#cccccc");
	$("#id_01").val("");
	$("#addtrue_01").attr("onclick","");
	$("#error").hide();
	$("#adddem").hide();
}
//隐藏修改框组别
function hidgp(){
	$("#title_02").text("添加组别");
	$("#add_02").show();
	$("#add_02").val("-请选择部门-");
	$("#add_02").css("color","#cccccc");
	$("#mr_02").hide();
	$("#add_03").val("组别名称");
	$("#add_03").css("color","#cccccc");
	$("#id_03").val("");
	$("#error_03").hide();
	$("#addtrue_02").attr("onclick","");
	$("#addgroup").hide();
}

var id;//需要删除的部门组ID
var obj;//需要删除的部门组标签对象
//判断是否删除部门
function deldmp(e){
	var developid=$(e).parents("tr").children().eq(1).find("input").val();
	$.ajax({
		url:"/webspring/department/deldem.do",
		type:"post",
		data:{
			developid:developid
		},
		dataType:"json",
	}).done(function(b){
		if(b.result==0){
			id=developid;
			obj=e;
			$("#mask").show();
			$("#mask_s").text(b.reason);
			$("#mask_s").css("left","16%");
			$("#mask_i").attr("onclick","delverify()");
		}else{
			$(e).parents("tr").remove();
		}
	})
}
//判断是否删除组别
function del(e){
	var developid = $(e).parents("tr").children().eq(1).find("input").val();
	$.ajax({
		url:"/webspring/department/deldem.do",
		type:"post",
		data:{
			developid:developid
		},
		dataType:"json",
	}).done(function(b){
		if(b.result==0){
			id=developid;
			obj=e;
			$("#mask").show();
			$("#mask_s").text(b.reason);
			$("#mask_s").css("left","16%");
			$("#mask_i").attr("onclick","delverify()");
		}else{
			var a = $(e).parents("tbody").prev("thead").find("a").text();
			a--;
			$(e).parents("tbody").prev("thead").find("a").text(a);
			$(e).parents("tr").remove();
		}
	})
}
//删除部门组
function delverify(){
	$.ajax({
		url:"/webspring/department/delverify.do",
		type:"post",
		data:{
			developid:id
		},
		dataType:"json",
	}).done(function(b){
		var a = $(obj).parents("tbody").prev("thead").find("a").text();
		a--;
		$(obj).parents("tbody").prev("thead").find("a").text(a);
		$(obj).parents("tr").remove();
		$("#mask").hide();
		$("#mask_i").attr("onclick","");
		$("#mask_s").css("left","36%");
	})
}
//添加部门，弹出部门添加框
function adddem(){
	$("#addtrue_01").attr("onclick","adddemtrue()");
	$("#adddem").show();
}
//添加部门
function adddemtrue(){
	var development = $("#add_01").val();
	var parentid =0;
	if(development=="" || development=="部门名称"){
		$("#error").text("*请输入部门名称");
		$("#error").show();
	}else{
		$.ajax({
			url:"/webspring/department/insertdem.do",
			type:"post",
			data:{
				development:development,
				parentid:parentid
			},
			dataType:"json",
		}).done(function(b){
			if(b==0){
				$("#error").show();
				$("#error").text("*该部门已存在");
			}else{
				$("#headcone_ed").children().children().eq(1).find("span").text(development);
				$("#headcone_ed").children().children().eq(1).find("input").attr("value",b);
				$("#headcone_ed").find("a").text("0");
				var a = $("#headcone_ed").html();
				$("#tabone").append("<thead class='headc'>"+a+"</thead><tbody class='bodyc'></tbody>");
				hiddem();
			}
		})
	}
}
var parent;//添加的组别所属部门
//添加组别，弹出组别添加框
function addgrp(e){
	var a = $(e).parents("tr").children().eq(1).find("span").text();
	$("#add_02").hide();
	$("#mr_02").show();
	$("#mr_02").val(a);
	$("#addtrue_03").attr("onclick","addgrptrue()");
	$("#addgroup").show();
	parent=e;
}
//添加组别
function addgrptrue(){
	var development = $("#add_03").val();
	var parentid = $(parent).parents("tr").children().eq(1).find("input").val();
	if(development=="组别名称" || development==""){
		$("#error_03").text("*请输入组别名称");
		$("#error_03").show();
	}else{
		$.ajax({
			url:"/webspring/department/insertdem.do",
			type:"post",
			data:{
				development:development,
				parentid:parentid
			},
			dataType:"json",
		}).done(function(b){
			if(b==0){
				$("#error_03").show();
				$("#error_03").text("*该部门已存在该组别");
			}else{
				$("#bodycone_ed").children().children().eq(1).find("input").attr("value",b);
				$("#bodycone_ed").children().children().eq(1).find("span").text(development);
				var a = $("#bodycone_ed").html();
				$(parent).parents("thead").next("tbody").append(a);
				var c = $(parent).parents("tr").find("a").text();
				c++;
				$(parent).parents("tr").find("a").text(c);
				hidgp();
			}
		})
	}
}