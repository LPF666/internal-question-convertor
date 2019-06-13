package com.eebbk.autotool.util.file;

import org.springframework.util.StringUtils;

/**
 * @author yangyz 修改org.eclipse.wst.common.component文件
 */
public class WstModify implements ModifyFileInterface
{
    private String projectName;

    @SuppressWarnings("unused")
    private WstModify()
    {

    }

    public WstModify(String projectName)
    {
        this.projectName = projectName;
    }

    @Override
    public void modifyLine(StringBuilder sb, String line)
    {
        if (StringUtils.isEmpty(line))
        {
            sb.append(line);
            return;
        }
        String[] strs;
        if (line.contains("deploy-name"))
        {
            strs = line.split("\"");
            for (int i = 0; i < strs.length; i++)
            {
                if (i == 1)
                {
                    sb.append(projectName);
                }
                else
                {
                    sb.append(strs[i]);
                }
                if (i != strs.length - 1 && strs.length > 1)
                {
                    sb.append("\"");
                }
            }
        }
        else if (line.contains("java-output-path"))
        {
            strs = line.split("\"");
            for (int i = 0; i < strs.length; i++)
            {
                if (i == 3)
                {
                    sb.append("/" + projectName + "/target/classes");
                }
                else
                {
                    sb.append(strs[i]);
                }
                if (i != strs.length - 1 && strs.length > 1)
                {
                    sb.append("\"");
                }
            }
        }
        else if (line.contains("context-root"))
        {
            strs = line.split("\"");
            for (int i = 0; i < strs.length; i++)
            {
                if (i == 3)
                {
                    sb.append(projectName);
                }
                else
                {
                    sb.append(strs[i]);
                }
                if (i != strs.length - 1 && strs.length > 1)
                {
                    sb.append("\"");
                }
            }
        }
        else
        {
            sb.append(line);
        }
    }

}
