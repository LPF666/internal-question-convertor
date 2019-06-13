package com.eebbk.internal.question.convertor.exportjarservice09;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.XuekubaoUtil;
public class UploadData29 {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
		long start,end;
		int handerCount=10000;//每一个批次处理2万的
		//本线程处理12840000-12870000万的数据，共5万数据量
		Integer handerStartIndex=12840000;
		Integer handerDataMaxIndex=12870000;
		//questionType表示是什么类型的试卷 0 文本题 1 latex 2 image 3 mathjye 如果为null就处理所有类型
		Integer questionType=null;
		//判断是试卷题还是非试卷题
		Integer tableFlag=0;
		start = System.currentTimeMillis();
		System.out.println("start time:" + start);
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
