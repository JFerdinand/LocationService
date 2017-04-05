var list;//部门组列表
var deviceList;//设备列表
$(function(){
	//设备
	$("#bodyctwo_ed").hide();
	$("#btntwo").on("click",function(){
		$("#bodyctwo").children().remove();
		$.ajax({
			url:"/webspring/device/devicemanager.do",
			type:"post",
			data:null,
			dataType:"json",
		}).done(function(b){
			//展示设备信息
			for(var i=0;i<b.deviceList.length;i++){
				$("#bodyctwo_ed").children().children().eq(1).find("span").text(b.deviceList[i].DNAME);
				$("#bodyctwo_ed").children().children().eq(1).find("input").attr("value",b.deviceList[i].DEVICEID);
				$("#bodyctwo_ed").children().children().eq(2).find("span").text(b.deviceList[i].DINFOR);
				$("#bodyctwo_ed").children().children().eq(3).find("span").text(b.deviceList[i].TYPENAME);
				$("#bodyctwo_ed").children().children().eq(3).find("input").attr("value",b.deviceList[i].TYPEID);
				$("#bodyctwo_ed").children().children().eq(4).find("span").text(b.deviceList[i].ment);
				$("#bodyctwo_ed").children().children().eq(4).find("input").attr("value",b.deviceList[i].mentid);
				if(typeof(b.deviceList[i].develname)=="undefined"){
					$("#bodyctwo_ed").children().children().eq(5).find("span").text("");
					$("#bodyctwo_ed").children().children().eq(5).find("input").attr("value","");
				}else{
					$("#bodyctwo_ed").children().children().eq(5).find("span").text(b.deviceList[i].develname);
					$("#bodyctwo_ed").children().children().eq(5).find("input").attr("value",b.deviceList[i].develid);
				}
				var a = $("#bodyctwo_ed").html();
				$("#bodyctwo").append(a);
			}
			aberrationD();
			//设置设备类型下拉列表
			$(".type_13").remove();
			for(var j=0;j<b.deviceTypeList.length;j++){
				var c="<option class='type_13' val='"+b.deviceTypeList[j].typeid+"'>"+b.deviceTypeList[j].typename+"</option>";
				$("#add_13").append(c);
			}
			//设置部门下拉列表
			$(".depm_13").remove();
			for(var k=0;k<b.developmentList.length;k++){
				var d="<option class='depm_13' val='"+b.developmentList[k].developid+"'>"+b.developmentList[k].development+"</option>";
				$("#add_14").append(d);
			}
			deviceList=b.deviceList;
			list=b.developmentList;
		})
	})
	$("#add_11").focus(function(){
		$("#error_11").hide();
		if($(this).val()=="设备名称"){
			$(this).css("color","");
			$(this).val("");
		}
	})
	$("#add_11").blur(function(){
		if($(this).val()==""){
			$(this).css("color","#cccccc");
			$(this).val("设备名称");
		}
	})
	$("#add_12").focus(function(){
		if($(this).val()=="设备信息"){
			$(this).css("color","");
			$(this).val("");
		}
	})
	$("#add_12").blur(function(){
		if($(this).val()==""){
			$(this).css("color","#cccccc");
			$(this).val("设备信息");
		}
	})
	$("#add_13").focus(function(){
		$("#error_13").hide();
		$(this).css("color","");
	})
	$("#add_13").blur(function(){
		if($('#add_13 option:selected').text()=="-请选择设备类型-"){
			$(this).css("color","#cccccc");
		}else{
			var e =$('#add_13 option:selected').attr("val");
			$("#id_13").val(e);
		}
	})
	$("#add_14").focus(function(){
		$("#error_14").hide();
		$(this).css("color","");
	})
	$("#add_14").blur(function(){
		if($('#add_14 option:selected').text()=="-请选择部门-"){
			$(this).css("color","#cccccc");
		}else{
			var f =$('#add_14 option:selected').attr("val");
			$("#id_14").val(f);
		}
	})
	$("#add_14").change(function(){
		$(".op_15").remove();
		$('#add_15 option:selected').text("-请选择组别-");
		$("#add_15").css("color","#cccccc");
	})
	$("#add_15").focus(function(){
		$(this).css("color","");
		$(".op_15").remove();
		if($('#add_14 option:selected').text()!="-请选择部门-"){
			var parentid = $('#add_14 option:selected').attr("val");
			//设置组别下拉列表
			for(var i=0;i<list.length;i++){
				if(list[i].developid==parentid){
					for(var j=0;j<list[i].developmentList.length;j++){
						var d="<option class='op_15' val='"+list[i].developmentList[j].developid+"'>"+list[i].developmentList[j].development+"</option>";
						$("#add_15").append(d);
					}
				}
			}
		}else{
			$("#error_14").show();
			$("#error_14").text("*请选择部门");
		}
	})
	$("#add_15").blur(function(){
		if($('#add_15 option:selected').text()=="-请选择组别-"){
			$(this).css("color","#cccccc");
		}else{
			var g =$('#add_15 option:selected').attr("val");
			$("#id_15").val(g);
		}
	})
})
//搜索设备
function searchD(){
	var dname = $("#search-one").val();
	$.ajax({
		url:"/webspring/device/searchdevice.do",
		type:"post",
		data:{
			dname:dname
		},
		dataType:"json",
	}).done(function(b){
		$("#bodyctwo").children().remove();
		for(var i=0;i<b.deviceList.length;i++){
			$("#bodyctwo_ed").children().children().eq(1).find("span").text(b.deviceList[i].DNAME);
			$("#bodyctwo_ed").children().children().eq(1).find("input").val(b.deviceList[i].DEVICEID);
			$("#bodyctwo_ed").children().children().eq(2).find("span").text(b.deviceList[i].DINFOR);
			$("#bodyctwo_ed").children().children().eq(3).find("span").text(b.deviceList[i].TYPENAME);
			$("#bodyctwo_ed").children().children().eq(3).find("input").val(b.deviceList[i].TYPEID);
			$("#bodyctwo_ed").children().children().eq(4).find("span").text(b.deviceList[i].ment);
			$("#bodyctwo_ed").children().children().eq(4).find("input").val(b.deviceList[i].mentid);
			$("#bodyctwo_ed").children().children().eq(5).find("span").text(b.deviceList[i].develname);
			$("#bodyctwo_ed").children().children().eq(5).find("input").val(b.deviceList[i].develid);
			var a = $("#bodyctwo_ed").html();
			$("#bodyctwo").append(a);
		}
	})
}
//显示设备添加框
function addD(){
	$("#title_11").text("添加设备");
	$("#adddev").show();
	
}
//显示设备修改框
var obj;
function updateD(i){
	obj=i;
	var a,b,c,d,e,f,g,h,j;
	a = $(i).parents("tr").children().eq(1).find("span").text();
	b = $(i).parents("tr").children().eq(1).find("input").val();
	c = $(i).parents("tr").children().eq(2).find("span").text();
	d = $(i).parents("tr").children().eq(3).find("span").text();
	e = $(i).parents("tr").children().eq(3).find("input").val();
	f = $(i).parents("tr").children().eq(4).find("span").text();
	g = $(i).parents("tr").children().eq(4).find("input").val();
	h = $(i).parents("tr").children().eq(5).find("span").text();
	j = $(i).parents("tr").children().eq(5).find("input").val();
	$("#add_11").val(a);
	$("#id_11").val(b);
	$("#add_11").css("color","");
	$("#add_12").val(c);
	$("#add_12").css("color","");
	$("#add_13").val(d);
	$("#id_13").val(e);
	$("#add_13").css("color","");
	$("#add_14").val(f);
	$("#id_14").val(g);
	$("#add_14").css("color","");
	if(h!=""){
		for(var i=0;i<list.length;i++){
			if(list[i].developid==g){
				for(var k=0;k<list[i].developmentList.length;k++){
					var d="<option class='op_15' val='"+list[i].developmentList[k].developid+"'>"+list[i].developmentList[k].development+"</option>";
					$("#add_15").append(d);
				}
			}
		}
		$("#add_15").val(h);
		$("#id_15").val(j);
		$("#add_15").css("color","");
	}
	$("#title_11").text("修改设备");
	$("#adddev").show();
}
//关闭设备添加或修改框
function cancel(){
	$("#add_11").val("设备名称");
	$("#id_11").val("");
	$("#add_11").css("color","#cccccc");
	$("#add_12").val("设备信息");
	$("#add_12").css("color","#cccccc");
	$("#add_13").val("-请选择设备类型-");
	$("#id_13").val("");
	$("#add_13").css("color","#cccccc");
	$("#add_14").val("-请选择部门-");
	$("#id_14").val("");
	$("#add_14").css("color","#cccccc");
	$("#add_15").val("-请选择组别-");
	$("#id_15").val("");
	$(".op_15").remove();
	$("#add_15").css("color","#cccccc");
	$(".error").hide();
	$("#adddev").hide();
}
//修改或添加确定
function ensure(){
	var a = $("#add_11").val();
	var b = $("#id_11").val();
	var c = $("#add_12").val();
	var d = $("#id_13").val();
	var e = $("#id_14").val();
	var f = $("#id_15").val();
	var g = $("#add_13").val();
	var h = $("#add_14").val();
	var j = $("#add_15").val();
	for(var i=0;i<deviceList.length;i++){
		if(deviceList[i].DEVICEID!=b && deviceList[i].DNAME==a && deviceList[i].TYPEID==d
		   && deviceList[i].mentid==e && deviceList[i].develid==f){
			$("#error_11").show();
			$("#error_11").text("*设备已存在，请重新输入设备名称");
			break;
		}
		if(deviceList[i].DEVICEID!=b && deviceList[i].DNAME==a && deviceList[i].TYPEID==d
		   && deviceList[i].mentid==e){
			$("#error_11").show();
			$("#error_11").text("*设备已存在，请重新输入设备名称");
			break;
		}
	}
	if($("#error_11").is(":hidden") && $("#error_13").is(":hidden") && $("#error_14").is(":hidden")){
		if(a=="设备名称"){
			$("#error_11").show();
			$("#error_11").text("*请输入设备名称");
		}else if(g=="-请选择设备类型-"){
			$("#error_13").show();
			$("#error_13").text("*请选择设备类型");
		}else if(h=="-请选择部门-"){
			$("#error_14").show();
			$("#error_14").text("*请选择设备所属部门");
		}else{
			if(c=="设备信息"){
				c="";
			}
			if(j=="-请选择组别-"){
				f="";
				j="";
			}
			if(f==""){
				f=e;
			}
			if(b==""){
				$.ajax({
					url:"/webspring/device/insertdevice.do",
					type:"post",
					data:{
						dname:a,
						dinfor:c,
						typeid:d,
						developid:f
					},
					dataType:"json",
				}).done(function(t){
					if(t.result==1){
						$("#bodyctwo_ed").children().children().eq(1).find("span").text(a);
						$("#bodyctwo_ed").children().children().eq(1).find("input").attr("value",t.deviceid);
						$("#bodyctwo_ed").children().children().eq(2).find("span").text(c);
						$("#bodyctwo_ed").children().children().eq(3).find("span").text(g);
						$("#bodyctwo_ed").children().children().eq(3).find("input").attr("value",d);
						$("#bodyctwo_ed").children().children().eq(4).find("span").text(h);
						$("#bodyctwo_ed").children().children().eq(4).find("input").attr("value",e);
						$("#bodyctwo_ed").children().children().eq(5).find("span").text(j);
						$("#bodyctwo_ed").children().children().eq(5).find("input").attr("value",f);
						var s = $("#bodyctwo_ed").html();
						$("#bodyctwo").append(s);
						cancel();
						aberrationD();
					}else{
						$("#error_11").show();
						$("#error_11").text("*设备已存在，请重新输入设备名称");
					}
				})
			}else{
				$.ajax({
					url:"/webspring/device/updatedevice.do",
					type:"post",
					data:{
						deviceid:b,
						dname:a,
						dinfor:c,
						typeid:d,
						developid:f
					},
					dataType:"json",
				}).done(function(result){
					if(result==1){
						$(obj).parents("tr").children().eq(1).find("span").text(a);
						$(obj).parents("tr").children().eq(2).find("span").text(c);
						$(obj).parents("tr").children().eq(3).find("span").text(g);
						$(obj).parents("tr").children().eq(3).find("input").val(d);
						$(obj).parents("tr").children().eq(4).find("span").text(h);
						$(obj).parents("tr").children().eq(4).find("input").val(e);
						$(obj).parents("tr").children().eq(5).find("span").text(j);
						$(obj).parents("tr").children().eq(5).find("input").val(f);
						cancel();
					}else{
						alert("修改失败");
					}
				})
			}
		}
	}
}
var objList=[];//选中删除的设备标签对象
var dList = [];//选中删除的设备ID
function delD(){
	dList = [];
	objList=[];
		$(".chetwo").each(function(){
			if($(this).is(':checked')){
				var a = $(this).parents("tr").children().eq(1).find("input").val();
				dList.push(a);
				var b = $(this).parents("tr");
				objList.push(b);
			}
		})
		if(dList!=""){
			$("#mask").show();
			$("#mask_s").text("是否删除？");
			$("#mask_s").css("left","38%");
			$("#mask_i").attr("onclick","delDevice()");
			
		}	
}
//删除设备
function delDevice(){
	$.ajax({
		url:"/webspring/device/deldevice.do",
		type:"post",
		data:{
			dList:dList.join()
		},
		dataType:"json",
	}).done(function(t){
		for(var i=0;i<objList.length;i++){
			$(objList[i]).remove();
		}
		$("#mask").hide();
		$("#mask_i").attr("onclick","");
		aberrationD();
	})
}
function aberrationD(){
	var a = 0;
	$(".trtwo").each(function(){
		if(a==0){
			$(this).attr("style","");
			a++;
		}else{
			$(this).attr("style","background-color:#f2f2f2;");
			a=0;
		}
	})
}