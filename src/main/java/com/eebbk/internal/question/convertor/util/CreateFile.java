package com.eebbk.internal.question.convertor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.eebbk.internal.question.convertor.constant.CommonConstant;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;

public class CreateFile {
	public static String urlPath=CommonConstant.PATH_HTML_DATA;//文件存放路径
	public static String urlPathTxt=CommonConstant.PATH_TXT_DATA;//文件存放路径
	public static String urlPathImgs=CommonConstant.PATH_IMG_DATA;//文件存放路径toku/2019
	public static String urlPathPreImgs=CommonConstant.PATH_LOCAL_TIKUPIC;//文件存放路径toku/2019

	
//	public static String urlPath="E:/filepath/tiku/";//文件存放路径

	
	public static void create(QuestionXuekubaoQueryVo que) throws Exception{
		String  FileNamePath = urlPath + "\\" + que.getCourse();
		FileOutputStream fis=null; 
		OutputStreamWriter out = null;
		try {
			File fileDir = new File(FileNamePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs(); 
			}
			if (que.getContent()!=null) {
				File file = new File(FileNamePath+"\\"+que.getIdOld()+"_content.html");
				if (!file.exists()) {
					
					boolean createNewFile = file.createNewFile();
				}
				fis=new FileOutputStream(FileNamePath+File.separator+que.getIdOld()+"_content.html");
				out=new OutputStreamWriter(fis,"UTF-8");
				String strTemp = que.getContent();
				out.write(strTemp);
				out.flush(); 
				out.close();
				fis.close();
				System.out.println("生成文件"+que.getIdOld()+"_content.html"+"成功");
			}
			
			if (que.getAnswer()!=null) {
				fis=new FileOutputStream(FileNamePath+File.separator+que.getIdOld()+"_answer.html");
				out=new OutputStreamWriter(fis,"UTF-8");
				String strTemp = que.getAnswer();
				out.write(strTemp);
				out.flush(); 
				out.close();
				fis.close();
				System.out.println("生成文件"+que.getIdOld()+"_answer.html"+"成功");
			}
			
			if (que.getSolution()!=null) {
				fis=new FileOutputStream(FileNamePath+File.separator+que.getIdOld()+"_solution.html");
				out=new OutputStreamWriter(fis,"UTF-8");
				String strTemp = que.getSolution();
				out.write(strTemp);
				out.flush(); 
				out.close();
				fis.close();
				System.out.println("生成文件"+que.getIdOld()+"_sulution.html"+"成功");
			}
		} catch (Exception e) {
			if(fis!=null)
				fis.close();
			if(out!=null){
				out.close();
			}
			e.printStackTrace();
		}finally{
			try{
				if(fis!=null)
					fis.close();// 关闭文件流
				if(out!=null)
					out.close();
			}catch (Exception e) {
				System.out.println("CreateDayFiles关闭文件流失败");
			}
		}
	}
	
	
	public static void createFileBylist(List<QuestionXuekubaoQueryVo> queList,String grade,String course,int htmlCount) throws Exception{
		if (CollectionUtils.isNotEmpty(queList)) {
			String bodyInfo=getContentToTable(queList);
			String convertHtml = HtmlTools.convertHtmlTowaibao(bodyInfo);
				String  FileNamePath = urlPath + "\\" + course+"\\"+course+grade;
				String FilePath=FileNamePath+File.separator+course+grade+"_content"+htmlCount+".html";
				FileOutputStream fis=null; 
				OutputStreamWriter out = null;
				try {
					File fileDir = new File(FileNamePath);
					if (!fileDir.exists()) {
						fileDir.mkdirs(); 
					}
					File file = new File(FilePath);
					if (!file.exists()) {
						boolean createNewFile = file.createNewFile();
					}
					fis=new FileOutputStream(FilePath);
					out=new OutputStreamWriter(fis,"UTF-8");
					out.write(convertHtml);
					out.flush(); 
					out.close();
					fis.close();
					System.out.println("生成文件成功");
				} catch (Exception e) {
					if(fis!=null)
						fis.close();
					if(out!=null){
						out.close();
					}
					e.printStackTrace();
				}finally{
					try{
						if(fis!=null)
							fis.close();// 关闭文件流
						if(out!=null)
							out.close();
					}catch (Exception e) {
						System.out.println("CreateDayFiles关闭文件流失败");
					}
				}
		}
	}
	//生成html的内容
	private static String getContentToTable(List<QuestionXuekubaoQueryVo> queList) {
		StringBuffer tableBf = new StringBuffer("");
		tableBf.append("<div class=\"css_qustion_top\">").append("\n");	
		tableBf.append("<span style=\"font-size: 30px;\">题库信息查询</span>").append("\n");
		tableBf.append("</div>").append("\n");
		tableBf.append("<div class=\"css_middle\">").append("\n");
		tableBf.append(" <div class=\"css_nav\">").append("\n");
		tableBf.append("   <p class=\"css_shouye\" style=\"font-size: 18px;\">首页&nbsp;&gt;&nbsp;题库信息展示</p>").append("\n");
		tableBf.append(" </div>").append("\n");
		tableBf.append("</div><br>").append("\n");
		tableBf.append("<div class=\"ddlsyd_page\">").append("\n");
		
		tableBf.append("	<table id=\"datagrid\" class=\"table table-bordered table-striped table-hover\">").append("\n");
		tableBf.append("	<thead>").append("\n");
		tableBf.append("	<th>题库ID</th>").append("\n");
		tableBf.append("	<th>题目</th>").append("\n");
		tableBf.append("	<th>答案</th>").append("\n");
		tableBf.append("	<th>分析</th>").append("\n");
		tableBf.append("	<th>科目</th>").append("\n");
		tableBf.append("	<th>年级</th>").append("\n");
		tableBf.append("	</thead>").append("\n");
		tableBf.append("	<tbody>").append("\n");
		for (int i = 0; i <queList.size(); i++) {
			QuestionXuekubaoQueryVo queVo = queList.get(i);
			tableBf.append("	<tr>").append("\n");
			tableBf.append("		<td>"+queVo.getIdOld()+"</td>").append("\n");
			tableBf.append("		<td>"+queVo.getContent().replaceAll("<TR>","").replaceAll("</TR>","").replaceAll("</TD>", "").replaceAll("<TD>", "")+"</td>").append("\n");
			tableBf.append("		<td>"+queVo.getAnswer().replaceAll("<TR>","").replaceAll("</TR>","").replaceAll("</TD>", "").replaceAll("<TD>", "")+"</td>").append("\n");
			tableBf.append("		<td>"+queVo.getSolution().replaceAll("<TR>","").replaceAll("</TR>","").replaceAll("</TD>", "").replaceAll("<TD>", "")+"</td>").append("\n");
			tableBf.append("		<td>"+queVo.getCourse()+"</td>").append("\n");
			tableBf.append("		<td>"+queVo.getGrade()+"</td>").append("\n");
			tableBf.append("	</tr>").append("\n");
		}
		tableBf.append("	</tbody>").append("\n");
		tableBf.append("	</table>").append("\n");
		tableBf.append("</div>").append("\n");
		tableBf.append("<div style=\"height: 200px\"></div>").append("\n");
		return tableBf.toString();
	}

