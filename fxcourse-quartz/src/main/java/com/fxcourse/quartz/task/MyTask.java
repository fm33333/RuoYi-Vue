package com.fxcourse.quartz.task;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component("myTask")
public class MyTask {

    public void showTime() {
        System.out.println("定时任务执行：" + new Date());
    }

}
