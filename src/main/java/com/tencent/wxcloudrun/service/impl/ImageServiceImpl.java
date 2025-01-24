package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.Image;
import com.tencent.wxcloudrun.dao.ImageMapper;
import com.tencent.wxcloudrun.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public void uploadImage(MultipartFile file) {
        try {
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            
            // 确保上传目录存在
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // 保存文件
            File destFile = new File(uploadPath + File.separator + fileName);
            file.transferTo(destFile);
            
            // 保存记录到数据库
            Image image = new Image();
            image.setImagePath(fileName);
            image.setStatus(0); // 未解析状态
            image.setCreateTime(LocalDateTime.now());
            image.setUpdateTime(LocalDateTime.now());
            
            imageMapper.insertImage(image);
            
        } catch (Exception e) {
            throw new RuntimeException("上传图片失败", e);
        }
    }

    @Override
    public List<Image> getParseResults() {
        return imageMapper.getAllImages();
    }
    
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
} 