package com.eebbk.internal.question.convertor.vo;

import java.util.Date;
import java.util.List;

public class QuestionsVo {
	/**
	 */
	private Integer id;
	private String title;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String optionE;
	private String answer1;
	private String answer2;
	private String parse;
	private String qtpye;
	private String diff;
	private String subjectId;
	private String subjectName;
	private Integer gradeId;
	private String gradeName;
	private String knowledges;
	private String area;
	private Integer year;
	private String paperTpye;
	private String source;
	private String fromSite;
	private Integer isSub;
	private Integer isNormal;
	private Integer isKonw;
	private String tiid;
	private String similarity;
	private Integer isunique;
	private Integer paperFlag;
	private List<SubQuestionVo> subQuestion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getParse() {
		return parse;
	}
	public void setParse(String parse) {
		this.parse = parse;
	}
	public String getQtpye() {
		return qtpye;
	}
	public void setQtpye(String qtpye) {
		this.qtpye = qtpye;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(String knowledges) {
		this.knowledges = knowledges;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getPaperTpye() {
		return paperTpye;
	}
	public void setPaperTpye(String paperTpye) {
		this.paperTpye = paperTpye;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getFromSite() {
		return fromSite;
	}
	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}
	public Integer getIsSub() {
		return isSub;
	}
	public void setIsSub(Integer isSub) {
		this.isSub = isSub;
	}
	public Integer getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(Integer isNormal) {
		this.isNormal = isNormal;
	}
	public Integer getIsKonw() {
		return isKonw;
	}
	public void setIsKonw(Integer isKonw) {
		this.isKonw = isKonw;
	}
	public String getTiid() {
		return tiid;
	}
	public void setTiid(String tiid) {
		this.tiid = tiid;
	}
	public String getSimilarity() {
		return similarity;
	}
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}
	public Integer getIsunique() {
		return isunique;
	}
	public void setIsunique(Integer isunique) {
		this.isunique = isunique;
	}
	public List<SubQuestionVo> getSubQuestion() {
		return subQuestion;
	}
	public void setSubQuestion(List<SubQuestionVo> subQuestion) {
		this.subQuestion = subQuestion;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getPaperFlag() {
		return paperFlag;
	}
	public void setPaperFlag(Integer paperFlag) {
		this.paperFlag = paperFlag;
	}
	
}
