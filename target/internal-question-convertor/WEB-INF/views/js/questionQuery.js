
function df(index){
	
	var pageNum=1;
	var id=$("#id").val();
	var pageSize=$("#pageSize").val();
	var grade=$("#grade").val();
	var subject=$("#subject").val();
	var pnum=index+1;
	var jsonobj = {
        "id":id,"grade":grade,"subject":subject,"pageSize":pageSize,"pageNum":pnum,"ramdom":Math.random()
    };
	debugger;
	$.ajax({
	type:"POST",
	url:"api/questionInfo/selectTikubaoinfo3",
	data:jsonobj,
	datatype:"text",
	success:function(msg){
		debugger;
		var list = msg.data.recordList;
		var totalNum= 100;
		var html = "";
		html += '<table class="table table-bordered table-striped table-hover">';
		html += '<thead><th style="text-align:center;">题目ID</th><th style="text-align:center;">原表题目ID</th><th style="text-align:center;width:20%">题目内容</th><th style="text-align:center;">题目答案</th><th style="text-align:center;">题目解析</th><th style="text-align:center;">科目</th><th style="text-align:center;">年级</th></thead>';
		html += '<tbody>';
		$.each(list, function(i, obj) {
			var id = obj.id,idOld = obj.idOld,content = obj.content,answer = obj.answer,solution = obj.solution;
			var course=obj.course,grade=obj.grade;
			if(grade==null){
				grade='无';
			}
			html += '<tr><td style="width:5%" align="center">' + id +'</td>'
			+ '<td style="width:8%" align="center">' + idOld +'</td>'
			+ '<td style="width:20%">'+ content + '</td>'
			+ '<td style="width:28%">'+ answer + '</td>'
			+ '<td style="width:22%">'+ solution + '</td>'
			+ '<td style="width:8%" align="center">'+ course + '</td>'
			+ '<td style="width:10%" align="center">'+ grade + '</td>'
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