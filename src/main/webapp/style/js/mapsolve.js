function refresh(type){
	switch(type){
	case 1:
		$("#show").attr("src","/webspring/map/show.do");
		break;
	case 2:
		$("#show").attr("src","/webspring/monitor/show.do");
		break;
	case 3:
		$("#show").attr("src","/webspring/allocation/manage.do");
		break;
	case 5:
		$("#show").attr("src","/webspring/query/deviceinfo.do");
		break;	
	}
}
function draw(type){
	var myDrawingManagerObject = new BMapLib.DrawingManager(map, {isOpen: true, enableDrawingTool: false,
	    enableCalculate: false,
        circleOptions: {
	        strokeColor:"red",    //边线颜色。
	        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
	        strokeWeight: 3,       //边线的宽度，以像素为单位。
	        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
	        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
	        strokeStyle: 'solid' //边线的样式，solid或dashed。
	        	},
        polylineOptions: {
		    strokeColor:"red",    
		    fillColor:"red",      
		    strokeWeight: 3,      
		    strokeOpacity: 0.8,	  
		    fillOpacity: 0.6,      
		    strokeStyle: 'solid' 
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
	myDrawingManagerObject.setDrawingMode(type);
	myDrawingManagerObject.addEventListener("overlaycomplete", function(e) {
		//创建右键菜单
		var markerMenu=new BMap.ContextMenu();
		markerMenu.addItem(new BMap.MenuItem('删除',function(){
			map.removeOverlay(e.overlay);
		}));
		e.overlay.addContextMenu(markerMenu)
		var con = confirm("是否确认绘制路线");
		if(con==true){
			myDrawingManagerObject.close();
		}
		else{
			map.removeOverlay(e.overlay);
			myDrawingManagerObject.close();
		}
	})
}
function distance(){
	var myDis = new BMapLib.DistanceTool(map);
	myDis.open();
}
function locationx(){
	$.ajax({
		type:"post",
		data:{
			num:1
		},
		url:"/webspring/map/location.do",
		dataType:"json"
	}).done(function(b){
		map.centerAndZoom(new BMap.Point(b.lng, b.lat),16);
		var new_point = new BMap.Point(b.lng, b.lat);
		var marker = new BMap.Marker(new_point);
		var url = "/webspring/style/js/images/mark_bs.png";
		var icon = new BMap.Icon(url,BMAP_POINT_SIZE_TINY);
		marker.setIcon(icon); // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		map.panTo(new_point); 
	})
}
//function showallmark(type){
//	$(".loopcontent li").each(function(i){
//		var levelname = $(this).attr("levelname");
//		var dname = $(this).attr("dname");
//		var dtime = $(this).attr("dtime");
//		var text = "<div style='background:#FFFACD;border:0;height:100%;margin:0px;padding:0px'><table><tr><td>时间："+dtime+"</td></tr><tr><td>分组："+levelname+"</td></tr><tr><td>设备名称："+dname+"</td></tr></table></div>"
//	    var bdlng = $(this).attr("bdlng");
//	    var bdlat = $(this).attr("bdlat");
//		var new_point = new BMap.Point(bdlng,bdlat);
//		var marker = new BMap.Marker(new_point);
//	    var infowindow = new BMap.InfoWindow(text);
//	    marker.openInfoWindow(infowindow);
//	})
//}
