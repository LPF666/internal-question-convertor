$(function() {
	$("#ddlsyd_query").click(function() {
		$("#ddlsyd_page").html('');
		if($("#pageSize").val()==''){
			$("#pageSize").val("2");
		}
		$("#ddlsyd_page").html('');
		$("#ddlsyd_page").show();
		$("#ddlsyd_pagination").show();
		df(0);
	});

	
});
function df(index){
	var pageNum=1;
	var pageSize=$("#pageSize").val();
	var grade=$("#grade").val();
	var subject=$("#subject").val();
	var pnum=index+1;
	var jsonobj = {
        "id":0,"grade":grade,"subject":subject,"pageSize":pageSize,"pageNum":pnum,"ramdom":Math.random()
    };
	debugger;
	$.ajax({
	type:"POST",
	url:"api/questionInfo/selectTikubaoinfo",
	data:jsonobj,
	dataType : "json",
	success:function(msg){
		var list = msg.data.recordList;
		var totalNum= msg.data.totalCount;
		var html = "";
		html += '<table class="table table-bordered table-striped table-hover">';
		html += '<thead><th>题目ID</th><th>题目内容</th><th>题目答案</th><th>题目解析</th><th>年级</th><th>科目</th></thead>';
		html += '<tbody>';
		$.each(list, function(i, obj) {
			var id = obj.id,content = obj.content,answer = obj.answer,solution = obj.solution;
			var course=obj.course,grade=obj.grade;
			html += '<tr><td style="width:5%">' + id +'</td>'
			+ '<td style="width:25%">'+ content + '</td>'
			+ '<td style="width:25%">'+ answer + '</td>'
			+ '<td style="width:25%">'+ solution + '</td>'
			+ '<td style="width:10%">'+ course + '</td>'
			+ '<td style="width:10%">'+ grade + '</td>'
			+'</tr>';
		});
		html += '</tbody></table>';
		$("#ddlsyd_page").html(html);
	    $("#ddlsyd_pagination").pagination(totalNum, {
			callback : df,
			prev_text : "<上一页",
			next_text : "下一页 >",
			items_per_page : pageSize,
			num_display_entries : 10,
			num_edge_entries : 10,
			prev_show_always : true,
			next_show_always : true,
			current_page : index,
			link_to : "javascript:void(0)"
		});
	},
	error:function(msg){
		debugger;
		alert(msg.information);
	}
	})
}