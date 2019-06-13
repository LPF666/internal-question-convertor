package com.eebbk.internal.question.convertor.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eebbk.edu.common.cloudfile.QiniuUtil;
import com.eebbk.edu.common.framework.core.holder.PageHolder;
import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.edu.common.util.exception.BizException;
import com.eebbk.edu.common.util.json.JsonTool;
import com.eebbk.edu.common.util.page.PageBean;
import com.eebbk.edu.common.util.page.PageParam;
import com.eebbk.edu.common.web.util.WebPathUtil;
import com.eebbk.edu.redis.JedisWrapper;
import com.eebbk.internal.question.convertor.constant.CommonConstant;
import com.eebbk.internal.question.convertor.constant.RedisKey;
import com.eebbk.internal.question.convertor.dao.QuestionMapper;
import com.eebbk.internal.question.convertor.enums.FailedStatusEnum;
import com.eebbk.internal.question.convertor.pojo.GradePojo;
import com.eebbk.internal.question.convertor.pojo.QuestionXuekubaoPojo;
import com.eebbk.internal.question.convertor.service.QuestionConvertService;
import com.eebbk.internal.question.convertor.util.AssignListUtils;
import com.eebbk.internal.question.convertor.util.DataFormatUtil;
import com.eebbk.internal.question.convertor.util.FtpUtil;
import com.eebbk.internal.question.convertor.util.HtmlTools;
import com.eebbk.internal.question.convertor.util.HtmlUtil;
import com.eebbk.internal.question.convertor.util.LogUtil;
import com.eebbk.internal.question.convertor.util.RedisUtil;
import com.eebbk.internal.question.convertor.vo.DataCountsVo;
import com.eebbk.internal.question.convertor.vo.GradeAndSubjectVo;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;
import com.eebbk.internal.question.convertor.vo.SubQuestionVo;

import redis.clients.jedis.JedisCluster;
@Service
public class QuestionConvertServiceImpl implements QuestionConvertService {
	@Autowired
    private QuestionMapper questionMapper;
    @Autowired
    PageHolder pageHolder;
	@Override
	public List<QuestionsVo> selectQuestion() {
		List<QuestionsVo> selectQuestion = questionMapper.selectQuestion();
		return selectQuestion;
	}

