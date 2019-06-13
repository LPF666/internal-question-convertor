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
		<title>题库信息编辑页面</title>
<!-- 		<link  href="js/layui/css/modules/layer/default/layer.css" rel="stylesheet"/> -->
		<link  href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/default.css" rel="stylesheet" />
		<link  href="css/question.css" rel="stylesheet"/>
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="./js/bootstrap.min.js" type="text/javascript"></script>
		<script src="js/layui/lay/modules/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		var currentPage=1;
		var total=$("#total");
		$(document).ready(function() {
			
			$("#reset").click(function(){
				$("#pageNum").val(1);
				$("#id").val(null);
				$("#grade").val(null);
				$("#subject").val(null);
			});
			UE.Editor.prototype.bkgetActionUrl = UE.Editor.prototype.getActionUrl;
	        UE.Editor.prototype.getActionUrl = function (action) {
	        	debugger;
	            if (action=="api/questionInfo_update/uploadFile") {
	                return "<%=request.getContextPath()%>/api/questionInfo_update/uploadFile";
	            }else{
	                return this.bkgetActionUrl.call(this,action);
	            }
	        }
		});
		function gradeclick(){
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
	        	cancel: function (index, layero) {//取消事件
	        	}
	        });
		}
	</script>
</head>
<body>
	<div class="css_qustion_top">
		<span style="font-size: 30px;">数据修改页面</span>
	</div>
	<div class="css_middle">
		<div class="css_nav">
			<p class="css_shouye" style="font-size: 18px;"><a href="javascript:history.back(-1)">返回<a/>&nbsp;&gt;&nbsp;数据修改</p>
			<input name="subject" id="subject" type="text" style="width: 10%;border:1px solid #c8cccf;" />
			<input id="pre" style="width: 10%;" type="button" value="查询"/>   
			<br><br>
			<table class="table table-bordered table-striped table-hover" style="text-decoration: none">
				
				<tr>
					<td align="center" >题干：</td>
					<td >
						<script id="contentcontainer" name="content" type="text/plain" ></script>
					</td>
				</tr>
				<tr>
					<td>答案：</td>
					<td>
						<script id="answercontainer" name="answer" type="text/plain" ></script>					
					</td>
				</tr>
				<tr>
					<td>解析：</td>
					<td>
						<script id="solutioncontainer" name="sulution" type="text/plain" ></script>					
					</td>
				</tr>
			
			</table>
			
			
		</div>
		
	</div>
	 <!-- 配置文件 -->
    <script type="text/javascript" src="resources/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="resources/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件 -->
    <script type="text/javascript" src="resources/ueditor/lang/zh-cn/zh-cn.js"></script>

    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('contentcontainer');
        var ue2 = UE.getEditor('answercontainer');
        var ue3 = UE.getEditor('solutioncontainer');
    </script>
	<br>
    <div class="ddlsyd_page">
		
	</div>
	<div style="height: 200px"></div>
</body>
</html>