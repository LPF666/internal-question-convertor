<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>        
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
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
		<link href="css/blue_pagination.css" rel="stylesheet" />
		<link  href="css/question.css" rel="stylesheet"/>
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="./js/jquery.pagination.js" type="text/javascript"></script>
		<script src="./js/bootstrap.min.js" type="text/javascript"></script>
		<script src="js/layui/lay/modules/layer.js"></script>
<script type="text/javascript">
	var currentPage=1;
	var total=$("#total");
	$(document).ready(function() {
		
		var pagenum=$('#pageNum').val();
		if(pagenum==null||pagenum==""){
			$('#pageNum').val(1);
		}
		debugger;
		
		$("#pre").click(function(){
			debugger;
			var pageNum=parseInt($('#pageNum').val());
			if (pageNum!=null&&pageNum==1) {
				layer.alert("已经是第一页了");
				return;
			}
			var page=parseInt(pageNum-1);
			$("#pageNum").val(page);
			var data=$("#dataForm");
			data.submit();
		});
		
		$("#next").click(function(){
			debugger;
			var pageSize=$('#pageSize option:selected').val();
			var pageNum=parseInt($('#pageNum').val());
			if(total!=null&&currentPage>total){
				layer.alert("已经是最后一页了");
			}
			var page=parseInt(pageNum+1);
			$("#pageNum").val(page);
			var data=$("#dataForm");
			data.submit();
		});
		$("#reset").click(function(){
			$("#pageNum").val(1);
			$("#id").val(null);
			$("#idOld").val(null);
			$("#grade").val(null);
			$("#subject").val(null);
		});
		
		
	});
	function detailInfo(picExistflag,contentExistFlag,answerExistFlag,parseExistFlag,qiniuFlag){
		debugger;
		if(picExistflag==0){
			picExistflag="图片题且图片缺失";
		}else if(picExistflag==1){
			picExistflag="图片题且图片正常";
		}else{
			picExistflag="非图片题";
		}
		if(contentExistFlag==0){
			contentExistFlag="题目内容异常";
		}else if(contentExistFlag==1){
			contentExistFlag="题目内容正常";
		}
		if(parseExistFlag==0){
			parseExistFlag="题目不存在解析";
		}else if(parseExistFlag==1){
			parseExistFlag="题目带有解析";
		}
		if(answerExistFlag==0){
			answerExistFlag="题目缺少答案";
		}else if(answerExistFlag==1){
			answerExistFlag="题目带有答案";
		}
		if(qiniuFlag==0){
			qiniuFlag="  (3). 图片格式有误，上传云存储失败";
		}else{
			qiniuFlag="";
		}
		layer.open({
            type: 1,//1是文本，2是iframe
            //page层
            area: ['40%', '40%'],
            title: '题目信息描述',
            closeBtn: 1, //显示关闭按钮
            shade: 0.3,//遮罩透明度
            shadeClose:true,//点击遮罩关闭层
            moveType: 1,//拖拽风格，0是默认，1是传统拖动
            shift: 1,//0-6的动画形式，-1不开启
            fix:true,//不随滚动条滚动，一直在可视区域。
            border:[0],
            closeBtn:true,
            btn:['取消'],
			content: "<div id=\"detalDes\" style=\"font-weight: bold\"> <p >&nbsp;&nbsp;  (1). "+picExistflag+"</p>"
			+"<p>&nbsp;&nbsp;  (2). "+contentExistFlag+"</p><p> &nbsp;&nbsp; (3). "+parseExistFlag+"</p>  "
			+"<p>&nbsp;&nbsp;  (4). "+answerExistFlag+"</p><p>&nbsp;&nbsp;"+qiniuFlag+"</p>  </div> ",
            
        });
		
	}
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
		<span style="font-size: 30px;">题库信息查询</span>
	</div>
	<div class="css_middle">
		<div class="css_nav">
			<p class="css_shouye" style="font-size: 18px;">首页&nbsp;&gt;&nbsp;题库信息查询</p>
			<form action="api/questionInfo/selectTikubaoinfo" method ="post" id="dataForm">
				
				<div >
					<font style="font-weight: bold">题目ID:</font> <input name="id" id="id" type="text" 
					style="width: 5%;border:1px solid #c8cccf;" />
					<font style="font-weight: bold">原题ID:</font> <input name="idOld" id="idOld" type="text" 
					style="width: 5%;border:1px solid #c8cccf;" />
					&nbsp;<font style="font-weight: bold">年级:</font>
					<input name="grade" id="grade" type="text"
					style="width: 10%;border:1px solid #c8cccf;" value="${grade }"/>
					
					&nbsp;<font style="font-weight: bold">科目:</font>
					<input name="subject" id="subject" type="text"
					style="width: 10%;border:1px solid #c8cccf;" value="${subject }"/>
					
					<input id="test" style="width: 10%;background-color: #ffff33" onclick="gradeclick()" type="button" value="选择年级科目"/>
					&nbsp;<font style="font-weight: bold">当前页:</font>
					<input name="currentPage" id="currentPage" type="hidden"  value="${currentPage}"
					style="width: 5%;border:1px solid #c8cccf;"  />
					<input name="pageNum" id="pageNum" type="text"  value="${currentPage}"
					style="width: 5%;border:1px solid #c8cccf;"  />
					<font style="font-weight: bold">每页展示数据:</font>
					<select class="" name="pageSize" id="pageSize" style="width:5%;border:1px solid #c8cccf;"  >
						<option value="50">50</option>
						<option value="40">40</option>
						<option value="30">30</option>
						<option value="20">20</option>
						<option value="10">10</option>
						<option value="60">60</option>
						<option value="70">70</option>
						<option value="80">80</option>
						<option value="90">90</option>
						<option value="100">100</option>
					</select>
					<input name="" id="" type="submit" value="查询"	
					style="width: 15%;" />
					<br><br>
					<font style="font-weight: bold">起始时间:</font>
					<input name="startTimeStr" id="startTimeStr" type="date"
					style="width: 10%;border:1px solid #c8cccf;" />