	@Override
	public void insertQuestionXuekubao(Integer pageNum, Integer pageSize) {
		List<QuestionsVo> questionList = questionMapper.selectQuestionPage(pageNum,pageSize);
		System.out.println(questionList.size());
		long start,end;
		start = System.currentTimeMillis();
		System.out.println("数据开始处理时间："+start);
		Iterator<QuestionsVo> iteratorQuestionList = questionList.iterator();
		QuestionXuekubaoPojo questionXuekubao=null;
		int count=0;
		List<QuestionXuekubaoPojo> questionXuekubaoList=new ArrayList<QuestionXuekubaoPojo>();
		while (iteratorQuestionList.hasNext()) {
			questionXuekubao=new QuestionXuekubaoPojo();
			System.out.println("开始处理第"+(++count)+"条数据");
			String content="";
			String answer="";
			StringBuffer answerSub=new StringBuffer("");
			StringBuffer parseSub=new StringBuffer("");
			String solution="";
			String course=null;
			String grade=null;
			String  picExistflagTitle=null;
			String  qiniuTitleFlag=null;
			String  qiniuContentFlag=null;
			String  qiniuAnswer1Flag=null;
			String  qiniuAnswer2Flag=null;
			String  qiniuSubAnswerFlag=null;
			String  qiniuParseFlag=null;
			String  qiniuSubParseFlag=null;
			String answer1Sub=null;
			String answer2Sub=null;
			Integer contentExistFlag=1;
			Integer answerExistFlag=1;
			Integer parseExistFlag=1;
			
//			String picExistFlagSubAnswer="picExistFlag:1";
//			String  picExistflagSubParse="picExistFlag:1";
			String picExistFlagSubAnswer=null;
			String  picExistflagSubParse=null;
			QuestionsVo questionsVo = iteratorQuestionList.next();
			StringBuffer titleBuffer = new StringBuffer("<div class=\"question_title\">");
			Integer id = questionsVo.getId();
			String title = questionsVo.getTitle();
			if (StrUtils.isNotEmpty(title)) {
				title=DataFormatUtil.formatUploadData(title);
			}else{
				contentExistFlag=0;
			}
			titleBuffer=titleBuffer.append(title).append("</div>");
			if (questionsVo.getId()==4093765) {
				System.out.println("");
			}
			String answer1 = questionsVo.getAnswer1();
			String answer2 = questionsVo.getAnswer2();
			if (StrUtils.isNotEmpty(answer1)&&StrUtils.isNotEmpty(answer2)) {
				answerExistFlag=1;
			}
			if (StrUtils.isEmpty(answer1)&&StrUtils.isEmpty(answer2)) {
				answerExistFlag=0;
			}
			if (StrUtils.isNotEmpty(answer1)) {
				StringBuffer as = new StringBuffer("");
				answer1=DataFormatUtil.formatUploadData(answer1);
				String[] split = answer1.split("\\$###\\$");
				for (int i = 0; i < split.length; i++) {
					if (StrUtils.isNotEmpty(split[i])) {
						if (split.length>1) {
							as=as.append("&nbsp;&nbsp;"+split[i]+"&nbsp;&nbsp;");
						}else{
							as=as.append(split[i]);
						}
					}
				}
				answer1=as.toString();
			}
			Map<String,Object> mapAnswer1=getNewImgsrc(answer1);
			Object errorAnswer1 = mapAnswer1.get("error");
			if (errorAnswer1!=null) {
				qiniuAnswer1Flag="0";
			}
			answer1=(String) mapAnswer1.get("msg");
			String  picExistflagAnswer1 = (String) mapAnswer1.get("picExistflag");
			if (StrUtils.isNotEmpty(answer2)) {
				answer2=DataFormatUtil.formatUploadData(answer2);
			}
			Map<String,Object> mapAnswer2=getNewImgsrc(answer2);
			Object errorAnswer2 = mapAnswer2.get("error");
			if (errorAnswer2!=null) {
				qiniuAnswer2Flag="0";
			}
			
			answer2=(String) mapAnswer2.get("msg");
			String  picExistflagAnswer2 = (String) mapAnswer2.get("picExistflag");
			String parse = questionsVo.getParse();
			if (StrUtils.isNotEmpty(parse)) {
				parse=DataFormatUtil.formatUploadData(parse);
			}else{
				parseExistFlag=0;
			}
			
			
			Map<String,Object> mapParse=getNewImgsrc(parse);
			Object errorParse = mapParse.get("error");
			if (errorParse!=null) {
				qiniuParseFlag="0";
			}
			parse=(String) mapParse.get("msg");
			String  picExistflagParse = (String) mapParse.get("picExistflag");
			

			
			String gradeName = questionsVo.getGradeName();
			String subjectName = questionsVo.getSubjectName();
			List<SubQuestionVo> subQuestionList=null;
			if (questionsVo!=null&&id!=null) {
				Integer isSub = questionsVo.getIsSub();
				if (isSub!=null&&1==isSub) {
					subQuestionList = questionMapper.selectSubQuestionByQueId(id);
				}
			}
			//如果存在子题，则区子题的题干与 母体的题干进行拼接
			StringBuffer subTitles=new StringBuffer("");
			StringBuffer subTitleOne=null;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        Integer testid= questionsVo.getId();
	        if (testid==17750141) {
				System.out.println(17750141);
			}
			if (CollectionUtils.isNotEmpty(subQuestionList)) {
				String  picExistflagSubAnswer1="picExistFlag:1";
				String  picExistflagSubAnswer2="picExistFlag:1";
				String qiniuSubAnswer1Flag="1";
				String qiniuSubAnswer2Flag="1";
				//判断是否有答案解析是否存在
				for (SubQuestionVo nextSubQue:subQuestionList){
					String answerSub1 = nextSubQue.getAnswer1();
					String answerSub2 = nextSubQue.getAnswer2();
					String parseSub0 = nextSubQue.getParse();
					if (1==answerExistFlag) {
					}else{
						if (StrUtils.isEmpty(answerSub1)&&StrUtils.isEmpty(answerSub2)) {
							answerExistFlag=0;
						}else{
							answerExistFlag=1;
						}
					}
					if (StrUtils.isNotEmpty(answerSub1)) {
						Map<String,Object> mapSubAnswer1=getNewImgsrc(answerSub1);
						Object errorSubAnswer1 = mapSubAnswer1.get("error");
						if (errorSubAnswer1!=null) {
							qiniuSubAnswer1Flag="0";
						}
						answerSub1=(String) mapSubAnswer1.get("msg");
						answerSub=answerSub.append(getAnwerText(answerSub1,null)).append("<br/>");

						picExistflagSubAnswer1 = (String) mapSubAnswer1.get("picExistflag");
					}
					if (StrUtils.isNotEmpty(answerSub2)) {
						Map<String,Object> mapSubAnswer2=getNewImgsrc(answerSub2);
						Object errorSubAnswer2 = mapSubAnswer2.get("error");
						if (errorSubAnswer2!=null) {
							qiniuSubAnswer2Flag="0";
						}
						answerSub2=(String) mapSubAnswer2.get("msg");
						answerSub=answerSub.append(getAnwerText(null,answerSub2)).append("<br/>");

						picExistflagSubAnswer2 = (String) mapSubAnswer2.get("picExistflag");
					}
					
					if (picExistflagSubAnswer1!=null&&picExistflagSubAnswer2!=null) {
						if (!"picExistFlag:1".equals(picExistflagSubAnswer1)&&!"picExistFlag:1".equals(picExistflagSubAnswer2)) {
							picExistFlagSubAnswer="picExistFlag:0";
						}
					}
					if (!"1".equals(qiniuSubAnswer1Flag)&&!"1".equals(qiniuSubAnswer2Flag)) {
						qiniuSubAnswerFlag="0";
					}
					
					if (1==parseExistFlag) {
					}else{
						if (StrUtils.isEmpty(parseSub0)) {
							parseExistFlag=0;
						}else{
							parseExistFlag=1;
							Map<String,Object> mapSubParse=getNewImgsrc(parseSub0);
							Object errorSubParse = mapSubParse.get("error");
							if (errorSubParse!=null) {
								qiniuSubParseFlag="0";
							}
							parseSub0=(String) mapSubParse.get("msg");
							parseSub=parseSub.append(parseSub0).append("<br/>");
							String picExistflagSubParseC = (String) mapSubParse.get("picExistflag");
							if ("picExistFlag:0".equals(picExistflagSubParseC)) {
								picExistflagSubParse=picExistflagSubParseC;
							}
						}
					}
				}
				Boolean flag=false;//是否为选择性的题
				Iterator<SubQuestionVo> iteratorSubQuestion = subQuestionList.iterator();
				//该循环判断是否是带选项的题
				
				for (SubQuestionVo nextSubQue:subQuestionList){
//					SubQuestionVo nextSubQue = iteratorSubQuestion.next();
					String optionA = nextSubQue.getOptionA();
					String optionB = nextSubQue.getOptionB();
					String optionC = nextSubQue.getOptionC();
					String optionD = nextSubQue.getOptionD();
					if (StrUtils.isNotEmpty(optionA)||StrUtils.isNotEmpty(optionB)||StrUtils.isNotEmpty(optionC)||StrUtils.isNotEmpty(optionD)) {
						flag=true;
					}
				}
				while (iteratorSubQuestion.hasNext()) {
					subTitleOne = new StringBuffer("<p class=\"question_option\">");
					SubQuestionVo nextSubQue = iteratorSubQuestion.next();
					String subTitle = nextSubQue.getTitle();
					if (StrUtils.isEmpty(subTitle)) {
						contentExistFlag=0;
					}
					String optionA = nextSubQue.getOptionA();
					String optionB = nextSubQue.getOptionB();
					String optionC = nextSubQue.getOptionC();
					String optionD = nextSubQue.getOptionD();
					
					if (StrUtils.isEmpty(optionA)&&StrUtils.isNotEmpty(optionB)) {
						contentExistFlag=0;
					}
					if (StrUtils.isNotEmpty(optionC)) {
						if (StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)) {
							contentExistFlag=0;
						}
					}
					if (StrUtils.isNotEmpty(optionD)) {
						if (StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)||StrUtils.isEmpty(optionC)) {
							contentExistFlag=0;
						}
					}
					subTitleOne=subTitleOne.append(subTitle).append("&nbsp;");
					if (flag) {
						if (StrUtils.isNotEmpty(optionA)) {
							subTitleOne=subTitleOne.append("A.").append(optionA).append("&nbsp;&nbsp;");
						}
						if (StrUtils.isNotEmpty(optionB)) {
							subTitleOne=subTitleOne.append("B.").append(optionB).append("&nbsp;&nbsp;");
						}
						if (StrUtils.isNotEmpty(optionC)) {
							subTitleOne=subTitleOne.append("C.").append(optionC).append("&nbsp;&nbsp;");
						}
						if (StrUtils.isNotEmpty(optionD)) {
							subTitleOne=subTitleOne.append("D.").append(optionD).append("&nbsp;&nbsp;");
						}
					}
					subTitleOne = subTitleOne.append("</p>");
					subTitles.append(subTitleOne);
				}
			}else{
				//isNormal：当试题为选择题并进行了题干与选项分离，则该字段为1，否则为0
				if (1==questionsVo.getIsNormal()) {
					subTitleOne = new StringBuffer("<p class=\"question_option\">");
					String optionA = questionsVo.getOptionA();
					if (StrUtils.isNotEmpty(optionA)) {
						subTitleOne=subTitleOne.append("A.").append(optionA).append("&nbsp;&nbsp;");
					}
					String optionB = questionsVo.getOptionB();				
					if (StrUtils.isNotEmpty(optionA)) {
						subTitleOne=subTitleOne.append("B.").append(optionB).append("&nbsp;&nbsp;");
					}
					String optionC = questionsVo.getOptionC();
					if (StrUtils.isNotEmpty(optionC)) {
						subTitleOne=subTitleOne.append("C.").append(optionC).append("&nbsp;&nbsp;");
					}
					String optionD = questionsVo.getOptionD();
					if (StrUtils.isNotEmpty(optionD)) {
						subTitleOne=subTitleOne.append("D.").append(optionD).append("&nbsp;&nbsp;");
					}
					String optionE = questionsVo.getOptionE();
					if (StrUtils.isNotEmpty(optionE)) {
						subTitleOne=subTitleOne.append("E.").append(optionE).append("&nbsp;&nbsp;");
					}
					if (StrUtils.isEmpty(optionA)&&StrUtils.isNotEmpty(optionB)) {
						contentExistFlag=0;
					}
					if (StrUtils.isNotEmpty(optionC)) {
						if (StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)) {
							contentExistFlag=0;
						}
					}
					
					if (StrUtils.isNotEmpty(optionD)) {
						if (StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)||StrUtils.isEmpty(optionC)) {
							contentExistFlag=0;
						}
					}
					if (StrUtils.isNotEmpty(optionE)) {
						if (StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)||StrUtils.isEmpty(optionC)||StrUtils.isEmpty(optionD)) {
							contentExistFlag=0;
						}
					}
					
					subTitleOne = subTitleOne.append("</p>");
					subTitles.append(subTitleOne);
				}
			}
			if (StrUtils.isNotEmpty(subTitles)) {
				title=title+subTitles.toString();
			}
			Map<String,Object> mapTitle=getNewImgsrc(title);
