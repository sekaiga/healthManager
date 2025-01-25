package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Image;
import com.tencent.wxcloudrun.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ApiResponse uploadImage(@RequestParam("file") MultipartFile file) {
        logger.info("收到文件上传请求，文件名: {}, 大小: {}bytes", file.getOriginalFilename(), file.getSize());
        try {
            if (file.isEmpty()) {
                logger.warn("上传的文件为空");
                return ApiResponse.error("请选择文件");
            }
            imageService.uploadImage(file);
            logger.info("文件上传成功: {}", file.getOriginalFilename());
            return ApiResponse.ok();
        } catch (Exception e) {
            logger.error("上传文件失败: {}", e.getMessage(), e);
            return ApiResponse.error("上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/results")
    public ApiResponse getResults() {
        logger.info("收到获取结果请求");
        try {
            List<Image> results = imageService.getParseResults();
            logger.info("成功获取结果，数量: {}", results.size());
            return ApiResponse.ok(results);
        } catch (Exception e) {
            logger.error("获取结果失败: {}", e.getMessage(), e);
            return ApiResponse.error(e.getMessage());
        }
    }
} 