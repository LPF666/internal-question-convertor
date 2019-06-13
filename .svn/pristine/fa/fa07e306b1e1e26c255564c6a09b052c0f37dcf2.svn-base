package com.eebbk.autotool.util.jdbc;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class JdbcUtil {
	public static SqlSessionFactory getSqlSession(){
		String resource = "spring-jdbc.xml"; 
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
}
