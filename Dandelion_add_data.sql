use dandelion;

-- USER - PW: 1234567a
insert into `user`(`email`, `phone`, `password`, `full_name`) values('ldphat99@gmail.com', '0326423996', '$10$rOq4Q6.4CWC9cY5hqJbuZuMnfjp4yxM4rvytBuitFzZYbndLM0Pb2', 'ly dai phat');
insert into `user`(`email`, `phone`, `password`, `full_name`) values('zzsakura2018@gmail.com', '0986858157', '$10$rOq4Q6.4CWC9cY5hqJbuZuMnfjp4yxM4rvytBuitFzZYbndLM0Pb2', 'ly dai hai');
insert into `user`(`email`, `phone`, `password`, `full_name`) values('phatb1705292@student.ctu.edu.vn','0335747997', '$10$rOq4Q6.4CWC9cY5hqJbuZuMnfjp4yxM4rvytBuitFzZYbndLM0Pb2', 'nguyen van a');

select * from `user`; 

-- ROLES
insert into `role`(`role_name`) values ("ADMIN");
insert into `role`(`role_name`) values ("MANAGER");
insert into `role`(`role_name`) values ("CUSTOMER");

select * from `role`;

;-- USER ROLES
	-- ADMIN with id=1 have 3 roles
insert into `user_role`(`user_id`, `role_id`) values (1, 1);
insert into `user_role`(`user_id`, `role_id`) values (1, 2);
insert into `user_role`(`user_id`, `role_id`) values (1, 3);

	-- MANAGER with id=2 have 2 roles
insert into `user_role`(`user_id`, `role_id`) values (2, 2);
insert into `user_role`(`user_id`, `role_id`) values (2, 3);

	-- CUSTOMER with id=3 have only 1 role
insert into `user_role`(`user_id`, `role_id`) values (3, 3);

select * from `user_role`;

-- PAYMENT TYPE
insert into `payment_type`(`name`) values('COD');
INSERT INTO `payment_type` (`name`, `provider`, `account_no`, `expiry_date`) VALUES ('Credit Card', 'Visa', '1234567890123456', '2024-12-31');
INSERT INTO `payment_type` (`name`, `provider`, `account_no`, `expiry_date`) VALUES ('Debit Card', 'MasterCard', '9876543210987654', '2023-10-15');
INSERT INTO `payment_type` (`name`, `provider`, `account_no`, `expiry_date`) VALUES ('PayPal', 'PayPal Inc.', 'paypal@example.com', '2024-06-30');
INSERT INTO `payment_type` (`name`, `provider`, `account_no`, `expiry_date`) VALUES ('Bank Transfer', 'ABC Bank', 'ABC123456789', '2023-09-25');

select * from `payment_type`;

-- PAYMENT METHOD
insert into `user_payment_method`(`user_id`, `payment_type_id`) values(1, 1);
insert into `user_payment_method`(`user_id`, `payment_type_id`) values(1, 2);
insert into `user_payment_method`(`user_id`, `payment_type_id`) values(2, 3);
insert into `user_payment_method`(`user_id`, `payment_type_id`) values(2, 4);
insert into `user_payment_method`(`user_id`, `payment_type_id`) values(3, 5);

select * from `user_payment_method`;

-- ADDRESS
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`) 
	VALUES (1, '123 Nguyen Van A Street', 'Apartment 4B', '0123456789', 'Ho Chi Minh City', 'District 1', 'Phuong Tan Dinh', '700000', 'Viet Nam');
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`) 
	VALUES (1, '456 Le Loi Avenue', NULL, '0987654321', 'Hanoi', 'Ba Dinh', 'Phuong Quoc Tu Giam', '100000', 'Viet Nam');
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`) 
	VALUES (2, '789 Tran Hung Dao Street', NULL, '0771234567', 'Da Nang', 'Hai Chau', 'Phuong Hai Chau 1', '550000', 'Viet Nam');
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`) 
	VALUES (2, '101 Vo Van Tan Road', 'Apartment 7C', '0909876543', 'Can Tho', 'Ninh Kieu', 'Phuong An Binh', '920000', 'Viet Nam');
INSERT INTO `address` (`user_id`, `address_line_1`, `address_line_2`, `phone`, `city`, `district`, `ward`, `postal_code`, `country`) 
	VALUES (3, '222 Tran Phu Street', NULL, '0881122334', 'Hue', 'Phuong Vinh Ninh', 'Phuong Ninh Phong', '530000', 'Viet Nam');

select * from `address`;

-- USER ADDRESS
insert into `user_address`(`user_id`, `address_id`) values(1, 1);
insert into `user_address`(`user_id`, `address_id`) values(1, 2);
insert into `user_address`(`user_id`, `address_id`) values(1, 3);
insert into `user_address`(`user_id`, `address_id`) values(2, 1);
insert into `user_address`(`user_id`, `address_id`) values(2, 2);
insert into `user_address`(`user_id`, `address_id`) values(2, 3);
insert into `user_address`(`user_id`, `address_id`) values(2, 4);
insert into `user_address`(`user_id`, `address_id`) values(3, 1);
insert into `user_address`(`user_id`, `address_id`) values(3, 5);

select * from `user_address`;

-- USER AUTHENTICATION
INSERT INTO `user_authentication` (`token`) 
	VALUES ('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ._Xy7BQdvm-F6wGm3NJ7YxKWul1t7EC4v5XRej-OvMwA');
