package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.eebbk.edu.common.cloudfile.QiniuUtil;
import com.eebbk.internal.question.convertor.constant.CommonConstant;

public class FtpUtil {
	public static boolean uploadFile(String url,// FTP服务器hostname
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String path, // FTP服务器保存目录
			String filename, // 上传到FTP服务器上的文件名
			InputStream input // 输入流
	) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory(path);
			ftp.setFileType(FTP.ASCII_FILE_TYPE);
			String FtpFilename = new String(filename.getBytes("GBK"),
					"iso-8859-1");
			boolean result = ftp.storeFile(FtpFilename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			System.out.println("ftp:copyFile:uploadFile:error====");
//			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
 
	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
 
	public static boolean downFile(String url, // FTP服务器
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String localPath// 下载后保存到本地的路径
 
	) {
 
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			ftp.enterLocalPassiveMode();
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
//			System.out.println("getReplyCode="+reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = null;
			String[] names = null;
			if (ftp.listFiles() != null) {
				fs = ftp.listFiles();
			}
			if (ftp.listNames().length > 0) {
				names = ftp.listNames();
			}
			if (fs != null && fs.length > 0) {
				for (FTPFile ff : fs) {
					String FtpFilename = new String(ff.getName().getBytes(
							"iso-8859-1"), "GBK");
					if (FtpFilename.equals(fileName)) {
						File localFile = new File(localPath + "/" + FtpFilename);
						if (!localFile.exists()) {
							OutputStream is = new FileOutputStream(localFile);
							boolean result = ftp.retrieveFile(ff.getName(), is);
							is.close();
						}
					}
				}
			} else {
				if (names != null) {
					for (int i = 0, j = names.length; i < j; i++) {
						if (names[i].equals(fileName)) {
							File localFile = new File(localPath + "/"
									+ names[i]);
							if (!localFile.exists()) {
								OutputStream is = new FileOutputStream(
										localFile);
								boolean result = ftp.retrieveFile(names[i], is);
								is.close();
							}
						}
					}
				} else {
					ftp.logout();
					success = true;
					return success;
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
 
	/**
	 * Description: 从FTP服务器删除文件
	 * 
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @return
	 */
 
	public static boolean deleteFile(String url, // FTP服务器
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName// 要下载的文件名
 
	) {
 
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.enterLocalPassiveMode();
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			String[] names = ftp.listNames();
 
			if (fs != null && fs.length > 0) {
				for (FTPFile ff : fs) {
					String FtpFilename = new String(ff.getName().getBytes(
							"iso-8859-1"), "GBK");
					if (FtpFilename.equals(fileName)) {
						ftp.deleteFile(FtpFilename);
					}
				}
			} else {
				if (names != null) {
					// System.getProperties().getProperty("os.name").toUpperCase().startsWith("WIN");
					for (int i = 0, j = names.length; i < j; i++) {
						if (names[i].equals(fileName)) {
							ftp.deleteFile(names[i]);
						}
					}
				} else {
					ftp.logout();
					success = true;
					return success;
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
 
	/**
	 * Description: 从FTP服务器下载文件,上传到ftp服务器
	 * 
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
 
	public static String copyFile(String url, // 下载FTP服务器
			int port,// 下载FTP服务器端口
			String username, // 下载FTP登录账号
			String password, // 下载FTP登录密码
			String remotePath,// 下载FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String fileNewname // 上传到FTP服务器上的文件名
 
	) {
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
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			ftp.enterLocalPassiveMode();
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return fileName;
			}
			int lastIndexOfNew = fileName.lastIndexOf("/");
			if (lastIndexOfNew>=0) {
				String substringPre = fileName.substring(0, lastIndexOf);
				remotePath=remotePath+substringPre;
				fileName  = fileName.substring(lastIndexOf+1, fileName.length());
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
				InputStream fileSteam = getFileSteam(url, port, username, password, remotePath, fileName);
				Boolean result = FtpUtil.uploadFile(url,
						port, username,
						password, remotePath, fileNewname,
						fileSteam);
			}
			ftp.logout();
		} catch (IOException e) {
			System.out.println("ftp:copyFile:error====");
//			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return picBufferUpload.toString();
	}
	
	public static InputStream getFileSteam(String url, // FTP服务器
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName// 要下载的文件名
	) {
 
		boolean success = false;
		InputStream input=null;
		FTPClient ftp = new FTPClient();
		StringBuffer filePathBuffer=new StringBuffer("");
		try {
			int reply;
			ftp.connect(url, port);
			ftp.enterLocalPassiveMode();
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
//			System.out.println("getReplyCode="+reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			String[] fileNameArr = fileName.split("/");
			for (int i = 0; i < fileNameArr.length; i++) {
				if (" ".equals(fileNameArr[i])) {
					continue;
				}
				if (i != fileNameArr.length-1) {
					filePathBuffer.append("/").append(fileNameArr[i]);
				}
				if (i == fileNameArr.length-1) {
					fileName=fileNameArr[i];
				}
			}
			ftp.changeWorkingDirectory(remotePath+filePathBuffer.toString());// 转移到FTP服务器目录
			FTPFile[] fs = null;
			String[] names = null;
			if (ftp.listFiles() != null) {
				fs = ftp.listFiles();
			}
			input=ftp.retrieveFileStream(fileName);
		} catch (IOException e) {
			System.out.println("error:getFileSteam======");
//			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return input;
	}
	
	public static Boolean fileIsExistFTP(String url, // FTP服务器
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName// 要下载的文件名
	) {
 
		boolean success = false;
		InputStream input=null;
		FTPClient ftp = new FTPClient();
		StringBuffer filePathBuffer=new StringBuffer("");
		try {
			int reply;
			ftp.connect(url, port);
			ftp.enterLocalPassiveMode();
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
//			System.out.println("getReplyCode="+reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			String[] fileNameArr = fileName.split("/");
			for (int i = 0; i < fileNameArr.length; i++) {
				if (" ".equals(fileNameArr[i])) {
					continue;
				}
				if (i != fileNameArr.length-1) {
					filePathBuffer.append("/").append(fileNameArr[i]);
				}
				if (i == fileNameArr.length-1) {
					fileName=fileNameArr[i];
				}
			}
			ftp.changeWorkingDirectory(remotePath+filePathBuffer.toString());// 转移到FTP服务器目录
			FTPFile[] fs = null;
			String[] names = null;
			if (ftp.listFiles() != null) {
				fs = ftp.listFiles();
			}
			input=ftp.retrieveFileStream(fileName);
			if (input!=null) {
				success=true;
			}
		} catch (IOException e) {
			System.out.println("error:fileIsExistFTP======");
//			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	 public static boolean refileName(String ftpHost, String ftpUserName, String ftpPassword, Integer ftpPort, String ftpPath,
			    String fileName,String newName){
			        boolean isRename = false;
			        FTPClient ftpClient = new FTPClient();
			        try {
			        	ftpClient.connect(ftpHost, ftpPort);
			        	ftpClient.enterLocalPassiveMode();
						// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			        	ftpClient.login(ftpUserName, ftpPassword);// 登录
						int reply = ftpClient.getReplyCode();
//						System.out.println("getReplyCode="+reply);
						if (!FTPReply.isPositiveCompletion(reply)) {
							ftpClient.disconnect();
							return false;
						}
						int lastIndexOf = fileName.lastIndexOf("/");
						int lastIndexOfNew = newName.lastIndexOf("/");
						if (lastIndexOf>=0) {
							String substringPre = fileName.substring(0, lastIndexOf);
							ftpPath=ftpPath+substringPre;
							fileName  = fileName.substring(lastIndexOf+1, fileName.length());
						}
						if (lastIndexOfNew>0) {
							newName  = newName.substring(lastIndexOfNew+1, newName.length());
						}
			            ftpClient.changeWorkingDirectory(ftpPath);
			            isRename = ftpClient.rename(fileName, newName);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }finally {
			            if(ftpClient.isConnected()){
			                try{
			                    ftpClient.disconnect();
			                }catch(IOException e){
			                	System.out.println("error:refileName======");
//			                    e.printStackTrace();
			                }
			            }
			        }
			        return isRename;
			    }
	 
	 public static boolean copyfileName(String ftpHost, String ftpUserName, String ftpPassword, Integer ftpPort, String ftpPath,
			    String fileName,String newName){
			        boolean isRename = false;
			        FTPClient ftpClient = new FTPClient();
			        try {
			        	ftpClient.connect(ftpHost, ftpPort);
			        	ftpClient.enterLocalPassiveMode();
						// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			        	ftpClient.login(ftpUserName, ftpPassword);// 登录
						int reply = ftpClient.getReplyCode();
//						System.out.println("getReplyCode="+reply);
						if (!FTPReply.isPositiveCompletion(reply)) {
							ftpClient.disconnect();
							return false;
						}
						int lastIndexOf = fileName.lastIndexOf("/");
						int lastIndexOfNew = newName.lastIndexOf("/");
						if (lastIndexOf>=0) {
							String substringPre = fileName.substring(0, lastIndexOf);
							ftpPath=ftpPath+substringPre;
							fileName  = fileName.substring(lastIndexOf+1, fileName.length());
						}
						if (lastIndexOfNew>0) {
							newName  = newName.substring(lastIndexOfNew+1, newName.length());
						}
			            ftpClient.changeWorkingDirectory(ftpPath);
			            isRename = ftpClient.rename(fileName, newName);
			        } catch (Exception e) {
			        	System.out.println("error:copyfileName======");
//			            e.printStackTrace();
			        }finally {
			            if(ftpClient.isConnected()){
			                try{
			                    ftpClient.disconnect();
			                }catch(IOException e){
			                	System.out.println("error:copyfileName======");
//			                    e.printStackTrace();
			                }
			            }
			        }
			        return isRename;
			    }
	public static void main(String[] args)  {
		String ftpHost=CommonConstant.FTP_ADDR;
		String ftpUserName=CommonConstant.FTP_USER;
		String ftpPassword=CommonConstant.FTP_PWD;
		Integer ftpPort=CommonConstant.FTP_PORT;
		String ftpPath=CommonConstant.FTP_PATH;
		String filename="/upimg/gzdl/STSource/2014111723382189714697/SYS201411172338224440679853_ST/SYS201411172338224440679853_ST.001.jpeg";
//		String filename="/upimg/czdl/STSource/20131101154744086371579/SYS201311011547440863715010_ST/images0.png";
//		String filename="/upimages/quiz/images/201208/32/abd3628d.png";
//		String fileName="/upimg/czyy/STSource/2014101713543587931448/SYS201410171354373637866245_ST/SYS201410171354373637866245_ST.001.png";
//		String newName="/upimg/czyy/STSource/2014101713543587931448/SYS201410171354373637866245_ST/SYS201410171354373637866245_ST001.png";
//		String filename="/upimg/pic1/upload/papers/g06/20120917/2012091717220667349636.png";
		String newName="001.png";

//      从ftp下载文件
//		Boolean result = FtpUtil.downFile("192.168.242.140",
//				21,"ftp-lpf",
//				"lpf123lpf","/usr/local/ftpdir/20w_quan_images/img/20090316", "20090316213924001.gif",
//				"E:/");
//		InputStream fileIo = FtpUtil.getFileSteam("192.168.242.140",
//				21,"root",
//				"lpf666","/usr/local/ftpdir/20w_quan_images","/img/20090316/20090316213924001X.gif"
//				);
//		try {
//			
//			String url = QiniuUtil.uploadFile("20090316213924001.gif", fileIo, "common-pic", false);
//			System.out.println("sucess----"+url);
//		} catch (Exception e) {
//			System.out.println("error----"+e.toString());
//		}
//		boolean refileName = refileName(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, newName);
//		if (refileName) {
//			System.out.println("名字修改成功");
//		}else{
//			System.out.println("名字修改失败");
//
//		}
		InputStream in = FtpUtil.getFileSteam(CommonConstant.FTP_ADDR,
				21,CommonConstant.FTP_USER,
				CommonConstant.FTP_PWD,CommonConstant.FTP_PATH, filename
				);
        String url = QiniuUtil.uploadFile("002.png", in, "common-pic", false);
        System.out.println("url="+url);
        
//		String copyFile = copyFile(CommonConstant.FTP_ADDR, CommonConstant.FTP_PORT, CommonConstant.FTP_USER, CommonConstant.FTP_PWD, CommonConstant.FTP_PATH, 
//        		fileName, newName);
//		
	
	}
	
}
