/**
 * 
 */
package com.eebbk.internal.question.convertor.constant;


public interface RedisKey
{
    /**
     * jedis实例键值social
     */
    String JEDIS_INSTANCE_KEY_SOCIAL = "cache";
    
    /**
     * 项目名
     */
    String JEDIS_PROJECT_NAME = "internal-question-convertor";
    
    /**
     * 获取所有的年级和科目
     */
    String JEDIS_GRADE_SUBJECT_KEY =JEDIS_PROJECT_NAME+ "all:grade-subject";
    /**
     * 获取题库总数量
     */
    String JEDIS_TOTAL_KEY =JEDIS_PROJECT_NAME+  "all:total";
    
    /**
     * 获取图片题情况
     */
    String JEDIS_PIC_INTO_KEY =JEDIS_PROJECT_NAME+  "all:pic:situation";
    
    /**
     * 题库内容统计
     */
    String JEDIS_CONTENT_INTO_KEY =JEDIS_PROJECT_NAME+  "all:content:situation";
    
    /**
     * 题库答案统计
     */
    String JEDIS_ANSWER_INTO_KEY =JEDIS_PROJECT_NAME+  "all:answer:situation";
    
    /**
     * 题目内容解析
     */
    String JEDIS_PARSE_INTO_KEY =JEDIS_PROJECT_NAME+  "all:parse:situation";
    
    /**
     * 年级科目统计信息
     */
    String JEDIS_GRADE_SUBJECT_INTO_KEY =JEDIS_PROJECT_NAME+  "all:grade:subject:situation";

}
