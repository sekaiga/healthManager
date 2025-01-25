CREATE TABLE IF NOT EXISTS `images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) NOT NULL COMMENT '图片路径',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '解析状态：0-未解析，1-解析中，2-解析完成',
  `parse_result` text COMMENT 'OCR解析结果',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 