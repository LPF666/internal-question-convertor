package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class DistributeThread {
	public static String urlPath="E:\\ThreadDistributeFile\\rebuild";//文件存放路径

	public static void main(String[] args) {
		/**
		 * #service-01  每个线程跑5W的数据，跑30个线程
			   Thread-01:
				Integer handerStartIndex=0;
				Integer handerDataMaxIndex=50000;
				for (int i = handerStartIndex; i<handerDataMaxIndex; i++) {
			System.out.println("处理数据的起始记录下标="+i);  
			XuekubaoUtil.insertQuestionXuekubao(i, handerCount, questionMapper,tableFlag,questionType); 
			System.out.println("数据的起始记录下标="+i+"已处理完");
			i=i+handerCount;
			--i;   
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
			resultBf.append("#service-").append(i).append("每个线程跑5W的数据，跑30个线程").append("\n");
			for (int j = 1; j <= ThreadNum; j++) {
				resultBf.append("	Thread--").append(j).append("\n");
				handerStartIndex=handerStartIndexPre;
				handerDataMaxIndex=handerDataMaxIndexPre+handerCountTread;
				resultBf.append("		//").append("本线程处理").append(handerStartIndex.toString()).append("-").append(handerDataMaxIndex.toString()).append("万的数据，共5万数据量").append("\n");
				resultBf.append("		Integer handerStartIndex="+handerStartIndex).append(";").append("\n");
				resultBf.append("		Integer handerDataMaxIndex="+handerDataMaxIndex).append(";").append("\n");
				try {
					createJavaFile(i,j,handerStartIndex,handerDataMaxIndex,serviceNum);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handerStartIndexPre=handerStartIndex+handerCountTread;
				handerDataMaxIndexPre=handerDataMaxIndexPre+handerCountTread;
			}
		}
//		System.out.println(resultBf.toString());
	}

	private static void createJavaFile(int i, int j, Integer handerStartIndex, Integer handerDataMaxIndex,Integer serviceNum) throws Exception {
		String packageName="exportjarserviceRebuild";
		if (i==serviceNum) {
			packageName=packageName+i;
		}else{
			packageName=packageName+"0"+i;
		}
		File fileDir =null;
		fileDir = new File(urlPath+File.separator+packageName);
		judeDirExists(fileDir);
		FileOutputStream fis=null; 
		OutputStreamWriter out = null;
		String fileContent=getJavaContent(i,j,handerStartIndex,handerDataMaxIndex,packageName);
		File file=null;
		if (i==serviceNum) {
			file = new File(urlPath+File.separator+packageName+File.separator+"UploadData"+j+".java");
		}else{
			file = new File(urlPath+File.separator+packageName+File.separator+"UploadData"+j+".java");
		}
		if (!file.exists()) {
			boolean createNewFile = file.createNewFile();
		}
		try {
			fis=new FileOutputStream(urlPath+File.separator+packageName+File.separator+"UploadData"+j+".java");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out=new OutputStreamWriter(fis,"UTF-8");
		out.write(fileContent);
		out.flush(); 
		out.close();
		fis.close();
		System.out.println("生成文件service"+i+".java"+"成功");
		
	}

	private static String getJavaContent(int i, int j, Integer handerStartIndex, 
			Integer handerDataMaxIndex,String packageName) {
		
		StringBuffer content = new StringBuffer("");
		content.append("package com.eebbk.internal.question.convertor."+packageName+";").append("\n");

//		if (i==10) {
//			content.append("package com.eebbk.internal.question.convertor."+packageName+i+";").append("\n");
//		}else{
//			content.append("package com.eebbk.internal.question.convertor."+packageName+"0"+i+";").append("\n");
//		}
		content.append("import org.springframework.context.ApplicationContext;").append("\n");
		content.append("import org.springframework.context.support.ClassPathXmlApplicationContext;").append("\n");
		content.append("import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;").append("\n");
		content.append("import com.eebbk.internal.question.convertor.util.XuekubaoUtil;").append("\n");
		content.append("public class UploadData"+j+" {").append("\n");
		content.append("	public static void main(String[] args) {").append("\n");
		content.append("		ApplicationContext ac = new ClassPathXmlApplicationContext(\"applicationContext.xml\");").append("\n");
		content.append("		QuestionMapper2 questionMapper = (QuestionMapper2) ac.getBean(\"questionMapper2\");").append("\n");
		content.append("		long start,end;").append("\n");
		content.append("		int handerCount=10000;//每一个批次处理2万的").append("\n");
		content.append("		//").append("本线程处理").append(handerStartIndex.toString()).append("-").append(handerDataMaxIndex.toString()).append("万的数据，共5万数据量").append("\n");
		content.append("		Integer handerStartIndex="+handerStartIndex).append(";").append("\n");
		content.append("		Integer handerDataMaxIndex="+handerDataMaxIndex).append(";").append("\n");
		content.append("		//questionType表示是什么类型的试卷 0 文本题 1 latex 2 image 3 mathjye 如果为null就处理所有类型").append("\n");
		content.append("		Integer questionType=null;").append("\n");
		content.append("		//0标识处理图片 1标识不处理图片").append("\n");
		content.append("		Integer tableFlag=1;").append("\n");
		content.append("		start = System.currentTimeMillis();").append("\n");
		content.append("		System.out.println(\"start time:\" + start);").append("\n");
		content.append("		//每个线程处理30万数据量，每次处理2万的数据量").append("\n");
		content.append("		for (int i = handerStartIndex; i<handerDataMaxIndex; i++) {").append("\n");
		content.append("			System.out.println(\"处理数据的起始记录下标=\"+i);").append("\n");
		content.append("			XuekubaoUtil.insertQuestionXuekubao(i, handerCount, questionMapper,tableFlag,questionType); ").append("\n");
		content.append("			System.out.println(\"数据的起始记录下标=\"+i+\"已处理完\");").append("\n");
		content.append("			i=i+handerCount;").append("\n");
		content.append("			--i;").append("\n");
		content.append("		}").append("\n");
		content.append("		System.out.println(\"执行结束====================本线程处理数据:\"+handerStartIndex+\"——\"+handerDataMaxIndex);").append("\n");
		content.append("		end = System.currentTimeMillis(); ").append("\n");
		content.append("		System.out.println(\"end time:\" + end+ \"; =====用时:\" + (end - start)/1000 + \"(s)\");").append("\n");
		content.append("	}").append("\n");
		content.append("}").append("\n");
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
