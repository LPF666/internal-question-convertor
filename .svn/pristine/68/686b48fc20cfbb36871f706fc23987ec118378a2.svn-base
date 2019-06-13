package com.eebbk.spring.mvc.druid.config;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @项目名称：spring-mvc
 * @类名称：DruidStatFilter
 * @类描述：配置Druid
 *       启动项目后访问 http://yourdomain/admin/druid/login.html 即可查看数据源及SQL统计
 *       eg.http://127.0.0.1:6001/admin/druid/login.html
 * @创建人：杨一中
 * @创建时间：2017年2月13日 下午6:09:38
 * @company:步步高教育电子有限公司
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
    initParams={
             // 忽略资源
             @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/admin/druid/*"),
             //配置profileEnable能够监控单个url调用的sql列表
             @WebInitParam(name="profileEnable",value="true"),
             //关闭session监控
             @WebInitParam(name="profileEnable",value="true"),
     }
)
public class DruidStatFilter extends WebStatFilter
{

}
