package com.eebbk.autotool.start;

import java.io.File;

import org.springframework.util.StringUtils;

import com.eebbk.autotool.pojo.ProInitBaseParam;
import com.eebbk.autotool.util.constant.ConstantUtil;
import com.eebbk.autotool.util.constant.PathUtil;
import com.eebbk.autotool.util.file.FileModifyUtil;
import com.eebbk.autotool.util.file.NameModify;
import com.eebbk.autotool.util.file.XmlUtil;
import com.eebbk.autotool.util.log.TestLogUtil;
import com.eebbk.autotool.util.str.AutoStrUtil;

/**
 * @项目名称：webdemo
 * @类名称：InitProjectNameMain
 * @类描述：初始化项目起始类
 * @创建人：杨一中
 * @创建时间：2016年4月29日 下午3:56:41
 * @company:步步高教育电子有限公司
 */
public class InitProjectNameMain
{
    /**参入的参数*/
    private static ProInitBaseParam inputBaseParam;
    
    /**起始方法*/
    public static void main(String[] args)
    {
    	String[] param = new String[]{InitMain.PROJECT_NEW, ConstantUtil.PROJECT_DEMO};
    	init(param);
    }
    
    //参数1.新项目名 参数2.demo项目名
    public static void init(String[] args)
    {
    	initParam(args);
        if (!checkParam())
        {
            return;
        }
        //开发环境配置修改
        changeEclipseSetting();
        //项目基本信息配置修改
        changeProjectSetting();
        //java代码层修改
        changeJavaSetting();
        //根据新工程规范创建文件夹
        buildFilePath();
    }
    
    private static void buildFilePath() {
        //获取工程目录
        String[] paths = inputBaseParam.getProjectName().split("-");
        StringBuilder sb = new StringBuilder();
        for (String path : paths)
        {
            sb.append(File.separator);
            sb.append(path);
        }
        String proPackagePath = ConstantUtil.BASE_PACKAGE_PATH + sb.toString();
        File pathFile = new File(ConstantUtil.BASE_PACKAGE_PATH + sb.toString());
        if (!pathFile.exists())
        {
            pathFile.mkdirs();
        }
        
        for (String path : ConstantUtil.BASE_STRUCTURE)
        {
            //如果文件不存在则创建
            pathFile = new File(proPackagePath + File.separator + path);
            if (!pathFile.exists())
            {
                pathFile.mkdirs();
            }
        }
	}
    
    //java代码层修改
    private static void changeJavaSetting()
    {
    }
    
    //开发环境配置修改
    private static void changeEclipseSetting()
    {
        //修改org.eclipse.wst.common.component
        NameModify wstModify = new NameModify(inputBaseParam.getDemoName(), inputBaseParam.getProjectName());
        FileModifyUtil.readReplaceFile(new File(inputBaseParam.getDevPath() + File.separator + ".settings"  + File.separator + "org.eclipse.wst.common.component"), wstModify);
    }

    //项目基本信息配置修改
    private static void changeProjectSetting()
    {
    	//1.pom文件
    	//FileModifyUtil.readReplaceFile(new File(inputBaseParam.getDevPath() + File.separator + "pom.xml"), wstModify);
    	XmlUtil.readReplaceFile(inputBaseParam.getDevPath(), inputBaseParam.getProjectName());
    	NameModify dbModify = new NameModify(ConstantUtil.DEFAULT_DB, inputBaseParam.getProjectName());
    	//2.基础配置文件db配置修改
    	FileModifyUtil.readReplaceFile(new File(inputBaseParam.getDevPath() 
    			+ File.separator + "src" 
    	        + File.separator + "main"
    	        + File.separator + "resources"
    	        + File.separator + "application-dev.properties"
    	        ), dbModify);
    	//5.test  spring db配置修改
    	FileModifyUtil.readReplaceFile(new File(inputBaseParam.getDevPath() 
    			+ File.separator + "src" 
    	        + File.separator + "test"
    	        + File.separator + "resources"
    	        + File.separator + "autotool"
    	        + File.separator + "spring"
    	        + File.separator + "jdbc.properties"
    	        ), dbModify);
    	//6.编译后的 test spring db配置修改
        FileModifyUtil.readReplaceFile(new File(inputBaseParam.getDevPath() 
                + File.separator + "target" 
                + File.separator + "test-classes"
                + File.separator + "autotool"
                + File.separator + "spring"
                + File.separator + "jdbc.properties"
                ), dbModify);
    }
    
    //初始化参数
    private static void initParam(String[] args)
    {
        inputBaseParam = new ProInitBaseParam();
        inputBaseParam.setProjectName(AutoStrUtil.getParam(0, args));
        if (args.length > 1)
        {
        	inputBaseParam.setDemoName(AutoStrUtil.getParam(1, args));
        }
        inputBaseParam.setDevPath(PathUtil.getDevProjectPath());
    }

    //校验参数
    private static boolean checkParam()
    {
        boolean isOk = true;
        TestLogUtil.info("demo项目名为:" + inputBaseParam.getDemoName());
        if (StringUtils.isEmpty(inputBaseParam.getProjectName()))
        {
            isOk =  false;
        }
        TestLogUtil.info("新项目名为:" + inputBaseParam.getProjectName());
        if (StringUtils.isEmpty(inputBaseParam.getDevPath()))
        {
            isOk =  false;
            TestLogUtil.info("参数不完整.项目路径为空.");
        }
        TestLogUtil.info("根目录:" + inputBaseParam.getDevPath());
        return isOk;
    }
}
