<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head runat="server">
		<meta charset="utf-8" />
		<title></title>
		<link  href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/default.css" rel="stylesheet" />
		<link href="css/blue_pagination.css" rel="stylesheet" />
		<link  href="css/question.css" rel="stylesheet"/>
		<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
		<script src="./js/jquery.pagination.js" type="text/javascript"></script>
		<script src="./js/bootstrap.min.js" type="text/javascript"></script>
		<script src="./js/questionQuery.js" type="text/javascript" ></script>
	</head>
	<body>
					<div class="css_qustion_top">
						<span style="font-size: 30px;">题库信息查询</span>
					</div>
					<div class="css_middle">
						<div class="css_nav">
							<p class="css_shouye" style="font-size: 18px;">首页&nbsp;&gt;&nbsp;题库信息查询</p>
						<span class="btn " type="" style="font-size: 15px;">题库ID：</span>
						 <input type="text" id="id" class="input" style="width:10%;border-radius:5px;border:1px solid #c8cccf;height:2.2em;"    >				
						<span class="btn " type="" style="font-size: 15px;">年级：</span>
					    <input type="text" id="grade" class="input" style="width:10%;border-radius:5px;border:1px solid #c8cccf;height:2.2em;"    >				
							<span class="btn css_ddlsydcx_button" type="button" style="font-size: 15px;">科目：</span>
						    <input type="text" id="subject" style="width:10%;border-radius:5px;border:1px solid #c8cccf;height:2.2em;"  value="数学"  >
						<span class=" ">
							<button class="btn " type="button">分页数：</button>
						</span>
						 <select class="" id="pageSize" style="width:10%;border-radius:5px;border:1px solid #c8cccf;height:2.2em;">
	<!-- 									<option value="">请选择</option> -->
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="30">30</option>
								<option value="40">40</option>
								<option value="50">50</option>
								<option value="60">60</option>
								<option value="70">70</option>
								<option value="80">80</option>
								<option value="90">90</option>
								<option value="100">100</option>
						 </select>
						 &nbsp;&nbsp;
						 <button type="button" class="btn btn-info" id="ddlsyd_query" style="height:2.6em;margin-left:20px;width:16%;" >查询</button>
							
						</div>
						<div id="ddlsyd_page" style="margin-top:20px"></div>
						<div id="ddlsyd_pagination" class="my_pagination"></div>
					</div>
					<div style="height:200px;border: solid 0px #AAE;">
					
					
					</div>
				
	</body>
</html>
