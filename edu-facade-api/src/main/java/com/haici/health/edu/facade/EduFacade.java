package com.haici.health.edu.facade;

import com.haici.health.base.facade.vo.BaseFacadeReqVo;
import com.haici.health.base.facade.vo.BaseFacadeRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 16:21
 * @Description:
 */
@FeignClient("edu-cloud")
public interface EduFacade {

    @RequestMapping(method = RequestMethod.POST, value = "/facade/edu/test")
    public BaseFacadeRespVo<String> test(@RequestBody BaseFacadeReqVo vo);

}
