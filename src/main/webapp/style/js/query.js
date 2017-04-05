var global; //全局变量
$(document).ready(function(){
	$("#btnone").css("backgroundColor","#00a2ca");
	$("#btnone").css("color","white");
	aclick();//页面加载时即加载此方法
	
})
/**
 * 输入框聚焦默认文字消失
 */
function fonthide(){
	var input = $("#search-two").val();
	if(input=="请输入设备名称"){
		$("#search-two").val("");
		$("#search-two").attr("style","")
	}else{
		$("#search-two").attr("style","")
	}
}
/**
 * 输入框失焦默认文字显示
 */
function fontshow(){
	var input = $("#search-two").val();
	if(input==""){
		$("#search-two").val("请输入设备名称");
		$("#search-two").attr("style","color:#adadad;font-family:'黑体';font-size:16px")
	}
}
/**
 * 点击查看标签显示地图
 **/
function aclick(){
	$("#right-concent a").click(function(){
		$("#mask").show();
		map.clearOverlays();
		var relng = $(this).attr("relng");
		var relat = $(this).attr("relat");
		var new_point = new BMap.Point(relng,relat);
		var marker = new BMap.Marker(new_point);
		map.addOverlay(marker);
		map.centerAndZoom(new_point,16);
	})
}
function reveal(e){
	if($(e).parents("thead").next("tbody").is(":hidden")){
		$(e).parents("thead").next("tbody").show();
	}else{
		$(e).parents("thead").next("tbody").hide();
	}
}
/**
 * 点击侧栏，样式改变
 * @param self
 */
function change(self) {
	$("#search-two").val("请输入设备名称")
	var c=document.getElementById(self.id);
	$(".btn").css("backgroundColor","#F0F0F0");
	$(".btn").css("color","black");
	c.style.backgroundColor="#00a2ca";
	c.style.color="white";
	$(".edit").hide();
	var e = $("#"+self.id).attr('val');
	$("#"+e).show();
	if(self.id=="btntwo"){
		$("#search-two").attr("style","color:#adadad;font-family:'黑体';font-size:16px")
		warningrecord();
	}
	else{
		window.location.reload();
	}
}
/**
 * 名称搜索功能
 */
function search(){
	var searchname=$("#search-two").val();
	$.ajax({
		type:"post",
		url:"/webspring/query/singleinfo.do",
		dataType:"json"
	}).done(function(b){
		/*
		 * 将一开始查询的数据回传
		 */
		var list = [];
		for(var i = 0;i < b.list.length;i++){
			for(var j = 0;j<b.list[i].devicespecial.length;j++){
				/*
				 * 将页面输入的名称与回传数据作比较
				 */
				if(searchname==b.list[i].devicespecial[j].设备名称){
					list.push(b.list[i].devicespecial[j]);
				}
			}
		}
		$("#right-concent").empty(); //清空div中的内容
		/*
		 * 用回传的数据开始拼接Html
		 */
		$("#right-concent").append("<table id='searchinfo'> <thead style='display:block;' id='headc'><tr></tr></thead><tbody id='bodyc' style='overflow:auto;display:block;height:300px' class='bodyc'></table>")
		for(var data in list[0]){
			if(data!='relng'&&data!='relat'&&data!='deviceid')
				$("#searchinfo tr").append("<th style='width:300px;height:35px'>"+data+"</th>");
		}
		
		for(var i = 0;i < list.length;i++){
			$(".bodyc").append("<tr></tr>")
			for(var data in list[i]){
				if(data!='relng'&&data!='relat'&&data!='deviceid')
					$("#searchinfo tr").eq(i+1).append("<td style='width:300px'>"+list[i][data]+"</td>");
			}
			$("#searchinfo tr").eq(i+1).append("<td style='width:300px'><a href='javascript:void(0)' style='background-color:#e5e5e5'  relng='"+list[i].relng+"' relat='"+list[i].relat+"'>查看</a></td>")
		}
		$("#headc tr").append("<th style='width:300px;height:35px'>位置</th>")
		aclick(); //回传之后需要再次调用a标签的点击事件，否则a标签失效
	})
	
}
/**
 * 隐藏遮罩层
 */
function maskhide(){
	$("#mask").hide();
}
/**
 * 报警统计功能
 */
function warningrecord(){
	$("#right a").attr("href","javascript:searchwarning()");
	$("#right-concent").empty();
	$.ajax({
		type:"post",
		url:"/webspring/query/warningrecord.do",
		dataType:"json"
	}).done(function(b){
		global = b;
		$("#right-concent").append("<table id='searchinfo'> <thead id='headc'><tr><th style='width:180px;height:35px'>设备名称</th><th style='width:180px;height:35px'>手机号</th><th style='width:180px;height:35px'>数值报警</th><th style='width:180px;height:35px'>报警数值</th><th style='width:180px;height:35px'>上下限</th><th style='width:180px;height:35px'>范围报警</th><th style='width:180px;height:35px'>报警时间</th></tr></thead></table>")
		for(var i = 0;i < b.list.length;i++){
			$("#searchinfo").append("<tr></tr>")
			$("#searchinfo tr").eq(i+1).append("<td>"+b.list[i].设备名称+"</td><td>"+b.list[i].手机号+"</td><td>"+b.list[i].WARNINGNAME+"</td><td>"+b.list[i].WARNINGNUM+"</td><td>("+b.list[i].DOWNLIMET+","+b.list[i].UPLIMIT+")</td><td>"+b.list[i].ARANGEWARNING+"</td><td>"+b.list[i].RECORDTIME+"</td>")
		}
	})
}
function searchwarning(){
	var dname = $("#search-two").val();
	$("#right-concent").empty();
	$("#right-concent").append("<table id='searchinfo'> <thead id='headc'><tr><th style='width:180px;height:35px'>设备名称</th><th style='width:180px;height:35px'>手机号</th><th style='width:180px;height:35px'>数值报警</th><th style='width:180px;height:35px'>报警数值</th><th style='width:180px;height:35px'>上下限</th><th style='width:180px;height:35px'>范围报警</th><th style='width:180px;height:35px'>报警时间</th></tr></thead></table>")
	for(var i = 0;i < global.list.length;i++){
		$("#searchinfo").append("<tr></tr>")
		if(dname==global.list[i].设备名称){
			$("#searchinfo tr").eq(i+1).append("<td>"+global.list[i].设备名称+"</td><td>"+global.list[i].手机号+"</td><td>"+global.list[i].WARNINGNAME+"</td><td>"+global.list[i].WARNINGNUM+"</td><td>("+global.list[i].DOWNLIMET+","+global.list[i].UPLIMIT+")</td><td>"+global.list[i].ARANGEWARNING+"</td><td>"+global.list[i].RECORDTIME+"</td>")
		}
	}
}