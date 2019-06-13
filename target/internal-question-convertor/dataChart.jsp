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
		<link  href="js/layui/css/modules/layer/default/layer.css" rel="stylesheet"/>
		<link  href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/default.css" rel="stylesheet" />
		<link  href="css/question.css" rel="stylesheet"/>
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="./js/bootstrap.min.js" type="text/javascript"></script>
		<script src="js/layui/lay/modules/layer.js"></script>

<script type="text/javascript">
	var currentPage=1;
	var total=$("#total");
	$(document).ready(function() {
		$(document).ready(function() {
			$.ajax({
				type:"POST",
				url:"api/questionInfo/getTotalQueCounts",
				data:null,
				datatype:"json",
				success:function(msg){
					$("#totalQue").val(msg);
				},
				error:function(msg){
				}
				})
		});
		$("#picButton").click(function(){
			layer.open({
	            type: 2,//1是文本，2是iframe
	            //page层
	            area: ['50%', '70%'],
	            title: '图片题统计情况',
	            closeBtn: 1, //显示关闭按钮
	            shade: 0.3,//遮罩透明度
	            shadeClose:false,//点击遮罩关闭层
	            moveType: 1,//拖拽风格，0是默认，1是传统拖动
	            shift: 1,//0-6的动画形式，-1不开启
	            fix:true,//不随滚动条滚动，一直在可视区域。
	            border:[0],
	            closeBtn:[1,true],
				content: 'dataChart_img.jsp',
	            btn:['取消'],//  btn:['确定','取消'],
	        	cancel: function (index, layero) {//取消事件
	        	}
	        });
			
		});
		
		$("#contentButton").click(function(){
			layer.open({
	            type: 2,//1是文本，2是iframe
	            //page层
	            area: ['60%', '70%'],
	            title: '图片题统计情况',
	            closeBtn: 1, //显示关闭按钮
	            shade: 0.3,//遮罩透明度
	            shadeClose:false,//点击遮罩关闭层
	            moveType: 1,//拖拽风格，0是默认，1是传统拖动
	            shift: 1,//0-6的动画形式，-1不开启
	            fix:true,//不随滚动条滚动，一直在可视区域。
	            border:[0],
	            closeBtn:[1,true],
				content: 'dataChart_content_answer.jsp',
	            btn:['取消'],//  btn:['确定','取消'],
	        	cancel: function (index, layero) {//取消事件
	        	}
	        });
			
		});

		$("#gradeSubjectButton").click(function(){
			layer.open({
	            type: 2,//1是文本，2是iframe
	            //page层
	            area: ['60%', '70%'],
	            title: '图片题统计情况',
	            closeBtn: 1, //显示关闭按钮
	            shade: 0.3,//遮罩透明度
	            shadeClose:false,//点击遮罩关闭层
	            moveType: 1,//拖拽风格，0是默认，1是传统拖动
	            shift: 1,//0-6的动画形式，-1不开启
	            fix:true,//不随滚动条滚动，一直在可视区域。
	            border:[0],
	            closeBtn:[1,true],
				content: 'dataChart_grade_subject.jsp',
	            btn:['取消'],//  btn:['确定','取消'],
	        	cancel: function (index, layero) {//取消事件
	        	}
	        });
			
		});
		
		$("#parseButton").click(function(){
			layer.open({
	            type: 2,//1是文本，2是iframe
	            //page层
	            area: ['60%', '70%'],
	            title: '图片题统计情况',
	            closeBtn: 1, //显示关闭按钮
	            shade: 0.3,//遮罩透明度
	            shadeClose:false,//点击遮罩关闭层
	            moveType: 1,//拖拽风格，0是默认，1是传统拖动
	            shift: 1,//0-6的动画形式，-1不开启
	            fix:true,//不随滚动条滚动，一直在可视区域。
	            border:[0],
	            closeBtn:[1,true],
				content: 'dataChart_parse.jsp',
	            btn:['取消'],//  btn:['确定','取消'],
	        	cancel: function (index, layero) {//取消事件
	        	}
	        });
			
		});		
	});
</script>
</head>
<body>
	<div class="css_qustion_top">
		<span style="font-size: 30px;">数据统计页面</span>
	</div>
	<div class="css_middle">
		<div class="css_nav" style="color:black">
			<p class="css_shouye" style="font-size: 18px;color:black;"><a href="javascript:history.back(-1)">返回<a/>&nbsp;&gt;&nbsp;数据统计</p>
			
			<font style="font-weight: bold;color: black">题库总数量:</font> <input name="totalQue" id="totalQue" type="text" 
					style="width: 10%;border:1px solid #c8cccf;color: black;" />
					<br><br>
			<input id="picButton"  style="width: 20%;color:black;" type="button" value="图片题情况"/><br><br>
			<input id="contentButton" style="width: 20%;color:black;" type="button" value="题目和答案非空情况"/><br><br>
			<input id="parseButton" style="width: 20%;color:black;" type="button" value="是否包含解析情况"/><br><br>
			<input id="gradeSubjectButton" style="width: 20%;color:black;" type="button" value="各年级科目题目统计"/><br><br>
		
		</div>
		
	</div>
	<br>
	<div style="height: 200px"></div>
	
</body>
</html>