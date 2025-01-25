package com.tencent.wxcloudrun.task;

import com.tencent.wxcloudrun.model.Image;
import com.tencent.wxcloudrun.dao.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ImageParseTask {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageParseTask.class);
    
    @Autowired
    private ImageMapper imageMapper;
    
    @Async("imageParseExecutor")
    public void startImageParseTask() {
        logger.info("图片解析任务启动");
        while (true) {
            try {
                Image image = imageMapper.findFirstByStatus(0);
                if (image == null) {
                    Thread.sleep(1000);
                    continue;
                }
                
                logger.info("开始处理图片: {}", image.getImagePath());
                
                image.setStatus(1);
                image.setUpdateTime(LocalDateTime.now());
                imageMapper.updateImage(image);
                logger.info("更新图片状态为处理中: {}", image.getId());
                
                // TODO: 调用OCR服务进行解析
                String parseResult = "OCR解析结果";
                logger.info("OCR解析完成: {}", image.getId());
                
                image.setStatus(2);
                image.setParseResult(parseResult);
                image.setUpdateTime(LocalDateTime.now());
                imageMapper.updateImage(image);
                logger.info("更新解析结果完成: {}", image.getId());
                
            } catch (InterruptedException e) {
                logger.warn("图片解析任务被中断");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.error("处理图片时发生错误: {}", e.getMessage(), e);
            }
        }
    }
} 