//			error
			Object errorTitle = mapTitle.get("error");
			if (errorTitle!=null) {
				qiniuTitleFlag="0";
			}
			title=(String) mapTitle.get("msg");
			picExistflagTitle = (String) mapTitle.get("picExistflag");
			content = HtmlTools.convertHtml(title,0);
			answer=getAnwerText(answer1,answer2);
			if (StrUtils.isNotEmpty(answer)) {
				answer=HtmlTools.convertHtml(answer,0);
			}else if (answerSub.length()>0) {
				answer=HtmlTools.convertHtml(answerSub.toString(),0);
			}
			if (StrUtils.isNotEmpty(parse)) {
				parse = HtmlTools.convertHtml(parse,0);
			}else if(parseSub.length()>0){
				parse=HtmlTools.convertHtml(parseSub.toString(),0);
			}
			Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = null;
			try {
				parseDate = sdf.parse(createTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            questionXuekubao.setIdOld(id);
            questionXuekubao.setContent(content);
            questionXuekubao.setCreateTime(parseDate);
            questionXuekubao.setGrade(gradeName);
            questionXuekubao.setCourse(subjectName);
            questionXuekubao.setSolution(parse);
            questionXuekubao.setAnswer(answer);
            questionXuekubao.setContentExistFlag(contentExistFlag);
            questionXuekubao.setAnswerExistFlag(answerExistFlag);
            questionXuekubao.setParseExistFlag(parseExistFlag);
            
            if ("picExistFlag:0".equals(picExistflagParse)||"picExistFlag:0".equals(picExistflagSubParse)||"picExistFlag:0".equals(picExistflagTitle)||"picExistFlag:0".equals(picExistflagAnswer2)
            		||"picExistFlag:0".equals(picExistflagAnswer1)||"picExistFlag:0".equals(picExistFlagSubAnswer)) {
				questionXuekubao.setPicExistflag(0);
			}else if (picExistflagParse==null&&picExistflagSubParse==null&&picExistflagTitle==null&&picExistflagAnswer1==null
						&&picExistflagAnswer2==null&&picExistFlagSubAnswer==null) {
				questionXuekubao.setPicExistflag(2);
			}else{
				questionXuekubao.setPicExistflag(1);
			}
            
            
            
            if ((qiniuTitleFlag!=null&&"0".equals(qiniuTitleFlag))||(qiniuParseFlag!=null&&"0".equals(qiniuParseFlag))||(qiniuSubParseFlag!=null&&"0".equals(qiniuSubParseFlag))
            		||(qiniuAnswer1Flag!=null&&"0".equals(qiniuAnswer1Flag))||(qiniuAnswer2Flag!=null&&"0".equals(qiniuAnswer2Flag))||(qiniuSubAnswerFlag!=null&&"0".equals(qiniuSubAnswerFlag))) {
            	questionXuekubao.setQiniuFlag(0);
			}else{
				questionXuekubao.setQiniuFlag(1);
			}
            questionXuekubaoList.add(questionXuekubao);
		}
		int sizeList = questionXuekubaoList.size();
		//将该集合以500条数据分为N个集合，分开存储
		List<List<QuestionXuekubaoPojo>> fixedGrouping = AssignListUtils.fixedGrouping(questionXuekubaoList, 500);
		end = System.currentTimeMillis();
		if (CollectionUtils.isNotEmpty(fixedGrouping)) {
			long startEnd,endEnd;
			startEnd = System.currentTimeMillis();
			int i=0;
			System.out.println("数据处理完毕，准备入库=============================，数据处理用时："+(end-start)/1000+"s");
			Iterator<List<QuestionXuekubaoPojo>> iterator = fixedGrouping.iterator();
			while (iterator.hasNext()) {
				List<QuestionXuekubaoPojo> next = iterator.next();
				if (CollectionUtils.isNotEmpty(next)) {
					i=i+next.size();
					questionMapper.insertQuestionXuekubaoBatch(next);
				}
				System.out.println("----执行线程入库共"+i+"条记录----------");
			}
			endEnd = System.currentTimeMillis();
			System.out.println("数据入库完毕=============================，入库用时："+(endEnd-startEnd)/1000+"s");

		}else{
			System.out.println("没有数据需要入库，本次数据处理时间=============================，用时："+(end-start)/1000+"s");
		}
	}

	
