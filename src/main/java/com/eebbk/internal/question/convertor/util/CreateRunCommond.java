package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class CreateRunCommond {
	public static String urlPathRun="E:\\ThreadDistributeFile\\rebuild\\running-commond";//文件存放路径

	public static void main(String[] args) {
		/**
		 * #service-01  每个线程跑5W的数据，跑30个线程
			java -cp internal-question-convertor.jar com.eebbk.internal.question.convertor.
			exportjarservice01.UploadData1 start
			java -cp internal-question-convertor.jar com.eebbk.internal.question.convertor.
			exportjarservice01.UploadData2 start
	
		}  0-100   0-20 20-40 40-60 
		 */
		Integer serviceNum=10;//节点数
		Integer ThreadNum=10;//节点数
		Integer sumCounts=18000000;//处理数据总量
		Integer handerStartIndex=null;
		Integer handerDataMaxIndex=null;
		Integer handerCountTread=sumCounts/serviceNum/ThreadNum;
		if (handerCountTread==null) {
			System.out.println("error:handerCountTread====");
			return;
		}else{
			System.out.println("sucess:handerCount===="+handerCountTread);
		}
		Integer handerStartIndexPre=0;
		Integer handerDataMaxIndexPre=0;
		System.out.println(handerCountTread);
		//10000
		StringBuffer resultBf = new StringBuffer("");
		for (int i = 1; i <=serviceNum; i++) {
			resultBf.append("#service-").append(i).append("的run命令:").append("\n");
			try {
				createRunTxtFile(i,ThreadNum,serviceNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void createRunTxtFile(int i, int ThreadNum,Integer serviceNum) throws Exception {
		String packageName="exportjarserviceRebuild";
		if (i==serviceNum) {
			packageName=packageName+i;
		}else{
			packageName=packageName+"0"+i;
		}
		String filePath=urlPathRun+File.separator+"run-commond-"+i+".txt";
		File fileDir =null;
		fileDir = new File(urlPathRun);
		judeDirExists(fileDir);
		FileOutputStream fis=null; 
		OutputStreamWriter out = null;
		String fileContent=getRunContent(i,ThreadNum,packageName);
		File file=null;
		file = new File(filePath);
		if (!file.exists()) {
			boolean createNewFile = file.createNewFile();
		}
		try {
			fis=new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out=new OutputStreamWriter(fis,"UTF-8");
		out.write(fileContent);
		out.flush(); 
		out.close();
		fis.close();
		System.out.println("生成文件run-commond-"+packageName+".txt"+"成功");
	}

	private static String getRunContent(int i, int ThreadNum,String packageName) {
		Integer runCount=ThreadNum/10;
		StringBuffer content = new StringBuffer("");
		int flag=1;
		for (int m = 1; m <=ThreadNum; m++) {
			if (flag==10) {
				flag=0;
			    content.append("java -cp internal-question-convertor.jar com.eebbk.internal.question.convertor.")
			    .append(packageName).append(".UploadData").append(m).append("  start")
			    .append("\n").append("\n");
			}else{
				content.append("java -cp internal-question-convertor.jar com.eebbk.internal.question.convertor.")
				.append(packageName).append(".UploadData").append(m).append("  start")
				.append("\n");
			}
			flag++;
		}
		return content.toString();
	}	
	 // 判断文件夹是否存在不存在则创建
	public static void judeDirExists(File file) {
         if (file.exists()) {
             if (file.isDirectory()) {
                 System.out.println("dir exists");
             } else {
                 System.out.println("the same name file exists, can not create dir");
             }
         } else {
             System.out.println("dir not exists, create it ...");
             file.mkdir();
         }
	 
   }
}