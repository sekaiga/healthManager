package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.task.ImageParseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImageParseRunner implements ApplicationRunner {

    @Autowired
    private ImageParseTask imageParseTask;

    @Override
    public void run(ApplicationArguments args) {
        imageParseTask.startImageParseTask();
    }
} 