package com.eebbk.autotool.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.eebbk.edu.common.log.LogUtil;
import com.eebbk.edu.common.log.Logger;
import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.edu.common.util.exception.BBKException;
import com.eebbk.edu.common.util.extend.validate.RegularUtil;

public class PropertyUtil
{
    private static final Logger LOGGER = LogUtil.getLogger(PropertyUtil.class);

    private static final String WEB_ROOT = "webapp.root";
    private static final String WEB_ROOT_SUBFIX = ".root";
    private static final String WEB_ROOT_KEY = "web.app.root.key";

    public static void updateProperties(String devPath, String projectName)
    {
        try
        {
            updateLog4jProperties(devPath, projectName);
        }
        catch (Exception e)
        {
            // log4j属性依赖于web.xml的webRootKey,所以发生异常后，不能让它往下执行
            throw new BBKException("更新log4j属性失败");
        }

        updateApplicationProperties(devPath, projectName);
    }

    /**
     * 更新log4j-*.properties文件
     * @param devPath
     * @param projectName
     * @exception/throws [异常类型] [异常说明](可选)
     * @author panjianlin
     * @throws IOException 
     * @date 2017年10月17日 上午8:50:06
     * @since 1.0.0
     */
    private static void updateLog4jProperties(String devPath, String projectName) throws IOException
    {
        String folderPath = devPath + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator;

        List<String> log4jFilePaths = getLog4jFilePath(folderPath);

        for (String log4jFilePath : log4jFilePaths)
        {
            updateLog4jFile(log4jFilePath, projectName, devPath);
        }

    }

    private static void updateLog4jFile(String log4jFilePath, String projectName, String devPath) throws IOException
    {

        String oldWebAppRootKey = XmlUtil.getWebAppRootValue(devPath);

        if (StrUtils.isBlank(oldWebAppRootKey))
        {
            oldWebAppRootKey = WEB_ROOT;
        }
        List<String> lines = Files.readAllLines(Paths.get(log4jFilePath), Charset.defaultCharset()); // 读取文本文件

        for (int i = 0; i < lines.size(); i++)
        {

            String line = lines.get(i);

            if (line.contains(oldWebAppRootKey))
            {
                lines.remove(i);
                lines.add(i, line.replaceAll(oldWebAppRootKey, projectName + WEB_ROOT_SUBFIX));
            }
        }

        Files.write(Paths.get(log4jFilePath), lines, Charset.defaultCharset());

    }

    private static List<String> getLog4jFilePath(String folderPath)
    {
        List<String> log4jFilePaths = new ArrayList<String>();

        File folder = new File(folderPath);

        File[] files = folder.listFiles();

        for (File file : files)
        {
            String fileName = file.getName();

            // 如果是log4j文件标准格式，则进行记录
            if (RegularUtil.validatePattern("log4j-.*\\.properties", fileName))
            {
                log4jFilePaths.add(file.getAbsolutePath());
            }
        }
        return log4jFilePaths;
    }

    /**
     * 更新application.properties文件
     * @param devPath
     * @param projectName
     * @exception/throws [异常类型] [异常说明](可选)
     * @author panjianlin
     * @date 2017年10月11日 上午11:13:20
     * @since 1.0.0
     */
    private static void updateApplicationProperties(String devPath, String projectName)
    {
        // 更新application.properties文件
        String applicationPropertiesFile = devPath + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "application.properties";

        try
        {
            List<String> lines = Files.readAllLines(Paths.get(applicationPropertiesFile), Charset.defaultCharset()); // 读取文本文件

            boolean hasWebRootKey = false;
            for (int i = 0; i < lines.size(); i++)
            {

                String line = lines.get(i);

                if (line.replaceAll("\\s+", "").contains(WEB_ROOT_KEY + "="))
                {
                    lines.remove(i);
                    lines.add(i, WEB_ROOT_KEY + " = " + projectName + WEB_ROOT_SUBFIX);

                    hasWebRootKey = true;
                }
            }

            if (!hasWebRootKey)
            {
                lines.add(WEB_ROOT_KEY + " = " + projectName + WEB_ROOT_SUBFIX);
            }

            Files.write(Paths.get(applicationPropertiesFile), lines, Charset.defaultCharset());
        }
        catch (IOException e)
        {
            LOGGER.error(e, "修改属性配置文件失败");
        }

    }
}
