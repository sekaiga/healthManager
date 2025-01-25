package com.tencent.wxcloudrun.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Image {
    private Long id;
    private String imagePath;
    private Integer status;
    private String parseResult;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 