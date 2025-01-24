package com.tencent.wxcloudrun.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 图片存储路径
    private String imagePath;
    
    // 解析状态：0-未解析，1-解析中，2-解析完成
    private Integer status;
    
    // OCR解析结果
    @Column(columnDefinition = "TEXT")
    private String parseResult;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;
} 