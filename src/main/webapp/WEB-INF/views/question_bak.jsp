<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>首页</title>
<link rel="stylesheet" type="text/css"
	href="resources/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="resources/easyui/themes2/icon.css" />
<script type="text/javascript" src="resources/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="resources/comm.js"></script>	
<script type="text/javascript">
	var datagrid;
	$().ready(function() {
		
		datagrid = $("#datagrid").datagrid({
			url : 'api/questionInfo/selectTikubaoinfo2',
			pagination : true,//分页
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90,100 ],
			fit : true,
			queryParams:{id:null,subject:null,grade:null},
			checkOnSelect : true,
			selectOnCheck : true,
			fitColumns : true,
			columns : [ [ {
				title : '题库ID',
				field : 'id',
				width : 20,
				align :'center'
			}, {
				title : '原表ID',
				field : 'idOld',
				width : 20,
				align :'center'
			}, {
				title : '题目',
				field : 'content',
				width : 200,
				align :'center'
			}, {
				title : '答案',
				field : 'answer',
				width : 160,
				align :'center'
			}, {
				title : '分析',
				field : 'solution',
				width : 130,
				align :'center'
			} , {
				title : '科目',
				field : 'course',
				width : 15,
				align :'center'
			} , {
				title : '年级',
				field : 'grade',
				width : 15,
				align :'center'
			} ] ]
// 			,
// 			toolbar : [ {
// 				text : '添加',
// 				iconCls : 'icon-add',
// 				handler : function() {
// 				}
// 			}, {
// 				text : '修改',
// 				iconCls : 'icon-edit',
// 				handler : function() {
// 				}
// 			}, {
// 				text : '删除',
// 				iconCls : 'icon-remove',
// 				handler : function() {
// 				}
// 			}]

		});
	   });

	/*查询*/
	function query() {
		var para = {
			id : $("#id").val(),
			grade : $("#grade").val(),
			course : $("#course").val()
		}
		datagrid.datagrid('load', para);
	}
	

	
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false,title:'题库信息查询'"
		style="height: 60px; overflow: hidden;font-weight: bold;font-size: 20px; " text-align:center>
		<form action="">
			<table class="tableForm datagrid-toolbar"
				style="width: 60%; height: 100%" border=0>
				<tr >
					<td   align="right" style="font-weight: bold;font-size: 15px;">题目ID</td>
					<td align="left"><input name="id" id="id" type="text"
						style="width: 100%;" /> </td>
					<td align="right" style="font-weight: bold;font-size: 15px;">年级</td>
					<td align="left"><input name="grade" id="grade" type="text"
						style="width: 100%;" /> </td>
					<td align="right" style="font-weight: bold;font-size: 15px;">科目</td>
					<td align="left"><input name="course" id="course" type="text"
						style="width: 100%;" /> </td>						
					<td width="100px" align="left" style="font-weight: bold;font-size: 15px;">
						&nbsp;&nbsp;<button class="btn" style="width: 90%" onclick="query()">查询</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>
	<div id="dd" ></div>

</body>
</html>

