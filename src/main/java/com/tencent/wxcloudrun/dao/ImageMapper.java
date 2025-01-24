package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Image;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ImageMapper {
    void insertImage(Image image);
    List<Image> getAllImages();
    Image findFirstByStatus(Integer status);
    void updateImage(Image image);
} 