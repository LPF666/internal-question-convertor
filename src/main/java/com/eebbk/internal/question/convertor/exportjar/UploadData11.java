package com.eebbk.internal.question.convertor.exportjar;

import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.XuekubaoUtil;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UploadData11 {
	private static SqlSessionFactory sqlSessionFactory; 
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
		long start,end;
		start = System.currentTimeMillis();
		//开始处理数据下标,本次处理
		Integer handerStartIndex=200000;
		int handerCount=20000;//每一个批次处理2万的
		//questionType表示是什么类型的试卷 0 文本题 1 latex 2 image 3 mathjye 如果为null就处理所有类型
		System.out.println("start time:" + start);
		XuekubaoUtil.insertQuestionXuekubao(handerStartIndex, handerCount, questionMapper,0,null);
		System.out.println("执行结束=============================");
		end = System.currentTimeMillis(); 
		System.out.println("end time:" + end+ "; =====用时:" + (end - start)/1000 + "(s)");
	}
}
