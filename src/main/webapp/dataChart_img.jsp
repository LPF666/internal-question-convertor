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
					url:"api/questionInfo/getQueCountsNoImg",
					data:null,
					datatype:"json",
					success:function(msg){
						debugger;
						for ( var i = 0; i <msg.key.length; i++){
							    if(i==0){
							    	$("#key1").val(msg.key[i]);	
								}else if(i==1){
									$("#key2").val(msg.key[i]);	
								}else if(i==2){
									$("#key3").val(msg.key[i]);	
								}
						}
						
						for ( var i = 0; i <msg.value.length; i++){
						    if(i==0){
						    	$("#value1").val(msg.value[i]);	
							}else if(i==1){
								$("#value2").val(msg.value[i]);	
							}else if(i==2){
								$("#value3").val(msg.value[i]);	
							}
						}
				        // 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        option = {
				       		title: {
				                   text: '题库图片题统计情况'
				            },
				            color: ['#3398DB'],
				            tooltip : {
				                trigger: 'axis',
				                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				                }
				            },
				            grid: {
				                left: '3%',
				                right: '4%',
				                bottom: '3%',
				                containLabel: true
				            },
				            xAxis : [
				                {
				                    type : 'category',
				                    data : msg.key,
				                    axisTick: {
				                        alignWithLabel: true
				                    }
				                }
				            ],
				            yAxis : [
				                {
				                    type : 'value'
				                }
				            ],
				            series : [
				                {
				                    name:'数量',
				                    type:'bar',
				                    barWidth: '60%',
				                    data:msg.value
				                }
				            ]
				        };

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
// 						
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
			
			
		</div>
		
	</div>
	<br>
    <div class="ddlsyd_page">
    	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;margin: 0 auto"></div>
    
	</div>
	<input style="font-weight: bold;color: black;border: 0;" value="非图片题数量:" id="key1" readonly="readonly"> <input name="value1" id="value1" type="text"  value="0"
					style="font-weight: bold;border:1px solid #c8cccf;color: black;" readonly="readonly" /><br>
	<input style="font-weight: bold;color: black;border: 0;"  value="图片题且正常显示数量:" id="key2" readonly="readonly"> <input name="value2" value="0" id="value2" type="text" 
					style="font-weight: bold;border:1px solid #c8cccf;color: black;"readonly="readonly" /><br>
	<input style="font-weight: bold;color: black;border: 0;"  value="图片题且异常显示数量:" id="key3" readonly="readonly"> <input name="value3" value="0" id="value3" type="text" 
					style="font-weight: bold;border:1px solid #c8cccf;color: black;" readonly="readonly"/>
	<div style="height: 200px"></div>
	
</body>
</html>