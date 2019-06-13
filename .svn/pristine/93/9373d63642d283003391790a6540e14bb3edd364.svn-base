package com.eebbk.internal.question.convertor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.edu.common.util.exception.BizException;
import com.eebbk.edu.common.web.util.WebPathUtil;
import com.eebbk.internal.question.convertor.dao.mapper.QuestionMapper2;
import com.eebbk.internal.question.convertor.pojo.QuestionXuekubaoPojo;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;

public class XuekubaoDataHander {
	public static void dataHandler(File file,QuestionMapper2 questionMapper,
			Integer pageSize,Integer tableFlag,Integer handerWay) {
		String originalFilename = file.getName();
        if (!originalFilename.endsWith("txt"))
        {
        	return;
        }
        List<Long> queLists=new ArrayList<Long>();
        InputStreamReader read=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
//			read = new InputStreamReader(file.getInputStream(),"utf-8");
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxtJson="";
            List<QuestionsVo> questionByIds =null;
            Integer count=0;
            Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = sdf.parse(createTime);
            while((lineTxtJson = bufferedReader.readLine()) != null){
            	++count;
                System.out.println("lineTxt="+lineTxtJson);
                if (StrUtils.isNotEmpty(lineTxtJson)) {
                	queLists.add(Long.parseLong(lineTxtJson));
				}
//                if (count==10) {
//					break;
//				}
            }
            System.out.println("txt_count="+count);
            read.close();
            System.out.println("queLists:Size="+queLists.size()+"info:"+queLists.toString());
            if (CollectionUtils.isNotEmpty(queLists)) {
            	//把所有的ID取出来进行分组处理
        		 List<List<Long>> fixedGrouping = AssignListUtils.fixedGrouping(queLists, pageSize);
        		 if (CollectionUtils.isNotEmpty(fixedGrouping)) {
        			 Iterator<List<Long>> iterator = fixedGrouping.iterator();
        				while (iterator.hasNext()) {
        					List<Long> next = iterator.next();
        					if (CollectionUtils.isNotEmpty(next)) {
        						questionByIds = questionMapper.selectQuesitonsListByIds(next,0,pageSize);
        						if(CollectionUtils.isNotEmpty(questionByIds)){
        			            	System.out.println("处理数据量为："+questionByIds.size());
        			            }
        						//1 更新 0 新增
								XuekubaoDataHandlerUtil.insertQuestionXuekubao(questionByIds, questionMapper,tableFlag,handerWay);
        					}
        				}
        		 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 导出静态页面@author admin
	 * TODO
	 * @method dataHandlerCreateHtml
	 * @param file
	 * @param questionMapper
	 * @param pageSize
	 * @param tableFlag
	 * @return 
	 * @return void
	 * @throws Exception 
	 * @date 2019年5月18日 下午5:31:26
	 */
	public static List<HashMap<String, Object>> dataHandlerCreateHtml(File file,QuestionMapper2 questionMapper,Integer pageSize,Integer tableFlag) throws Exception {
        List<HashMap<String, Object>> listResult=new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> result = null;
        List<Long> queLists=null;
        //每个sheet中的内容
        List<HashMap<String, Object>> listSheetMap=new ArrayList<HashMap<String, Object>>();
        List<QuestionXuekubaoQueryVo> questionByIds =null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Integer count=0;
        //获取html中的内容，返回的listMap大小应该是sheet的个数
        listSheetMap=getObjectByExcel(file);
        if (CollectionUtils.isNotEmpty(listSheetMap)) {
			for (int i = 0; i < listSheetMap.size(); i++) {
				//该map存放年级、科目和实体对象
				result = new HashMap<String,Object>();
				//该map存放年级、科目和ids
				HashMap<String, Object> hashMap = listSheetMap.get(i);
				String dirNameGrade = (String) hashMap.get("dirNameGrade");
		        String dirNameSubject = (String) hashMap.get("dirNameSubject");
		        queLists= (List<Long>) hashMap.get("queListsLong");
		        System.out.println("queLists:Size="+queLists.size()+"info:"+queLists.toString());
		        if (CollectionUtils.isNotEmpty(queLists)) {
					questionByIds = questionMapper.selectXuekubaoQuesitonsListByIds(queLists,0,pageSize);
				}
		        result.put("dirNameGrade", dirNameGrade);
		        result.put("dirNameSubject", dirNameSubject);
		        result.put("queListQueObject", questionByIds);
		        listResult.add(result);
			}
		}
        
        
        
		return listResult;
        
	}

	/**
	 * @author lipf
	 * 获取html中的内容
	 * @method getObjectByExcel
	 * @param file
	 * @return
	 * @return HashMap<String,Object>
	 * @throws Exception 
	 * @date 2019年5月18日 下午5:41:02
	 */
	private static List<HashMap<String, Object>> getObjectByExcel(File file) throws Exception {
		List<HashMap<String, Object>> listSheet = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map = null;
		List<Long> queListsLong=null;
		String originalFilename = file.getName();
		String dirNameGrade="defaultGradeDir";
		String dirNameSubject="defaultSubjectDir";
        if (!originalFilename.endsWith("xlsx")&&!originalFilename.endsWith("xls"))
        {
            System.out.println("error:====文件格式不对");
        }
        XSSFWorkbook xwb = null;
        FileInputStream fileInputStream=new FileInputStream(file);
        // 读取excel工作簿
        xwb = new XSSFWorkbook(fileInputStream);
        // 读取excel的词库页
//        XSSFSheet sheet = xwb.getSheet("Sheet1");
        int numberOfSheets = xwb.getNumberOfSheets();
        //循环每个sheet，生成对应的html内容
        for (int k = 0; k < numberOfSheets; k++) {
        	//存放ids的集合
        	queListsLong=new ArrayList<Long>();
        	//每循环一个sheet应该生成一个Map存储信息
        	map = new HashMap<String,Object>();
        	String sheetName = xwb.getSheetName(k);
        	XSSFSheet sheet = xwb.getSheet(sheetName);
        	for (int i = 1; i <= sheet.getLastRowNum(); i++)
            {
            	String id="";
            	List<String> otherAppList = new ArrayList<String>();
                XSSFRow row = sheet.getRow(i);
                if (row != null)
                {
                	 
                	Boolean mergedRegion0 = isMergedRegion(sheet, i, 0);
                	if (mergedRegion0) {
                		id = getMergedRegionValue(sheet, i, 0);
    				}else{
    					XSSFCell cell = row.getCell(0);
    					if (cell!=null) {
    						id = getCellValue(cell);
    					}
    				}
                	if (StringUtils.isNotEmpty(id)) {
                		queListsLong.add(Long.parseLong(id));
					}else{
						continue;
					}
                	Boolean mergedRegion1 = isMergedRegion(sheet, i, 1);
                	if (mergedRegion1) {
                		dirNameGrade = getMergedRegionValue(sheet, i, 1);
                	}else{
                		XSSFCell cell = row.getCell(1);
                		if (cell!=null) {
                			dirNameGrade = cell.getStringCellValue();
                		}
                	}
                	Boolean mergedRegion2 = isMergedRegion(sheet, i, 2);
                	if (mergedRegion2) {
                		dirNameSubject = getMergedRegionValue(sheet, i, 2);
    				}else{
    					XSSFCell cell = row.getCell(2);
    					if (cell!=null) {
    						dirNameSubject = getCellValue(cell);
    					}
    				}
                	
                }
                
            }
        	map.put("dirNameGrade", dirNameGrade);
        	map.put("dirNameSubject", dirNameSubject);
        	map.put("queListsLong", queListsLong);
        	listSheet.add(map); 
        }
		return listSheet;
	}
	private static Boolean isMergedRegion(XSSFSheet sheet,int row,int column){
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row>=firstRow&&row<=lastRow) {
				if (column>=firstColumn&&column<=lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	public static String getMergedRegionValue(XSSFSheet sheet ,int row , int column){      
        int sheetMergeCount = sheet.getNumMergedRegions();      
              
        for(int i = 0 ; i < sheetMergeCount ; i++){      
            CellRangeAddress ca = sheet.getMergedRegion(i);      
            int firstColumn = ca.getFirstColumn();      
            int lastColumn = ca.getLastColumn();      
            int firstRow = ca.getFirstRow();      
            int lastRow = ca.getLastRow();      
            if(row >= firstRow && row <= lastRow){      
                if(column >= firstColumn && column <= lastColumn){      
                    XSSFRow xRow = sheet.getRow(firstRow);     
                    XSSFCell xCell = xRow.getCell(firstColumn);      
                    return getCellValue(xCell);
                }      
            }      
        }      
        return null ;      
    }
	public static String getCellValue(XSSFCell cell){      
        if(cell == null) return "";    
        
        if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){      
            return cell.getStringCellValue();      
        }else if(cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){      
            return String.valueOf(cell.getBooleanCellValue());      
        }else if(cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA){      
            return cell.getCellFormula() ;      
        }else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){      
//            return String.valueOf(cell.getNumericCellValue());   
        	cell.setCellType(1);
        	return cell.getStringCellValue();      
        }  
        return "";      
    }
	/**
	 * @author lipf
	 * TODO
	 * @method exportTxtHandler
	 * @param file
	 * @param pageSize
	 * @return void
	 * @date 2019年5月28日 下午7:28:38
	 */
	public static void exportTxtHandler(File file,
			Integer pageSize) {
		String originalFilename = file.getName();
        if (!originalFilename.endsWith("txt"))
        {
        	return;
        }
        List<Long> queLists=new ArrayList<Long>();
        InputStreamReader read=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxtJson="";
            List<QuestionsVo> questionByIds =null;
            Integer count=0;
            Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = sdf.parse(createTime);
            while((lineTxtJson = bufferedReader.readLine()) != null){
            	++count;
                System.out.println("lineTxt="+lineTxtJson);
                if (StrUtils.isNotEmpty(lineTxtJson)) {
                	queLists.add(Long.parseLong(lineTxtJson));
				}
            }
            System.out.println("txt_count="+count);
            read.close();
            System.out.println("queLists:Size="+queLists.size()+"info:"+queLists.toString());
            if (CollectionUtils.isNotEmpty(queLists)) {
            	//把所有的ID取出来进行分组处理
        		 List<List<Long>> fixedGrouping = AssignListUtils.fixedGrouping(queLists, pageSize);
        		 if (CollectionUtils.isNotEmpty(fixedGrouping)) {
        			 for (int i = 0; i < fixedGrouping.size(); i++) {
        				 CreateFile.exportTxtHandler(fixedGrouping.get(i),i);
					}
        		 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * @author lipf
	 * 导出图片题的图片文件
	 * @method exportPicsHandler
	 * @param file
	 * @param pageSize
	 * @param questionMapper 
	 * @return void
	 * @date 2019年6月9日 上午9:45:32
	 */
	public static void exportimgsHandler(File file,
			Integer pageSize, QuestionMapper2 questionMapper) {
		String originalFilename = file.getName();
        if (!originalFilename.endsWith("txt"))
        {
        	return;
        }
        List<Long> queLists=new ArrayList<Long>();
        InputStreamReader read=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxtJson="";
            List<QuestionXuekubaoQueryVo> selectXuekubaoQuesitonsUnuploadPicListByIds =null;
            Integer count=0;
            Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = sdf.parse(createTime);
            while((lineTxtJson = bufferedReader.readLine()) != null){
            	++count;
                System.out.println("lineTxt="+lineTxtJson);
                if (StrUtils.isNotEmpty(lineTxtJson)) {
                	queLists.add(Long.parseLong(lineTxtJson));
				}
            }
            System.out.println("txt_count="+count);
            read.close();
            System.out.println("queLists:Size="+queLists.size()+"info:"+queLists.toString());
            
            if (CollectionUtils.isNotEmpty(queLists)) {
            	//把所有的ID取出来进行分组处理
	       		 List<List<Long>> fixedGrouping = AssignListUtils.fixedGrouping(queLists, pageSize);
	       		 if (CollectionUtils.isNotEmpty(fixedGrouping)) {
	       			 for (int i = 0; i < fixedGrouping.size(); i++) {
	       				List<Long> next = fixedGrouping.get(i);
 						if (CollectionUtils.isNotEmpty(next)) {
 							selectXuekubaoQuesitonsUnuploadPicListByIds = questionMapper.selectXuekubaoQuesitonsUnuploadPicListByIds(next,0,pageSize);
 							CreateFile.exportImgsHandler(selectXuekubaoQuesitonsUnuploadPicListByIds);
						}
					 }
	       		 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @author lipf
	 * 通过id插入数据
	 * @method insertTableByidHandler
	 * @param file
	 * @param pageSize
	 * @param questionMapper
	 * @return void
	 * @date 2019年6月10日 上午8:57:04
	 */
	public static void insertTableByidHandler(File file,
			Integer pageSize, QuestionMapper2 questionMapper) {
		String originalFilename = file.getName();
        if (!originalFilename.endsWith("txt"))
        {
        	return;
        }
        List<Long> queLists=new ArrayList<Long>();
        InputStreamReader read=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxtJson="";
            List<QuestionXuekubaoQueryVo> selectXuekubaoQuesitonsUnuploadPicListByIds =null;
            Integer count=0;
            Date d = new Date();
            String createTime = sdf.format(d);
            Date parseDate = sdf.parse(createTime);
            while((lineTxtJson = bufferedReader.readLine()) != null){
            	++count;
                System.out.println("lineTxt="+lineTxtJson);
                if (StrUtils.isNotEmpty(lineTxtJson)) {
                	queLists.add(Long.parseLong(lineTxtJson));
				}
            }
            System.out.println("txt_count="+count);
            read.close();
            System.out.println("queLists:Size="+queLists.size()+"info:"+queLists.toString());
            
            if (CollectionUtils.isNotEmpty(queLists)) {
            	//把所有的ID取出来进行分组处理
	       		 List<List<Long>> fixedGrouping = AssignListUtils.fixedGrouping(queLists, 500);
	       		 if (CollectionUtils.isNotEmpty(fixedGrouping)) {
	       			 for (int i = 0; i < fixedGrouping.size(); i++) {
	       				List<Long> next = fixedGrouping.get(i);
 						if (CollectionUtils.isNotEmpty(next)) {
 							selectXuekubaoQuesitonsUnuploadPicListByIds = questionMapper.selectQuesitonsNoUploadListByIds(next);
 							questionMapper.insertQuestionUnuploadBatch(selectXuekubaoQuesitonsUnuploadPicListByIds);
 							System.out.println("插入第"+(i+1)*500+"条数据。。。。。");
 						}
					 }
	       		 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
