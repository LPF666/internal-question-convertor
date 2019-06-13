package com.eebbk.internal.question.convertor.dealpic;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.util.XuekubaoDataHander;
/**
 * 根据题目id生成对应img的图片
 * @package  com.eebbk.internal.question.convertor.dealpic
 * @file     GenaratePicurl.java
 * @author   admin
 * @date     2019年6月9日 上午9:42:24
 * @version  V 1.0
 */
public class GenaratePicurl8 {
	public static void main(String[] args) {
			
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_地理id_old.txt");
//
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_化学id_old.txt");
//
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_科学id_old.txt");
//		    File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_历史id_old.txt");

//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_生物id_old.txt");
//
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_物理id_old.txt");
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_数学id_old.txt");
//
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_英语id_old.txt");
//
//			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_语文id_old.txt");
//
			File file = new File("E:\\xiaotiancai\\youdao\\BBK-Youdao-10w\\BBK_Youdao_政治id_old.txt");

			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean("questionMapper2");
			if (!file.isFile()) {
				System.out.println("the file is not exist..........");
				return;
			}
			Integer pageSize=10000; 
			
			System.out.println("starting..............");

			XuekubaoDataHander.exportimgsHandler(file,pageSize,questionMapper);
			System.out.println("finished..............");
		}
}
