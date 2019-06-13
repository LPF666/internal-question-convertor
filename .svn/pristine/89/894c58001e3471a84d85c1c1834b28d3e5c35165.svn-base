package com.eebbk.spring.mvc.check.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eebbk.edu.common.util.template.ResultVoTemplate;
import com.eebbk.spring.mvc.check.service.CheckDbService;

/**
 * @项目名称：spring-boot
 * @类名称：CheckIconController
 * @类描述：不影响正常业务验证是否服务通畅
 * @创建人：杨一中
 * @创建时间：2017年1月20日 下午5:58:41
 * @company:步步高教育电子有限公司
 */
@Api(value = "服务校验")
@RestController
@RequestMapping("/admin/check")
public class CheckController
{
    @Autowired
    private CheckDbService checkDbService;

    /**
     * 校验db查询否通畅
     * @param machineId 机器码
     * @param validateCode 校验值
     * @return
     * @exception/throws [异常类型] [异常说明](可选)
     * @author 杨一中
     * @date 2017年6月12日 下午2:16:05
     * @since 1.0.0
     */
    @ApiOperation(value = "校验数据库是否正常", notes = "同时作为swagger示例")
    @RequestMapping(value = "/checkDb", method = { RequestMethod.POST, RequestMethod.GET })
    public String checkDb(
            @ApiParam(value = "机器序列号", defaultValue = "M1000000") @RequestParam(value = "machineId", required = true) String machineId,
            @ApiParam(value = "校验码", defaultValue = "M1000000") @RequestHeader(value = "validateCode", required = true) String validateCode)
    {
        checkDbService.checkDb();
        return ResultVoTemplate.outputSuccess();
    }
}
