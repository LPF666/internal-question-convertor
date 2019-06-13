<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>        
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<base href="<%=basePath%>">
		<title>题库查询页面</title>
		<link  href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/default.css" rel="stylesheet" />
		<link rel="stylesheet" href="js/layui/css/modules/layer/default/layer.css" />
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="js/layui/lay/modules/layer.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$("#datatr").on("dblclick", "tr", function() {
			debugger;
			var grade=$(this).children('td').eq(1).text();
			var subject=$(this).children('td').eq(2).text();
			window.parent.$("#grade").val(grade);
			window.parent.$("#subject").val(subject);
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
	});
	
</script>
</head>
<body>
	<div class="css_middle">
		<div class="css_nav">
			<p class="css_shouye" style="font-size: 18px;">条件&nbsp;&gt;&nbsp;年级科目(<font color="red">双击选择相应的数据</font>)</p>
		</div>
	</div>
	<br>
    <div class="filed_table" style="width:80%;margin:0 auto">
		
		<table id="datagrid" class="table table-bordered table-striped table-hover">
			<thead>
			<th>序号</th>
			<th>年级</th>
			<th>科目</th>
		</thead>
		<tbody id="datatr">
		<c:forEach items="${list }" var="rep" varStatus="st">
			<tr id="datatr">
				<td>${st.index}</td>
				<td>${rep.gradeName }</td>
				<td>${rep.subjectName }</td>
			</tr>
		</c:forEach>
		</tbody>
		
		</table>
		<input id="grade" type="hidden" >
		<input id="subject" type="hidden" >
<!-- 		<button id="confirm">确定</button> &nbsp; &nbsp; &nbsp; -->
<!-- 		<button id="cancel">取消</button> -->
	</div>
	<div style="height: 200px"></div>
</body>
</html>