-- 商品表
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`    bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `stock` bigint                                                        DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
INSERT INTO product (id, name, stock)
VALUES (1, 'aa', 100);