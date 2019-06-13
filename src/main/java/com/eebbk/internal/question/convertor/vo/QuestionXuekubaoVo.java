package com.eebbk.internal.question.convertor.vo;

import java.util.Date;

public class QuestionXuekubaoVo {
	/*
	 *   `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` text COLLATE utf8_unicode_ci NOT NULL COMMENT '题目的题干',
  `answer` text COLLATE utf8_unicode_ci COMMENT '题目的答案',
  `solution` text COLLATE utf8_unicode_ci COMMENT '题目的解析',
  `course` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课程分类',
  `grade` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '年级',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`) USING BTREE,
	 */
	
	private Integer id;
	private Integer idOld;
	private String content;
	private String answer;
	private String solution;
	private String course;
	private String grade;
	private Integer picExistflag;
	private Integer contentExistFlag;
	private Integer answerExistFlag;
	private Integer parseExistFlag;
	private Integer qiniuFlag;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	
	public Integer getIdOld() {
		return idOld;
	}

	public void setIdOld(Integer idOld) {
		this.idOld = idOld;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPicExistflag() {
		return picExistflag;
	}

	public void setPicExistflag(Integer picExistflag) {
		this.picExistflag = picExistflag;
	}

	public Integer getContentExistFlag() {
		return contentExistFlag;
	}

	public void setContentExistFlag(Integer contentExistFlag) {
		this.contentExistFlag = contentExistFlag;
	}

	public Integer getAnswerExistFlag() {
		return answerExistFlag;
	}

	public void setAnswerExistFlag(Integer answerExistFlag) {
		this.answerExistFlag = answerExistFlag;
	}

	public Integer getParseExistFlag() {
		return parseExistFlag;
	}

	public void setParseExistFlag(Integer parseExistFlag) {
		this.parseExistFlag = parseExistFlag;
	}

	public Integer getQiniuFlag() {
		return qiniuFlag;
	}

	public void setQiniuFlag(Integer qiniuFlag) {
		this.qiniuFlag = qiniuFlag;
	}
	
	
}
