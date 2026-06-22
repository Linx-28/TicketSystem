-- ============================================
-- 票务系统数据库初始化脚本（2.0版本）
-- 使用 Navicat 执行此脚本创建数据库和表
-- ============================================

CREATE DATABASE IF NOT EXISTS `ticketsystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `ticketsystem`;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` INT PRIMARY KEY AUTO_INCREMENT,
                                      `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) DEFAULT NULL,
    `phone` VARCHAR(20) DEFAULT NULL,
    `role` VARCHAR(10) NOT NULL DEFAULT 'user' COMMENT 'user: 普通用户, admin: 管理员',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 票品表
CREATE TABLE IF NOT EXISTS `ticket` (
                                        `id` INT PRIMARY KEY AUTO_INCREMENT,
                                        `name` VARCHAR(200) NOT NULL COMMENT '票品名称',
    `type` VARCHAR(50) NOT NULL COMMENT '类型: concert/体育/电影/演出',
    `description` TEXT COMMENT '详细描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    `image` VARCHAR(255) DEFAULT NULL COMMENT '图片路径',
    `status` VARCHAR(10) NOT NULL DEFAULT 'on_sale' COMMENT 'on_sale: 在售, sold_out: 售罄, off: 下架',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
                                       `id` INT PRIMARY KEY AUTO_INCREMENT,
                                       `order_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    `user_id` INT NOT NULL,
    `total_price` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
    `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT 'pending: 待支付, paid: 已支付, cancelled: 已取消',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单项表
CREATE TABLE IF NOT EXISTS `order_item` (
                                            `id` INT PRIMARY KEY AUTO_INCREMENT,
                                            `order_id` INT NOT NULL,
                                            `ticket_name` VARCHAR(200) NOT NULL COMMENT '票品名称',
                                            `quantity` INT NOT NULL DEFAULT 1 COMMENT '购买数量',
                                            `price` DECIMAL(10,2) NOT NULL COMMENT '购买时的单价',
    FOREIGN KEY (`order_id`) REFERENCES `order`(`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入默认管理员 (密码: admin123)
INSERT INTO `user` (`username`, `password`, `role`) VALUES ('admin', 'admin123', 'admin')
    ON DUPLICATE KEY UPDATE `username`=`username`;

-- 插入测试票品数据
INSERT INTO `ticket` (`name`, `type`, `description`, `price`, `stock`, `image`, `status`) VALUES
                                                                                              ('周杰伦2025巡回演唱会 - 北京站', 'concert', '亚洲天王周杰伦2025年世界巡回演唱会北京站，经典歌曲现场演绎，不容错过。', 880.00, 500, '/static/images/演唱会/周杰伦2026巡回演唱会 - 北京站.png', 'on_sale'),
                                                                                              ('五月天2025演唱会 - 上海站', 'concert', '五月天「好好好想见到你」演唱会上海站，让我们一起歌唱青春。', 680.00, 300, '/static/images/演唱会/五月天2025演唱会 - 上海站.jpg', 'on_sale'),
                                                                                              ('CBA总决赛 - 广东vs辽宁', '体育', 'CBA总决赛巅峰对决，广东宏远对阵辽宁本钢，现场见证冠军诞生。', 580.00, 200, '/static/images/体育/CBA总决赛 - 广东vs辽宁.jpg', 'on_sale'),
                                                                                              ('中超联赛 - 上海海港vs山东泰山', '体育', '中超联赛焦点战，上海海港主场迎战山东泰山。', 280.00, 800, '/static/images/体育/中超联赛 - 上海海港vs山东泰山.jpg', 'on_sale'),
                                                                                              ('《流浪地球3》首映礼', '电影', '国产科幻巨制《流浪地球3》首映礼，主创团队亲临现场。', 128.00, 1000, '/static/images/电影/流浪地球2.jpg', 'on_sale'),
                                                                                              ('《封神第二部》IMAX场', '电影', '乌尔善导演魔幻巨制《封神第二部》IMAX版本，震撼视听。', 158.00, 600, '/static/images/电影/《封神第二部》.jpg', 'on_sale'),
                                                                                              ('话剧《雷雨》2025巡演', '演出', '经典话剧《雷雨》，曹禺先生传世之作，全新编排。', 388.00, 400, '/static/images/演出/话剧《雷雨》2025巡演.jpg', 'on_sale'),
                                                                                              ('德云社相声专场 - 北京', '演出', '德云社相声大会，郭德纲、于谦领衔主演，欢乐不断。', 488.00, 350, '/static/images/演出/德云社相声专场 - 北京.jpg', 'on_sale')
    ON DUPLICATE KEY UPDATE `name`=`name`;