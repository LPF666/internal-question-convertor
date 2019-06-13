package com.eebbk.autotool.start;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.eebbk.autotool.dao.InitDbMapper;
import com.eebbk.autotool.pojo.ProInitBaseParam;
import com.eebbk.autotool.util.constant.ConstantUtil;
import com.eebbk.autotool.util.constant.PathUtil;
import com.eebbk.autotool.util.file.FileModifyUtil;
import com.eebbk.autotool.util.str.AutoStrUtil;
import com.eebbk.edu.common.log.LogUtil;
import com.eebbk.edu.common.log.Logger;
import com.eebbk.edu.common.util.PropFilesUtils;
import com.eebbk.edu.common.util.PropFilesUtils.PathTypeEnum;
import com.eebbk.edu.common.util.StrUtils;

/**
 * @项目名称：spring-boot
 * @类名称：InitDbMain
 * @类描述：初始化db库表 自动化创建基础表
 * @创建人：杨一中
 * @创建时间：2016年4月29日 下午3:57:34
 * @company:步步高教育电子有限公司
 */
public class InitDbMain
{
    private static final Logger LOGGER = LogUtil.getLogger(InitDbMain.class);

    /** 参入的参数 */
    private static ProInitBaseParam inputBaseParam;

    private static final String DEV_PROP = "application-dev.properties";
    private static final String REAL_PROP = "application-real.properties";
    private static final String TEST_PROP = "application-test.properties";

    /** 起始方法 */
    public static void main(String[] args)
    {
        String[] param = new String[] { ConstantUtil.DEFAULT_DB, ConstantUtil.PROJECT_DEMO };
        init(param);
    }

    public static void init(String[] args)
    {
        initParam(args);
        if (!checkParam())
        {
            return;
        }
        initDo(args);
    }

    /**
     * 初始化测试环境 开发环境 生产环境的“t_check_db”表
     * 
     * @param args
     * @exception/throws [异常类型] [异常说明](可选)
     * @author panjianlin
     * @date 2017年5月23日 下午2:34:09
     * @since [起始版本]
     */
    private static void initDo(String[] args)
    {
        initCheckDB(DEV_PROP);
        initCheckDB(TEST_PROP);
        initCheckDB(REAL_PROP);
    }

    private static void initCheckDB(String jdbcClassPath)
    {
        DruidDataSource dataSource = initDataSourceByJDBCProperties(jdbcClassPath);
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new ClassPathResource("autotool/spring/mybatis-config.xml"));
        sessionFactoryBean.setMapperLocations(new Resource[] { new ClassPathResource(
                "com/eebbk/autotool/dao/mapper/InitDbMapper.xml") });
        SqlSessionFactory sqlSessionFactory;
        try
        {
            sqlSessionFactory = sessionFactoryBean.getObject();
        }
        catch (Exception e)
        {
            LOGGER.error("获取sqlSessionFactory异常", e);
            throw new RuntimeException(e);
        }

        // ApplicationContext bf = new
        // FileSystemXmlApplicationContext("classpath*:/autotool/spring/spring-bean.xml");
        // // 1.获取服务bean实例
        // // InitDbMapper DbUtil = (InitDbMapper) bf.getBean("InitDbMapper");
        // SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)
        // bf.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        InitDbMapper DbUtil = sqlSession.getMapper(InitDbMapper.class);

        // if (DbUtil.isExistTable("t_check_db",
        // inputBaseParam.getProjectName()) == 0)
        // {
        // List<String> sqls = readSql();
        // for (String sql : sqls)
        // {
        // TestLOGGER.info("initDb.sql:" + sql);
        // DbUtil.insert(sql);
        // }
        // }
        // else
        // {
        // TestLOGGER.info("initDb.表数据已经存在，跳过db初始化.");
        // }

        List<String> sqls = readSql();
        for (String sql : sqls)
        {
            LOGGER.info("initDb.sql:" + sql);
            DbUtil.insert(sql);
        }

