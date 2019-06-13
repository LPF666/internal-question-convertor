package com.eebbk.internal.question.convertor.vo;

public class GradeVo {
	/**
	 *   `gradeId` int(11) NOT NULL,
  		  `gradeName` varchar(255) DEFAULT NULL,
	 */
	private Integer gradeId;
	private String gradeName;
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
}
