package com.eebbk.internal.question.convertor.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eebbk.edu.common.cloudfile.QiniuUtil;
import com.eebbk.edu.common.framework.core.util.PageUtil;
import com.eebbk.edu.common.http.network.HttpClientManage;
import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.edu.common.util.extend.constant.page.Page;
import com.eebbk.edu.common.util.extend.constant.page.Param;
import com.eebbk.edu.common.util.json.JsonTool;
import com.eebbk.edu.common.util.page.PageBean;
import com.eebbk.edu.common.web.template.ResultVoTemplate;
import com.eebbk.internal.question.convertor.pojo.GradePojo;
import com.eebbk.internal.question.convertor.service.QuestionConvertService;
import com.eebbk.internal.question.convertor.util.CreateFile;
import com.eebbk.internal.question.convertor.util.DataGrid;
import com.eebbk.internal.question.convertor.util.FileUtils;
import com.eebbk.internal.question.convertor.vo.DataCountsVo;
import com.eebbk.internal.question.convertor.vo.GradeAndSubjectVo;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;
import com.mysql.fabric.xmlrpc.base.Array;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(value = "题库信息维护", description = "题库信息维护")
@Controller
@RequestMapping("api/questionInfo")
public class QuestionController {
	@Autowired
	private QuestionConvertService questionConvertService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@ApiOperation("查询原题库数据")
    @ResponseBody
    @RequestMapping(value = "/selectQuestionInfo", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String selectQuestion()
    {
		List<QuestionsVo> questions=questionConvertService.selectQuestion();
        return ResultVoTemplate.outputSuccess(questions);
    }
	
	@ApiOperation(value = "查询题库宝数据", notes = "同时作为swagger示例")
    @ResponseBody
    @RequestMapping(value = "/selectTikubaoinfo_bak", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
    public String selectTikubaoinfo_bak(
    		 @ApiParam(value = "主键ID") @RequestParam(value = "id", required = true) Integer id,
    		 @ApiParam(value = "年级" ) @RequestParam(value = "grade", required = true) String grade,
    		 @ApiParam(value = "科目", defaultValue = "语文") @RequestParam(value = "subject", required = true) String subject,
    		 @ApiParam(value = Page.NUM) @RequestParam(value = Param.NUM, required = false) Integer pageNum,
             @ApiParam(value = Page.SIZE) @RequestParam(value = Param.SIZE, required = false) Integer pageSize
    		)
    {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (id!=null) {
			paramMap.put("id", id);
		}
		if (StrUtils.isNotEmpty(grade)) 
		{
			paramMap.put("grade", grade);
		}
		if (StrUtils.isNotEmpty(subject)) 
		{
			paramMap.put("course", subject);
		}
		PageBean questions=questionConvertService.selectTikubaoinfo(paramMap,PageUtil.getPageParam(pageNum, pageSize));
        return ResultVoTemplate.outputSuccess(questions);
    }
	
	@ApiOperation("将转换后的数据插入学库宝")
    @ResponseBody
    @RequestMapping(value = "/insertQuestionXuekubao", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String insertQuestionXuekubao(
    		@ApiParam(value = "起始下标", defaultValue = "0") @RequestParam(value = "pageNum", required = true) Integer pageNum, 
    		@ApiParam(value = "每页的数量", defaultValue = "10") @RequestParam(value = "pageSize", required = true) Integer pageSize)
    {
		questionConvertService.insertQuestionXuekubao(pageNum,pageSize);
        return ResultVoTemplate.outputSuccess();
    }
	
	
	@ApiOperation("上传js插件文件")
    @ResponseBody
    @RequestMapping(value = "/uploadJsFile", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String uploadAppInfo(
   		 @ApiParam(value = "文件名字", defaultValue = "MLCore-2016.08.15.min.js") 
   		 @RequestParam(value = "fileName", required = true) String fileName)
    {
		String cloudPath=questionConvertService.uploadJsFile(fileName);
        return ResultVoTemplate.outputSuccess(cloudPath);
    }
	
	@ResponseBody
    @RequestMapping(value = "/selectTikubaoinfo2", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
    public DataGrid selectTikubaoinfo2(QuestionXuekubaoQueryVo xuekubao)
    {
		DataGrid dg = new DataGrid();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (xuekubao.getId()!=null) 
		{
			paramMap.put("id", xuekubao.getId());
		}
		
		if (StrUtils.isNotEmpty(xuekubao.getGrade())) 
		{
			paramMap.put("grade", xuekubao.getGrade());
		}
		if (StrUtils.isNotEmpty(xuekubao.getCourse())) 
		{
			paramMap.put("course", xuekubao.getCourse());
		}
		PageBean questions=questionConvertService.selectTikubaoinfo(paramMap,PageUtil.getPageParam(xuekubao.getPage(), xuekubao.getRows()));
		Integer totalCount = questions.getTotalCount();
		dg.setRows(questions.getRecordList());
		dg.setTotal(totalCount);
		return dg;
    }
	
    @RequestMapping(value = "/selectTikubaoinfo3", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
	public String selectTikubaoinfo3(
   		 @ApiParam(value = "主键ID") @RequestParam(value = "id", required = true) Integer id,
   		 @ApiParam(value = "年级" ) @RequestParam(value = "grade", required = true) String grade,
   		 @ApiParam(value = "科目", defaultValue = "语文") @RequestParam(value = "subject", required = true) String subject,
   		 @ApiParam(value = Page.NUM) @RequestParam(value = Param.NUM, required = false) Integer pageNum,
         @ApiParam(value = Page.SIZE) @RequestParam(value = Param.SIZE, required = false) Integer pageSize
   		,Model model)
    {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (id!=null) 
		{
			paramMap.put("id", id);
		}
		if (StrUtils.isNotEmpty(grade)) 
		{
			paramMap.put("grade", grade);
		}
		if (StrUtils.isNotEmpty(subject)) 
		{
			paramMap.put("course", subject);
		}
		PageBean questions=questionConvertService.selectTikubaoinfo(paramMap,PageUtil.getPageParam(pageNum, pageSize));
		model.addAttribute("list", questions);
		model.addAttribute("total", questions.getPageCount());
		model.addAttribute("currentPage", questions.getCurrentPage());
		return "index";
	}
    
    @RequestMapping(value = "/selectTikubaoinfo", method = {RequestMethod.POST,RequestMethod.GET})
	public String selectTikubaoinfo(QuestionXuekubaoQueryVo xuekubao,Model model)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (xuekubao.getId()!=null) {
			paramMap.put("id", xuekubao.getId());
		}
		if (xuekubao.getIdOld()!=null) 
		{
			paramMap.put("idOld", xuekubao.getIdOld());	
		}
		if (StrUtils.isNotEmpty(xuekubao.getGrade())) 
		{
			paramMap.put("grade", xuekubao.getGrade());
			model.addAttribute("grade", xuekubao.getGrade());
		}
		if (StrUtils.isNotEmpty(xuekubao.getSubject())) 
		{
			paramMap.put("course", xuekubao.getSubject());
			model.addAttribute("subject", xuekubao.getSubject());
		}
		String startTimeStr = xuekubao.getStartTimeStr();
		String endTimeStr = xuekubao.getEndTimeStr();
		try 
		{
			if (StringUtils.isNotEmpty(startTimeStr)) 
			{
				Date parseStartDate = sdf.parse(startTimeStr);
				paramMap.put("startTime", parseStartDate);
			}
			
			if (StringUtils.isNotEmpty(endTimeStr)) {
				Date parseEndDate = sdf.parse(endTimeStr);
				paramMap.put("endTime", parseEndDate);
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		Integer queType = xuekubao.getQueType();
		PageBean questions=null;
		//0 非试卷题  1 试卷题
		if (queType!=null&&queType==1) 
		{
			questions=questionConvertService.selectShijuanTikubaoinfo(paramMap,PageUtil.getPageParam(xuekubao.getPageNum(), xuekubao.getPageSize()));
		}else{
			questions=questionConvertService.selectTikubaoinfo(paramMap,PageUtil.getPageParam(xuekubao.getPageNum(), xuekubao.getPageSize()));
		}
		model.addAttribute("list", questions);
		model.addAttribute("total", questions.getPageCount());
		model.addAttribute("totalCount", questions.getTotalCount());
		model.addAttribute("currentPage", questions.getCurrentPage());
		return "index";
	}
    
    
    @ResponseBody
    @RequestMapping(value = "/selectAllGrade", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
    public String selectAllGrade()
    {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<GradePojo> grades=questionConvertService.selectAllGrade();
        return ResultVoTemplate.outputSuccess(grades);
    }
    
    @ResponseBody
    @RequestMapping(value = "/selectAllSubjectByGradeName", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
    public String selectAllSubjectByGradeName(String gradeName)
    {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<GradeAndSubjectVo> subject=questionConvertService.selectAllSubjectByGradeName(gradeName);
        return ResultVoTemplate.outputSuccess(subject);
    }
    
    @RequestMapping(value = "/selectAllSubjectByGradeNamePage", method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
    public String selectAllSubjectByGradeNamePage(QuestionXuekubaoQueryVo xuekubao,Model model)
    {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<GradeAndSubjectVo> subject=questionConvertService.selectAllSubjectByGradeName(xuekubao.getGrade());
		model.addAttribute("list", subject);
		return "grade";
    }
    
    @ApiOperation("提取各科目的题并生成文件")
    @ResponseBody
    @RequestMapping(value = "/getQuestionFileBysubject", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getQuestionFileBysubject()
    {
		List<QuestionXuekubaoQueryVo> questions=questionConvertService.getQuestionFileBysubject();
		Iterator<QuestionXuekubaoQueryVo> iteratorQue = questions.iterator();
		while (iteratorQue.hasNext()) 
		{
			QuestionXuekubaoQueryVo que = iteratorQue.next();
			try {
				CreateFile.create(que);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return ResultVoTemplate.outputSuccess(questions);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getQueCountsNoImg", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getQueCountsNoImg()
    {
    	List<DataCountsVo> dataVoList=questionConvertService.getQueCountsNoImg();
    	List<String> key=new ArrayList<String>();
    	List<Integer> value=new ArrayList<Integer>();

    	for (DataCountsVo dataVo:dataVoList) 
    	{
    		String fieldNameOne = dataVo.getFieldNameOne();
    		if (StrUtils.isNotEmpty(fieldNameOne)&&"1".equals(fieldNameOne)) 
    		{
				key.add("图片题且显示正常");
				value.add(dataVo.getFieldCount());
			}else if (StrUtils.isNotEmpty(fieldNameOne)&&"2".equals(fieldNameOne)) {
				key.add("非图片题");
				value.add(dataVo.getFieldCount());
			}else if (StrUtils.isNotEmpty(fieldNameOne)&&"0".equals(fieldNameOne)) {
				key.add("图片题且显示异常");
				value.add(dataVo.getFieldCount());
			}
    	}
    	Map map = new HashMap<String,Object>();
    	map.put("key", key);
    	map.put("value", value);
    	System.out.println(JsonTool.toJson(map));
		return  JsonTool.toJson(map);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getTotalQueCounts", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getTotalQueCounts()
    {
    	Integer total =questionConvertService.getTotalQueCounts();
		return  JsonTool.toJson(total);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getCountsByContentandAnswer", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getCountsByContentandAnswer()
    {
    	List<DataCountsVo> totalContent =questionConvertService.getNoExistSituationByContent();
    	
    	for (DataCountsVo dataVo:totalContent) 
    	{
    		String fieldNameOne = dataVo.getFieldNameOne();
    		if (StrUtils.isNotEmpty(fieldNameOne)&&"1".equals(fieldNameOne)) 
    		{
    			dataVo.setFieldNameOne("题目正常数量");
			}
    		else if (StrUtils.isNotEmpty(fieldNameOne)&&"0".equals(fieldNameOne)) 
			{
				dataVo.setFieldNameOne("题目异常数量");
			}
    		
    	}
    	List<DataCountsVo> totalAnswer =questionConvertService.getNoExistSituationByAnswer();
    	for (DataCountsVo dataVo:totalAnswer) 
    	{
    		String fieldNameOne = dataVo.getFieldNameOne();
    		if (StrUtils.isNotEmpty(fieldNameOne)&&"1".equals(fieldNameOne)) 
    		{
    			dataVo.setFieldNameOne("答案存在数量");
			}
    		else if (StrUtils.isNotEmpty(fieldNameOne)&&"0".equals(fieldNameOne)) 
			{
				dataVo.setFieldNameOne("答案不存在数量");
			}
    		
    	}
    	Map map = new HashMap<String,Object>();
    	map.put("content", totalContent);
    	map.put("answer", totalAnswer);
		return  JsonTool.toJson(map);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getCountsBySolution", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getCountsBySolution()
    {
    	List<DataCountsVo> totalParse =questionConvertService.getNoExistSituationBySolution();
    	
    	for (DataCountsVo dataVo:totalParse) 
    	{
    		String fieldNameOne = dataVo.getFieldNameOne();
    		if (StrUtils.isNotEmpty(fieldNameOne)&&"1".equals(fieldNameOne)) 
    		{
    			dataVo.setFieldNameOne("存在解析");
			}else if (StrUtils.isNotEmpty(fieldNameOne)&&"0".equals(fieldNameOne)) 
			{
				dataVo.setFieldNameOne("不存在解析");
			}
    	}
    	Map map = new HashMap<String,Object>();
    	map.put("parse", totalParse);
		return  JsonTool.toJson(map);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getQueCountsByGradeAndSubject", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String getQueCountsByGradeAndSubject()
    {
    	List<DataCountsVo> totalGradeAndSubject = questionConvertService.getQueCountsByGradeAndSubject();
    	Map map = new HashMap<String,Object>();
    	map.put("gradeSubejct", totalGradeAndSubject);
		return  JsonTool.toJson(map);
    }
    
    @ResponseBody
    @RequestMapping(value = "/exportXls", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
    public String exportXls() {
		//第一步：查询所有的分区数据
		List<Map> list = new ArrayList<Map>();
		HashMap<String, Object> hashMap1 = new HashMap<String,Object>();
		HashMap<String, Object> hashMap2 = new HashMap<String,Object>();
		HashMap<String, Object> hashMap3 = new HashMap<String,Object>();
		HashMap<String, Object> hashMap4 = new HashMap<String,Object>();
		hashMap1.put("id", 1);
		hashMap1.put("name", "小明");
		hashMap1.put("grade", "三年级");
		hashMap1.put("subject", "数学");
		hashMap2.put("id", 2);
		hashMap2.put("name", "小明2");
		hashMap2.put("grade", "三年级");
		hashMap2.put("subject", "数学");
		hashMap3.put("id", 3);
		hashMap3.put("name", "小明3");
		hashMap3.put("grade", "三年级");
		hashMap3.put("subject", "数学");
		hashMap4.put("id", 4);
		hashMap4.put("name", "小明4");
		hashMap4.put("grade", "三年级");
		hashMap4.put("subject", "数学");
		list.add(hashMap1);
		list.add(hashMap2);
		list.add(hashMap3);
		list.add(hashMap4);
		//第二步：使用POI将数据写到Excel文件中
		//在内存中创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("姓名");
		headRow.createCell(2).setCellValue("年级");
		headRow.createCell(3).setCellValue("科目");
		for (Map map : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(map.get("id").toString());
			dataRow.createCell(1).setCellValue(map.get("name").toString());
			dataRow.createCell(2).setCellValue(map.get("grade").toString());
			dataRow.createCell(3).setCellValue(map.get("subject").toString());
		}
		//第三步：使用输出流进行文件下载（一个流、两个头）
		String filename = "123.xls";
		String contentType = request.getSession().getServletContext().getMimeType(filename);
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		//获取客户端浏览器类型
		String agent = request.getHeader("User-Agent");
		try {
			filename = FileUtils.encodeDownloadFilename(filename,agent);
			response.setHeader("content-disposition", "attachment;filename="+filename);
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			return "导出成功";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "导出失败";
		}
	}
    
    
    @ApiOperation("查询原题库数据")
    @ResponseBody
    @RequestMapping(value = "/dataHandler", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String uploadQuestionInfo(
            @ApiParam(value = "处理题库用数据", required = true) @RequestParam(value = "file") MultipartFile file)
    {
    	questionConvertService.dataHandler(file);
        return ResultVoTemplate.outputSuccess();
    }
    
    
    @ApiOperation(value = "将图片上传到七牛云存储")
    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST} )
    public String uploadQuestionImg( @ApiParam(value = "图片内容", required = false)  MultipartFile multipartFile)
    {
        String bucketName = "common-file";
        try {
            byte[] bytes  = multipartFile.getBytes();
            String fileName = multipartFile.getName();
            String uploadFile = QiniuUtil.uploadFile(fileName, new ByteArrayInputStream(bytes), bucketName, false);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", multipartFile.getName());//新的文件名
            map.put("size", multipartFile.getSize());
            map.put("state", "SUCCESS");
            map.put("url", uploadFile);
            String json = JsonTool.toJson(map);
            return json;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
      return null;
    }
}
