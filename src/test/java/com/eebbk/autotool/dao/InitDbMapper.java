package com.eebbk.autotool.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("InitDbMapper")
public interface InitDbMapper {
	//执行sql
	void insert(String sql);
	//判断某个表是否存在
	int isExistTable(@Param("tableName") String tableName,@Param("projectName") String projectName);
}
