-- 商品表
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                           `name` varchar(100) DEFAULT NULL,
                           `stock` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO product (id, name, stock) VALUES(1, 'aa', 100);