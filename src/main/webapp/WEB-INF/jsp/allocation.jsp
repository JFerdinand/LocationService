<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>配置管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/allocation/department.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/allocation/userauthority.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/allocation/device.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/alarm.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />  
	<style type="text/css">
		body,html{
			margin:0;
            padding:0;
            width:100%;
            height:100%;
            overflow-x:hidden;
		}
		#main{
			width:100%;
			height:100%;
			position:relative;
		}
		#left{
			width:10%;
			height:100%;
			background-color:#e8e8e8;
		}
		#department,#terminal,#power,#warning,#sampling,#mapedit{
			width:90%;
			height:100%;
			background-color: white;
			position: absolute;
			left:10%;
			top:0%;
		}
		.btn{
			width:7.5%;
			height:3.38%;
			border-radius: 10px;
			position: absolute;
			left:1.2%;
			font-family: "幼圆";
			font-size: 14px;
			border: none;
		}
		#btnone{
			top:4.12%;
		}
		#btntwo{
			top:11.62%;
		}
		#btnthree{
			top:19.12%;
		}
		#btnfour{
			top:26.62%;
		}
		#btnfive{
			top:34.12%;
		}
		#btnsix{
			top:41.62%;
		}
		#btnseven{
			top:49.12%;
		}
		
		#search-one{
			width: 17%;
			height: 2.5%;
			position: absolute;
			top:2%;
			right:10%;
			border-radius: 5px;
			background-color: #e8e8e8;
			border: none;
		}		
		#search-three{
			width: 17%;
			height: 2.5%;
			position: absolute;
			top:2%;
			right:10%;
			border-radius: 5px;
			background-color: #e8e8e8;
			border: none;
		}
		#search-oneImg{
			position: absolute;
			top:2.4%;
			right:10%;
		}
		#btnsearch{
			position: absolute;
			left:5%;
			top:1.7%;
			border-radius:10px;
			background-color:#242b31;
			width: 5.5%;
			height: 3.18%;
			font-family: "黑体";
			color: white;
			border:none;
		}
		#btnadd{
			position: absolute;
			left:5%;
			top:1.7%;
			border-radius:10px;
			background-color:#242b31;
			width: 5.5%;
			height: 3.18%;
			font-family: "黑体";
			color: white;
			border:none;
		}
		#btndel{
			position: absolute;
			left:12%;
			top:1.7%;
			border-radius:10px;
			background-color:#242b31;
			width: 5.5%;
			height: 3.18%;
			font-family: "黑体";
			color: white;
			border:none;
		}
		th,span{
			font-size: 12px;
			font-family: "黑体";
			font-weight: normal;
		}
		#right-concent{
			width: 100%;
			position: absolute;
			top:6%;
			left:0%;
		}
		table{
			width:100%;
			position: absolute;
			
		}
		thead{
			background-color:#ececec;
		}
		tbody{
			text-align: center;
		}
		.add_in:focus{
			transition:border linear .2s,box-shadow linear .5s;
			-moz-transition:border linear .2s,-moz-box-shadow linear .5s;
			-webkit-transition:border linear .2s,-webkit-box-shadow linear .5s;
			outline:none;border-color:#54a8ea; 
			box-shadow:0 0 6px #54a8ea; 
			-moz-box-shadow:0 0 6px #54a8ea;
			-webkit-box-shadow:0 0 6px #54a8ea;
			}
		a:link {
			font-size: 12px;
			color: #000000;
			text-decoration: none;
			}
			a:visited {
			font-size: 12px;
			color: #000000;
			text-decoration: none;
			}
			a:hover {
			font-size: 12px;
			color: #999999;
			text-decoration: underline;
		} 
	</style>
