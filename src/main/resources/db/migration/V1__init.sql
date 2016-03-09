CREATE TABLE stores (
	store_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL DEFAULT '',
    created_by bigint(20) NOT NULL,
    updated_by bigint(20) NOT NULL,
    created_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (store_id),
);

INSERT INTO stores (name, created_by, updated_by) VALUES ('store-1', 0, 0);
INSERT INTO stores (name, created_by, updated_by) VALUES ('store-2', 0, 0);
