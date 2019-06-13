package com.eebbk.internal.question.convertor.exportjar;

import com.eebbk.internal.question.convertor.constant.CommonConstant;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.XuekubaoDataHander;

import java.io.File;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HandlerDataUpdate12 {
	private static SqlSessionFactory sqlSessionFactory; 
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
		long start,end;
		start = System.currentTimeMillis();
		System.out.println("start time:" + start);
//		File file = new File(CommonConstant.PATH_LOCAL_DATA_IDS);
		File file = new File(CommonConstant.PATH_LOCAL_DATA_IDS_PIC_QINIU12);
		//是否为试卷题0 否 1 是
		Integer tableFlag=0;
		//处理方式：0   新增   1  更新
		Integer handerWay=1;
		if(file.isFile() && file.exists()) {
			XuekubaoDataHander.dataHandler(file, questionMapper,5000,tableFlag,handerWay);
		}
		System.out.println("执行结束=============================");
		end = System.currentTimeMillis(); 
		System.out.println("end time:" + end+ "; =====用时:" + (end - start)/1000 + "(s)");
	}
}