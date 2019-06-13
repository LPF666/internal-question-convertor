package com.eebbk.internal.question.convertor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.eebbk.edu.common.log.LogUtil;
import com.eebbk.edu.common.log.Logger;
import com.eebbk.edu.common.util.StrUtils;
import com.eebbk.edu.common.util.conf.Application;
import com.eebbk.edu.common.util.json.JsonTool;
import com.eebbk.edu.redis.JedisClusterManager;
import com.eebbk.edu.redis.JedisWrapper;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

/**
 * redis缓存工具类
 * @project syncchinese 
 * @author wuzhixian
 * @date 2018年3月13日 上午8:40:45
 * @company 步步高教育电子有限公司
 */
public class RedisUtil {

    private static final Logger LOG = LogUtil.getLogger(RedisUtil.class);
    
    public static final String TOKEN_MAP_KEY = "pinyingenius.tokenmap";

//    private static JedisWrapper jedis   = null;
    
    private static JedisCluster jedis = null;
    
    // redis配置信息初始化
    static {
        jedis = JedisClusterManager.getInstance("cache");
    }
    
    public static JedisCluster getJedis()
    {
        return jedis;
    }
    
    public static long setValueForMap(String mapKey, String key,  String value)
    {
        long count = jedis.hset(mapKey, key, value);
        return count;
    }
    
    public static String getValueByMap(String mapKey, String key)
    {
        String value = jedis.hget(mapKey, key);
        return value;
    }
    
    public static String getValue(String key)
    {
        String value = jedis.get(key);
        return value;
    }
    
    
}
