package com.eebbk.internal.question.convertor.exportjar;

import com.eebbk.internal.question.convertor.constant.CommonConstant;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.CreateFile;
import com.eebbk.internal.question.convertor.util.XuekubaoDataHander;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HandlerCreateHtmlData {
	private static SqlSessionFactory sqlSessionFactory; 
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
		long start,end;
		start = System.currentTimeMillis();
		System.out.println("start time:" + start);
		File file = new File(CommonConstant.PATH_HTML_DATA+"科学\\科学初中\\科学初中.xlsx");
		//E:\filepath\tiku
//		File file = new File("E:/filepath/tiku/英语小学.xlsx");
		//是否为试卷题0 否 1 是
		Integer tableFlag=0;
		boolean isFile = file.isFile();
		boolean isExists = file.exists();
		if(file.isFile() && file.exists()) {
			try {
				List<HashMap<String, Object>> listHtml = XuekubaoDataHander.dataHandlerCreateHtml(file, questionMapper,100,tableFlag);
				//拿到多个sheet的值，创建多个html文件
				if (CollectionUtils.isNotEmpty(listHtml)) {
					for (int i = 0; i < listHtml.size(); i++) {
						Map<String, Object> dataHandlerCreateHtml=listHtml.get(i);
						String dirNameGrade= (String) dataHandlerCreateHtml.get("dirNameGrade");
						String dirNameSubject= (String) dataHandlerCreateHtml.get("dirNameSubject");
						List<QuestionXuekubaoQueryVo> questVoList = (List<QuestionXuekubaoQueryVo>) dataHandlerCreateHtml.get("queListQueObject");
						CreateFile.createFileBylist(questVoList, dirNameGrade, dirNameSubject,i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}