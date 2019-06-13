package com.eebbk.autotool.util.file;

import org.springframework.util.StringUtils;

/**
 * @author yangyz 简单的文件名替换
 */
public class NameModify implements ModifyFileInterface
{
    private String demoName;
    private String projectName;

    @SuppressWarnings("unused")
    private NameModify()
    {

    }

    public NameModify(String demoName, String projectName)
    {
        this.projectName = projectName;
        this.demoName = demoName;
    }

    @Override
    public void modifyLine(StringBuilder sb, String line)
    {
        if (StringUtils.isEmpty(line))
        {
            sb.append(line);
            return;
        }
        if (line.contains(demoName))
        {
            sb.append(line.replace(demoName, projectName));
        }
        else if (line.contains(demoName.toLowerCase()))
        {
            sb.append(line.replace(demoName.toLowerCase(),
                    projectName.toLowerCase()));
        }
        else
        {
            sb.append(line);
        }
    }

}
