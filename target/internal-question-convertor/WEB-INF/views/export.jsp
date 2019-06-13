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
<%-- ${pageContext.request.contextPath} --%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>Insert title here</title>
		<link  href="js/layui/css/modules/layer/default/layer.css" rel="stylesheet"/>
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="js/layui/lay/modules/layer.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		
		debugger;
		$("#test").click(function(){
			$.ajax({
				type:"POST",
				url:"api/questionInfo/exportXls",
				data:"123",
				success:function(msg){
				},
				error:function(msg){
					debugger;
					alert(msg.information);
				}
			})
		});
	});
</script>
<body>
	<button id="test">点我</button>
</body>
</html>