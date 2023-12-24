use dandelion;
-- ROLES
-- insert into `role`(`role_name`) values ("ADMIN");
-- insert into `role`(`role_name`) values ("MANAGER");
-- insert into `role`(`role_name`) values ("CUSTOMER");


select * from `user`;
select * from `role`;
select * from `user_role`;
select * from `user_authentication`;
select * from `product`;
select * from `category`;
select * from `address`;

-- PAYMENT TYPE
insert into `payment_method`(`name`, `is_enabled`) values('COD', true);
select * from `payment_method`;


-- ADDRESS
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country` , `is_default`) 
	VALUES (1, '123 Nguyen Van A Street', 'Apartment 4B', '0123456789', 'Ho Chi Minh City', 'District 1', 'Phuong Tan Dinh', '700000', 'Viet Nam', 1);
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`, `is_default`) 
	VALUES (1, '456 Le Loi Avenue', NULL, '0987654321', 'Hanoi', 'Ba Dinh', 'Phuong Quoc Tu Giam', '100000', 'Viet Nam', 0);
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`, `is_default`) 
	VALUES (2, '789 Tran Hung Dao Street', NULL, '0771234567', 'Da Nang', 'Hai Chau', 'Phuong Hai Chau 1', '550000', 'Viet Nam', 1);
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`, `is_default`) 
	VALUES (2, '101 Vo Van Tan Road', 'Apartment 7C', '0909876543', 'Can Tho', 'Ninh Kieu', 'Phuong An Binh', '920000', 'Viet Nam', 0);
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`, `is_default`) 
	VALUES (3, '222 Tran Phu Street', NULL, '0881122334', 'Hue', 'Phuong Vinh Ninh', 'Phuong Ninh Phong', '530000', 'Viet Nam', 1);

select * from `address`;


-- USER AUTHENTICATION
select * from `user_authentication`;

select * from `user`;

-- PRODUCT CATEGORIES
insert into `category`(`name`) values("cate1");
insert into `category`(`name`) values("cate2");
insert into `category`(`name`) values("cate3");
insert into `category`(`name`) values("cate4");
insert into `category`(`name`) values("cate5");

select * from `category`;

-- PRODUCT
insert into `product`(`category_id`, `name`, `weight`, `quantity`, `media_url`, `price`, `description`, `information`, `tag`) 
	values("1", "StreetStyle Classic Canvas Orange", 500, 1523, "https://res.cloudinary.com/de8xbko8y/image/upload/v1694839085/uploads/sample_image.jpg", 599, "this is desc", " this is information", "tag1,tag2"); 
insert into `product`(`category_id`, `name`, `weight`, `quantity`, `media_url`, `price`, `description`, `information`, `tag`) 
	values("1", "EcoFlex Comfort Apple", 500, 1732, "https://res.cloudinary.com/de8xbko8y/image/upload/v1694839085/uploads/sample_image.jpg", 399, "this is desc", " this is information", "tag1,tag3"); 

DELIMITER //
CREATE PROCEDURE generateProducts(IN num_rows INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE num_tags INT;
    DECLARE random_tags VARCHAR(255);
    WHILE i <= num_rows DO
        SET num_tags = FLOOR(RAND() * 5) + 1;
        SET random_tags = '';
        
        WHILE num_tags > 0 DO
            SET random_tags = CONCAT(random_tags, 'tag', FLOOR(RAND() * 10) + 1); -- Generate tags from 1 to 9
            SET num_tags = num_tags - 1;
            
            IF num_tags > 0 THEN
                SET random_tags = CONCAT(random_tags, ',');
            END IF;
        END WHILE;
        
        INSERT INTO `product`(`category_id`, `name`, `weight`, `quantity`, `media_url`, `price`, `description`, `information`, `tag`)
        VALUES
            (FLOOR(RAND() * 5) + 1, 
             CONCAT('Product ', FLOOR(RAND() * 1000 * i)), 
             FLOOR(RAND() * 1000) + 100, 
             FLOOR(RAND() * 1000) + 1, 
             'https://res.cloudinary.com/de8xbko8y/image/upload/v1696991580/uploads/vn-11134207-7r98o-lknwgw53gqkw0a_txekyq.jpg', 
             FLOOR(RAND() * 500) + 50, 
             CONCAT('This is description ', i), 
             CONCAT('This is information ', i), 
             random_tags);
        SET i = i + 1;
    END WHILE;
    
END //
DELIMITER ;

call generateProducts(100);




select * from `product`; 
select * from `category`;



-- SHOPPING CART
 
select * from `shopping_cart`;
select * from `shopping_cart_item`;

-- ORDER STATUS
INSERT INTO `order_status` (`status`) VALUES ('TO_PAY');
INSERT INTO `order_status` (`status`) VALUES ('PROCESSING');
INSERT INTO `order_status` (`status`) VALUES ('DELIVERING');
INSERT INTO `order_status` (`status`) VALUES ('CANCELED');
INSERT INTO `order_status` (`status`) VALUES ('COMPLETED');
INSERT INTO `order_status` (`status`) VALUES ('REFUND');

select * from `order_status`;

-- SHIPPING METHOD
INSERT INTO `shipping_method` (`name`, `price`) VALUES ('Standard Shipping', 5.99);
INSERT INTO `shipping_method` (`name`, `price`) VALUES ('Express Shipping', 12.50);
INSERT INTO `shipping_method` (`name`, `price`) VALUES ('Next Day Delivery', 19.99);
INSERT INTO `shipping_method` (`name`, `price`) VALUES ('Economy Shipping', 3.99);
INSERT INTO `shipping_method` (`name`, `price`) VALUES ('Free Shipping', 0.00);

select * from `shipping_method`;

-- SHOP ORDER
INSERT INTO `shop_order` (`user_id`, `order_transaction_id`,`user_full_name`, `user_comment`, `user_phone`, `shipping_address`, `shipping_method_id`, `merchandise_total`, `shipping_fee`, `total`, `order_status_id`, `tracking_code`)
VALUES (1, null, 'ly dai phat,' 'comment', '0326423996', '123 Nguyen Van A Street', 2, 898.00, 2.0, 900.00, 4, 'TRACK123456');

select * from `shop_order`;

-- ORDER DETAIL
insert into `order_detail`(`product_id`, `shop_order_id`, `quantity`, `price`) values (1, 1, 1, 399);
insert into `order_detail`(`product_id`, `shop_order_id`, `quantity`, `price`) values (2, 1, 1, 499);

select * from `shop_order`;
select * from `order_detail`;
select * from `shopping_cart`;

-- USER REVIEW
INSERT INTO user_review (`user_id`, `ordered_product_id`, `rating_value`, `comment`, `like`, `media_url`)
	VALUES (1, 1, 4, 'This product exceeded my expectations! It\'s fantastic.', 12, 'https://example.com/review1.jpg');

INSERT INTO user_review (`user_id`, `ordered_product_id`, `rating_value`, `comment`, `like`, `media_url`)
	VALUES (1, 2, 5, 'I love this product. It\'s the best purchase I\'ve made.', 8, 'https://example.com/review2.png');
    
select * from `user_review`;
-- 
