<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/leaflet.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/leaflet-src.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/mapshow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/Tree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/leaflet.markercluster.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/leaflet.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/leaflet.draw.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/Tree.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/MarkerCluster.Default.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/Control.MiniMap.css">
<!-- <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css?v=1.0?v=1.0" /> -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>	
<!-- <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=2aa289471dcf736c9bb2ac477cc6413e&plugin=AMap.ToolBar"></script> -->
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />    
    <script type="text/javascript">
    $(function(){

        $(".st_tree").Tree({
                    
            /* 可无视代码部分*/
            click:function(a){
            if(!$(a).attr("hasChild"))
            alert($(a).attr("ref"));
                }
                        
            });
                    
       });
    </script>	
	<style type="text/css">
		body, html,#allmap{width: 100%;height: 100%;margin:0;overflow: hidden;font-family:"微软雅黑";}
    .left{
            width:100%;
            height:100%;
            border-right: 1px solid #5BC0DE;
            z-index: 999;
            font-family:"微软雅黑";
    }
    .right{
            width:20%;
            height:100%;
            position:absolute;top:0%;left:80%;
            float:right;
            background-color:white;
            z-index:1000;
    }
     .ccc{
            width: 5%;
            height: 5%;
            position: absolute;top:50%;left:0%;
            background: url(${pageContext.request.contextPath}/style/imgs/left.gif)  no-repeat center center;       
    }
        .panneone{
            width:95%;
            height:9%;
            background-color:#337ABB;
            position: absolute;top:0%;left:5%;
    }
    .pannetwo{
            width:95%;
            height:8%;
            background-color:black;
            position: absolute;top:9%;left:5%;
    }
    .pannethree{
            width:95%;
            height:82%;
            position: absolute;top:19%;left:5%;
           
    }
    .navv{	
    		width: 100%;
    		height: 5%;
    		position: absolute;top:0%;left: 0%;
    }
    .searchh{
    		width: 100%;
    		height: 5%;                                           
            position: absolute;top:8%;left:0%;
    }
    .st_tree{
    		width: 100%;
    		height: 84%;
            position: absolute;top:16%;left: 0%;
    }
	</style>
</head>
<body>
	<div id="allmap">              
    </div>
    <!--右侧栏-->
    <div class="right">
    	  <!--滑动栏-->
        	<button class="ccc"></button>
        	<script type=text/javascript>
                    var flag=true;
        	        $(".ccc").click(function toggle() {
                    if(flag) {
                    $(".right").animate({left:'98.8%'},500);
                   // $(this).css("background":"url(imgs/right.gif)  no-repeat center center");
                    } else {
                    $(".right").animate({left:'79.6%'},500);
                    //$(this).css("background-color":"black");
                    }
                    flag = !flag;
                    })
        	</script>
    	
        <!--面板1-->
        <div class="panneone">
        </div>
        <!--面板2-->
        <div class="pannetwo">
        </div>
        <!--面板3-->
        <div class="pannethree">
            <!--面板3导航-->
            <div class="navv">
                <div class="input-group">
<!--                     <input type="text" class="form-control" value="搜索开放数据" onfocus="this.value=''" onblur="this.value='搜索'"  style="color:#ccc"> -->
<!--                     <span class="input-group-btn"> -->
<!--                     <button class="btn btn-default" type="button">Go!</button> -->
<!--                     </span> -->
                </div>
                
            </div>
            <!--面板3搜索-->
            <div class="searchh">
            		<select style=" display:none;" id="select" onchange="calc()"  name="mode">
						<option value="baidu" lng="${lng}" lat="${lat}">百度</option>
						<option value="gaode" lng="${glng}" lat="${glat}">高德</option>
					</select>
					<input type="button" value="刷新" onclick="ref()">
					<select id="selecttime">
						<option value="1" >一天以内</option>
						<option value="3" >三天以内</option>
						<option value="7" >一周以内</option>
						<option value="30" >一个月以内</option>
					</select>
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">人员管理</li>
                </ul>
            </div>
            <!--面板3文件树-->
            <div class="st_tree">
	            <c:forEach items="${voelevel}" var="c">
	                <ul class="remove" id="ul">
	                    <li><a href="#" ref="xtgl">${c.levelname}</a></li>
	                        <ul class="level" id="level"show="true" >
		                        <c:forEach items="${c.list}" var="c1">
		                        <%--这里的blng是百度坐标，后面需要用到真实坐标转换 --%>
		                            <li dtime="${c1.dtime}" deviceid="${c1.deviceid}"  levelname="${c.levelname}" bdlng="${c1.relng }" bdlat="${c1.relat }">
		                            <a href="#" ref="yhgl" >${c1.dname}</a>
		                            <input type="button" id="${c1.deviceid}" value="轨迹查看" onclick="checkpath(this.id)">
		                            </li>
		                        </c:forEach>
	                        </ul>
	                </ul>
	             </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
var map = new BMap.Map("allmap");
var lng = $("#select option:selected").attr("lng");
var lat = $("#select option:selected").attr("lat");
map.centerAndZoom(new BMap.Point(lng,lat),16);
map.enableScrollWheelZoom(true);
map.clearOverlays(); 
var new_point = new BMap.Point(lng,lat);
var marker = new BMap.Marker(new_point);
var url = "/webspring/style/js/images/mark_bs.png";
var icon = new BMap.Icon(url,BMAP_POINT_SIZE_TINY);
marker.setIcon(icon); // 创建标注
map.addOverlay(marker);              // 将标注添加到地图中
map.panTo(new_point); 
var pointarr = [];
var idarr = [];
$(".level li").each(function(i){
	var levelname = $(this).attr("levelname");
	var dname = $(this).text();
	var dtime = $(this).attr("dtime")
// 	var d = new Date($(this).attr("dtime"));
// 	var dtime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
	var text = "<div style='background:#FFFACD;border:0;height:200px;margin:0px;padding:0px'><table><tr><td>时间："+dtime+"</td></tr><tr><td>分组："+levelname+"</td></tr><tr><td>设备名称："+dname+"</td></tr></table></div>"
    var bdlng = $(this).attr("bdlng");
    var bdlat = $(this).attr("bdlat");
    var deviceid =$(this).attr("deviceid"); 
    var point = new BMap.Point(bdlng,bdlat);
    pointarr.push(point);
    idarr.push(deviceid);
    var new_point = new BMap.Point(bdlng,bdlat);
    var marker = new BMap.Marker(new_point);
    var infowindow = new BMap.InfoWindow(text);
    map.addOverlay(marker);             // 将标注添加到地图中
    marker.addEventListener("click", showInfo);
    function showInfo(){
    	marker.openInfoWindow(infowindow);
    }    
})


function ref(){
	window.location.reload(); 
}
</script>