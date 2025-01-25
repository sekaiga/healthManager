package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.Image;
import com.tencent.wxcloudrun.dao.ImageMapper;
import com.tencent.wxcloudrun.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ImageMapper imageMapper;

    @PostConstruct
    public void init() {
        logger.info("初始化上传目录: {}", uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            logger.info("创建上传目录{}: {}", uploadPath, created ? "成功" : "失败");
        }
    }

    @Override
    public void uploadImage(MultipartFile file) {
        try {
            logger.info("开始处理文件上传: {}", file.getOriginalFilename());
            
            if (file.getSize() > 10 * 1024 * 1024) {
                logger.warn("文件大小超过限制: {}bytes", file.getSize());
                throw new RuntimeException("文件大小不能超过10MB");
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            
            logger.info("生成新文件名: {}", fileName);
            
            File destFile = new File(uploadPath + File.separator + fileName);
            file.transferTo(destFile);
            logger.info("文件保存成功: {}", destFile.getAbsolutePath());
            
            Image image = new Image();
            image.setImagePath(fileName);
            image.setStatus(0);
            image.setCreateTime(LocalDateTime.now());
            image.setUpdateTime(LocalDateTime.now());
            
            imageMapper.insertImage(image);
            logger.info("数据库记录创建成功，ID: {}", image.getId());
            
        } catch (Exception e) {
            logger.error("上传图片失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传图片失败: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Image> getParseResults() {
        logger.info("获取所有解析结果");
        List<Image> results = imageMapper.getAllImages();
        logger.info("获取到 {} 条结果", results.size());
        return results;
    }
    
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
} 