INSERT INTO `user_authentication` (`token`) 
	VALUES ('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNTY3ODkwMTIzIiwibmFtZSI6IkpvaG4gSm9obiIsImlhdCI6MTUxNjIzOTAyMn0.MKlwF2j52XGe5JBCbCwK8LdrsrKHH64UM3bdXvobvDw');
INSERT INTO `user_authentication` (`token`) 
	VALUES ('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMzQ1Njc4OTAiLCJuYW1lIjoiTWFyayBMb25nIiwiaWF0IjoxNTE2MjM5MDIyfQ.hWteR-2tUVv2KsXj3JJhHPtUkVh0x-Y1jP4N7KnLJfE');

select * from `user_authentication`;

-- UPDATE USER TABLE FOR AUTHENTICATION
update `user` set `user_authentication_id`= 1  where `user`.id = 1;
update `user` set `user_authentication_id`= 2  where `user`.id = 2;
update `user` set `user_authentication_id`= 3  where `user`.id = 3;

select * from `user`;

-- PRODUCT CATEGORIES
insert into `category`(`name`) values("Sneakers");
insert into `category`(`name`) values("Sandals");
insert into `category`(`name`) values("Boots");

select * from `category`;

-- PRODUCT VARIATION
insert into `variation`(`category_id`, `name`) values(1, "Size");
insert into `variation`(`category_id`, `name`) values(1, "Colors");

select * from `variation`;

-- PRODUCT VARIATION OPTION
	-- Ex:  variation_id = 1 => Size
insert into `variation_option`(`variation_id`, `value`) values (1,"36");
insert into `variation_option`(`variation_id`, `value`) values (1,"37");
insert into `variation_option`(`variation_id`, `value`) values (1,"38");
insert into `variation_option`(`variation_id`, `value`) values (1,"39");
insert into `variation_option`(`variation_id`, `value`) values (1,"40");

	-- Ex:  variation_id = 2 => Colors
insert into `variation_option`(`variation_id`, `value`) values (2,"Black");
insert into `variation_option`(`variation_id`, `value`) values (2,"White");
insert into `variation_option`(`variation_id`, `value`) values (2,"Gray");

select * from `variation_option`;

-- PRODUCT
insert into `product`(`category_id`, `name`, `description`, `information`) 
	values("1", "StreetStyle Classic Canvas Sneakers", "this is desc", " this is information"); 
insert into `product`(`category_id`, `name`, `description`, `information`) 
	values("1", "EcoFlex Comfort Sneakers", "this is desc", " this is information"); 

select * from `product`; 
select * from `category`;

-- PRODUCT ITEM 
insert into `product_item`(`product_id`, `SKU`, `qty_in_stock`,  `price`, `img_url`) 
	values (1, "SKU1", 1823, 399, "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/f1247bad-7acf-4af6-8198-8c5aab26eeb2/air-max-flyknit-racer-shoes-Q9lN71.png");
insert into `product_item`(`product_id`, `SKU`, `qty_in_stock`,  `price`, `img_url`) 
	values (1, "SKU2", 3532, 499, "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/8199b269-d037-4d65-8d6a-bf16a1f7a411/air-max-90-ltr-older-shoes-9xnt2B.png");

select * from `product_item`;

-- PRODUCT CONFIGURATION
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 1);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 2);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 3);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 4);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 5);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 6);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 7);
insert into `product_configuration`(`product_item_id`, `variation_option_id`) values (1, 8);

select * from `product_configuration`;
select * from `variation_option`;

-- SHOPPING CART
insert into `shopping_cart`(`user_id`, `status`) values(1, true);
insert into `shopping_cart`(`user_id`, `status`) values(2, true);
insert into `shopping_cart`(`user_id`, `status`) values(3, true);
 
 select * from `shopping_cart`;
 
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(1, 1 , 5);
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(1, 2 , 3);
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(2, 1 , 8);
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(2, 2 , 10);
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(3, 1 , 2);
insert into `shopping_cart_item`(`cart_id`, `product_item_id`, `quantity`) values(3, 2 , 5);

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
INSERT INTO `shop_order` (`user_id`, `payment_method`, `shipping_address`,`order_user_fullname`, 
  `order_user_phone`, `order_user_email`, `shipping_method_id`, `order_total`, `order_status_id`, `order_tracking_number`)
VALUES (1, 'COD', '123 Nguyen Van A Street', 'Ly Dai Phat', '0326423996', 'ldphat99@gmail.com', 2, 898.00, 4, 'TRACK123456');

select * from `shop_order`;

-- ORDER DETAIL
insert into `order_detail`(`product_item_id`, `shop_order_id`, `quantity`, `price`) values (1, 1, 1, 399);
insert into `order_detail`(`product_item_id`, `shop_order_id`, `quantity`, `price`) values (2, 1, 1, 499);

select * from `order_detail`;

-- USER REVIEW
INSERT INTO user_review (`user_id`, `ordered_product_id`, `rating_value`, `comment`, `like`, `media_url`)
	VALUES (1, 1, 4, 'This product exceeded my expectations! It\'s fantastic.', 12, 'https://example.com/review1.jpg');

INSERT INTO user_review (`user_id`, `ordered_product_id`, `rating_value`, `comment`, `like`, `media_url`)
	VALUES (1, 2, 5, 'I love this product. It\'s the best purchase I\'ve made.', 8, 'https://example.com/review2.png');
    
select * from `user_review`;

