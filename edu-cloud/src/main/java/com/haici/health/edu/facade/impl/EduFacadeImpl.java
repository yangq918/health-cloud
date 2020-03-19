package com.haici.health.edu.facade.impl;

import com.haici.health.base.facade.vo.BaseFacadeReqVo;
import com.haici.health.base.facade.vo.BaseFacadeRespVo;
import com.haici.health.edu.facade.EduFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 16:36
 * @Description:
 */
@RestController
public class EduFacadeImpl implements EduFacade {
    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/facade/edu/test")
    public BaseFacadeRespVo<String> test(@RequestBody BaseFacadeReqVo vo) {
        return BaseFacadeRespVo.succssFacadeResp("test facade is ok");
    }
}
