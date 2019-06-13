package com.eebbk.internal.question.convertor.pojo;

import java.util.Date;

public class QuestionsPojo {
	/**
	 *   `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` text COMMENT '试题-题干',
  `option_a` text COMMENT '选项A',
  `option_b` text COMMENT '选项B',
  `option_c` text COMMENT '选项C',
  `option_d` text COMMENT '选项D',
  `option_e` text COMMENT '选项E',
  `answer1` text COMMENT 'ѡ',
  `answer2` text COMMENT '非标准格式答案或含部分过程说明的答案',
  `parse` text COMMENT '试题解析',
  `qtpye` varchar(80) DEFAULT NULL COMMENT '试题题型',
  `diff` float(3,2) DEFAULT NULL,
  `md5` varchar(50) DEFAULT NULL COMMENT '试题题干的md5值',
  `subjectId` tinyint(2) DEFAULT NULL COMMENT '学科Id',
  `gradeId` int(5) DEFAULT NULL COMMENT '年级ID',
  `knowledges` varchar(200) DEFAULT NULL COMMENT '知识点名称',
  `area` varchar(50) DEFAULT NULL COMMENT '试题区域',
  `year` int(4) DEFAULT NULL COMMENT '试题年份',
  `paperTpye` varchar(50) DEFAULT NULL COMMENT '试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他',
  `source` varchar(200) DEFAULT NULL COMMENT '试题来源(试卷)',
  `fromSite` varchar(50) DEFAULT NULL COMMENT '试题来源（网站）',
  `isSub` tinyint(1) DEFAULT NULL COMMENT '是否存在图片水印',
  `isNormal` tinyint(1) DEFAULT NULL COMMENT '是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1',
  `isKonw` tinyint(1) DEFAULT NULL COMMENT '是否匹配章节知识点，1匹配，0不匹配',
  `tiid` varchar(50) DEFAULT NULL COMMENT '试题的tiid，结合fromsite进行同网站试题排重，用于增量更新',
  `Similarity` int(5) DEFAULT '0' COMMENT '试题在题库中的相似度，相似度越高，质量越低',
  `isunique` tinyint(2) DEFAULT NULL,
  `md52` varchar(50) DEFAULT NULL,
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
	private Integer subjectId;
	private Integer gradeId;
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
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
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
	
	
}
