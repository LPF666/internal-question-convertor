package com.eebbk.autotool.util.constant;

public class PathUtil
{
    /**获取当前项目的开发路径*/
    public static String getDevProjectPath()
    {
        return System.getProperty("user.dir"); 
    }
    
    /**获取当前项目的名字*/
    public static String getNowProjectName()
    {
        String[] paths = getDevProjectPath().split("\\\\");
        return paths[paths.length - 1]; 
    }
}
