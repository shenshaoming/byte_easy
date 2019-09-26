package com.mbyte.easy.scheduling;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * 测试springboot调度
 * @author  申劭明
 */
//@Configuration
//@EnableScheduling
public class TestScheduling {
    /**
     * 添加定时任务，每隔5秒钟调用一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public void testJobs(){
        System.out.println(System.currentTimeMillis());
    }


}
