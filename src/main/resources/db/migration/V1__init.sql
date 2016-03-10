CREATE TABLE stores (
	store_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
    created_by bigint(20) NOT NULL,
    updated_by bigint(20) NOT NULL,
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
	price decimal(15,2) NOT NULL,
    created_by bigint(20) NOT NULL,
    updated_by bigint(20) NOT NULL,
    created_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (store_id)
);

CREATE TABLE stocks (
	product_id BIGINT UNSIGNED NOT NULL,
	store_id BIGINT UNSIGNED NOT NULL,
	available_count BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (product_id, store_id)
);

CREATE TABLE orders (
	order_id BIGINT UNSIGNED NOT NULL,
	store_id BIGINT UNSIGNED NOT NULL,
	order_date timestamp  NOT NULL,
	status tinyint NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone CHAR(10) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE order_products (
	order_id BIGINT UNSIGNED NOT NULL,
	product_id BIGINT UNSIGNED NOT NULL,
	product_count BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (order_id)
);
