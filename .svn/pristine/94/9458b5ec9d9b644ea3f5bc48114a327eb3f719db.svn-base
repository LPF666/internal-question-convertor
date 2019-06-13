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
		<title>图片题</title>
		<link  href="js/layui/css/modules/layer/default/layer.css" rel="stylesheet"/>
		<link  href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/default.css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
		<script src="js/layui/lay/modules/layer.js"></script>
		<script src="js/echarts.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#back").click(function(){
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
				var total=window.parent.$("#totalQue").val();
				$("#total").val(total);
				$("#test").val(total);
				$.ajax({
					type:"POST",
					url:"api/questionInfo/getCountsByContentandAnswer",
					data:null,
					datatype:"json",
					success:function(msg){
						debugger;
						var content=msg.content;
						var answer=msg.answer;
						var html = "";
						html += '<table class="table table-bordered table-striped table-hover">';
						html += '<thead><th style="text-align:center;">统计指标</th><th style="text-align:center;width:20%">数量</th></thead>';
						html += '<tbody>';
						$.each(content, function(i, obj) {
							var fieldNameOne = obj.fieldNameOne,fieldCount = obj.fieldCount;
							html += '<td style="width:30%" align="center">' + fieldNameOne +'</td>'
							+ '<td style="width:30%" align="center">'+ fieldCount + '</td>'
							+'</tr>';
						});
						html += '</tbody></table>';
						$("#contentDiv").html(html);
						
						
						
						var htmlAnswer = "";
						htmlAnswer += '<table class="table table-bordered table-striped table-hover">';
						htmlAnswer += '<thead><th style="text-align:center;">统计指标</th><th style="text-align:center;width:20%">数量</th></thead>';
						htmlAnswer += '<tbody>';
						$.each(answer, function(i, obj) {
							var fieldNameOne = obj.fieldNameOne,fieldCount = obj.fieldCount;
							htmlAnswer += '<td style="width:30%" align="center">' + fieldNameOne +'</td>'
							+ '<td style="width:30%"  align="center">'+ fieldCount + '</td>'
							+'</tr>';
						});
						htmlAnswer += '</tbody></table>';
						$("#answerDiv").html(htmlAnswer);
					},
					error:function(msg){
						debugger;
						alert(msg);
					}
					})
			});
		
		</script>
</head>
<body>
	<div class="css_middle">
		<div class="css_nav">
			<p class="css_shouye" style="font-size: 18px;"><a href="javascript:history.back(-1)" id="back">返回<a/>&nbsp;&gt;&nbsp;数据统计&nbsp;&gt;&nbsp;数据总量：<input id="total" type="text" style="border:0;font-weight: bold;font-size:20px;" readonly="readonly"></p>
			<br><br>
			<div style="margin: 0 auto;color: black;font-weight: bold;text-align: center;">题目内容是否为空统计情况</div>
			<br><div id="contentDiv" style="margin: 0 auto;width: 80%;color: black;">
				
				<br><br>
			
			</div>	
			<div style="margin: 0 auto;color: black;font-weight: bold;text-align: center;" >答案是否为空统计情况</div>
			<br><div id="answerDiv" style="margin: 0 auto;width: 80%;color: black;">
			
			
			</div>	
				
		</div>
		
	</div>
	<div style="height: 200px"></div>
	
</body>
</html>