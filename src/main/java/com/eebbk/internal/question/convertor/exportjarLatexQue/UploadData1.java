package com.eebbk.internal.question.convertor.exportjarLatexQue;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.XuekubaoUtil;
/**
 * 第一台服务器节点处理数据:
 * @package  com.eebbk.internal.question.convertor.exportjarservice01
 * @file     UploadData1.java
 * @author   admin
 * @date     2019年5月15日 下午7:27:22
 * @version  V 1.0
 */
public class UploadData1 {
	/**
	 * @author admin
	 * 0万-30万数据处理，共30万数据量
	 * @method main
	 * @param args
	 * @return void
	 * @date 2019年5月15日 下午7:55:08
	 */
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
		long start,end;
		int handerCount=20000;//每一个批次处理2万的
		//开始处理数据下标,本次处理
		Integer handerStartIndex=0;
		//每个线程处理30万数据量，每次处理2万的数据量
		Integer handerDataMaxIndex=300000;
		start = System.currentTimeMillis();
		System.out.println("start time:" + start);
		//questionType表示是什么类型的试卷 0 文本题 1 latex 2 image 3 mathjye 如果为null就处理所有类型
		Integer questionType=1;
		//判断是试卷题还是非试卷题
		Integer tableFlag=0;
		//每个线程处理30万数据量，每次处理2万的数据量
		for (int i = handerStartIndex; i<handerDataMaxIndex; i++) {
			System.out.println("处理数据的起始记录下标="+i);
			XuekubaoUtil.insertQuestionXuekubao(i, handerCount, questionMapper,tableFlag,questionType); 
			System.out.println("数据的起始记录下标="+i+"已处理完");
			i=i+handerCount;
			--i;
		}
		System.out.println("执行结束====================本线程处理数据:"+handerStartIndex+"——"+handerDataMaxIndex);
		end = System.currentTimeMillis(); 
		System.out.println("end time:" + end+ "; =====用时:" + (end - start)/1000 + "(s)");
	}
}