        dataSource.close();
    }

    private static DruidDataSource initDataSourceByJDBCProperties(String jdbcClassPath)
    {
        if (StrUtils.isBlank(jdbcClassPath))
        {
            LOGGER.error("jdbc文件路径不能为空");
            throw new IllegalArgumentException("jdbc文件路径不能为空");
        }

        PropFilesUtils propFilesUtils = new PropFilesUtils(new String[] { jdbcClassPath }, PathTypeEnum.SOURCE);
        String url = propFilesUtils.getValue("spring.datasource.url");
        String username = propFilesUtils.getValue("spring.datasource.username");
        String password = propFilesUtils.getValue("spring.datasource.password");

        if (StrUtils.isBlank(url))
        {
            LOGGER.error("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.url”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
            throw new IllegalArgumentException("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.url”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
        }

        if (StrUtils.isBlank(username))
        {
            LOGGER.error("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.username”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
            throw new IllegalArgumentException("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.username”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
        }

        if (StrUtils.isBlank(password))
        {
            LOGGER.error("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.password”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
            throw new IllegalArgumentException("为确保数据库环境的正确性，请在属性文件：" + jdbcClassPath
                    + "添加上“spring.datasource.password”属性,如果目前没有相应环境的数据库，请在之后在数据库执行路径为“sql/base.sql”下的创建“t_check_db”表语句");
        }

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    private static List<String> readSql()
    {
        List<String> sqls = new ArrayList<String>();
        File sqlDir = new File(PathUtil.getDevProjectPath() + File.separator + "sql");
        if (sqlDir.exists())
        {
            for (File file : sqlDir.listFiles())
            {
                sqls.addAll(FileModifyUtil.readSqlFile(file));
            }
        }
        return sqls;
    }

    // 初始化参数
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

    // 校验参数
    private static boolean checkParam()
    {
        boolean isOk = true;
        LOGGER.info("demo项目名为:" + inputBaseParam.getDemoName());
        if (StringUtils.isEmpty(inputBaseParam.getProjectName()))
        {
            isOk = false;
        }
        LOGGER.info("新项目名为:" + inputBaseParam.getProjectName());
        if (StringUtils.isEmpty(inputBaseParam.getDevPath()))
        {
            isOk = false;
            LOGGER.info("参数不完整.项目路径为空.");
        }
        LOGGER.info("根目录:" + inputBaseParam.getDevPath());

        // if (!checkJDBCProperties())
        // {
        // isOk = false;
        // }

        return isOk;
    }

    /**
     * @description 检查jdbc的属性是否设置【jdbc.url、jdbc.userName、jdbc.password】
     * @author panjianlin
     * @date 2017年5月13日 下午5:04:23
     * @return
     */
    // private static boolean checkJDBCProperties()
    // {
    // boolean isOk = true;
    // PropFilesUtils propFilesUtils = new PropFilesUtils(new String[] {
    // "autotool" + File.separator + "spring"
    // + File.separator + "jdbc.properties" }, PathTypeEnum.SOURCE);
    // String url = propFilesUtils.getValue("jdbc.url");
    // if (StrUtils.isBlank(url))
    // {
    // TestLOGGER.warn("数据库“jdbc.url”属性未设置，请在路径文件：src/tet/resources/autotool/spring/jdbc.properties下设置");
    // isOk = false;
    // }
    // String userName = propFilesUtils.getValue("jdbc.userName");
    // if (StrUtils.isBlank(userName))
    // {
    // TestLOGGER.warn("数据库“jdbc.userName”属性未设置，请在路径文件：src/tet/resources/autotool/spring/jdbc.properties下设置");
    // isOk = false;
    // }
    // String password = propFilesUtils.getValue("jdbc.password");
    // if (StrUtils.isBlank(password))
    // {
    // TestLOGGER.warn("数据库“jdbc.password”属性未设置，请在路径文件：src/tet/resources/autotool/spring/jdbc.properties下设置");
    // isOk = false;
    // }
    //
    // return isOk;
    // }
}
