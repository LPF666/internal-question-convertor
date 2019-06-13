<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

<!-- 正常数据导出肯定要传入参数，我这里没有用ajax传参，简单用链接传参 -->
<script type="text/javascript">
function download(){
     var url="api/questionInfo/exportXls";
     window.open(url);
}
</script>
<body>
<form action="">
<input type="button" value="报表导出" onclick="download()"/>
</form>
</body>
</html>