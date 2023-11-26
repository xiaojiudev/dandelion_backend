-- drop database dandelion;
create database if not exists dandelion;
use dandelion;

drop table if exists `user`;

CREATE TABLE `user` (
	  `id` bigint PRIMARY KEY AUTO_INCREMENT,
	  `email` varchar(255) UNIQUE NOT NULL,
	  `phone` varchar(20) NOT NULL,
	  `password` varchar(1000) NOT NULL,
	  `full_name` varchar(100) NOT NULL,
	  `gender` ENUM ('MALE', 'FEMALE', 'OTHER') default 'OTHER',
	  `avatar` varchar(255) DEFAULT "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png",
	  `blocked` timestamp DEFAULT NULL,
	  `created_at` timestamp DEFAULT (now()),
	  `modified_at` timestamp DEFAULT NULL
	) engine=InnoDB default char set=utf8mb4;

drop table if exists `address`;

CREATE TABLE `address` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint not null,
  `address_line_1` varchar(255) DEFAULT null,
  `address_line_2` varchar(255) DEFAULT null,
  `phone` varchar(20) DEFAULT null,
  `city` varchar(255) DEFAULT null,
  `district` varchar(255) DEFAULT null,
  `ward` varchar(255) DEFAULT null,
  `postal_code` varchar(20) DEFAULT null,
  `country` varchar(255) default("Viet Nam"),
  `is_default` boolean DEFAULT false,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null,
  unique key (`user_id`, `address_line_1`)
) engine=InnoDB default char set=utf8mb4;

drop table if exists `role`;

CREATE TABLE `role` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `role_name` varchar(100) unique NOT NULL
) engine=InnoDB default char set=utf8mb4;

drop table if exists `user_role`;

CREATE TABLE `user_role` (
  `user_id` bigint,
  `role_id` bigint default 3,
  primary key ( `user_id`, `role_id`)
) engine=InnoDB default char set=utf8mb4;

drop table if exists `user_authentication`;

CREATE TABLE `user_authentication` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint default null,
  `token` varchar(255) null,
  `expired_at` timestamp null,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `product`;

CREATE TABLE `product` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `category_id` bigint default null,
  `name` varchar(255) unique NOT NULL,
  `weight` double(10,2) unsigned NULL,
  `quantity` int unsigned NOT NULL default 0,
  `media_url` text NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` text NOT NULL,
  `information` longtext NOT NULL,
  `tag` varchar(500) null,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `category`;

CREATE TABLE `category` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) unique NOT NULL
) engine=InnoDB default char set=utf8mb4;

drop table if exists `shopping_cart`;

CREATE TABLE `shopping_cart` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint not null,
  `status` boolean not null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `shopping_cart_item`;

CREATE TABLE `shopping_cart_item` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `cart_id` bigint not null,
  `product_id` bigint not null,
  `quantity` int unsigned NOT NULL default 1,
  unique key (`cart_id`, `product_id`)
) engine=InnoDB default char set=utf8mb4;

drop table if exists `shop_order`;

CREATE TABLE `shop_order` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint not null, 
  `shipping_method_id` bigint not null,
  `order_transaction_id` bigint null,
  `order_status_id` bigint not null,
  `user_full_name` varchar(100) NOT NULL,
  `user_comment` varchar(255) null, 
  `user_phone` varchar(20) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  `merchandise_total` decimal(10,2) unsigned NOT NULL,
  `shipping_fee` decimal(10,2) unsigned NOT NULL,
  `total` decimal(10,2) unsigned NOT NULL,
  `tracking_code` varchar(50) unique not null,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `order_detail`;

CREATE TABLE `order_detail` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `product_id` bigint not null,
  `shop_order_id` bigint not null,
  `quantity` int unsigned NOT NULL default 1 check(`quantity` >= 1),
  `price` decimal(10,2) unsigned not null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `order_transaction`;

CREATE TABLE `order_transaction` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `payment_method_id` bigint not null,
  `transaction_status` ENUM ('PENDING', 'FAILED', 'SUCCESS'),
  `transaction_amount` decimal(10,2) NOT NULL,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null
);


drop table if exists `payment_method`;

CREATE TABLE `payment_method` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) unique NOT NULL,
  `is_enabled` boolean not null
) engine=InnoDB default char set=utf8mb4;


drop table if exists `shipping_method`;

CREATE TABLE `shipping_method` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) unique NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT NULL
) engine=InnoDB default char set=utf8mb4;

drop table if exists `order_status`;

CREATE TABLE `order_status` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `status` ENUM ('TO_PAY', 'PROCESSING', 'DELIVERING', 'CANCELED', 'COMPLETED', 'REFUND') unique not null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `user_review`;

CREATE TABLE `user_review` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint not null,
  `ordered_product_id` bigint not null,
  `rating_value` ENUM ('1', '2', '3', '4', '5') not null,
  `comment` varchar(300) default null,
  `like` int unsigned DEFAULT 0,
  `media_url` text DEFAULT null,
  `created_at` timestamp DEFAULT (now()),
  `modified_at` timestamp DEFAULT null
) engine=InnoDB default char set=utf8mb4;

drop table if exists `product_wishlist`;

CREATE TABLE `product_wishlist` (
  `user_id` bigint,
  `product_id` bigint,
  primary key(`user_id`, `product_id`)
) engine=InnoDB default char set=utf8mb4;

drop table if exists `promotion`;

CREATE TABLE `promotion` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name_code` varchar(20) unique NOT NULL,
  `description` varchar(255) NOT NULL,
  `discount_rate` decimal(10,2) NOT NULL check (`discount_rate` > 0 and `discount_rate` <= 100),
  `max_price_discount` decimal(10,2) unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `created_at` timestamp DEFAULT (now())
) engine=InnoDB default char set=utf8mb4;

drop table if exists `promotion_product`;

CREATE TABLE `promotion_product` (
  `product_id` bigint,
  `promotion_id` bigint,
  primary key (`product_id`, `promotion_id`)
) engine=InnoDB default char set=utf8mb4;

ALTER TABLE `user_authentication` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `address` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `user_role` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `user_role` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `product` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `shopping_cart` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `shopping_cart_item` ADD FOREIGN KEY (`cart_id`) REFERENCES `shopping_cart` (`id`);

ALTER TABLE `shopping_cart_item` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `shop_order` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `shop_order` ADD FOREIGN KEY (`shipping_method_id`) REFERENCES `shipping_method` (`id`);

ALTER TABLE `shop_order` ADD FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`);

ALTER TABLE `shop_order` ADD FOREIGN KEY (`order_transaction_id`) REFERENCES `order_transaction` (`id`);

ALTER TABLE `order_transaction` ADD FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`);

ALTER TABLE `order_detail` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `order_detail` ADD FOREIGN KEY (`shop_order_id`) REFERENCES `shop_order` (`id`);

ALTER TABLE `user_review` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `user_review` ADD FOREIGN KEY (`ordered_product_id`) REFERENCES `order_detail` (`id`);

ALTER TABLE `product_wishlist` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `product_wishlist` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`);
