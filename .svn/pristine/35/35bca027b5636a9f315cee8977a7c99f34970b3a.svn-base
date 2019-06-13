package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.eebbk.edu.common.cloudfile.QiniuUtil;
import com.eebbk.internal.question.convertor.constant.CommonConstant;

/**
 * TODO
 * @package  com.eebbk.internal.question.convertor.util
 * @file     HandlerFileNameSuffix.java
 * @author   admin
 * @date     2019年5月14日 上午9:46:34
 * @version  V 1.0
 */
public class HandlerFileNameSuffix {
	/**
	 * @author admin
	 * 处理文件后缀名/upimg/czyy/STSource/2014101713543587931448/SYS201410171354373637866245_ST/
	 * SYS201410171354373637866245_ST.001.png
	 * @method handlerFileNameSuffix
	 * @param name
	 * @return
	 * @return String
	 * @date 2019年5月14日 上午9:47:20
	 */
	public static String handlerFileNameSuffix(String name){
		StringBuffer bfImg = new StringBuffer("");
		String[] split0 = name.split("/");
		int lastIndexOf = name.lastIndexOf("/");
		String substringPre = name.substring(0, lastIndexOf+1);
		bfImg=bfImg.append(substringPre);
		String substringEnd = name.substring(lastIndexOf+1, name.length());
		String[] splitEnd = substringEnd.split("\\.");
		if (splitEnd.length==3) {
			String namePre=null;
			for (int i = 0; i < splitEnd.length; i++) {
				if (splitEnd[i].contains(" ")) {
					continue;
				}
    			if (i==splitEnd.length-1) {
    				long current = System.currentTimeMillis();
    				if (namePre!=null) {
    					bfImg=bfImg.append(namePre).append(current).append(".").append(splitEnd[i]);
					}
    			}else if(i==splitEnd.length-2){
    				namePre=splitEnd[i];
    			}else{
//    				bfImg=bfImg.append(splitEnd[i]);
    			}
        	}
        		boolean refileName = FtpUtil.refileName(CommonConstant.FTP_ADDR, CommonConstant.FTP_USER, CommonConstant.FTP_PWD, CommonConstant.FTP_PORT, CommonConstant.FTP_PATH, 
        				name, bfImg.toString());
        		if (refileName) {
        			name=bfImg.toString();
					System.out.println("文件命名成功");
				}else{
					System.out.println("文件命名失败");
				}
		}
		return name;
	}
	
	public static String copyLocalFile(String preFilePath,String fileName,String fileNewname) {
		int lastIndexOf = fileName.lastIndexOf("/");
		String fileNewnamePre=null;
		StringBuffer picBufferUpload = new StringBuffer("");
		if (lastIndexOf>=0) {
			fileNewname  = fileName.substring(lastIndexOf+1, fileName.length());
			fileNewnamePre  = fileName.substring(0, lastIndexOf+1);
			picBufferUpload=picBufferUpload.append(fileNewnamePre);
		}
		String[] splitEnd = fileNewname.split("\\.");
		if (splitEnd.length==3) {
			String namePre=null;
			StringBuffer bfImg = new StringBuffer("");
			for (int i = 0; i < splitEnd.length; i++) {
				if (splitEnd[i].contains(" ")) {
					continue;
				}
    			if (i==splitEnd.length-1) {
    				long current = System.currentTimeMillis();
    				if (namePre!=null) {
    					bfImg=bfImg.append(namePre).append(current).append(".").append(splitEnd[i]);
					}
    			}else if(i==splitEnd.length-2){
    				namePre=splitEnd[i];
    			}else{
    			}
        	}
			fileNewname=bfImg.toString();
			picBufferUpload=picBufferUpload.append(fileNewname);
		}else{
			return fileName;
		}
		if (lastIndexOf>=0) {
			String substringPre = fileName.substring(0, lastIndexOf+1);
			preFilePath=preFilePath+substringPre;
			fileName  = fileName.substring(lastIndexOf+1, fileName.length());
		}
        try {
            FileInputStream input = new FileInputStream(preFilePath+fileName);
            FileOutputStream output = new FileOutputStream(preFilePath+fileNewname);
            int read = input.read();       
            while ( read != -1 ) {
                output.write(read);
                read = input.read();
            }          
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println("==error:copyLocalFile:"+e.toString());
        }
//        System.out.println("==sucess:copyLocalFile");
        return picBufferUpload.toString();
    }
	/**
	 * @author admin
	 * TODO将不正常的文件名字修正
	 * @method getCorrectFileName
	 * @param fileName
	 * @return
	 * @return String
	 * @date 2019年5月16日 上午11:11:59
	 */
	public static String getCorrectFileName(String fileName) {
		String fileNewname=fileName;
		int lastIndexOf = fileName.lastIndexOf("/");
		String fileNewnamePre=null;
		StringBuffer picBufferUpload = new StringBuffer("");
		if (lastIndexOf>=0) {
			fileNewname  = fileName.substring(lastIndexOf+1, fileName.length());
			fileNewnamePre  = fileName.substring(0, lastIndexOf+1);
			picBufferUpload=picBufferUpload.append(fileNewnamePre);
		}
		String[] splitEnd = fileNewname.split("\\.");
		if (splitEnd.length==3) {
			String namePre=null;
			StringBuffer bfImg = new StringBuffer("");
			for (int i = 0; i < splitEnd.length; i++) {
				if (splitEnd[i].contains(" ")) {
					continue;
				}
    			if (i==splitEnd.length-1) {
    				long current = System.currentTimeMillis();
    				if (namePre!=null) {
    					bfImg=bfImg.append(namePre).append(current).append(".").append(splitEnd[i]);
					}
    			}else if(i==splitEnd.length-2){
    				namePre=splitEnd[i];
    			}else{
    			}
        	}
			fileNewname=bfImg.toString();
			picBufferUpload=picBufferUpload.append(fileNewname);
		}else{
			return fileName;
		}
        return picBufferUpload.toString();
    }
	/**
	 * @author admin
	 * 判断文件是否存在
	 * @method fileIsExist
	 * @param file
	 * @return
	 * @return Boolean
	 * @date 2019年5月16日 下午12:21:32
	 */
	public static Boolean fileIsExist(String file) {
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
	public static InputStream getLocalFileSteam(String preFilePath, String fileName) {
		InputStream fileInputStream=null;
		try {
			fileInputStream = new FileInputStream(preFilePath+fileName);
		} catch (Exception e) {
			System.out.println("==error:getLocalFileSteam:"+e.toString());
		}
		return fileInputStream;
	}
	public static void main(String[] args) {
//		String fileName="/upimg/gzdl/STSource/2014111723382189714697/SYS201411172338224440679853_ST/SYS201411172338224440679853_ST.001.jpeg";
//		String fileName="/upimg/czdl/STSource/20131101154744086371579/SYS201311011547440863715010_ST/images0.png";
//		String fileName="Upload/2010-03/22/7a8754e5-24e2-4b95-8236-eba71b76dee0/paper.files/image001.003.gif";
		String fileName="/tikupic/mathml/1515768006.83.png";
		String preFilePath=CommonConstant.PATH_LOCAL_TIKUPIC;
//		String copyLocalFile = copyLocalFile(preFilePath, fileName, fileName);
		InputStream in = getLocalFileSteam(preFilePath,fileName);
		String updateFileName = getCorrectFileName(fileName);
		System.out.println("updateFileName="+updateFileName);
		try {
			String url = QiniuUtil.uploadFile(updateFileName, in, "common-pic", false);
			System.out.println(url);
		} catch (Exception e) {
			System.out.println("error:"+e.toString());
		}
		
	}

}
