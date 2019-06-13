package com.eebbk.spring.mvc.check.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eebbk.edu.common.util.exception.BizException;
import com.eebbk.edu.common.util.network.enums.BaseFailedStatusEnum;
import com.eebbk.spring.mvc.check.dao.CheckDbMapper;
import com.eebbk.spring.mvc.check.pojo.CheckDb;
import com.eebbk.spring.mvc.check.service.CheckDbService;

/**
 * @项目名称：spring-boot
 * @类名称：CheckDbServiceImpl
 * @类描述：db校验实现类
 * @创建人：杨一中
 * @创建时间：2017年2月3日 下午2:58:50
 * @company:步步高教育电子有限公司
 */
@Service
public class CheckDbServiceImpl implements CheckDbService
{
    @Autowired
    private CheckDbMapper checkDbMapper;

    /**
     * 1.新增一条数据 2.查出新增的数据 3.删除新增的数据
     */
    @Override
    public void checkDb()
    {
        CheckDb checkDb = new CheckDb();
        checkDbMapper.insert(checkDb);
        CheckDb checkDbInDb = checkDbMapper.select(checkDb);
        if (null == checkDbInDb)
        {
            throw new BizException(BaseFailedStatusEnum.DB_ERROR);
        }
        int count = checkDbMapper.delete(checkDb);
        if (1 != count)
        {
            throw new BizException(BaseFailedStatusEnum.DB_ERROR);
        }
    }

}
