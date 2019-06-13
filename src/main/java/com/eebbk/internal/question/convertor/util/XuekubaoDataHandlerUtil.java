package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.InputStream;
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

import com.eebbk.edu.common.cloudfile.QiniuUtil;
import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.internal.question.convertor.constant.CommonConstant;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.pojo.QuestionXuekubaoPojo;
import com.eebbk.internal.question.convertor.util.AssignListUtils;
import com.eebbk.internal.question.convertor.util.DataFormatUtil;
import com.eebbk.internal.question.convertor.util.FtpUtil;
import com.eebbk.internal.question.convertor.util.HtmlTools;
import com.eebbk.internal.question.convertor.util.HtmlUtil;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;
import com.eebbk.internal.question.convertor.vo.SubQuestionVo;

public class XuekubaoDataHandlerUtil {
	public static void insertQuestionXuekubao(List<QuestionsVo> questionList,QuestionMapper2 questionMapper,Integer tableFlag,Integer handerWay) {
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
			QuestionsVo questionsVo = iteratorQuestionList.next();
			Integer paperFlag = questionsVo.getPaperFlag();
			Integer idOldTest = questionsVo.getId();
			System.out.println("开始处理第"+(++count)+"条数据,id_old="+questionsVo.getId());
			String content="";
			String answer="";
			StringBuffer answerSub=new StringBuffer("");
			StringBuffer parseSub=new StringBuffer("");
			String solution="";
			String course=null;
			String grade=null;
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
			String picExistFlagSubAnswer=null;
			String  picExistflagSubParse=null;
			Map<String,Integer>  contentUrlFlags=null;
			Map<String,Integer>  answer1UrlFlags=null;
			Map<String,Integer>  answer1SubUrlFlags=null;
			Map<String,Integer>  answer2UrlFlags=null;
			Map<String,Integer>  answer2SubUrlFlags=null;
			Map<String,Integer>  parseUrlFlags=null;
			Map<String,Integer>  parseSubUrlFlags=null;
			StringBuffer titleBuffer = new StringBuffer("<div class=\"question_title\">");
			Integer id = questionsVo.getId();
			String title = questionsVo.getTitle();
			if (StrUtils.isNotEmpty(title)) {
				title=DataFormatUtil.formatUploadData(title);
			}else{
				contentExistFlag=0;
			}
			titleBuffer=titleBuffer.append(title).append("</div>");
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
			String  picExistflagAnswer1=null;
			if (tableFlag==0) {
				Map<String,Object> mapAnswer1=getNewImgsrc(answer1);
				Object errorAnswer1 = mapAnswer1.get("error");
				if (errorAnswer1!=null) {
					qiniuAnswer1Flag="0";
				}
				answer1=(String) mapAnswer1.get("msg");
				picExistflagAnswer1 = (String) mapAnswer1.get("picExistflag");
				answer1UrlFlags=(Map<String, Integer>) mapAnswer1.get("urlFlags");

			}else if (tableFlag==1) {
				Map<String,Object> mapAnswer1=getImgUrlsFlags(answer1);
				answer1UrlFlags=(Map<String, Integer>) mapAnswer1.get("urlFlags");
			}
			
			
			if (StrUtils.isNotEmpty(answer2)) {
				answer2=DataFormatUtil.formatUploadData(answer2);
			}
			String  picExistflagAnswer2=null;
			if (tableFlag==0) {
				Map<String,Object> mapAnswer2=getNewImgsrc(answer2);
				Object errorAnswer2 = mapAnswer2.get("error");
				if (errorAnswer2!=null) {
					qiniuAnswer2Flag="0";
				}
				answer2=(String) mapAnswer2.get("msg");
				picExistflagAnswer2 = (String) mapAnswer2.get("picExistflag");
				answer2UrlFlags=(Map<String, Integer>) mapAnswer2.get("urlFlags");
			}else if (tableFlag==1) {
				Map<String,Object> mapAnswer2=getImgUrlsFlags(answer2);
				answer2UrlFlags=(Map<String, Integer>) mapAnswer2.get("urlFlags");
			}
			
			
			String parse = questionsVo.getParse();
			if (StrUtils.isNotEmpty(parse)) {
				parse=DataFormatUtil.formatUploadData(parse);
			}else{
				parseExistFlag=0;
			}
			String  picExistflagParse=null;
			if (tableFlag==0) {
				Map<String,Object> mapParse=getNewImgsrc(parse);
				Object errorParse = mapParse.get("error");
				if (errorParse!=null) {
					qiniuParseFlag="0";
				}
				parse=(String) mapParse.get("msg");
				picExistflagParse = (String) mapParse.get("picExistflag");
				parseUrlFlags=(Map<String, Integer>) mapParse.get("urlFlags");

			}else if (tableFlag==1) {
				Map<String,Object> mapParse=getImgUrlsFlags(parse);
				parseUrlFlags=(Map<String, Integer>) mapParse.get("urlFlags");
			}
			
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
						if (tableFlag==1) {
							Map<String,Object> mapSubAnswer1=getImgUrlsFlags(answerSub1);
							answer1SubUrlFlags=(Map<String, Integer>) mapSubAnswer1.get("urlFlags");
						}else if (tableFlag==0){
							Map<String,Object> mapSubAnswer1=getNewImgsrc(answerSub1);
							Object errorSubAnswer1 = mapSubAnswer1.get("error");
							if (errorSubAnswer1!=null) {
								qiniuSubAnswer1Flag="0";
							}
							answerSub1=(String) mapSubAnswer1.get("msg");
							answerSub=answerSub.append(getAnwerText(answerSub1,null)).append("<br/>");

							picExistflagSubAnswer1 = (String) mapSubAnswer1.get("picExistflag");
							answer1SubUrlFlags=(Map<String, Integer>) mapSubAnswer1.get("urlFlags");
						}
						
					}
					if (StrUtils.isNotEmpty(answerSub2)) {
						if (tableFlag==1) {
							Map<String,Object> mapSubAnswer2=getImgUrlsFlags(answerSub2);
							answer2SubUrlFlags=(Map<String, Integer>) mapSubAnswer2.get("urlFlags");
						}else if (tableFlag==0){
							Map<String,Object> mapSubAnswer2=getNewImgsrc(answerSub2);
							Object errorSubAnswer2 = mapSubAnswer2.get("error");
							if (errorSubAnswer2!=null) {
								qiniuSubAnswer2Flag="0";
							}
							answerSub2=(String) mapSubAnswer2.get("msg");
							answerSub=answerSub.append(getAnwerText(null,answerSub2)).append("<br/>");
							picExistflagSubAnswer2 = (String) mapSubAnswer2.get("picExistflag");
							answer2SubUrlFlags=(Map<String, Integer>) mapSubAnswer2.get("urlFlags");
						}
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
							if(tableFlag==0) {
								Map<String,Object> mapSubParse=getImgUrlsFlags(parseSub0);
								parseSubUrlFlags=(Map<String, Integer>) mapSubParse.get("urlFlags");
							}else if(tableFlag==1){
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
								parseSubUrlFlags=(Map<String, Integer>) mapSubParse.get("urlFlags");
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
				Integer a=1;
				boolean ans=false;
				if (answer1!=null) {
					if ("0".equals(answer1)||"1".equals(answer1)||"2".equals(answer1)||"3".equals(answer1)) {
						ans=true;
					}else if (answer1.contains("&nbsp;0&nbsp;")||answer1.contains("&nbsp;1&nbsp;")
							||answer1.contains("&nbsp;2&nbsp;")||answer1.contains("&nbsp;3&nbsp;")
							||answer1.contains("&nbsp;4&nbsp;")||answer1.contains("A")||answer1.contains("B")
							||answer1.contains("C")||answer1.contains("D")||answer1.contains("E")) {
						ans=true;
					}
				}
				
				if (a==questionsVo.getIsNormal()||ans) {
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
				}else{
					subTitleOne = new StringBuffer("<p class=\"question_option\">");
					String optionA = questionsVo.getOptionA();
					if (StrUtils.isNotEmpty(optionA)) {
						subTitleOne=subTitleOne.append(optionA).append("&nbsp;&nbsp;").append("</br>");
					}
					String optionB = questionsVo.getOptionB();				
					if (StrUtils.isNotEmpty(optionA)) {
						subTitleOne=subTitleOne.append(optionB).append("&nbsp;&nbsp;").append("</br>");
					}
					String optionC = questionsVo.getOptionC();
					if (StrUtils.isNotEmpty(optionC)) {
						subTitleOne=subTitleOne.append(optionC).append("&nbsp;&nbsp;").append("</br>");
					}
					String optionD = questionsVo.getOptionD();
					if (StrUtils.isNotEmpty(optionD)) {
						subTitleOne=subTitleOne.append(optionD).append("&nbsp;&nbsp;").append("</br>");
					}
					String optionE = questionsVo.getOptionE();
					if (StrUtils.isNotEmpty(optionE)) {
						subTitleOne=subTitleOne.append(optionE).append("&nbsp;&nbsp;").append("</br>");
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
			if (testid==15788598) {
				System.out.println(15788598);
			}
			String  picExistflagTitle=null;
			if (tableFlag==0) {
				Map<String,Object> mapTitle=getNewImgsrc(title);
				Object errorTitle = mapTitle.get("error");
				if (errorTitle!=null) {
					qiniuTitleFlag="0";
				}
				picExistflagTitle = (String) mapTitle.get("picExistflag");
				title=(String) mapTitle.get("msg");
				contentUrlFlags=(Map<String, Integer>) mapTitle.get("urlFlags");
			}else if (tableFlag==1) {
				Map<String,Object> mapTitle=getImgUrlsFlags(title);
				contentUrlFlags=(Map<String, Integer>) mapTitle.get("urlFlags");
			}
			
			content = HtmlTools.convertHtml(title,tableFlag);
			answer=getAnwerText(answer1,answer2);
			if (StrUtils.isNotEmpty(answer)) {
				answer=HtmlTools.convertHtml(answer,tableFlag);
			}else if (answerSub.length()>0) {
				answer=HtmlTools.convertHtml(answerSub.toString(),tableFlag);
			}
			if (StrUtils.isNotEmpty(parse)) {
				parse = HtmlTools.convertHtml(parse,tableFlag);
			}else if(parseSub.length()>0){
				parse=HtmlTools.convertHtml(parseSub.toString(),tableFlag);
			}
			Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = null;
			try {
				parseDate = sdf.parse(createTime);
			} catch (ParseException e) {
				parseDate=new Date();
				System.out.println("error:ParseException=====");
//				e.printStackTrace();
			}
            questionXuekubao.setIdOld(id);
            questionXuekubao.setContent(content);
            questionXuekubao.setCreateTime(parseDate);
            questionXuekubao.setGrade(gradeName);
            questionXuekubao.setCourse(subjectName);
            questionXuekubao.setSolution(parse);
            questionXuekubao.setAnswer(answer);
            questionXuekubao.setPaperFlag(paperFlag);
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
			Integer urlImgFlag=0;
			Integer urlTikupicFlag=0;
			Integer urlUpimagesFlag=0;
			Integer urlUpimgFlag=0;
			Integer urlUploadFlag=0;
			Integer urlUserFlag=0;
			Integer urlZximagesFlag=0;
			Map<String, Integer> finalFlags = getFinalFlags(contentUrlFlags, answer1UrlFlags, answer1SubUrlFlags, answer2UrlFlags,
					answer2SubUrlFlags, parseUrlFlags, parseSubUrlFlags);
			
			if (finalFlags!=null) {
				questionXuekubao.setUrlImgFlag(finalFlags.get("urlImgFlag"));
				questionXuekubao.setUrlTikupicFlag(finalFlags.get("urlTikupicFlag"));
				questionXuekubao.setUrlUpimagesFlag(finalFlags.get("urlUpimagesFlag"));
				questionXuekubao.setUrlUpimgFlag(finalFlags.get("urlUpimgFlag"));
				questionXuekubao.setUrlUploadFlag(finalFlags.get("urlUploadFlag"));
				questionXuekubao.setUrlUserFlag(finalFlags.get("urlUserFlag"));
				questionXuekubao.setUrlZximagesFlag(finalFlags.get("urlZximagesFlag"));
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
			Integer dataCount=0;
			while (iterator.hasNext()) {
				List<QuestionXuekubaoPojo> next = iterator.next();
				if (CollectionUtils.isNotEmpty(next)) {
					++dataCount;
					i=i+next.size();
					if (tableFlag==0) {
						if (handerWay==1) {
							System.out.println("update .....");
							questionMapper.updateQuestionXuekubaoBatch(next);
						}else{
							System.out.println("insert .....");
							questionMapper.insertQuestionXuekubaoBatch(next);
						}
					}else if (tableFlag==1) {
						if (handerWay==1) {
							System.out.println("update paper.....");
							questionMapper.updateQuestionXuekubaoPaperBatch(next);
						}else{
							System.out.println("insert paper.....");
							questionMapper.insertQuestionXuekubaoPaperBatch(next);
						}
					}
				}
				System.out.println("----执行线程入库共"+i+"条记录----------");
			}
			endEnd = System.currentTimeMillis();
			System.out.println("数据入库完毕=============================，入库用时："+(endEnd-startEnd)/1000+"s");

		}else{
			System.out.println("没有数据需要入库，本次数据处理时间=============================，用时："+(end-start)/1000+"s");
		}
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
				if (nextImg.contains("http")) {
					continue;
				}
				//v:shapes="image0070.jpeg"----15788598
				String[] splitNextImg = nextImg.split("/");
				if (splitNextImg.length<=2) {
					continue;
				}
				if (nextImg.contains("file:///")) {
					continue;
				}
				Boolean fileIsExist = HandlerFileNameSuffix.fileIsExist(CommonConstant.PATH_LOCAL_TIKUPIC+nextImg);
//				Boolean fileIsExist=FtpUtil.fileIsExistFTP(CommonConstant.FTP_ADDR,
//	    				21,CommonConstant.FTP_USER,
//	    				CommonConstant.FTP_PWD,CommonConstant.FTP_PATH, nextImg);
				if (fileIsExist) {
					replaceImg=getImageUrlFtp(CommonConstant.PATH_LOCAL_TIKUPIC, nextImg);
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
	
	
	private static String getImageUrl(String path, String name) {
		if ("/upimages/quiz/images/201507/221/076e7015.png".equals(name)) {
			System.out.println("Error:"+name);
		}
		String url = null;
        try
        {
            url = QiniuUtil.uploadFile(name, path + name, "common-pic", false);
            Thread.sleep(100);
        }
        catch (Exception e)
        {
//            throw new BizException(FailedStatusEnum.QINIU_UPLOAD_ERROR, "name:" + name);
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
	
	private static String getImageUrlFtp(String path, String name) {
		String url = null;
		StringBuffer bfImg=new StringBuffer("");
        try
        {
        	InputStream in = HandlerFileNameSuffix.getLocalFileSteam(CommonConstant.PATH_LOCAL_TIKUPIC, name);
//        	InputStream in = FtpUtil.getFileSteam(CommonConstant.FTP_ADDR,
//        			21,CommonConstant.FTP_USER,
//        			CommonConstant.FTP_PWD,CommonConstant.FTP_PATH, name
//        			);
        	name = HandlerFileNameSuffix.getCorrectFileName(name);
            url = QiniuUtil.uploadFile(name, in, "common-pic", false);
            Thread.sleep(100);
        }
        catch (Exception e)
        {
        	System.out.println("qiniu:error====="+e.toString());
        	return "qiniu:error";
        }
        return url;
	}
	public static String getAnwerText(String answer1,String answer2){
			String answer=null;
		if (StrUtils.isNotEmpty(answer1)&&StrUtils.isNotEmpty(answer2)) {
			Pattern panswer2 = Pattern.compile("^【(.*)$");
	        Matcher manswer2 = panswer2.matcher(answer2);
	        
	        Pattern panswer3 = Pattern.compile("^[0-9]【(.*)$");
	        Matcher manswer3 = panswer3.matcher(answer2);
			if (manswer2.find()) {
				//有的题的答案给的是0,1,2这样的形式
				if ("0".equals(answer1)||answer1.contains("&nbsp;0&nbsp;")) {
					answer1=answer1.replaceFirst("0", "A");
				}else if ("1".equals(answer1)||answer1.contains("&nbsp;1&nbsp;")) {
					answer1=answer1.replaceFirst("1", "B");
				}else if ("2".equals(answer1)||answer1.contains("&nbsp;2&nbsp;")) {
					answer1=answer1.replaceFirst("2", "C");
				}else if ("3".equals(answer1)||answer1.contains("&nbsp;3&nbsp;")) {
					answer1=answer1.replaceFirst("3", "D");
				}else if ("4".equals(answer1)||answer1.contains("&nbsp;4&nbsp;")) {
					answer1=answer1.replaceFirst("4", "E");
				}
				//有的答案在ansewer里面2【解析】
				if(manswer3.find()){
					///答案2中出现的问题  1【解析】
					answer = answer1+"  ";
					String A=answer2.substring(0,1);
					if (A!=null&&A.equals("0")) {
						answer2=answer2.replaceFirst(A, "A");
					}else if (A!=null&&A.equals("1")) {
						answer2=answer2.replaceFirst(A, "B");
					}else if (A!=null&&A.equals("2")) {
						answer2=answer2.replaceFirst(A, "C");
					}else if (A!=null&&A.equals("3")) {
						answer2=answer2.replaceFirst(A, "D");
					}
				}
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
	/**
	 * @author lipf
	 * 判断图片url的路径
	 * @method getImgUrlsFlags
	 * @param msg
	 * @return
	 * @return Map<String,Object>
	 * @date 2019年5月31日 上午8:51:16
	 */
	private static Map<String,Object> getImgUrlsFlags(String msg) {
		Map<String, Integer> urlFlags = RegexUtil.getUrlFlags(msg);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("urlFlags", urlFlags);
		return map;
	}
	/**
	 * @author lipf
	 * TODO将带有图片的路径分类过滤
	 * @method getFinalFlags
	 * @param contentUrlFlags
	 * @param answer1UrlFlags
	 * @param answer1SubUrlFlags
	 * @param answer2UrlFlags
	 * @param answer2SubUrlFlags
	 * @param parseUrlFlags
	 * @param parseSubUrlFlags
	 * @return
	 * @return Integer
	 * @date 2019年5月31日 上午10:09:13
	 */
	private static Map<String,Integer> getFinalFlags(Map<String,Integer>  contentUrlFlags,
			Map<String,Integer>  answer1UrlFlags,Map<String,Integer>  answer1SubUrlFlags,
			Map<String,Integer>  answer2UrlFlags,Map<String,Integer>  answer2SubUrlFlags,
			Map<String,Integer>  parseUrlFlags,Map<String,Integer>  parseSubUrlFlags
			){
		 
		 Map<String,Integer>  finalUrlFlags=new HashMap<String,Integer>();
		 Integer flag=1;
		 //1
		 Integer urlImgFlagContent = null;
		 Integer urlTikupicFlagContent = null;
		 Integer urlUpimagesFlagContent = null;
		 Integer urlUpimgFlagContent = null;
		 Integer urlUploadFlagContent = null;
		 Integer urlUserFlagContent = null;
		 Integer urlZximagesFlagContent = null;
		 if (contentUrlFlags!=null) {
			 urlImgFlagContent = contentUrlFlags.get("urlImgFlag");
			 urlTikupicFlagContent = contentUrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlagContent = contentUrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlagContent = contentUrlFlags.get("urlUpimgFlag");
			 urlUploadFlagContent = contentUrlFlags.get("urlUploadFlag");
			 urlUserFlagContent = contentUrlFlags.get("urlUserFlag");
			 urlZximagesFlagContent = contentUrlFlags.get("urlZximagesFlag");
		 }
		 //2
		 Integer urlImgFlaganswer1 = null;
		 Integer urlTikupicFlaganswer1 = null;
		 Integer urlUpimagesFlaganswer1 = null;
		 Integer urlUpimgFlaganswer1 = null;
		 Integer urlUploadFlaganswer1 = null;
		 Integer urlUserFlaganswer1 = null;
		 Integer urlZximagesFlaganswer1 = null;
		 if (answer1UrlFlags!=null) {
			 urlImgFlaganswer1 = answer1UrlFlags.get("urlImgFlag");
			 urlTikupicFlaganswer1 = answer1UrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlaganswer1 = answer1UrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlaganswer1 = answer1UrlFlags.get("urlUpimgFlag");
			 urlUploadFlaganswer1 = answer1UrlFlags.get("urlUploadFlag");
			 urlUserFlaganswer1 = answer1UrlFlags.get("urlUserFlag");
			 urlZximagesFlaganswer1 = answer1UrlFlags.get("urlZximagesFlag");
		 }
		 //3
		 Integer urlImgFlaganswer1SubUrl = null;
		 Integer urlTikupicFlaganswer1SubUrl = null;
		 Integer urlUpimagesFlaganswer1SubUrl = null;
		 Integer urlUpimgFlaganswer1SubUrl = null;
		 Integer urlUploadFlaganswer1SubUrl = null;
		 Integer urlUserFlaganswer1SubUrl = null;
		 Integer urlZximagesFlaganswer1SubUrl = null;
		 if (answer1SubUrlFlags!=null) {
			 urlImgFlaganswer1SubUrl = answer1SubUrlFlags.get("urlImgFlag");
			 urlTikupicFlaganswer1SubUrl = answer1SubUrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlaganswer1SubUrl = answer1SubUrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlaganswer1SubUrl = answer1SubUrlFlags.get("urlUpimgFlag");
			 urlUploadFlaganswer1SubUrl = answer1SubUrlFlags.get("urlUploadFlag");
			 urlUserFlaganswer1SubUrl = answer1SubUrlFlags.get("urlUserFlag");
			 urlZximagesFlaganswer1SubUrl = answer1SubUrlFlags.get("urlZximagesFlag");
		 }
		 //4
		 Integer urlImgFlaganswer2 = null;
		 Integer urlTikupicFlaganswer2 = null;
		 Integer urlUpimagesFlaganswer2 = null;
		 Integer urlUpimgFlaganswer2 = null;
		 Integer urlUploadFlaganswer2 = null;
		 Integer urlUserFlaganswer2 = null;
		 Integer urlZximagesFlaganswer2 = null;
		 if (answer2UrlFlags!=null) {
			 urlImgFlaganswer2 = answer2UrlFlags.get("urlImgFlag");
			 urlTikupicFlaganswer2 = answer2UrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlaganswer2 = answer2UrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlaganswer2 = answer2UrlFlags.get("urlUpimgFlag");
			 urlUploadFlaganswer2 = answer2UrlFlags.get("urlUploadFlag");
			 urlUserFlaganswer2 = answer2UrlFlags.get("urlUserFlag");
			 urlZximagesFlaganswer2 = answer2UrlFlags.get("urlZximagesFlag");
		 }
		 //5
		 Integer urlImgFlaganswer2Sub = null;
		 Integer urlTikupicFlaganswer2Sub = null;
		 Integer urlUpimagesFlaganswer2Sub = null;
		 Integer urlUpimgFlaganswer2Sub = null;
		 Integer urlUploadFlaganswer2Sub = null;
		 Integer urlUserFlaganswer2Sub = null;
		 Integer urlZximagesFlaganswer2Sub = null;
		 if (answer2SubUrlFlags!=null) {
			 urlImgFlaganswer2Sub = answer2SubUrlFlags.get("urlImgFlag");
			 urlTikupicFlaganswer2Sub = answer2SubUrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlaganswer2Sub = answer2SubUrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlaganswer2Sub = answer2SubUrlFlags.get("urlUpimgFlag");
			 urlUploadFlaganswer2Sub = answer2SubUrlFlags.get("urlUploadFlag");
			 urlUserFlaganswer2Sub = answer2SubUrlFlags.get("urlUserFlag");
			 urlZximagesFlaganswer2Sub = answer2SubUrlFlags.get("urlZximagesFlag");
	 	 }
		 //6
		 Integer urlImgFlagparse = null;
		 Integer urlTikupicFlagparse = null;
		 Integer urlUpimagesFlagparse = null;
		 Integer urlUpimgFlagparse = null;
		 Integer urlUploadFlagparse = null;
		 Integer urlUserFlagparse = null;
		 Integer urlZximagesFlagparse = null;
		 if (parseUrlFlags!=null) {
			 urlImgFlagparse = parseUrlFlags.get("urlImgFlag");
			 urlTikupicFlagparse = parseUrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlagparse = parseUrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlagparse = parseUrlFlags.get("urlUpimgFlag");
			 urlUploadFlagparse = parseUrlFlags.get("urlUploadFlag");
			 urlUserFlagparse = parseUrlFlags.get("urlUserFlag");
			 urlZximagesFlagparse = parseUrlFlags.get("urlZximagesFlag");
		 }
		 //7
		 Integer urlImgFlagparseSub = null;
		 Integer urlTikupicFlagparseSub = null;
		 Integer urlUpimagesFlagparseSub = null;
		 Integer urlUpimgFlagparseSub = null;
		 Integer urlUploadFlagparseSub = null;
		 Integer urlUserFlagparseSub = null;
		 Integer urlZximagesFlagparseSub = null;
		 if (parseSubUrlFlags!=null) {
			 urlImgFlagparseSub = parseSubUrlFlags.get("urlImgFlag");
			 urlTikupicFlagparseSub = parseSubUrlFlags.get("urlTikupicFlag");
			 urlUpimagesFlagparseSub = parseSubUrlFlags.get("urlUpimagesFlag");
			 urlUpimgFlagparseSub = parseSubUrlFlags.get("urlUpimgFlag");
			 urlUploadFlagparseSub = parseSubUrlFlags.get("urlUploadFlag");
			 urlUserFlagparseSub = parseSubUrlFlags.get("urlUserFlag");
			 urlZximagesFlagparseSub = parseSubUrlFlags.get("urlZximagesFlag");
		 }
		 //img目录的标志
		 if (flag==urlImgFlagContent||flag==urlImgFlaganswer1||flag==urlImgFlaganswer1SubUrl||
				 flag==urlImgFlaganswer2||flag==urlImgFlaganswer2Sub||flag==urlImgFlagparse||
						 flag==urlImgFlagparseSub
			) {
			 finalUrlFlags.put("urlImgFlag", 1);
		 }else{
			 finalUrlFlags.put("urlImgFlag", 0);
		 }
		 
		 //Tikupic路径的标志
		 if (flag==urlTikupicFlagContent||flag==urlTikupicFlaganswer1||flag==urlTikupicFlaganswer1SubUrl||
				 flag==urlTikupicFlaganswer2||flag==urlTikupicFlaganswer2Sub||flag==urlTikupicFlagparse||
						 flag==urlTikupicFlagparseSub
			) {
			 finalUrlFlags.put("urlTikupicFlag", 1);
		 }else{
			 finalUrlFlags.put("urlTikupicFlag", 0);
		 }
		 
		 //Upimages路径的标志
		 if (flag==urlUpimagesFlagContent||flag==urlUpimagesFlaganswer1||flag==urlUpimgFlaganswer1SubUrl||
				 flag==urlUpimgFlaganswer2||flag==urlUpimagesFlaganswer2Sub||flag==urlUpimgFlagparse||
						 flag==urlUpimagesFlagparseSub
			) {
			 finalUrlFlags.put("urlUpimagesFlag", 1);
		 }else{
			 finalUrlFlags.put("urlUpimagesFlag", 0);
		 }
		 
		 //Upimg路径的标志
		 if (flag==urlUpimgFlagContent||flag==urlUpimgFlaganswer1||flag==urlUpimagesFlaganswer1SubUrl||
				 flag==urlUpimagesFlaganswer2||flag==urlUpimgFlaganswer2Sub||flag==urlUpimagesFlagparse||
						 flag==urlUpimgFlagparseSub
			) {
			 finalUrlFlags.put("urlUpimgFlag", 1);
		 }else{
			 finalUrlFlags.put("urlUpimgFlag", 0);
		 }
		 
		 //Upload路径的标志
		 if (flag==urlUploadFlagContent||flag==urlUploadFlaganswer1||flag==urlUploadFlaganswer1SubUrl||
				 flag==urlUploadFlaganswer2||flag==urlUploadFlaganswer2Sub||flag==urlUploadFlagparse||
						 flag==urlUploadFlagparseSub
			) {
			 finalUrlFlags.put("urlUploadFlag", 1);
		 }else{
			 finalUrlFlags.put("urlUploadFlag", 0);
		 }
		 
		 
		 //User路径的标志
		 if (flag==urlUserFlagContent||flag==urlUserFlaganswer1||flag==urlUserFlaganswer1SubUrl||
				 flag==urlUserFlaganswer2||flag==urlUserFlaganswer2Sub||flag==urlUserFlagparse||
						 flag==urlUserFlagparseSub
			) {
			 finalUrlFlags.put("urlUserFlag", 1);
		 }else{
			 finalUrlFlags.put("urlUserFlag", 0);
		 }
		 
		 //Zximages路径的标志
		 if (flag==urlZximagesFlagContent||flag==urlZximagesFlaganswer1||flag==urlZximagesFlaganswer1SubUrl||
				 flag==urlZximagesFlaganswer2||flag==urlZximagesFlaganswer2Sub||flag==urlZximagesFlagparse||
						 flag==urlZximagesFlagparseSub
			) {
			 finalUrlFlags.put("urlZximagesFlag", 1);
		 }else{
			 finalUrlFlags.put("urlZximagesFlag", 0);
		 }
		 
		 return finalUrlFlags;
		 
	}



}
