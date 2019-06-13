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
			layer.open({
	            type: 2,//1是文本，2是iframe
	            //page层
	            area: ['70%', '60%'],
	            title: '年级科目信息查询',
	            closeBtn: 1, //显示关闭按钮
	            shade: 0.3,//遮罩透明度
	            shadeClose:false,//点击遮罩关闭层
	            moveType: 1,//拖拽风格，0是默认，1是传统拖动
	            shift: 1,//0-6的动画形式，-1不开启
	            fix:true,//不随滚动条滚动，一直在可视区域。
	            border:[0],
	            closeBtn:[1,true],
				content: 'api/questionInfo/selectAllSubjectByGradeNamePage',
	            btn:['取消'],//  btn:['确定','取消'],
// 	            yes: function (index, layero) {
// 	            	debugger;
// 	            	//得到iframe页的窗口对象（2种方式）-----------推荐使用第二种方式获得对象
// 	            	//对应调用子页面的方法（2种方式）
// 	            	var body = layer.getChildFrame('body', index);  //此处我理解的是加载目标页面的内容
// 	            	var gradeSon=body.find('#grade').val();
// 	            	var subjectSon=body.find('#subject').val();
//                  	$("#grade").val(gradeSon);//查到目标页面的内容赋给当前页面元素
//                  	$("#subject").val(subjectSon);//查到目标页面的内容赋给当前页面元素
// //                  layer.close(index);//这块是点击确定关闭这个弹出层
// 	            },
            	cancel: function (index, layero) {//取消事件
            	},
	        });
		});
	});
</script>
<body>
	<button id="test">点我</button>
	<input type="text" id="grade" >
	<input type="text" id="subject" >
</body>
</html>