package com.haici.health.educomsumer.controller;

import com.haici.health.base.facade.vo.BaseFacadeReqVo;
import com.haici.health.component.utils.vo.BaseResult;
import com.haici.health.edu.facade.EduFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/15 19:47
 * @Description:
 */
@RestController
public class EduConsumerController {

    @Autowired
    private EduFacade eduFacade;

    @GetMapping("/api/educonsumer/test")
    public BaseResult<String> test() {
        return eduFacade.test(BaseFacadeReqVo.newInstance(null));
    }


}
