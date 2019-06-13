package com.eebbk.internal.question.convertor.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;

import com.eebbk.internal.question.convertor.pojo.GradePojo;
import com.eebbk.internal.question.convertor.pojo.QuestionXuekubaoPojo;
import com.eebbk.internal.question.convertor.pojo.QuestionsPojo;
import com.eebbk.internal.question.convertor.pojo.SubQuestionPojo;
import com.eebbk.internal.question.convertor.pojo.SubjectPojo;
import com.eebbk.internal.question.convertor.vo.DataCountsVo;
import com.eebbk.internal.question.convertor.vo.GradeAndSubjectVo;
import com.eebbk.internal.question.convertor.vo.QuestionXuekubaoQueryVo;
import com.eebbk.internal.question.convertor.vo.QuestionsListVo;
import com.eebbk.internal.question.convertor.vo.QuestionsVo;
import com.eebbk.internal.question.convertor.vo.SubQuestionVo;

public interface QuestionMapper {


	Integer insertQuestionXuekubao(@Param("que") QuestionXuekubaoPojo que);

	int insertQuestionXuekubaoBatch(List<QuestionXuekubaoPojo> questionXuekubaoQueryPojo);
	
	List<QuestionsVo> selectQuestion();
	
	List<SubQuestionVo> selectSubQuestionByQueId(Integer pid);

	Integer selectQuesitonIsExist(@Param("idOld")Integer idOld);

	Integer updateQuestionXuekubao(@Param("que")QuestionXuekubaoPojo questionXuekubao);

	List<GradePojo> selectAllGrade();
	
	List<GradeAndSubjectVo> selectAllSubjectByGradeName(@Param("gradeName")String gradeName);

	List<QuestionXuekubaoQueryVo> getQuestionFileBysubject();
	
	
	List<DataCountsVo> getQueCountsNoImg();
	
	List<DataCountsVo> getNoExistSituationByContent();
	
	List<DataCountsVo> getNoExistSituationByAnswer();
	
	List<DataCountsVo> getNoExistSituationBySolution();
	
	List<DataCountsVo> getQueCountsByGradeAndSubject();
	
	List<DataCountsVo> getQueCountsByGrade();
	
	List<DataCountsVo> getQueCountsBysubject();
	
	Integer getTotalQueCounts();

	List<QuestionsVo> selectQuestionPage(@Param("pageNum")int pageNum,@Param("pageSize") int pageSize);
	
	List<QuestionsVo> selectQuesitonsListByIds(@Param("Ids")List<Long> Ids);
	
	
	
}