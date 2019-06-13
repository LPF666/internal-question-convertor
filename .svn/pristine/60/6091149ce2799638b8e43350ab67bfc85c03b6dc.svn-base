package com.eebbk.spring.mvc.druid.config;

import com.alibaba.druid.support.http.StatViewServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @项目名称：spring-mvc
 * @类名称：DruidStatViewServlet
 * @类描述：配置Druid
 *       启动项目后访问 http://yourdomain/admin/druid/login.html 即可查看数据源及SQL统计
 *       eg.http://127.0.0.1:6001/admin/druid/login.html
 * @创建人：杨一中
 * @创建时间：2017年2月13日 下午6:10:06
 * @company:步步高教育电子有限公司
 */
@WebServlet(urlPatterns="/admin/druid/*",
           initParams={
                    // IP白名单 (没有配置或者为空，则允许所有访问)
                    //@WebInitParam(name="allow",value="127.0.0.1"),
                    //@WebInitParam(name="deny",value="192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
                    @WebInitParam(name="loginUsername",value="druid"),// 用户名
                    @WebInitParam(name="loginPassword",value="druid"),// 密码
                    @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
           }
)
public class DruidStatViewServlet extends StatViewServlet
{

    /**
     */
    private static final long serialVersionUID = -8320977347304155308L;

}   
