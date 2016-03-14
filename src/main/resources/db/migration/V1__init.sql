CREATE TABLE stores (
	store_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
    created_by bigint(20),
    updated_by bigint(20),
    created_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (store_id),
);

CREATE TABLE products (
	product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	store_id BIGINT UNSIGNED NOT NULL,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(50),
	sku VARCHAR(10) NOT NULL,
	price double NOT NULL,
    created_by bigint(20),
    updated_by bigint(20),
    created_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (product_id)
);

CREATE TABLE stocks (
	stock_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	product_id BIGINT UNSIGNED NOT NULL,
	store_id BIGINT UNSIGNED NOT NULL,
	available_count BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (stock_id)
);

CREATE TABLE orders (
	order_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	store_id BIGINT UNSIGNED NOT NULL,
	order_date timestamp NOT NULL,
	status int NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone VARCHAR(10) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE order_products (
	order_product_id BIGINT UNSIGNED NOT NULL,
	order_id BIGINT UNSIGNED NOT NULL,
	product_id BIGINT UNSIGNED NOT NULL,
	product_count BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (order_id)
);

--Data--
INSERT INTO stores (store_id, name) VALUES (1001, 'global-store-1');
INSERT INTO stores (store_id, name) VALUES (1002, 'global-store-2');
