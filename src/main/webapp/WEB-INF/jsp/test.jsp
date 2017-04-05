<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/js/query.js" charset="utf-8"></script>
<title>test</title>
</head>
<body>
	<div id="search">
		<input type="text" value="" id="idsearch" ><button type="button" onclick="search(1)">id搜索</button>
	</div>
	<div id="show">
		<c:forEach items="${list}" var="c">
		<table border="0" class="everyinfo">
			<tr>
			<c:forEach items="${c.devicespecial[0]}"  var="entry">
			<c:if test="${entry.key!='relng'&&entry.key!='relat'&&entry.key!='deviceid'}">
				<th><c:out value="${entry.key}" /></th>
			</c:if>
			</c:forEach>
				<th>
				位置
				</th>
			</tr>
			<c:forEach items="${c.devicespecial}"  var="c1">
				<tr>
				<c:forEach items="${c1}"  var="c2">
				<c:if test="${c2.key!='relng'&&c2.key!='relat'&&c2.key!='deviceid'}">
				<td>
				${c2.value}
				</td>
				</c:if>
				</c:forEach>
				<td align="center">
				<a href="javascript:void(0)" relng="${c1.relng}" relat="${c1.relat}">
				查看
				</a>
				</td>
				</tr>
			</c:forEach>
		</table>
		</c:forEach>
	</div>	
</body>
</html>