package com.eebbk.internal.question.convertor.exportjar;

import java.io.File;

import com.eebbk.internal.question.convertor.util.XuekubaoDataHander;

public class CreateTxt {
	public static void main(String[] args) {
		
//		File file = new File("E:\\xiaotiancai\\xuekubao\\dataHandler\\pics\\no_pic.txt");
		File file = new File("E:\\xiaotiancai\\xuekubao\\dataHandler\\pics\\pic_shuiyin_ids.txt");
		
		if (!file.isFile()) {
			System.out.println("the file is not exist..........");
			return;
		}
		Integer pageSize=100000; 
		
		System.out.println("starting..............");

		XuekubaoDataHander.exportTxtHandler(file,pageSize);
		System.out.println("finished..............");
	}
}
