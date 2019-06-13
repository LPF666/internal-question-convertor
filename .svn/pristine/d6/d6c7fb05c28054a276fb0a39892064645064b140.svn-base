package com.eebbk.autotool.util.log;


import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * 
 * @项目名称：webdemo
 * @类名称：LogUtil
 * @类描述：日志管理文件，用于对日志进行分类管理，对日志性能进行优化；
 * @创建人：杨一中
 * @创建时间：2014-6-11 上午8:46:48
 * @company:步步高教育电子有限公司
 */
public class TestLogUtil {
	// 不同类型日志输出到不同的文件
	private static Logger INFOLOG = Logger.getLogger("INFOLOG");
	private static Logger WARNLOG = Logger.getLogger("WARNLOG");
	private static Logger ERRORLOG = Logger.getLogger("ERRORLOG");
	// 设置这个变量是为了在配置文件中配置了%C %F %L时能打印出被调用类的类名及行号
	private static String FQCN = TestLogUtil.class.getName();
	
	static{
	    init();
	}
	
	private TestLogUtil(){
	}

	// 初始化，如果是本地调试则保留console输出，同时设置根节点日志级别为DEBUG，子节点未设置则会继承根节点的设置
	// 该方法在ReadResourceMapping类读取配置文件的静态块中执行
	public static void init() {
	    Layout layout = new PatternLayout("%-5p %d [%t] %l %m%n");
        Appender appender = new ConsoleAppender(layout, "System.out");
        Logger.getRootLogger().setLevel(Level.INFO);
        Logger.getRootLogger().addAppender(appender);
        INFOLOG.addAppender(appender);
        WARNLOG.addAppender(appender);
        ERRORLOG.addAppender(appender);
	}
	
	// 类似下面的判断可以在某级别的日志被关闭时，避免隐性的性能损耗（即使不需要输出日志，大量debug等语句的参数处理也需要消耗可观的计算量）
	public static void debug(String s) {
		// 写在这里是没用的，用此判断是为了减少 拼接字符串s，这个判断要放到外面
		// if (LOG.isDebugEnabled())
		// {
		INFOLOG.log(FQCN, Level.DEBUG, s, null);
		// }
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
