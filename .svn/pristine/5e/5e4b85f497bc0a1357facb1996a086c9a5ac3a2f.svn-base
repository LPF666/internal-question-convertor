package com.eebbk.internal.question.convertor.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @项目名称：h600s
 * @类名称：LogUtil
 * @类描述：日志管理文件，用于对日志进行分类管理，对日志性能进行优化；
 * @创建人：杨一中
 * @创建时间：2014-6-11 上午8:46:48
 * @company:步步高教育电子有限公司
 */
public class LogUtil {
	// 不同类型日志输出到不同的文件
	private static Logger INFOLOG = Logger.getLogger("INFOLOG");
	private static Logger WARNLOG = Logger.getLogger("WARNLOG");
	private static Logger ERRORLOG = Logger.getLogger("ERRORLOG");
	//记录登陆登出的日志，后续可以通过日志采集器进行日志入库
	private static Logger LOGINLOG = Logger.getLogger("LOGINLOG");
	// 设置这个变量是为了在配置文件中配置了%C %F %L时能打印出被调用类的类名及行号
	private static String FQCN = LogUtil.class.getName();

	//打印登陆登出及操作日志信息到单独的文件中,后续再考虑怎么做分布式日志收集。
	//方案1.生产者消费者模式异步写入数据库（log4j原生支持）
	//方案2.写入redis中，然后通过单独的日志系统进行入库管理
	public static void opInfo(String userName, String operate)
	{
	    opInfo(userName,operate,null);
	}
	
	public static void opInfo(String userName, String operate, String remark)
    {
	    LOGINLOG.log(FQCN, Level.INFO, String.format("user.%s:operate.%s:remark.%s", userName, operate, remark), null);
    }
	
	public static void opError(String userName, String operate, String remark, Throwable t)
	{
	    LOGINLOG.log(FQCN, Level.ERROR, String.format("user.%s:operate.%s:remark.%s", userName, operate, remark), t);
	}

	// 类似下面的判断可以在某级别的日志被关闭时，避免隐性的性能损耗（即使不需要输出日志，大量debug等语句的参数处理也需要消耗可观的计算量）
	public static void debug(String s) {
		INFOLOG.log(FQCN, Level.DEBUG, s, null);
	}

	public static void debug(String s, Throwable t) {
		INFOLOG.log(FQCN, Level.DEBUG, s, t);
	}

	public static void info(String s) {
		INFOLOG.log(FQCN, Level.INFO, s, null);
	}

	public static void info(String s, Throwable t) {
		INFOLOG.log(FQCN, Level.INFO, s, t);
	}

	public static void warn(String s) {
		WARNLOG.log(FQCN, Level.WARN, s, null);
	}

	public static void warn(String s, Throwable t) {
		WARNLOG.log(FQCN, Level.WARN, s, t);
	}

	public static void error(String s) {
		ERRORLOG.log(FQCN, Level.ERROR, s, null);
	}

	public static void error(String s, Throwable t) {
		ERRORLOG.log(FQCN, Level.ERROR, s, t);
	}

}
