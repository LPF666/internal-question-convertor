package com.eebbk.autotool.pojo;

/**
 * @项目名称：webdemo
 * @类名称：BaseParam
 * @类描述：
 * @创建人：杨一中
 * @创建时间：2016年4月29日 下午6:13:25
 * @company:步步高教育电子有限公司
 */
public class BaseParam
{
	/**模板项目名,默认为webdemo*/
    private String demoName = "webdemo";
    /**项目名*/
    private String projectName;
    /**开发路径*/
    private String devPath;
    
    public String getProjectName()
    {
        return this.projectName;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    public String getDevPath()
    {
        return this.devPath;
    }
    public void setDevPath(String devPath)
    {
        this.devPath = devPath;
    }
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
}
