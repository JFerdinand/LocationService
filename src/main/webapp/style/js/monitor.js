var markarr = [];
var textarr = [];
var count;
//var point1 = [];
//var flag = 0;
$(document).ready(function(){
    $('p').hide();
    $('#example_1').datepicker();
    $.ajax({
        type: "POST",
        data: {
            num:1
        },
        url: "/webspring/monitor/solve.do",
        dataType:"json",
    }).done(function(b){
        //生成树结构
        layui.use('tree', function() {
            layui.tree({
                elem: '#tree', //指定Html元素
                click: function(item) { //点击节点回调
                    if(item.levelname!=null){
                        for(var i = 0;i < markarr.length;i++){
                            if(item.relng==markarr[i].getPosition().lng&&item.relat==markarr[i].getPosition().lat){
                                var infowindow = new BMap.InfoWindow(textarr[i]);
                                markarr[i].openInfoWindow(infowindow);
                            }
                        }
                    }
                    //判断时间和id是否存在，存在则传到后台
                    var time = $("#example_1").val();
                    var deviceid = item.deviceid;
                    if(time!=""&&deviceid!=null&&time!="请点击选择时间"){
                        map.clearOverlays();
                        $.ajax({
                            type: "POST",
                            data: {
                                time:time,
                                deviceid:deviceid,
                            },
                            url: "/webspring/monitor/checkpath.do",
                            dataType:"json",
                        }).done(function(b){
                            var pointarr = [];
                            if(b.list==""){
                                alert("此时间没有轨迹")
                            }
                            else{

                                for(var i = 0;i < b.list.length;i++){
                                    var point  = new BMap.Point(b.list[i].relng,b.list[i].relat);
                                    pointarr[i] = point;
                                }
//								point1 = [];
//								flag = 0;
//								drivingRoad(b.pointarr);
                                pathcheck(pointarr);
                            }
                        })
                    }
                },
                nodes: b.list
            });
        })
    })

})
function monitor(){
    $("#example_1").val("请点击选择时间")
    map.clearOverlays();
    $('p').hide(); //隐藏时间控件
    lx();
}
function fonthide(){
    var input = $("#example_1").val();
    if(input=="请点击选择时间"){
        $("#example_1").val("");
        $("#example_1").attr("style","")
    }else{
        $("example_1").attr("style","")
    }
}
/**
 * 输入框失焦默认文字显示
 */
function fontshow(){
    var input = $("#example_1").val();
    if(input==""){
        $("#example_1").val("请点击选择时间");
        $("#example_1").attr("style","color:#adadad;font-family:'黑体';font-size:16px")
    }
}
/*
 * 定时器每秒调一次方法，向前端回传设备位置信息
 */
