package com.eebbk.internal.question.convertor.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.eebbk.edu.common.util.page.PageBean;
import com.eebbk.edu.common.util.page.PageParam;
import com.eebbk.internal.question.convertor.pojo.GradePojo;
import com.eebbk.internal.question.convertor.vo.DataCountsVo;
import com.eebbk.internal.question.convertor.vo.GradeAndSubjectVo;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;

public interface QuestionConvertService {

	List<QuestionsVo> selectQuestion();

	void insertQuestionXuekubao(Integer pageNum, Integer pageSize);
	
	
	
	PageBean selectTikubaoinfo(Map<String, Object> paramMap, PageParam pageParam);

	String  uploadJsFile(String fileName);

	List<GradePojo> selectAllGrade();

	List<GradeAndSubjectVo> selectAllSubjectByGradeName(String gradeName);

	List<QuestionXuekubaoQueryVo> getQuestionFileBysubject();
	
	List<DataCountsVo> getQueCountsNoImg();
	
	Integer getTotalQueCounts();
	
	List<DataCountsVo> getNoExistSituationByContent();
	
	List<DataCountsVo> getNoExistSituationByAnswer();
	
	List<DataCountsVo> getNoExistSituationBySolution();
	
	List<DataCountsVo> getQueCountsByGradeAndSubject();
	
	List<DataCountsVo> getQueCountsByGrade();
	
	List<DataCountsVo> getQueCountsBysubject();

	void insertQuestionXuekubao(int pageNum, int pageSize);

	void dataHandler(MultipartFile file);

	PageBean selectShijuanTikubaoinfo(Map<String, Object> paramMap, PageParam pageParam);
}
