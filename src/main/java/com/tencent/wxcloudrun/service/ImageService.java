package com.tencent.wxcloudrun.service;

import org.springframework.web.multipart.MultipartFile;
import com.tencent.wxcloudrun.model.Image;
import java.util.List;

public interface ImageService {
    // 上传图片
    void uploadImage(MultipartFile file);
    
    // 获取解析结果
    List<Image> getParseResults();
} 