    /**
     * @author lipf
     * TODO导出txt文件
     * @method exportTxtHandler
     * @param next
     * @param i
     * @return void
     * @date 2019年5月28日 下午7:27:33
     */
	public static void exportTxtHandler(List<Long> next, int i) {
		if (CollectionUtils.isNotEmpty(next)) {
			
			String  FileNamePath = urlPathTxt;
			String FilePath=urlPathTxt+File.separator;
			if (i==0) {
				FilePath=FilePath+"qiniu_pic_flag"+".txt";
			}else{
				FilePath=FilePath+"qiniu_pic_flag"+i+".txt";
			}
			FileOutputStream fis=null; 
			StringBuffer convertTxt = new StringBuffer("");
			for (int j = 0; j < next.size(); j++) {
				convertTxt=convertTxt.append(next.get(j)).append("\n");
			}
			OutputStreamWriter out = null;
			try {
				File fileDir = new File(FileNamePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs(); 
				}
				File file = new File(FilePath);
				if (!file.exists()) {
					boolean createNewFile = file.createNewFile();
				}
				fis=new FileOutputStream(FilePath);
				out=new OutputStreamWriter(fis,"UTF-8");
				out.write(convertTxt.toString());
				out.flush(); 
				out.close();
				fis.close();
				System.out.println("生成文件成功"+urlPathTxt);
			} catch (Exception e) {
				if(fis!=null){
					try {
						fis.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				if(out!=null){
					try {
						out.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				e.printStackTrace();
			}finally{
				try{
					if(fis!=null)
						fis.close();// 关闭文件流
					if(out!=null)
						out.close();
				}catch (Exception e) {
					System.out.println("CreateDayFiles关闭文件流失败");
				}
			}
		}

	}

	/**
	 * @author lipf
	 * 生成图片
	 * @method exportImgsHandler
	 * @param questionByIds
	 * @return void
	 * @date 2019年6月9日 上午9:58:30
	 */
	
	public static void exportImgsHandler(List<QuestionXuekubaoQueryVo> selectXuekubaoQuesitonsUnuploadPicListByIds) {
		if (CollectionUtils.isNotEmpty(selectXuekubaoQuesitonsUnuploadPicListByIds)) {
			for (QuestionXuekubaoQueryVo que:selectXuekubaoQuesitonsUnuploadPicListByIds) {
				Integer picExistflag = que.getPicExistflag();
				String content = que.getContent();
				String answer = que.getAnswer();
				String solution = que.getSolution();
				generateImgs(content,answer,solution);
			}
		}
		
	}

	/**
	 * @author lipf
	 * 生成本地图片文件
	 * @method generateImgs
	 * @param content
	 * @param answer
	 * @param solution
	 * @return void
	 * @date 2019年6月9日 上午10:14:30
	 */
	private static void generateImgs(String content, String answer, String solution) {
		List<String> contentImgList = HtmlUtil.extralImg(content);
		List<String> answerImgList = HtmlUtil.extralImg(content);
		List<String> solutionImgList = HtmlUtil.extralImg(content);
		String urlPathImgs=CommonConstant.PATH_LOCAL_TIKUPIC;//文件存放路径toku/2019

		String urlPathImgsNew=CommonConstant.PATH_IMG_DATA;//文件存放路径toku/2019
		if (CollectionUtils.isNotEmpty(contentImgList)) 
		{
			for (String contentImg:contentImgList) {
				if (StringUtils.isNotEmpty(contentImg)) {
					generateImgLocal(contentImg);
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(answerImgList)) 
		{
			for (String answerImg:answerImgList) {
				if (StringUtils.isNotEmpty(answerImg)) {
					generateImgLocal(answerImg);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(solutionImgList)) 
		{
			for (String sulutionImg:solutionImgList) {
				if (StringUtils.isNotEmpty(sulutionImg)) {
					generateImgLocal(sulutionImg);
				}
			}
		}
	}

	private static void generateImgLocal(String fileName) {
		try {
			Boolean fileIsExist = HandlerFileNameSuffix.fileIsExist(urlPathPreImgs+fileName);
            if (fileIsExist) {
            	FileInputStream input = new FileInputStream(urlPathPreImgs+fileName);
            	int lastIndexOf = fileName.lastIndexOf("/");
            	String urlPic = fileName.substring(0, lastIndexOf+1);
            	File fileDir = new File(urlPathImgs+urlPic);
				if (!fileDir.exists()) {
					fileDir.mkdirs(); 
				}
            	File fileNamePath = new File(urlPathImgs+fileName);
            	if (!fileNamePath.exists()) {
					
            		FileOutputStream output = new FileOutputStream(urlPathImgs+fileName);
            		int read = input.read();       
            		while ( read != -1 ) {
            			output.write(read);
            			read = input.read();
            		}          
            		input.close();
            		output.close();
				}
			}
        } catch (Exception e) {
            System.out.println("==error:copyLocalFile:"+e.toString());
        }
		if (!fileName.contains("question.css")) {
			System.out.println("==sucessful:"+urlPathImgs+fileName);
		}
		
	}

	
	
	
	

   
	

	
}
