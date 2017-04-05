/**
 * 全局变量
 */
var device; 
var covering;
var coveringshow;
var tools;

$(document).ready(function(){
	$("button").click(function(){
		var id  = $(this).attr("id")
		if(id!="btnfour"){
			$("#alarm").hide();
		}
		else{
			$("#alarm").slideToggle();
			map.clearOverlays();
			$(".BMapLib_Drawing_panel").empty();
			rangeshow();
		}
	});
})
/**
 * 从后台将设备读取出来
 */
function showdevice(){
	$.ajax({
		type:"post",
		url:"/webspring/setalarm/deviceinfo.do",
		dataType:"json"
	}).done(function(b){
		device = b.list;
	})
}
/**
 * 范围警告显示
 */
function rangealarm(){
	var myDrawingManagerObject = new BMapLib.DrawingManager(map, {isOpen: false, enableDrawingTool: true,
	    enableCalculate: false,
	    drawingToolOptions: {
	        anchor: BMAP_ANCHOR_TOP_LEFT,
	        offset: new BMap.Size(5, 5),
	        drawingModes : [
	            BMAP_DRAWING_CIRCLE,
	            BMAP_DRAWING_POLYGON,
	         ]
	    },
	    circleOptions: {
	        strokeColor:"red",    //边线颜色。
	        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
	        strokeWeight: 3,       //边线的宽度，以像素为单位。
	        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
	        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
	        strokeStyle: 'solid' //边线的样式，solid或dashed。
	        	},
		polygonOptions: {
		    strokeColor:"red",  
		    fillColor:"red",      
		    strokeWeight: 3,       
		    strokeOpacity: 0.8,	  
		    fillOpacity: 0.6,      
		    strokeStyle: 'solid' 
		    	},
	    });
	myDrawingManagerObject.addEventListener("overlaycomplete", function(e) {
		//创建右键菜单
		covering = e;
//		var con = confirm("是否确认绘制路线");
//		if(con==true){
//			myDrawingManagerObject.close();
//		}
//		else{
//			map.removeOverlay(e.overlay);
//			myDrawingManagerObject.close();
//		}
		$("#setalarm").empty()
		$("#setalarm").show();
		$("#setalarm").append("<div id='deviceinfo' style='width:28%;height:44%;position:absolute;top:32%;left:35%;background-color:white;border-radius:10px'></div>")
		$("#deviceinfo").append("<div id='title_01' style='width:100%;position:absolute;top:0%;left:0%;background-color:#00a2ca;color:white;font-family:黑体;font-size:16px;text-align:center;line-height:40px;border-radius:10px 10px 0px 0px'>选择设备</div>")
		$("#deviceinfo").append("<div style='overflow:auto;top:10%;position:absolute;width:100%;height:70%'><table id='table'><tbody style='text-align:left'></tbody></table></div>")
		for(var i = 0;i < device.length;i++){
			$("#table").append("<tr><td><input value="+device[i].deviceid+" class='checkbox' type='checkbox'>"+device[i].dname+"</td></tr>")
		}
		$("#deviceinfo").append("<input id='addtrue_01' type='submit' onclick='confirm()' value='确定' style='background-color:#00a2ca;position:absolute;left:27%;bottom:10%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:黑体;font-size: 15px;'><input type='submit' onclick='infocancel()'value='取消' style='background-color:#00a2ca;position:absolute;left:57%;bottom:10%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:黑体;font-size: 15px;'>");
		myDrawingManagerObject.close();
	})	
}
/**
 * 绘制完成点击确定将所画覆盖物存到数据库
 */
function confirm(){
	var pointarr = [];
	var deviceidarr = [];
	var type,lng,lat,radius;
	var ischecked = $("#table .checkbox");
	var count=ischecked.length;
	for(var i  = 0;i < ischecked.length;i++){
		if(ischecked[i].checked){
			deviceidarr.push(ischecked[i].value) //将设备id存起来
			count--;
		}
	}
	/*
	 * 判断设备是否选择
	 */
	if(count==ischecked.length){
		alert("请选择设备后确定")
	}else{
		$("#setalarm").hide();
		if(covering.drawingMode=="polygon"){
			type=1;
			for(var i = 0;i<covering.overlay.getPath().length;i++){
				var lnglat = "("+covering.overlay.getPath()[i].lng+","+covering.overlay.getPath()[i].lat+")"
				pointarr.push(lnglat);
			}
		}
		else if(covering.drawingMode=="circle"){
			type=0;
			lng = covering.overlay.getCenter().lng;
			lat = covering.overlay.getCenter().lat;
			radius = covering.overlay.getRadius();
		}
		$.ajax({
			type:"post",
			data:{
				type:type,
				lng:lng,
				lat:lat,
				radius:radius,
				pointarr:pointarr.join(),
				deviceid:deviceidarr.join()
			},
			url:"/webspring/setalarm/saveRalarm.do",
			dataType:"json"
		}).done(function(b){
			alert(b.result)
		})
	}
}
/**
 * 取消事件
 */
function infocancel(){
	$("#setalarm").hide();
	map.removeOverlay(covering.overlay);
}
/**
 * 范围显示
 */
function rangeshow(){
	$.ajax({
		type:"post",
		url:"/webspring/setalarm/rangeshow.do",
		dataType:"json"
	}).done(function(b){
		/*
		 * 多边形范围显示
		 */
		if(b.polyRange!=""){
			for(var i=0;i<b.polyRange.length;i++){
				var pointstr = [];
				var pointarr = [];
				pointstr = b.polyRange[i].pointarr.substring(1,b.polyRange[i].pointarr.length-1).split("),(");
				for(var j=0;j<pointstr.length;j++){
					var split = pointstr[j].split(",");
					var point = new BMap.Point(split[0],split[1]);
					pointarr.push(point);
				}
				var Polgon = new BMap.Polygon(pointarr);
				Polgon.setStrokeColor("red");
				map.addOverlay(Polgon);
				rightclick(Polgon,b.polyRange[i].polygonid,1); 
				
			}
		}
		/*
		 * 圆形范围显示
		 */
		else if(b.circleRange!=""){
			for(var i=0;i<b.circleRange.length;i++){
				var point = new BMap.Point(b.circleRange[i].lng,b.circleRange[i].lat);
				var Circle = new BMap.Circle(point,b.circleRange[i].radius)
				Circle.setStrokeColor("red"); 
				map.addOverlay(Circle);
				rightclick(Circle,b.circleRange[i].circleid,0);
			}
		}
	})
}
/**
 * 右键菜单
 * 将polygonid和类型传入（0为圆形，1为多边形）
 */
function rightclick(overlay,id,typeid){
	var markerMenu=new BMap.ContextMenu();
	markerMenu.addItem(new BMap.MenuItem('删除',function(){
		map.removeOverlay(overlay);
		$.ajax({
			type:"post",
			data:{
				id:id,
				typeid:typeid
			},
			url:"/webspring/setalarm/deletealarm.do",
			dataType:"json"
		})
	}));
	overlay.addContextMenu(markerMenu)
}
/**
 * 区域报警设置
 */
function rangeset(){
	rangealarm();
	showdevice();
}