//	private String getImageUrl(String path, String name) {
//		String url = null;
//        try
//        {
//            url = QiniuUtil.uploadFile(name, path + name, "common-pic", false);
//        }
//        catch (Exception e)
//        {
//            throw new BizException(FailedStatusEnum.QINIU_UPLOAD_ERROR, "name:" + name);
//        }
//        return url;
//	}
	
	private static String getImageUrlFtp(String path, String name) {
		String url = null;
        try
        {
        	InputStream in = FtpUtil.getFileSteam(CommonConstant.FTP_ADDR,
    				21,CommonConstant.FTP_USER,
    				CommonConstant.FTP_PWD,CommonConstant.FTP_PATH, name
    				);
            url = QiniuUtil.uploadFile(name, in, "common-pic", false);
        }
        catch (Exception e)
        {
        	System.out.println("qiniu:error====="+e.toString());
        	return "qiniu:error";
        }
        return url;
	}
	
	
	private static Boolean fileIsExist(String file) {
		File fileName = new File(file);
		if (fileName.isDirectory()) {
			return false;
		}
		if (fileName.exists()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public PageBean selectTikubaoinfo(Map<String, Object> paramMap, PageParam pageParam) {
		return pageHolder.listPage(pageParam, paramMap, "selectXuebaoListPage", QuestionMapper.class);
	}
	
	@Override
	public PageBean selectShijuanTikubaoinfo(Map<String, Object> paramMap, PageParam pageParam) {
		return pageHolder.listPage(pageParam, paramMap, "selectShijuanXuebaoListPage", QuestionMapper.class);
	}

	@Override
	public String uploadJsFile(String fileName) {
		String jsPath=null;
		Boolean fileIsExist = fileIsExist(CommonConstant.PATH_LOCAL_TIKUPIC+fileName);
		if (fileIsExist) {
			jsPath=getImageUrl(CommonConstant.PATH_LOCAL_TIKUPIC, fileName);
		}
		return jsPath;
	}

	@Override
	public List<GradePojo> selectAllGrade() {
		return questionMapper.selectAllGrade();
	}

	@Override
	public List<GradeAndSubjectVo> selectAllSubjectByGradeName(String gradeName) 
	{
		//先从redis中获取
		//JedisWrapper jedis = RedisUtil.getJedis();
        JedisCluster jedis = RedisUtil.getJedis();
        String allGradeSubjectKey = RedisKey.JEDIS_GRADE_SUBJECT_KEY;
        //jedis.del(allGradeSubjectKey);
        String allGradeAndSubjectJson = jedis.get(allGradeSubjectKey);
        List<GradeAndSubjectVo> gradeAndSubjectList = new ArrayList<>();
        if (StringUtils.isNotEmpty(allGradeAndSubjectJson)) 
        {
        	gradeAndSubjectList = JsonTool.toObjects(allGradeAndSubjectJson, GradeAndSubjectVo.class);
        	return gradeAndSubjectList;
        }else {
        	gradeAndSubjectList=questionMapper.selectAllSubjectByGradeName(gradeName);
            //存储redis，过期时间为1小时   /100
            jedis.setex(allGradeSubjectKey, 360000, JsonTool.toJson(gradeAndSubjectList));
            return gradeAndSubjectList;
        }
	}

	@Override
	public List<QuestionXuekubaoQueryVo> getQuestionFileBysubject() 
	{
		return questionMapper.getQuestionFileBysubject();
	}

	@Override
	public List<DataCountsVo> getQueCountsNoImg() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String Key = RedisKey.JEDIS_PIC_INTO_KEY;
        String value= jedis.get(Key);
        if (StringUtils.isNotEmpty(value)) {
        	return  JsonTool.toObjects(value, DataCountsVo.class);
        }
        else
        {
        	//存储redis，过期时间为小时
            jedis.setex(Key, 3600, JsonTool.toJson(questionMapper.getQueCountsNoImg()));
        	return questionMapper.getQueCountsNoImg();
        }
	}

	@Override
	public Integer getTotalQueCounts() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String allTotalKey = RedisKey.JEDIS_TOTAL_KEY;
        String allTotal= jedis.get(allTotalKey);
        if (StringUtils.isNotEmpty(allTotal)) {
        	return Integer.valueOf(allTotal);
        }
        else
        {
        	//存储redis，过期时间为半小时
        	Integer totalQueCounts = questionMapper.getTotalQueCounts();
            jedis.setex(allTotalKey, 3600, totalQueCounts.toString());
        	return totalQueCounts;
        }
	}

	@Override
	public List<DataCountsVo> getNoExistSituationByContent() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String Key = RedisKey.JEDIS_CONTENT_INTO_KEY;
        String value= jedis.get(Key);
        if (StringUtils.isNotEmpty(value)) 
        {
        	return  JsonTool.toObjects(value, DataCountsVo.class);
        }
        else
        {
        	//存储redis，过期时间为半小时
            jedis.setex(Key, 3600, JsonTool.toJson(questionMapper.getNoExistSituationByContent()));
        	return questionMapper.getNoExistSituationByContent();
        }
	}

	@Override
	public List<DataCountsVo> getNoExistSituationByAnswer() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String Key = RedisKey.JEDIS_ANSWER_INTO_KEY;
        String value= jedis.get(Key);
        if (StringUtils.isNotEmpty(value)) 
        {
        	return  JsonTool.toObjects(value, DataCountsVo.class);
        }
        else
        {
        	//存储redis，过期时间为半小时
            jedis.setex(Key, 3600, JsonTool.toJson(questionMapper.getNoExistSituationByAnswer()));
        	return questionMapper.getNoExistSituationByAnswer();
        }
	}

	@Override
	public List<DataCountsVo> getNoExistSituationBySolution() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String Key = RedisKey.JEDIS_PARSE_INTO_KEY;
        String value= jedis.get(Key);
        if (StringUtils.isNotEmpty(value)) 
        {
        	return  JsonTool.toObjects(value, DataCountsVo.class);
        }else{
        	//存储redis，过期时间为半小时
            jedis.setex(Key, 3600, JsonTool.toJson(questionMapper.getNoExistSituationBySolution()));
        	return questionMapper.getNoExistSituationBySolution();
        }
	}

	@Override
	public List<DataCountsVo> getQueCountsByGradeAndSubject() 
	{
		JedisCluster jedis = RedisUtil.getJedis();
        String Key = RedisKey.JEDIS_GRADE_SUBJECT_INTO_KEY;
        String value= jedis.get(Key);
        if (StringUtils.isNotEmpty(value)) 
        {
        	return  JsonTool.toObjects(value, DataCountsVo.class);
        }
        else
        {
        	//存储redis，过期时间为半小时
            jedis.setex(Key, 3600, JsonTool.toJson(questionMapper.getQueCountsByGradeAndSubject()));
        	return questionMapper.getQueCountsByGradeAndSubject();
        }
	}

	@Override
	public List<DataCountsVo> getQueCountsByGrade() 
	{
		return questionMapper.getQueCountsByGrade();
	}

	@Override
	public List<DataCountsVo> getQueCountsBysubject() 
	{
		return questionMapper.getQueCountsBysubject();
	}

	@Override
	public void insertQuestionXuekubao(int pageNum, int pageSize) 
	{
		List<QuestionsVo> questionList = questionMapper.selectQuestionPage(pageNum,pageSize);
		System.out.println(questionList.size());
		long start,end;
		start = System.currentTimeMillis();
		System.out.println("数据开始处理时间："+start);
		Iterator<QuestionsVo> iteratorQuestionList = questionList.iterator();
		QuestionXuekubaoPojo questionXuekubao=null;
		int count=0;
		List<QuestionXuekubaoPojo> questionXuekubaoList=new ArrayList<QuestionXuekubaoPojo>();
		while (iteratorQuestionList.hasNext()) {
			questionXuekubao=new QuestionXuekubaoPojo();
			System.out.println("开始处理第"+(++count)+"条数据");
			String content=null;
			String answer=null;
			String solution=null;
			String course=null;
			String grade=null;
			String  picExistflagTitle=null;
			String  qiniuTitleFlag=null;
			String  qiniuContentFlag=null;
			String  qiniuAnswer1Flag=null;
			String  qiniuAnswer2Flag=null;
			String  qiniuParseFlag=null;
			Integer contentExistFlag=1;
			Integer answerExistFlag=1;
			Integer parseExistFlag=1;
			QuestionsVo questionsVo = iteratorQuestionList.next();
			StringBuffer titleBuffer = new StringBuffer("<div class=\"question_title\">");
			Integer id = questionsVo.getId();
			String title = questionsVo.getTitle();
			if (StrUtils.isNotEmpty(title)) 
			{
				title=DataFormatUtil.formatUploadData(title);
			}else{
				contentExistFlag=0;
			}
			titleBuffer=titleBuffer.append(title).append("</div>");
			if (questionsVo.getId()==4093765) 
			{
				System.out.println("");
			}
			String answer1 = questionsVo.getAnswer1();
			String answer2 = questionsVo.getAnswer2();
			if (StrUtils.isNotEmpty(answer1)&&StrUtils.isNotEmpty(answer2)) 
			{
				answerExistFlag=1;
			}
			if (StrUtils.isEmpty(answer1)&&StrUtils.isEmpty(answer2)) 
			{
				answerExistFlag=0;
			}
			if (StrUtils.isNotEmpty(answer1)) 
			{
				StringBuffer as = new StringBuffer("");
				answer1=DataFormatUtil.formatUploadData(answer1);
				String[] split = answer1.split("\\$###\\$");
				for (int i = 0; i < split.length; i++) {
					if (StrUtils.isNotEmpty(split[i])) {
						if (split.length>1) {
							as=as.append("&nbsp;&nbsp;"+split[i]+"&nbsp;&nbsp;");
						}else{
							as=as.append(split[i]);
						}
					}
				}
				answer1=as.toString();
			}
			Map<String,Object> mapAnswer1=getNewImgsrc(answer1);
			Object errorAnswer1 = mapAnswer1.get("error");
			if (errorAnswer1!=null) 
			{
				qiniuAnswer1Flag="0";
			}
			answer1=(String) mapAnswer1.get("msg");
			String  picExistflagAnswer1 = (String) mapAnswer1.get("picExistflag");
			if (StrUtils.isNotEmpty(answer2)) 
			{
				answer2=DataFormatUtil.formatUploadData(answer2);
			}
			Map<String,Object> mapAnswer2=getNewImgsrc(answer2);
			Object errorAnswer2 = mapAnswer2.get("error");
			if (errorAnswer2!=null) 
			{
				qiniuAnswer2Flag="0";
			}
			answer2=(String) mapAnswer2.get("msg");
			String  picExistflagAnswer2 = (String) mapAnswer2.get("picExistflag");
			String parse = questionsVo.getParse();
			if (StrUtils.isNotEmpty(parse)) 
			{
				parse=DataFormatUtil.formatUploadData(parse);
			}else{
				parseExistFlag=0;
			}
			Map<String,Object> mapParse=getNewImgsrc(parse);
			Object errorParse = mapParse.get("error");
			if (errorParse!=null) {
				qiniuParseFlag="0";
			}
			parse=(String) mapParse.get("msg");
			String  picExistflagParse = (String) mapParse.get("picExistflag");
			String gradeName = questionsVo.getGradeName();
			String subjectName = questionsVo.getSubjectName();
			List<SubQuestionVo> subQuestionList=null;
			if (questionsVo!=null&&id!=null) 
			{
				Integer isSub = questionsVo.getIsSub();
				if (isSub!=null&&1==isSub) 
				{
					subQuestionList = questionMapper.selectSubQuestionByQueId(id);
				}
			}
			//如果存在子题，则区子题的题干与 母体的题干进行拼接
			StringBuffer subTitles=new StringBuffer("");
			StringBuffer subTitleOne=null;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        Integer testid= questionsVo.getId();
	        if (testid==17750141) 
	        {
				System.out.println(17750141);
			}
			if (CollectionUtils.isNotEmpty(subQuestionList)) 
			{
				//判断是否有答案解析是否存在
				for (SubQuestionVo nextSubQue:subQuestionList){
					String answerSub1 = nextSubQue.getAnswer1();
					String answerSub2 = nextSubQue.getAnswer2();
					String parseSub = nextSubQue.getParse();
					if (1==answerExistFlag) {
					}else{
						if (StrUtils.isEmpty(answerSub1)&&StrUtils.isEmpty(answerSub2)) {
							answerExistFlag=0;
						}else{
							answerExistFlag=1;
						}
					}
					
					if (1==parseExistFlag) 
					{
					}else{
						if (StrUtils.isEmpty(parseSub)) 
						{
							parseExistFlag=0;
						}else{
							parseExistFlag=1;
						}
					}
				}
				Boolean flag=false;//是否为选择性的题
				Iterator<SubQuestionVo> iteratorSubQuestion = subQuestionList.iterator();
				//该循环判断是否是带选项的题
				for (SubQuestionVo nextSubQue:subQuestionList){
					String optionA = nextSubQue.getOptionA();
					String optionB = nextSubQue.getOptionB();
					String optionC = nextSubQue.getOptionC();
					String optionD = nextSubQue.getOptionD();
					if (StrUtils.isNotEmpty(optionA)||StrUtils.isNotEmpty(optionB)||StrUtils.isNotEmpty(optionC)||StrUtils.isNotEmpty(optionD)) {
						flag=true;
					}
				}
				while (iteratorSubQuestion.hasNext()) 
				{
					subTitleOne = new StringBuffer("<p class=\"question_option\">");
					SubQuestionVo nextSubQue = iteratorSubQuestion.next();
					String subTitle = nextSubQue.getTitle();
					if (StrUtils.isEmpty(subTitle)) {
						contentExistFlag=0;
					}
					String optionA = nextSubQue.getOptionA();
					String optionB = nextSubQue.getOptionB();
					String optionC = nextSubQue.getOptionC();
					String optionD = nextSubQue.getOptionD();
					
					if (StrUtils.isEmpty(optionA)&&StrUtils.isNotEmpty(optionB)) {
						contentExistFlag=0;
					}
					if (StrUtils.isNotEmpty(optionC)&&(StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB))) {
						contentExistFlag=0;
					}
					if (StrUtils.isNotEmpty(optionD)&&(StrUtils.isEmpty(optionA)||StrUtils.isEmpty(optionB)||StrUtils.isEmpty(optionC))) {
						contentExistFlag=0;
					}
					subTitleOne=subTitleOne.append(subTitle).append("&nbsp;");
					if (flag) {
						subTitleOne=subTitleOne.append("A.").append(optionA).append("&nbsp;&nbsp;");
						subTitleOne=subTitleOne.append("B.").append(optionB).append("&nbsp;&nbsp;");
						subTitleOne=subTitleOne.append("C.").append(optionC).append("&nbsp;&nbsp;");
						subTitleOne=subTitleOne.append("D.").append(optionD).append("&nbsp;&nbsp;");
					}
					subTitleOne = subTitleOne.append("</p>");
					subTitles.append(subTitleOne);
				}
			}else{
				//isNormal：当试题为选择题并进行了题干与选项分离，则该字段为1，否则为0
				if (1==questionsVo.getIsNormal()) {
					subTitleOne = new StringBuffer("<p class=\"question_option\">");
					String optionA = questionsVo.getOptionA();
					subTitleOne=subTitleOne.append("A.").append(optionA).append("&nbsp;&nbsp;");
					String optionB = questionsVo.getOptionB();				
					subTitleOne=subTitleOne.append("B.").append(optionB).append("&nbsp;&nbsp;");
					String optionC = questionsVo.getOptionC();
					subTitleOne=subTitleOne.append("C.").append(optionC).append("&nbsp;&nbsp;");
					String optionD = questionsVo.getOptionD();
					subTitleOne=subTitleOne.append("D.").append(optionD).append("&nbsp;&nbsp;");
					String optionE = questionsVo.getOptionE();
					subTitleOne=subTitleOne.append("E.").append(optionE).append("&nbsp;&nbsp;");
					if (StrUtils.isEmpty(optionA)&&StrUtils.isNotEmpty(optionB)) {
						contentExistFlag=0;
					}
					subTitleOne = subTitleOne.append("</p>");
					subTitles.append(subTitleOne);
				}
			}
			if (StrUtils.isNotEmpty(subTitles)) 
			{
				title=title+subTitles.toString();
			}
			Map<String,Object> mapTitle=getNewImgsrc(title);
//			error
			Object errorTitle = mapTitle.get("error");
			if (errorTitle!=null) {
				qiniuTitleFlag="0";
			}
			title=(String) mapTitle.get("msg");
			picExistflagTitle = (String) mapTitle.get("picExistflag");
			content = HtmlTools.convertHtml(title,0);
			if (StrUtils.isNotEmpty(answer1)&&StrUtils.isNotEmpty(answer2)) 
			{
				Pattern panswer2 = Pattern.compile("^【(.*)$");
		        Matcher manswer2 = panswer2.matcher(answer2);
				if (manswer2.find()) {
					answer = HtmlTools.convertHtml(answer1+"<br />"+answer2,0);
				}else{
					Pattern p = Pattern.compile("^m\\{(.*)}$");
			        Matcher m = p.matcher(answer1);
			        String regexAnswer1="";
			        if (m.find()) {
						System.out.println("匹配上了");
						String[] split = answer1.split("m\\{");
						for (int i = 1; i < split.length; i++) {
							regexAnswer1=regexAnswer1+split[i].charAt(0);
							answer1=regexAnswer1;
						}
					}
					answer=HtmlTools.convertHtml(answer1,0);
				}
			}else if (StrUtils.isNotEmpty(answer1)) {
				answer = HtmlTools.convertHtml(answer1,0);
			}else if(StrUtils.isNotEmpty(answer2)){
				answer = HtmlTools.convertHtml(answer2,0);
			}
			parse = HtmlTools.convertHtml(parse,0);
			Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = null;
			try {
				parseDate = sdf.parse(createTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            questionXuekubao.setIdOld(id);
            questionXuekubao.setContent(content);
            questionXuekubao.setCreateTime(parseDate);
            questionXuekubao.setGrade(gradeName);
            questionXuekubao.setCourse(subjectName);
            questionXuekubao.setSolution(parse);
            questionXuekubao.setAnswer(answer);
            questionXuekubao.setContentExistFlag(contentExistFlag);
            questionXuekubao.setAnswerExistFlag(answerExistFlag);
            questionXuekubao.setParseExistFlag(parseExistFlag);
            
            if ("picExistFlag:0".equals(picExistflagParse)||"picExistFlag:0".equals(picExistflagTitle)||"picExistFlag:0".equals(picExistflagAnswer2)) {
				questionXuekubao.setPicExistflag(0);
			}else if (picExistflagParse==null&&picExistflagTitle==null&&picExistflagAnswer1==null&&picExistflagAnswer2==null) {
				questionXuekubao.setPicExistflag(2);
			}else{
				questionXuekubao.setPicExistflag(1);
			}
            if ((qiniuTitleFlag!=null&&"0".equals(qiniuTitleFlag))||(qiniuParseFlag!=null&&"0".equals(qiniuParseFlag))||(qiniuAnswer1Flag!=null&&"0".equals(qiniuAnswer1Flag))||(qiniuAnswer2Flag!=null&&"0".equals(qiniuAnswer2Flag))) {
            	questionXuekubao.setQiniuFlag(0);
			}else{
				questionXuekubao.setQiniuFlag(1);
			}
            questionXuekubaoList.add(questionXuekubao);
		}
		int sizeList = questionXuekubaoList.size();
		//将该集合以500条数据分为N个集合，分开存储
		List<List<QuestionXuekubaoPojo>> fixedGrouping = AssignListUtils.fixedGrouping(questionXuekubaoList, 500);
		end = System.currentTimeMillis();
		if (CollectionUtils.isNotEmpty(fixedGrouping)) {
			long startEnd,endEnd;
			startEnd = System.currentTimeMillis();
			int i=0;
			System.out.println("数据处理完毕，准备入库=============================，数据处理用时："+(end-start)/1000+"s");
			Iterator<List<QuestionXuekubaoPojo>> iterator = fixedGrouping.iterator();
			while (iterator.hasNext()) {
				List<QuestionXuekubaoPojo> next = iterator.next();
				if (CollectionUtils.isNotEmpty(next)) {
					i=i+next.size();
					questionMapper.insertQuestionXuekubaoBatch(next);
				}
				System.out.println("----执行线程入库共"+i+"条记录----------");
			}
			endEnd = System.currentTimeMillis();
			System.out.println("数据入库完毕=============================，入库用时："+(endEnd-startEnd)/1000+"s");

		}else{
			System.out.println("没有数据需要入库，本次数据处理时间=============================，用时："+(end-start)/1000+"s");
		}
	}
	private static String getImageUrl(String path, String name) {
		if ("/upimages/quiz/images/201507/221/076e7015.png".equals(name)) {
			System.out.println("Error:"+name);
		}
		String url = null;
        try
        {
            url = QiniuUtil.uploadFile(name, path + name, "common-file", false);
        }
        catch (Exception e)
        {
        	// throw new BizException(FailedStatusEnum.QINIU_UPLOAD_ERROR, "name:" + name);
        	System.out.println("qiniu:error====="+e.toString());
        	return "qiniu:error";
        }
        return url;
	}
	
	private static Map<String,Object> getNewImgsrc(String msg) {
		List<String> srcImgList = HtmlUtil.extralImg(msg);
		String picExistflag=null;
		Map<String,Object> map=new HashMap<String,Object>();
		if (CollectionUtils.isNotEmpty(srcImgList)) 
		{
			Iterator<String> iteratorImg = srcImgList.iterator();
			
			while (iteratorImg.hasNext()) {
				String replaceImg=null;
				String nextImg = iteratorImg.next();
				Boolean fileIsExist = fileIsExist(CommonConstant.PATH_LOCAL_TIKUPIC+nextImg);
//				Boolean fileIsExist=FtpUtil.fileIsExistFTP(CommonConstant.FTP_ADDR,
//	    				21,CommonConstant.FTP_USER,
//	    				CommonConstant.FTP_PWD,CommonConstant.FTP_PATH, nextImg);
				if (fileIsExist) {
					replaceImg=getImageUrl(CommonConstant.PATH_LOCAL_TIKUPIC, nextImg);
//					replaceImg=getImageUrlFtp(CommonConstant.PATH_LOCAL_TIKUPIC, nextImg);
					picExistflag="picExistFlag:1";
				}else{
					picExistflag="picExistFlag:0";
				}
				if ((StrUtils.isNotEmpty(replaceImg)) && (!("qiniu:error".equals(replaceImg))))
		        {
		          msg = msg.replaceAll(nextImg, replaceImg);
		        } else if ("qiniu:error".equals(replaceImg)) {
		          map.put("error", "qiniu:error");
		        }
			}
			
		}
		map.put("msg", msg);
		map.put("picExistflag", picExistflag);
		return map;
	}
	public static String getAnwerText(String answer1,String answer2){
		String answer=null;
	if (StrUtils.isNotEmpty(answer1)&&StrUtils.isNotEmpty(answer2)) {
		Pattern panswer2 = Pattern.compile("^【(.*)$");
        Matcher manswer2 = panswer2.matcher(answer2);
		if (manswer2.find()) {
			answer = answer1+"<br />"+answer2;
		}else{
			Pattern p = Pattern.compile("^m\\{(.*)}$");
	        Matcher m = p.matcher(answer1);
	        String regexAnswer1="";
	        if (m.find()) {
				System.out.println("匹配上了");
				String[] split = answer1.split("m\\{");
				for (int i = 1; i < split.length; i++) {
					regexAnswer1=regexAnswer1+split[i].charAt(0);
					answer1=regexAnswer1;
				}
			}
			answer=answer1;
		}
	}else if (StrUtils.isNotEmpty(answer1)) {
		answer = answer1;
	}else if(StrUtils.isNotEmpty(answer2)){
		answer = answer2;
	}
	return answer;
}

	@Override
	public void dataHandler(MultipartFile file) {
		LogUtil.info("处理题库信息数据...");
		String originalFilename = file.getOriginalFilename();
		String webRoot = WebPathUtil.SINGLE.getWebRoot();
        if (!originalFilename.endsWith("txt"))
        {
        	return;
        }
        List<Long> queLists=new ArrayList<Long>();
        InputStreamReader read=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			read = new InputStreamReader(file.getInputStream(),"utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuffer txtInfo = new StringBuffer("");
            String lineTxtJson="";
            Integer count=0;
            Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = sdf.parse(createTime);
            while((lineTxtJson = bufferedReader.readLine()) != null){
            	++count;
                System.out.println("lineTxt="+lineTxtJson);
                txtInfo.append(lineTxtJson+"\n");
                if (count==10) {
					break;
				}
            }
            read.close();
            if (CollectionUtils.isNotEmpty(queLists)) {
            	questionMapper.selectQuesitonsListByIds(queLists);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}