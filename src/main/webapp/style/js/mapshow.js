$(document).ready(function(){
	$.ajax({
		type:"post",
		data:{
			num:1
		},
		url:"/webspring/map/membershow.do",
		dataType:"json"
	}).done(function(b){
		/*
		 * 循环返回的集合，将集合中的相关信息保存
		 */
		var index = 0;
		var markarr = [];
		var textarr = [];
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
					pointarr[index] = new_point;
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
		map.setViewport(pointarr); //根据标记自适应地图
		/*
		 * 在左边显示树形结构，点击相应的节点可以定位对应标注并显示相关信息。
		 */
		layui.use('tree', function() {
			layui.tree({
				elem: '#menu', //指定Html元素
				click: function(item) { //点击节点回调
					if(item.levelname!=null){
						for(var i = 0;i < markarr.length;i++){
							if(item.relng==markarr[i].getPosition().lng&&item.relat==markarr[i].getPosition().lat){
								map.centerAndZoom(new BMap.Point(markarr[i].getPosition().lng,markarr[i].getPosition().lat),16);
								var infowindow = new BMap.InfoWindow(textarr[i]);
								markarr[i].openInfoWindow(infowindow);	
							}
						}
					}
				},
				nodes: b.list
			});
		})
	})

})
