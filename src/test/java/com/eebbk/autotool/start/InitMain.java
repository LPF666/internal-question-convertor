package com.eebbk.autotool.start;

import com.eebbk.autotool.util.constant.ConstantUtil;
import com.eebbk.autotool.util.constant.PathUtil;

/**
 * @author yangyz
 * 初始化总类
 */
public class InitMain {
    //需要初始化的参数
    //新工程名参数
    public static String PROJECT_NEW = PathUtil.getNowProjectName();
    
	//进行一系列初始化操作
	public static void main(String[] args) {
		//第一个参数为新项目名，第二个参数为demo项目名
		String[] param = new String[]{PROJECT_NEW, ConstantUtil.PROJECT_DEMO};
		//修改项目名
		InitProjectNameMain.init(param);
		//初始化数据库
		InitDbMain.init(param);
	}
	
}
