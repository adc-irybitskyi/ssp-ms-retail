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
	PRIMARY KEY (store_id),
);
