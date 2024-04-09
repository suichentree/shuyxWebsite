package com.shuyx.shuyxxxljob.controller;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/4 17:32
 */
@RestController
@Slf4j
public class XxlJobController {

    @XxlJob("testTask1")
    public void testTask1(){
        String param = XxlJobHelper.getJobParam();
        //XxlJobHelper.log会把信息呈现在调度中心中。
        XxlJobHelper.log("测试开始");
        //打印任务参数
        log.info("任务开始执行");
        log.info("任务参数为{}",param);
        log.info("任务执行完成");
        XxlJobHelper.log("测试结束");
    }
}