function lx(){
    $.ajax({
        type: "POST",
        data: {
            num:1
        },
        url: "/webspring/monitor/solve.do",
        dataType:"json",
    }).done(function(b){
        map.clearOverlays();
        var index = 0;
        for (var i = 0; i < b.list.length; i++) {
            for (var j = 0; j < b.list[i].children.length; j++) {
                var listnode = b.list[i].children[j]
                for (var j2 = 0; j2 < listnode.children.length; j2++) {
                    var special=[];
                    var dtime = listnode.children[j2].dtime;
                    var development = b.list[i].name;
                    var levelname = listnode.name;
                    var dname = listnode.children[j2].name;
                    for(var j3 = 0;j3 < b.list2.length;j3++){
                        for(var j4 = 0;j4 < b.list2[j3].devicespecial.length;j4++){
                            if(listnode.children[j2].deviceid==b.list2[j3].devicespecial[j4].deviceid){
                                var str=""
                                for(var key in b.list2[j3].devicespecial[j4]){
                                    if(key!="deviceid"){
                                        str+="<tr><td>"+key+"："+b.list2[j3].devicespecial[j4][key]+"</td></tr>";
                                    }
                                }
                                special.push(str);
                            }
                        }
                    }
                    var text = "<div style='background:#ff4e5f;border:0;font-weight:bold;font-family:微软雅黑;color:#ffffff;line-height:23px;height:100%;margin:0px;padding:0px'><table><tr><td>时间："+dtime+"</td></tr><tr><td>部门："+development+"</tr></td><tr><td>分组："+levelname+"</td></tr><tr><td>设备名称："+dname+"</td></tr>"+special.join()+"</table></div>"
                    textarr.push(text)
                    var new_point = new BMap.Point(listnode.children[j2].relng,listnode.children[j2].relat);
                    var infowindow = new BMap.InfoWindow(text);
                    index++;
                    addMarker(new_point,infowindow,listnode.children[j2].dpic);
                }
            }
        }
		/*
		 *在Map上显示标记mark，点击Mark可以查看当前标记的信息。
		 */
        function addMarker(point,infowindow,dpic){

            var marker = new BMap.Marker(point);
            markarr.push(marker);
            if(dpic!=null){
                var url = dpic;
                var size = new BMap.Size(50,50);
                var icon = new BMap.Icon(url,size);
                marker.setIcon(icon);
            }
            map.addOverlay(marker);
            marker.addEventListener("click",getAttr);
            function getAttr(){
                marker.openInfoWindow(infowindow);
            }
        }
    })
    count = setTimeout(function() {
        lx()
    },1000)
}
/*
 * 取消定时器定时回传
 */
function clearsolve(){
    alert("请先选择时间")
    $('p').show()
    clearInterval(count); //取消定时器
    map.clearOverlays(); //清除覆盖物
}
/*
 * 轨迹回放功能
 */
function pathcheck(poi){
    var len=poi.length;
    // 百度地图API功能
    map.centerAndZoom(new BMap.Point(poi[0].lng,poi[0].lat), 18);
    var myP1 = new BMap.Point(poi[0].lng,poi[0].lat);    //起点
    var myP2 = new BMap.Point(poi[len-1].lng,poi[len-1].lat);//终点
    var lushu;
    var polyline = new BMap.Polyline(poi, {strokeColor:"white", strokeWeight:2, strokeOpacity:0.5});
    map.addOverlay(polyline);
    var myIcon = new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/Mario.png', new BMap.Size(32,50),{anchor : new BMap.Size(0, 0)});
    map.addOverlay(myIcon);
    map.setViewport(poi);
    lushu = new BMapLib.LuShu(map,poi,{
        defaultContent:"",//"从天安门到百度大厦"
        autoView:false,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
        speed: 200,
        enableRotation:false,//是否设置marker随着道路的走向进行旋转
        landmarkPois:[],
    });
    lushu.start();
}
/*
 * 路径规划
 */
function drivingRoad(pointarr){
    var arr = [];
    for(var i = 0;i<pointarr.length;i++){
        var Point = [];
        for(var j = 0;j<pointarr[i].length;j++){
            var split = pointarr[i][j].split(",");
            var point  = new BMap.Point(split[0],split[1]);
            Point.push(point)
        }
        baiduPlan(Point,pointarr.length)
    }
    return arr;
}
/*
 * 百度驾车规划
 */
function baiduPlan(Point,index){
    var options = {
        onSearchComplete: function(results){
            if (driving.getStatus() == BMAP_STATUS_SUCCESS){
                // 获取第一条方案
                flag++;
                var plan = results.getPlan(0);
                for(var i = 0;i<plan.getNumRoutes();i++){
                    for(var j = 0;j<plan.getRoute(i).getPath().length;j++){
                        point1.push(plan.getRoute(i).getPath()[j])
                    }
                }
                if(flag == index){
                    pathcheck(point1)
                }
            }
        }

    };
    var driving = new BMap.DrivingRoute(map, options);
    //        alert(Point[Point.length-1])
    driving.search(Point[0], Point[Point.length-1],{waypoints:Point});
}