<!--  					<input type="text" class="layui-input" id="startTime" placeholder=" - "> -->
					&nbsp;<font style="font-weight: bold">截止时间:</font>
					<input name="endTimeStr" id="endTimeStr" type="date"
					style="width: 10%;border:1px solid #c8cccf;" />
<!-- 					<input type="text" class="layui-input" id="endTime" placeholder=" - "> -->
					<input id="pre" style="width: 10%;" type="button" value="上一页"/>
					<input id="next" style="width: 10%;" type="button" value="下一页"/>
					<input id="reset" style="width: 5%;" type="button" value="重置"/>
					<a id="data"  href="dataChart.jsp"/>数据统计</a>
				</div>
			</form>
		</div>
	</div>
	<br>
    <div class="ddlsyd_page">
		
		<table id="datagrid" class="table table-bordered table-striped table-hover">
			<thead>
			<th>题库ID</th>
			<th>原题ID</th>
			<th>题目</th>
			<th>答案</th>
			<th>分析</th>
			<th>科目</th>
			<th>年级</th>
			<th>详情</th>
		</thead>
		<tbody>
		<c:forEach items="${list.recordList }" var="rep">
			<tr>
				<td>${rep.id }</td>
				<td>${rep.idOld }</td>
				<td>${rep.content }</td>
				<td>${rep.answer }</td>
				<td>${rep.solution }</td>
				<td>${rep.course }</td>
				<td>${rep.grade }</td>
				<td><input type="button" value="详情" id="detailInfo" 
				onclick="detailInfo(${rep.picExistflag },${rep.contentExistFlag },${rep.answerExistFlag },${rep.parseExistFlag },${rep.qiniuFlag })"></td>
			</tr>
		</c:forEach>
		</tbody>
		
		</table>
		<font style="font-weight: bold">总页数:</font><input name="total" id="total" type="text" value="${total}"
					style="width: 5%;border:1px solid #c8cccf;"  />&nbsp;&nbsp;&nbsp;
		<font style="font-weight: bold">总记录条数:</font><input name="totalCount" id="totalCount" type="text" value="${totalCount}"
					style="width: 5%;border:1px solid #c8cccf;"  />
	</div>
	   
		<div style="height: 200px"></div>
<%-- 		${rep.picExistflag },${rep.contentExistFlag },${rep.answerExistFlag },${rep.parseExistFlag },${rep.qiniuFlag } --%>
</body>
</html>