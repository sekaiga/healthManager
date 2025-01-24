package com.tencent.wxcloudrun.repository;

import com.tencent.wxcloudrun.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 查找第一个未解析的图片
    Image findFirstByStatus(Integer status);
} 