</head>
<body>
	<div id="main">
		<div id="left">
			<button id="btnone" onclick="change(this)" class="btn" val="department">部门管理</button>
			<button id="btntwo" onclick="change(this)" class="btn" val="terminal">终端管理</button>
			<button id="btnthree" onclick="change(this)"  class="btn" val="power">权限管理</button>
			<button id="btnfour" onclick="change(this)"  class="btn" val="warning">报警管理</button>
			<div id="alarm" style="left: 3.0%; top: 30%;position: absolute;width: 7.5%;height: 3.38%;display:none">
			<span style="display: inline-block;font-size: 12px;">
			<a href="javascript:rangeset()">区域报警设置</a>
			</span>

			</div>
			<button id="btnfive" onclick="change(this)"  class="btn" val="sampling">采样设置</button>
			<button id="btnsix" onclick="change(this)"  class="btn" val="mapedit">地图编辑</button>
		</div>
		<!--部门管理 -->
		<div id="department" class="edit">
			<button id="btnadd" onclick="adddem()">+添加</button>
			<div id="right-concent">
				<table border="0" id="tabone">
					
					<thead id="headcone_ed">
						<tr>
							<th onclick="reveal(this)" style="width:180px;height:35px"><img src="${pageContext.request.contextPath}/style/bjimgs/35.png" alt=""></th>
							<th onclick="reveal(this)" style="width:120px;height:35px"><span></span><input type="text" style="display:none;" value=""/></th>
							<th onclick="reveal(this)" style="width:120px;height:35px"><span>共<a>0</a>个组别</span></th>
							<th>
								<button class="seetb"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none" onclick="addgrp(this)" >添加</button>
								<button class="seetb"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none" onclick="editdem(this)" >编辑</button>
								<button class="seetb"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none" onclick="deldmp(this)" >删除</button>
							</th>
						</tr>
					</thead>
					<tbody id="bodycone_ed">
						<tr>
							<td></td>
							<td><span></span><input  type="text" value="" style="display:none;"/></td>
							<td></td>
							<td>
								<button class="seetc"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none"onclick="editgroup(this)" >编辑</button>
								<button class="seetc"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none"onclick="del(this)" >删除</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 终端管理 -->
		<div id="terminal" class="edit">
			<input type="text" id="search-one"/>
			<img id="search-oneImg" onclick="searchD()" src="${pageContext.request.contextPath}/style/bjimgs/36.png"  width="18px" >
			<button id="btnsearch" onclick="addD()">+添加</button>
			<button id="btndel" onclick="delD()">删除</button>
			<div id="right-concent">
				<table border="0">
					<thead class="headc">
						<tr>
							<th  style="width:180px;height:35px"><img src="${pageContext.request.contextPath}/style/bjimgs/35.png" alt=""></th>
							<th  style="width:120px;height:35px">设备名称</th>
							<th  style="width:120px;height:35px">设备信息</th>
							<th  style="width:120px;height:35px">类型</th>
							<th  style="width:120px;height:35px">部门</th>
							<th  style="width:120px;height:35px">组别</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="bodyctwo">
					</tbody>
					<tbody id="bodyctwo_ed">
						<tr class="trtwo">
							<td><input class="chetwo" type="checkbox"/></td>
							<td><span></span><input  type="text" value="" style="display:none;"/></td>
							<td><span></span></td>
							<td><span></span><input  type="text" value="" style="display:none;"/></td>
							<td><span></span><input  type="text" value="" style="display:none;"/></td>
							<td><span></span><input  type="text" value="" style="display:none;"/></td>
							<td>
								<button class="seetc"  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none" onclick="updateD(this)" >编辑</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 权限管理 -->
		<div id="power" class="edit">
		    <input type="text" id="search-three" onfocus="setStyle()" onblur="getStyle()" value="请输入你要查找的用户名"/>
			<img id="search-oneImg" onclick="searchUser()" src="${pageContext.request.contextPath}/style/bjimgs/36.png"  width="18px" >
			<div id="right-concent">
			<table border="0">
				<thead>
					<tr>
						<th style="width:180px;height:35px"><img src="${pageContext.request.contextPath}/style/bjimgs/35.png" alt=""></th>
						<th style="width:180px;height:35px;font-size: 16px;"><span>用户名称</span></th>
						<th style="width:180px;height:35px;font-size: 16px;"><span>用户权限</span></th>
						<th></th>
					</tr>
				</thead>
				<tbody id="bodycthree">						
					<tr id="tr_30">
						<td></td>
						<td></td>
						<td>
						    <span></span>
						    <select class="test" style="display:none">						 
							    <option val="2">管理员</option>
							    <option val="3">普通用户</option>
						    </select>
						</td>
						<td>
							<button  style="font-size: 13px;font-family: '黑体';border-radius:10px;background-color:#e5e5e5;border:none" onclick="editpower(this)">编辑</button>
						</td>
					</tr>						
				</tbody>
			</table>
		  </div>
		</div>
		<!-- 报警管理 -->、
		<div id="warning" class="edit"></div>
		<!-- 采样设置-->
		<div id="sampling" class="edit"></div>
		<!-- 地图编辑 -->
		<div id="mapedit" class="edit"></div>
	</div>
		<!-- 遮罩 -->
		<div  id="mask" style="display:none;width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000">
			<div style="width:23%;height:18%;position:absolute;top:38%;left:38%;background-color:white;border-radius:15px">
				<a id="mask_s" style="display:inline-block;position:absolute;top:23%;left:36%;font-family:'黑体';">是否删除？</a>
				<input id="mask_i" type="button" onclick="" value="确定" style="display: inline-block;position:absolute;bottom:23%;left:25%;font-family:'黑体';color:white;background-color:#242b31;border-radius:5px;font-size:15px;border:none;line-height:22px;"/>
				<input type="button" onclick="hid()" value="取消" style="display: inline-block;position:absolute;bottom:23%;left:57%;font-family:'黑体';color:white; background-color:#242b31;border-radius:5px;font-size:15px;border:none;line-height:22px;"/>
			</div>
		</div>
		<!-- 遮罩 -->
		<!-- 添加部门 -->
		<div  id="adddem" style="display:none;width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000">
			<div style="width:28%;height:22%;position:absolute;top:32%;left:35%;background-color:white;border-radius:10px">
				<div id="title_01" style="width:100%;position:absolute;top:0%;left:0%;background-color:#242b31;color:white;font-family:'黑体';font-size: 16px; text-align: center;       
				 line-height: 40px;   border-radius:10px 10px 0px 0px">添加部门</div>
					<input id="add_01" class="add_in" type="text" style="width:55%;height:15%;position:absolute;top:35%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;" value="部门名称">
					<input id="id_01" type="text" style="display:none;">
					<span id="error" style="width:55%;height:15%;position:absolute;top:50%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					<input id="addtrue_01" type="submit" onclick="" value="确定" style="background-color:#242b31;position:absolute;left:27%;bottom:10%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
					<input type="submit" onclick="hiddem()" value="取消" style="background-color:#242b31;position:absolute;left:57%;bottom:10%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
			</div>
		</div>
		<!--新加的——添加部门 -->
		<!-- 添加组别 -->
		<div  id="addgroup" style="display:none;width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000">
			<div style="width:28%;height:28%;position:absolute;top:32%;left:35%;background-color:white;border-radius:10px">
				<div id="title_02" style="width:100%;position:absolute;top:0%;left:0%;background-color:#242b31;color:white;font-family:'黑体';font-size: 16px; text-align: center;       
				 line-height: 40px;   border-radius:10px 10px 0px 0px">添加组别</div>
					<select id="add_02" class="add_in" type="text" style="width:55%;height:12%;position:absolute;top:28%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;">
					<option selected="selected">-请选择部门-</option>
					</select>
					<input id="mr_02" type="text" disabled="true" style="width:55%;height:12%;position:absolute;top:28%;left:24%;background-color:#f0f0f0;border:none;">
					<span id="error_02" style="width:55%;height:12%;position:absolute;top:40%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					<input id="add_03" class="add_in" type="text" style="width:55%;height:12%;position:absolute;top:52%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;" value="组别名称">
					<input id="id_03" type="text" style="display:none;">
					<span id="error_03" style="width:55%;height:12%;position:absolute;top:64%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					<input id="addtrue_03" type="submit" onclick="" value="确定" style="background-color:#242b31;position:absolute;left:27%;bottom:7%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
					<input type="submit" onclick="hidgp()" value="取消" style="background-color:#242b31;position:absolute;left:57%;bottom:7%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
			</div>
		</div>
		<!--新加的——添加组别 -->
		<!-- 添加设备 -->
		<div  id="adddev" style="display:none;width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000">
			<div style="width:28%;height:45%;position:absolute;top:18%;left:35%;background-color:white;border-radius:10px">
				<div id="title_11" style="width:100%;position:absolute;top:0%;left:0%;background-color:#242b31;color:white;font-family:'黑体';font-size: 16px; text-align: center;       
				 line-height: 40px;   border-radius:10px 10px 0px 0px">添加设备</div>
					<input id="add_11" class="add_in" type="text" style="width:55%;height:7%;position:absolute;top:15%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;" value="设备名称">
					<input id="id_11" type="text" value="" style="display:none;"/>
					<span id="error_11" class="error" style="display:none;width:55%;height:6%;position:absolute;top:22%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					
					<input id="add_12" class="add_in" type="text" style="width:55%;height:7%;position:absolute;top:28%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;" value="设备信息">
					
					<select id="add_13" class="add_in" type="text" style="width:55%;height:7%;position:absolute;top:41%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;">
						<option selected="selected">-请选择设备类型-</option>
					</select>
					<input id="id_13" type="text" value="" style="display:none;"/>
					<span id="error_13" class="error" style="display:none;width:55%;height:6%;position:absolute;top:48%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					
					<select id="add_14" class="add_in" type="text" style="width:55%;height:7%;position:absolute;top:54%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;">
						<option selected="selected">-请选择部门-</option>
					</select>
					<input id="id_14" type="text" value="" style="display:none;"/>
					<span  id="error_14" class="error" style="display:none;width:55%;height:6%;position:absolute;top:61%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					
					<select id="add_15" class="add_in" type="text" style="width:55%;height:7%;position:absolute;top:67%;left:24%;background-color:#f0f0f0;border:none;color:#cccccc;">
						<option selected="selected">-请选择组别-</option>
					</select>
					<input id="id_15" type="text" value="" style="display:none;"/>
					<span id="error_15" class="error" style="display:none;width:55%;height:7%;position:absolute;top:74%;left:24%;color:red;font-weight: bold;font-family: '微软雅黑';font-size: 12px;"></span>
					
					<input id="addtrue_01" onclick="ensure()" type="submit" onclick="" value="确定" style="background-color:#242b31;position:absolute;left:27%;bottom:6%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
					<input type="submit" onclick="cancel()" value="取消" style="background-color:#242b31;position:absolute;left:57%;bottom:6%;width:20%;border:none;border-radius:5px;line-height:25px;color:white;font-family:'黑体';font-size: 15px;">
			</div>
		</div>
<!--新加的——添加设备 -->
<!--jiangmingjun  -->
<div  id="setalarm" style="display:none;width:100%;height:100%;background:rgba(0,0,0,0.38);position: absolute;top: 0%;left:0%; z-index:1000">
</div>
<script type="text/javascript">
	var map = new BMap.Map("warning");
	map.centerAndZoom(new BMap.Point(116.404, 39.915),12);
	map.enableScrollWheelZoom(true);
	map.disableDoubleClickZoom(); 
	map.disablePinchToZoom();
	function change(self) {
       	var c=document.getElementById(self.id);
		$(".btn").css("backgroundColor","#F0F0F0");
		$(".btn").css("color","black");
		c.style.backgroundColor="#00a2ca";
		c.style.color="white";
		$(".edit").hide();
		var e = $("#"+self.id).attr('val');
		$("#"+e).show();
       }
	 function hid(){
        	$("#mask").hide();
      }
</script>
</body>
</html>